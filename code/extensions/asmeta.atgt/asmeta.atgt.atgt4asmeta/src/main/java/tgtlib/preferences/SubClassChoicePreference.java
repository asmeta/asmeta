package tgtlib.preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * per scegliere una sottoclasse rispetto una classe la scalte è tra le classi e
 * non tra le istanze
 * 
 * @author garganti
 * 

 * @version $Revision: 1.0 $
 */
public class SubClassChoicePreference<T> extends SimplePreference {

	List<Class<? extends T>> subclasses;

	/**
	 * Constructor for SubClassChoicePreference.
	 * @param key String
	 * @param description String
	 */
	public SubClassChoicePreference(String key, String description) {
		super(key, null, description);
		subclasses = new ArrayList<Class<? extends T>>();
	}

	/**
	 * Constructor for SubClassChoicePreference.
	 * @param key String
	 * @param description String
	 * @param e Class<E>
	 */
	public <E extends T> SubClassChoicePreference(String key,
			String description, Class<E> e) {
		super(key, null, description);
	}

	/**
	 * Method addSubClass.
	 * @param e Class<E>
	 */
	public <E extends T> void addSubClass(Class<E> e) {
		if (subclasses.contains(e))
			throw new RuntimeException("class " + e + "already present");
		subclasses.add(e);
	}

	/**
	 * returns the value selected
	 * 
	 * @return Class<? extends T>
	 */
	@Override
	public Class<? extends T> getValue() {
		return findClass(super.getStringValue());
	}

	/**
	 * set the subcalss to be used by its simple name
	 * 
	 * @param val String
	 */
	@Override
	public void setValue(String val) {
		if (findClass(val) != null)
			super.setValue(val);
		else
			throw new ChoiceNotValidException("choice not permitted");
	}

	/**
	 * Method setValue.
	 * @param c Class<E>
	 */
	public <E extends T> void setValue(Class<E> c) {
		if (!subclasses.contains(c))
			super.setValue(c.getSimpleName());
		else
			throw new ChoiceNotValidException("choice not permitted");
	}

	/**
	 * Method accept.
	 * @param prefVisitor PreferenceVisitor<T>
	 * @return T
	 */
	@Override
	public <T> T accept(PreferenceVisitor<T> prefVisitor) {
		return prefVisitor.forSubClassChoicePref(this);
	}

	/**
	 * finds a class by its simple name
	 * 
	 * @param name
	
	 * @return Class<? extends T>
	 */
	private Class<? extends T> findClass(String name) {
		for (Class<? extends T> c : subclasses)
			if (c.getSimpleName().equals(name))
				return c;
		return null;
	}
}
