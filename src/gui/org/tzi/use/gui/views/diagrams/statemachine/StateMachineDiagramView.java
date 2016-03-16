/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.gui.views.diagrams.statemachine;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.TransitionEvent;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;

import com.google.common.eventbus.Subscribe;

/**
 * @author Lars Hamann
 *
 */
@SuppressWarnings("serial")
public class StateMachineDiagramView extends JPanel implements View, PrintableView {

	protected MSystem system;
	
	protected final MStateMachine stateMachine;
	
	protected MObject monitoredInstance;
	
	protected final MainWindow parent;
		
	protected StateMachineDiagram diagram;
	
	/**
	 * @param mainWindow
	 * @param system
	 * @param sm
	 */
	public StateMachineDiagramView(MainWindow mainWindow, MSystem system, MStateMachine sm) {
		this.system = system;
		this.stateMachine = sm;
		this.parent = mainWindow;
		
		this.setFocusable(true);
        
        setLayout( new BorderLayout() );
        initDiagram(true, null);
	}

	//TODO: To parent class
	public void initDiagram(boolean loadDefaultLayout, StateMachineDiagramOptions opt) {
		if (opt == null)
			diagram = new StateMachineDiagram( this, parent.logWriter() );
		else
			diagram = new StateMachineDiagram( this, parent.logWriter(), new StateMachineDiagramOptions(opt));
		
		diagram.setStatusBar(parent.statusBar());
		
		this.removeAll();
        add( new JScrollPane(diagram) );
        initState();
        setDiagramCaption();
        
        if (loadDefaultLayout) {
        	diagram.loadDefaultLayout();
        }
        
        getSystem().getEventBus().register(this);
	}
	
	/**
	 * 
	 */
	private void setDiagramCaption() {
		StringBuilder caption = new StringBuilder();
		caption.append(this.stateMachine.getContext().name());
		caption.append("::");
		caption.append(this.stateMachine.name());
		
		if (this.monitoredInstance != null) {
			caption.append(" for ");
			caption.append(this.monitoredInstance.name());
		}
		
		diagram.setCaption(caption.toString());
	}

	/**
	 * 
	 */
	private void initState() {
		diagram.setStateMachine(stateMachine);
		diagram.initialize();
	}

	public void setMonitoredInstance(MObject instance) {
		if (!instance.cls().getAllOwnedProtocolStateMachines().contains(this.stateMachine)) {
			throw new IllegalArgumentException("Invalid instance to monitor!");
		}
		
		this.monitoredInstance = instance;
		
		setDiagramCaption();
		highlightCurrentState();
	}
	
	/**
	 * 
	 */
	private void highlightCurrentState() {
		MObjectState currentState = monitoredInstance.state(system.state()); 
		MProtocolStateMachineInstance psmInstance = currentState.getProtocolStateMachineInstance(stateMachine);
		
		diagram.setActiveState(psmInstance.getCurrentState(stateMachine.getDefaultRegion()));
		
		diagram.repaint();
	}

	@Override
	public void printView(PageFormat pf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void export( Graphics2D g ) {
    	JComponent toExport = diagram;
        
    	boolean oldDb = toExport.isDoubleBuffered();
    	toExport.setDoubleBuffered(false);
    	toExport.paint(g);
    	toExport.setDoubleBuffered(oldDb);
    }

	@Override
	public void detachModel() {
		getSystem().getEventBus().unregister(this);
	}

	@Subscribe
	public void onTransition(TransitionEvent e) {
		if (e.getSource().equals(this.monitoredInstance) && 
				e.getStateMachine().equals(stateMachine)) {
			
			this.highlightCurrentState();
		}
	}

	/**
	 * @return the system
	 */
	public MSystem getSystem() {
		return system;
	}
	
	@Override
	public float getContentHeight() {
		return diagram.getPreferredSize().height;
	}

	@Override
	public float getContentWidth() {
		return diagram.getPreferredSize().width;
	}
}
