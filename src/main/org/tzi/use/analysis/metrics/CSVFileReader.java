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

package org.tzi.use.analysis.metrics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 * @author ms
 *
 */

public class CSVFileReader {

	private final String fileName;
	private ArrayList<List<String>> lines;

	/**
	 * 
	 */
	public CSVFileReader(String fileName) {
		this.fileName = fileName;
		try {
			parse();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parse() throws FileNotFoundException {
		BufferedReader csvBuffer = null;
		try {
			csvBuffer = new BufferedReader(new FileReader(fileName));

			String csvLine;
			while((csvLine = csvBuffer.readLine()) != null) {
				lines.add(Arrays.asList(csvLine.split("\\s*,\\s*")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (csvBuffer != null) csvBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<List<String>> getLines() {
		return lines;
	}
}