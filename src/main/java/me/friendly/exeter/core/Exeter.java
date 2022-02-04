package me.friendly.exeter.core;

import java.io.*;
import java.io.File;

import me.friendly.api.event.EventProcessor;
import me.friendly.api.event.basic.BasicEventManager;
import me.friendly.exeter.command.CommandManager;
import me.friendly.exeter.config.ConfigManager;
import me.friendly.exeter.friend.FriendManager;
import me.friendly.exeter.gui.screens.accountmanager.AccountManager;
import me.friendly.exeter.keybind.KeybindManager;
import me.friendly.exeter.logging.Logger;
import me.friendly.exeter.module.ModuleManager;
import me.friendly.exeter.plugin.PluginManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.lwjgl.opengl.Display;

/**
 * Exeter client for Forge 1.12.2
 *
 * Exeter client. A client created by Friendly,
 * for Minecraft version 1.8. It has been released
 * or leaked on that version. Gopro336 has obtained
 * that version, and here, has reconstructed the
 * original source code. In this process, Gopro has
 * also ported the client to his preferred version
 * and platform, Minecraft 1.12.2 forge. Furthermore,
 * Gopro has done work to clean up the decompiled code,
 * and javadoc it.
 *
 * @author Friendly
 * @author Gopro336
 * @version b23
 */
public final class Exeter {
    private static Exeter instance = null;
    public static final String TITLE = "Exeter";
    public static final int BUILD = 23;
    public final long startTime = System.nanoTime() / 1000000L;
    private BasicEventManager eventManager;
    private KeybindManager keybindManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private FriendManager friendManager;
    private ConfigManager configManager;
    private AccountManager accountManager;
    private PluginManager pluginManager;
    private File directory;

    // client event bus
    public static EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    public Exeter() {

        // Register the EventProcessor
        EVENT_BUS.register(EventProcessor.INSTANCE);

        // Register the event manager
        MinecraftForge.EVENT_BUS.register(EventProcessor.INSTANCE);

        Logger.getLogger().print("Initializing...");
        instance = this;

        // In exeter 1.8, the config file is named clarinet for whatever reason. I changed that to be exeter
        this.directory = new File(System.getProperty("user.home"), "exeter");
//        this.directory = new File(System.getProperty("user.home"), "clarinet");

        if (!this.directory.exists()) {
            Logger.getLogger().print(String.format("%s client directory.", this.directory.mkdir() ? "Created" : "Failed to create"));
        }
        this.eventManager = new BasicEventManager();
        this.configManager = new ConfigManager();
        this.friendManager = new FriendManager();
        this.keybindManager = new KeybindManager();
        this.commandManager = new CommandManager();
        this.moduleManager = new ModuleManager();
//        this.accountManager = new AccountManager();
        this.pluginManager = new PluginManager();
        this.getConfigManager().getRegistry().forEach(config -> config.load(new Object[0]));
        try {
            this.pluginManager.onLoad();
            System.out.println("Plugin manager started.");
            System.out.println(this.pluginManager.getList() + "has been loaded.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Hook Thread"){

            @Override
            public void run() {
                Logger.getLogger().print("Shutting down...");
                getConfigManager().getRegistry().forEach(config -> config.save(new Object[0]));
                Logger.getLogger().print("Shutdown.");
            }
        });
        Display.setTitle(String.format("%s b%s  ", TITLE, 23));
        Logger.getLogger().print(String.format("Initialized, took %s milliseconds.", System.nanoTime() / 1000000L - this.startTime));
    }

    public static Exeter getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public KeybindManager getKeybindManager() {
        return this.keybindManager;
    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }

    public BasicEventManager getEventManager() {
        return this.eventManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    // AccountManager is not working
    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public File getDirectory() {
        return this.directory;
    }
}

