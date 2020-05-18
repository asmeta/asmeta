//AG 1 maggio 08: dovrebbe parsare rule domain??
// 

asm test3

	import ../../STDL/StandardLibrary
	
signature:
	
	dynamic abstract domain Memory
	
	controlled buffer: Integer
	controlled m_mem: Prod(Memory, Integer) -> Integer
	//controlled foo: Prod(Memory, Integer, Integer) -> Rule
	controlled foo: Memory -> Rule(Memory, Integer, Integer)
        controlled foo1: Memory -> Rule
        controlled foo2: Rule(Integer,Integer)
	
	controlled step: Integer
	
	static mem1: Memory
	static mem2: Memory

definitions:

	macro rule r_read($mem in Memory, $data in Integer, $address in Integer) =
		$data := m_mem($mem, $address)

	macro rule r_write($mem in Memory, $data in Integer, $address in Integer) =
		m_mem($mem, $address) := $data

        macro rule r_do_nothing = skip
        macro rule r_do_nothing1($x in Integer, $y in Integer) = skip

		
	main rule r_main =
		if step = undef then seq
			// problema 1: valori fasulli durante l'assegnamento
			//foo(mem1, 0, 0) := <<r_write[mem1, buffer, 0]>>
			foo(mem1) := <<r_write>>
			//foo(mem2, 0, 0) := <<r_read[mem2, buffer, 0]>>
			foo(mem2) := <<r_read>>
			step := 0
                        foo1(mem1):= <<r_do_nothing>>
                        foo2:=<<r_do_nothing1>>
		endseq else seq
			// problema 2: i valori fasulli sono ora quelli che mantengono l'associazione
			//foo(mem2, buffer, 0)
			foo(mem2)[mem2, buffer, 0]
                        r_read[mem1, buffer, 1] //macro call rule
                        r_do_nothing[]  //macro call rule  
                        foo1(mem1)
                        foo2[2,3] 
			skip
		endseq endif
		
default init s0:

	function m_mem($mem in Memory, $address in Integer) = 123
	
	
