#include"CarSystem001main.h"
void setup(){
}

CarSystem001main carSystem001main;
			
void loop(){
  carSystem001main.getInputs();
  carSystem001main.r_Main();
  carSystem001main.fireUpdateSet();
  carSystem001main.setOutputs();
}

