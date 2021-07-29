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

package org.tzi.use.gen.assl.dynamics;

import java.io.PrintWriter;

import org.tzi.use.config.Options;
import org.tzi.use.util.Log;

/**
 * Base class for all try evaluations
 * @author Lars Hamann
 *
 */
public abstract class GEvalInstrTry extends GEvalInstruction {
	
	private final Output output;
	
	public GEvalInstrTry(boolean first) {
		if (first && !Options.testMode) {
			output = new OutputFirst();
		} else {
			output = Output.defaultOut;
		}
	}
	
	protected void initProgress(long max) {
		output.initProgress(max);
	}
	
	protected void outPutProgress(long state) {
		output.outPutProgress(state);
	}
	
	protected void endProgress() {
		output.endProgress();
	}

	private static class Output {
		public static final Output defaultOut = new Output();
		
		protected void initProgress(long end) {}
		protected void outPutProgress(long state) {}
		protected void endProgress() {}
	}
	
	private static class OutputFirst extends Output {
		static int numCols = 50;
		static char barChar = '#';
		
		protected long max;
		protected int last;
		protected PrintWriter progressOut;
		
		protected void initProgress(long end) {
			progressOut = new PrintWriter(Log.out());
			max = end;
			progressOut.println("Progress of first Try in ASSL-Procedure (" + max + " combinations):");
			
			progressOut.print("|");
			
			for (int i = 1; i < numCols - 1; ++i)
				progressOut.print('-');

			progressOut.println('|');
			progressOut.flush();
		}
		
		protected void outPutProgress(long state) {
			int barNum = (int) (numCols * ((double)state / (double)max));
			if (barNum > last) {
				for (int i = last; i < barNum; ++i) {
					progressOut.print(barChar);
				}
				
				progressOut.flush();
				last = barNum;
			}
		}
		
		protected void endProgress() {
			progressOut.println();
			progressOut.flush();
		}
	}
}
