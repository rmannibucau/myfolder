package com.github.rmannibucau.myfolder.configuration;

import org.apache.meecrowave.Meecrowave;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.nio.file.Paths;

@ApplicationScoped
public class ConfigurationProducer {
    @Inject
    private Meecrowave.Builder builder;

    @Produces
    public RuntimeConfiguration config() {
        final MyFolderOptions extension = builder.getExtension(MyFolderOptions.class);
        if (extension.getFolder() == null) {
            throw new IllegalArgumentException("No folder configured");
        }
        return new RuntimeConfiguration(Paths.get(extension.getFolder()));
    }
}
