package com.kinnara.jwplugins.asyncruntool;

import org.joget.apps.app.service.AppUtil;
import org.joget.plugin.base.DefaultApplicationPlugin;
import org.joget.plugin.base.Plugin;
import org.joget.plugin.base.PluginManager;
import org.joget.plugin.property.model.PropertyEditable;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Plugin Generator Helper
 */
public class PluginGenerator {
    /***
     * Generate Generic Plugin
     * @param elementProperty
     * @param <T> Plugin
     * @return
     * @throws PluginGeneratorException
     */
    public static <T extends Plugin> T getPlugin(Map<String, Object> elementProperty) throws PluginGeneratorException {
        ApplicationContext applicationContext = AppUtil.getApplicationContext();
        if(applicationContext == null) {
            throw new PluginGeneratorException("Null Application Context");
        }

        PluginManager pluginManager = (PluginManager) applicationContext.getBean("pluginManager");
        if(pluginManager == null) {
            throw new PluginGeneratorException("Null Plugin Manager");
        }

        String className = String.valueOf(elementProperty.get("className"));
        Map<String, Object> properties = (Map<String, Object>)elementProperty.get("properties");
        Plugin plugin = pluginManager.getPlugin(className);
        if(plugin == null) {
            throw new PluginGeneratorException("Error generating plugin ["+ className+ "]");
        }

        if(plugin instanceof PropertyEditable) {
            ((PropertyEditable)plugin).setProperties(properties);
        }

        return (T) plugin;
    }

    /**
     * Generate {@link DefaultApplicationPlugin}
     * @param elementProperty
     * @param mapProperty
     * @return
     * @throws PluginGeneratorException
     */
    public static DefaultApplicationPlugin getDefaultApplicationPlugin(Map<String, Object> elementProperty, Map<String, Object> mapProperty) throws PluginGeneratorException {
        DefaultApplicationPlugin defaultApplicationPlugin = getPlugin(elementProperty);

        // add missing properties
        for(Map.Entry<String, Object> entry : mapProperty.entrySet()) {
            if(!defaultApplicationPlugin.getProperties().containsKey(entry.getKey()))
                defaultApplicationPlugin.getProperties().put(entry.getKey(), entry.getValue());
        }

        return defaultApplicationPlugin;
    }


    /**
     * Exception to handle Plugin Generator Error
     */
    static class PluginGeneratorException extends Throwable {
        PluginGeneratorException(String message) {
            super(message);
        }
    }
}
