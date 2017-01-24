package com.github.rmannibucau.myfolder.front;

import com.github.rmannibucau.myfolder.configuration.RuntimeConfiguration;
import org.apache.meecrowave.Meecrowave;
import org.apache.meecrowave.junit.MonoMeecrowave;
import org.apache.meecrowave.testing.ConfigurationInject;
import org.apache.meecrowave.testing.Injector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.junit.Assert.assertEquals;

@RunWith(MonoMeecrowave.Runner.class)
public class MyFolderFileResourceTest {
    @ConfigurationInject
    private Meecrowave.Builder config;

    @Inject
    private RuntimeConfiguration configuration;

    @Before
    public void inject() {
        Injector.inject(this);
    }

    @Test
    public void list() {
        final Client client = ClientBuilder.newClient();
        try {
            final MyFolderFileResource.FolderContent content = client.target("http://localhost:" + config.getHttpPort() + "/api/file/list")
                    .request(APPLICATION_JSON_TYPE)
                    .post(entity(new MyFolderFileResource.ListRequest() {{
                        setFolder(configuration.getFolder().toAbsolutePath().toString());
                    }}, APPLICATION_JSON_TYPE), MyFolderFileResource.FolderContent.class);
            assertEquals(1, content.getFiles().size());

            final MyFolderFileResource.FileEntry child = content.getFiles().iterator().next();
            assertEquals("child1", child.getText());
        } finally {
            client.close();
        }
    }
}
