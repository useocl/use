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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.tzi.use.util.Pair;
import org.tzi.use.util.collections.CollectionUtil.UniqueList;

/**
 * An iterator which iterates over generated partitions of two lists.
 * One of the lists is specified as unique.
 * 
 * Each returned partition contains only combinations from one element of the unique list
 * e.g.:<br/>
 * <code>{a,b} {c,d}</code> (second list is marked as unique) results in:<br/> 
 * <code>0 = {(a,c), (a,d)}, 1 = {(b,c), (b,d)}</code>
 * 
 * @author Lars Hamann
 *
 */
public class MinCombinationsIterator<T> implements Iterator<List<Pair<T>>> {

	private List<T> firstList;
	private List<T> secondList;
	private UniqueList unique;
	
	private List<List<Pair<T>>> partitionedCombinations;
		
	private class State {
		public State(List<Pair<T>> head, List<List<Pair<T>>> tail) {
			this.head = head;
			this.tail = tail;
			this.index = 0;
		}
		
		public List<Pair<T>> head;
		public List<List<Pair<T>>> tail;
		public int index;
		
		public String toString() {
			return "head: " + head.toString() + "; tail:" + tail.toString() + "; index:" + index;
		}
	}
	
	private Stack<State> toDo;
	
	private List<Pair<T>> nextLinkSet;
	
	public MinCombinationsIterator(List<T> firstList, List<T> secondList, UniqueList unique) {
		this.firstList = firstList;
		this.secondList = secondList;
		this.unique = unique;
		
		buildPartitions();
		initState();
	}
	
	private void buildPartitions() {
		// Each partition contains only combinations from one element of the unique list
    	// e.g.: {a,b} {c,d} results in:
    	// 0 = {(a,c), (a,d)}, 1 = {(b,c), (b,d)}
    	partitionedCombinations = new ArrayList<List<Pair<T>>>();
    	    	
    	List<T> l1 = (unique == UniqueList.FIRST_IS_UNIQUE ? secondList : firstList);
    	List<T> l2 = (unique == UniqueList.FIRST_IS_UNIQUE ? firstList : secondList);
    	
    	for (int index = 0; index < l1.size(); index++) {
    		List<Pair<T>> partition = new ArrayList<Pair<T>>();
    		
    		for (int index2 = 0; index2 < l2.size(); index2++) {
    			Pair<T> entry = new Pair<T>();
    			if (unique == UniqueList.FIRST_IS_UNIQUE) {
    				entry.first  = l2.get(index2);
    				entry.second = l1.get(index);
    			} else {
    				entry.first  = l1.get(index);
    				entry.second = l2.get(index2);
    			}
    			partition.add(entry);
    		}
    		
    		if (partition.size() > 0)
    			partitionedCombinations.add(partition);
    	}
	}
		
	private void initState() {
		toDo = new Stack<MinCombinationsIterator<T>.State>();
		
		// Loops over all partitions of the combinations and calls combine.
    	// The tails is reduced by one after every call, because the previous call includes already the combinations
    	for (int index = 0; index < partitionedCombinations.size(); ++index) {
    		toDo.push(new State(Collections.<Pair<T>>emptyList(), partitionedCombinations.subList(index, partitionedCombinations.size())));
    	}
    	
    	// Empty LinkSet is first
    	this.nextLinkSet = Collections.emptyList();
    }
	
    private void buildNextCombination() {
    	if (toDo.isEmpty()) {
    		nextLinkSet = null;
    		return;
    	}
    	    	
    	State s = toDo.peek();

    	List<Pair<T>> myCombinations = s.tail.get(0);
    	
    	if (s.index + 1 == myCombinations.size()) {
			toDo.pop();
		}
    	
    	this.nextLinkSet = new ArrayList<Pair<T>>(s.head);
    	nextLinkSet.add(myCombinations.get(s.index));
    			
		for (int i = 1; i < s.tail.size(); ++i) {
			List<List<Pair<T>>> newTail = s.tail.subList(i, s.tail.size());
			toDo.push(new State(new LinkedList<Pair<T>>(nextLinkSet), newTail));
		}
		
		s.index++;

    }
    
    /* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return nextLinkSet != null;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public List<Pair<T>> next() {
		List<Pair<T>> res = nextLinkSet;
		buildNextCombination();
		return res;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
