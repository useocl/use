package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

public class MVariationEnd extends MCmd {

	private MSystem system;
	
	public MVariationEnd(SrcPos pos, MSystem system) {
		super(pos, false);
		this.system = system;
	}

	@Override
	public void getChanges(StateChangeEvent sce, boolean undoChanges) { }

	@Override
	public String getUSEcmd() {
		return "endVariation";
	}

	@Override
	protected void doExecute() throws CommandFailedException {
		try {
			system.endVariation();
		} catch (MSystemException e) {
			throw new CommandFailedException(e.getMessage());
		}
	}

	@Override
	public String name() {
		return "End variation";
	}

	@Override
	public String toString() {
		return name();
	}

	@Override
	public void undo() throws CannotUndoException { }

}
