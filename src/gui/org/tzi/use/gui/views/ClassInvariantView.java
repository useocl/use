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

package org.tzi.use.gui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.evalbrowser.ExprEvalBrowser;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.ClassInvariantChangedEvent;
import org.tzi.use.uml.sys.events.ClassInvariantsLoadedEvent;
import org.tzi.use.uml.sys.events.ClassInvariantsUnloadedEvent;
import org.tzi.use.uml.sys.events.tags.SystemStateChangedEvent;
import org.tzi.use.util.NullWriter;

import com.google.common.eventbus.Subscribe;

/**
 * A table showing invariants and their results.
 * 
 * @author Mark Richters
 * @author Frank Hilken
 */
@SuppressWarnings("serial")
public class ClassInvariantView extends JPanel implements View {
    
	private class EvalResult {
		public final int index;
		public final Value result;
		public final String message;
		public final long duration;
		
		public EvalResult(int index, Value result, String message, long duration) {
			this.index = index;
			this.result = result;
			this.message = message;
			this.duration = duration;
		}
	}
	
	private JTable fTable;
    private JLabel fLabel; // message at bottom of view
    private JProgressBar fProgressBar;

    private MSystem fSystem;
    private MModel fModel;
    
    private MClassInvariant[] fClassInvariants = new MClassInvariant[0];
    private EvalResult[] fValues;

    private ClassInvariantTableModel fMyTableModel;
    private int fSelectedRow = -1;
    private boolean fOpenEvalBrowserEnabled = false;
    private boolean showFlags = true;
    private boolean showDuration = false;
    
    private InvWorker worker = null;

    private ExecutorService executor = Executors.newFixedThreadPool(Options.EVAL_NUMTHREADS);

    private class ClassInvariantTableModel extends AbstractTableModel {
        private final String[] columnNames = { "Invariant", "Loaded", "Active", "Negate", "Satisfied", "Duration (ms)" };
        private final int[] columnWidths =   {  150,         50,       50,       50,       70,          70 };

        @Override
		public String getColumnName(int col) {
        	col = calcColumnByOptions(col);
            return columnNames[col];
        }

        public int getColumnWidth(int col){
        	col = calcColumnByOptions(col);
        	return columnWidths[col];
        }
        
        @Override
		public int getColumnCount() {
        	int res = 2;
        	if(showFlags){
        		res += 3;
        	}
        	if(showDuration){
        		res += 1;
        	}
            return res;
        }

        @Override
		public int getRowCount() {
            return fClassInvariants.length;
        }

        @Override
		public Object getValueAt(int row, int col) {
        	col = calcColumnByOptions(col);
        	EvalResult evalRes = fValues[row];
        	boolean hasResult = evalRes != null && fClassInvariants[row].isActive();
        	
        	if (col == 0) {
        		String out = showFlags ? fClassInvariants[row].qualifiedName() : fClassInvariants[row].qualifiedName() + createFlagString(fClassInvariants[row]);
        		if (hasResult) {
        			return out;
        		}
        		else {
        			return "<html><font color='gray'>" + out + "</font></html>";
        		}
        	} else if(col == 1){
        		return fClassInvariants[row].isLoaded();
        	} else if(col == 2){
        		return fClassInvariants[row].isActive();
        	} else if(col == 3){
        		return fClassInvariants[row].isNegated();
        	} else if (col == 4) {
        		if (hasResult) {
        			boolean valid = evalRes.result != null && evalRes.result.isBoolean() && ((BooleanValue)evalRes.result).value();
        			StringBuilder res = new StringBuilder();
        			res.append("<html><font color='");
        			
        			if (valid){
        				res.append("green");
        			} else {
        				res.append("red");
        			}
        			
        			res.append("'>");
        			
        			if (evalRes.result == null) {
        				res.append(evalRes.message);
        			} else {
        				res.append(evalRes.result.toString());
        			}
        			
        			res.append("</font></html>");
        			return res.toString();
        			
        		} else if(!fClassInvariants[row].isActive()){
        			return "<html><font color='gray'>inactive</font></html>";
        		} else {
        			return null;
        		}
            } else if( col == 5){
            	// duration
            	return evalRes != null ? evalRes.duration + " " : null;
            } else {
            	return null;
            }
        }
        
		private String createFlagString(MClassInvariant inv) {
			if(!inv.isActive()){
				return " (+d)";
			} else if(inv.isNegated()){
				return " (+n)";
			} else {
				return "";
			}
		}

		@Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        	columnIndex = calcColumnByOptions(columnIndex);
        	if(columnIndex == 2){
        		fSystem.setClassInvariantFlags(fClassInvariants[rowIndex], (Boolean)aValue, null);
        	} else if(columnIndex == 3){
        		fSystem.setClassInvariantFlags(fClassInvariants[rowIndex], null, (Boolean)aValue);
        	} else {
        		super.setValueAt(aValue, rowIndex, columnIndex);
        	}
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
        	columnIndex = calcColumnByOptions(columnIndex);
        	if(showFlags){
        		return (columnIndex == 2 || columnIndex == 3);
        	} else {
        		return false;
        	}
        }
        
        @Override
		public Class<?> getColumnClass(int c) {
        	c = calcColumnByOptions(c);
        	switch (c) {
			case 1:
			case 2:
			case 3:
				return Boolean.class;
			case 4:
				return Value.class;
			default:
				return Object.class;
			}
        }
        
        /**
		 * Given any currently visible column index, this method returns the
		 * index of the column in the static data model contained in the table
		 * model.
		 */
        private int calcColumnByOptions(int c){
        	int res = c;
        	if(c > 0 && !showFlags){
        		res += 3;
        	}
        	return res;
        }
        
        
    }
    
    public ClassInvariantView(final MainWindow parent, MSystem system) {
        fSystem = system;
        fModel = fSystem.model();
        fSystem.getEventBus().register(this);

        JPopupMenu popupMenu = createPopupMenu();
        
        setLayout(new BorderLayout());         
        fMyTableModel = new ClassInvariantTableModel();
        fTable = new JTable();
        fTable.setModel(fMyTableModel);
        fTable.setComponentPopupMenu(popupMenu);
        JScrollPane scrollPane = new JScrollPane(fTable);
        scrollPane.setComponentPopupMenu(popupMenu);
        add(scrollPane, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        fLabel = new JLabel();
        fLabel.setForeground(Color.black);
        bottomPanel.add(fLabel, BorderLayout.CENTER);
        fProgressBar = new JProgressBar();
        fProgressBar.setPreferredSize(new Dimension(50, 17));
        fProgressBar.setStringPainted(true);
        bottomPanel.add(fProgressBar, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // track selections
        fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = fTable.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            @Override
			public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    fSelectedRow = -1;
                } else {
                    fSelectedRow = lsm.getMinSelectionIndex();
                }
            }
        });

        // double click on table opens an ExprEvalBrowser on the
        // selected invariant
        setOpenEvalBrowserEnabled(false);
        fTable.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 
                	&& fSelectedRow >= 0 && fOpenEvalBrowserEnabled) {
                    
                	if(!fClassInvariants[fSelectedRow].isActive()){
                		return;
                	}
                	
            		Expression expr = fClassInvariants[fSelectedRow]
            				.flaggedExpression();
            		Evaluator evaluator = new Evaluator(true);
            		try {
            			evaluator.eval(expr, fSystem.state());
            		} catch (MultiplicityViolationException ex) {
            			return;
            		}

                    ExprEvalBrowser.createPlus(evaluator
                            .getEvalNodeRoot(), fSystem, fClassInvariants[fSelectedRow]);
                }
            }
        });
        
        updateTableStructure();
        init();
        update();
    }

	private JPopupMenu createPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		
		JCheckBoxMenuItem flagModeItem = new JCheckBoxMenuItem("Show invariant flags");
		flagModeItem.setSelected(showFlags);
		flagModeItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				showFlags = e.getStateChange() == ItemEvent.SELECTED;
				updateTableStructure();
			}
		});
		menu.add(flagModeItem);
		
		JCheckBoxMenuItem showDurationItem = new JCheckBoxMenuItem("Show evaluation time");
		showDurationItem.setSelected(showDuration);
		showDurationItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				showDuration = e.getStateChange() == ItemEvent.SELECTED;
				updateTableStructure();
			}
		});
		menu.add(showDurationItem);
		
		return menu;
	}

	private void init() {
		int n = fModel.classInvariants().size();

		// initialize array of class invariants
		fClassInvariants = new MClassInvariant[n];
		System.arraycopy(fModel.classInvariants().toArray(), 0,
				fClassInvariants, 0, n);
		Arrays.sort(fClassInvariants);

		// initialize value array to undefined values
		fValues = new EvalResult[n];
		clearValues();
		
		fProgressBar.setMinimum(0);
		fProgressBar.setMaximum(n);
	}

	private void clearValues() {
        for (int i = 0; i < fValues.length; i++)
            fValues[i] = null;
    }

    private void setOpenEvalBrowserEnabled(boolean on) {
        if (on) {
            fTable.setToolTipText("Double click to open evaluation browser");
        } else {
            fTable.setToolTipText(null);
        }
        fOpenEvalBrowserEnabled = on;
    }

    @Subscribe
    public void onClassInvariantLoading(ClassInvariantsLoadedEvent ev){
    	init();
    	update();
    }
    
   	@Subscribe
    public void onClassInvariantUnloading(ClassInvariantsUnloadedEvent ev){
   		init();
   		update();
   	}
   	
    @Subscribe
    public void onClassInvariantStateChange(ClassInvariantChangedEvent ev){
    	update();
    }
    
    /**
	 * Listen to any event that changes the system state to update all
	 * invariants.
	 */
    @Subscribe
    public void onSystemStateChanged(SystemStateChangedEvent e) {
    	update();
    }
    
    private synchronized void update() { 
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	
    	if (worker != null) {
    		if(!worker.isDone()) {	
    			worker.cancel(false);
    		}
    	}

    	worker = new InvWorker();
    	worker.execute();
    }
    
    /**
     * Updates the table when its structure (columns) change.
     */
    private void updateTableStructure() {
    	fMyTableModel.fireTableStructureChanged();
		for (int i = 0; i < fTable.getColumnModel().getColumnCount(); i++) {
			fTable.getColumnModel().getColumn(i).setPreferredWidth(fMyTableModel.getColumnWidth(i));
		}
		fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));

		int satisifedCol = 1;
		if (showFlags) {
			satisifedCol += 3;
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		fTable.getColumnModel().getColumn(satisifedCol).setCellRenderer(centerRenderer);

		if (showDuration) {
			int durationCol = 2;
			
			if (showFlags) {
				durationCol += 3;
			}
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
			fTable.getColumnModel().getColumn(durationCol).setCellRenderer(rightRenderer);
		}
	}
    
    /**
     * Detaches the view from its model.
     */
    @Override
	public void detachModel() {
        fSystem.getEventBus().unregister(this);
        if(!worker.isDone()){
        	worker.cancel(false);
        }
        executor.shutdown();
    }
    
    private class InvWorker extends SwingWorker<Void,Integer> {

    	private String labelText;
    	
    	int numFailures = 0;
    	int progress = 0;
    	int progressEnd = 0;
    	long duration = 0;
    	boolean structureOK = true;
    	
    	// determines if the MultiplicityViolation Label should be shown
    	boolean violationLabel = false; 
    	
    	MSystemState systemState;
    	
    	protected synchronized int incrementProgress() {
    		return ++progress;
    	}
    	
    	public InvWorker() { 
    		systemState = fSystem.state();
    	}
    	
		@Override
		protected Void doInBackground() throws Exception {
			long start = System.currentTimeMillis();

			progressEnd = fClassInvariants.length;
			clearValues();
        
			// check invariants
			if (Options.EVAL_NUMTHREADS > 1)
				labelText = "Working (using " + Options.EVAL_NUMTHREADS + " concurrent threads)...";
			else
				labelText = "Working...";

			publish(0);

            List<Future<EvalResult>> futures = new ArrayList<Future<EvalResult>>();
            ExecutorCompletionService<EvalResult> ecs = new ExecutorCompletionService<EvalResult>(executor);
            
            for (int i = 0; i < fClassInvariants.length; i++) {
            	if(!fClassInvariants[i].isActive()){
            		continue;
            	}
        		MyEvaluatorCallable cb = new MyEvaluatorCallable(systemState, i, fClassInvariants[i]);
        		futures.add(ecs.submit(cb));
            }
            
            for (int i = 0; i < fClassInvariants.length && !isCancelled(); i++) {
            	if(!fClassInvariants[i].isActive()){
            		continue;
            	}
                try {
                	EvalResult res = ecs.take().get();
                    fValues[res.index] = res;
                    publish(incrementProgress());
                    
                    boolean ok = false;
                    // if v == null it is not considered as a failure, rather it is
                    // a MultiplicityViolation and it is skipped as failure count
                    boolean skip = false;
                    if (res.result != null) {
                        ok = res.result.isDefined() && ((BooleanValue)res.result).isTrue();
                    } else {
                        violationLabel = true;
                        skip = true;
                    }

                    if (!skip && !ok)
                        numFailures++;
                    
                } catch (InterruptedException ex) {
                	break;
                }
            }
            
            for (Future<EvalResult> f : futures) {
            	f.cancel(true);
            }

            structureOK = systemState.checkStructure(new PrintWriter(new NullWriter()), false);
            
            duration = System.currentTimeMillis() - start;
            return null;
        }

		@Override
		protected void process(List<Integer> chunks) {
			int lastProgress = chunks.get(chunks.size() - 1);
			
			fLabel.setForeground(Color.black);
			fLabel.setText(labelText);
			
			fProgressBar.setMaximum(progressEnd);
			fProgressBar.setValue(lastProgress);
			fMyTableModel.fireTableDataChanged();

			repaint();
		}

		@Override
		protected void done() {
			setOpenEvalBrowserEnabled(true);

			String text;
			
            // show summary of results
            if (numFailures == 0) {
                if (violationLabel) {
                    fLabel.setForeground(Color.red);
                    text = "Model inherent constraints violated (see Log for details).";
                } else {
                    fLabel.setForeground(Color.black);
                    text = "Constrs. OK.";
                }
            } else {
                fLabel.setForeground(Color.red);
                text = numFailures + " constraint" + ((numFailures > 1) ? "s" : "") + " failed.";
            }
            
            if(numFailures == 0 && structureOK && !violationLabel){
            	fLabel.setForeground(Color.black);
            	text = "Cnstrs. OK.";
            } else if (numFailures == 0 && violationLabel){
            	fLabel.setForeground(Color.red);
            	text = "Model inherent constraints violated (see Log for details).";
            } else if(numFailures == 0 && !structureOK){
            	fLabel.setForeground(Color.red);
            	text = "Explicit cnstrs. OK. Inherent cnstrs. failed.";
            } else {
            	fLabel.setForeground(Color.red);
            	text = numFailures + " cnstr" + ((numFailures > 1) ? "s" : "") + ". failed.";
            	text = text + " Inherent cnstrs. " + (structureOK?"OK":"failed") + ".";
            }
            
            text = text + String.format(" (%,dms)", duration);
            
            fLabel.setText(text);
            fProgressBar.setMaximum(1);
			fProgressBar.setValue(1);
			fMyTableModel.fireTableDataChanged();
			
            setCursor(Cursor.getDefaultCursor());
		}
		
		private class MyEvaluatorCallable implements Callable<EvalResult> {
	    	final int index;
	    	final MSystemState state;
	    	final MClassInvariant inv;
	    	
	    	public MyEvaluatorCallable(MSystemState state, int index, MClassInvariant inv) {
	    		this.state = state;
	    		this.index = index;
	    		this.inv = inv;
	    	}

			@Override
			public EvalResult call() throws Exception {
				if (isCancelled()) return null;
				
				Evaluator eval = new Evaluator();
				Value v = null;
				String message = null;
				long start = System.currentTimeMillis();
				
				try {
					v = eval.eval(inv.flaggedExpression(), state);
				} catch (MultiplicityViolationException e) {
					message = e.getMessage();
				}
				
				return new EvalResult(index, v, message, System.currentTimeMillis() - start);
			}
	    }
    }
}
