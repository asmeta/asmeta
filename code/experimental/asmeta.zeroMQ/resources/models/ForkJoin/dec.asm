asm dec
import StandardLibrary

signature:

monitored myinput : Integer
out funcDec: Integer
definitions:
 main rule r_Main =
 
 funcDec := myinput - 1