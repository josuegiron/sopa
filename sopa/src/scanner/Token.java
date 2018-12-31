/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

/**
 *
 * @author josuegiron
 */
public class Token {

    public int ID, Token, Row, Colum, Offset;

    public int getID() {
        return ID;
    }
    
    public int Length(){
        return Lexeme.length();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getToken() {
        return Token;
    }

    public void setToken(int Token) {
        this.Token = Token;
    }

    public int getRow() {
        return Row;
    }

    public void setRow(int Row) {
        this.Row = Row;
    }

    public int getColum() {
        return Colum;
    }

    public void setColum(int Colum) {
        this.Colum = Colum;
    }

    public String getLexeme() {
        return Lexeme;
    }

    public void setLexeme(String Lexeme) {
        this.Lexeme = Lexeme;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public void addChar(char currentChar) {
        this.Lexeme += currentChar;
    }

    public Token(int ID, int Token, int Row, int Colum, String Lexeme, int Type) {
        this.ID = ID;
        this.Token = Token;
        this.Row = Row;
        this.Colum = Colum;
        this.Lexeme = Lexeme;
        this.Type = Type;
    }

    public Token() {
        this.Lexeme = "";
    }
    public String Lexeme;
            public int Type;
}
