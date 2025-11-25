package dev.lost.glyphs.resourcepack.resourcepack;

import com.google.gson.JsonElement;
import dev.lost.glyphs.resourcepack.files.packmcmeta.PackMcMeta;
import dev.lost.glyphs.resourcepack.files.texture.Texture;
import dev.lost.glyphs.resourcepack.resourcepackbuilder.ResourcePackBuilderImpl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResourcePackImpl implements ResourcePack {

    private PackMcMeta mcmeta;
    private final Map<String, JsonElement> jsonFiles = new HashMap<>();
    private final Map<String, Texture> textures = new HashMap<>();

    @Override
    public PackMcMeta mcmeta() {
        return mcmeta;
    }

    @Override
    public void mcmeta(PackMcMeta mcmeta) {
        this.mcmeta = mcmeta;
    }

    @Override
    public void mcmeta(int packFormat, String description) {
        mcmeta = PackMcMeta.meta(packFormat, description);
    }

    @Override
    public Map<String, JsonElement> jsonFiles() {
        return jsonFiles;
    }

    @Override
    public void jsonFile(String path, JsonElement element) {
        jsonFiles.put(path, element);
    }

    @Override
    public Map<String, Texture> textures() {
        return textures;
    }

    @Override
    public void texture(Texture texture) {
        textures.put(texture.path(), texture);
    }

    @Override
    public void build(File outputFile) {
        new ResourcePackBuilderImpl().build(this, outputFile);
    }
}
