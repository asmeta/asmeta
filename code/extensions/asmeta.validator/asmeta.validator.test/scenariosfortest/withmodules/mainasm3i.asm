// this asm imports the asm with the monitored function
asm mainasm3i
import ../StandardLibrary
import subdir/importedasm3
signature:
  controlled c_f : Boolean

definitions:
  main rule r_main = c_f := m_f3
  
