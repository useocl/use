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

// $Id$

package org.tzi.use.gui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.MMHTMLPrintVisitor;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StateChangeEvent;

/**
 * A table showing invariants and their results.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
@SuppressWarnings("serial")
public class ClassInvariantView extends JPanel implements View {
    private JTable fTable;

    private JLabel fLabel; // message at bottom of view

    private JProgressBar fProgressBar;

    private MSystem fSystem;

    private MModel fModel;

    private MClassInvariant[] fClassInvariants;

    private Value[] fValues;

    private MyTableModel fMyTableModel;

    private int fSelectedRow = -1;

    private boolean fOpenEvalBrowserEnabled = false;

    private volatile InvWorker worker = null;

    /**
     * The table model.
     */
    class MyTableModel extends AbstractTableModel {
        final String[] columnNames = { "Invariant", "Result" };

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getColumnCount() {
            return 2;
        }

        public int getRowCount() {
            return fClassInvariants.length;
        }

        public Object getValueAt(int row, int col) {
            if (col == 0)
                return fClassInvariants[row];
            else
                return fValues[row];
        }

        public Class<?> getColumnClass(int c) {
            if (c == 1)
                return Value.class;
            else
                return Object.class;
        }
    }

    /**
     * Renderer for (boolean) values. Uses different colors for different
     * values.
     */
    class ValueRenderer extends JLabel implements TableCellRenderer {
        final Color colorTrue = new Color(0, 0x80, 0);

        final Color colorFalse = new Color(0xc0, 0, 0);

        final Color colorUndefined = Color.gray;

        public Component getTableCellRendererComponent(JTable table,
                Object obj, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Color c = colorUndefined;
            if (obj == null)
                setText("n/a");
            else {
                setText(obj.toString());
                if (obj instanceof BooleanValue) {
                    boolean b = ((BooleanValue) obj).value();
                    c = (b) ? colorTrue : colorFalse;
                }
            }
            this.setForeground(c);
            return this;
        }
    }

    public ClassInvariantView(MainWindow parent, MSystem system) {
        
        fSystem = system;
        fModel = fSystem.model();
        fSystem.addChangeListener(this);

        int n = fModel.classInvariants().size();

        // initialize array of class invariants
        fClassInvariants = new MClassInvariant[n];
        System.arraycopy(fModel.classInvariants().toArray(), 0,
                fClassInvariants, 0, n);
        Arrays.sort(fClassInvariants);

        // initialize value array to undefined values
        fValues = new Value[n];
        clearValues();

        setLayout(new BorderLayout());
        fMyTableModel = new MyTableModel();
        fTable = new JTable();
        fTable.setModel(fMyTableModel);
        add(new JScrollPane(fTable), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        fLabel = new JLabel();
        fLabel.setForeground(Color.black);
        bottomPanel.add(fLabel, BorderLayout.CENTER);
        fProgressBar = new JProgressBar(0, n);
        fProgressBar.setStringPainted(true);
        bottomPanel.add(fProgressBar, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
        fTable.setDefaultRenderer(Value.class, new ValueRenderer());

        // track selections
        fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = fTable.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
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
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && fSelectedRow >= 0
                        && fOpenEvalBrowserEnabled) {
                    
                    Expression expr = fClassInvariants[fSelectedRow]
                            .expandedExpression();
                    Evaluator evaluator = new Evaluator(true);
                    try {
                        evaluator.eval(expr, fSystem.state());
                    } catch (MultiplicityViolationException ex) {
                        return;
                    }
                    // get the invariant as html string
                    StringWriter sw = new StringWriter();
                    sw.write("<html>");
                    
                    MMVisitor v = new MMHTMLPrintVisitor(new PrintWriter(sw));
                    fClassInvariants[fSelectedRow].processWithVisitor(v);
                    sw.write("</html>");
                    String htmlSpec = sw.toString();
                    
                    // get the invariant as normal string
                    sw = new StringWriter();
                    v = new MMPrintVisitor(new PrintWriter(sw));
                    fClassInvariants[fSelectedRow].processWithVisitor(v);
                    String spec = sw.toString();
                    
                    ExprEvalBrowser.createPlus(evaluator
                            .getEvalNodeRoot(), fSystem, spec, htmlSpec);
                }
            }
        });
        
        update();
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

    public void stateChanged(StateChangeEvent e) {
    	update();
    }

    private synchronized void update() { 
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	
    	if (worker != null && !worker.isDone()) {
    		worker.shutdown();
    		worker.cancel(true);
    	}
    	
    	worker = new InvWorker();
    	worker.execute();
    }
    
    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.removeChangeListener(this);
    }
    
    private class InvWorker extends SwingWorker<Boolean,Integer> {
    	
    	private final ExecutorService executor;
    	
    	private String labelText;
    	
    	int numFailures = 0;
    	
    	int progress = 0;
    	
    	int progressEnd = 0;
    	
    	long duration = 0;
    	
    	// determines if the MultiplicityViolation Label should be shown
    	boolean violationLabel = false; 
    	
    	protected synchronized int incrementProgress() {
    		++progress;
    		return progress;
    	}
    	
    	public InvWorker() {
    		executor = Executors.newFixedThreadPool(Options.EVAL_NUMTHREADS);
    	}
    	
    	/* (non-Javadoc)
		 * @see javax.swing.SwingWorker#doInBackground()
		 */
		@Override
		protected Boolean doInBackground() throws Exception {
			long start = System.currentTimeMillis();
			
			MSystemState systemState = fSystem.state();
				
			progressEnd = fClassInvariants.length;
			clearValues();
        
			// check invariants
			if (Options.EVAL_NUMTHREADS > 1)
				labelText = "Working (using " + Options.EVAL_NUMTHREADS + " concurrent threads)...";
			else
				labelText = "Working...";

			publish(2);
        		
            List<MyEvaluatorCallable> tasks = new ArrayList<MyEvaluatorCallable>(fClassInvariants.length);
            
            for (int i = 0; i < fClassInvariants.length; i++) {
            	MyEvaluatorCallable cb = new MyEvaluatorCallable(systemState, i,fClassInvariants[i]);
                tasks.add(cb);
            }
            
            List<Future<Value>> results;
            try {
            	results = executor.invokeAll(tasks);
            } catch (InterruptedException e) {
            	return null;
            }
            
            for (int i = 0; i < fClassInvariants.length; i++) {
                try {
                	Future<Value> nextFuture = results.get(i);
                	
                	// Here we wait for the result of task n
                    Value v = nextFuture.get();
                    
                    boolean ok = false;
                    // if v == null it is not considered as a failure, rather it is
                    // a MultiplicityViolation and it is skipped as failure count
                    boolean skip = false;
                    if (v != null) {
                        ok = v.isDefined() && ((BooleanValue) v).isTrue();
                    } else {
                        violationLabel = true;
                        skip = true;
                    }

                    if (!skip && !ok)
                        numFailures++;
                    
                } catch (InterruptedException ex) {
                    // OK
                }
            }
            
            duration = System.currentTimeMillis() - start;
            
            executor.shutdown();
            executor.notifyAll();
            
            return null;
        }

		/**
		 * 
		 */
		public void shutdown() {
			executor.shutdown(); // Disable new tasks from being submitted
			try {
				// Wait a while for existing tasks to terminate
				if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
					executor.shutdownNow(); // Cancel currently executing tasks
					// Wait a while for tasks to respond to being cancelled
					if (!executor.awaitTermination(2, TimeUnit.SECONDS))
						System.err.println("Pool did not terminate");
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				executor.shutdownNow();
				// Preserve interrupt status
				Thread.currentThread().interrupt();
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.SwingWorker#process(java.util.List)
		 */
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

		/* (non-Javadoc)
		 * @see javax.swing.SwingWorker#done()
		 */
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
                    text = "Constraints ok.";
                }
            } else {
                fLabel.setForeground(Color.red);
                text = numFailures + " constraint" + ((numFailures > 1) ? "s" : "") + " failed.";
            }
            
            text = text + String.format(" (%,dms)", duration);
            
            fLabel.setText(text);
            fProgressBar.setMaximum(1);
			fProgressBar.setValue(1);
			fMyTableModel.fireTableDataChanged();
			
            setCursor(Cursor.getDefaultCursor());
		}
		
		private class MyEvaluatorCallable implements Callable<Value> {
	    	final int index;
	    	final MSystemState state;
	    	final MClassInvariant inv;
	    	
	    	public MyEvaluatorCallable(MSystemState state, int index, MClassInvariant inv) {
	    		this.state = state;
	    		this.index = index;
	    		this.inv = inv;
	    	}

			/* (non-Javadoc)
			 * @see org.tzi.use.uml.ocl.expr.EvaluatorCallable#call()
			 */
			@Override
			public Value call() throws Exception {
				Evaluator eval = new Evaluator();
				Value v = null;
				
				try {
					v = eval.eval(inv.expandedExpression(), state);
				} catch (MultiplicityViolationException e) { }
				
				fValues[index] = v;
				publish(incrementProgress());
				return v;
			}
	    }
    }
}
