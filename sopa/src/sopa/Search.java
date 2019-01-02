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
        int i = busqueda.row-1;
        
        if(searchLength >= word.length){
            for(int j = busqueda.column-1; j >= 0; j--){
                
                if(searchLength < word.length){
                    return false;
                }
                searchLength--;
                
                //System.out.println("nueva iteracion: ");
                int k = j;
                boolean done = true;
                for(char letter: word){
                    //System.out.println(letter);
                    //System.out.println(matriz.sopa[i][k].letter);
                    if(letter == matriz.sopa[i][k].letter){
                        containers.add(matriz.sopa[i][k]);
                        k--;
                    }else{
                        done = false;
                    }
                }
                if(done){
                    busqueda.setContainers(containers);
                    return true;
                }else{
                    containers.clear();
                }
            }
        }
        return false;
    }

    private boolean searchLeft() {
        int searchLength =  matriz.column - busqueda.column;
        int i = busqueda.row-1;
        
        if(searchLength >= word.length){
            for(int j = busqueda.column-1; j <= matriz.column; j++){
                if(searchLength < word.length){
                    return false;
                }
                searchLength--;
                //System.out.println("nueva iteracion: ");
                int k = j;
                boolean done = true;
                for(char letter: word){
                    //System.out.println(letter);
                    //System.out.println(matriz.sopa[i][k].letter);
                    if(letter == matriz.sopa[i][k].letter){
                        containers.add(matriz.sopa[i][k]);
                        k++;
                    }else{
                        done = false;
                    }
                }
                if(done){
                    busqueda.setContainers(containers);
                    return true;
                }else{
                    containers.clear();
                }
            }
        }
        return false;
    }

    private boolean searchUp() {
        int searchLength =  matriz.row - busqueda.row;
        int j = busqueda.column-1;
        
        if(searchLength >= word.length){
            for(int i = busqueda.row-1; i <= matriz.row; i++){
                if(searchLength < word.length){
                    return false;
                }
                searchLength--;
                
                //System.out.println("nueva iteracion: ");
                int h = i;
                boolean done = true;
                for(char letter: word){
                    //System.out.println(letter);
                    //System.out.println(matriz.sopa[h][j].letter);
                    if(letter == matriz.sopa[h][j].letter){
                        containers.add(matriz.sopa[h][j]);
                        h++;
                    }else{
                        done = false;
                    }
                }
                if(done){
                    busqueda.setContainers(containers);
                    return true;
                }else{
                    containers.clear();
                }
            }
        }
        return false;
        
    }

    private boolean searchDown() {
        System.out.println("SEARCH DOWN");
        int searchLength = busqueda.row;
        int j = busqueda.column-1;
        
        if(searchLength >= word.length){
            for(int i = busqueda.row-1; i >= 0; i--){
                if(searchLength < word.length){
                    return false;
                }
                searchLength--;
                
                System.out.println("nueva iteracion: ");
                int h = i;
                boolean done = true;
                for(char letter: word){
                    System.out.println(letter);
                    System.out.println(matriz.sopa[h][j].letter);
                    if(letter == matriz.sopa[h][j].letter){
                        containers.add(matriz.sopa[h][j]);
                        h--;
                    }else{
                        done = false;
                    }
                }
                if(done){
                    busqueda.setContainers(containers);
                    return true;
                }else{
                    containers.clear();
                }
            }
        }
        return false;
    }

}
