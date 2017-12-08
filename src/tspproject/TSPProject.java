/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspproject;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author eddie
 */
public class TSPProject {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {    
        
        //GUI gui = new GUI();
        //gui.setVisible(true);

        myGUI g = new myGUI();
            
       /* CityList CL = new CityList(1000,1000){{
            addCity(500, 500, "Central City");
            addCity(500, 0, "North City");
            addCity(500, 1000, "South City");
            addCity(0, 500, "West City");
            addCity(1000, 500, "East City");
            addCity(485, 241, "Paris");
            addCity(671, 707, "Lyon");
            addCity(500, 950, "Marseille");
            addCity(48, 200, "Brest");
            addCity(142, 896, "Pau");
            addCity(550, 10, "Lille");
        }};*/
        
      
        
            
    }
    
    
    private void mainTest(CityList CL){
        final int noOfSol = 50;
        double minimal = 0;
        int countMinimal = 0;
        int startingCity = (int)(Math.random()*( CL.size()-0));
        startingCity = 5;
        //        ShorterResearch sh = new ShorterResearch(noOfSol, (int)(Math.random()*( CL.size()-0)), CL, minimumDistance);
        BestRouteSearcher BRS = new BestRouteSearcher(noOfSol, startingCity, CL);
        //while(BRS.getNumberOfGeneration() < 1000 && BRS.getLowestDistance().getDistance() > minimumDistance){
        while(countMinimal < 30){
        //System.out.println(BRS.getGenerations());
            BRS.nextGeneration();
            System.out.println(BRS.getLowestDistance().getDistance());
            if(BRS.getLowestDistance().getDistance() == minimal){
                countMinimal++;
            }
            else{
                countMinimal = 0;
                minimal = BRS.getLowestDistance().getDistance();
            }
        }
        System.out.println(BRS.getGenerations());
        System.out.println(BRS.getNumberOfGeneration());
    }
    
    private void tests(CityList CL){
        System.out.println("_____Villes_____");
        System.out.println(CL.getCityListInfo());
                
        System.out.println("_____Routes_____");        
        ArrayList<Integer> seq = new ArrayList(){{
            add(0);
            add(2);
            add(1);
            add(4);
        }};
        System.out.println("_____Details");  
//        System.out.println(routeUtility.getRouteDetails(seq, CL));
        for(int i = 0;i<seq.size()-1;i++){
            System.out.println(CL.getDistance(seq.get(i), seq.get(i+1)));
        }
        System.out.println("_____Total");
//        double routeLength = routeUtility.getRouteLength(seq, CL);
//        System.out.println(routeLength);
    }
    
}
