package asmeta.asmetal2java.codegen.translator

/**
 * Util class which contains static methods to generate the class definition 
 * of the controlled and not-controlled functions
 */
class FunctionClassDef {
	
	/**
	 * Get the class definition for a controlled function with null domain and generic codomain D
	 */
	static def String getFun0CtrlClass(){
		return '''
					/**
					 * A generic controlled function class with null domain and codomain D.
					 * 
					 * @param <D> the type of the value to be managed, represents the Codomain of the function.
					 */
					class Fun0Ctrl<D> {
						
						private D currValue; // the current value
						private D newValue;  // the new value to be assigned
					
						/**
						* Sets the new value for this function.
						* 
						* <p>The provided value is stored as {@code newValue}, representing the value 
						* that will replace the current value {@code oldValue} in the next step.</p>
						*
						* @param d the new value to be prepared for assignment
						*/
						void set(D d) {
								newValue = d;
						}
						
						/**
						* Retrieves the function current value.
						* 
						* <p>The {@code currValue} represents the current state of the function before 
						* any pending updates to {@code newValue} are applied.</p>
						* 
						* @return the current value
						*/
						D get() {
							return currValue;
						}
						
						/**
						* Initializes both the current and new values with the provided value.
						* 
						* <p>This method is meant to be used to set the initial state of the function</p>
						* 
						* @param d the value to initialize both the current and new states
						*/
						void init(D d){
							currValue = newValue = d;
						}
						
					    /**
					     * Updates the current value with the new value.
					     * 
					     * <p>This method copies the {@code newValue} into {@code currValue}, 
					     * effectively promoting the new value to the current value.</p>
					     */
					    void update() {
					        currValue = newValue;
					    }
					}
				'''
	}
	
	/**
	 * Get the class definition for a controlled function with generic domain D and generic codomain C
	 */
	static def String getFunNCtrlClass(){
		return '''
					/**
					* A generic class for controlled functions with domain (not null) and codomain D -> C
					*
					*
					* @param <D> the type of the key in the mappings, represents the Domain of the function.
					* @param <C> the type of the value in the mappings, represents the Codomain of the function.
					*/
					static class FunNCtrl<D, C> {
						
						private Map<D, C> currValues = new HashMap<>(); // The current value map
						private Map<D, C> newValues = new HashMap<>();  // The new value map to be assigned
					
					    /**
					     * Sets a new value for a given key in the new values map.
					     * 
					     * <p>The provided key-value pair is stored in the {@code newValues} map, 
					     * representing the new state that will replace the current state in the next step.</p>
					     * 
					     * @param d the key for the new value
					     * @param c the new value to be associated with the key
					     */
						void set(D d, C c) {
							newValues.put(d, c);
						}
						
					    /**
					     * Retrieves the current value associated with a given key.
					     * 
					     * <p>The {@code currValues} map holds the current state of the key-value mappings.
					     * This method returns the value associated with the specified key in the current state.</p>
					     * 
					     * @param d the key whose associated value is to be returned
					     * @return the current value associated with the given key, or {@code null} if the key is not found
					     */
						C get(D d) {
							return currValues.get(d);
						}
						
						/**
						 * Initializes both the current and new maps with a single key-value pair.
						 * 
						 * <p>This method is meant to be used to set the initial state of the function.</p>
						 * 
						 * @param d the key to be added to both maps
						 * @param c the value to be associated with the key in both maps
					     */
						void init(D d, C c){
							currValues.put(d,c);
							newValues.put(d,c);
						}
						
					    /**
					     * Updates the current value with the new value.
					     * 
					     * <p>This method copies the {@code newValues} into {@code currValues}, 
					     * effectively promoting the new value to the current value.</p>
					     */
					    void update() {
					        currValues = newValues;
					    }
						
					}
				'''
	}
	
	/**
	 * Get the class definition for a non controlled function with null domain and generic codomain D
	 */
	static def String getFun0Class(){
		return 	'''
					/**
					 * A generic function class with null domain and codomain D.
					 * 
					 * @param <D> the type of the value to be managed, represents the Codomain of the function.
					 */
					class Fun0<D> {
						
						D value; // the current value
						
						/**
						* Sets the new value for this function.
						*
						* @param d the new value to be assigned
						*/
						void set(D d) {
							value = d;
						}
						
						/**
						* Retrieves the function current value.
						* 
						* @return the current value
						*/
						D get() {
							return value;
						}
					}
				'''
	}
	
	/**
	 * Get the class definition for a non controlled function with generic domain D and generic codomain C
	 */
	static def String getFunNClass(){
		return	'''
			/**
			* A generic class for functions with domain (not null) and codomain D -> C
			*
			*
			* @param <D> the type of the key in the mappings, represents the Domain of the function.
			* @param <C> the type of the value in the mappings, represents the Codomain of the function.
			*/
			class FunN<D, C> {
					
				Map<D, C> values = new HashMap<>();
				
				 /**
			     * Sets a new value for a given key in the new values map.
			     * 
			     * @param d the key for the new value
			     * @param c the new value to be associated with the key
			     */
				void set(D d, C c) {
					values.put(d, c);
				}
				
				/**
			     * Retrieves the current value associated with a given key.
			     * 
			     * @param d the key whose associated value is to be returned
			     * @return the current value associated with the given key, or {@code null} if the key is not found
			     */
				C get(D d) {
					return values.get(d);
				}
			}
			'''
	}
	
	
}