package org.tzi.use.gen.tool;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

/**
 * This class represents a Procedure Signature
 * and can be used to determine if one signature
 * conforms to another. Inheritance is supported
 * @author Lars Hamann
 */
public class GSignature {

	private String procedureName;
	private List<Type> types;
	
	public GSignature(String procedureName, List<Type> parameterTypes)
	{
		this.procedureName = procedureName;
		this.types = new ArrayList<Type>(parameterTypes);
	}

	public String getProcedureName() {
		return procedureName;
	}

	public List<Type> getTypes() {
		return types;
	}

	public boolean conformsTo(GSignature sig)
	{
		// The signature can only conform to sig, if
		// name and count of types are equal.
		// This function supports overloading. USE in general not.
		if (this.procedureName.equals(sig.getProcedureName()) &&
			this.types.size() == sig.getTypes().size())
		{
			List<Type> sigTypes = sig.getTypes();
			
			for(int index = 0; index < this.types.size(); index++)
			{
				if (!sigTypes.get(index).conformsTo(types.get(index)))
					return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "procedure " + 
				this.procedureName + "(" + 
				StringUtil.fmtSeq(this.types.iterator(), ",") + 
				")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((procedureName == null) ? 0 : procedureName.hashCode());
		result = prime * result + ((types == null) ? 0 : types.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GSignature other = (GSignature) obj;
		if (procedureName == null) {
			if (other.procedureName != null)
				return false;
		} else if (!procedureName.equals(other.procedureName))
			return false;
		if (types == null) {
			if (other.types != null)
				return false;
		} else if (!types.equals(other.types))
			return false;
		return true;
	}

	/**
	 * @param procedures
	 * @return
	 */
	public GProcedure findMatching(List<GProcedure> procedures) {
		for (GProcedure proc : procedures) {
            if (proc.getSignature().conformsTo(this)) {
            	return proc;
            }
        }
		
		return null;
	}
}
