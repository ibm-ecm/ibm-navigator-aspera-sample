/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.extension.PluginAction;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The send menu action available for all document context menus.
 */
public class SendAction extends PluginAction {
	@Override
	public String getId() {
		return "AsperaSendAction";
	}

	@Override
	public String getActionFunction() {
		return "";
	}

	@Override
	public String getActionModelClass() {
		return "aspera/SendAction";
	}

	@Override
	@Deprecated
	public String getIcon() {
		return "";
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("com.ibm.ecm.extension.aspera.nls.ServicesMessages", locale, this.getClass().getClassLoader());

		return bundle.getString("action.send");
	}

	@Override
	public String getPrivilege() {
		return "privExport"; // enabled when download is enabled
	}

	@Override
	public String getServerTypes() {
		return "p8,cm";
	}

	@Override
	public boolean isMultiDoc() {
		return true;
	}

	@Override
	public boolean isGlobal() {
		return false;
	}

	@Override
	public String[] getMenuTypes() {
		return new String[]{"ItemContextMenu", "WorkItemDocumentContextMenu", "AttachmentItemContextMenu", "AttachmentItemContextMenuCM", "VersionsContextMenu", "VersionsCMContextMenu", "FavoriteItemContextMenu", "TeamspaceItemContextMenu", "SelectObjectItemContextMenu"};
	}
}
