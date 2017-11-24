/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eddie
 */
public class BestRouteSearcher {
    /*
        First Level : All the generations
        2nd level : A single generation
        3rd level : a possible solution
    */
    private final ArrayList<ArrayList<Route>> generations;
    private final int indexOfStartCity;
    private final CityList CL;
    private Route lowestDistance;
    private final int cAlgo;
    private final int breed;
    private final double mutation;
    private final int scoreMethode;
    private final int elit;
    /**
     * n is the number of pSolution on each row
     * indexOfStartCity is the  ID of the firstCity
     * aim is the minimum length that we want to reach (objective)
     * @param n
     * @param indexOfStartCity 
     * @param CL 
     */
    public BestRouteSearcher(int n, int indexOfStartCity, CityList CL) {
        generations = new ArrayList<>();
        this.indexOfStartCity = indexOfStartCity;
        this.CL = CL;
        this.cAlgo = 0;
        breed = 0;
        mutation = 5.0;
        scoreMethode = 0;
        elit = 0;
        initFirstGen(n);
    }
    BestRouteSearcher(int noOfSol, int startingCity, CityList CL, int cAlgo, int breed, double mutation, int scoreMethode, int elit) {
        generations = new ArrayList<>();
        this.indexOfStartCity = startingCity;
        this.CL = CL;
        this.cAlgo = cAlgo;
        this.breed = breed;
        this.mutation = mutation;
        this.scoreMethode = scoreMethode;
        this.elit = elit;
        initFirstGen(noOfSol);
    }
    
    public Route getLowestDistance() {
        setLowestDistance();
        return lowestDistance;
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
        ArrayList<Route> lastGen = generations.get(generations.size()-1);
        
        double lD = 9999999;
        Route lR = lastGen.get(0);
                
        for(Route r : lastGen){
            if(r.getDistance() < lD){
                lR = r;
                lD = r.getDistance();
            }
        }
        
        lowestDistance = lR;
    }
    

    /**
     * call in constructor
     * generate the first routes
     * @param n 
     */
    private void initFirstGen(int n) {
        //FirstGen
        ArrayList<Route> firstGen = new ArrayList<>();
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
            //we add the psolution to the first gen
            firstGen.add(new Route(pSolution, CL));
        }
        generations.add(firstGen);
//        System.out.println(firstGen);
    }

    private ArrayList<Route> sortByScore(ArrayList<Route> lastGen) {
        ArrayList<Route> gen = (ArrayList < Route >)lastGen.clone();
        ArrayList<Route> Sorted = new ArrayList<>();
        
        int size = gen.size();
        for(int i = 0; i<size;i++){
            Route shortest = new Route(new ArrayList<Integer>(), CL);
            double length = 999999;
            int index = 0;
            for(int j = 0; j<gen.size();j++){
                if(gen.get(j).getDistance() < length){
                    length = gen.get(j).getDistance();
                    index = j;
                    shortest = gen.get(j);
                }
            }
            Sorted.add(shortest);
            gen.remove(index);
        }
        return Sorted; 
    }
        
    //public void getCouples(){ 
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
    //}
    
    public void nextGeneration() {
        ArrayList<Route> lastGen = generations.get(generations.size()-1);
        //System.out.println(lastGen);

        setScores(lastGen);
        lastGen = sortByScore(lastGen);
        //System.out.println(lastGen);
        ArrayList<Route> newGen = new ArrayList<>();
        for(int i = 0;i<elit;i++){
            newGen.add(lastGen.get(i));
        }
        while(newGen.size() < lastGen.size()){
//        for(int i = 0; i<lastGen.size()/2;i++){         
            Route r1 = rankSelection(lastGen);
            Route r2 = r1;
            while (r1 == r2){
                r2 = rankSelection(lastGen);
            }
            
//            System.out.print("r1 :");
//            System.out.println(r1);
//            System.out.print("r2 :");
//            System.out.println(r2);

            Route[] childs = getChilds(r1,r2);
            for(Route r : childs){
                double rand = Math.random()*(100);
                if(rand < mutation){
//                    System.out.println("-----------Mutation !!!!!!-----------");
//                    System.out.println(rand + " - " + mutation);
//                    System.out.println(r);
                    r.mutate();
//                    System.out.println(r);
//                    System.out.println("--------------------------------------");
                }
                newGen.add(r);
            }
        }
        generations.add(newGen);
        
    }

    public ArrayList<ArrayList<Route>> getGenerations() {
        return generations;
    }
    
    private Route rankSelection(ArrayList<Route> gen) {
        double r = Math.random();
        //System.out.println(r);
        
        int index = 0;
        switch(cAlgo){
            case 0 : 
                //methone par rank
                double d = 0.5;
                while(index<gen.size()-1){
                    if(r < d){
                        break;
                        //return gen.get(index);
                    }
                    index++;
                    d += (1-d)/2;
                }
                break;
            case 1: 
                double s = gen.get(index).getScore();
                while(index<gen.size()-1){
                    if(r < s){
                        break;
                    }
                    index++;
                    s += gen.get(index).getScore();
                }
                break;
            case 2: //random
                index = 0 + (int)(Math.random() * gen.size()-1); 
                break;
        }
        
        
        
        //System.out.println("r : " + r + " - d : " + d + " - index : " + index + " - dist : " + gen.get(index).getDistance());
        return gen.get(index);
    }
    
    private Route[] getChilds(Route r1, Route r2) {
        Route[] childs = new Route[2];
        //System.out.println(r1.getRoute());
        //System.out.println(r2.getRoute());
        
        for(int index = 0; index < 2;index++){
            switch(breed){
                case 0 : 
                    //int r = (int)Math.random()*(CL.size()+1);
                    int rand = 0,rand2 = CL.size()-1;
                    while(rand2 - rand > CL.size()/2){
                        rand = 1 + (int)(Math.random() * ((CL.size()-2 - 0) + 1));
                        rand2 = rand + (int)(Math.random() * ((CL.size()-2 - rand) + 1));
                    }
                    //System.out.println(rand + " -- " + rand2);

                    ArrayList<Integer> parent1Heritage = new ArrayList<>();
                    for(int i = rand; i<=rand2;i++){
                        parent1Heritage.add(r1.getRoute().get(i));
                    }
                    //System.out.println(parent1Heritage);

                    ArrayList<Integer> c = new ArrayList<>();
                    boolean b = true;
                    for(int i = 0; i<r1.getRoute().size(); i++){
                        if(i == rand && b){
                            c.addAll(parent1Heritage);
                            //i = rand2;
                            b = false;
                            i--;
                        }
                        else{
                            if(!c.contains(r2.getRoute().get(i)) && !parent1Heritage.contains(r2.getRoute().get(i)))
                            c.add(r2.getRoute().get(i));
                        }
                    }
                    c.add(c.get(0));
                    //System.out.println("\n" + c + "\n\n");
                    childs[index] = new Route(c, CL);
                    break;
                case 1 :
//                    System.out.println("AVG!!!!");
                    HashMap<Integer,Double> avgs = new HashMap();
                    for(int i=0;i<r1.getRoute().size()-1;i++){
                        if(i != r1.getRoute().get(0)){
                            int r1i = 0;
                            int r2i = 0;
                            r1i = r1.getRoute().indexOf(i);
                            r2i = r2.getRoute().indexOf(i);
//                            for(r1i=0;r1i<r1.getRoute().size()-1;r1i++){
//                                if(r1i == r1.getRoute().get(i)) break;
//                            }
//                            for(r2i=0;r2i<r2.getRoute().size()-1;r2i++){
//                                if(r2i == r2.getRoute().get(i)) break;
//                            }
//                            System.out.println(i + " - " + r1i + " - " + r2i);
                            double avg = ((double)r1i+(double)r2i)/2;
                            avgs.put(i, avg);
                        }  
                    }
//                    System.out.println(r1 +" "+ r2);
//                    System.out.println(avgs);
                    avgs = sortByValues(avgs);
//                    System.out.println(avgs); 
//                    System.out.println("");
                    ArrayList<Integer> ch = new ArrayList<>(avgs.keySet());
                    ch.add(0,r1.getRoute().get(0));
                    ch.add(r1.getRoute().get(0));
                    childs[index] = new Route(ch, CL);
                    break;
            }         
        }
        return childs;
    }  
 private static HashMap sortByValues(HashMap map) { 
       List list = new LinkedList(map.entrySet());
       // Defined Custom Comparator here
       Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
            }
       });

       // Here I am copying the sorted list in HashMap
       // using LinkedHashMap to preserve the insertion order
       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       } 
       return sortedHashMap;
  }

    private void setScores(ArrayList<Route> lastGen) {
        ArrayList<Double> scores = new ArrayList<>();
        
        switch(scoreMethode){
            case 0 :
                for(Route r : lastGen){
                  scores.add(1/(r.getDistance())*10000);
                }

                //System.out.println(scores);

                double sum = 0;
                for(Double d:scores)
                    sum += d;
                //System.out.println(sum);

                int i = 0;
                for(Route r : lastGen){
                    r.setScore(scores.get(i)/sum);
                    i++;
                }
                break;
            case 1 :
                for(Route r : lastGen){
                  scores.add((Math.pow(1/r.getDistance(),10))*10000);
                }

                //System.out.println(scores);

                sum = 0;
                for(Double d:scores)
                    sum += d;
                //System.out.println(sum);

                i = 0;
                for(Route r : lastGen){
                    r.setScore(scores.get(i)/sum);
                    i++;
                }
                break;
        }
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }





}
