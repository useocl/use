/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

// $Id: ClassDiagramOptions.java 1050 2009-07-07 16:25:22Z lars $

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.nio.file.Path;

import org.tzi.use.gui.views.diagrams.DiagramOptions;

/**
 * Contains all optional settings for the class diagram.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class ClassDiagramOptions extends DiagramOptions {

	public enum ShowCoverage {
		DONT_SHOW,
		SHOW,
		SHOW_EXPAND_OPERATIONS;
		
		@Override
		public String toString() {
			switch (this.ordinal()) {
			case 0:
				return "Don't show";
			case 1:
				return "Show";
			case 2:
				return "Show (expand operations)";
			default:
				return "<unknown>";
			}
		}
	}
	
    public ClassDiagramOptions() {
    }
    
    protected ShowCoverage fShowCoverage = ShowCoverage.DONT_SHOW;
    
    /**
     * Copy constructor
     * @param source
     */
    public ClassDiagramOptions(ClassDiagramOptions source) {
        super(source);
        this.fShowMutliplicities = source.fShowMutliplicities;
        this.fShowCoverage = source.fShowCoverage;
    }
    
    @Override
    protected void registerAdditionalColors() {
       // No additional colors
    }
    
    public ClassDiagramOptions(Path modelFile) {
    	this();
    	this.modelFileName = modelFile;
    }
    
    public boolean isShowMutliplicities() {
        return fShowMutliplicities;
    }

    public void setShowMutliplicities( boolean showMutliplicities ) {
        fShowMutliplicities = showMutliplicities;
    }

    /**
	 * @param showCoverage
	 */
	public void setShowCoverage(ShowCoverage showCoverage) {
		fShowCoverage = showCoverage;
		onOptionChanged("SHOWCOVERAGE");
	}

	public ShowCoverage getShowCoverage() {
		return fShowCoverage;
	}
}
