/* Cosi.java is part of Cosi
 * created 18.11.2011 
 *
 *
 * Copyright (c) 2011, The Cosi Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
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
package org.cosiproject.cosi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.arguments.ArgumentHelp;
import org.cosiproject.cosi.arguments.ArgumentVersion;
import org.cosiproject.cosi.core.config.CosiXML;
import org.cosiproject.cosi.core.logger.COSIAppender;
import org.cosiproject.cosi.core.pluginloader.DefaultPluginLoader;
import org.cosiproject.cosi.core.pluginloader.PluginLoader;
import org.cosiproject.cosi.core.pluginloader.plugin.PluginAPI;
import org.cosiproject.cosi.core.pluginloader.plugin.PluginContainer;
import org.cosiproject.cosi.core.pluginloader.util.PluginPool;
import org.cosiproject.cosi.cron.Cron;
import org.cosiproject.cosi.thread.ThreadManager;
import org.cosiproject.toolkit.argumentor.Argument;
import org.cosiproject.toolkit.argumentor.ArgumentCallback;
import org.cosiproject.toolkit.argumentor.ArgumentEvent;
import org.cosiproject.toolkit.argumentor.ArgumentHandler;
import org.cosiproject.toolkit.argumentor.ArgumentMultiplicity;
import org.cosiproject.toolkit.argumentor.ArgumentPrefix;
import org.cosiproject.toolkit.argumentor.ArgumentSeparator;
import org.cosiproject.toolkit.argumentor.ArgumentSet;
import org.cosiproject.toolkit.argumentor.ArgumentSwitch;
import org.cosiproject.toolkit.localizer.Localizer;
import org.cosiproject.toolkit.version.Version;

/**
 * The Class Cosi.
 */
public class Cosi {

	/** The logger. */
	private static Logger logger = Logger.getLogger(Cosi.class);
	
	/** The instance. */
	private static Cosi instance;
	
	/** The arguments. */
	private static String[] arguments;
	
	/** The localizer. */
	private static Localizer localizer = Localizer.getLocalizer(Cosi.class);
	
	/** The VERSION. */
//	public static final Version VERSION = new Version(Cosi.class.getPackage().getSpecificationVersion(), Cosi.class.getPackage().getSpecificationTitle());

	private static CosiXML cosiXML;
	
	/** The list plugin loader. */
	private ArrayList<PluginLoader> listPluginLoader;
	private static ArgumentHandler argumentHandler = new ArgumentHandler();

	static {
		Logger.getRootLogger().addAppender(new COSIAppender());

		// *
		// * Building argument list.
		// *
		ArgumentSet set = new ArgumentSet("cosi");
		ArgumentCallback safetyMode = new ArgumentCallback() {

			@Override
			public void process(ArgumentEvent e) {
				// TODO Auto-generated method stub

			}
		};
		ArrayList<Argument> safetyParamList = new ArrayList<Argument>();
		safetyParamList.add(new Argument("S", ArgumentPrefix.DASH));
		safetyParamList.add(new Argument("safety",
				ArgumentPrefix.DOUBLEDASH));

		set.addArgumentSwitch(new ArgumentSwitch(
				safetyParamList,
				ArgumentSeparator.NONE,
				ArgumentMultiplicity.ZERO_OR_ONE,
				localizer.getKey("argumentSafety"),
				safetyMode));

		ArrayList<Argument> versionParamList = new ArrayList<Argument>();
		versionParamList.add(new Argument("V", ArgumentPrefix.DASH));
		versionParamList.add(new Argument("version",
				ArgumentPrefix.DOUBLEDASH));

		set.addArgumentSwitch(new ArgumentSwitch(versionParamList,
				ArgumentSeparator.NONE, ArgumentMultiplicity.ZERO_OR_ONE,
				localizer.getKey("argumentVersion"), new ArgumentVersion()));

		ArrayList<Argument> helpParamList = new ArrayList<Argument>();
		helpParamList.add(new Argument("h", ArgumentPrefix.DASH));
		helpParamList.add(new Argument("help",
				ArgumentPrefix.DOUBLEDASH));

		set.addArgumentSwitch(new ArgumentSwitch(helpParamList, ArgumentSeparator.NONE,
				ArgumentMultiplicity.ZERO_OR_ONE, localizer
						.getKey("argumentHelp"), new ArgumentHelp()));

		argumentHandler.setRootSet(set);
	}

	/**
	 * Constructor does nothing
	 */
	protected Cosi() {
	}

	protected Cosi(CosiXML cosiXML) {
		this();
		DefaultPluginLoader pluginLoader = new DefaultPluginLoader();
		pluginLoader.setSuffix(cosiXML.getPluginLoaderXML().getSuffix());
		pluginLoader.setPluginDirectoryList(cosiXML.getPluginLoaderXML().getDirectories());
 		pluginLoader.setPluginPool(new PluginPool<PluginContainer>());
		getPluginLoaderList().add(pluginLoader);
	}

	/**
	 * Start the COSI FrameWork.
	 */
	public void bootstrap() {

		logger.info("Starting COSI " + getVersion());
		
			if(getCosiXML() == null)
				setCosiXML(CosiXML.load("cosi.xml"));
		
		try {
			ThreadManager.getInstance();			
			try {
				argumentHandler.parse(getArguments());
			} catch (IllegalArgumentException e) {
				System.out.println(Cosi.getVersionString());
				System.out.println();
				System.err.println(e.getMessage());
				System.exit(-1);
			}
			// initializing workspace...
			
			// kickin' Cron!
			Cron.getInstance();
			// tell the pluginloaders to do their work..!
			loadPlugins();

		} catch (Exception e) {
			logger.error("Uncorrectly caught exception: ", e);
		}
	}

	/**
	 * Load plugins.
	 */
	private void loadPlugins() {
		// TODO Auto-generated method stub
		for (PluginLoader pluginLoader : getPluginLoaderList()) {
			Logger logger = Logger.getLogger(pluginLoader.getClass().getName());
			logger.debug("Loading Plugins... ");
			pluginLoader.load();
			logger.debug("Creating Plugins... ");
			pluginLoader.createPlugins();
			logger.debug("Initializing Plugins... ");
			pluginLoader.initPlugins();
		}
	}

	/*
	 * Wohou, start up.
	 */
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// parsing command line arguments.
		arguments = args;
		Cosi.getInstance().bootstrap();
	}

	/**
	 * This returns the COSI instance.
	 * 
	 * @return Instace of COSI
	 */
	public static Cosi getInstance() {
		if (instance == null) {
			instance = new Cosi();
		}
		return instance;
	}

	/*
	 * Some static methods. Just for the moment.
	 */
	/**
	 * Returns the current language.
	 *
	 * @return the lang
	 */
	public static String getLang() {
		return System.getProperty("user.language");
	}

	/**
	 * Get formatted version.
	 *
	 * @return the version
	 */
	public static Version getVersion() {
		return null;
	}

	/**
	 * Gets the plugin loader list.
	 *
	 * @return the pluginLoader
	 */
	public ArrayList<PluginLoader> getPluginLoaderList() {
		if (listPluginLoader == null)
			listPluginLoader = new ArrayList<PluginLoader>();
		return listPluginLoader;
	}

	/**
	 * Register plugin loader.
	 *
	 * @param loader the loader
	 */
	public void registerPluginLoader(PluginLoader loader) {
		getPluginLoaderList().add(loader);
	}

	/**
	 * Shutdown everything that needs to be shut down. If exit is true, this
	 * method will quit the program.
	 *
	 * @param exit the exit
	 */
	public void shutdown(boolean exit) {
		logger.debug("Recieved shutdown...");
		logger.info("stopped.");
		if (exit)
			System.exit(0);
	}

	/**
	 * Gets the file.
	 *
	 * @param file the file
	 * @return the file
	 */
	public static File getFile(String file) {
		return new File(getHome() + File.separator + file);
	}

	/**
	 * Gets the home.
	 *
	 * @return the home
	 */
	public static String getHome() {
		String retVal = null;
		File currentPath = new File(".");
		try {
			retVal = currentPath.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Could not get home path: " + e, e);
		}
		return retVal;
	}

	/**
	 * Gets the arguments.
	 *
	 * @return the arguments
	 */
	public static String[] getArguments() {
		return arguments;
	}

	/**
	 * Gets the version string.
	 *
	 * @return the version string
	 */
	public static String getVersionString() {
		// TODO Auto-generated method stub
		return "COSI, Version " + Cosi.getVersion() + " ("
				+ System.getProperty("os.name") + " "
				+ System.getProperty("os.version") + " "
				+ System.getProperty("os.arch") + ")";
	}

	
	public PluginAPI getPlugin(Class pluginClass) {
		for(PluginLoader loader : getPluginLoaderList()) {
			PluginAPI plugin = loader.getPluginPool().getPlugin(pluginClass);
			if(plugin != null)
				return plugin;
		}
		return null;
	}

	public static CosiXML getCosiXML() {
		return cosiXML;
	}

	public static void setCosiXML(CosiXML cosiXML) {
		Cosi.cosiXML = cosiXML;
	}
	
	
}
