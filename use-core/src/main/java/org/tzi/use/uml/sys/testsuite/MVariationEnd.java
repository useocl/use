package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.util.SrcPos;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.mm.instance.MSystemException;

public class MVariationEnd extends MVariation {

	private MSystem system;
	
	public MVariationEnd(SrcPos pos, MSystem system) {
		super(pos);
		this.system = system;
	}

	@Override
	public void doExecute() throws MSystemException {
		system.endVariation();
	}

	public String name() {
		return "End variation";
	}

	@Override
	public String toString() {
		return name();
	}
}
