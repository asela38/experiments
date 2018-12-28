package org.nand.to.tettris.compiler.analyser;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CompilationEngine implements Closeable {
	private JackTokenizer tokenizer;
	private PrintWriter writer;

	public CompilationEngine(File inFile, File outFile) throws IOException {
		tokenizer = new JackTokenizer(inFile);
		writer = new PrintWriter(outFile);

		// debugTokenizer();

		compileClass();
	}

	private void debugTokenizer() {
		while (tokenizer.hasMoreTokens()) {
			tokenizer.advance();
			switch (tokenizer.tokenType()) {
			case IDENTIFIER:
				System.out.printf("%s (%s) %n", tokenizer.identifier(), tokenizer.tokenType());
				break;
			case INT_CONST:
				System.out.printf("%s (%s) %n", tokenizer.intVal(), tokenizer.tokenType());
				break;
			case KEYWORD:
				System.out.printf("%s (%s) %n", tokenizer.keyWord(), tokenizer.tokenType());
				break;
			case STRING_CONST:
				System.out.printf("%s (%s) %n", tokenizer.stringVal(), tokenizer.tokenType());
				break;
			case SYMBOL:
				System.out.printf("%s (%s) %n", tokenizer.symbol(), tokenizer.tokenType());
				break;
			default:
				System.out.printf(" Unrecognized Type %n");
				break;

			}

		}
	}

	private void openTag(String tag) {
		writer.printf("<%s>%n", tag);
	}

	private void closeTag(String tag) {
		writer.printf("</%s>%n", tag);
	}

	private void singleTag(String key, String value) {
		writer.printf("<%1$s> %2$s </%1$s>%n", key, value);
	}

	private void keyWordTag() {
		singleTag("keyword", tokenizer.keyWord().name().toLowerCase());
		tokenizer.advance();
	}

	private void identifierTag() {
		singleTag("identifier", tokenizer.identifier());
		tokenizer.advance();
	}

	private void symbolTag() {
		String symbol = String.valueOf(tokenizer.symbol());
		switch (symbol) {
		case ">":
			symbol = "&gt;";
			break;
		case "<":
			symbol = "&lt;";
			break;
		case "&":
			symbol = "&amp;";
			break;
		case "\"":
			symbol = "&quot;";
			break;
		default:
			break;
		}
		singleTag("symbol", symbol);
		tokenizer.advance();
	}

	private void integerConstant() {
		singleTag("integerConstant", String.valueOf(tokenizer.intVal()));
		tokenizer.advance();
	}

	private void stringConstant() {
		singleTag("stringConstant", tokenizer.stringVal());
		tokenizer.advance();
	}

	public void compileClass() {
		tokenizer.advance();
		openTag("class");

		keyWordTag();
		identifierTag();
		symbolTag();

		outer: while (tokenizer.tokenType() == TokenType.KEYWORD) {
			switch (tokenizer.keyWord()) {

			case STATIC:
			case FIELD:
				compileClassVarDec();
				break;
			case FUNCTION:
			case METHOD:
			case CONSTRUCTOR:
				compileSubroutineDec();
				break;
			default:
				break outer;
			}
		}

		symbolTag();
		closeTag("class");
	}

	public void compileClassVarDec() {
		openTag("classVarDec");

		keyWordTag();
		keyOrIdTag();
		identifierTag();
		while (tokenizer.symbol() == ',') {
			symbolTag();
			identifierTag();
		}
		symbolTag();

		closeTag("classVarDec");
	}

	private void keyOrIdTag() {
		if (tokenizer.tokenType() == TokenType.KEYWORD)
			keyWordTag();
		else
			identifierTag();
	}

	public void compileSubroutineDec() {
		openTag("subroutineDec");
		keyWordTag();
		keyOrIdTag();
		identifierTag();
		symbolTag();
		compileParameterList();
		symbolTag();
		compileSubroutineBody();
		closeTag("subroutineDec");

	}

	public void compileParameterList() {
		openTag("parameterList");
		if (tokenizer.symbol() != ')') {
			keyOrIdTag();
			identifierTag();
		}
		while (tokenizer.symbol() == ',') {
			symbolTag();

			keyOrIdTag();

			identifierTag();
		}

		closeTag("parameterList");

	}

	public void compileSubroutineBody() {
		openTag("subroutineBody");
		symbolTag();
		compileVarDec();
		compileStatements();
		symbolTag();
		closeTag("subroutineBody");
	}

	public void compileVarDec() {
		while (tokenizer.keyWord() == KeyWord.VAR) {

			openTag("varDec");
			keyWordTag();
			keyOrIdTag();
			identifierTag();
			while (tokenizer.symbol() == ',') {
				symbolTag();
				identifierTag();
			}
			symbolTag();
			closeTag("varDec");
		}

	}

	public void compileStatements() {
		openTag("statements");
		outer: while (tokenizer.tokenType() != TokenType.SYMBOL) {

			switch (tokenizer.keyWord()) {
			case LET:
				compileLet();
				break;
			case IF:
				compileIf();
				break;
			case WHILE:
				compileWhile();
				break;
			case DO:
				compileDo();
				break;
			case RETURN:
				compileReturn();
				break;
			default:
				break outer;
			}

		}
		closeTag("statements");
	}

	public void compileLet() {
		openTag("letStatement");
		keyWordTag();
		identifierTag();
		if (tokenizer.symbol() == '[') {
			symbolTag();
			compileExpression();
			symbolTag();
		}
		symbolTag();
		compileExpression();
		symbolTag();
		closeTag("letStatement");
	}

	public void compileIf() {
		openTag("ifStatement");
		keyWordTag();
		symbolTag();
		compileExpression();
		symbolTag();
		symbolTag();
		compileStatements();
		symbolTag();

		if (tokenizer.keyWord() == KeyWord.ELSE) {

			keyWordTag();
			symbolTag();
			compileStatements();
			symbolTag();
		}

		closeTag("ifStatement");
	}

	public void compileWhile() {
		openTag("whileStatement");
		keyWordTag();
		symbolTag();
		compileExpression();
		symbolTag();
		symbolTag();
		compileStatements();
		symbolTag();
		closeTag("whileStatement");
	}

	public void compileDo() {
		openTag("doStatement");
		keyWordTag();
		identifierTag();
		if (tokenizer.symbol() == '.') {
			symbolTag();
			identifierTag();
		}
		symbolTag();
		compileExpressionList();
		symbolTag();
		symbolTag();
		closeTag("doStatement");
	}

	public void compileReturn() {
		openTag("returnStatement");
		keyWordTag();
		if (tokenizer.symbol() != ';')
			compileExpression();
		symbolTag();
		closeTag("returnStatement");
	}

	private List<String> EOPS = Arrays.asList("+", "*", "-", "/", "&", "|", "<", ">", "=");

	public void compileExpression() {
		openTag("expression");
		compileTerm();
		while (EOPS.contains(String.valueOf(tokenizer.symbol()))) {
			symbolTag();
			compileTerm();
		}
		closeTag("expression");
	}

	public void compileTerm() {
		openTag("term");
		switch (tokenizer.tokenType()) {
		case IDENTIFIER:

			identifierTag();
			switch (tokenizer.symbol()) {
			case '.':
				symbolTag();
				identifierTag();
				symbolTag();
				compileExpressionList();
				symbolTag();
				break;
			case '(':
				symbolTag();
				compileExpression();
				symbolTag();
				break;
			case '[':
				symbolTag();
				compileExpression();
				symbolTag();
				break;

			default:
				break;
			}

			break;
		case INT_CONST:
			integerConstant();
			break;
		case KEYWORD:
			keyWordTag();
			break;
		case STRING_CONST:
			stringConstant();
			break;
		case SYMBOL:

			if (tokenizer.symbol() == '~') {
				symbolTag();
				compileTerm();
			} else if (tokenizer.symbol() == '(') {
				symbolTag();
				compileExpression();
				symbolTag();
			} else {
				symbolTag();
			}
			break;
		default:
			break;

		}
		closeTag("term");
	}

	public void compileExpressionList() {
		openTag("expressionList");
		if (tokenizer.symbol() != ')')
			compileExpression();
		while (tokenizer.symbol() == ',') {
			symbolTag();
			compileExpression();
		}
		closeTag("expressionList");
	}

	@Override
	public void close() throws IOException {
		writer.flush();
		writer.close();
		tokenizer.close();
	}

	@Override
	public String toString() {
		switch (tokenizer.tokenType()) {
		case IDENTIFIER:
			return String.format("%s (%s) %n", tokenizer.identifier(), tokenizer.tokenType());
		case INT_CONST:
			return String.format("%s (%s) %n", tokenizer.intVal(), tokenizer.tokenType());
		case KEYWORD:
			return String.format("%s (%s) %n", tokenizer.keyWord(), tokenizer.tokenType());
		case STRING_CONST:
			return String.format("%s (%s) %n", tokenizer.stringVal(), tokenizer.tokenType());
		case SYMBOL:
			return String.format("%s (%s) %n", tokenizer.symbol(), tokenizer.tokenType());
		default:
			return String.format(" Unrecognized Type %n");

		}

	}
}
