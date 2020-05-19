/** append in una sequenza di interi*/

asm AppendSeqTerm

import ../../../STDL/StandardLibrary

signature:
dynamic controlled succ_fibo: Seq(Integer)

definitions:

main rule r_Main =
succ_fibo := append(succ_fibo,4)

default init s0:
function succ_fibo = [1, 1]
