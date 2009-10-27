package org.tzi.use.myPluginServiceUsage;

import org.tzi.use.runtime.service.IPluginService;
import org.tzi.use.util.Log;

/**
 * My PluginService, another Example Service class
 * 
 * @author Roman Asendorf
 * 
 */
public class MyPluginService implements IPluginService {

	protected String parameter;

	public String getParameter() {
		if (this.parameter == null) {

		}
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Object executeService(Object object) {
		try {
			this.parameter = (String) object;
			System.out.println("Running MyPluginService...");
		} catch (Exception e) {
			Log.error("Could not execute My Plugin Service, " + e);
		}
		return this.parameter;
	}
}
