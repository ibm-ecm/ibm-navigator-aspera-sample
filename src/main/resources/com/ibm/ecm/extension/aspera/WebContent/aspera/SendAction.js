/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/_base/array",
	"ecm/model/Action",
	"ecm/model/Desktop",
	"ecm/model/Message",
	"ecm/model/admin/PluginConfig",
	"aspera/SendDialog"
], function(declare, lang, array, Action, Desktop, Message, PluginConfig, SendDialog) {

	/**
	 * @name aspera.SendAction
	 * @class A menu action to open the send dialog with items to be sent
	 * @augments ecm.model.Action
	 */
	return declare("aspera.SendAction", [
		Action
	], {
		/** @lends aspera.SendAction.prototype */
		_sendDialog: new SendDialog(),
		_pluginConfiguration: null,
		_defaultMaxDocs: 50,

		constructor: function(args) {
			lang.mixin(this, args);
			if (!this.id)
				this.id = "asperaSend";
		},

		/**
		 * Checks if the menu action should be visible.
		 *
		 * @param repository The current repository
		 * @param items The items to be checked
		 * @return true if some of the items have content
		 */
		isVisible: function(repository, items) {
			return this.inherited(arguments) && array.some(items, this._isVisibleForItem);
		},

		_isVisibleForItem: function(item) {
			return !!item && (lang.isFunction(item.hasContent) ? item.hasContent() : true);
		},

		/**
		 * Checks if the menu action should be enabled.
		 *
		 * @param repository The current repository
		 * @param items The items to be checked
		 * @return true if the current user has access to download some of the items
		 */
		isEnabled: function(repository, listType, items) {
			var realItems = array.map(items, function(item) {
				return item.item ? item.item : item;
			});
			if (!realItems || realItems.length < 1 || (realItems.length > 1 && !this.multiDoc))
				return false;

			return array.some(realItems, this._isEnabledForItem);
		},

		_isEnabledForItem: function(item) {
			return !!item && item.hasPrivilege("privExport");
		},

		/**
		 * Shows the send dialog or an error message if the user selected more than the maximum number of items that can be sent at a time.
		 *
		 * @param repository The current repository
		 * @param items The items to be sent
		 */
		performAction: function(repository, items) {
			this.logEntry("performAction", "items: " + items + ", repository: " + repository);
			this._getPluginConfiguration(lang.hitch(this, function(config) {
				var max = this._getMaxNumberOfItems(config);
				if (items.length > max) {
					Desktop.addMessage(Message.createErrorMessage("send_dialog_too_many_items_error", [
						max,
						items.length
					], false, this.messages));
				} else {
					this._showSendDialog(items, repository);
				}
			}));
			this.logExit("performAction");
		},

		_getPluginConfiguration: function(callback) {
			if (this._pluginConfiguration) {
				callback(this._pluginConfiguration);
			} else {
				var plugin = PluginConfig.createPluginConfig("aspera");
				plugin.getConfig(lang.hitch(this, function() {
					this._pluginConfiguration = JSON.parse(plugin.getConfiguration());
					callback(this._pluginConfiguration);
				}));
			}
		},

		_getMaxNumberOfItems: function(pluginConfig) {
			var max = this._defaultMaxDocs;
			if (pluginConfig.maxNumberOfItems)
				max = pluginConfig.maxNumberOfItems;
			return max;
		},

		_showSendDialog: function(items, repository) {
			this._sendDialog.setItems(items, repository);
			this._sendDialog.show();
		}
	});
});
