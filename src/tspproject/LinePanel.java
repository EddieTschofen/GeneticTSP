/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author eddie
 */
public class LinePanel extends JPanel{
    int x,y,x2,y2;
    
    public LinePanel(int x, int y, int x2, int y2, Rectangle bounds) {
        this.x = x;
        this.x2 = x2;
        this.y = y;
        this.y2 = y2;    
        
        this.setLayout(null);
        this.setBounds(bounds);
//        this.setBounds(mapPanel.getBounds());
        this.setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.blue);
        g.drawLine(x, y, x2, y2);        
    }

    
}
