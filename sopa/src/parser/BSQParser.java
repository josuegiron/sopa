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
import sopa.Busqueda;
import sopa.Container;
import sopa.Matriz;
import sopa.Search;
import sopa.Variable;

/**
 *
 * @author josue
 */
public class BSQParser {

    public ArrayList<Token> tokenList;
    public ArrayList<Error> ErrorTable;
    public ArrayList<Variable> SimbolTable;
    private ArrayList<Integer> terms;
    private ArrayList<String> signos;
    private ArrayList<Busqueda> busquedas;
    private Alphabet alph;
    private Busqueda busqueda;
    public Error error;
    public Matriz matriz;
    int column = 0, row = 0, currentColumn = 0, currentRow = 0, numPosition = 0;
    Variable temp;
    

    public BSQParser(ArrayList<Token> tokenList, Matriz matriz) {
        this.matriz = matriz;
        ErrorTable = new ArrayList<error.Error>();
        SimbolTable = new ArrayList<Variable>();
        this.tokenList = tokenList;
        Token end = new Token(0, 0, 0, 0, "", 0);
        this.tokenList.add(end);
        alph = new Alphabet();
        terms = new ArrayList<Integer>();
        signos = new ArrayList<String>();
        busquedas = new ArrayList<Busqueda>();
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
            AddError(15, tokenList.get(0));
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
            AddError(16, tokenList.get(0));
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
            AddError("ID", tokenList.get(0));
        } else {
            agregarVariable(tokenList.get(0).Lexeme);
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
                    AddError("ID", tokenList.get(0));
                } else {
                    agregarVariable(tokenList.get(0).Lexeme);
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
        terms = new ArrayList<Integer>();
        signos = new ArrayList<String>();
        
        if (!validateTokenType(3)) {
            AddError("ID", tokenList.get(0));
        } else {
            temp = obtenerVariable(tokenList.get(0).Lexeme);
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
        temp.setSignos(signos);
        temp.setTerms(terms);
        temp.valuteExpresion();
        SimbolTable.add(temp);
        System.out.println("Variable: "+ temp.name+"="+ temp.value);
    }
    
    private void expresion() {
        
        termino();
        otroTermino();

    }

    private void termino() {
        switch (tokenList.get(0).Type) {
            case 2:
                terms.add(Integer.parseInt(tokenList.get(0).Lexeme));
                tokenList.remove(0);
                break;
            case 3:
                terms.add(obtenerVariable(tokenList.get(0).Lexeme).value);;
                tokenList.remove(0);
                break;
            default:
                AddError("NUMERO o ID", tokenList.get(0));
                break;
        }

    }

    private void otroTermino() {
        switch (tokenList.get(0).Type) {
            case 9:
                signos.add(tokenList.get(0).Lexeme);
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
        busqueda = new Busqueda();
        if (!validateToken(alph.GetReservedWord(3))) {
            AddError(3, tokenList.get(0));
        } else {
            tokenList.remove(0);
        }
        if (!validateTokenType(3)) {
            AddError("ID", tokenList.get(0));
        } else {
            busqueda.setName(tokenList.get(0).Lexeme);
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
        System.out.println(busqueda.name);
        System.out.println(busqueda.word);
        System.out.println(busqueda.column);
        System.out.println(busqueda.row);
        System.out.println(busqueda.searchType);
        System.out.println(busqueda.fontColor);
        System.out.println(busqueda.backColor);
        System.out.println("-----");
        Search search = new Search(matriz, busqueda);
        if(search.searchWord()){
            System.out.println("deberia pintar");
            busqueda.paintWord();
        }
        busquedas.add(busqueda);
        
        
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
            AddError(4, tokenList.get(0));
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
            AddError("CADENA", tokenList.get(0));
        } else {
            busqueda.setWord(tokenList.get(0).Lexeme.replace("\"", ""));
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
            AddError(5, tokenList.get(0));
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
                busqueda.setColumn(Integer.parseInt(tokenList.get(0).Lexeme));
                tokenList.remove(0);
                break;
            case 3:
                busqueda.setColumn(obtenerVariable(tokenList.get(0).Lexeme).value);
                tokenList.remove(0);
                break;
            default:
                AddError("NUMERO o ID", tokenList.get(0));
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
            AddError(6, tokenList.get(0));
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
                busqueda.setRow(Integer.parseInt(tokenList.get(0).Lexeme));
                tokenList.remove(0);
                break;
            case 3:
                busqueda.setRow(obtenerVariable(tokenList.get(0).Lexeme).value);
                tokenList.remove(0);
                break;
            default:
                AddError("NUMERO o ID", tokenList.get(0));
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
            AddError(7, tokenList.get(0));
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
                busqueda.setSearchType(1);
                tokenList.remove(0);
                break;
            case "horizontal_izquierda":
                busqueda.setSearchType(2);
                tokenList.remove(0);
                break;
            case "vertical_abajo":
                busqueda.setSearchType(4);
                tokenList.remove(0);
                break;
            case "vertical_arriba":
                busqueda.setSearchType(3);
                tokenList.remove(0);
                break;
            default:
                AddError("palabra reservada", tokenList.get(0));
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
            AddError(8, tokenList.get(0));
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
            AddError("COLOR HEXADECIMAL", tokenList.get(0));
        } else {
            busqueda.setFontColor(tokenList.get(0).Lexeme);
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
            AddError(9, tokenList.get(0));
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
            AddError("COLOR HEXADECIMAL", tokenList.get(0));
        } else {
            busqueda.setBackColor(tokenList.get(0).Lexeme);
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
    
    private void agregarVariable(String name){
        if(!validarVariable(name)){
            SimbolTable.add(new Variable(name));
        }else{
            AddError("nombre de variable distinto", tokenList.get(0));
        }
    }
    
    private boolean validarVariable(String name){
        for(Variable var: SimbolTable){
            if(var.name.equals(name)){
                return true;
            }
        }
        return false;
    }
    
    private Variable obtenerVariable(String name){
        for(Variable var: SimbolTable){
            if(var.name.equals(name)){
                return var;
            }
        }
        AddError("que la variable estuviera declarada", tokenList.get(0));
        Variable err =  new Variable();
        err.setValue(0);
        return err;
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
