asm Clock
import StandardLibrary
import CTLLibrary
signature:
domain Second subsetof Integer
domain Minute subsetof Integer
domain Hour subsetof Integer
monitored signal: Boolean
controlled sec: Second
controlled min: Minute
controlled h: Hour
definitions:
domain Second = {0 : 59}
domain Minute = {0 : 59}
domain Hour = {0 : 23}
macro rule r_IncMinHours = par
if min = 59 then h := (h + 1) mod 24 endif
min := (min + 1) mod 60 endpar

ctlspec ag (eq(min,59) implies ax(eq(min,0)))

main rule r_Main = if signal then par
if sec = 59 then r_IncMinHours[] endif
sec := (sec + 1) mod 60 endpar endif
default init s0:
function sec = 0
function min = 0
function h = 0