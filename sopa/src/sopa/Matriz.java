/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopa;

/**
 *
 * @author josue
 */
public class Matriz {
    public int column, row;
    public char[][] sopa;

    public Matriz(int row, int column) {
        this.column = column;
        this.row = row;
        sopa = new char[row][column];
    }
}
