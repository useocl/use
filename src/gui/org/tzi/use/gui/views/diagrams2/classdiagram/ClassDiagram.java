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

package org.tzi.use.gui.views.diagrams2.classdiagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

/**
 * Class diagram based on JGraphX library
 * @author lhamann
 *
 */
public class ClassDiagram extends JPanel implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9067598677231795448L;

	private MSystem system;
	
	private mxGraph graph;
	
	private mxGraphComponent graphComponent;
	
	public ClassDiagram(MSystem system) {
		this.system = system; 
		initGraph();
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		
		graphComponent = new mxGraphComponent(graph);
		graphComponent.getConnectionHandler().setEnabled(false);

		graphComponent.getViewport().setOpaque(false);
		graphComponent.setBackground(Color.WHITE);
		// Otherwise all user objects must be serializable
		graphComponent.setDragEnabled(false);
		graphComponent.getGraphHandler().setImagePreview(true);
		
		this.add(graphComponent);
	}

	private void initGraph() {
		double x = 30, y = 30;
		
		graph = new mxGraph();
		
		Map<MClass, Object> vertexMappings = new HashMap<MClass, Object>();
		
		graph.getModel().beginUpdate();
		try
		{
			for (MClass cls : system.model().classes()) {
				vertexMappings.put(cls, graph.insertVertex(null, cls.name(), cls, x, y, 100, 50, "strokeColor=black;fillColor=#fff8b4;fontColor=black"));
			}

			for (MAssociation ass : system.model().associations()) {
				String style = "strokeColor=black;fontColor=black;";
				MAssociationEnd aendSource = ass.associationEnds().get(0);
				MAssociationEnd aendTarget = ass.associationEnds().get(1);
				
				style += mxConstants.STYLE_STARTARROW + "=" + mxConstants.NONE + ";" +
						 mxConstants.STYLE_ENDARROW + "=" + mxConstants.NONE + ";";
								
				mxCell edge = (mxCell)graph.insertEdge(null, ass.name(), ass,
										vertexMappings.get(aendSource.cls()),
										vertexMappings.get(aendTarget.cls()),
										style);
				edge.getGeometry().setY(5);
			}
			
			Object cell = graph.getDefaultParent();
			mxIGraphLayout layout = new mxHierarchicalLayout(graph, JLabel.WEST);
			layout.execute(cell);
		} finally {
			graph.getModel().endUpdate();
		}
		
		graph.setCellsDeletable(false);
		graph.setCellsDisconnectable(false);
		graph.setCellsCloneable(false);
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.sys.StateChangeListener#stateChanged(org.tzi.use.uml.sys.StateChangeEvent)
	 */
	@Override
	public void stateChanged(StateChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.View#detachModel()
	 */
	@Override
	public void detachModel() {
		// TODO Auto-generated method stub
		
	}
}
