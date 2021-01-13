// this asm imports the asm with the monitored function
asm mainasm2i
import ../StandardLibrary
import importedasm2
signature:
  controlled c_f : Boolean

definitions:
  main rule r_main = c_f := m_f and m_f2
  
