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

    public Search(Matriz matriz) {
        this.matriz = matriz;
        containers = new ArrayList<Container>();
    }

    public boolean searchWord() {
        switch (busqueda.searchType) {
            case 1:
                return searchRight(busqueda.row, busqueda.column);
            case 2:
                return searchLeft(busqueda.row, busqueda.column);
            case 3:
                return searchUp(busqueda.row, busqueda.column);
            case 4:
                return searchDown(busqueda.row, busqueda.column);
            default:
                return false;
        }
    }

    private boolean searchRight(int row, int column) {
        
        int searchLength = column+1;
        int i = row - 1;

        if (searchLength >= word.length) {
            if (searchLength < word.length) {
                return false;
            }
            searchLength--;

            //System.out.println("nueva iteracion: ");
            int k = column-1;
            boolean done = true;
            for (char letter : word) {
                //System.out.println(letter);
                //System.out.println(matriz.sopa[i][k].letter);
                if (letter == matriz.sopa[i][k].letter) {
                    containers.add(matriz.sopa[i][k]);
                    k--;
                } else {
                    done = false;
                }
            }
            if (done) {
                busqueda.setContainers(containers);
                return true;
            } else {
                containers.clear();
            }
        }
        return false;
    }

    private boolean searchLeft(int row, int column) {
        int searchLength = matriz.column - column+1;
        int i = row - 1;
        System.out.println("Tamaño de la busqueda "+searchLength);
        if (searchLength >= word.length) {
            if (searchLength < word.length) {
                return false;
            }
            searchLength--;
            int k = column-1;
            boolean done = true;
            for (char letter : word) {
                if (letter == matriz.sopa[i][k].letter) {
                    containers.add(matriz.sopa[i][k]);
                    k++;
                } else {
                    done = false;
                }
            }
            if (done) {
                busqueda.setContainers(containers);
                return true;
            } else {
                containers.clear();
            }
        }
        System.out.println("hola");

        return false;
    }

    private boolean searchUp(int row, int column) {
        int searchLength = matriz.row - row+1;
        int j = column - 1;

        if (searchLength >= word.length) {
            if (searchLength < word.length) {
                return false;
            }
            searchLength--;

            //System.out.println("nueva iteracion: ");
            int h = row-1;
            boolean done = true;
            for (char letter : word) {
                //System.out.println(letter);
                //System.out.println(matriz.sopa[h][j].letter);
                if (letter == matriz.sopa[h][j].letter) {
                    containers.add(matriz.sopa[h][j]);
                    h++;
                } else {
                    done = false;
                }
            }
            if (done) {
                busqueda.setContainers(containers);
                return true;
            } else {
                containers.clear();
            }
        }
        return false;

    }

    private boolean searchDown(int row, int column) {
        System.out.println("SEARCH DOWN");
        int searchLength = row+1;
        int j = column - 1;

        if (searchLength >= word.length) {
            if (searchLength < word.length) {
                return false;
            }
            searchLength--;

            System.out.println("nueva iteracion: ");
            int h = row;
            boolean done = true;
            for (char letter : word) {
                //System.out.println(letter);
                //System.out.println(matriz.sopa[h][j].letter);
                if (letter == matriz.sopa[h][j].letter) {
                    containers.add(matriz.sopa[h][j]);
                    h--;
                } else {
                    done = false;
                }
            }
            if (done) {
                busqueda.setContainers(containers);
                return true;
            } else {
                containers.clear();
            }
        }

        return false;
    }

    public Busqueda fastSearch(String word) {
        this.word = word.toCharArray();
        busqueda = new Busqueda();
        busqueda.setBackColor("#909090");
        busqueda.setFontColor("#000000");
        System.out.println(matriz.row);
        for (int i = 1; i <= matriz.row; i++) {
            for (int j = 1; j <= matriz.column; j++) {
                System.out.println(j);
                busqueda.column = j;
                busqueda.row = i;
                if (searchLeft(i, j)) {
                    busqueda.searchType = 1;
                    return busqueda;
                } else if (searchRight(i, j)) {
                    busqueda.searchType = 2;
                    return busqueda;
                } else if (searchUp(i, j)) {
                    busqueda.searchType = 3;
                    return busqueda;
                } else if (searchDown(i, j)) {
                    busqueda.searchType = 4;
                    return busqueda;
                }
                
                
            }
        }
        System.out.println("No lo encontre");
        return null;
    }
}
