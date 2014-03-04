package pfc.blast.frontend.testing;

import java.util.LinkedList;
import java.util.List;

import pfc.blast.frontend.RangeCreator;
import pfc.blast.frontend.RangeCreatorDynamicLoad;
import pfc.blast.frontend.RangeCreatorFixedLoad;
import pfc.blast.frontend.RangeCreatorSequential;
import pfc.blast.frontend.RangeCreatorStaticLoad;

public class RangeTest {

    public static void main(String args[]){
        
        System.out.println("**********************");
        System.out.println("******* TEST 1 *******");
        System.out.println("**********************");
        
        RangeCreator rangeTest1 = new RangeCreatorSequential(6298);
        while(!rangeTest1.isCompleted()){
            System.out.println(rangeTest1.getNextRange());    
        }
        
              
        System.out.println("**********************");
        System.out.println("******* TEST 2 *******");
        System.out.println("**********************");
        
        RangeCreator rangeTest2 = new RangeCreatorStaticLoad(6298,10);
        while(!rangeTest2.isCompleted()){
            System.out.println(rangeTest2.getNextRange());    
        }
        
        
        System.out.println("**********************");
        System.out.println("******* TEST 3 *******");
        System.out.println("**********************");
        
        RangeCreator rangeTestFinal3 = new RangeCreatorDynamicLoad(6298, 3);
        while(!rangeTestFinal3.isCompleted()){
            System.out.println(rangeTestFinal3.getNextRange());    
        }


        System.out.println("**********************");
        System.out.println("******* TEST 4 *******");
        System.out.println("**********************");
        
        RangeCreator rangeTestFinal4 = new RangeCreatorFixedLoad(6298, 3);
        while(!rangeTestFinal4.isCompleted()){
            System.out.println(rangeTestFinal4.getNextRange());    
        }
        
    }
}
