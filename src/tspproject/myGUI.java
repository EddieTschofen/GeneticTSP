/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspproject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author eddie
 */
public class myGUI {
    
    private CityList CL;

    private JFrame GUI;
    private JPanel optionsPanel;
    private JPanel mapConteneurPanel;
    private JPanel mapPanel; 
    private JPanel mapLines;
    
    private TextField byGen;
    private TextField consec;
    private JComboBox cityCombo;
    private JComboBox cAlgoCombo;
    private JComboBox breedCombo;
    private TextField mutat;
    private JComboBox scoreCombo;
    private JComboBox elitCombo;

    
    private JLabel JLNoGen;
    private JLabel JLLength;

    private ArrayList<Component> linePanels;
    
    public myGUI() {
        
        InitCL();
        
        InitFrame();
        
        InitOption();
        InitMap();   

//        addLine(1, 0, 500, 500);
//        addLine(500, 0, 500, 500);

        GUI.setVisible(true);
        byGen.requestFocus();
        initMapPositions();
    }

    private void InitFrame() {
        GUI = new JFrame("TSP");
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GUI.setPreferredSize(new Dimension(800, 585));
        GUI.setSize(800, 585);
        GUI.setResizable(true);
        
        GUI.setBackground(Color.white);

//        GUI.setVisible(true);    
    }

    private void InitOption() {
        optionsPanel = new JPanel();        
        
        //init Button
//        JButton initButton = new JButton("Initialization");
//        initButton.addActionListener((ActionEvent e) -> {
//            emptyLines();
//            initMapPositions();
//        });
//        initButton.setLayout(null);
//        initButton.setBounds(25, 5, 200, 25);
//        optionsPanel.add(initButton);
        
        //Number of solution by Generation
        int Y = 25;
        int intervalY=50;
        byGen = new TextField("150");
        byGen.setBounds(10, Y, 35, 25);
        optionsPanel.add(byGen);
        JLabel JL = new JLabel("Solutions by generation");
        JL.setLayout(null);
        JL.setBounds(50, Y, 200, 25);
        optionsPanel.add(JL);
        //countMinimal (successive generation with same minimal value)
        Y += intervalY;
        consec = new TextField("50");
        consec.setBounds(10, Y, 35, 25);
        optionsPanel.add(consec);
        JLabel JL2 = new JLabel("successive generations");
        JL2.setLayout(null);
        JL2.setBounds(50, Y-10, 200, 25);
        optionsPanel.add(JL2);
        JLabel JL3 = new JLabel("with same value to stop");
        JL3.setLayout(null);
        JL3.setBounds(50, Y+10, 200, 25);
        optionsPanel.add(JL3); 
        //Starting city
        Y += intervalY;
        JLabel JL4 = new JLabel("First city :");
        JL4.setLayout(null);
        JL4.setBounds(10, Y, 200, 25);
        optionsPanel.add(JL4);
        String[] villes = CL.getCityArray();
        cityCombo = new JComboBox(villes);
        cityCombo.setLayout(null);
        cityCombo.setBounds(85, Y+3, 150, 20);
        optionsPanel.add(cityCombo);
        //Selection algo
        Y += intervalY;
        JLabel JL5 = new JLabel("Couple : ");
        JL5.setLayout(null);
        JL5.setBounds(10, Y, 200, 25);
        optionsPanel.add(JL5);
        String cAlgo[] = {"Rank","Roulette","Random"};
        cAlgoCombo = new JComboBox(cAlgo);
        cAlgoCombo.setLayout(null);
        cAlgoCombo.setBounds(85, Y+3, 150, 20);
        optionsPanel.add(cAlgoCombo);
        //Breed method
        Y += intervalY;
        JLabel JL6 = new JLabel("Breed : ");
        JL6.setLayout(null);
        JL6.setBounds(10, Y, 200, 25);
        optionsPanel.add(JL6);
        String breedA[] = {"Order crossover","Average Crossover"};
        breedCombo = new JComboBox(breedA);
        breedCombo.setLayout(null);
        breedCombo.setBounds(85, Y+3, 150, 20);
        optionsPanel.add(breedCombo);
        //Mutation percentage
        Y += intervalY;
        mutat = new TextField("10");
        mutat.setBounds(10, Y, 35, 25);
        optionsPanel.add(mutat);
        JLabel JL7 = new JLabel("% chance of mutation");
        JL7.setLayout(null);
        JL7.setBounds(50, Y, 200, 25);
        optionsPanel.add(JL7);
        //Score Methode
        Y += intervalY;
        JLabel JL8 = new JLabel("Score : ");
        JL8.setLayout(null);
        JL8.setBounds(10, Y, 200, 25);
        optionsPanel.add(JL8);
        String ScoreA[] = {"0","1"};
        scoreCombo = new JComboBox(ScoreA);
        scoreCombo.setSelectedIndex(1);
        scoreCombo.setLayout(null);
        scoreCombo.setBounds(85, Y+3, 50, 20);
        optionsPanel.add(scoreCombo);
        //Elitisme
        Y += intervalY;
        JLabel JL9 = new JLabel("Elitism : ");
        JL9.setLayout(null);
        JL9.setBounds(10, Y, 200, 25);
        optionsPanel.add(JL9);
        String elitA[] = {"0","2","4","6","8","10"};
        elitCombo = new JComboBox(elitA);
        elitCombo.setLayout(null);
        elitCombo.setBounds(85, Y+3, 50, 20);
        optionsPanel.add(elitCombo);
        
        //results
        Y = 460;
        JLNoGen = new JLabel();
        JLNoGen.setLayout(null);
        JLNoGen.setBounds(10, Y, 200, 25);
        optionsPanel.add(JLNoGen);
        JLabel JLNoGenL = new JLabel("Generations");
        JLNoGenL.setLayout(null);
        JLNoGenL.setBounds(50, Y, 200, 25);
        optionsPanel.add(JLNoGenL);
        //
        Y = 485;
        JLLength = new JLabel();
        JLLength.setLayout(null);
        JLLength.setBounds(80, Y, 200, 25);
        optionsPanel.add(JLLength);
        JLabel JLLengthL = new JLabel("Length : ");
        JLLengthL.setLayout(null);
        JLLengthL.setBounds(10, Y, 200, 25);
        optionsPanel.add(JLLengthL);
        
        //Start Button
        JButton startButton = new JButton("Start");
        startButton.addActionListener((ActionEvent e) -> {
            StartSimulation();
        });            
        startButton.setLayout(null);
        startButton.setBounds(25, 520, 200, 25);
        optionsPanel.add(startButton);
        
        optionsPanel.setLayout(null);
        optionsPanel.setBounds(0, 0, 250, 550);
        optionsPanel.setBackground(new Color((float)140/255,(float)165/255, (float)206/255));
        GUI.add(optionsPanel);
    }

    private void InitMap() {
//        File[] a = new File("./").listFiles();
//        for(int i=0;i<a.length;i++){
//            System.out.println(a[i]);
//        }
//        System.out.println(new File("./").listFiles());
        mapConteneurPanel = new JPanel();
        mapConteneurPanel.setBackground(Color.gray);
        mapConteneurPanel.setLayout(null);
        mapConteneurPanel.setBounds(250, 0, 550, 550);
        mapConteneurPanel.setBackground(new Color((float)218/255,(float)165/255, (float)32/255));
        GUI.add(mapConteneurPanel);
        
        mapPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File("src/img/carteFr.jpg"));
                } catch (Exception e) {System.err.println("Marche pas");}
                g.drawImage(img, 0, 0, 550, 550, null);
            }
        };      
        mapPanel.setBackground(Color.LIGHT_GRAY);
        mapPanel.setLayout(null);
        mapPanel.setBounds(275, 25, 500, 500);
        
        mapConteneurPanel.add(mapPanel);
        
        mapLines = new JPanel();
        mapLines.setLayout(null);
        mapLines.setBounds(0,0,550,550);
        mapLines.setOpaque(false);
        
        mapPanel.add(mapLines);
    }
    
    private void initMapPositions(){
        JLabel lab;
        JLabel nameLab;
        for(City c : CL.getCityList()){            
            lab = new JLabel(new ImageIcon("./src/img/p.png"));
            lab.setLayout(null);
            lab.setBounds((int)c.getX()-5,(int)c.getY()-5,10,10);
            
            nameLab = new JLabel(c.getName());
            nameLab.setLayout(null);
            int x = 0;
            int y = 0;
            if(c.getX() > 450){
                x += -70;
                y += 5;
            }
            else if(c.getX() < 50){
                y += 5; 
            }
            if(c.getY() > 450){
                y -= 10;
                x += 10;
            }
            else if(c.getY() < 50){
                y += 10;
                x -= 20;
            }
            
            if(c.getX() < 450 && c.getX() > 50 && c.getY() < 450 && c.getY() > 50){
                x -= 25;
                y += 5;
            }
                        
            nameLab.setBounds((int)c.getX()+x,(int)c.getY()+y,150,15);
            
            mapPanel.add(lab);
            mapPanel.add(nameLab);            
        }
                
        GUI.repaint();
        GUI.revalidate();
        
    }

    private void InitCL() {
        CL = new CityList(500,500){{
//            addCity(0, 0, "ZeroCity");
//            addCity(250, 250, "Central City");
//            addCity(250, 0, "North City");
//            addCity(250, 500, "South City");
//            addCity(0, 250, "West City");
//            addCity(500, 250, "East City");
            addCity(277, 138, "Paris");
            addCity(360, 312, "Lyon");
            addCity(369, 447, "Marseille");
            addCity(42, 150, "Brest");
            addCity(152, 448, "Pau");
            addCity(290, 35, "Lille");
            addCity(380, 350, "Grenoble");
            addCity(155, 355, "Bordeaux");
            addCity(450, 156, "Strasbourg");
            addCity(285, 310, "Clermont");
            addCity(285, 480, "Perpignan");
            addCity(138, 220, "Nantes");
            addCity(277, 225, "Bourges");
            addCity(242, 432, "Toulouse");
            addCity(370, 210, "Dijon");
            addCity(130, 162, "Rennes");
            addCity(438, 438, "Cannes");
        }};
    }

    private void StartSimulation() {
       
        final int noOfSol = Integer.parseInt(byGen.getText());
        int count = Integer.parseInt(consec.getText());
        int startingCity = cityCombo.getSelectedIndex();
        int cAlgo = cAlgoCombo.getSelectedIndex();
        int breed = breedCombo.getSelectedIndex();    
        double mutation = Double.parseDouble(mutat.getText());
        int scoreMethode = scoreCombo.getSelectedIndex();
        int elit = Integer.parseInt((String)elitCombo.getSelectedItem());
        
        double minimal = 0;
        int countMinimal = 0;
        
//        BestRouteSearcher BRS = new BestRouteSearcher(noOfSol, startingCity, CL);
        BestRouteSearcher BRS = new BestRouteSearcher(noOfSol, startingCity, CL,cAlgo,breed,mutation,scoreMethode,elit);
        while(countMinimal < count && BRS.getNumberOfGeneration() < 10000/*|| minimal > 2000*/){
            BRS.nextGeneration();
            System.out.println(BRS.getNumberOfGeneration());             
            System.out.println(BRS.getLowestDistance().getDistance());
            if(BRS.getLowestDistance().getDistance() == minimal){
                countMinimal++;
            }
            else{
                countMinimal = 0;
                minimal = BRS.getLowestDistance().getDistance();
            }
            
        }
//        System.out.println(BRS.getGenerations());
        System.out.println(BRS.getNumberOfGeneration()); 
        JLNoGen.setText(BRS.getNumberOfGeneration()+"");
        JLLength.setText(BRS.getLowestDistance().getDistance()+"");
        
        emptyLines();
        drawPath(BRS.getLowestDistance());
//       testDrawLine();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addLine(int x, int y, int x2, int y2) {
        Rectangle bounds = new Rectangle(0, 0, 550, 550);

        Component comp = new LinePanel(x, y, x2, y2,bounds);
        mapLines.add(comp);
        mapLines.repaint();
        mapLines.revalidate();
    }
    
    private void emptyLines(){
        mapLines.removeAll();
        mapLines.repaint();
        mapLines.revalidate();        
    }
    
    private int testIndex = 0;
    private void testDrawLine(){
        switch(testIndex){
            case 0:
                addLine(0, 0, 0, 100);
                break;
            case 1:
                addLine(0, 100, 100, 100);
                break;
            case 2:
                addLine(200, 100, 200, 200);
                break;
            case 3:
                for(int i = 0; i<CL.size()-1;i++){
                    addLine((int)CL.getCity(i).getX(), (int)CL.getCity(i).getY(), (int)CL.getCity(i+1).getX(), (int)CL.getCity(i+1).getY());
                    System.out.println((int)CL.getCity(i).getX() + " - " + (int)CL.getCity(i).getY() + " : " + (int)CL.getCity(i+1).getX() + " - " + (int)CL.getCity(i+1).getY());     
                }
                break;
            case 4:
                emptyLines();
                testIndex = -1;
                break;
            case 999:
                mapPanel.setVisible(false);
                optionsPanel.setVisible(false);
                mapConteneurPanel.setVisible(false);
                GUI.repaint();
                GUI.revalidate();
                break;

        }
        testIndex++;
    }

    private void drawPath(Route lowestDistance) {
//        System.err.println(lowestDistance);
        ArrayList<Integer> l = lowestDistance.getRoute();
        for(int i=0;i<l.size()-1;i++){
            City c1 = CL.getCity(l.get(i));
            City c2 = CL.getCity(l.get(i+1));
            
//            System.out.println(c1 + " - " + c2);
            
            addLine((int) c1.getX(), (int) c1.getY(), (int) c2.getX(), (int) c2.getY());
        }
//        System.out.println(l);
        System.out.println(lowestDistance.getDistance());
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
