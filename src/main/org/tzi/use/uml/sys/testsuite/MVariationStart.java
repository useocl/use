package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

public class MVariationStart extends MCmd {
	private MSystem system;
	
	public MVariationStart(SrcPos pos, MSystem system) {
		super(pos, false);
		this.system = system;
	}

	@Override
	public void getChanges(StateChangeEvent sce, boolean undoChanges) {	}

	@Override
	public String getUSEcmd() {
		return "variationStart";
	}

	@Override
	protected void doExecute() throws CommandFailedException {
		system.beginVariation();
	}

	@Override
	public String name() {
		return "Start variation";
	}

	@Override
	public String toString() {
		return name();
	}

	@Override
	public void undo() throws CannotUndoException {	}
}
