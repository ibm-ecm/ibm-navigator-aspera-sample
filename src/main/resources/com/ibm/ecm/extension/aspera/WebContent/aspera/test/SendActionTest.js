define([
	"doh/runner",
	"dojo/aspect",
	"ecm/model/Desktop",
	"ecm/model/Item",
	"ecm/model/ContentItem",
	"ecm/model/Repository",
	"aspera/SendAction"
], function(doh, aspect, Desktop, Item, ContentItem, Repository, SendAction) {
	doh.register("aspera.tests.SendActionTest", [
		function shouldBeVisible() {
			// given
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				mimetype: "test",
				repository: repository
			});
			// when, then
			var action = new SendAction();
			doh.t(action.isVisible(repository, [
				contentItem
			]));
		},

		function shouldFolderBeVisible() {
			// given
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				mimetype: "folder",
				repository: repository
			});
			// when, then
			var action = new SendAction();
			doh.f(action.isVisible(repository, [
				contentItem
			]));
		},

		function shouldBeEnabled() {
			// given
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				repository: repository,
				privileges: ecm.model.Item.PrivilegeToBitmask["privExport"],
				mimetype: "test"
			});
			// when, then
			var action = new SendAction();
			doh.t(action.isEnabled(repository, null, [
				contentItem
			]));
		},

		function shouldNotBeEnabledGivenNoAccess() {
			// given
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				repository: repository,
				privileges: 0,
				mimetype: "test"
			});
			// when, then
			var action = new SendAction();
			doh.f(action.isEnabled(repository, null, [
				contentItem
			]));
		},

		function shouldBeEnabledGivenSomeEnabled() {
			// given
			var repository = new Repository({
				id: "repository"
			});
			var contentItem1 = new ContentItem({
				id: "contentItem1",
				repository: repository,
				privileges: ecm.model.Item.PrivilegeToBitmask["privExport"],
				mimetype: "test"
			});
			var contentItem2 = new ContentItem({
				id: "contentItem2",
				repository: repository,
				privileges: 0,
				mimetype: "test"
			});
			// when, then
			var action = new SendAction({
				multiDoc: true
			});
			doh.t(action.isEnabled(repository, null, [
				contentItem1,
				contentItem2
			]));
		},

		function shouldBeEnabledGivenMultipleRepositories() {
			// given
			var repository = new Repository({
				id: "repository"
			});
			var contentItem1 = new ContentItem({
				id: "contentItem1",
				repository: repository,
				privileges: ecm.model.Item.PrivilegeToBitmask["privExport"],
				mimetype: "test"
			});
			var contentItem2 = new ContentItem({
				id: "contentItem2",
				repository: new Repository({
					id: "repository2"
				}),
				privileges: ecm.model.Item.PrivilegeToBitmask["privExport"],
				mimetype: "test"
			});
			// when, then
			var action = new SendAction({
				multiDoc: true
			});
			doh.t(action.isEnabled(repository, null, [
				contentItem1,
				contentItem2
			]));
		},

		function shouldAddTooManyItemsErrorMessage() {
			// given
			var action = new SendAction({
				maxNumberOfItems: 1
			});
			var deferred = new doh.Deferred();
			var handle = aspect.after(Desktop, "onMessageAdded", function() {
				// then
				deferred.resolve("called");
				handle.remove();
			});
			// when
			action.performAction(new Repository(), [
				new ContentItem(),
				new ContentItem()
			]);

			return deferred;
		}
	]);
});
