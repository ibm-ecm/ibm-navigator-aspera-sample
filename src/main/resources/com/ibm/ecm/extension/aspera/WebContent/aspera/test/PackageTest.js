define([
	"doh/runner",
	"dojo/aspect",
	"ecm/model/Desktop",
	"ecm/model/WorkItem",
	"aspera/Package"
], function(doh, aspect, Desktop, WorkItem, Package) {
	Desktop.setServicesUrl(require.toUrl("aspera") + "/test/json/");
	var getPackage = function() {
		return new Package({
			sender: "sender",
			password: "password",
			recipients: "recipients",
			title: "title",
			note: "note",
			earPassword: "earPassword"
		});
	};

	doh.register("aspera.tests.PackageTest", [
		function isValid() {
			var asperaPack = new Package();
			asperaPack.sender = "sender";
			doh.f(asperaPack.isValid());
			asperaPack.password = "password";
			doh.f(asperaPack.isValid());
			asperaPack.recipients = "recipients";
			doh.f(asperaPack.isValid());
			asperaPack.title = "title";
			doh.f(asperaPack.isValid());
			asperaPack.setItems([
				{
					id: "itemId1",
					repository: {
						id: "repositoryId1"
					}
				}
			], {
				id: "repositoryId"
			});
			doh.t(asperaPack.isValid());
		},

		function prepareJsonPost() {
			var asperaPack = getPackage();
			asperaPack.setItems([
				{
					id: "itemId"
				}
			], {
				id: "repositoryId"
			});
			var json = asperaPack._prepareJsonPost();
			doh.is("recipients", json.recipients);
			doh.is("title", json.title);
			doh.is("note", json.note);
			doh.is("earPassword", json.earPassword);
			doh.f(json.notifyOnUpload);
			doh.f(json.notifyOnDownload);
			doh.is(1, json.items.length);
			doh.is("itemId", json.items[0].itemId);
			doh.is("repositoryId", json.items[0].repositoryId);
			asperaPack.notifyOnUpload = false;
			asperaPack.notifyOnDownload = true;
			asperaPack.setItems([
				{
					id: "itemId1",
					repository: {
						id: "repositoryId1"
					}
				},
				{
					id: "itemId2",
					repository: {
						id: "repositoryId2"
					}
				}
			], {
				id: "repositoryId"
			});
			json = asperaPack._prepareJsonPost();
			doh.f(json.notifyOnUpload);
			doh.t(json.notifyOnDownload);
			doh.is(2, json.items.length);
			doh.is("itemId1", json.items[0].itemId);
			doh.is("repositoryId1", json.items[0].repositoryId);
			doh.is("itemId2", json.items[1].itemId);
			doh.is("repositoryId2", json.items[1].repositoryId);
		},

		function prepareJsonPostForWorkItem() {
			var asperaPack = getPackage();
			asperaPack.setItems([
				new WorkItem({
					id: "itemId",
					docid: "docId"
				})
			], {
				id: "repositoryId",
				type: "cm"
			});
			var json = asperaPack._prepareJsonPost();
			doh.is("recipients", json.recipients);
			doh.is("title", json.title);
			doh.is("note", json.note);
			doh.is("earPassword", json.earPassword);
			doh.f(json.notifyOnUpload);
			doh.f(json.notifyOnDownload);
			doh.is(1, json.items.length);
			doh.is("docId", json.items[0].itemId);
			doh.is("repositoryId", json.items[0].repositoryId);
			asperaPack.setItems([
				new WorkItem({
					id: "itemId",
					docid: "docId"
				})
			], {
				id: "repositoryId",
				type: "p8"
			});
			json = asperaPack._prepareJsonPost();
			doh.is("itemId", json.items[0].itemId);
		},

		function send() {
			var asperaPack = getPackage();
			asperaPack.setItems([
				{
					id: "itemId1",
					repository: {
						id: "repositoryId1"
					}
				}
			], {
				id: "repositoryId"
			});

			var deferred = new doh.Deferred();
			asperaPack.send(function() {
				doh.is("16749f52-9f42-4d95-8998-2f9c5579fcda", asperaPack.id);
				deferred.resolve("called");
			});

			return deferred;
		},
	
		function sendOnPackageStarted() {
			var asperaPack = getPackage();
			asperaPack.setItems([
				{
					id: "itemId1",
					repository: {
						id: "repositoryId1"
					}
				}
			], {
				id: "repositoryId"
			});

			var deferred = new doh.Deferred();
			aspect.after(asperaPack, "onPackageStarted", function() {
				deferred.resolve("called");
			});
			asperaPack.send();

			return deferred;
		},

		function sendInvalid() {
			var asperaPack = new Package();
			var deferred = new doh.Deferred();
			asperaPack.send(null, function() {
				deferred.resolve("called");
			});

			return deferred;
		},

		function sendError() {
			var asperaPack = new Package();
			asperaPack.setItems([
				{
					id: "itemId1",
					repository: {
						id: "repositoryId1"
					}
				}
			], {
				id: "invalid"
			});

			var deferred = new doh.Deferred();
			asperaPack.send(null, function() {
				deferred.resolve("called");
			});

			return deferred;
		},

		function stop() {
			var asperaPack = new Package({
				repository: {
					id: "stop"
				},
				id: "id"
			});

			var deferred = new doh.Deferred();
			asperaPack.stop(function(response) {
				doh.t(response.stopped);
				deferred.resolve("called");
			});

			return deferred;
		},
	
		function sendOnPackageStopRequested() {
			var asperaPack = new Package({
				repository: {
					id: "stop"
				},
				id: "id"
			});
			var deferred = new doh.Deferred();
			aspect.after(asperaPack, "onPackageStopRequested", function(response) {
				doh.t(response.stopped);
				deferred.resolve("called");
			}, true);
			asperaPack.stop();

			return deferred;
		},

		function stopError() {
			var asperaPack = new Package({
				repository: {
					id: "stopError"
				},
				id: "id"
			});
			var deferred = new doh.Deferred();
			asperaPack.stop(null, function() {
				deferred.resolve("called");
			});

			return deferred;
		}
	]);
});
