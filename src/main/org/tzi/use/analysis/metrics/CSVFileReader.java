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

import jline.internal.Log;

/**
 * TODO
 * @author ms
 *
 */

public class CSVFileReader {

	private final String tailPath;
	private ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
	
	/**
	 * 
	 */
	public CSVFileReader(String tailPath) {
		this.tailPath = tailPath;
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
			
			Log.info();
			Log.info("Loading" + " " + path());
			Log.info();
			
			csvBuffer = new BufferedReader(new FileReader(path()));

			String csvLine;
			while((csvLine = csvBuffer.readLine()) != null) {
				
				// skip comment lines starting with #
				if(csvLine.startsWith("#")) continue;
				
				String[] splits = csvLine.split("\\s*,\\s*");
				ArrayList<String> result = new ArrayList<String>(Arrays.asList(splits));
				lines.add(result);
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
	
	private String path() {
		return System.getProperty("user.dir") + "/" + tailPath + ".csv";
	}
	
	public ArrayList<ArrayList<String>> getLines() {
		return lines;
	}
}