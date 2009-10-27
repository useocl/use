package org.tzi.use.examplePlugin;

import org.tzi.use.runtime.service.IPluginService;
import org.tzi.use.util.Log;

public class ExamplePluginService implements IPluginService {

    protected String filename;

    public String getFilename() {
	if (this.filename == null) {

	}
	return this.filename;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public Object executeService(Object object) {
	try {
	    this.filename = (String) object;
	    System.out.println("Overgiven name was: [" + this.filename + "]");
	} catch (Exception e) {
	    Log.error("Could not execute Example Plugin Service, " + e);
	}
	return this.filename;
    }
}
