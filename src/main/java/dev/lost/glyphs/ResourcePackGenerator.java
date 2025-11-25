package dev.lost.glyphs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.lost.glyphs.resourcepack.files.texture.Texture;
import dev.lost.glyphs.resourcepack.resourcepack.ResourcePack;
import it.unimi.dsi.fastutil.ints.Int2CharOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ResourcePackGenerator {

    public static void generate(Glyphs plugin, File glyphsConfigFile) {
        ResourcePack resourcePack = ResourcePack.resourcePack();
        resourcePack.mcmeta(12, "Glyphs Resource Pack");
        char c = 57344;
        JsonArray providers = new JsonArray();
        JsonObject langObject = new JsonObject();
        if (plugin.getConfig().getBoolean("offset_characters.enabled", false)) {
            int from = plugin.getConfig().getInt("offset_characters.from", -1023);
            int to = plugin.getConfig().getInt("offset_characters.to", 1023);
            int maxExp = 0;
            while ((1 << (maxExp + 1)) <= Math.max(Math.abs(from), Math.abs(to))) {
                maxExp++;
            }
            IntArrayList offsets = new IntArrayList();

            for (int exp = 0; exp <= maxExp; exp++) {
                int value = 1 << exp;
                if (-value >= from) offsets.add(-value);
                if (value <= to) offsets.add(value);
            }
            offsets.sort(null);
            Int2CharOpenHashMap characters = new Int2CharOpenHashMap();
            JsonObject providerObject = new JsonObject();
            providerObject.addProperty("type", "space");
            JsonObject advancesObject = new JsonObject();
            for (int offset : offsets) {
                if (c >= 63743) {
                    throw new RuntimeException("Exceeded maximum number of glyphs (6400)");
                }
                advancesObject.addProperty(String.valueOf(c++), offset);
                characters.addTo(offset, c);
            }
            providerObject.add("advances", advancesObject);
            providers.add(providerObject);
            for (int i = from; i <= to; i++) {
                int remaining = i;
                StringBuilder sb = new StringBuilder();

                for (int j = offsets.size() - 1; j >= 0; j--) {
                    int off = offsets.getInt(j);
                    if (remaining == 0) break;
                    if ((remaining > 0 && off > 0 && (remaining & off) == off) ||
                            (remaining < 0 && off < 0 && ((-remaining) & (-off)) == -off)) {
                        sb.append(characters.get(off));
                        remaining -= off;
                    }
                }
                langObject.addProperty("offsets." + i, sb.toString());
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(glyphsConfigFile);
        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section != null) {
                if (c >= 63743) {
                    throw new RuntimeException("Exceeded maximum number of glyphs (6400)");
                }
                JsonObject providerObject = new JsonObject();
                providerObject.addProperty("type", "bitmap");
                String imagePath = section.getString("image_path");
                if (imagePath == null) throw new RuntimeException("Missing image path for glyph: " + key);
                if (!imagePath.endsWith(".png")) imagePath += ".png";
                providerObject.addProperty("file", imagePath);
                providerObject.addProperty("ascent", section.getInt("ascent", 7));
                providerObject.addProperty("height", section.getInt("height", 8));
                JsonArray charsObject = new JsonArray();
                String character = String.valueOf(c++);
                charsObject.add(character);
                providerObject.add("chars", charsObject);
                providers.add(providerObject);
                langObject.addProperty("glyph." + key, character);
            }
        }
        JsonObject fontObject = new JsonObject();
        fontObject.add("providers", providers);
        resourcePack.jsonFile("assets/minecraft/font/default.json", fontObject);
        resourcePack.jsonFile("assets/minecraft/lang/en_us.json", langObject);

        // Put all textures into the resource pack
        File texturesFolder = new File(plugin.getDataFolder(), "textures");
        try (Stream<Path> paths = Files.walk(texturesFolder.toPath())) {
            paths.forEach(path -> {
                File file = path.toFile();
                if (file.isFile()) {
                    try {
                        String relativePath = texturesFolder.toPath().relativize(file.toPath()).toString().replace("\\", "/");
                        resourcePack.texture(Texture.file("assets/minecraft/textures/" + relativePath, file));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File resourcePackFile = new File(plugin.getDataFolder(), "resource-pack.zip");
        resourcePack.build(resourcePackFile);
    }

}
