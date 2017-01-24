package com.github.rmannibucau.myfolder.test;

import org.apache.meecrowave.Meecrowave;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyFolderConfigurer implements Meecrowave.ConfigurationCustomizer {
    @Override
    public void accept(final Meecrowave.Builder builder) {
        final File file = new File("target/myfolder-test");
        builder.property("myfolder-folder", file.getAbsolutePath());

        // create some content
        file.mkdirs();
        try (final FileWriter writer = new FileWriter(new File(file, "child1"))) {
            writer.write("child_1");
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
