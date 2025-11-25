package dev.lost.glyphs.commands;

import dev.lost.glyphs.Glyphs;
import dev.lost.glyphs.ResourcePackGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class GlyphsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Use /glyphs reload");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage("Reloading glyphs configuration...");
                Glyphs instance = Glyphs.getPlugin(Glyphs.class);
                instance.reloadConfig();
                ResourcePackGenerator.generate(instance, new File(instance.getDataFolder(), "glyphs.yml"));
                sender.sendMessage("Built resource pack with updated configuration.");
            } else {
                sender.sendMessage("Unknown subcommand. Use /glyphs reload");
            }
        }
        return true;
    }
}
