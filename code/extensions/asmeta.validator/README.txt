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

== validatore vero e proprio ==
asmeta.validator   POM e CI
asmeta.validator.feature POM e CI
asmeta.validator.test
asmeta.validator.ui  ==> interfaccia per eclipse

== definizione del linguaggio per avalla con xtext ==
org.asmeta.avallaxt   POM e CI
org.asmeta.avallaxt.feature
org.asmeta.avallaxt.ide
org.asmeta.avallaxt.repository
org.asmeta.avallaxt.tests  POM e CI
org.asmeta.avallaxt.ui
org.asmeta.avallaxt.ui.tests
