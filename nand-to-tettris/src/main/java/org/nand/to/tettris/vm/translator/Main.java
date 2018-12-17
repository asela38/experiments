package org.nand.to.tettris.vm.translator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1)
			throw new IllegalArgumentException("No file specified");
		System.out.println(Arrays.toString(args));
		Pattern filePattern = Pattern.compile("(.*).vm");
		Matcher fileMatcher = filePattern.matcher(args[0]);
		if (!fileMatcher.matches())
			throw new IllegalArgumentException("File Name doesnt match the format: " + filePattern.pattern());

		String fileName = fileMatcher.group(1);
		try (Parser parser = new Parser(String.format("%s.vm", fileName));
				CodeWriter writer = new CodeWriter(String.format("%s.asm", fileName))) {
			while (parser.hasMoreCommands()) {
				parser.advance();
				writer.writeComment(parser.current());
				switch (parser.commandType()) {
				case C_ARITHMETIC:
					writer.writeArithmetic(parser.arg1());
					break;
				case C_CALL:
					break;
				case C_FUNCTION:
					break;
				case C_GOTO:
                    writer.writeGoto(parser.arg1());
					break;
				case C_IF:
                    writer.writeIf(parser.arg1());
					break;
				case C_LABEL:
                    writer.writeLabel(parser.arg1());
					break;
				case C_POP:
					writer.writePop(parser.arg1(), parser.arg2());
					break;
				case C_PUSH:
					writer.writePush(parser.arg1(), parser.arg2());
					break;
				case C_RETURN:
					break;
				default:
					break;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
