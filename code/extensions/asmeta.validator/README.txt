ANGELO
MAGGIO 18
PORTING DI AVALLA TO XTEXT

org.asmeta.avallaxt --> language definition [su svn]
org.asmeta.avallaxt.feature --> language feature [non su svn?]
org.asmeta.avallaxt.ide
org.asmeta.avallaxt.repository
org.asmeta.avallaxt.tests

NON SU SVN?
org.asmeta.avallaxt.ui
org.asmeta.avallaxt.ui.tests


VALIDATOR - porting da versione prececente

org.asmeta.avallaxt.validator --> praticamente identico per ora al validator classico 
org.asmeta.avallaxt.validator.test  che contiene i casi di test di del validator

asmeta.validator.ui -> versione identica a quello vecchio solo che chiama il validator nuovo

AUGUST 2023
Angelo: la maggior parte dei plugin viene compilata solo con PDS, con maven non funziona, questo validatore è un esempio
- l aprima soluzione è duplicare le dipendenze sia nel manifest che nel pom, in questo mode entrambi andranno

NOVEMBRE 23:

asmeta.avallaxt.parent --> questo è un tentativo si usar ela nuova piattaforma xtext + tyco per il build nella sudir ci sono tutti i codici

== validatore vero e proprio ==
asmeta.validator
asmeta.validator.feature
asmeta.validator.test
asmeta.validator.ui  ==> interfaccia per eclipse

== definizione del linguaggio per avalla con xtext ==
org.asmeta.avallaxt
org.asmeta.avallaxt.feature
org.asmeta.avallaxt.ide
org.asmeta.avallaxt.repository
org.asmeta.avallaxt.tests
org.asmeta.avallaxt.ui
org.asmeta.avallaxt.ui.tests
