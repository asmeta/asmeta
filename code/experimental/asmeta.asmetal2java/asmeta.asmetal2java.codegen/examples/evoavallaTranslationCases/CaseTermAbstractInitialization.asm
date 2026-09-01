asm CaseTermAbstractInitialization

import ../STDL/StandardLibrary

signature:
	abstract domain User
	abstract domain Film
	controlled association: User -> Film
	static user1: User
	static user2: User
	static film1: Film

definitions:
	main rule r_Main =
		skip

default init s0:
	function association($user in User) =
		switch($user)
			case user1: film1
			case user2: undef
			otherwise undef
		endswitch
