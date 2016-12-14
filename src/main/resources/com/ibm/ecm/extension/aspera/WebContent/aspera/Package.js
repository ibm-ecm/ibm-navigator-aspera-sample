/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/_base/array",
	"dojo/_base/json",
	"dojo/io-query",
	"ecm/LoggerMixin",
	"ecm/model/Request",
	"ecm/model/Desktop",
	"ecm/model/Repository",
	"ecm/model/WorkItem"
], function(declare, lang, array, json, ioQuery, LoggerMixin, Request, Desktop, Repository, WorkItem) {

	/**
	 * @name aspera.Package
	 * @class Package data used to send files
	 */
	var asperaPackage = declare("aspera.Package", LoggerMixin, {
		/** @lends aspera.Package.prototype */

		/**
		 * The Id of the package being sent
		 */
		id: null,

		/**
		 * The current repository
		 */
		repository: null,

		/**
		 * The items to be sent
		 */
		items: [],

		/**
		 * The user name of the sender
		 */
		sender: null,

		/**
		 * The password of the sender
		 */
		password: null,

		/**
		 * The recipients of the package
		 */
		recipients: null,

		/**
		 * The package title
		 */
		title: null,

		/**
		 * The package note
		 */
		note: null,

		/**
		 * The encryption passphrase (not supported yet)
		 */
		earPassphrase: null,

		/**
		 * The boolean setting to receive a notification from the Aspera server when the package is uploaded
		 */
		notifyOnUpload: false,

		/**
		 * The boolean setting to receive a notification from the Aspera server when the package is downloaded
		 */
		notifyOnDownload: false,

		constructor: function(args) {
			lang.mixin(this, args);
			this.setItems(this.items, this.repository);
			if (!this.items)
				this.items = [];
		},

		/**
		 * Sets the items and the current repository. The repository of each item takes precedence over the current repository.
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
		 * Verifies required parameters are set.
		 */
		isValid: function() {
			return !!this.recipients && !!this.title && this.items instanceof Array && this.items.length > 0;
		},

		/**
		 * Sends the package. Calls the onPackageStarted() function when succeeded.
		 *
		 * @param callback The function to call back when the transfer is started
		 * @param errorback The function to call back when an error is returned
		 */
		send: function(callback, errorback, requestHeaders) {
			var methodName = "send";
			this.logEntry(methodName);
			if (!this.isValid()) {
				this.logDebug(methodName, "invalid parameters");
				if (lang.isFunction(errorback))
					errorback();

				return null;
			}

			var request = this._postPluginService("send", null, this._prepareJsonPost(), callback, errorback, lang.hitch(this, function(response) {
				lang.mixin(this, response.pkg);
				this.onPackageStarted(response);
			}), requestHeaders);
			this.logExit(methodName);

			return request;
		},

		_postPluginService: function(service, serviceParams, jsonPost, callback, errorback, onServiced, requestHeaders) {
			var requestParams = {};
			if (this.repository) 
				requestParams.repositoryId = this.repository.id;
			var requestBody = {};
			if (serviceParams)
				lang.mixin(requestBody, serviceParams);
			if (jsonPost)
				requestBody.json_post = json.toJson(jsonPost);
			this.logDebug("_postPluginService", "request service: " + service + ", " + json.toJson(serviceParams));

			return Request.postPluginService("aspera", service, "application/x-www-form-urlencoded; charset=UTF-8", {
				requestParams: requestParams,
				requestBody: ioQuery.objectToQuery(requestBody),
				requestCompleteCallback: function(response) {
					if (lang.isFunction(onServiced))
						onServiced(response);
					if (lang.isFunction(callback))
						callback(response);
				},
				requestFailedCallback: function(response) {
					if (lang.isFunction(errorback))
						errorback(response);
				},
				requestHeaders: requestHeaders ? requestHeaders : undefined
			});
		},

		_prepareJsonPost: function() {
			var jsonPost = {
				recipients: this.recipients,
				title: this.title,
				note: this.note,
				earPassphrase: this.earPassphrase,
				notifyOnUpload: this.notifyOnUpload === true,
				notifyOnDownload: this.notifyOnDownload === true
			};
			if (this.sender)
				jsonPost.sender = this.sender;
			if (this.password)
				jsonPost.password = this.password;

			jsonPost.items = [];
			array.forEach(this.items, lang.hitch(this, function(item) {
				var repo = item.repository ? item.repository : this.repository;
				var itemId = repo.type == "cm" && item.docid && item.isInstanceOf && item.isInstanceOf(WorkItem) ? item.docid : item.id;
				jsonPost.items.push({
					itemId: itemId,
					repositoryId: repo.id
				});
			}));

			return jsonPost;
		},

		/**
		 * An event function called when the transfer is started. The response includes the Id of the package being sent.
		 *
		 * @param response The JSON response from the server
		 */
		onPackageStarted: function(response) {
			this.logDebug("onPackageStarted", "package: " + json.toJson(response.pkg));
		},

		/**
		 * Requests to stop the package being sent.
		 * Calls the onPackageStopRequested() function when succeeded.
		 *
		 * @param callback The function to call back when the request is made
		 * @param errorback The function to call back when an error is returned
		 */
		stop: function(callback, errorback) {
			var methodName = "stop";
			this.logEntry(methodName);
			if (!this.id) {
				this.logDebug(methodName, "cannot request to stop the package without id");
				if (lang.isFunction(errorback))
					errorback();

				return null;
			}

			var request = this._postPluginService("package", {
				actionType: "stop",
				packageId: this.id
			}, null, callback, errorback, lang.hitch(this, this.onPackageStopRequested));
			this.logExit(methodName);

			return request;
		},

		/**
		 * An event function called when the transfer is requested to stop.
		 *
		 * @param response The JSON response from the server
		 */
		onPackageStopRequested: function(response) {
			this.logDebug("onPackageStopRequested", "stopped: " + response.stopped);
		},

		/**
		 * Requests the fresh data.
		 * Calls the onPackageRefreshed() function when succeeded.
		 *
		 * @param callback The function to call back when the data is received
		 * @param errorback The function to call back when an error is returned
		 */
		refresh: function(callback, errorback) {
			var methodName = "refresh";
			this.logEntry(methodName);
			if (!this.id) {
				this.logDebug(methodName, "cannot request the data of the package without id");
				if (lang.isFunction(errorback))
					errorback();

				return null;
			}

			var request = this._postPluginService("package", {
				actionType: "get",
				packageId: this.id
			}, null, callback, errorback, lang.hitch(this, function(response) {
				if (response.pkg && response.pkg.id) {
					lang.mixin(this, response.pkg);
					this.onPackageRefreshed(response);
				} else if (lang.isFunction(errorback)) {
					this.logDebug(methodName, "failed to refresh the package");
					errorback(response);
				}
			}));
			this.logExit(methodName);

			return request;
		},

		/**
		 * An event function called when the data is refreshed.
		 *
		 * @param response The JSON response from the server
		 */
		onPackageRefreshed: function(response) {
			this.logDebug("onPackageRefreshed", "package: " + json.toJson(response.pkg));
		}
	});

	return asperaPackage;
});
