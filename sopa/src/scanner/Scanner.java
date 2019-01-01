/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import error.Error;
import java.util.ArrayList;

/**
 *
 * @author josue
 */
public class Scanner {

    public String Code;
    public int currentRow, currentColum, currentState;
    private char currentChar;
    private int index;
    public ArrayList<Token> TokenTable;
    public ArrayList<Error> ErrorTable;
    public Alphabet alph;
    private Token token;

    public Scanner() {

    }

    public void SetCode(String code) {
        Code = code + " ";
    }

    private void ValidateReservWord(Token token) {
        if (alph.ValidateReservWord(token.Lexeme)) {
            TokenTable.add(token);
        } else {
            token.Type = 3;
            TokenTable.add(token);
        }
    }

    public void Scan() {
        currentState = 0;
        currentRow = 1;
        currentColum = 0;
        TokenTable = new ArrayList<Token>();
        ErrorTable = new ArrayList<Error>();
        alph = new Alphabet();

        for (index = 0; index < Code.length(); index++) {
            currentChar = Code.charAt(index);
            currentColum++;
            switch (currentState) {
                case 0: // So
                    token = new Token();
                    token.Row = currentRow;
                    token.Colum = currentColum;

                    token.Offset = index;

                    if (alph.ValidateAlphabet(alph.L, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(1); //    ID
                        currentState = 1;
                    } else if ('#' == currentChar) {
                        token.addChar(currentChar);
                        token.setType(5); //    HEXADECIMAL
                        currentState = 5;
                    } else if (alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(2); //    NUMERO
                        currentState = 12;
                    } else if ('\"' == currentChar) {
                        token.addChar(currentChar);
                        token.setType(4); //    CADENA
                        currentState = 13;
                    } else if (':' == currentChar) {
                        token.addChar(currentChar);
                        token.setType(6); //    DOS PUNTOS
                        currentState = 11;
                    } else if ('=' == currentChar) {
                        token.addChar(currentChar);
                        token.setType(7); //    IGUAL
                        currentState = 11;
                    } else if (';' == currentChar) {
                        token.addChar(currentChar);
                        token.setType(8); //    PUNTO Y COMA
                        currentState = 11;
                    } else if (alph.ValidateAlphabet(alph.S, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(9); //    ARITMETICO
                        currentState = 11;
                    } else if (alph.ValidateAlphabet(alph.LL, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(10); //    LLAVES
                        currentState = 11;
                    } else if (alph.ValidateAlphabet(alph.C, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(11); //    CORCHETES
                        currentState = 11;
                    } else if (',' == currentChar) {
                        token.addChar(currentChar);
                        token.setType(12); //    COMA
                        currentState = 11;
                    } else {
                        switch (currentChar) {
                            case '\t':
                            case ' ':
                            case '\b':
                            case '\f':
                            case '\r':
                                currentState = 0;
//                                currentRow++;
//                                currentColum = 0;
                                break;
                            case '\n':
                                currentRow++;
                                currentColum = 0;
                                currentState = 0;
                                break;
                            default:
                                token.addChar(currentChar);
                                currentState = -1;
                                currentColum--;
                                break;
                        }
                    }
                    break;

                case 1: // S1 *
                    if (alph.ValidateAlphabet(alph.L, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 1;
                    } else if ('_' == currentChar) {
                        token.addChar(currentChar);
                        currentState = 2;
                    } else if (alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 4;
                    } else {    //  Aceptacion
                        currentState = 0;
                        index--;
                        currentColum--;
                        ValidateReservWord(token);
                    }
                    break;
                case 2: // S2 
                    if (alph.ValidateAlphabet(alph.L, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 3;
                    }
                    break;
                case 3: // S3 *
                    if (alph.ValidateAlphabet(alph.L, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 3;
                    } else {    //  Aceptacion
                        currentState = 0;
                        index--;
                        currentColum--;
                        ValidateReservWord(token);
                    }
                    break;
                case 4: // S4 *
                    if (alph.ValidateAlphabet(alph.L, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 4;
                    } else if (alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 4;
                    } else {    //  Aceptacion
                        currentState = 0;
                        index--;
                        currentColum--;
                        ValidateReservWord(token);
                    }
                    break;
                case 5: // S5
                    if (alph.ValidateAlphabet(alph.Lh, currentChar) || alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 6;
                    } else {
                        index--;
                        currentState = -1;
                    }
                    break;
                case 6: // S6 
                    if (alph.ValidateAlphabet(alph.Lh, currentChar) || alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 7;
                    } else {
                        index--;
                        currentState = -1;
                    }
                    break;
                case 7: // S7 
                    if (alph.ValidateAlphabet(alph.Lh, currentChar) || alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 8;
                    } else {
                        index--;
                        currentState = 1;
                    }
                    break;
                case 8: // S8 
                    if (alph.ValidateAlphabet(alph.Lh, currentChar) || alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 9;
                    } else {
                        index--;
                        currentState = -1;
                    }
                    break;
                case 9: // S9 
                    if (alph.ValidateAlphabet(alph.Lh, currentChar) || alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 10;
                    } else {
                        index--;
                        currentState = -1;
                    }
                    break;
                case 10: // S10 
                    if (alph.ValidateAlphabet(alph.Lh, currentChar) || alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 11;
                    } else {
                        index--;
                        currentState = -1;
                    }
                    break;
                case 11: // S11 
                    currentState = 0;
                    index--;
                    currentColum--;
                    TokenTable.add(token);
                    break;
                case 12: // S12 
                    if (alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 12;
                    } else {
                        currentState = 0;
                        index--;
                        currentColum--;
                        TokenTable.add(token);
                    }
                    break;
                case 13: // S13 
                    if ('\"' == currentChar) {
                        token.addChar(currentChar);
                        currentState = 11;
                    } else {
                        token.addChar(currentChar);
                        currentState = 13;
                    }
                    break;
                default: // ERROR
                    if (alph.ValidateSpecialChar(currentChar)) {
                        currentState = 0;
                        index--;
                        Error error = new Error(0, token.Row, token.Colum, token.Lexeme, "El componente lexico no pertenece al alfabeto del lenguaje", token.Offset);
                        ErrorTable.add(error);
                    } else {
                        token.addChar(currentChar);
                    }
            }
        }
        System.out.println("Lexemas: ");
        for (Token token : TokenTable) {
            System.out.println("Lexema: " + token.Lexeme + " Type: " + alph.Token.get(token.Type)[0] + " Location: " + token.Row + ", " + token.Colum);
        }
        System.out.println("Errores: ");
        for (Error error : ErrorTable) {
            System.out.println("Error: " + error.Error + " Location: " + error.Row + ", " + error.Colum);
        }
    }
}
