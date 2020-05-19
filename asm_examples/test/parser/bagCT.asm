asm bagCT

import ../../STDL/StandardLibrary

signature:

       static fooBag: Bag(Integer)
       static fooBagCT: Bag(Integer)

definitions:

		function fooBag = <1,2,2>

		function fooBagCT = <$o in <1,2,2> : $o>
       
default init s0:
