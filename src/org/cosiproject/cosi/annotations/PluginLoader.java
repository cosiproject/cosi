package org.cosiproject.cosi.annotations;

import org.cosiproject.cosi.api.pluginloader.DefaultPluginLoader;

public @interface PluginLoader {

	Class<DefaultPluginLoader> value();

}
