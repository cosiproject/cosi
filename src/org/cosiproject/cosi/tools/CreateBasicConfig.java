package org.cosiproject.cosi.tools;

import java.io.File;
import java.util.ArrayList;

import org.cosiproject.cosi.core.config.CosiXML;
import org.cosiproject.cosi.core.config.PluginLoaderXML;
import org.cosiproject.cosi.core.pluginloader.DefaultPluginLoader;
import org.cosiproject.toolkit.tools.XMLTool;

public class CreateBasicConfig {

	public static void main(String[] args) {
		CosiXML defaultXML = new CosiXML();
		PluginLoaderXML pluginLoaderXML = new PluginLoaderXML();
		pluginLoaderXML.setClassName(DefaultPluginLoader.class.getCanonicalName());
		ArrayList<File> list = new ArrayList<File>();
		list.add(new File("plugins"));
		pluginLoaderXML.setDirectories(list);
		pluginLoaderXML.setSuffix("cpm");
		defaultXML.setPluginLoaderXML(pluginLoaderXML);
		XMLTool.saveToFile(CosiXML.class, defaultXML, new File("cosi.xml"));
	}
}
