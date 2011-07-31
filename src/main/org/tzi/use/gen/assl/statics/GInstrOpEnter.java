package org.tzi.use.gen.assl.statics;

import java.util.ArrayList;
import java.util.List;

public class GInstrOpEnter extends GInstruction {

	GValueInstruction fObjname;
	String fOpname;
	List fParameter;
	
	public GInstrOpEnter(GValueInstruction oid, String opname, List params) {
		fObjname = oid;
		fOpname = opname;
		fParameter = (List) params;
	}

	public GValueInstruction objname() {
		return fObjname;
	}
	
	public String opname() {
		return fOpname;
	}
	
	public List parameter() {
		return fParameter;
	}
	
	@Override
	public String toString() {
		return "OpEnter "+fObjname.toString()+" "+fOpname+"("+fParameter+")";
	}

}
