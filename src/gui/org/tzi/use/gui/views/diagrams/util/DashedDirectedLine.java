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


package org.tzi.use.gui.views.diagrams.util;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a dashed directed line
 */
public class DashedDirectedLine extends DirectedLine {

    /**
     * The default length of a single dash
     */
    public static final int DEFAULT_FRAGMENT_LENGTH = 10;
    /**
     * The default length of the distance between two dashes
     */
    public static final int DEFAULT_INTERSECTION_LENGTH = 10;

    private int fragmentLength = DEFAULT_FRAGMENT_LENGTH;
    private int intersectionLength = DEFAULT_INTERSECTION_LENGTH;

    /**
     * Creates a dashed directed line between the given coordinates with the given lengths of dashes and intersections
     *
     * @param sourceX            X coordinate of source point
     * @param sourceY            Y coordinate of source point
     * @param targetX            X coordinate of target point
     * @param targetY            Y coordinate of target point
     * @param fragmentLength     length of a single dash
     * @param intersectionLength length of the distance between two dashes
     */
    public DashedDirectedLine(final double sourceX, final double sourceY, final double targetX, final double targetY,
                              final int fragmentLength, final int intersectionLength) {
        init(sourceX, sourceY, targetX, targetY);
        this.fragmentLength = fragmentLength;
        this.intersectionLength = intersectionLength;
    }

    /**
     * Creates a dashed directed line between the given coordinates with the default lengths of dashes and intersections
     *
     * @param sourceX X coordinate of source point
     * @param sourceY Y coordinate of source point
     * @param targetX X coordinate of target point
     * @param targetY Y coordinate of target point
     */
    public DashedDirectedLine(final double sourceX, final double sourceY, final double targetX, final double targetY) {
        init(sourceX, sourceY, targetX, targetY);
    }

    /**
     * Overridden template method
     *
     * @see DirectedLine
     */
    public I_DirectedLine doCreateDirectedLine(final double sourceX, final double sourceY, final double targetX,
                                               final double targetY) {
        return new DashedDirectedLine(sourceX, sourceY, targetX, targetY, fragmentLength, intersectionLength);
    }

    /**
     * Draws the dashed line into the given graphic
     *
     * @param graphic to be drawn into
     * @return line
     */
    public I_DirectedLine draw(final Graphics graphic) {
        final ArrayList fragments = fragmentLine();
        for (final Iterator iterator = fragments.iterator(); iterator.hasNext();) {
            final I_DirectedLine line = (I_DirectedLine) iterator.next();
            line.draw(graphic);
        }
        return this;
    }

    ArrayList fragmentLine() {
        final double length = calculateLength();
        final double numberOfSteps = length / (fragmentLength + intersectionLength);
        final double gradientAngle = calculateGradientAngle();

        return createFragments(numberOfSteps, gradientAngle, sourceX, sourceY);
    }

    private void init(final double sourceX, final double sourceY, final double targetX, final double targetY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    private ArrayList createFragments(final double numberOfSteps, final double gradientAngle,
                                      double actualSourceX, double actualSourceY) {
        final ArrayList fragments = new ArrayList();
        for (int times = 1; times <= numberOfSteps; times++) {
            final I_DirectedLine fragment = createFragment(actualSourceX, actualSourceY, gradientAngle);
            fragments.add(fragment);

            final I_DirectedLine intersection =
                    createIntersection(fragment.getTargetX(), fragment.getTargetY(), gradientAngle);
            actualSourceX = intersection.getTargetX();
            actualSourceY = intersection.getTargetY();
        }
        return fragmentTail(fragments, gradientAngle, actualSourceX, actualSourceY);
    }

    private ArrayList fragmentTail(final ArrayList fragments, final double gradientAngle, final double actualSourceX,
                                   final double actualSourceY) {
        final double tailLength = Util.calculateDistance(actualSourceX, actualSourceY, targetX, targetY);
        if (Math.round(tailLength) > 0) {
            final I_DirectedLine fragment = createLastFragment(actualSourceX, actualSourceY, gradientAngle, tailLength);
            fragments.add(fragment);
        }
        return fragments;
    }

    private I_DirectedLine createIntersection(final double actualTargetX, final double actualTargetY,
                                              final double gradientAngle) {
        return DirectedLineFactory.createSolidDirectedLine(actualTargetX, actualTargetY,
                actualTargetX + intersectionLength, actualTargetY).rotateAroundSourcePoint(gradientAngle);
    }

    private I_DirectedLine createFragment(final double actualSourceX, final double actualSourceY,
                                          final double gradientAngle) {
        return DirectedLineFactory.createSolidDirectedLine(actualSourceX, actualSourceY,
                actualSourceX + fragmentLength, actualSourceY).rotateAroundSourcePoint(gradientAngle);
    }

    private I_DirectedLine createLastFragment(final double actualSourceX, final double actualSourceY,
                                              final double gradientAngle, final double tailLength) {
        return DirectedLineFactory.createSolidDirectedLine(actualSourceX, actualSourceY,
                actualSourceX + Math.min(tailLength, fragmentLength), actualSourceY)
                .rotateAroundSourcePoint(gradientAngle);
    }
}
