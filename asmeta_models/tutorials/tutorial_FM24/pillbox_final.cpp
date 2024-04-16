/* pillbox_final.cpp automatically generated from ASM2CODE */
#include "pillbox_final.h"

using namespace pillbox_finalnamespace;

/* Conversion of ASM rules in C++ methods */
void pillbox_final::r_reset (Drawer* _drawer){
	{ //par
		drawerLed[1][_drawer] = OFF;
		drugIndex[1][_drawer] = (drugIndex[0][_drawer] + 1);
		isPillTobeTaken[1][_drawer] = false;
	}//endpar
}
void pillbox_final::r_pillToBeTaken (Drawer* _drawer){
	{ //par
		if (! (isOn(_drawer))){ 
			r_reset_timer(tenMinutes);
		}
		drawerLed[1][_drawer] = ON;
	}//endpar
}
void pillbox_final::r_ON (Drawer* _drawer){
	if (isOn(_drawer)){ 
		if (expired(tenMinutes) | isPillTaken[_drawer]){ 
			r_reset(_drawer);
		}
	}
}
void pillbox_final::r_choosePillToTake(){
	vector<const Drawer*> point0;
	for(const auto& _drawer : Drawer::elems)
	if(isPillTobeTaken[0][_drawer] & isOff(_drawer) & ! (areOthersOn(_drawer))){
	point0.push_back(&(*_drawer));
	}
	int rndm = rand() % point0.size();
	{
	auto _drawer = *point0[rndm];
	  if(point0.size()>0){
		r_pillToBeTaken(&_drawer);
		 }
	}
}
void pillbox_final::r_setOtherDrawers(){
	for(auto _drawer : Drawer::elems)
		if(true){	
			{ //par
				if (pillDeadlineHit(_drawer)){ 
					isPillTobeTaken[1][_drawer] = true;
				}
				if (isThereAnyOtherDeadline(_drawer)){ 
					r_ON(_drawer);
				}
			}//endpar
		}
}
void pillbox_final::r_Main(){
	{ //par
		r_choosePillToTake();
		r_setOtherDrawers();
	}//endpar
}

/* Static function definition */
bool pillbox_final::isOn(Drawer* _d){return (drawerLed[0][_d] == ON);}
bool pillbox_final::isOff(Drawer* _d){return (drawerLed[0][_d] == OFF);}
bool pillbox_final::areOthersOn(Drawer* _d){return [&](){      /*<--- caseTerm*/ 
	if(_d==drawer1) 
		return isOn(drawer2) | isOn(drawer3);
	else if(_d==drawer2)
		return isOn(drawer1) | isOn(drawer3);
	else if(_d==drawer3)
		return isOn(drawer2) | isOn(drawer1);
   }();}
bool pillbox_final::pillDeadlineHit(Drawer* _d){return (time_consumption[0][_d][drugIndex[0][_d]] <= mCurrTimeSecs);}
bool pillbox_final::isThereAnyOtherDeadline(Drawer* _d){return (drugIndex[0][_d] < (unsigned int)((time_consumption[0][_d]).size()));}

/* Function and domain initialization */
pillbox_final::pillbox_final(): 
//Static domain initialization 
LedLights_elems({OFF,ON}), 
Drugs_elems({TYLENOL,ASPIRINE,MOMENT})
{
	/* Init static functions Abstract domain */
	tenMinutes = new Timer;
	drawer1 = new Drawer;
	drawer2 = new Drawer;
	drawer3 = new Drawer;
	/* Function initialization */
	for(const auto& _drawer : Drawer::elems){
		drawerLed[0].insert({_drawer,OFF});
		drawerLed[1].insert({_drawer,OFF});
	}
	for(const auto& _drawer : Drawer::elems){
	time_consumption[0].insert({_drawer,[&](){      /*<--- caseTerm*/ 
		if(_drawer==drawer1) 
			return vector<int>{60, 1200, 1800};
		else if(_drawer==drawer2)
			return vector<int>{2400, 3000, 3600};
		else if(_drawer==drawer3)
			return vector<int>{180, 1200, 1800};
	   }()});
	time_consumption[1].insert({_drawer,[&](){      /*<--- caseTerm*/ 
		if(_drawer==drawer1) 
			return vector<int>{60, 1200, 1800};
		else if(_drawer==drawer2)
			return vector<int>{2400, 3000, 3600};
		else if(_drawer==drawer3)
			return vector<int>{180, 1200, 1800};
	   }()});
	}
	for(const auto& _drawer : Drawer::elems){
	drug[0].insert({_drawer,[&](){      			/*<--- caseTerm*/ 
		if(_drawer==drawer1) 
			return TYLENOL;
		else if(_drawer==drawer2)
			return ASPIRINE;
		else if(_drawer==drawer3)
			return MOMENT;
	   }()});
	drug[1].insert({_drawer,[&](){      			/*<--- caseTerm*/ 
		if(_drawer==drawer1) 
			return TYLENOL;
		else if(_drawer==drawer2)
			return ASPIRINE;
		else if(_drawer==drawer3)
			return MOMENT;
	   }()});
	}
	for(const auto& _drawer : Drawer::elems){
		drugIndex[0].insert({_drawer,0});
		drugIndex[1].insert({_drawer,0});
	}
	for(const auto& _t : Timer::elems){
		duration[0].insert({_t,600});
		duration[1].insert({_t,600});
	}
	for(const auto& _t : Timer::elems){
		start[0].insert({_t,currentTime(_t)});
		start[1].insert({_t,currentTime(_t)});
	}
}

/* initialize controlled functions that contains monitored functions in the init term */
void pillbox_final::initControlledWithMonitored(){
}

/* Apply the update set */
void pillbox_final::fireUpdateSet(){
	drawerLed[0] = drawerLed[1];
	time_consumption[0] = time_consumption[1];
	drug[0] = drug[1];
	drugIndex[0] = drugIndex[1];
	isPillTobeTaken[0] = isPillTobeTaken[1];
	start[0] = start[1];
	duration[0] = duration[1];
}

/* init static functions and elements of abstract domains */
set< Drawer*>Drawer::elems;
Timer*pillbox_final::tenMinutes;
Drawer*pillbox_final::drawer1;
Drawer*pillbox_final::drawer2;
Drawer*pillbox_final::drawer3;

