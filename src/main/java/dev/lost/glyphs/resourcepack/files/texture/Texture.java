package dev.lost.glyphs.resourcepack.files.texture;

import dev.lost.glyphs.resourcepack.files.ResourcePackFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public interface Texture {

    String path();

    ResourcePackFile file();

    static Texture file(String path, File file) throws IOException {
        return new TextureImpl(path, file);
    }

    static Texture bytes(String path, byte[] bytes) {
        return new TextureImpl(path, bytes);
    }

    static Texture rcpFile(String path, ResourcePackFile file) {
        return new TextureImpl(path, file);
    }

    static Texture image(String path, BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return new TextureImpl(path, baos.toByteArray());
        }
    }

}
