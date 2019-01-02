/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.RowFilter.Entry;

/**
 *
 * @author josuegiron
 */
public class Alphabet {

    String[] L = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "\\u00d1", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "\\u00f1", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    String[] Lh = {"A", "B", "C", "D", "E", "F", "a", "b", "c", "d", "e", "f"};
    String[] N = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    String[] S = {"+", "–", "*", "/", "-"};
    String[] LL = {"{", "}"};
    String[] C = {"[", "]"};
    //String[] P = {",", ":", ";"};
    String[] Cm = {"\""};
    String[] SL = {"\n"};
    Map<Integer, String> ReservedWords = new HashMap<Integer, String>();
    Map<Integer, String[]> Token = new HashMap<Integer, String[]>();
    String[] des;
    private char[] specialChar = {' ', '\t', '\b', '\f', '\r', '\n', ';'};

    public boolean ValidateAlphabet(String[] alph, char currentChar) {
        String currentCharStr = String.valueOf(currentChar);
        for (String item : alph) {
            if (item.equals(currentCharStr)) {
                return true;
            }
        }
        return false;
    }

    public boolean ValidateSpecialChar(char cuerrentChar) {
        for (char item : specialChar) {
            if (item == cuerrentChar) {
                return true;
            }
        }
        return false;
    }

    public boolean ValidateReservWord(String lexeme) {

        String lexemem = lexeme.toLowerCase();

        Iterator it = ReservedWords.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            if (ReservedWords.get(key).equals(lexemem)) {
                return true;
            }
        }
        return false;
    }

    public String[] GetToken(int id) {
        Iterator it = Token.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            if (key.equals(id)) {
                return Token.get(key);
            }
        }
        return null;
    }

    public String GetReservedWord(int id) {

        return ReservedWords.get(id);

    }

    public Alphabet() {
        ReservedWords.put(1, "matriz");
        ReservedWords.put(2, "x");
        ReservedWords.put(3, "busqueda");
        ReservedWords.put(4, "palabra");
        ReservedWords.put(5, "columna");
        ReservedWords.put(6, "fila");
        ReservedWords.put(7, "orden_busqueda");
        ReservedWords.put(8, "color_texto");
        ReservedWords.put(9, "fondo_casilla");
        ReservedWords.put(10, "horizontal_derecha");
        ReservedWords.put(11, "horizontal_izquierda");
        ReservedWords.put(12, "vertical_arriba");
        ReservedWords.put(13, "vertical_abajo");
        ReservedWords.put(15, "variables");
        ReservedWords.put(16, "variable");
        ReservedWords.put(17, "variable");

        String[] reservada = {"Palabra reservada", "Letra mayuscula, seguidas de letras minusculas que termina o no en Mayuscula"};
        Token.put(1, reservada);
        String[] numero = {"Numero", "Numero seguidos de numeros"};
        Token.put(2, numero);
        String[] id = {"ID", "Letra seguida de numeros y letras"};
        Token.put(3, id);
        String[] cadena = {"Cadena", "Comillas seguida de cualquier cosa y finaliza con comillas"};
        Token.put(4, cadena);
        String[] numHex = {"Numero hexadecimal", "Hash seguido de numeros y cualquier letra de la A a la F"};
        Token.put(5, numHex);
        String[] dp = {"Dos puntos", "Caracter de dos puntos"};
        Token.put(6, dp);
        String[] igual = {"Igual", "Caracter igual"};
        Token.put(7, igual);
        String[] pc = {"Punto y coma", "Caracter punto y coma"};
        Token.put(8, pc);
        String[] signo = {"Signo aritmetico", "Signo de suma, resta, multiplicacion o division"};
        Token.put(9, signo);
        String[] llaves = {"Llaves", "Caracteres de llaves abiertas y cerradas"};
        Token.put(10, llaves);
        String[] Corchete = {"Corchetes", "Caracteres de corchetes abiertos y cerrados"};
        Token.put(11, Corchete);
        String[] coma = {"Coma", "Caracter coma"};
        Token.put(12, coma);
    }

}
