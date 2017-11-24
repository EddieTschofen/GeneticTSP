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
public class CityList {
    private final ArrayList<City> cityList;
    private final double height;
    private final double width;

    public CityList(double h, double w) {
        cityList = new ArrayList<>();
        height = h;
        width = w;
    }
    
    /**
     * add a city to the list
     * @param x
     * @param y
     * @param name 
     */
    public void addCity(double x, double y, String name){
        if((0 <= x && x <= width) && (0 <= y && y <= height)){
            cityList.add(new City(x, y, name));
        }
    }
    
    /**
     * calculate the distance between 2 cities
     * @param a
     * @param b
     * @return 
     */
    public double getDistance(int a, int b){
       double xa = cityList.get(a).getX();
       double ya = cityList.get(a).getY();
       double xb = cityList.get(b).getX();
       double yb = cityList.get(b).getY();
       
       double dist = Math.sqrt(Math.pow(xb-xa,2)+Math.pow(yb-ya,2));
       
       return dist;
    }
    
    /**
     * get all the city info
     * @return 
     */
    public String getCityListInfo(){
        String str = "";
        for (City city : cityList) {
            str += city.toString() + "\n";        
        }     
        return str;
    }
    
    /**
     * getter
     * @param i
     * @return 
     */
    public City getCity(int i){
        return cityList.get(i);
    }
    
    public int size(){
        return cityList.size();
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public String[] getCityArray() {
        String cities[] = new String[cityList.size()];
        for(int i=0;i<cityList.size();i++){
            cities[i] = cityList.get(i).getName();
        }
        return cities;
    }
    
    
}
