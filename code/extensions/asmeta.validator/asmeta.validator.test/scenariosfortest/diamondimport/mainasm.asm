// this asm imports the asm with the monitored function
asm mainasm
import ../StandardLibrary
import importedasmL
import importedasmR
signature:
  controlled c_f : Boolean

definitions:
    main rule r_main = c_f := m_f and m_fr and m_fl
  