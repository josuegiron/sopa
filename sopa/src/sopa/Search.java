/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopa;

import java.util.ArrayList;

/**
 *
 * @author josue
 */
public class Search {

    private Matriz matriz;
    private ArrayList<Container> containers;
    private Busqueda busqueda;
    char[] word;

    public Search(Matriz matriz, Busqueda busqueda) {
        this.matriz = matriz;
        this.busqueda = busqueda;
        containers = new ArrayList<Container>();
        word = busqueda.word.toCharArray();
    }

    public boolean searchWord() {
        switch (busqueda.searchType) {
            case 1:
                return searchRight();
            case 2:
                return searchLeft();
            case 3:
                return searchUp();
            case 4:
                return searchDown();
            default:
                return false;
        }
    }

    private boolean searchRight() {
        int searchLength = busqueda.column;
        int i = busqueda.row;
        if(searchLength >= word.length){
            for(int j = busqueda.column; j >= word.length; j--){
                int k = busqueda.column;
                for(char letter: word){
                    System.out.println(letter);
                    System.out.println(matriz.sopa[i][k].letter);
                    if(letter == matriz.sopa[i][k].letter){
                        containers.add(matriz.sopa[i][k]);
                        k--;
                    }else{
                        break;
                    }
                }
                busqueda.setContainers(containers);
                return true;
            }
        }
        return false;
    }

    private boolean searchLeft() {
        return false;
    }

    private boolean searchUp() {
        return false;
    }

    private boolean searchDown() {
        return false;
    }

}
