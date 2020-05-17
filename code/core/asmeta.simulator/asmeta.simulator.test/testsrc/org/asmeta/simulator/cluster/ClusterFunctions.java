/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.cluster;

import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.Value;

public class ClusterFunctions {

	// increase or dcrease by adding or deletin $p to $c
	 //let ($tmp = if contains($c,$p) then - 1.0 else 1.0 endif) in    
    //  ntor(size($c)) / ( ntor(size($c)) + $tmp ) * distance2($p,center($c))
/*	 static RealValue newSSR(org.asmeta.simulator.value.SetValue sv, org.asmeta.simulator.value.TupleValue tv){
	       
		 int tmp = sv.getValue().contains(tv) ?  -1: 1;
		 sim.getCurrentState().
	 }*/
	
	/*
	   // || p1 -p2 ||^2 
	   function distance2($p1 in Point, $p2 in Point) =
			square(x($p1) - x($p2)) + square( y($p1) - y($p2 ))

	*/	
	static public RealValue distance2(org.asmeta.simulator.value.TupleValue<Double> tv1, org.asmeta.simulator.value.TupleValue<Double> tv2){
		double dx = tv1.getValue().get(0).getValue() - (tv2.getValue().get(0).getValue());  
		double dy = tv1.getValue().get(1).getValue() - (tv2.getValue().get(1).getValue());
		return new RealValue(dx * dx + dy * dy);
		
	}
	
	/*	function newSSRC($c in Cluster, $p in Point, $cp in Point) =
  	 let ($tmp = if contains($c,$p) then - 1.0 else 1.0 endif) in    
      (ntor(size($c)) / ( ntor(size($c)) + $tmp )) * distance2($p,$cp) 
    endlet*/

	static public Value newSSRC(SetValue c, TupleValue p, TupleValue cp){
		 int tmp = c.getValue().contains(p) ?  -1: 1;
		 int size = c.getValue().size();
		 double d2 = distance2(p,cp).getValue();
		 return new RealValue(((double)size / (size + tmp)) * d2);
	}
	
	
	static public Value removing(SetValue sv, org.asmeta.simulator.value.TupleValue tv){
		sv.getValue().remove(tv);
		return sv;
	}
	
	
	static public Value adding(SetValue sv, org.asmeta.simulator.value.TupleValue tv){
		sv.getValue().add(tv);
		return sv;
	}
	
	
/*   	// sum of the Points in a Cluster --> Point   	
   	function sumPoints($c in Cluster) =
      if isEmpty($c) then (0.0,0.0)
      else  let ($c_seq =  asSequence($c), $one = first($c_seq)) in      			
      		(x($one) + x(sumPoints(asSet(tail($c_seq)))), y($one) + y(sumPoints(asSet(tail($c_seq)))) )
      		endlet
      endif */

	static public TupleValue sumPoints(SetValue sv){
		double dx = 0.0, dy = 0.0;
		for(Object v: sv.getValue()){
			dx += ((RealValue)((TupleValue)v).getValue().get(0)).getValue();
			dy += ((RealValue)((TupleValue)v).getValue().get(1)).getValue();
		}
		return new TupleValue(new RealValue(dx),new RealValue(dy));
	}

}
