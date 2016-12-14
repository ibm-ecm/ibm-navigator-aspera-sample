define([
	"doh/runner",
	"ecm/model/Item",
	"ecm/model/ContentItem",
	"ecm/model/Repository",
	"aspera/SendAction"
], function(doh, Item, ContentItem, Repository, SendAction) {
	doh.register("aspera.tests.SendActionTest", [
		function isVisible() {
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				mimetype: "test",
				repository: repository
			});
			var action = new SendAction();
			doh.t(action.isVisible(repository, [
				contentItem
			]));
		},

		function isVisibleFolder() {
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				mimetype: "folder",
				repository: repository
			});
			var action = new SendAction();
			doh.f(action.isVisible(repository, [
				contentItem
			]));
		},

		function isEnabled() {
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				repository: repository,
				privileges: ecm.model.Item.PrivilegeToBitmask["privExport"],
				mimetype: "test"
			});
			var action = new SendAction();
			doh.t(action.isEnabled(repository, null, [
				contentItem
			]));
		},

		function isEnabledNoAccess() {
			var repository = new Repository({
				id: "repository"
			});
			var contentItem = new ContentItem({
				id: "contentItem",
				repository: repository,
				privileges: 0,
				mimetype: "test"
			});
			var action = new SendAction();
			doh.f(action.isEnabled(repository, null, [
				contentItem
			]));
		},

		function isEnabledNotAll() {
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
			var action = new SendAction({
				multiDoc: true
			});
			doh.t(action.isEnabled(repository, null, [
				contentItem1,
				contentItem2
			]));
		},

		function isEnabledMultipleRepositories() {
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
			var action = new SendAction({
				multiDoc: true
			});
			doh.t(action.isEnabled(repository, null, [
				contentItem1,
				contentItem2
			]));
		}
	]);
});
