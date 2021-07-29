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

package org.tzi.use.util.collections;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * A stack (LIFO) which is limited to
 * the provided number of entries.
 * When an add exceeds the limit, the
 * oldest item is removed from the stack. 
 *  
 * @author Lars Hamann
 *
 */
public class LimitedStack<E> implements Collection<E> {
	
	private Stack<E> container;
	private int limit;

	public LimitedStack(int limit) {
        this.limit = limit;
        this.container = new Stack<E>();
    }

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
     * Returns the element at the specified position in this Stack.
     *
     * @param index index of the element to return
     * @return object at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *            ({@code index < 0 || index >= size()})
     */
	public E get(int index) {
		return container.get(index);
	}
	
	 /**
      * Pushes an item onto the top of this stack.
      * If necessary, i. e., after adding the size would be grater then limit,
      * the oldest element of the stack is removed.     
      * This has exactly the same effect as:
      * <blockquote><pre>add(item)</pre></blockquote>
      *
      * @param   item   the item to be pushed onto this stack.
      * @return  the <code>item</code> argument.
      * @see     java.util.Vector#addElement
      */
    public E push(E item) {
    	add(item);
    	return item;
    }

    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return     The object at the top of this stack (the last item
     *             of the <tt>Vector</tt> object).
     * @exception  EmptyStackException  if this stack is empty.
     */
    public synchronized E pop() {
		return container.pop();
    }

    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return     the object at the top of this stack (the last item
     *             of the <tt>Vector</tt> object).
     * @exception  EmptyStackException  if this stack is empty.
     */
    public synchronized E peek() {
		return container.peek();
    }

    
	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		boolean res = container.add(e);
		ensureLimit();		
		return res;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean res = container.addAll(c);
		ensureLimit();
		return res;
	}
	
	
	/**
	 * 
	 */
	private void ensureLimit() {
		while (container.size() > limit) { container.remove(container.size()); }
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return container.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return container.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return container.contains(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return container.iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		return container.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return container.toArray(a);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return container.remove(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return container.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return container.removeAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return container.retainAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		container.clear();
	}
}
