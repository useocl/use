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
package org.tzi.use.util.collections;

/*
 * This class was originally published at:
 * http://www.merriampark.com/perm.htm
 */

//--------------------------------------
//Systematically generate permutations. 
//--------------------------------------

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PermutationGenerator<T> {

	private int[] a;
	private BigInteger numLeft;
	private BigInteger total;
	private List<T> listToPermute;
	private List<T> lastPermutation = new ArrayList<T>();
	
	// -----------------------------------------------------------
	// Constructor. WARNING: Don't make n too large.
	// Recall that the number of permutations is n!
	// which can be very large, even when n is as small as 20 --
	// 20! = 2,432,902,008,176,640,000 and
	// 21! is too big to fit into a Java long, which is
	// why we use BigInteger instead.
	// ----------------------------------------------------------

	public PermutationGenerator(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Min 1");
		}
		a = new int[n];
		total = getFactorial(n);
		reset();
	}

	public PermutationGenerator(List<T> list) {
		this(list.size());
		listToPermute = list;
	}
	
	// ------
	// Reset
	// ------

	public void reset() {
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		numLeft = new BigInteger(total.toString());
	}

	// ------------------------------------------------
	// Return number of permutations not yet generated
	// ------------------------------------------------

	public BigInteger getNumLeft() {
		return numLeft;
	}

	// ------------------------------------
	// Return total number of permutations
	// ------------------------------------

	public BigInteger getTotal() {
		return total;
	}

	// -----------------------------
	// Are there more permutations?
	// -----------------------------

	public boolean hasMore() {
		return numLeft.compareTo(BigInteger.ZERO) == 1;
	}

	// ------------------
	// Compute factorial
	// ------------------

	private static BigInteger getFactorial(int n) {
		BigInteger fact = BigInteger.ONE;
		for (int i = n; i > 1; i--) {
			fact = fact.multiply(new BigInteger(Integer.toString(i)));
		}
		return fact;
	}

	// --------------------------------------------------------
	// Generate next permutation (algorithm from Rosen p. 284)
	// --------------------------------------------------------

	public int[] getNext() {

		if (numLeft.equals(total)) {
			numLeft = numLeft.subtract(BigInteger.ONE);
			return a;
		}

		int temp;

		// Find largest index j with a[j] < a[j+1]

		int j = a.length - 2;
		while (a[j] > a[j + 1]) {
			j--;
		}

		// Find index k such that a[k] is smallest integer
		// greater than a[j] to the right of a[j]

		int k = a.length - 1;
		while (a[j] > a[k]) {
			k--;
		}

		// Interchange a[j] and a[k]

		temp = a[k];
		a[k] = a[j];
		a[j] = temp;

		// Put tail end of permutation after jth position in increasing order

		int r = a.length - 1;
		int s = j + 1;

		while (r > s) {
			temp = a[s];
			a[s] = a[r];
			a[r] = temp;
			r--;
			s++;
		}

		numLeft = numLeft.subtract(BigInteger.ONE);
		return a;

	}

	public List<T> getNextList() {
		int[] nextResult = getNext();
		lastPermutation.clear();
		
		for (int index : nextResult) {
			lastPermutation.add(listToPermute.get(index));
		}
		
		return lastPermutation;
	}
}
