package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.sys.MSystem;

public class MVariationStart extends MVariation {
	private MSystem system;
	
	public MVariationStart(SrcPos pos, MSystem system) {
		super(pos);
		this.system = system;
	}

	@Override
	public void doExecute() {
		system.beginVariation();
	}

	public String name() {
		return "Start variation";
	}

	@Override
	public String toString() {
		return name();
	}
}
