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

package org.tzi.use.main.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for command line operations.
 * 
 * @author Frank Hilken
 */
public final class ShellUtil {
	
	private ShellUtil(){
	}

	/**
	 * Transforms a command argument string into an array of arguments. Single
	 * quoted, double quoted and arguments without quotes are handled.
	 */
	public static String[] parseArgumentList(String arguments) {
		arguments = arguments.trim();
		List<String> argList = new ArrayList<String>();
		Pattern p = Pattern.compile("([\"'])(.+?)\\1");
		Matcher m = p.matcher(arguments);
		int cursor = 0;
		
		while(cursor < arguments.length()){
			if(m.find(cursor) && m.start() == cursor){
				argList.add(m.group(2));
				cursor = nextNonSpacePosition(arguments, cursor + m.group(0).length());
			} else {
				int nextSpace = arguments.indexOf(" ", cursor);
				if(nextSpace == -1){
					argList.add(arguments.substring(cursor));
					break;
				}
				argList.add(arguments.substring(cursor, nextSpace));
				cursor = nextNonSpacePosition(arguments, nextSpace);
			}
		}
		
		return argList.toArray(new String[argList.size()]);
	}

	/**
	 * Returns the next non-space character as defined by POSIX {@code \s} from
	 * the given start position in the given {@code String}.
	 */
	private static int nextNonSpacePosition(String str, int idx){
		Matcher m = Pattern.compile("[^\\s]").matcher(str);
		if(m.find(idx)){
			return m.start();
		} else {
			return str.length();
		}
	}
	
}
