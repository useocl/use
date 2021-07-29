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

package org.tzi.use.uml.ocl.type;

import java.util.Set;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * The OCL message type referencing signals or operations.
 * @author Lars Hamann
 *
 */
public class MessageType extends TypeImpl {

	private final MSignal referredSignal;
	
	private final MOperation referredOperation;
	
	private final int hashCode;
	
	/**
	 * Creates a new message type referring a signal.
	 * @param referredSignal The referred signal. Cannot be <code>null</code>.
	 * @throws IllegalArgumentException If <code>referredSignal</code> is <code>null</code>.
	 */
	public MessageType(MSignal referredSignal) {
		this.referredSignal = referredSignal;
		this.referredOperation = null;
		hashCode = generateHash();
	}
	
	/**
	 * Creates a new message type referring
	 * an operation.
	 * @param referredOperation The referred operation. Cannot be <code>null</code>.
	 * @throws IllegalArgumentException If <code>referredOperation</code> is <code>null</code>.
	 */
	public MessageType(MOperation referredOperation) {
		this.referredSignal = null;
		this.referredOperation = referredOperation;
		hashCode = generateHash();
	}

	public boolean isReferencingSignal() {
		return this.referredSignal != null;
	}
	
	public boolean isReferencingOperation() {
		return this.referredOperation != null;
	}
	
	@Override
	public boolean conformsTo(Type t) {
		if (t.isTypeOfOclAny()) {
			return true;
		}
		
		if (!(t instanceof MessageType)) {
			return false;
		}
		
		MessageType otherType = (MessageType)t;
		
		if (this.isReferencingSignal() != otherType.isReferencingSignal()) {
			return false;
		}
		
		if (isReferencingOperation()) {
			return this.referredOperation.equals(otherType.referredOperation);
		} else {
			for (MSignal parent : this.referredSignal.generalizationHierachie(true)) {
				if (parent.equals(otherType.referredSignal))
					return true;
			}
		}
		
		return false;
	}

	@Override
	public StringBuilder toString(StringBuilder sb) {
		if (this.isReferencingOperation()) {
			sb.append(this.referredOperation.name());
		} else {
			sb.append(this.referredSignal.name());
		}
		
		return sb;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MessageType)) {
			return false;
		}
		
		MessageType mt = (MessageType)obj;
		
		if (this.isReferencingOperation() != mt.isReferencingOperation()) {
			return false;
		}
		
		if (this.isReferencingOperation()) {
			return this.referredOperation.equals(mt.referredOperation);
		} else {
			return this.referredSignal.equals(mt.referredSignal);
		}
	}

	private int generateHash() {
		HashFunction hf = Hashing.md5();
		HashCode hc = hf.newHasher()
		       .putBoolean(isReferencingSignal())
		       .putString(isReferencingOperation() ? this.referredOperation.name() : "", Charsets.UTF_8)
		       .putString(isReferencingSignal() ? this.referredSignal.name() : "", Charsets.UTF_8)
		       .hash();
		
		return hc.asInt();
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public Set<Type> allSupertypes() {
		return null;
	}
}
