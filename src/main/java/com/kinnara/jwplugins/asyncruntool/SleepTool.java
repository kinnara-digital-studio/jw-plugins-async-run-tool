package com.kinnara.jwplugins.asyncruntool;

import org.joget.apps.app.service.AppUtil;
import org.joget.commons.util.LogUtil;
import org.joget.plugin.base.DefaultApplicationPlugin;

import java.util.Date;
import java.util.Map;

/**
 * @author aristo
 */
public class SleepTool extends DefaultApplicationPlugin {
    public String getName() {
        return "Sleep Tool";
    }

    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }

    public String getDescription() {
        return "Sleep Tool";
    }

    public Object execute(Map map) {
        long milis = 0;
        try {
            milis = Long.valueOf(getPropertyString("milis"));
        }catch (NumberFormatException e) {
            LogUtil.error(getClassName(), e, e.getMessage());
        }

        try {
            LogUtil.info(getClassName(), "Sleeping at ["+ new Date().toString() +"]");
            Thread.sleep(milis);
            LogUtil.info(getClassName(), "Waking up at ["+ new Date().toString() +"]");
        } catch (InterruptedException e) {
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
        return AppUtil.readPluginResource(getClassName(), "properties/SleepTool.json", null, false, "messages/SleepTool");
    }
}
