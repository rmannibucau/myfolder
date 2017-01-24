package com.github.rmannibucau.myfolder.front;

import javax.enterprise.context.Dependent;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Dependent
@ApplicationPath("api")
public class MyFolderApplication extends Application {
}
