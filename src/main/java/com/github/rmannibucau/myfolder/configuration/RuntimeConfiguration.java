package com.github.rmannibucau.myfolder.configuration;

import lombok.Data;

import javax.enterprise.inject.Vetoed;
import java.nio.file.Path;

@Data
@Vetoed
public class RuntimeConfiguration {
    private final Path folder;
}
