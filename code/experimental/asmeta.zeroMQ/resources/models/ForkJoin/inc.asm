asm inc
import StandardLibrary

signature:

monitored myinput : Integer
out funcInc: Integer
definitions:
 main rule r_Main =
 
 funcInc := myinput + 1