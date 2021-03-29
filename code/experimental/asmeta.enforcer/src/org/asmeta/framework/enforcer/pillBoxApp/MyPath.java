package org.asmeta.framework.enforcer.pillBoxApp;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class MyPath {
	
	private String path;
	
	public MyPath(String string) {
		/*File currentDirectory = new File(new File(".").getAbsolutePath());
		path=currentDirectory.getAbsolutePath();*/
		URL asmpath = getClass().getResource("specs/"+string);
		if (asmpath == null){
			throw new RuntimeException("pillbox.asm  not found");
		}
		path=asmpath.getPath();
		System.out.println(path);
	}
	
	public String getPath() {
		return path;
	}

}
