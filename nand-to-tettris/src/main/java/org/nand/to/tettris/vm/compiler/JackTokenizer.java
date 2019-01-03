package org.nand.to.tettris.vm.compiler;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class JackTokenizer implements Closeable {
    private int       current = 0;
    private char[]    chars;
    private TokenType tokenType;
    private KeyWord   keyWord;
    private char      symbol;
    private String    identifier;
    private int       intVal;
    private String    stringVal;

    public JackTokenizer(File inputFile) throws IOException {
        String content = Files.lines(inputFile.toPath())
                .map(l -> l.replaceAll("\\/\\/.*", ""))
                .map(l -> l.replaceAll("^\\s*(?=\\S)", " "))
                .map(l -> l.replaceAll("^\\s*\\*.*$", ""))
                .map(l -> l.replaceAll("^\\s*\\/\\*\\*.*$", ""))
                .map(l -> l.replaceAll("\\/\\*\\*.*\\*\\/", ""))
                .filter(l -> !l.trim().isEmpty())
                .collect(Collectors.joining(" "));

        content = content.replaceAll("\\/\\*\\*.*\\*\\/", "");
        chars = content.toCharArray();

    }

    public boolean hasMoreTokens() {
        return current < chars.length;
    }
    
    public void reset() {
    	symbol = 0;
    }

    public void advance() {
        if (!hasMoreTokens())
            return;

        char c = chars[current];
        switch (c) {
        case ' ':
            current++;
            advance();
            break;
        case '{':
        case '}':
        case '(':
        case ')':
        case '[':
        case ']':
        case '.':
        case ',':
        case ';':
        case '+':
        case '-':
        case '*':
        case '/':
        case '&':
        case '|':
        case '<':
        case '>':
        case '=':
        case '~':
            tokenType = TokenType.SYMBOL;
            symbol = c;
            current++;
            break;
        case '"':
            tokenType = TokenType.STRING_CONST;
            readStringVal();
            reset();
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            tokenType = TokenType.INT_CONST;
            intVal = Integer.parseInt(readWord());
            reset();
            break;
        default:
        	reset();
            tokenType = TokenType.KEYWORD;
            String word = readWord();
            switch (word) {
            case "class":
                keyWord = KeyWord.CLASS;
                break;
            case "constructor":
                keyWord = KeyWord.CONSTRUCTOR;
                break;
            case "function":
                keyWord = KeyWord.FUNCTION;
                break;
            case "method":
                keyWord = KeyWord.METHOD;
                break;
            case "field":
                keyWord = KeyWord.FIELD;
                break;
            case "static":
                keyWord = KeyWord.STATIC;
                break;
            case "var":
                keyWord = KeyWord.VAR;
                break;
            case "int":
                keyWord = KeyWord.INT;
                break;
            case "char":
                keyWord = KeyWord.CHAR;
                break;
            case "boolean":
                keyWord = KeyWord.BOOLEAN;
                break;
            case "void":
                keyWord = KeyWord.VOID;
                break;
            case "true":
                keyWord = KeyWord.TRUE;
                break;
            case "false":
                keyWord = KeyWord.FALSE;
                break;
            case "null":
                keyWord = KeyWord.NULL;
                break;
            case "this":
                keyWord = KeyWord.THIS;
                break;
            case "let":
                keyWord = KeyWord.LET;
                break;
            case "do":
                keyWord = KeyWord.DO;
                break;
            case "if":
                keyWord = KeyWord.IF;
                break;
            case "else":
                keyWord = KeyWord.ELSE;
                break;
            case "while":
                keyWord = KeyWord.WHILE;
                break;
            case "return":
                keyWord = KeyWord.RETURN;
                break;
            default:
                identifier = word;
                tokenType = TokenType.IDENTIFIER;
            }
            break;

        }

    }

    private String readWord() {
        char c;
        String word = "";
        while (String.valueOf(c = chars[current++]).matches("[A-Za-z0-9_]"))
            word += String.valueOf(c);
        current--;
        return word;
    }

    private void readStringVal() {
        char c;
        stringVal = "";
        current++;
        while ((c = chars[current++]) != '"')
            stringVal += String.valueOf(c);
    }

    public TokenType tokenType() {
        return tokenType;
    }

    public KeyWord keyWord() {
        return keyWord;
    }

    public char symbol() {
        return symbol;
    }

    public String identifier() {
        return identifier;
    }

    public int intVal() {
        return intVal;
    }

    public String stringVal() {
        return stringVal;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }
}
