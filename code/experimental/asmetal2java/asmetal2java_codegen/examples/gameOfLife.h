// gameOfLife.h automatically generated from ASMETA2CODE
#ifndef GAMEOFLIFE_H
#define GAMEOFLIFE_H

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
namespace gameOfLifenamespace {
typedef unsigned int RowsWorld;

typedef unsigned int ColumnsWorld;

}

using namespace gameOfLifenamespace;

class gameOfLife {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<RowsWorld> RowsWorld_elems;

	const set<ColumnsWorld> ColumnsWorld_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	map<tuple<RowsWorld, ColumnsWorld>, bool> alive[2];
	int aliveNeighb(RowsWorld param0_aliveNeighb,
			ColumnsWorld param1_aliveNeighb);
	set<tuple<RowsWorld, ColumnsWorld>> neighbours(RowsWorld param0_neighbours,
			ColumnsWorld param1_neighbours);
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_conway(RowsWorld _r, ColumnsWorld _c);
	void r_Main();

	gameOfLife();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

