module vector
	
	import ../../STDL/StandardLibrary
	export *
	
signature:
	
	dynamic abstract domain IntVector
	
	controlled m_vector: Prod(IntVector, Integer) -> Integer
	
definitions:
	
