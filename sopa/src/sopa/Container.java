/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopa;

import javax.swing.JLabel;

/**
 *
 * @author josue
 */
public class Container extends JLabel {

    public int row, column;

    public Container(int row, int column, String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.row = row;
        this.column = column;
    }
}
