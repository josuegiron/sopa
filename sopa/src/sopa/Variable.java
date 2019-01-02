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
public class Variable {

    public int value;
    public String name;
    public ArrayList<Integer> terms;
    public ArrayList<String> signos;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void valuteExpresion() {
        signos.add("#");
        int i = 0, j = 0, total;
        while (!signos.get(i).equals("#")) {
            switch (signos.get(i)) {
                case "–":
                case "-":
                    if (signos.get(i + 1).equals("-") || signos.get(i + 1).equals("+") || signos.get(i + 1).equals("–") || signos.get(i + 1).equals("#")) {
                        total = terms.get(j) - terms.get(j + 1);
                        terms.remove(j);
                        terms.set(j, total);
                        signos.remove(i);
                    } else {
                        i++;
                        j++;
                    }
                    break;
                case "+":
                    if (signos.get(i + 1).equals("-") || signos.get(i + 1).equals("+") || signos.get(i + 1).equals("–") || signos.get(i + 1).equals("#")) {
                        total = terms.get(j) + terms.get(j + 1);
                        terms.remove(j);
                        terms.set(j, total);
                        signos.remove(i);
                    } else {
                        i++;
                        j++;
                    }
                    break;
                case "/":
                    total = terms.get(j) / terms.get(j + 1);
                    terms.remove(j);
                    terms.set(j, total);
                    signos.remove(i);
                    i = 0;
                    j = 0;
                    break;
                case "*":
                    total = terms.get(j) * terms.get(j + 1);
                    terms.remove(j);
                    terms.set(j, total);
                    signos.remove(i);
                    i = 0;
                    j = 0;
                    break;
                default:
                    break;
            }
        }
        this.value = terms.get(0);
    }

    public Variable(String name) {
        terms = new ArrayList<Integer>();
        signos = new ArrayList<String>();
        this.name = name;
    }

    public Variable() {
        terms = new ArrayList<Integer>();
        signos = new ArrayList<String>();
        this.name = name;
    }

    public ArrayList<Integer> getTerm() {
        return terms;
    }

    public void setTerms(ArrayList<Integer> term) {
        this.terms = term;
    }

    public ArrayList<String> getSigno() {
        return signos;
    }

    public void setSignos(ArrayList<String> signo) {
        this.signos = signo;
    }
}
