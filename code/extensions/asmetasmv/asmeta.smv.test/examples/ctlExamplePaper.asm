asm ctlExamplePaper
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
   enum domain EnumDom = {AA | BB}
   controlled foo: EnumDom -> Boolean
   monitored mon: Boolean

definitions:
   //true
   CTLSPEC ag(foo(AA) iff ax(not(foo(AA))))
   //true
   CTLSPEC ag(not(foo(AA)) iff ax(foo(AA)))
   //false. Gives counterexample.
   CTLSPEC not(ef(foo(AA) != foo(BB)))
   
   main rule r_Main = 
      par
         foo(AA) := not(foo(AA))
         if(mon) then
            foo(BB) := not(foo(BB))
         endif
      endpar

default init s0:
   function foo($x in EnumDom) = true