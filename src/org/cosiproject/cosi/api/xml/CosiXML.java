package org.cosiproject.cosi.api.xml;

import java.io.File;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.cosiproject.toolkit.tools.XMLTool;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="cosi")
public class CosiXML {

	@XmlAttribute
	private String lang;
	
	@XmlElement(name="pluginloader")
	private PluginLoaderXML pluginLoaderXML;
	
	
	
	
	
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public PluginLoaderXML getPluginLoaderXML() {
		return pluginLoaderXML;
	}

	public void setPluginLoaderXML(PluginLoaderXML pluginLoaderXML) {
		this.pluginLoaderXML = pluginLoaderXML;
	}

	/*** STATIC METHODS BELOW ***/
	
	
	public static CosiXML load(String string) {
		// TODO Auto-generated method stub
		return load(new File(string));
	}
	
	public static CosiXML load(File file) {
		return (CosiXML) XMLTool.loadFromFile(CosiXML.class, file);
	}
	

}
