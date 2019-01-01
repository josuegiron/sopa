/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import scanner.Alphabet;
import scanner.Token;
import error.Error;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import sopa.Container;
import sopa.Matriz;

/**
 *
 * @author josue
 */
public class LTSParser {

    public ArrayList<Token> tokenList;
    private Alphabet alph;
    public ArrayList<Error> ErrorTable;
    public Error error;
    public Matriz matriz;
    int column = 0, row = 0, currentColumn = 0, currentRow = 0, numPosition = 0;

    public LTSParser(ArrayList<Token> tokenList) {
        ErrorTable = new ArrayList<error.Error>();
        this.tokenList = tokenList;
        Token end = new Token(0, 0, 0, 0, "", 0);
        this.tokenList.add(end);
        alph = new Alphabet();
        matriz();

    }

    private boolean validateToken(String lexeme) {
        String token = tokenList.get(0).Lexeme.toLowerCase();
        if (token.equals(lexeme)) {
            return true;
        }
        return false;
    }

    private boolean validateTokenType(int tokenID) {
        //System.out.println(tokenList.get(0).Lexeme);
        if (tokenList.get(0).Type == tokenID) {
            return true;
        }
        return false;
    }

    private void matriz() {

        if (!validateToken(alph.GetReservedWord(1))) {
            PrintError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(2)) {
            AddError("numero", tokenList.get(0));
        } else {
            row = Integer.parseInt(tokenList.get(0).Lexeme);
            tokenList.remove(0);
        }
        if (!validateToken(alph.GetReservedWord(2))) {
            AddError(2, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(2)) {
            AddError("numero", tokenList.get(0));
        } else {
            column = Integer.parseInt(tokenList.get(0).Lexeme);
            tokenList.remove(0);
        }

        matriz = new Matriz(row, column);

        positions();
    }

    private void positions() {
        switch (tokenList.get(0).Lexeme) {
            case "[":
                if(numPosition < row*column){
                     position();
                }else{
                    AddError("Las letras ingresadas sobrepasan la matriz", tokenList.get(0));
                }
                break;
            default:
                
                if(numPosition != row*column){
                    AddError("La matriz no esta completa o se pasa", tokenList.get(0));
                }
                
                break;
        }

    }

    private void position() {
        if (!validateToken("[")) {
            AddError("[", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(3)) {
            AddError(3, tokenList.get(0));
        } else {
            Container container = new Container(currentRow, currentColumn, tokenList.get(0).Lexeme, JLabel.CENTER);
            
            matriz.sopa[currentRow][currentColumn] = container;
            next();
            tokenList.remove(0);
        }
        if (!validateToken("]")) {
            AddError("]", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        positions();
    }

    private void next() {
        currentColumn++;
        numPosition++;
        if (currentColumn >= column) {
            currentColumn = 0;
            currentRow++;
        }
    }

    private void AddError(String description, Token token) {
        ErrorTable.add(new Error(0, token.Row, token.Row, token.Lexeme, "Se esperaba " + description, token.Offset));
        PrintError(":", tokenList.get(0));
    }

    private void AddError(int id, Token token) {
        ErrorTable.add(new Error(0, token.Row, token.Row, token.Lexeme, "Se esperaba " + alph.GetReservedWord(id), token.Offset));
        PrintError(id, tokenList.get(0));
    }

    private void PrintError(int rwID, Token token) {
        System.out.println("Error lexico: se esperaba la palabra reservada " + alph.GetReservedWord(rwID) + " en: " + token.Row + ", " + token.Colum);
    }

    private void PrintError(String type, Token token) {
        System.out.println("Error lexico: se esperaba  " + type + " en: " + token.Row + ", " + token.Colum);
    }
}
