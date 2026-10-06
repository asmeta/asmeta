/** at every step increments the seconds 
*/
asm example

import ../../STDL/StandardLibrary


signature:
 enum domain Product = {ESPRESSO | CAPPUCCINO | LATTE}
 controlled available: Product -> Boolean
 controlled selected: Product

definitions:

 main rule r_Main =
  choose $c in Product with available($c) do
    selected := $c

default init s0:
 function available($c in Product) = true
 function selected = ESPRESSO