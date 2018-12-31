/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

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

    private void ValidateToken(Token token) {
        if (alph.ValidateLexeme(token.Lexeme)) {
            TokenTable.add(token);
        } else {
            Error error = new Error(0, token.Row, token.Colum, token.Lexeme, "La palabra no pertenece al lenguaje", token.Offset);
            ErrorTable.add(error);
        }
    }

    private void ValidatePice(Token token) {
        if (alph.ValidatePice(token.Lexeme)) {
            TokenTable.add(token);
        } else if (alph.ValidateAlphabet(alph.PV, token.Lexeme.charAt(0))){
            token.Type = 6;
            TokenTable.add(token);
        }else{
            Error error = new Error(0, token.Row, token.Colum, token.Lexeme, "La palabra no pertenece al lenguaje", token.Offset);
            ErrorTable.add(error);
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
                        token.setType(1); //    RESERVADA
                        currentState = 1;
                    } else if (alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(2); //    NUMERO
                        currentState = 3;
                    } else if (alph.ValidateAlphabet(alph.Cm, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(3); //    CADENA
                        currentState = 5;
                    } else if (alph.ValidateAlphabet(alph.B, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(4); //    CORCHETES Y LLAVES
                        currentState = 4;
                    } else if (alph.ValidateAlphabet(alph.Mm, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(5); //    MAYOR O MENOR
                        currentState = 4;
                    } else if (alph.ValidateAlphabet(alph.PV, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(6); //    POTENCIA Y V
                        currentState = 4;
                    } else if (alph.ValidateAlphabet(alph.P, currentChar)) {
                        token.addChar(currentChar);
                        token.setType(7); //    COMA, DOS PUNTOS Y PUNTO Y COMA
                        currentState = 4;
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
                        currentState = 2;
                    } else {
                        currentState = 0;
                        index--;
                        currentColum--;
                        ValidatePice(token);
                    }
                    break;
                case 2: // S2 *
                    if (alph.ValidateAlphabet(alph.L, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 2;
                    } else {
                        currentState = 0;
                        index--;
                        currentColum--;
                        ValidateToken(token);
                    }
                    break;
                case 3: // S3 *
                    if (alph.ValidateAlphabet(alph.N, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 3;
                    } else {
                        currentState = 0;
                        index--;
                        currentColum--;
                        TokenTable.add(token);
                    }
                    break;
                case 4: // S4 *
                    currentState = 0;
                    index--;
                    currentColum--;
                    TokenTable.add(token);
                    break;
                case 5: // S5
                    if (alph.ValidateAlphabet(alph.Cm, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 4;
                    } else {
                        token.addChar(currentChar);
                        currentState = 6;
                    }
                    break;
                case 6: // S6 
                    if (alph.ValidateAlphabet(alph.Cm, currentChar)) {
                        token.addChar(currentChar);
                        currentState = 4;
                    } else {
                        token.addChar(currentChar);
                        currentState = 6;
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
//        System.out.println("Lexemas: ");
//        for (Token token : TokenTable) {
//            System.out.println("Lexema: " + token.Lexeme + " Location: " + token.Row + ", " + token.Colum);
//        }
//        System.out.println("Errores: ");
//        for (Error error : ErrorTable) {
//            System.out.println("Error: " + error.Error + " Location: " + error.Row + ", " + error.Colum);
//        }
    }
}
