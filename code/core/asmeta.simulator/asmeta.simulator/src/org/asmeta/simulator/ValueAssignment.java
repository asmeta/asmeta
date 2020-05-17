/*******************************************************************************
 * Copyright (c) 2005, 2006 ASMETA group (http://asmeta.sourceforge.net)
 * License Information: http://asmeta.sourceforge.net/licensing/
 * 
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2 as
 *   published by the Free Software Foundation.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 *   USA
 * 
 *   http://www.gnu.org/licenses/gpl.txt
 * 
 *   
 *******************************************************************************/

/*
 * VariableValueAssignment.java
 *
 * Created on 31 maggio 2006, 9.16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;

import org.asmeta.simulator.value.Value;


/**
 *  Assignment between variables and values.
 *  
 */
public class ValueAssignment extends Assignment<Value> {
	
	/**
     * Creates an empty assignment.
     * 
     */
    public ValueAssignment() {
    }

    /**
     * Copy constructor.
     * 
     */
    public ValueAssignment(ValueAssignment assignment) {
    	super(assignment);
    }
    
    @Override
	public String toString(){
    	return assignmentMap.toString();
    }
                
}
