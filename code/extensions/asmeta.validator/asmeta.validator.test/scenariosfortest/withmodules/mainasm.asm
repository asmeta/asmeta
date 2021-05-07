// this asm imports the asm with the monitored function
asm mainasm
import ../StandardLibrary
import importedasm
signature:
  controlled c_f : Boolean

definitions:
//  main rule r_main = c_f := m_f
    main rule r_main = c_f := not m_f
  