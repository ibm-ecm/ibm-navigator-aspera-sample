/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.extension.PluginAction;

import java.util.Locale;

/**
 * The send menu action available for all document context menus.
 */
public class SendAction extends PluginAction {
	/**
	 * Returns the Id of the action.
	 *
	 * @return "AsperaSendAction"
	 */
	@Override
	public String getId() {
		return "AsperaSendAction";
	}

	/**
	 * Returns an empty string as this action doesn't provide a JavaScript function to use when the plug-in action is invoked.
	 *
	 * @return An empty string
	 */
	@Override
	public String getActionFunction() {
		return "";
	}

	/**
	 * Returns the name of the <code>ecm.model.Action</code> subclass to use when the plug-in action is invoked.
	 *
	 * @return "aspera/SendAction"
	 */
	@Override
	public String getActionModelClass() {
		return "aspera/SendAction";
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String getIcon() {
		return "";
	}

	/**
	 * Returns the localized name of the action.
	 *
	 * @param locale The locale for which a resource bundle is desired
	 * @return The localized name
	 */
	@Override
	public String getName(final Locale locale) {
		return Util.getString("action.send", locale);
	}

	/**
	 * Returns a string that contains the list of privilege names that the user must have on the selected
	 * documents for this action to be enabled.
	 *
	 * @return "privExport" to enable the action when download is enabled
	 */
	@Override
	public String getPrivilege() {
		return "privExport"; // enabled when download is enabled
	}

	/**
	 * Returns the server types that this action is valid on.
	 *
	 * @return "p8, cm"
	 */
	@Override
	public String getServerTypes() {
		return "p8,cm";
	}

	/**
	 * Returns true if the action is enabled when multiple documents are selected.
	 *
	 * @return true
	 */
	@Override
	public boolean isMultiDoc() {
		return true;
	}

	/**
	 * Returns true if the action is global.
	 *
	 * @return false
	 */
	@Override
	public boolean isGlobal() {
		return false;
	}

	/**
	 * Returns the menu types that the action supports.
	 *
	 * @return An array of menu types
	 */
	@Override
	public String[] getMenuTypes() {
		return new String[]{"ItemContextMenu", "WorkItemDocumentContextMenu", "AttachmentItemContextMenu", "AttachmentItemContextMenuCM", "VersionsContextMenu", "VersionsCMContextMenu", "FavoriteItemContextMenu", "TeamspaceItemContextMenu", "SelectObjectItemContextMenu"};
	}
}
