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
 * 
 */

package org.tzi.use.gui.views.diagrams.elements;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.tzi.use.gui.views.diagrams.util.Util;

/**
 * A node just for comments.
 * 
 * @author Subi Aili
 */
public class CommentNode extends PlaceableNode {

	private JComponent parent;
	
	private String commentText = "";
	private JTextArea commentArea;
	private JFrame commentFrame;
	private JPanel commentPanel;
	private static final int MINHEIGHT = 19;
	private static final int MINWIDTH = 40;
	private static final int DEFAULT_WIDTH = 158;
	private static final int DEFAULT_HEIGHT = 47;
	private static final int DOGEAR_SIZE = 8;
	private static final int NODE_BORDER_THICKNESS = 1;
	private static final int COMMENTNODE_STRING_DISTANCE = 8;
	private int commentNodeX;
	private int commentNodeY;
	private int commentNodeWidth;
	private int commentNodeHeight;
	
	private static int id;
	private int commentNodeId;
	
	public CommentNode(double pX, double pY, JComponent parent) {
		commentNodeId = id++;
		setPosition(pX, pY);
		this.parent = parent;
		setCalculatedBounds(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinHeight(MINHEIGHT);
		setMinWidth(MINWIDTH);
	}
	
	@Override
	public boolean isResizable(){ 
		return true;
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	protected void onDraw(Graphics2D g) {
		Rectangle2D.Double currentBounds = getRoundedBounds();
				
		if (g.getClipBounds() != null && 
	    	    !Util.enlargeRectangle(currentBounds, 10).intersects(g.getClipBounds())) {
	    		return;
	    }
		
		// cache old color and thickness settings
		Color oldColor = g.getColor(); 
		Stroke oldStroke = g.getStroke();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(NODE_BORDER_THICKNESS));
		// drawing the CommentNode
		commentNodeX = (int) currentBounds.x;
		commentNodeY = (int) currentBounds.y;
		commentNodeWidth = (int) currentBounds.width;
		commentNodeHeight = (int) currentBounds.height;
		g.drawLine(commentNodeX, commentNodeY, commentNodeX+commentNodeWidth-DOGEAR_SIZE, commentNodeY);
		g.drawLine(commentNodeX, commentNodeY, commentNodeX, commentNodeY+commentNodeHeight);
		g.drawLine(commentNodeX, commentNodeY+commentNodeHeight, commentNodeX+commentNodeWidth, commentNodeY+commentNodeHeight);
		g.drawLine(commentNodeX+commentNodeWidth, commentNodeY+commentNodeHeight, commentNodeX+commentNodeWidth, commentNodeY+DOGEAR_SIZE);
		g.drawLine(commentNodeX+commentNodeWidth-DOGEAR_SIZE, commentNodeY, commentNodeX+commentNodeWidth, commentNodeY+DOGEAR_SIZE);
		g.drawLine(commentNodeX+commentNodeWidth-DOGEAR_SIZE, commentNodeY, commentNodeX+commentNodeWidth-DOGEAR_SIZE, commentNodeY+DOGEAR_SIZE);
		g.drawLine(commentNodeX+commentNodeWidth-DOGEAR_SIZE, commentNodeY+DOGEAR_SIZE, commentNodeX+commentNodeWidth, commentNodeY+DOGEAR_SIZE);
		
		drawCommentNodeString(g,commentText, commentNodeX+COMMENTNODE_STRING_DISTANCE, commentNodeY);

		// restore old color and thickness settings
		g.setColor(oldColor);
		g.setStroke(oldStroke);
	}
	
	public void drawCommentNodeString(Graphics2D g2d, String text, int x, int y) {
		FontMetrics fm = g2d.getFontMetrics();
		String[] lines = text.split("\n");
		int totalLines = commentNodeHeight / fm.getHeight();
		int actualLines;
		
		int textMaxWidth = commentNodeWidth - (2*COMMENTNODE_STRING_DISTANCE);
		
		if(lines.length <= totalLines) {
			actualLines = lines.length;
		} else {
			actualLines = totalLines - 1;
		}

		for (int i = 0; i < actualLines; i++) {
			String currentLine = lines[i];
			int currentLinePixelLength = SwingUtilities.computeStringWidth(fm, currentLine);
			if(currentLinePixelLength+10 > textMaxWidth){
				String ellipsis = "...";
				int currentStringPixelLength = SwingUtilities.computeStringWidth(fm, ellipsis);
				int currentStringCharCount = 0;
				int totalStringCharCount = currentLine.length();
				while(currentStringCharCount < totalStringCharCount && (currentStringPixelLength += fm.charWidth(currentLine.charAt(currentStringCharCount))) < textMaxWidth){
					currentStringCharCount++;
				}
				currentLine = currentLine.substring(0, currentStringCharCount)+ ellipsis;
			}
			g2d.drawString(currentLine, x, y += g2d.getFontMetrics().getHeight());
		}
		
		if(actualLines < lines.length) {			
			g2d.drawString("...", x, y += fm.getHeight());
		}
	}
	
	public void setEditable() {
		commentFrame = new JFrame();
		commentFrame.setUndecorated(true);
		commentPanel = new JPanel(new BorderLayout());
		
		commentArea = new JTextArea(commentText);
		commentArea.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent e) {
				commentText = commentArea.getText();
				commentFrame.dispose();
			}
		});
		commentArea.setLineWrap(false);
		commentArea.setWrapStyleWord(true);
		
		Point p = new Point(commentNodeX+NODE_BORDER_THICKNESS, commentNodeY+NODE_BORDER_THICKNESS);
        SwingUtilities.convertPointToScreen(p, parent);
        commentPanel.setPreferredSize(new Dimension(commentNodeWidth-NODE_BORDER_THICKNESS,commentNodeHeight-NODE_BORDER_THICKNESS));
		commentPanel.add(commentArea);
		commentFrame.setContentPane(commentPanel);
		commentFrame.setLocation(p);
		commentFrame.pack();
		commentFrame.setVisible(true);
        commentArea.selectAll();
        commentArea.setCaretPosition(commentArea.getDocument().getLength());
        commentArea.requestFocus();
	}

	@Override
	protected void doCalculateSize(Graphics2D g) {
	}

	@Override
	public String name() {
		return "CommentNode"+commentNodeId;
	}

	@Override
	protected String getStoreType() {
		return "Comment";
	}

}
