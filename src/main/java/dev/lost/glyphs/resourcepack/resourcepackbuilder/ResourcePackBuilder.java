package dev.lost.glyphs.resourcepack.resourcepackbuilder;

import dev.lost.glyphs.resourcepack.resourcepack.ResourcePack;

import java.io.File;

public interface ResourcePackBuilder {

    void build(ResourcePack resourcePack, File outputFile);

}
