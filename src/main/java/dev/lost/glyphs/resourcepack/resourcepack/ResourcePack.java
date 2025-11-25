package dev.lost.glyphs.resourcepack.resourcepack;

import com.google.gson.JsonElement;
import dev.lost.glyphs.resourcepack.files.packmcmeta.PackMcMeta;
import dev.lost.glyphs.resourcepack.files.texture.Texture;

import java.io.File;
import java.util.Map;

public interface ResourcePack {

    static ResourcePack resourcePack() {
        return new ResourcePackImpl();
    }

    PackMcMeta mcmeta();

    void mcmeta(PackMcMeta meta);

    void mcmeta(int packFormat, String description);

    Map<String, JsonElement> jsonFiles();

    void jsonFile(String path, JsonElement element);

    Map<String, Texture> textures();

    void texture(Texture texture);

    void build(File outputFile);
}
