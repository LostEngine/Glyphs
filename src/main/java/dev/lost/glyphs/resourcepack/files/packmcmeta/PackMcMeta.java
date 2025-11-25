package dev.lost.glyphs.resourcepack.files.packmcmeta;

import com.google.gson.JsonElement;

public interface PackMcMeta {

    static PackMcMeta meta(int packFormat, String description) {
        return new PackMcMetaImpl(packFormat, description);
    }

    int packFormat();

    String description();

    JsonElement json();

}