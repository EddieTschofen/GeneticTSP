/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depreciated;

import java.util.ArrayList;
import tspproject.CityList;

/**
 *
 * @author eddie
 */
public final class routeUtility {   
    
    /**
     * return the sum of the distances between the cities
     * @param seq
     * @param CL
     * @return 
     */
    public static double getRouteLength(ArrayList<Integer> seq, CityList CL){
        double length = 0.0;
        for(int i = 0;i<seq.size()-1;i++){
            length += CL.getDistance(seq.get(i), seq.get(i+1));
        }
        return length;
    }
    
    /**
     * get the route detail
     * @param seq
     * @param CL
     * @return 
     */
    public static String getRouteDetails(ArrayList<Integer> seq, CityList CL){
        String str = "";
        for(int i = 0;i<seq.size();i++){
            str += CL.getCity(seq.get(i)).toString() + "\n";
        }
        return str;
    }
}
