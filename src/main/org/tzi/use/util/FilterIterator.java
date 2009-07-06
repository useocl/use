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

package org.tzi.use.util;

import java.util.Iterator;

/**
 * A FilterIterator filters objects from another iterator by applying
 * an unary predicate.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class FilterIterator<T> implements Iterator<T> {
    private Iterator<T> fIter;
    private UnaryPredicate fPred;
    private T fNext;
    private boolean fHaveNext;

    public FilterIterator(Iterator<T> iter, UnaryPredicate pred) {
        fIter = iter;
        fPred = pred;
    }

    public boolean hasNext() {
        fHaveNext = false;
        while (fIter.hasNext() ) {
            fNext = fIter.next();
            if (fPred.isTrue(fNext) ) {
                return fHaveNext = true;
            }
        }
        return false;
    }

    public T next() {
        if (fHaveNext ) {
            fHaveNext = false;
            return fNext;
        } else {
            T n;
            do {
                n = fIter.next();
            } while (! fPred.isTrue(n) );
            return n;
        }
    }

    public void remove() {
        fIter.remove();
    }
}
