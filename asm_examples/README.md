## asm_examples: examples of Asmeta specifications

#### simple generic examples (not case studies) are under *examples* directory

examples: real examples (parser OK, simulator all OK). 
          it can contain sub directories - distributed as examples on the web site

simple_ex: mono agent (simple) asm specs, accepted by the parser and simulated by the interpreter -very simple examples
	models: meaningful specs of complex systems (but still mono agent)
	agents: multi agent asm specs
	... other directories for particular examples (e.g. oneTrafficLight, production cell, ...)
	metamodel: the metamodels
	
In examples there are also some case studies.	

#### standard libraries 
STDL: standard library


#### some case studies

We have added also some case studies used in papers

   * **PHD**
   * **PillBox**
   * **stereoacuity**
 

##### specification used for testing purposes

test: 	specifications used to test asmeta (to validate, to check coverage, ...)
		normally they are used only internally to check the code, but the user can take a look at them to understand
		particular features (or constructs).
		The name of the specs should be meaningful

it can contain subdirectories:
		
env: all the environment files used to run the tests 

simulator: all the specs used to test the simulator with JUnit or Fit (the test consists in checking the final state)
			...
parser: to test only the parser (the result of the test is "pass"), with subdirs:
		 	rules: several kinds of rules
		 	domains: several kinds of domains
  		 	terms: terms
  		 	...

errors: spec with errors, used to test asmeta. The faults are in the specs and the parser or simulator can 
				correctly detect them.
		 np: wrong specs correctly rejected by the parser (to exercise the parser and get the coverage)
		 rpns_w semantically wrong spec (but syntactically correct i.e. accepted by the parser), the interpreter won't interpret them
		 rpns_r: the interpreter must be extended (e.g. Turbo specs)


errors:  (to be emptied) : specs used to show bugs in asmeta, which should be fixed soon (not a missing feature)
			it contains the subdirectories:
			wp: wrong specs but still accepted by the parser
			wnp_w:  rejected by the parser but not for the right reason (the parser fails to find the error)
			rnp: (almost) correct specs still rejected by the parser
 			rpns: correct spec, correct by the parser, but not by the interpreter (and the interpreter should simulate them)
 			rpws: correct spec for compiler, wrong for simulation but the simulator still interprets them 
	(w: wrong r: right syntactically / p: parser accepts, np: it does not/ s: simulator accepts, ns: it does not)
