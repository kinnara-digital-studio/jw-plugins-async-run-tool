package com.kinnara.jwplugins.asyncruntool;

import org.joget.apps.app.service.AppUtil;
import org.joget.commons.util.LogUtil;
import org.joget.plugin.base.DefaultApplicationPlugin;
import org.joget.plugin.base.Plugin;

import java.util.Map;

/**
 * @author aristo
 */
public class AsyncRunTool extends DefaultApplicationPlugin {
    public String getName() {
        return "Async Run Tool";
    }

    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }

    public String getDescription() {
        return getName();
    }

    @Override
    public Object execute(Map map) {
        try {
            // generate Default Application Plugin
            final DefaultApplicationPlugin asyncTool = PluginGenerator.getDefaultApplicationPlugin((Map<String, Object>) getProperty("tool"), map);

            // run in secondary thread
            new Thread(new Runnable() {
                public void run() {
                    asyncTool.execute(asyncTool.getProperties());;
                }
            }).start();

        } catch (PluginGenerator.PluginGeneratorException e) {
            LogUtil.error(getClassName(), e, e.getMessage());
        }

        return null;
    }

    public String getLabel() {
        return getName();
    }

    public String getClassName() {
        return getClass().getName();
    }

    public String getPropertyOptions() {
        return AppUtil.readPluginResource(getClassName(), "properties/AsyncRunTool.json", null, false, "messages/AsyncRunTool");
    }
}
