package dev.lost.glyphs.resourcepack.files.texture;

import dev.lost.glyphs.resourcepack.files.ResourcePackFile;

import java.io.File;
import java.io.IOException;

public class TextureImpl implements Texture {

    private final String path;
    private final ResourcePackFile file;

    public TextureImpl(String path, ResourcePackFile file) {
        this.path = path;
        this.file = file;
    }

    public TextureImpl(String path, File file) throws IOException {
        this.path = path;
        this.file = new ResourcePackFile(path, file);
    }

    public TextureImpl(String path, byte[] bytes) {
        this.path = path;
        this.file = new ResourcePackFile(path, bytes);
    }

    public String path() {
        return path;
    }

    public ResourcePackFile file() {
        return file;
    }
}
