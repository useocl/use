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

package org.tzi.use.gui.views.diagrams.behavior.sequencediagram;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.Lifeline.ObjectBox;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.util.StringUtil;

// ************************************************************************************************************
/**
 * An activation in the sequence diagram. For each command (except for opexit)
 * shown in the diagram there is an Activation-object.
 * 
 * @quang A activation will be showed on sequence diagram as synchron messages
 *        or operation calls.
 * 
 * @author Marc Richters, Antje Werner
 */
public class Activation {

	/**
	 * The lifeline which is receiver of the activation message.
	 */
	private Lifeline ownerLifeline;

	/**
	 * The command of this activation.
	 */
	protected Event eventOfActivation;

	/**
	 * The position on the time axis on which the activation message is
	 * positioned (number of antecessors + 1)
	 */
	private int startRowOfActivationMessage;

	/**
	 * The position on the time axis on which the activations answer is
	 * positioned.
	 */
	private int endRowOfActivationAnswer;

	/**
	 * The source-activation from which the command fCmd has given. Null if the
	 * command was given by the actors Lifeline.
	 */
	private Activation sourceActivation;

	/**
	 * The nesting of the fOwner lifeline at the moment of the command call.
	 */
	private int nestingOfOwnerLifeline;

	/**
	 * The y-value on which the message arrow should be drawn.
	 */
	protected int yPositionOfMessageArrow;

	/**
	 * The y-value on which the answer should be drawn.
	 */
	protected int yPositionOfAnswerLine;

	/**
	 * The length of the message which is sent by this activation.
	 */
	private int messageLength;

	private SequenceDiagram sequenceDiagram;

	public SequenceDiagram getSequenceDiagram() {
		return sequenceDiagram;
	}

	private SDProperties fProperties;

	public SDProperties getSequenceDiagramProperties() {
		return fProperties;
	}

	/**
	 * Constructs a new Activation object. This constructor should be used for
	 * non-parallel activations.
	 * 
	 * @param start the position on the time axis on which the activation
	 *            message is positioned
	 * @param owner the lifeline which is receiver of the activation message
	 * @param cmd the command which should be represented
	 * @param yValue the y-value on which the activation message should be drawn
	 */
	public Activation(int start, Lifeline owner, Event event, Activation src, int yValue, SequenceDiagram sequenceDiagram) {
		startRowOfActivationMessage = start;
		endRowOfActivationAnswer = 0;
		ownerLifeline = owner;
		eventOfActivation = event;
		sourceActivation = src;
		yPositionOfAnswerLine = 0;
		this.sequenceDiagram = sequenceDiagram;
		fProperties = sequenceDiagram.getProperties();

		// message which will be sent by the message of this activation
		String message = createMessage();
		// the FontMetrics object used for drawing the diagram
		FontMetrics fm = sequenceDiagram.getFontMetrics(fProperties.getFont());
		// width of Activation-Message + 8 as distance to the lifelines
		messageLength = fm.stringWidth(message);
	}

	/**
	 * Returns the command of this activation.
	 * 
	 * @return the command of this activation.
	 */
	public Event getEvent() {
		return eventOfActivation;
	}

	/**
	 * Returns the position on the time axis on which the activation message is
	 * positioned (number of antecessors + 1).
	 * 
	 * @return the position on the time axis
	 */
	public int getStart() {
		return startRowOfActivationMessage;
	}

	/**
	 * Returns the position on the time axis on which the activations answer is
	 * positioned.
	 * 
	 * @return the position of the answer on the time axis
	 */
	public int getEnd() {
		return endRowOfActivationAnswer;
	}

	/**
	 * Returns the nesting of the fOwner lifeline at the moment of the command
	 * call.
	 * 
	 * @return the nesting of the fOwner lifeline
	 */
	public int getNesting() {
		return nestingOfOwnerLifeline;
	}

	/**
	 * Returns the y-value on which the activation message arrow should be
	 * drawn.
	 * 
	 * @return the y value of the activation.
	 */
	public int getYOfActivationMessArrow() {
		return yPositionOfMessageArrow;
	}

	/**
	 * Returns the y-value on which the activation ends.
	 * 
	 * @return the y value on which the activation ends
	 */
	public int getYEndOfActivation() {
		return yPositionOfAnswerLine;
	}

	/**
	 * Returns the length of the activation message.
	 * 
	 * @return the length of the activation message
	 */
	public int getMessLength() {
		return messageLength;
	}

	/**
	 * Returns the Lifeline of the source activation.
	 * 
	 * @return the lifeline of the source activation if the source is not null;
	 *         otherwise null
	 */
	public Lifeline getSrc() {
		// is sourceActivation is not the actors lifeline -> return lifeline
		if (sourceActivation != null)
			return sourceActivation.owner();
		// else return null
		return null;
	}

	/**
	 * Returns the lifeline which is receiver of the activation message.
	 * 
	 * @return the lifeline which is receiver of the message
	 */
	Lifeline owner() {
		return ownerLifeline;
	}

	/**
	 * Sets the position on the time axis on which the activations answer is
	 * positioned.
	 * 
	 * @param end the position for the answer
	 */
	public void setEnd(int end) {
		endRowOfActivationAnswer = end;
	}

	/**
	 * Sets the nesting for this activation.
	 * 
	 * @param n the nesting
	 */
	public void setNesting(int n) {
		nestingOfOwnerLifeline = n;
	}

	/**
	 * Sets the y-value of this activation.
	 * 
	 * @param y the new y-value of this activation
	 */
	public void setY(int y) {
		yPositionOfMessageArrow = y;
	}
	
	/**
     * Calculates the length of the longest message sent by this activation.
     * 
     * @return the length of the message
     */
    int calculateMessLength() {
        // message which will be sent by the message of this activation
        String message = createMessage();
        // the FontMetrics object used for drawing the diagram
        FontMetrics fm = sequenceDiagram.getFontMetrics(fProperties.getFont());
        // width of Activation-Message + 8 as distance to the lifelines
        int messLength = fm.stringWidth(message) + 8;
        // owner-lifeline of the activation
        
        // distance from goal-lifeline to the source-lifeline
        // (-> difference of the column-numbers)
        int srcDistance = ownerLifeline.column();
        if (sourceActivation != null && sourceActivation.owner() != null) {
            srcDistance = ownerLifeline.calculateDistance(sourceActivation.owner());
        }
        // if recursive activation
        if (srcDistance == 0) {
            messLength = fm.stringWidth(message) + 15 + 14;
            // otherwise, if soruce-lifeline on the left side of
            // this lifeline
        } else {
            // if command of activation = create
        	// TODO: This is messy
            if (message.equals("create") || message.startsWith("create link")) {
                // the create-arrow ends at the beginning of the
                // ObjectBox
                messLength += ownerLifeline.getObjectBox().getWidth() / 2;
                // if command of activation = insert
            }
            // if there is a return arrow
            if (endRowOfActivationAnswer > 0) {
                // if openter-command
                if (eventOfActivation instanceof OperationEnteredEvent) {
                    // calculate length of the result message
                	MOperationCall opCall = 
                		((OperationEnteredEvent)eventOfActivation).getOperationCall();
                	
                    if (fProperties.showValues()) {
                        Value result = opCall.getResultValue();
                        if (result != null) {
                            String resultLabel = result.toString();
                            if (fm.stringWidth(resultLabel) + 14 > messLength)
                                messLength = fm.stringWidth(resultLabel) + 14;
                        }
                    }
                }
            }
            // consider the nestings of the corresponding lifelines
            if (nestingOfOwnerLifeline > 0) {
                messLength += fProperties.frWidth() / 2;
            }
            if (nestingOfOwnerLifeline > 1)
                messLength += (sourceActivation.nestingOfOwnerLifeline - 1) * fProperties.frOffset();

            if (sourceActivation != null) {
                if (sourceActivation.owner() != null && sourceActivation.nestingOfOwnerLifeline > 1)
                    messLength += (sourceActivation.nestingOfOwnerLifeline - 1)
                            * fProperties.frOffset();
                if (sourceActivation.owner() == null || sourceActivation.nestingOfOwnerLifeline > 0)
                    messLength += fProperties.frWidth() / 2;
            } else {
                messLength += fProperties.frWidth() / 2;
            }

            if (!(eventOfActivation instanceof ObjectDestroyedEvent)
                    && !(eventOfActivation instanceof LinkDeletedEvent)) {
                if (nestingOfOwnerLifeline > 0) {
                    messLength += fProperties.frWidth() / 2;
                }
            }
        }
        // no recursive message
        if (srcDistance != 0) {
            // calculate distance between two lifelines subject to the
            // message length
            // and the distance between source- and goal-lifeline
        	messageLength = messLength / srcDistance;
            return messLength / srcDistance;
            // recursive message
        } else {
            // message is on the right side of the lifeline -> affect
            // position
            // of successor-lifelines -> return negative value
        	messageLength = -messLength;
            return -messLength;
        }

    }

	/**
	 * Calculates the y-value for the answer message of this Activation
	 * depending on the fProperties which are set in the fProperties object of
	 * the SequenceDiagram.
	 * 
	 * @return the y-position for the answer message
	 */
	public int calculateEnd() {
		// y-value of the command-message
		int value = yPositionOfMessageArrow;
		// if there is a return arrow
		if (endRowOfActivationAnswer > 0) {
			// calculate position subject to the user settings for the
			// distance
			// between two messages
			if (fProperties.getActManDist() != -1) {
				value += (endRowOfActivationAnswer - startRowOfActivationMessage) * fProperties.getActManDist();
			} else {
				value += (endRowOfActivationAnswer - startRowOfActivationMessage) * fProperties.actStep();
			}
		}
		// set yPositionOfAnswerLine-value and return result
		yPositionOfAnswerLine = value;
		return value;
	}

	public int getSendMessagePos() {
		int result = 0;
		// if there is a return arrow calculate position subject to the user settings for the
		// distance between two messages
		if (fProperties.getActManDist() != -1) {
			result = startRowOfActivationMessage * fProperties.getActManDist();
		} else {
			result = startRowOfActivationMessage * fProperties.actStep();
		}
		return result;
	}

	public int getAnswerPos() {
		return yPositionOfAnswerLine;
	}

	/**
	 * Draws the message of the command of this activation in the diagram.
	 * 
	 * @param g the graphics on which the message should be drawn
	 * @param fm the FontMetrics-Object of the diagram
	 */
	void drawMessageSend(Graphics2D g, FontMetrics fm) {
		g.setColor(Color.black);
		// activation is recursive if the owner of sourceActivation is the same
		// as the owner of this activation
		boolean isRecursive = sourceActivation != null && sourceActivation.owner() == ownerLifeline;

		// x-value of the source lifeline of the activation ->
		// left Margin + Center x-Coordinate of Actor-Lifeline (->10)
		int x_Src = fProperties.getLeftMargin() + 10;
		// x-value of the goal lifeline of the activation
		int x_Goal = owner().xValue();

		int xd = 0;

		// if sourceActivation is not the actors lifeline -> calculate x-value
		// of the
		// source lifeline
		if (sourceActivation != null)
			x_Src = sourceActivation.owner().xValue();

		// consider nestings of the source- and goal-lifeline
		if (x_Src < x_Goal) {
			if (sourceActivation != null) {
				if (sourceActivation.nestingOfOwnerLifeline > 1)
					x_Src += (sourceActivation.nestingOfOwnerLifeline - 1) * fProperties.frOffset();
				if (sourceActivation.nestingOfOwnerLifeline > 0)
					x_Src += fProperties.frWidth() / 2;
			} else {
				x_Src += fProperties.frWidth() / 2;
			}

			if (!(eventOfActivation instanceof ObjectDestroyedEvent) && !(eventOfActivation instanceof LinkDeletedEvent)) {

				if (nestingOfOwnerLifeline > 0) {
					x_Goal -= fProperties.frWidth() / 2;
				}
				if (nestingOfOwnerLifeline > 1) {
					x_Goal += (nestingOfOwnerLifeline - 1) * fProperties.frOffset();
				}
			}
		} else if (x_Src > x_Goal && sourceActivation != null) {
			if (!(eventOfActivation instanceof ObjectDestroyedEvent) && !(eventOfActivation instanceof LinkDeletedEvent)) {
				if (nestingOfOwnerLifeline > 0)
					x_Goal += fProperties.frWidth() / 2;
				if (nestingOfOwnerLifeline > 1)
					x_Goal += (nestingOfOwnerLifeline - 1) * fProperties.frOffset();
			}
			if (sourceActivation.getEnd() != 0) {
				if (sourceActivation.getNesting() > 0)
					x_Src -= fProperties.frWidth() / 2;
				if (sourceActivation.getNesting() > 1)
					x_Src += (sourceActivation.getNesting() - 1) * fProperties.frOffset();
			}
		} else {
			if (sourceActivation != null) {
				if (sourceActivation.getNesting() > 0)
					x_Src += fProperties.frWidth() / 2;
				if (sourceActivation.getNesting() > 1)
					x_Src += (sourceActivation.getNesting() - 1) * fProperties.frOffset();
			} else {
				x_Src += fProperties.frWidth() / 2;
			}
			if (nestingOfOwnerLifeline > 0)
				x_Goal += fProperties.frWidth() / 2;
			if (nestingOfOwnerLifeline > 1)
				x_Goal += (nestingOfOwnerLifeline - 1) * fProperties.frOffset();

		}
		// if create-command
		if (eventOfActivation instanceof ObjectCreatedEvent) {
			// object box of the owner-lifeline
			ObjectBox objectBox = ownerLifeline.getObjectBox();
			// x-value of the object box
			int x_ObjectBox = objectBox.getStart();
			x_Goal = x_ObjectBox;
			// draw message
			drawCreateMessage(g, fm, x_Src, x_ObjectBox, x_Src);
			// if insert-command
		} else if (eventOfActivation instanceof LinkInsertedEvent) {
			// draw message
			drawInsertMessages(g, fm, x_Src, x_Goal, x_Goal);
			// other command
		} else {
			if (eventOfActivation instanceof OperationEnteredEvent) {
				// if operation result was not correct -> change color to
				// red
				OperationEnteredEvent event = (OperationEnteredEvent) eventOfActivation;
				MOperationCall operationCall = event.getOperationCall();

				if (!operationCall.enteredSuccessfully()) {
					g.setColor(Color.red);
				}
			}
			// recursive message
			if (isRecursive) {
				g.drawArc(x_Goal - 40, yPositionOfMessageArrow - fProperties.actStep() / 3, 80, fProperties.actStep() / 3, 90, -180);
				g.drawLine(x_Goal, yPositionOfMessageArrow - fProperties.actStep() / 3, x_Goal - fProperties.frOffset(),
						yPositionOfMessageArrow - fProperties.actStep() / 3);
				xd = +10;
				// otherwise
			} else {
				// draw horizontal line
				g.drawLine(x_Src, yPositionOfMessageArrow, x_Goal, yPositionOfMessageArrow);
				xd = (x_Goal <= x_Src) ? +10 : -10;
			}
			// draw arrow
			int[] xp = { x_Goal, x_Goal + xd, x_Goal + xd };
			int[] yp = { yPositionOfMessageArrow, yPositionOfMessageArrow - 4, yPositionOfMessageArrow + 4 };
			g.fillPolygon(xp, yp, xp.length);

		}
		// message of the activation
		String msgLabel = createMessage();
		// draw message label into the diagram
		if (isRecursive) {
			g.drawString(msgLabel, x_Src + 15, yPositionOfMessageArrow - fProperties.actStep() / 3 - 2);
		} else {
			int labelWidth = fm.stringWidth(msgLabel);
			int x_value = 0;
			// position of goal lifeline
			int x_Goal2 = x_Goal;
			// if insert-command
			if (eventOfActivation instanceof LinkInsertedEvent) {
				// calculate position of goal lifeline
				if (x_Goal > x_Src)
					x_Goal2 = ownerLifeline.getObjectBox().getStart();
				else if (x_Goal < x_Src)
					x_Goal2 = ownerLifeline.getObjectBox().getStart() + ownerLifeline.getObjectBox().getWidth();
			}
			// calculate x-position fot the message label
			if (x_Src < x_Goal) {
				x_value = x_Src + (x_Goal2 - x_Src - labelWidth) / 2;
			} else {
				x_value = x_Goal2 + (x_Src - x_Goal2 - labelWidth) / 2;
			}

			g.drawString(msgLabel, x_value, yPositionOfMessageArrow - 2);

		}
		g.setColor(Color.black);
		// openter- or opexit-command
		if ((eventOfActivation instanceof OperationEnteredEvent) && ((OperationEnteredEvent) eventOfActivation).getOperationCall().enteredSuccessfully()) {
			// draw answer message in black color
			drawAnswer(g, fm, x_Src, x_Goal, yPositionOfAnswerLine);
		}
	}

	/**
	 * Draws an insert message in the sequence diagram.
	 * 
	 * @param g the graphics on which tha message should be drawn
	 * @param fm the FontMetrics-Object of the diagram
	 * @param x1 the start-x-value for the message
	 * @param x2 the end-x-value of the message
	 * @param x the x-value of the goal lifeline (without consideration of
	 *            frames)
	 */
	private void drawInsertMessages(Graphics2D g, FontMetrics fm, int x1, int x2, int x) {
		// object box of the owner lifeline
		ObjectBox objectBox = ownerLifeline.getObjectBox();
		// calculate start and end position of the box
		objectBox.calculateStart(fm);
		objectBox.calculateEnd(fm);

		// x-value where the arrow ends -> normally on the left end of the
		// box
		int x_ObjectBox = objectBox.getStart();
		// if owner-lifeline is on the left side of the source lifeline
		// -> arrow ends at the right side of the box
		if (x1 > x2)
			x_ObjectBox = objectBox.getEnd();

		int xd = 0;
		// draw line of arrow
		g.drawLine(x1, yPositionOfMessageArrow, x_ObjectBox, yPositionOfMessageArrow);

		xd = (x1 <= x2) ? -10 : +10;
		// draw arrowhead
		int[] xp = { x_ObjectBox, x_ObjectBox + xd, x_ObjectBox + xd };
		int[] yp = { yPositionOfMessageArrow, yPositionOfMessageArrow - 4, yPositionOfMessageArrow + 4 };
		g.fillPolygon(xp, yp, xp.length);

		// List of involved objects
		LinkInsertedEvent event = (LinkInsertedEvent) eventOfActivation;
		List<MObject> objects = event.getParticipants();
		List<String> roleNames = event.getAssociation().roleNames();

		// regard each object
		for (int i = 0; i < objects.size(); i++) {
			MObject object = objects.get(i);
			// lifeline of the object
			Lifeline oll = sequenceDiagram.getAllLifelines().get(object);
			// if the lifeline is not marked to be hidden
			if (!oll.isHidden()) {
				// calculate y-position of the 'inserted as'-message
				// depending
				// on the user settings
				int y3 = 0;
				if (fProperties.getActManDist() == -1) {
					y3 = yPositionOfMessageArrow + (i + 1) * fProperties.actStep();
				} else {
					y3 = yPositionOfMessageArrow + (i + 1) * fProperties.getActManDist();
				}

				// x-value of the object lifeline
				int x3 = oll.xValue();
				// consider nestings of the source and goal lifelines
				if (x3 < x2) {
					if (x2 > ownerLifeline.xValue()) {
						x2 -= fProperties.frWidth() / 2;
						if (getNesting() > 0) {
							x2 -= fProperties.frWidth() / 2;
							x -= fProperties.frWidth() / 2;
						}
						if (getNesting() > 1) {
							x2 += (getNesting() - 1) * fProperties.frOffset();
							x += (getNesting() - 1) * fProperties.frOffset();
						}
					}
					if (((InsertActivation) this).fNestings[i] > 0) {
						x3 += fProperties.frWidth() / 2;// 5;
					}
					if (((InsertActivation) this).fNestings[i] > 1) {
						x3 += (oll.activationNesting - 1) * fProperties.frOffset();
					}
				} else {
					if (x2 < ownerLifeline.xValue()) {
						x2 += fProperties.frWidth() / 2;// 5;
						if (getNesting() > 0) {
							x2 += fProperties.frWidth() / 2;// 5;
							x += fProperties.frWidth() / 2;
						}
						if (getNesting() > 1) {
							x2 += (getNesting() - 1) * fProperties.frOffset();
							x += (getNesting() - 1) * fProperties.frOffset();
						}
					}
					if (((InsertActivation) this).fNestings[i] > 0) {
						x3 -= fProperties.frWidth() / 2;// 5;
					}
					if (((InsertActivation) this).fNestings[i] > 1) {
						x3 += (oll.activationNesting - 1) * fProperties.frOffset();
					}

				}
				// draw arrow line
				g.drawLine(x2, y3, x3, y3);
				// draw arrowhead
				xd = (x3 <= x2) ? -10 : +10;
				g.drawLine(x3, y3, x3 - xd, y3 - 4);
				g.drawLine(x3, y3, x3 - xd, y3 + 4);

				// 'inserted as'-message
				String message = "inserted as " + roleNames.get(i);
				int labelWidth = fm.stringWidth(message);
				// calculate position to draw the message
				int x_value = 0;
				if (x < x3) {
					x_value = x + (x3 - x - labelWidth) / 2; // -
					// fIsCoeval
				} else {
					x_value = x3 + (x - x3 - labelWidth) / 2; // -
					// fIsCoeval
				}
				// draw message
				g.drawString(message, x_value, y3 - 2);
			}
		}

	}

	/**
	 * Draws an create message in the sequence diagram.
	 * 
	 * @param g the graphics on which tha message should be drawn
	 * @param fm the FontMetrics-Object of the diagram
	 * @param x1 the start-x-value for the message
	 * @param x2 the end-x-value of the message
	 * @param x the x-value of the goal lifeline (without consideration of
	 *            frames)
	 */
	private void drawCreateMessage(Graphics g, FontMetrics fm, int x1, int x2, int x) {
		// object box of goal lifeline
		ObjectBox objectBox = ownerLifeline.getObjectBox();
		// calculate start and end position of the box
		objectBox.calculateStart(fm);
		objectBox.calculateEnd(fm);
		// x-value where the arrow ends -> normally on the left end of the
		// box
		int x_ObjectBox = objectBox.getStart();
		// if owner-lifeline is on the left side of the source lifeline
		// -> arrow ends at the right side of the box
		if (x1 > x2)
			x_ObjectBox = objectBox.getEnd();

		int xd = 0;
		// draw arrow line
		g.drawLine(x1, yPositionOfMessageArrow, x_ObjectBox, yPositionOfMessageArrow);

		xd = (x1 <= x2) ? -10 : +10;
		// draw arrowhead
		int[] xp = { x_ObjectBox, x_ObjectBox + xd, x_ObjectBox + xd };
		int[] yp = { yPositionOfMessageArrow, yPositionOfMessageArrow - 4, yPositionOfMessageArrow + 4 };
		g.fillPolygon(xp, yp, xp.length);

	}

	/**
	 * Draws the answer message for the command in the sequence diagram.
	 * 
	 * @param g the graphics on which tha message should be drawn
	 * @param fm the FontMetrics-Object of the diagram
	 * @param x_Src the x-value for the source lifeline
	 * @param x_Goal the x-value of the goal lifeline
	 * @param y the y-value on which the answer should be drawn
	 */
	private void drawAnswer(Graphics2D g, FontMetrics fm, int x_Src, int x_Goal, int y) {
		// message is recursive if the owner of sourceActivation is the same as
		// the owner of this activation
		boolean isRecursive = sourceActivation != null && sourceActivation.owner() == ownerLifeline;

		// result label
		String resultLabel = null;
		// result value
		Value result = null;

		// if openter-command
		if (eventOfActivation instanceof OperationEnteredEvent) {
			// OperationCall-object of the openter-command
			MOperationCall fOpCall = ((OperationEnteredEvent) eventOfActivation).getOperationCall();
			// one or more postconditions of the activation is false
			// -> arrow in red color
			if (!fOpCall.exitedSuccessfully())
				g.setColor(Color.red);
			// user has activated 'Show values'
			if (fProperties.showValues() && fOpCall.getOperation().hasResultType()) {
				// result value of the operation call
				result = fOpCall.getResultValue();
				// if there was a result label -> calculate textual
				// description
				if (result != null)
					resultLabel = result.toString();
			}
			// all other commands -> no result label
		} else {
			resultLabel = "";
		}

		Stroke oldStroke = g.getStroke();
		g.setStroke(fProperties.getDASHEDSTROKE());

		int xd = (x_Src <= x_Goal) ? +10 : -10;
		// recurive answer
		if (isRecursive) {
			// draw arc
			g.drawArc(x_Goal - 40, y, 80, fProperties.actStep() / 3, -90, 180);
			g.drawLine((int) (x_Goal + 5f), y + fProperties.actStep() / 3, x_Goal - fProperties.frOffset(), y + fProperties.actStep() / 3);

			g.setStroke(oldStroke);

			// draw arrowhead
			int y0 = y + fProperties.actStep() / 3;
			g.drawLine(x_Src, y0, x_Src + xd, y0 - 3);
			g.drawLine(x_Src, y0, x_Src + xd, y0 + 3);

			// if there was a result -> draw result label
			if (resultLabel != null)
				g.drawString(resultLabel, x_Goal + 15, y - 2);
			// answer is not recursive
		} else {
			// draw arrow line
			g.drawLine(x_Src, y, x_Goal, y);
			g.setStroke(oldStroke);

			// draw arrowhead
			g.drawLine(x_Src, y, x_Src + xd, y - 3);
			g.drawLine(x_Src, y, x_Src + xd, y + 3);

			// if there was a result label
			// -> draw it on the right position
			if (resultLabel != null) {
				int labelWidth = fm.stringWidth(resultLabel);
				int x_value = 0;
				if (x_Src < x_Goal) {
					x_value = x_Src + (x_Goal - x_Src - labelWidth) / 2;
				} else {
					x_value = x_Goal + (x_Src - x_Goal - labelWidth) / 2;
				}

				g.drawString(resultLabel, x_value, y - 2);
			}

		}

		g.setStroke(oldStroke);
		g.setColor(Color.black);
	}

	private String createOperationEnterMessage() {
		String msgLabel = "";
		MOperationCall fOpCall = ((OperationEnteredEvent) eventOfActivation).getOperationCall();
		msgLabel = fOpCall.getOperation().name();
		if (fProperties.showValues()) {
			StringBuilder argMsg = new StringBuilder();
			argMsg.append("(");
			StringUtil.fmtSeq(argMsg, fOpCall.getArgumentsAsNamesAndValues().values(), ",");
			argMsg.append(")");
			msgLabel += argMsg;
		}
		return msgLabel;
	}

	private String createCreateMassage() {
		return "create";
	}

	private String createDestroyMassage() {
		return "destroy";
	}

	private String createAttributeAssignMessage() {
		String msgLabel = "";
		String attribute = ((AttributeAssignedEvent) eventOfActivation).getAttribute().name();
		String value = ((AttributeAssignedEvent) eventOfActivation).getValue().toString();
		msgLabel = "set " + attribute;
		if (fProperties.showValues())
			msgLabel = msgLabel + " := " + value;
		return msgLabel;
	}

	private String createLinkInsertMessage() {
		String msgLabel = "";
		boolean isLinkObject = (((LinkInsertedEvent) eventOfActivation).getAssociation() instanceof MAssociationClass);

		if (isLinkObject) {
			msgLabel = "create";
		} else {
			msgLabel = "insert";
		}

		if (fProperties.showValues()) {
			if (isLinkObject) {
				msgLabel = msgLabel + " between ";
			}

			List<MObject> objects = ((LinkInsertedEvent) eventOfActivation).getParticipants();
			msgLabel = msgLabel + "(@";
			for (int i = 0; i < objects.size(); i++) {
				MObject object = objects.get(i);
				msgLabel = msgLabel + object.toString();
				if (i < (objects.size() - 1))
					msgLabel = msgLabel + ",@";
				else
					msgLabel = msgLabel + ")";
			}
		}
		return msgLabel;
	}

	private String createLinkDeleteMassage() {
		String msgLabel = "delete";
		if (fProperties.showValues()) {
			List<MObject> objects = ((LinkDeletedEvent) eventOfActivation).getParticipants();
			msgLabel = msgLabel + "(@";
			for (int i = 0; i < objects.size(); i++) {
				MObject object = objects.get(i);
				msgLabel = msgLabel + object.toString();
				if (i < (objects.size() - 1))
					msgLabel = msgLabel + ",@";
				else
					msgLabel = msgLabel + ")";
			}
		}
		return msgLabel;
	}

	/**
	 * Creates the message which should be sent to the goal lifeline.
	 * 
	 * @return the message
	 */
	private String createMessage() {
		String msgLabel = "";
		if (eventOfActivation instanceof OperationEnteredEvent) {
			msgLabel = createOperationEnterMessage();
		} else if (eventOfActivation instanceof ObjectCreatedEvent) {
			msgLabel = createCreateMassage();
		} else if (eventOfActivation instanceof ObjectDestroyedEvent) {
			msgLabel = createDestroyMassage();
		} else if (eventOfActivation instanceof AttributeAssignedEvent) {
			msgLabel = createAttributeAssignMessage();
		} else if (eventOfActivation instanceof LinkInsertedEvent) {
			msgLabel = createLinkInsertMessage();
		} else if (eventOfActivation instanceof LinkDeletedEvent) {
			msgLabel = createLinkDeleteMassage();
		}
		return msgLabel;
	}
}
