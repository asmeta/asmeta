/* This ASMETA specification represents a PillBox with the following characteristics:
* - The PillBox has 3 Drawers. In each drawer a specific type of Drug can be placed. Only a Pill type can 
 * 	 be in a single Drawer.
 * 
 * - For each Drawer, the PillBox has a switch that signal the opening status of the drawer (true, when 
 * 	 the drawer is open, false otherwise) and a RedLed that is turned on when it is time to take the 
 *   pill in that drawer.
 * 
 * - For each type of Pill, it is possible to set different deadlines during the day, meaning that the 
 *   same Drawer might be opened several times.
 * 
 * - The PillBox uses the time as an input, meaning that there is an external timer acting as the clock 
 * 	 of the system.
 * 
 * - The RedLed has several possible states, indicating deadlines:
 * 		+ It is OFF when it is not time to take the specific Pill in the Drawer
 * 		+ It is ON when it is time to take the specific Pill in the Drawer
 * 
 * - The PillBox works with TEN MINUTES timeouts:
 * 		+ When it is time to take the Pill, the RedLed stays on for 10 minutes
 * 
 * - Only a Drawer can have RedLed=ON per time. In case multiple pills hit their deadline at the same time
 * 	 (or when another pill is waiting to be taken) the PillBox randomly chooses the order in which the 
 *   pills have to be taken
 */ 