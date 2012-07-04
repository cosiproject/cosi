package org.cosiproject.cosi.api.xml;

import java.io.File;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.NONE)

public class PluginLoaderXML {
	
	@XmlAttribute 
	private String suffix;
	
	@XmlElement(name="class")
	private String className;
	
	@XmlElement(name="directory")
	private List<File> directories;

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<File> getDirectories() {
		return directories;
	}

	public void setDirectories(List<File> directories) {
		this.directories = directories;
	}

	
	
	
}
