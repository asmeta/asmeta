package tgtlib.preferences;

/**
 * visits the preferences
 * 
 * @author garganti
 * 
 * @param <T>
 */
public interface PreferenceVisitor<T> {

	T forStringPreference(StringPreference sp);

	T forCheckedPref(CheckedPreference cp);

	T forChoicePref(ChoicePreference choicePreference);

	T forIntegerPreference(IntegerPreference integerPreference);

	<S> T forSubClassChoicePref(SubClassChoicePreference<S> cp);


}
