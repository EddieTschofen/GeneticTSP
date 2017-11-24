/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depreciated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import tspproject.CityList;

/**
 *
 * @author eddie
 */
public class ShorterResearch {
    /*
        First Level : All the generations
        2nd level : A single generation
        3rd level : a possible solution
    */
    private final ArrayList<HashMap<ArrayList<Integer>,Double>> generations;
    private final int indexOfStartCity;
    private final CityList CL;
    private double lowestDistance;
    private ArrayList<Integer> lowestIndex; 
    private final double distanceAim;
    /**
     * n is the number of pSolution on each row
     * indexOfStartCity is the  ID of the firstCity
     * aim is the minimum length that we want to reach (objective)
     * @param n
     * @param indexOfStartCity 
     * @param CL 
     */
    public ShorterResearch(int n, int indexOfStartCity, CityList CL, double aim) {
        generations = new ArrayList<>();
        this.indexOfStartCity = indexOfStartCity;
        this.CL = CL;
        this.distanceAim = aim;
        initFirstGen(n);
    }

    /**
     * getters of lowestDistance and lowest index
     * call set before
     * @return 
     */
    public double getLowestDistance() {
        setLowestDistance();
        return lowestDistance;
    }
    public ArrayList<Integer> getLowestIndex() {
        setLowestDistance();
        return lowestIndex;
    }
    
    /**
     * get the length of a given solution
     * @param pSolution
     * @return 
     */
    private double getNotation(ArrayList<Integer> pSolution) {        
        return routeUtility.getRouteLength(pSolution, CL);
    }
    
    /**
     * gets the number on generation already done
     * @return 
     */
    public int getNumberOfGeneration(){
        return generations.size();
    }
    
    
    /**
     * looks for the LowestDistance on the current generation
     */
    private void setLowestDistance(){
        HashMap<ArrayList<Integer>, Double> lastGen = generations.get(generations.size()-1);
        
        double lD = 9999999;
        ArrayList<Integer> lI = new ArrayList<>();
        
        Set<ArrayList<Integer>> keys = lastGen.keySet();
        
        for(ArrayList<Integer> key : keys){
            if(lastGen.get(key) < lD){
                lD = lastGen.get(key);
                lI = key;
            }
        }
        
        lowestDistance = lD;
        lowestIndex = lI;
    }
    

    
    
    
    
    /**
     * call in constructor
     * generate the first routes
     * @param n 
     */
    private void initFirstGen(int n) {
        //FirstGen
        HashMap<ArrayList<Integer>,Double> firstGen = new HashMap<>();
        //A possible solution
        ArrayList<Integer> pSolution;
        //We do it n times (there will be n possible solutions)
        for(int i = 0;i<n;i++){
            //initialisation of the new possible solution
            pSolution = new ArrayList<>();
            //We add the first city
            pSolution.add(indexOfStartCity);
            //This is the list of city we already went to
            ArrayList<Integer> alreadyWent = new ArrayList<>();
            //for the number of available city -1
            for(int j=0;j<CL.size()-1;j++){
                //we set the random number
                int r = -1;
                //while we didn't find a new city number
                //System.out.println(alreadyWent);
                while(r == -1 || r == indexOfStartCity || alreadyWent.contains(r)){
                    //new random
                    r = (int)(Math.random()*( CL.size()-0));     
                }
                //we add the city we went to
                alreadyWent.add(r);
                // we add it also in the list of city
                pSolution.add(r);
            }
            // We want to go back to the city
            pSolution.add(indexOfStartCity);
            double notation = getNotation(pSolution);
            //we add the psolution to the first gen
            firstGen.put(pSolution,notation);
        }
        generations.add(firstGen);
        System.out.println(firstGen);
    }

    public void getCouples(){
        
        
        
        HashMap<ArrayList<Integer>, Double> lastGen = generations.get(generations.size()-1);
        Set<ArrayList<Integer>> keys = lastGen.keySet();

        /*
        //totalDist is the total of all solution length
        double totalDist = 0.0;
        for(int i = 0; i<keys.size();i++){
            totalDist += routeUtility.getRouteLength((ArrayList<Integer>)keys.toArray()[i], CL);
        }
        
        //set the purcentage for each solution
        ArrayList<Double> purcentage = new ArrayList();
        int purcentageMethod = 1;
        for(int i = 0; i<keys.size();i++){
            switch(purcentageMethod){
                case 1 :
                    double dist = lastGen.get((ArrayList<Integer>)keys.toArray()[i]);
                    purcentage.add(1/(dist/totalDist));
                    break;
            }
        }
        double tot = 0;
        for(double d:purcentage) tot += d;
        for(int i = 0;i<purcentage.size();i++) purcentage.set(i, (purcentage.get(i)/tot*100));
        
        System.out.println(purcentage);
        */
        
        
        
    }
    
    public void nextGeneration() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        
    }
    
    
    
    
    
    
    /**
     * do a mutation on a solution
     * intervert 2 city
     * @param pSolution 
     */
    private void mutation(ArrayList<Integer> pSolution){
        int r1 = 1 + (int)(Math.random()*(CL.size()-1)); 
        int r2 = r1;
        while(r1 == r2){
            r2 = 1 + (int)(Math.random()*(CL.size()-1));
        }
        int r1Val = pSolution.get(r1);
        int r2Val = pSolution.get(r2);
        
        pSolution.set(r1, r2Val);
        pSolution.set(r2, r1Val);
    }    
}
