package tgtlib.definitions.expression.type;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * types defined by its elements (like enumerative, bool)
 * 
 * @author garganti
 * 
 */
public abstract class ElementsType extends EnumerableType {

	/** The elements. */
	List<EnumConst> elements;

	public ElementsType(String _name) {
		super(_name);
		this.elements = new Vector<EnumConst>();
	}

	/**
	 * Add a new element in the enumeration. warning this creates a new ID at
	 * every invocation
	 * 
	 * @deprecated use addEnumConst instead
	 * @param _name
	 *            the _name
	 */
	@Deprecated
	public void addElement(String _name) {
		addElement(new EnumConstCreator().createEnumConst(_name));
	}

	/**
	 * Add a new element in the enumeration.
	 * 
	 * @param _name
	 *            the _name
	 */
	public void addElement(EnumConst ec) {
		this.elements.add(ec);
		ec.setType(this);
	}

	/**
	 * All the elements in the enumeration.
	 * 
	 * @return the list< enum const>
	 */
	@Override
	public List<EnumConst> allElements() {
		return Collections.unmodifiableList(this.elements);
	}

	/*
	 * (non-Javadoc) : get the number of elements
	 * 
	 * @see atgt.specification.type.Type#range()
	 */
	@Override
	public int range() {
		return this.elements.size();
	}
}
