// Bare.h automatically generated from ASMETA2CODE
#ifndef BARE_H
#define BARE_H

//Arduino.h uses WString instead...
#include<string>
typedef std::string String;
#include<iostream> 
#include<vector> 
using namespace std;
#include <set>
#include <map>
#include <list>
#include <bits/stl_tree.h>
#include <boost/tuple/tuple.hpp>
#define ANY String



/////////////////////////////////////////////////
/// DOMAIN DEFINITIONS
/////////////////////////////////////////////////
/* Domain definitions here */
namespace Barenamespace{
	enum DomainEnumerative {DD, CC, EE};
	
	typedef int D1;
	
	typedef std::tuple<string, std::list<int>, D1> D2;
	
	typedef std::list<std::tuple<string, int, string>> D3;
	
	typedef std::tuple<string, D1> D4;
	
	typedef string D5;
	
	typedef std::tuple<string, string> D6;
	
	typedef std::list<std::list<int>> D7;
	
	typedef int D8;
	
}

using namespace Barenamespace;

class Bare{
  
/////////////////////////////////////////////////
/// DOMAIN CONTAINERS
/////////////////////////////////////////////////
/* Domain containers here */
const std::set<DomainEnumerative> DomainEnumerative_elems;

const std::set<D1> D1_elems;

const std::set<D2> D2_elems;

const std::set<D3> D3_elems;

const std::set<D4> D4_elems;

const std::set<D5> D5_elems;

const std::set<D6> D6_elems;

const std::set<D7> D7_elems;

std::set<D8> D8_elems;

public:
/////////////////////////////////////////////////
/// FUNCTIONS
/////////////////////////////////////////////////
static D1 staticFunction();

static std::tuple<int, int> staticFunction2();

static std::tuple<int, D1> staticFunction4();

static int staticFunction3 (int param0_staticFunction3);
static int staticFunction5 (std::list<std::tuple<int, int>> param0_staticFunction5);
int controlledFunction1[2];
std::map<int, int> controlledFunction2[2];
std::map<std::tuple<D1, D1, D1>, int> controlledFunction3[2];
string controlledFunction4[2];
D1 controlledFunction5[2];
int derivedFunction();

////////////////////////////////////////////////
/// RULE DEFINITION
/////////////////////////////////////////////////
/* Rule definition here */
void r_Parameter (int _param, D5 _param2);
void r_skipprova();
void r_Main();

Bare();

void getInputs();

void setOutputs();

void fireUpdateSet();

};
#endif

