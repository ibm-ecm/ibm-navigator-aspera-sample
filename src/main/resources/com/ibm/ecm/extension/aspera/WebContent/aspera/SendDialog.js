/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/_base/array",
	"dojo/aspect",
	"dojo/dom-class",
	"dojo/dom-geometry",
	"dojo/string",
	"dijit/focus",
	"dijit/form/ValidationTextBox",
	"dijit/form/CheckBox",
	"dijit/form/SimpleTextarea",
	"dijit/TitlePane",
	"dojox/html/entities",
	"ecm/LoggerMixin",
	"ecm/MessagesMixin",
	"ecm/model/Request",
	"ecm/model/Desktop",
	"ecm/model/Repository",
	"ecm/model/Message",
	"ecm/widget/HoverHelp",
	"ecm/widget/dialog/BaseDialog",
	"ecm/widget/dialog/MessageDialog",
	"aspera/Util",
	"aspera/Package",
	"dojo/text!./templates/SendDialogContent.html",
	"dojo/i18n!./nls/messages"
], function(declare, lang, array, aspect, domClass, domGeometry, string, focus, ValidationTextBox, CheckBox, SimpleTextarea, TitlePane, entities, LoggerMixin, MessagesMixin, Request, Desktop, Repository, Message, HoverHelp, BaseDialog, MessageDialog, Util, Package, template, messages) {

	/**
	 * @name aspera.SendDialog
	 * @class A dialog to send a package
	 * @augments ecm.widget.dialog.BaseDialog
	 */
	var sendDialog = declare("aspera.SendDialog", [
		BaseDialog,
		LoggerMixin,
		MessagesMixin
	], {
		/** @lends aspera.SendDialog.prototype */
		contentString: template,

		autofocus: false,
		keepContentAreaOverflowAuto: true,

		/**
		 * The current repository
		 */
		repository: null,

		/**
		 * The items to be sent
		 */
		items: null,

		_userId: null,

		postMixInProperties: function() {
			this.inherited(arguments);
			lang.mixin(this.messages, messages);
		},

		postCreate: function() {
			var methodName = "postCreate";
			this.logEntry(methodName);
			this.inherited(arguments);

			domClass.add(this.domNode, "ecmAsperaSendDialog");
			this.setResizable(true);
			this.setTitle(this.messages.send_dialog_title);
			this.setIntroText(this.messages.send_dialog_info);
			this.sendButton = this.addButton(this.messages.send_dialog_send_button_label, lang.hitch(this, function() {
				this.send(lang.hitch(this, function(pkg) {
					this._showStartedMessage(pkg);
				}));
			}), true, false);
			this.own(aspect.after(this, "onHide", lang.hitch(this, function() {
			})));
			this.own(aspect.after(this.sender, "onChange", lang.hitch(this, function() {
				this._setUserPaneTitle();
			})));
			this._notificationPane.watch("open", lang.hitch(this, function(name, oldValue, newValue) {
				if (newValue === true) {
					var element = this.contentArea;
					setTimeout(function() {
						element.scrollTop = element.scrollHeight;
					}, 200);
				}
			}));
			this.own(aspect.after(this.notifyOnUpload, "onChange", lang.hitch(this, function() {
				this._setNotificationPaneTitle();
			})));
			this.own(aspect.after(this.notifyOnDownload, "onChange", lang.hitch(this, function() {
				this._setNotificationPaneTitle();
			})));
			this.own(aspect.after(Desktop, "onSessionExpired", lang.hitch(this, function() {
				if (this._isUsingNonce()) {
					this.sender.reset();
					this.password.reset();
					this._userPane.set("open", true);
				}
			})));
			this.own(aspect.after(Desktop, "onLogout", lang.hitch(this, function() {
				this.sender.reset();
				this.password.reset();
			})));
			if (!this.items)
				this.items = [];
			this.logExit(methodName);
		},

		_setUserPaneTitle: function() {
			var sender = this.sender.get("value");
			this._userPane.set("title", entities.encode(string.substitute(this.messages.send_dialog_sender_title, [
				sender ? sender : this.messages.send_dialog_not_set
			])));
		},

		_setNotificationPaneTitle: function() {
			var uploadOn = this.notifyOnUpload.get("value") == "on";
			var downloadOn = this.notifyOnDownload.get("value") == "on";
			this._notificationPane.set("title", entities.encode(string.substitute(this.messages.send_dialog_notify_title, [
				uploadOn && downloadOn ? this.messages.send_dialog_notifyOnUploadDownload : uploadOn ? this.messages.send_dialog_notifyOnUpload_label : downloadOn ? this.messages.send_dialog_notifyOnDownload_label : this.messages.send_dialog_not_set
			])));
		},

		/**
		 * Shows the send dialog. Resets the sender info if the current user doesn't match with the sender info.
		 */
		show: function() {
			this._validateUser();
			this._setSendMessage();
			this._userPane.set("open", !this.sender.get("value") || !this.password.get("value"));
			this._setUserPaneTitle();
			this._notificationPane.set("open", false);
			this._setNotificationPaneTitle();

			return this.inherited(arguments).then(lang.hitch(this, function() {
				this._userPane.get("open") ? this.sender.focus() : this.recipients.focus();
			}));
		},

		/**
		 * Hides the send dialog. Resets package data.
		 */
		hide: function() {
			var promise = this.inherited(arguments);

			this.items = [];
			this.repository = null;
			this.recipients.reset();
			this.title.reset();
			this.note.reset();
			this.earPassphrase.reset();

			return promise;
		},

		_validateUser: function() {
			if (!this._isUserValid()) {
				this.logDebug("_validateUser", "reset sender info");
				this.sender.reset();
				this.password.reset();
			}
			this._userId = Desktop.userId;
		},

		_isUserValid: function() {
			return this._userId && this._userId == Desktop.userId;
		},

		_setSendMessage: function() {
			var message = this.items.length == 1 ? string.substitute(this.messages.send_dialog_send_one, [
				this.items[0].name ? this.items[0].name : ""
			]) : string.substitute(this.messages.send_dialog_send_more, [
				this.items.length
			]);
			this.sendMessageNode.innerHTML = message;
		},

		/**
		 * Sets the items to be sent and the current repository. The repository of each item takes precedence over the current repository.
		 *
		 * @param items The items to be sent
		 * @param repository The current repository
		 */
		setItems: function(items, repository) {
			var realItems = array.map(items, function(item) {
				return item.item ? item.item : item;
			});

			this.items = realItems;
			this.repository = repository;
		},

		/**
		 * Enables the send button if all the required parameters are set.
		 *
		 * @return true if the send button is enabled
		 */
		isSendEnabled: function() {
			var enabled = this.sender.get("value") && this.password.get("value") && this.recipients.get("value") && this.title.get("value") && this.items instanceof Array && this.items.length > 0 && this._isUserValid();
			this.sendButton.set("disabled", !enabled);

			return enabled;
		},

		/**
		 * Sends the package and calls back when the transfer is started or an error is returned.
		 *
		 * @param callback The function to call back when the transfer is started
		 * @param errorback The function to call back when an error is returned
		 */
		send: function(callback, errorback) {
			var methodName = "send";
			this.logEntry(methodName);
			if (!this.isSendEnabled()) {
				this.logDebug(methodName, "invalid parameters");
				if (lang.isFunction(errorback))
					errorback();

				return null;
			}

			var pkg = new Package({
				recipients: this.recipients.get("value"),
				title: this.title.get("value"),
				note: this.note.get("value"),
				earPassphrase: this.earPassphrase.get("value"),
				notifyOnUpload: this.notifyOnUpload.get("value") == "on",
				notifyOnDownload: this.notifyOnDownload.get("value") == "on",
				items: this.items,
				repository: this.repository
			});
			if (!this._isUsingNonce()) {
				pkg.sender = this.sender.get("value");
				pkg.password = this.password.get("value");
			}
			var request = pkg.send(lang.hitch(this, function() {
				this._nonce = request.xmlHttpRequest.getResponseHeader("ICN-AP-Nonce");
				if (this._nonce)
					this.password.set("value", this._nonce);
				if (lang.isFunction(callback))
					callback(pkg);
				this.hide();
			}), errorback, {
				"Cache-Control": "no-cache",
				"ICN-AP-Nonce": this._nonce
			});
			this.logExit(methodName);

			return request;
		},

		_isUsingNonce: function() {
			var password = this.password.get("value");

			return !!this._nonce && this._nonce == password;
		},

		_showStartedMessage: function(pkg) {
			if (this._startedMessage)
				this._startedMessage.destroyRecursive(false);

			this._startedMessage = new MessageDialog({
				text: string.substitute(this.messages.send_dialog_started, [
					pkg.title
				]),
				onHide: function() {
					Util.watchPackageStatus(pkg);
				}
			});

			this._startedMessage.show();
		},

		resize: function() {
			var content = this.contentArea.childNodes;
			if (content && content.length && content.length > 0) {
				var box = domGeometry.getContentBox(content[0]);
				domGeometry.setMarginBox(this.contentArea, box);
			}

			this.inherited(arguments);
		},

		destroy: function() {
			var methodName = "destroy";
			this.logEntry(methodName);
			if (this._startedMessage) {
				this._startedMessage.destroyRecursive(false);
				this._startedMessage = null;
			}

			this.inherited(arguments);
			this.logExit(methodName);
		}
	});

	return sendDialog;
});
