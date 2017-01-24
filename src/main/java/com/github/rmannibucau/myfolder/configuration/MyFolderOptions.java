package com.github.rmannibucau.myfolder.configuration;

import lombok.Data;
import org.apache.meecrowave.runner.Cli;
import org.apache.meecrowave.runner.cli.CliOption;

import javax.enterprise.inject.Vetoed;
import java.io.File;

@Data
@Vetoed
public class MyFolderOptions implements Cli.Options {
    @CliOption(name = "myfolder-folder", description = "Which folder to use")
    private String folder = new File(System.getProperty("user.home"), ".myfolder").getAbsolutePath();
}
