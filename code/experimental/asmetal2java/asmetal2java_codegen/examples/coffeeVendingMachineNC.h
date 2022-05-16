// coffeeVendingMachineNC.h automatically generated from ASMETA2CODE
#ifndef COFFEEVENDINGMACHINENC_H
#define COFFEEVENDINGMACHINENC_H

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
namespace coffeeVendingMachineNCnamespace {
enum CoinType {
	HALF, ONE
};

enum Product {
	COFFEE, TEA, MILK
};

typedef int QuantityDomain;

typedef int CoinDomain;

}

using namespace coffeeVendingMachineNCnamespace;

class coffeeVendingMachineNC {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<CoinType> CoinType_elems;

	const set<Product> Product_elems;

	const set<QuantityDomain> QuantityDomain_elems;

	const set<CoinDomain> CoinDomain_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	map<Product, QuantityDomain> available[2];
	CoinDomain coins[2];
	CoinType insertedCoin;
	Product chosenProduct;
	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_serveProduct(Product _p);
	void r_Main();

	coffeeVendingMachineNC();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

