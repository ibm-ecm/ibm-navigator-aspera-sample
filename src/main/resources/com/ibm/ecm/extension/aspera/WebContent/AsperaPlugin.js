/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

require([
	"dojo/_base/lang",
	"dojo/_base/array",
	"ecm/model/Request",
	"aspera/ConfigurationPane",
	"aspera/Package",
	"aspera/SendAction",
	"aspera/SendDialog"
], function(lang, array, Request, ConfigurationPane, Package, SendAction, SendDialog) {
	var c = console;
	var util = lang.setObject("aspera.util", {});
	util._packagesBuffer = [];

	util.list = function() {
		return util._list("list");
	};

	util._list = function(service) {
		return util._requestAction(service, function(response) {
			util._onListRetrieved(response.packages, true);
		});
	};

	util._requestAction = function(service, callback, errorback) {
		return Request.postPluginService("aspera", "package", "application/x-www-form-urlencoded; charset=UTF-8", {
			requestParams: {
				actionType: service,
			},
			requestCompleteCallback: function(response) {
				if (lang.isFunction(callback))
					callback(response);
				else
					c.log(response);
			},
			requestFailedCallback: function(response) {
				if (lang.isFunction(errorback))
					errorback(response);
				else
					c.log(response);
			}
		});
	};

	util._onListRetrieved = function(list, buffered) {
		if (buffered)
			util._packagesBuffer = [];
		array.forEach(list, function(pkg, i) {
			if (buffered)
				util._packagesBuffer.push(new Package(pkg));
			c.log((i + 1) + ". " + pkg.title + " (Sender: " + pkg.sender + ", Size: " + pkg.size + ", Status: " + pkg.status + ", Updated on: " + pkg.updatedOn + ", Last event: " + pkg.lastEvent + ")");
		});
	};

	util.listAll = function() {
		return util._list("listAll");
	};

	util.listRecent = function() {
		return util._list("listRecent");
	};

	util.listAllRecent = function() {
		return util._list("listAllRecent");
	};

	util.get = function(packageId) {
		var pkg = util._getPackage(packageId);

		return pkg.refresh(function() {
			c.log("Id: " + pkg.id);
			c.log("Title: " + pkg.title);
			c.log("Sender: " + pkg.sender);
			c.log("Recipients: " + pkg.recipients);
			c.log("Size: " + pkg.size);
			c.log("Status: " + pkg.status);
			c.log("Updated on: " + pkg.updatedOn);
			c.log("Last event: " + pkg.lastEvent);
			c.log("Last progress: ");
			if (pkg.progress) {
				c.log("  File - " + pkg.progress.file);
				c.log("  Bytes transferred - " + pkg.progress.bytesTransferred);
				c.log("  Seconds elapsed - " + pkg.progress.secondsElapsed);
			}
		});
	};

	util._getPackage = function(packageId) {
		var pkg;
		if (util._packagesBuffer[packageId - 1]) {
			pkg = util._packagesBuffer[packageId - 1];
		} else if (!array.some(util._packagesBuffer, function(p) {
			if (p.id == packageId)
				pkg = p;
			return !!pkg;
		})) {
			pkg = new Package({
				id: packageId
			});
		}

		return pkg;
	};

	util.stop = function(packageId) {
		var pkg = util._getPackage(packageId);

		return pkg.stop(function(response) {
			c.log("Package " + pkg.title + (response.stopped ? " stopped" : " not stopped"));
		});
	};

	util.stopAll = function() {
		return util._requestAction("stopAll", function(response) {
			c.log("Stopped packages:");
			util._onListRetrieved(response.packages);
		});
	};

	util.stopAllFromAll = function() {
		return util._requestAction("stopAllFromAll", function(response) {
			c.log("Stopped packages:");
			util._onListRetrieved(response.packages);
		});
	};
});
