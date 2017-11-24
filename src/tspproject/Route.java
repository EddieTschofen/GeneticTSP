/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspproject;

import java.util.ArrayList;

/**
 *
 * @author eddie
 */
public class Route {
    private ArrayList<Integer> route;
    private double distance;
    private final CityList CL;
    private double score;

    public Route(ArrayList<Integer> route, CityList CL) {
        this.route = route;
        this.CL = CL;
        setRouteLength();
        setScore(0);
    }
    
    
    public void setRouteLength(){
        double length = 0.0;
        for(int i = 0;i<route.size()-1;i++){
            length += CL.getDistance(route.get(i), route.get(i+1));
        }
        distance = length;
        setScore(0);
    }
    
    public String getRouteDetails(){
        String str = "";
        for(int i = 0;i<route.size();i++){
            str += CL.getCity(route.get(i)).toString() + "\n";
        }
        return str;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public double getDistance() {
        return distance;
    }

    public CityList getCL() {
        return CL;
    }
    
    @Override
    public String toString(){
        return "\n" + route + " - " + distance;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int i) {
        switch(i){
            case 0 : 
                score = distance;
                break;
        }
    }
    
    public void setScore(double d){
        score = d;
    }
    
    public void mutate(){
        int r1 = 1 + (int)(Math.random()*(CL.size()-1)); 
        int r2 = r1;
        while(r1 == r2){
            r2 = 1 + (int)(Math.random()*(CL.size()-1));
        }
        int r1Val = route.get(r1);
        int r2Val = route.get(r2);
        
        route.set(r1, r2Val);
        route.set(r2, r1Val);
        
        setRouteLength();
    }
    
}
