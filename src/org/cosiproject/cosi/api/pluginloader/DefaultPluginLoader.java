/* DefaultPluginLoader.java is part of Cosi
 * created 18.11.2011 
 *
 *
 * Copyright (c) 2011, The Cosi Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the <organization> nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  
 * 
 * @author lotherk
 *
 * 
 */
package org.cosiproject.cosi.api.pluginloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.CosiClassLoader;
import org.cosiproject.cosi.api.PluginAPI;
import org.cosiproject.cosi.api.PluginContainer;
import org.cosiproject.cosi.api.PluginLoader;
import org.cosiproject.cosi.api.xml.CPMClasspath;
import org.cosiproject.cosi.api.xml.CosiPluginModule;

public class DefaultPluginLoader extends PluginLoader {

	private static Logger logger = Logger.getLogger(DefaultPluginLoader.class);

	/**
	 * Nothing yet to do here.
	 */
	public DefaultPluginLoader() {
		logger.debug("PluginLoader initiated.");
	}

	/**
	 * Loads all the Plugins.
	 * 
	 * @throws COSIFatalErrorException
	 */
	@Override
	public void load() {

		for (File dir : getPluginDirectoryList()) {
			logger.debug("Working on " + dir);

			if (!dir.isDirectory()) {
				logger.warn("Skipping plugin directory "
						+ dir.getAbsolutePath()
						+ " (does not exist or is not a directory.)");
				continue;
			}
			String[] list = dir.list();
			logger.debug("Listing " + dir);
			File tmp;
			for (String file : list) {
				// checking if file ends with .cpm and is a directory.
				tmp = new File(dir.getAbsolutePath()
						+ System.getProperty("file.separator") + file);
				if (file.endsWith("." + getSuffix()) && tmp.isDirectory()) {
					try {

						this.readPlugin(tmp.getAbsolutePath());
					}

					catch (RuntimeException e) {
						// if(e.getCause() instanceof
						// COSIPluginIncompatibleException) {
						// logger.error("Plugin " + tmp.getAbsolutePath() +
						// " is not compatible with this PluginLoader, skipping.");
						// logger.debug(e.getCause().getMessage());
						// }
						// if(e.getCause() instanceof
						// COSIPluginAlreadyExistsException) {
						// logger.error("Plugin " + tmp.getAbsolutePath() +
						// " does already exist.");
						// logger.debug(e.getCause().getMessage());
						// }
					} catch (Exception e) {
						logger.error("Error while loading "
								+ tmp.getAbsolutePath() + ", skipping.");
						logger.error(e, e);

					}

					logger.debug("--------------------------------------------------------------------");

				}
			}

			logger.info(getPluginPool().size() + " Plugin(s) loaded.");
			// logger.debug("Calling .init() in every plugin.");
			// for(int i = 0; i < getPluginPool().size(); i++) {
			// // phew.
			// ((Plugin)((PluginContainer)
			// getPluginPool().get(i)).getPluginObject()).init();
			// }
		}
	}

	/**
	 * Call the onLoad method in all plugins. This method is deprecated.
	 */
	@Deprecated
	public void callOnLoad() {

	}

	/**
	 * Loads the Plugin given by path into the COSI FrameWork.
	 */
	@Override
	protected void readPlugin(String path) {

		logger.debug("Loading " + path);

		JAXBContext context;
		File file = null;
		PluginContainer pluginContainer = new PluginContainer();
		try {
			context = JAXBContext.newInstance(CosiPluginModule.class);
			Unmarshaller um = context.createUnmarshaller();
			file = new File(path + System.getProperty("file.separator")
					+ "plugin.xml");
			CosiPluginModule cpmConfig = (CosiPluginModule) um
					.unmarshal(new FileReader(file));

			logger.info(cpmConfig);

			// Creating Plugin object

			pluginContainer.setConfig(cpmConfig);
			pluginContainer.setBasePath(path);

			// logger.debug("Checking if mainClass " +
			// pluginContainer.getConfig().getPlugin().getEntryPoint() +
			// " is unique.");
			// if(getPluginPool().getPlugin(pluginContainer.getConfig().getPlugin().getEntryPoint())
			// != null) {
			// logger.debug("There is already a plugin loaded with the mainClass "
			// + pluginContainer.getConfig().getPlugin().getEntryPoint());
			// }

			/*
			 * add the jar's to classpath.
			 */
			for (CPMClasspath classPath : cpmConfig.getPlugin().getListClasspath()) {
				String classpath = path + File.separator + classPath.getSrc();

				logger.debug("Adding to classpath: " + classpath);
				CosiClassLoader.addToClassPath(getClass().getClassLoader(),
						classpath);
			}

			try {
				// Instantiating object
				logger.debug("Instantiating "
						+ cpmConfig.getPlugin().getEntryPoint());
				PluginAPI pluginObject = (PluginAPI) Class.forName(
						cpmConfig.getPlugin().getEntryPoint()).newInstance();
				pluginContainer.setPlugin(pluginObject);
				getPluginPool().add(pluginContainer);
			} catch (ClassNotFoundException e) {
				logger.error("Class not found " + e);
				removePluginContainer(pluginContainer);

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				removePluginContainer(pluginContainer);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				removePluginContainer(pluginContainer);
			}

		} catch (JAXBException e) {
			logger.error(e);
			removePluginContainer(pluginContainer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug("Plugin " + path + " has no plugin.xml, skipping.");
			removePluginContainer(pluginContainer);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			removePluginContainer(pluginContainer);
		}

		// try {
		// /*
		// * parse the plugin.xml
		// */
		//
		//
		//
		// // // Add pluginObject to PluginContainer
		// // pluginContainer.setPluginObject(pluginObject);
		// //
		// // getPluginPool().add(pluginContainer);
		// //
		// // // Call .onLoad method.
		// // logger.debug("Calling .onLoad()");
		// // pluginObject.onLoad();
		//
		// } catch (Exception e) {
		// logger.error(e, e);
		// throw new RuntimeException(e);
		// }

	}

	private void removePluginContainer(PluginContainer pc) {
		logger.debug("Removing PluginContainer " + pc.toString());
		getPluginPool().remove(pc);
	}

	@Override
	public void preLoadPlugins() {
		for (PluginContainer pluginContainer : getPluginPool()) {
			try {

				// load things into classpath
				for (CPMClasspath classPath : pluginContainer.getConfig()
						.getPlugin().getListClasspath()) {
					try {
						CosiClassLoader.addToClassPath(
								getClass().getClassLoader(),
								pluginContainer.getBasePath()
										+ File.separatorChar
										+ classPath.getSrc());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						logger.error(classPath
								+ " could not be found, skipping.", e);
						removePluginContainer(pluginContainer);
					}
				}

			} catch (NoClassDefFoundError e) {
				logger.error(e, e);
			}

		}

	}

	@Override
	public void createPlugins() {
		// TODO Auto-generated method stub
		for (PluginContainer pluginContainer : getPluginPool()) {
			try {
				PluginAPI plugin = (PluginAPI) Class
						.forName(
								pluginContainer.getConfig().getPlugin()
										.getEntryPoint()).newInstance();
				plugin.preLoad();
				pluginContainer.setPlugin(plugin);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				logger.warn(pluginContainer.getConfig().getPlugin()
						.getEntryPoint()
						+ " is not valid, skipping");

			}
		}
	}

}
