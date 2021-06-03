// this asm imports the asm with the monitored function + one mionitored of its own
asm mainasm2im
import ../StandardLibrary
import importedasm2
signature:
  controlled c_f : Boolean
  monitored m_f3 : Boolean

definitions:
  main rule r_main = c_f := m_f and m_f2 and m_f3
  
