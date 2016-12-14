/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/dom-class",
	"dijit/_TemplatedMixin",
	"dijit/_WidgetsInTemplateMixin",
	"ecm/widget/NumberSpinner",
	"ecm/widget/ValidationTextBox",
	"ecm/widget/admin/PluginConfigurationPane",
	"dojo/text!./templates/ConfigurationPane.html",
	"dojo/i18n!./nls/messages"
], function(declare, lang, domClass, _TemplatedMixin, _WidgetsInTemplateMixin, NumberSpinner, ValidationTextBox, PluginConfigurationPane, template, messages) {

	/**
	 * @name aspera.ConfigurationPane
	 * @class Aspera plug-in configuration pane
	 * @augments ecm.widget.admin.PluginConfigurationPane
	 */
	return declare("aspera.ConfigurationPane", [
		PluginConfigurationPane,
		_TemplatedMixin,
		_WidgetsInTemplateMixin
	], {
		/** @lends aspera.ConfigurationPane.prototype */

		templateString: template,
		widgetsInTemplate: true,

		postMixInProperties: function() {
			this.inherited(arguments);
			lang.mixin(this.messages, messages);
		},

		load: function(callback) {
			if (this.configurationString) {
				var jsonConfig = JSON.parse(this.configurationString);
				this.asperaServerUrl.set("value", jsonConfig.asperaServerUrl);
				this.maxNumberOfItems.set("value", jsonConfig.maxNumberOfItems);
				this.maxNumberOfRequests.set("value", jsonConfig.maxNumberOfRequests);
				this.targetTransferRateMbps.set("value", jsonConfig.targetTransferRateMbps);
			}
		},

		_onParamChange: function() {
			var configJson = {
				asperaServerUrl: this.asperaServerUrl.get("value"),
				maxNumberOfItems: this.maxNumberOfItems.get("value"),
				maxNumberOfRequests: this.maxNumberOfRequests.get("value"),
				targetTransferRateMbps: this.targetTransferRateMbps.get("value")
			};
			this.configurationString = JSON.stringify(configJson);
			this.onSaveNeeded(true);
		},

		validate: function() {
			if (!this.asperaServerUrl.isValid()) {
				domClass.remove(this.asperaServerUrl.domNode, "asperaServerUrlWarning");
				return false;
			}
			if (!this.maxNumberOfItems.isValid())
				return false;
			if (!this.maxNumberOfRequests.isValid())
				return false;
			if (!this.targetTransferRateMbps.isValid())
				return false;

			if (this.asperaServerUrl.get("value").toLowerCase().indexOf("https://") === 0) {
				domClass.remove(this.asperaServerUrl.domNode, "asperaServerUrlWarning");
			} else {
				domClass.add(this.asperaServerUrl.domNode, "asperaServerUrlWarning");
				this.asperaServerUrl.set("message", this.messages.configuration_pane_aspera_url_prompt);
			}

			return true;
		}
	});
});
