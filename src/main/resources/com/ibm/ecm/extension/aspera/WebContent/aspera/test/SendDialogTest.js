define([
	"doh/runner",
	"dojo/_base/json",
	"dojo/aspect",
	"dojo/io-query",
	"ecm/model/Desktop",
	"aspera/SendDialog"
], function(doh, json, aspect, ioQuery, Desktop, SendDialog) {
	Desktop.setServicesUrl(require.toUrl("aspera") + "test/json/");
	doh.register("aspera.tests.SendDialogTest", [
		function shouldSetPaneTitles() {
			// given
			var dialog = new SendDialog();
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				// when
				dialog.sender.set("value", "sender");
				dialog.sender.onChange();
				dialog.notifyOnUpload.set("value", "on");
				dialog.notifyOnUpload.onChange();
				dialog.notifyOnDownload.set("value", "on");
				dialog.notifyOnDownload.onChange();
				deferred.getTestCallback(function() {
					// then
					doh.is("Sender: sender", dialog._userPane.get("title"));
					doh.is("Notification: Notify me when the file is uploaded and downloaded", dialog._notificationPane.get("title"));
					dialog.destroy();
				})();
			});

			return deferred;
		},

		function shouldResetAndOpenUserPaneWhenSessionIsExpired() {
			var dialog = new SendDialog({
				_nonce: "nonce"
			});
			dialog.sender.set("value", "sender");
			dialog.password.set("value", "nonce");
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				// when
				Desktop.onSessionExpired();
				deferred.getTestCallback(function() {
					// then
					doh.is("", dialog.sender.get("value"));
					doh.is("", dialog.password.get("value"));
					doh.t(dialog._userPane.get("open"));
					dialog.destroy();
				})();
			});

			return deferred;
		},

		function shouldResetUserInfoWhenLoggedOut() {
			// given
			var dialog = new SendDialog();
			dialog.sender.set("value", "sender");
			dialog.password.set("value", "password");
			var deferred = new doh.Deferred();
			aspect.after(Desktop, "onLogout", function() {
				deferred.getTestCallback(function() {
					// then
					doh.is("", dialog.sender.get("value"));
					doh.is("", dialog.password.get("value"));
					dialog.destroy();
				})();
			});
			// when
			Desktop.onLogout();

			return deferred;
		},

		function shouldResetPackageInfo() {
			// given
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			dialog.setItems([], {});
			dialog.recipients.set("value", "recipients");
			dialog.title.set("value", "title");
			dialog.note.set("value", "note");
			dialog.earPassphrase.set("value", "earPassphrase");
			// when
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.hide().then(function() {
					deferred.getTestCallback(function() {
						// then
						doh.is("", dialog.recipients.get("value"));
						doh.is("", dialog.title.get("value"));
						doh.is("", dialog.note.get("value"));
						doh.is("", dialog.earPassphrase.get("value"));
						dialog.destroy();
					})();
				});
			});

			return deferred;
		},

		function shouldKeepUserInfo() {
			// given
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			dialog.setItems([], {});
			// when
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "password");
				dialog.hide().then(function() {
					dialog.show().then(function() {
						deferred.getTestCallback(function() {
							// then
							doh.is("sender", dialog.sender.get("value"));
							doh.is("password", dialog.password.get("value"));
							dialog.destroy();
						})();
					});
				});
			});

			return deferred;
		},

		function shouldResetUserInfoWhenUserIsChanged() {
			// given
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			dialog.setItems([], {});
			// when
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "password");
				dialog.earPassphrase.set("value", "earPassphrase");
				dialog.hide().then(function() {
					Desktop.userId = "user2";
					dialog.show().then(function() {
						deferred.getTestCallback(function() {
							// then
							doh.is("", dialog.sender.get("value"));
							doh.is("", dialog.password.get("value"));
							doh.is("", dialog.earPassphrase.get("value"));
							dialog.destroy();
						})();
					});
				});
			});

			return deferred;
		},

		function shouldEnableSendWhenAllRequiredFiledsAreSet() {
			// given
			Desktop.userId = "user1";
			var dialog = new SendDialog();
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				deferred.getTestCallback(function() {
					// when
					dialog.sender.set("value", "sender");
					// then
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					// and when
					dialog.password.set("value", "password");
					// then
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					// and when
					dialog.recipients.set("value", "recipients");
					// then
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					// and when
					dialog.title.set("value", "title");
					// then
					doh.f(dialog.isSendEnabled());
					doh.t(dialog.sendButton.get("disabled"));
					// and when
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
					// then
					doh.t(dialog.isSendEnabled());
					doh.f(dialog.sendButton.get("disabled"));
					dialog.destroy();
				})();
			});

			return deferred;
		},

		function shouldNotEnableSendWhenUserIsChanged() {
			// given
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
					// when
					Desktop.userId = "user2";
					dialog.show().then(function() {
						deferred.getTestCallback(function() {
							// then
							doh.f(dialog.isSendEnabled());
							doh.t(dialog.sendButton.get("disabled"));
							dialog.destroy();
						})();
					});
				});
			});

			return deferred;
		},

		function shouldSend() {
			// given
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
				// when
				dialog.send(function() {
					// then
					deferred.resolve("called");
					dialog.destroy();
				});
			});

			return deferred;
		},

		function shouldSendNonce() {
			// given
			var dialog = new SendDialog({
				_nonce: "nonce"
			});
			var deferred = new doh.Deferred();
			dialog.show().then(function() {
				dialog.sender.set("value", "sender");
				dialog.password.set("value", "nonce");
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
				// when
				var request = dialog.send(function() {
					deferred.getTestCallback(function() {
						// then
						doh.is("nonce", request.requestHeaders["ICN-AP-Nonce"]);
						var pkg = json.fromJson(ioQuery.queryToObject(request.requestBody).json_post);
						doh.is("title", pkg.title);
						doh.is(undefined, pkg.sender);
						doh.is(undefined, pkg.password);
						dialog.destroy();
					})();
				});
			});

			return deferred;
		},

		function shouldCallbackErrorHandlerWhenErrorIsReturned() {
			// given
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
					id: "error"
				});
				// when
				dialog.send(null, function() {
					// then
					deferred.resolve("called");
					dialog.destroy();
				});
			});

			return deferred;
		}
	]);
});
