package tgtlib.util.combinatorial;
 
import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * 
 * generates a combiantion iwth ripetition
 * 
 * From http://forums.sun.com/thread.jspa?threadID=5300684
 * 
 * TODO make iterable as CombiantionGenerator
 * 
 * @author garganti
 *
 * @version $Revision: 1.0 $
 */
public class CombinationRepetition<T> {
 
    private ArrayList<T[]> alist = new ArrayList<T[]>();
    
    // necessary to create arrays
    
    Class<T> clazz;
 
    /**
     * Constructor for CombinationRepetition.
     * @param tClazz Class<T>
     * @param objs T[]
     * @param p int
     */
    public CombinationRepetition(Class<T> tClazz, T[] objs, int p) {
 
        int slotIndex = 0;
        int maxValue = objs.length - 1;
        int[] array = new int[p];
        clazz = tClazz;
        fillSlot(objs, array, slotIndex, maxValue);
        
    }
 
    
    /**
     * Method fillSlot.
     * @param obj T[]
     * @param array int[]
     * @param slotIndex int
     * @param maxValue int
     */
    private void fillSlot(T[] obj, int[] array, int slotIndex, int maxValue) {
 
        if (slotIndex == array.length) {
            // print the combo
            //   CombinationRepetition.spewArray(alist,obj,a); no need anymore
            T[] tempobj = (T[]) Array.newInstance(clazz,array.length);
            for (int i = 0; i < tempobj.length; i++) {
                int index = array[i];
                tempobj[i] = obj[index];
            }
            alist.add(tempobj);
        } else {
            int minValue = 0;
            if (slotIndex > 0) {
                minValue = array[slotIndex - 1];
            }
            for (int i = minValue; i <= maxValue; i++) {
 
                array[slotIndex] = i;
                fillSlot(obj, array, slotIndex + 1, maxValue);
            }
        }
    }

    /**
	
	 * @return the alist */
	public ArrayList<T[]> getAlist() {
		return alist;
	}
 
}