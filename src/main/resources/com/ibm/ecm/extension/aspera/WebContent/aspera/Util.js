/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define([
	"dojo/_base/lang",
	"dojo/string",
	"ecm/model/Desktop",
	"ecm/model/Message",
	"ecm/model/Request",
	"aspera/Package",
	"dojo/i18n!aspera/nls/messages"
], function(lang, string, Desktop, Message, Request, Package, messages) {
	/**
	 * @name aspera.Util
	 * @class Utilities
	 */
	var Util = {
		/**
		 * Refreshes the package and adds the status message to the desktop status bar.
		 *
		 * @param pkg The package
		 * @param callback The function to call back when succeeded
		 * @param errorback The function to call back when an error is returned
		 */
		reportPackageStatus: function(pkg, callback, errorback) {
			pkg.refresh(function(response) {
				Util.addStatusMessage(pkg);
				if (lang.isFunction(callback))
					callback(pkg, response);
			}, errorback);
		},

		/**
		 * Adds the package status to the desktop status bar.
		 *
		 * @param pkg The package
		 */
		addStatusMessage: function(pkg) {
			var msg;
			switch (pkg.status) {
			case "STARTED":
				msg = messages.status_started;
				break;
			case "STOPPED":
				msg = messages.status_stopped;
				break;
			case "FAILED":
				msg = messages.status_failed;
				break;
			case "COMPLETED":
				msg = messages.status_completed;
				break;
			}	
			if (!msg)
				return;

			msg = pkg.status == "STARTED" ? string.substitute(msg, [
				pkg.title,
				Util._getProgressPercentage(pkg)
			]) : string.substitute(msg, [
				pkg.title
			]);
			Desktop.addMessage(new Message({
				text: msg
			}));
		},

		_getProgressPercentage: function(pkg) {
			var total = pkg.size;
			var sent = pkg.progress.bytesTransferred;
			var perc = Math.round(sent / total * 100);

			return isNaN(perc) ? 0 : perc;
		},

		/**
		 * Watches the package status by making polling requests.
		 *
		 * @param pkg The package
		 */
		watchPackageStatus: function(pkg) {
			Util.reportPackageStatus(pkg, function(pkg) {
				if (pkg.status == "STARTED") {
					var interval = pkg.size / 200000000;
					interval = interval < 3 ? 3 : interval > 15 ? 15 : interval;
					var watchTimer = setInterval(function() {
						if (Desktop.connected) {
							Util.reportPackageStatus(pkg, function(pkg) {
								if (pkg.status != "STARTED")
									clearTimeout(watchTimer);
							}, function() {
								clearTimeout(watchTimer);
							});
						} else {
							clearTimeout(watchTimer);
						}
					}, interval * 1000);
				}
			});
		}
	};

	return Util;
});
