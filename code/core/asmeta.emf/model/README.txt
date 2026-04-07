
- the reference model is AsmM.ecore model and the genmodel for the main packages

- with the temporal properties extends the 

- ALTENRATIVE WORKING

USE directly the AsmM_all that cotains all !
this is the solution adopted so far

- validation classes are no longer generated TO CHECK WHY???

ATTENZIONE: alcune classi sono modificate ma non in modo proprio:

(ad esempio AnyDomain)

BasictermsFactory --> added a method to create a boolean term from a Boolean
StructureFactory --> create Asm createAsm(String name, boolean isAsyncr);

// TODO move this methods to an (EMF or Parser) utility or comment with @generated (or alike)