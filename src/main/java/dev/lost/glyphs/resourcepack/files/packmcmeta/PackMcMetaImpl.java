package dev.lost.glyphs.resourcepack.files.packmcmeta;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public record PackMcMetaImpl(
        int packFormat,
        String description
) implements PackMcMeta {
    @Override
    public JsonElement json() {
        JsonObject json = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", packFormat);
        pack.addProperty("description", description);
        json.add("pack", pack);
        return json;
    }
}
