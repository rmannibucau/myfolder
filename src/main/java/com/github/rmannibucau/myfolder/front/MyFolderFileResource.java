package com.github.rmannibucau.myfolder.front;

import com.github.rmannibucau.myfolder.configuration.RuntimeConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("file")
@ApplicationScoped
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class MyFolderFileResource {
    private static final File[] NO_FILE = new File[0];
    private static final FileDetail NOT_EXISTING = new FileDetail(false, false, null, 0);

    @Inject
    private RuntimeConfiguration configuration;

    @POST
    @Path("list")
    public FolderContent list(@QueryParam("show-hidden") final boolean showHidden,
                              final ListRequest request) {
        final File root = ofNullable(request.getFolder())
                .map(f -> configuration.getFolder().resolve(f))
                .orElse(configuration.getFolder()).normalize().toFile();
        return new FolderContent(Stream.of(ofNullable(root.listFiles(f -> showHidden || !f.getName().startsWith("."))).orElse(NO_FILE))
                .map(f -> new FileEntry(f.getAbsolutePath(), f.getName(), f.isDirectory() ? "directory" : "file"))
                .sorted((o1, o2) -> {
                    final int icon = o1.getIcon().compareTo(o2.getIcon());
                    if (icon != 0) {
                        return icon;
                    }
                    return o1.getText().compareTo(o2.getText());
                })
                .collect(toList()));
    }

    @POST
    public FileDetail details(final DetailRequest request) {
        final File file = new File(request.getId());
        if (!file.exists()) {
            return NOT_EXISTING;
        } else if (file.isDirectory()) {
            return new FileDetail(true, true, file.getAbsolutePath(), 0);
        }
        return new FileDetail(true, false, file.getAbsolutePath(), file.length());
    }

    // TODO: delete, upload, (edit?) with servlet 3 Parts?

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FolderContent {
        private Collection<FileEntry> files;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDetail {
        private boolean exists;
        private boolean directory;
        private String path;
        private long length;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileEntry { // jstree format
        private String id;
        private String text;
        private String icon;
    }

    @Data
    public static class DetailRequest {
        private String id;
    }

    @Data
    public static class ListRequest {
        private String folder;
    }
}
