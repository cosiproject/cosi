package org.cosiproject.cosi.annotations;

import org.cosiproject.cosi.core.pluginloader.DefaultPluginLoader;

public @interface PluginLoader {

	Class<DefaultPluginLoader> value();

}
