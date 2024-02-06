package com.angel.mc;

import org.bukkit.plugin.java.JavaPlugin;

public final class MiniRPG extends JavaPlugin {
    public static MiniRPGProxy ktProxy = new MiniRPGProxy();

    @Override
    public void onLoad() {
        ktProxy.bind(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        ktProxy.onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ktProxy.onDisable();
    }


}
