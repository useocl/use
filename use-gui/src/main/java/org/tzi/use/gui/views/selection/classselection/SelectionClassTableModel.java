package org.tzi.use.gui.views.selection.classselection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;

import org.tzi.use.gui.util.Selection.ChangeEvent;
import org.tzi.use.gui.util.Selection.ChangeListener;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramData;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.uml.mm.MClassifier;

/** 
 * This table model contains visible classes of a class diagram
 * and the selection state of the class nodes.
 * @author   Jun Zhang 
 * @author   Jie Xu
 * @author   Lars Hamann
 */
@SuppressWarnings("serial")
public class SelectionClassTableModel extends AbstractTableModel implements ChangeListener {
			
	private ClassDiagram classDiagram;
	    
    public static class Row {
    	public Row(MClassifier classifier, boolean selected, boolean visible) {
    		this.classifier = classifier;
    		this.selected = selected;
    		this.visible = visible;
    	}
    	
    	public MClassifier classifier;
    	
    	public boolean visible;
    	
    	public boolean selected;
    }
    
    private List<Row> rows = new ArrayList<Row>();
    
	/**
	 * Constructor for SelectionClassTableModel
	 */
	public SelectionClassTableModel( ClassDiagram classDiagram) {
		this.classDiagram = classDiagram;
		this.classDiagram.getNodeSelection().addChangeListener(this);
		update();
	}
	
	/**
	 * Method getColumnClass determine the default renderer/ editor for
	 * each cell. 
	 */
	public Class<?> getColumnClass(int c) {
		switch (c) {
		case 0:
			return String.class;
		default:
			return Boolean.class;
		}
	}
	
	/**
	 * Method setValueAt takes the user input.
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {
		if (col != 1) return;
		
		Boolean v = (Boolean)value;
		Row r = rows.get(row);
		r.selected = v;
		
		changeSelection(r.classifier, v.booleanValue());
		
		fireTableRowsUpdated(row, row);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0:
			return "class";
		case 1:
			return "select";
		}
		
		return "";
	}

	public void changeSelection(MClassifier cf, boolean isAdd) {
		classDiagram.getNodeSelection().removeChangeListener(this);
		
		PlaceableNode node = ((ClassDiagramData)classDiagram.getVisibleData()).getNode(cf);
        
		if ( node != null ) {
			if ( classDiagram.getNodeSelection().isSelected( node ) ){
            	if(!isAdd)
            		classDiagram.getNodeSelection().remove( node );
            } else {
            	if(isAdd)
            		classDiagram.getNodeSelection().add( node );
            }
		}
	    
		classDiagram.getNodeSelection().addChangeListener(this);
		classDiagram.invalidateContent(true);
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		ClassDiagramData visibleData = (ClassDiagramData)classDiagram.getVisibleData();
		
		if (rows.size() == 0) {
			// initialize table model
			TreeSet<MClassifier> sortedNodes = new TreeSet<MClassifier>(new Comparator<MClassifier>() {
				@Override
				public int compare(MClassifier o1, MClassifier o2) {
					return o1.name().compareToIgnoreCase(o2.name());
				}
			});
			
			sortedNodes.addAll(classDiagram.getSystem().model().classes());
			sortedNodes.addAll(classDiagram.getSystem().model().enumTypes());
		
			for(MClassifier cf : sortedNodes) {
				PlaceableNode node = visibleData.getNode(cf);
				boolean selected = classDiagram.getNodeSelection().isSelected(node); 
				boolean visible = classDiagram.isVisible(cf);
				
				rows.add(new Row(cf, selected, visible));
			}
		} else {
			for (Row r : rows) {
				r.visible = visibleData.containsNodeForClassifer(r.classifier);
				if (r.visible) {
					r.selected = classDiagram.getNodeSelection().isSelected(visibleData.getNode(r.classifier));
				}
			}
		}
		
		this.fireTableDataChanged();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return rows.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Row r = rows.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return r.classifier.name();
		case 1:
			return r.selected;
		}
		
		return null;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		update();		
	}
	
	public void dispose() {
		this.classDiagram.getNodeSelection().removeChangeListener(this);
	}

	/**
	 * @return
	 */
	public Set<MClassifier> getSelectedClassifier() {
		Set<MClassifier> result = new HashSet<MClassifier>();
		for (Row r : rows) {
			if (r.selected) {
				result.add(r.classifier);
			}
		}
		
		return result;
	}

	/**
	 * Sets all values of selected to true
	 */
	public void selectAll() {
		classDiagram.getNodeSelection().removeChangeListener(this);
		
		for (Row r : rows) {
			r.selected = true;
			changeSelection(r.classifier, true);
		}
		
		classDiagram.getNodeSelection().addChangeListener(this);
		this.fireTableDataChanged();
	}

	/**
	 * Set all values of selected to false
	 */
	public void deselectAll() {
		classDiagram.getNodeSelection().removeChangeListener(this);
		
		for (Row r : rows) {
			r.selected = false;
			changeSelection(r.classifier, false);
		}
		
		classDiagram.getNodeSelection().addChangeListener(this);
		this.fireTableDataChanged();		
	}

	/**
	 * @return
	 */
	public List<Row> getRows() {
		return rows;
	}
}
