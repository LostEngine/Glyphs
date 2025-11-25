package dev.lost.glyphs.resourcepack.resourcepackbuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dev.lost.glyphs.resourcepack.files.texture.Texture;
import dev.lost.glyphs.resourcepack.resourcepack.ResourcePack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ResourcePackBuilderImpl implements ResourcePackBuilder {

    private static final Gson GSON = new Gson();

    @Override
    public void build(ResourcePack resourcePack, File outputFile) {
        try (FileOutputStream fos = new FileOutputStream(outputFile); ZipOutputStream zos = new ZipOutputStream(fos)) {

            zos.setMethod(ZipOutputStream.DEFLATED);
            zos.setLevel(Deflater.BEST_COMPRESSION);

            writeEntry(zos, "pack.mcmeta", GSON.toJson(resourcePack.mcmeta().json()));

            for (Map.Entry<String, JsonElement> e : resourcePack.jsonFiles().entrySet()) {
                writeEntry(zos, e.getKey(), GSON.toJson(e.getValue()));
            }

            for (Map.Entry<String, Texture> e : resourcePack.textures().entrySet()) {
                writeEntry(zos, e.getKey(), e.getValue().file().getBytes());
            }

            zos.finish();
        } catch (IOException ex) {
            throw new UncheckedIOException("Cannot build resource-pack zip", ex);
        }
    }

    private void writeEntry(ZipOutputStream zos, String path, String utf8Text) throws IOException {
        writeEntry(zos, path, utf8Text.getBytes(StandardCharsets.UTF_8));
    }

    private void writeEntry(ZipOutputStream zos, String path, byte[] bytes) throws IOException {
        ZipEntry entry = new ZipEntry(path);
        entry.setTime(System.currentTimeMillis());
        zos.putNextEntry(entry);
        zos.write(bytes);
        zos.closeEntry();
    }
}
