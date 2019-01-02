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
public class BSQParser {

    public ArrayList<Token> tokenList;
    private Alphabet alph;
    public ArrayList<Error> ErrorTable;
    public Error error;
    public Matriz matriz;
    int column = 0, row = 0, currentColumn = 0, currentRow = 0, numPosition = 0;

    public BSQParser(ArrayList<Token> tokenList) {
        ErrorTable = new ArrayList<error.Error>();
        this.tokenList = tokenList;
        Token end = new Token(0, 0, 0, 0, "", 0);
        this.tokenList.add(end);
        alph = new Alphabet();
        inicio();

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

    private void inicio() {
        bVariables();
        busquedas();
    }

    private void bVariables() {
        if (!validateToken(alph.GetReservedWord(15))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken("{")) {
            AddError("{", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        cuerpoVariables();   //  Cuerpo de la variable
        if (!validateToken("}")) {
            AddError("}", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void cuerpoVariables() {
        declaraciones();
        asignaciones();
    }

    private void declaraciones() {
        if (validateToken(alph.GetReservedWord(16))) {
            variable();
            declaraciones();
        }
    }

    private void variable() {
        if (!validateToken(alph.GetReservedWord(16))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        ids(); //   muchos id
        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void ids() {

        if (!validateTokenType(3)) {
            AddError(2, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        otherID();
    }

    private void otherID() {
        switch (tokenList.get(0).Lexeme) {
            case ",":
                if (!validateToken(",")) {
                    AddError(",", tokenList.get(0));
                } else {
                    tokenList.remove(0);
                }
                if (!validateTokenType(3)) {
                    AddError(3, tokenList.get(0));
                } else {
                    tokenList.remove(0);
                }
                otherID();
                break;
            default:
                break;
        }
    }

    private void asignaciones() {
        switch (tokenList.get(0).Type) {
            case 3:
                asignacion();
                asignaciones();
                break;
            default:
                break;
        }
    }

    private void asignacion() {
        if (!validateTokenType(3)) {
            AddError(3, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        expresion();
        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void expresion() {
        termino();
        otroTermino();

    }

    private void termino() {
        switch (tokenList.get(0).Type) {
            case 2:
                tokenList.remove(0);
                break;
            case 3:
                tokenList.remove(0);
                break;
            default:
                AddError("numero o id", tokenList.get(0));
                break;
        }

    }

    private void otroTermino() {
        switch (tokenList.get(0).Type) {
            case 9:
                tokenList.remove(0);
                termino();
                otroTermino();
                break;
            default:
                break;
        }

    }

    private void busquedas() {
        switch (tokenList.get(0).Type) {
            case 1:
                bBusqueda();
                busquedas();
                break;
            default:
                break;
        }

    }

    private void bBusqueda() {
        if (!validateToken(alph.GetReservedWord(3))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(3)) {
            AddError(3, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken("{")) {
            AddError("{", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        cuerpoBusqueda();

        if (!validateToken("}")) {
            AddError("}", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

    }

    private void cuerpoBusqueda() {
        palabra();
        columna();
        fila();
        ordenBusqueda();
        colorTexto();
        fondoCasilla();
    }

    private void palabra() {
        if (!validateToken(alph.GetReservedWord(4))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(4)) {
            AddError(4, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void columna() {
        if (!validateToken(alph.GetReservedWord(5))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        switch (tokenList.get(0).Type) {
            case 2:
                tokenList.remove(0);
                break;
            case 3:
                tokenList.remove(0);
                break;
            default:
                AddError("numero o una varialble", tokenList.get(0));
                break;
        }

        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void fila() {
        if (!validateToken(alph.GetReservedWord(6))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        switch (tokenList.get(0).Type) {
            case 2:
                tokenList.remove(0);
                break;
            case 3:
                tokenList.remove(0);
                break;
            default:
                AddError("numero o una varialble", tokenList.get(0));
                break;
        }

        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void ordenBusqueda() {
        if (!validateToken(alph.GetReservedWord(7))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        switch (tokenList.get(0).Lexeme.toLowerCase()) {
            case "horizontal_derecha":
                tokenList.remove(0);
                break;
            case "horizontal_izquierda":
                tokenList.remove(0);
                break;
            case "vertical_abajo":
                tokenList.remove(0);
                break;
            case "vertical_arriba":
                tokenList.remove(0);
                break;
            default:
                AddError(1, tokenList.get(0));
                break;
        }
        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void colorTexto() {
        if (!validateToken(alph.GetReservedWord(8))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(5)) {
            AddError(5, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
    }

    private void fondoCasilla() {
        if (!validateToken(alph.GetReservedWord(9))) {
            AddError(1, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(":")) {
            AddError(":", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }

        if (!validateToken("=")) {
            AddError("=", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(5)) {
            AddError(5, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateToken(";")) {
            AddError(";", tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
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
        PrintError(description, tokenList.get(0));
    }

    private void AddError(int id, Token token) {
        ErrorTable.add(new Error(0, token.Row, token.Row, token.Lexeme, "Se esperaba " + alph.GetReservedWord(id), token.Offset));
        PrintError(id, tokenList.get(0));
    }

    private void PrintError(int rwID, Token token) {
        System.out.println("Error lexico: se esperaba la palabra reservada " + alph.GetReservedWord(rwID) + " en: " + token.Row + ", " + token.Colum + " => " + token.Lexeme);
    }

    private void PrintError(String lexeme, Token token) {
        System.out.println("Error lexico: se esperaba  " + lexeme + " en: " + token.Row + ", " + token.Colum + " => " + token.Lexeme);
    }
}
