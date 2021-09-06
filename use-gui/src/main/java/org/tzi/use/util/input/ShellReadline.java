/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

// $Id$

package org.tzi.use.util.input;

import java.io.IOException;

import org.tzi.use.main.shell.Shell;

/**
 * A {@link Readline} implementation that reads input from the {@link Shell}
 * using the current readline that is on top of the readline stack. This might
 * either be interactive or from an open soil file.
 * 
 * @author Frank Hilken
 */
public class ShellReadline implements Readline {

	private Shell shell;
	
	public ShellReadline(Shell useShell) {
		shell = useShell;
	}

	@Override
	public String readline(String prompt) throws IOException {
		if(shell == null){
			throw new IOException("Stream closed");
		}
		return shell.readline(prompt);
	}

	@Override
	public void usingHistory() {
	}

	@Override
	public void readHistory(String filename) throws IOException {
	}

	@Override
	public void writeHistory(String filename) throws IOException {
	}

	@Override
	public void close() throws IOException {
		shell = null;
	}

	@Override
	public boolean doEcho() {
		return false;
	}

}
