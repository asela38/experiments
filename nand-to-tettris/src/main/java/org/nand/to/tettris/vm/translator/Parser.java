package org.nand.to.tettris.vm.translator;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements Closeable {

	private Scanner scanner;
	private String current;
	private String arg1;
	private int arg2;
	private CommandType commandType;

	enum OpPattern {

		POP("pop (\\S+) (\\d+)"),
		PUSH("push (\\S+) (\\d+)"),
		ARITHMETIC("^(\\S+)$"),
		LABEL("label (\\S+)"),
		IF("if-goto (\\S+)"),
		GOTO("goto (\\S+)"),
		RETURN("return"),
		CALL("call (\\S+) (\\d+)"),
		FUNCTION("function (\\S+) (\\d+)");

		private Pattern pattern;

		private OpPattern(String pattern) {
			this.pattern = Pattern.compile(pattern);
		}

		public Matcher getMatcher(String string) {
			return pattern.matcher(string);
		}
	}

	public Parser(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		System.out.printf("[%s] opens [%s] for read. %n", this.getClass().getName(), file.getAbsolutePath());
		scanner = new Scanner(file);
	}

	public boolean hasMoreCommands() {
		return scanner.hasNextLine();
	}

	public String current() {
		return current;
	}

	public CommandType commandType() {
		return commandType;
	}

	public String arg1() {
		return arg1;
	}

	public int arg2() {
		return arg2;
	}

	public boolean advance() {
		if (hasMoreCommands()) {
			current = scanner.nextLine();
			System.out.printf("[%s]%n", current);
			// Clean the line (remove comments and trim the line)
			current = current.replaceAll("//.*$", " ").trim();

			Matcher popOpMatcher = OpPattern.POP.getMatcher(current);
			Matcher pushOpMatcher = OpPattern.PUSH.getMatcher(current);
			Matcher arithmeticOpMatcher = OpPattern.ARITHMETIC.getMatcher(current);
			Matcher labelOpMatcher = OpPattern.LABEL.getMatcher(current);
			Matcher ifGotoOpMatcher = OpPattern.IF.getMatcher(current);
			Matcher gotoOpMatcher = OpPattern.GOTO.getMatcher(current);
			Matcher callOpMatcher = OpPattern.CALL.getMatcher(current);
			Matcher functionOpMatcher = OpPattern.FUNCTION.getMatcher(current);
			Matcher returnOpMatcher = OpPattern.RETURN.getMatcher(current);

			if (current.isEmpty()) {
				System.out.println("Empty Line" + current);
				advance();
			} else if (arithmeticOpMatcher.matches()) {
				commandType = CommandType.C_ARITHMETIC;
				setArgs(arithmeticOpMatcher);
			} else if (popOpMatcher.matches()) {
				commandType = CommandType.C_POP;
				setArgs(popOpMatcher);
			} else if (pushOpMatcher.matches()) {
				commandType = CommandType.C_PUSH;
				setArgs(pushOpMatcher);
			} else if (labelOpMatcher.matches()) {
				commandType = CommandType.C_LABEL;
				setArgs(labelOpMatcher);
			} else if (ifGotoOpMatcher.matches()) {
				commandType = CommandType.C_IF;
				setArgs(ifGotoOpMatcher);
			} else if (gotoOpMatcher.matches()) {
				commandType = CommandType.C_GOTO;
				setArgs(gotoOpMatcher);
			} else if (callOpMatcher.matches()) {
				commandType = CommandType.C_CALL;
				setArgs(callOpMatcher);
			} else if (functionOpMatcher.matches()) {
				commandType = CommandType.C_FUNCTION;
				setArgs(functionOpMatcher);
			} else if (returnOpMatcher.matches()) {
				commandType = CommandType.C_RETURN;
				setArgs(returnOpMatcher);
			} else {
				System.out.println("Non matching Line: " + current);
				advance();
			}
			return true;
		}
		return false;
	}

	private void setArgs(Matcher matcher) {
		arg1 = matcher.group(1);
		if (matcher.groupCount() > 1)
			arg2 = Integer.parseInt(matcher.group(2));
	}

	@Override
	public void close() throws IOException {
		scanner.close();
	}
}
