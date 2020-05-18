// Bare.cpp automatically generated from ASM2CODE

#include "Bare.h"
using namespace Barenamespace;

// Conversion of ASM rules in C++ methods
void Bare::r_Parameter (int _param, D5 _param2){
	{ //par
		if ((controlledFunction1[0] < 5)){ 
			controlledFunction3[1][std::make_tuple(1, 1, 1)] = 5;
		}
		if ((staticFunction() < 5)){ 
			; 
		}
		if ((controlledFunction4[0] != "prova")){ 
			; 
		}
		if ((controlledFunction5[0] == 1)){ 
			; 
		}
		if ((controlledFunction3[0][std::make_tuple(1, 1, 1)] == 5)){ 
			; 
		}
	}//endpar
}
void Bare::r_skipprova(){
	; 
}
void Bare::r_Main(){
	r_skipprova();
}

// Static function definition
D1 Bare::staticFunction(){return 5;}
std::tuple<int, int> Bare::staticFunction2(){return std::make_tuple(2, 5);}
int Bare::staticFunction3(int _a){return (10 + _a);}
int Bare::staticFunction5(std::list<std::tuple<int, int>> _x){return (10 + 5);}
int Bare::derivedFunction(){return (10 + staticFunction3(5));}

// Function and domain initialization
Bare::Bare(): 
// Static domain initialization 
D1_elems(std::set<int>{1, 2, 3}), 
D2_elems(std::set<std::tuple<string, std::list<int>, int>>{std::make_tuple("ciao", std::list<int>{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20}, 1), std::make_tuple("prova", std::list<int>{2}, 3)}), 
D5_elems(std::set<string>{"ciao", "prova", "casa"}), 
D4_elems(std::set<std::tuple<string, int>>{std::make_tuple("ciao", 2), std::make_tuple("prova", 6)}), 
D6_elems(std::set<std::tuple<string, string>>{std::make_tuple("ciao", "ciao"), std::make_tuple("prova", "prova")}), 
D3_elems(std::set<std::list<std::tuple<string, int, string>>>{std::list<std::tuple<string, int, string>>{std::make_tuple("prova", 4, "prova2"), std::make_tuple("provax", 5, "provax2")}, std::list<std::tuple<string, int, string>>{std::make_tuple("prova1", 4, "prova12"), std::make_tuple("provax1", 5, "provax12")}}), 
D7_elems(std::set<std::list<std::list<int>>>{std::list<std::list<int>>{std::list<int>{1, 5, 2}, std::list<int>{5, 9, 8}, std::list<int>{5, 8}}}), 
DomainEnumerative_elems({DD,CC,EE})
{
	// Dynamic domain initialization 
	D8_elems=std::set<int>{1, 2, 5};
	//Function initialization
	controlledFunction1[0] = controlledFunction1[1] = 5;
	controlledFunction4[0] = controlledFunction4[1] = "provafunzione";
	for(auto const& _x : D1_elems){
	for(auto const& _y : D1_elems){
	for(auto const& _z : D1_elems){
	controlledFunction3[0].insert({std::make_tuple(_x,_y,_z),((_x + _y) + _z)});
	controlledFunction3[1].insert({std::make_tuple(_x,_y,_z),((_x + _y) + _z)});
	}}}
}

// Apply the update set
void Bare::fireUpdateSet(){
	controlledFunction1[0] = controlledFunction1[1];
	controlledFunction2[0] = controlledFunction2[1];
	controlledFunction3[0] = controlledFunction3[1];
	controlledFunction4[0] = controlledFunction4[1];
	controlledFunction5[0] = controlledFunction5[1];
}

