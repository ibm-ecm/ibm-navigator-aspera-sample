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
			earPassphrase: "earPassphrase"
		});
	};

	doh.register("aspera.tests.PackageTest", [
		function shouldValidatePackage() {
			// given
			var asperaPack = new Package();
			// when
			asperaPack.sender = "sender";
			// then
			doh.f(asperaPack.isValid());
			// or when
			asperaPack.password = "password";
			// then
			doh.f(asperaPack.isValid());
			// or when
			asperaPack.recipients = "recipients";
			// then
			doh.f(asperaPack.isValid());
			// or when
			asperaPack.title = "title";
			// then
			doh.f(asperaPack.isValid());
			// or when
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
			// then
			doh.t(asperaPack.isValid());
		},

		function shouldPrepareJsonPost() {
			// given
			var asperaPack = getPackage();
			asperaPack.setItems([
				{
					id: "itemId"
				}
			], {
				id: "repositoryId"
			});
			// when
			var json = asperaPack._prepareJsonPost();
			// then
			doh.is("recipients", json.recipients);
			doh.is("title", json.title);
			doh.is("note", json.note);
			doh.is("earPassphrase", json.earPassphrase);
			doh.f(json.notifyOnUpload);
			doh.f(json.notifyOnDownload);
			doh.is(1, json.items.length);
			doh.is("itemId", json.items[0].itemId);
			doh.is("repositoryId", json.items[0].repositoryId);
			// and given
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
			// when
			json = asperaPack._prepareJsonPost();
			// then
			doh.f(json.notifyOnUpload);
			doh.t(json.notifyOnDownload);
			doh.is(2, json.items.length);
			doh.is("itemId1", json.items[0].itemId);
			doh.is("repositoryId1", json.items[0].repositoryId);
			doh.is("itemId2", json.items[1].itemId);
			doh.is("repositoryId2", json.items[1].repositoryId);
		},

		function shouldPrepareJsonPostForWorkItem() {
			// given
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
			// when
			var json = asperaPack._prepareJsonPost();
			// then
			doh.is("recipients", json.recipients);
			doh.is("title", json.title);
			doh.is("note", json.note);
			doh.is("earPassphrase", json.earPassphrase);
			doh.f(json.notifyOnUpload);
			doh.f(json.notifyOnDownload);
			doh.is(1, json.items.length);
			doh.is("docId", json.items[0].itemId);
			doh.is("repositoryId", json.items[0].repositoryId);
			// and given
			asperaPack.setItems([
				new WorkItem({
					id: "itemId",
					docid: "docId"
				})
			], {
				id: "repositoryId",
				type: "p8"
			});
			// when
			json = asperaPack._prepareJsonPost();
			// then
			doh.is("itemId", json.items[0].itemId);
		},

		function shouldSend() {
			// given
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
			// when
			var deferred = new doh.Deferred();
			asperaPack.send(function() {
				// then
				doh.is("16749f52-9f42-4d95-8998-2f9c5579fcda", asperaPack.id);
				deferred.resolve("called");
			});

			return deferred;
		},
	
		function shouldReceiveOnPackageStarted() {
			// given
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
				// then
				deferred.resolve("called");
			});
			// when
			asperaPack.send();

			return deferred;
		},

		function shouldInvalidateSend() {
			// given
			var asperaPack = new Package();
			// when
			var deferred = new doh.Deferred();
			asperaPack.send(null, function() {
				// then
				deferred.resolve("called");
			});

			return deferred;
		},

		function shouldFailSend() {
			// given
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
			// when
			var deferred = new doh.Deferred();
			asperaPack.send(null, function() {
				// then
				deferred.resolve("called");
			});

			return deferred;
		},

		function shouldStop() {
			// given
			var asperaPack = new Package({
				repository: {
					id: "stop"
				},
				id: "id"
			});
			// when
			var deferred = new doh.Deferred();
			asperaPack.stop(function(response) {
				// then
				doh.t(response.stopped);
				deferred.resolve("called");
			});

			return deferred;
		},
	
		function shouldReceiveOnPackageStopRequested() {
			// given
			var asperaPack = new Package({
				repository: {
					id: "stop"
				},
				id: "id"
			});
			var deferred = new doh.Deferred();
			aspect.after(asperaPack, "onPackageStopRequested", function(response) {
				// then
				doh.t(response.stopped);
				deferred.resolve("called");
			}, true);
			// when
			asperaPack.stop();

			return deferred;
		},

		function shouldFailStop() {
			// given
			var asperaPack = new Package({
				repository: {
					id: "stopError"
				},
				id: "id"
			});
			// when
			var deferred = new doh.Deferred();
			asperaPack.stop(null, function() {
				// then
				deferred.resolve("called");
			});

			return deferred;
		},

		function shouldRefresh() {
			// given
			var asperaPack = new Package({
				repository: {
					id: "refresh"
				},
				id: "id"
			});
			// when
			var deferred = new doh.Deferred();
			asperaPack.refresh(function() {
				// then
				doh.is("a7cfc622-2745-4f6c-af3a-70223bb3cbde", asperaPack.id);
				deferred.resolve("called");
			});

			return deferred;
		},
	
		function shouldReceiveOnPackageRefreshed() {
			// given
			var asperaPack = new Package({
				repository: {
					id: "refresh"
				},
				id: "id"
			});
			var deferred = new doh.Deferred();
			aspect.after(asperaPack, "onPackageRefreshed", function() {
				// then
				deferred.resolve("called");
			}, true);
			// when
			asperaPack.refresh();

			return deferred;
		},

		function shouldFailRefresh() {
			// given
			var asperaPack = new Package({
				repository: {
					id: "refreshError"
				},
				id: "id"
			});
			// when
			var deferred = new doh.Deferred();
			asperaPack.refresh(null, function() {
				// then
				deferred.resolve("called");
			});

			return deferred;
		}
	]);
});
