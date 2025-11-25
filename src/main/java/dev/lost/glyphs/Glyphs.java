package dev.lost.glyphs;

import dev.lost.glyphs.commands.GlyphsCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Glyphs extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        File glyphsConfig = new File(getDataFolder(), "glyphs.yml");
        if (!glyphsConfig.exists()) {
            // Save default assets if the config does not exist
            saveResource("glyphs.yml", false);
            saveResource("textures/font/larry_emoji.png", false);
        }
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        ResourcePackGenerator.generate(this, glyphsConfig);
        getCommand("glyphs").setExecutor(new GlyphsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
