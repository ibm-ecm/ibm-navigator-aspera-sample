define([
	"doh/runner",
	"dojo/aspect",
	"ecm/model/Desktop",
	"aspera/SendDialog"
], function(doh, aspect, Desktop, SendDialog) {
	Desktop.setServicesUrl(require.toUrl("aspera") + "test/json/");
	doh.register("aspera.tests.SendDialogTest", [
		function packageInfoIsReset() {
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			dialog.setItems([], {});
			dialog.recipients.set("value", "recipients");
			dialog.title.set("value", "title");
			dialog.note.set("value", "note");
			dialog.earPassword.set("value", "earPassword");
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.hide().then(function() {
					deferred.getTestCallback(function() {
						doh.is("", dialog.recipients.get("value"));
						doh.is("", dialog.title.get("value"));
						doh.is("", dialog.note.get("value"));
						doh.is("", dialog.earPassword.get("value"));
						dialog.destroy();
					})();
				});
			});

			return deferred;
		},

		function userInfoIsKept() {
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			dialog.setItems([], {});
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "password");
				dialog.hide().then(function() {
					dialog.show().then(function() {
						deferred.getTestCallback(function() {
							doh.is("sender", dialog.sender.get("value"));
							doh.is("password", dialog.password.get("value"));
							dialog.destroy();
						})();
					});
				});
			});

			return deferred;
		},

		function userInfoIsReset() {
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			dialog.setItems([], {});
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "password");
				dialog.earPassword.set("value", "earPassword");
				dialog.hide().then(function() {
					Desktop.userId = "user2";
					dialog.show().then(function() {
						deferred.getTestCallback(function() {
							doh.is("", dialog.sender.get("value"));
							doh.is("", dialog.password.get("value"));
							doh.is("", dialog.earPassword.get("value"));
							dialog.destroy();
						})();
					});
				});
			});

			return deferred;
		},

		function isSendEnabled() {
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				deferred.getTestCallback(function() {
					dialog.sender.set("value", "sender");
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					dialog.password.set("value", "password");
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					dialog.recipients.set("value", "recipients");
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					dialog.title.set("value", "title");
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					dialog.setItems([
						{
							id: "itemId1",
							repository: {
								id: "repositoryId1"
							}
						}
					], {
						id: "repositoryId"
					});
					doh.t(dialog.isSendEnabled());
					doh.f(dialog.sendButton.get("disabled"));
					dialog.destroy();
				})();
			});

			return deferred;
		},

		function isSendNotEnabled() {
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "password");
				dialog.recipients.set("value", "recipients");
				dialog.title.set("value", "title");
				dialog.setItems([
					{
						id: "itemId1",
						repository: {
							id: "repositoryId1"
						}
					}
				], {
					id: "repositoryId"
				});
				dialog.hide().then(function() {
					Desktop.userId = "user2";
					dialog.show().then(function() {
						deferred.getTestCallback(function() {
							doh.f(dialog.isSendEnabled());
							doh.t(dialog.sendButton.get("disabled"));
							dialog.destroy();
						})();
					});
				});
			});

			return deferred;
		},

		function send() {
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "password");
				dialog.recipients.set("value", "recipients");
				dialog.title.set("value", "title");
				dialog.setItems([
					{
						id: "itemId1",
						repository: {
							id: "repositoryId1"
						}
					}
				], {
					id: "repositoryId"
				});
				dialog.send(function() {
					deferred.resolve("called");
					dialog.destroy();
				});
			});

			return deferred;
		},

		function sendError() {
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "password");
				dialog.recipients.set("value", "recipients");
				dialog.title.set("value", "title");
				dialog.setItems([
					{
						id: "itemId1",
						repository: {
							id: "repositoryId1"
						}
					}
				], {
					id: "invalid"
				});
				dialog.send(null, function() {
					deferred.resolve("called");
					dialog.destroy();
				});
			});

			return deferred;
		}
	]);
});
