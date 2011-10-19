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

package org.tzi.use.util.collections;


/**
 * A thread-safe FIFO Queue.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class Queue {
    protected ListNode fHead;
    protected ListNode fTail;

    /** 
     * Appends <code>obj</code> to queue and notifies waiting threads. 
     */
    public synchronized void append(Object obj) {
        ListNode node = new ListNode(obj);
        if (fTail == null )
            fHead = node;
        else 
            fTail.setNext(node);
        fTail = node;
        notify();
    }

    /** 
     * Gets the first element, possibly blocking until element
     * available.
     *
     * @param timeout   the maximum time to wait in milliseconds
     * @exception InterruptedException Another thread has interrupted
     *            this thread.  
     */
    public synchronized Object get(int timeout) throws InterruptedException {
        while (fHead == null )
            wait(timeout);

        ListNode node = fHead;
        fHead = fHead.next();
        if (fHead == null )
            fTail = null;
        return node.object();
    }

    /** 
     * Gets the first element, possibly blocking until element
     * available.
     *
     * @exception InterruptedException Another thread has interrupted
     *            this thread.  
     */
    public synchronized Object get() throws InterruptedException {
        return get(0);
    }
}
