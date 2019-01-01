/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopa;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author josue
 */
public class Container extends JLabel {

    public int row, column;
    public char letter;

    public Container(int row, int column, String letter, int horizontalAlignment) {
        super(String.valueOf(letter.charAt(0)), horizontalAlignment);
        this.letter = letter.charAt(0);        
        this.row = row;
        this.column = column;

        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.black, 1);
        this.setSize(25, 25);
        this.setOpaque(true);
        this.setBackground(Color.white);//Color.decode("#0101010")
        this.setForeground(Color.black);
        this.setBorder(border);
        this.setVisible(true);
    }
    
    public void setBackgroundColor(String color){
        this.setBackground(Color.decode(color));
        this.setForeground(Color.white);
        if(color.equals("#FFFFFF")){
            this.setForeground(Color.black);
        }
    }
}
