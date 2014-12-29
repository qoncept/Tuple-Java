package jp.co.qoncept.util;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class TupleGenerator {
	private static final int DEFAULT_MAX_NUMBER_OF_ELEMENTS = 0xFF;

	public static void main(String[] args) {
		File directory = new File(".");
		int maxNumberOfElements = DEFAULT_MAX_NUMBER_OF_ELEMENTS;

		switch (args.length) {
		case 0: {
			printSyntax(out);
			return;
		}
		case 1:
		case 2: {
			try {
				maxNumberOfElements = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				printSyntaxError();
				return;
			}

			break;
		}
		default: {
			if (!"-o".equals(args[0])) {
				printSyntaxError();
				return;
			}

			directory = new File(args[1]);
			if (directory.exists()) {
				if (!directory.isDirectory()) {
					err.println("Error:");
					err.println(directory.getPath() + " is not a directory.");
					return;
				}
			} else {
				try {
					directory.mkdir();
				} catch (SecurityException e) {
					err.println("Error:");
					err.println("Cannot make a directory at "
							+ directory.getPath() + ".");
					e.printStackTrace(err);
					return;
				}
			}

			try {
				maxNumberOfElements = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				printSyntaxError();
				return;
			}

			break;
		}
		}

		try {
			for (int numberOfElements = 0; numberOfElements <= maxNumberOfElements; numberOfElements++) {
				generateTuple(directory, numberOfElements);
			}
		} catch (IOException e) {
			err.println("Error:");
			err.println("Cannot make files.");
			e.printStackTrace(err);
		} catch (SecurityException e) {
			err.println("Error:");
			err.println("Cannot make files.");
			e.printStackTrace(err);
		}
	}

	private static void printSyntaxError() {
		err.println("Syntax error.");
		err.println();

		printSyntax(err);
	}

	private static void printSyntax(PrintStream out) {
		out.println("Syntax:");
		out.println("java TupleGenerator [-o path/to/output/directory] maxNumberOfElements");
		out.println();

		out.println("Example:");
		out.println("java TupleGenerator -o src-gen 255");
	}

	private static void generateTuple(File directory, int numberOfElements)
			throws IOException {
		File temporaryFile = null;
		try {
			temporaryFile = File.createTempFile("Tuple" + numberOfElements,
					".java", directory);

			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(temporaryFile)));
				writeTuple(writer, numberOfElements);
				writer.flush();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}

			temporaryFile.renameTo(new File(directory, "Tuple"
					+ numberOfElements + ".java"));
			temporaryFile = null;
		} finally {
			if (temporaryFile != null) {
				temporaryFile.delete();
			}
		}
	}

	private static void writeTuple(BufferedWriter writer, int numberOfElements)
			throws IOException {
		int indentation = 0;

		String className = "Tuple" + numberOfElements;

		{ // package
			writeIndentation(writer, 0);
			writer.write("package jp.co.qoncept.util;");
			writer.newLine();
		}

		{
			writer.newLine();
		}

		{ // class
			{ // class begin
				writeIndentation(writer, 0);
				writer.write("public class ");
				writer.write(className);

				if (numberOfElements > 0) {
					writer.write("<");

					boolean first = true;
					for (int i = 0; i < numberOfElements; i++) {
						if (first) {
							first = false;
						} else {
							writer.write(", ");
						}

						writeType(writer, i);
					}

					writer.write(">");
				}

				writer.write(" {");
				writer.newLine();
			}

			{ // class body
				indentation++;

				{ // fields
					for (int i = 0; i < numberOfElements; i++) {
						writeIndentation(writer, indentation);
						writer.write("private final ");
						writeType(writer, i);
						writer.write(" ");
						writeValue(writer, i);
						writer.write(";");
						writer.newLine();
					}
				}

				if (numberOfElements > 0) {
					writer.newLine();
				}

				{ // constructor
					{ // constructor begin
						writeIndentation(writer, indentation);

						writer.write("public ");
						writer.write(className);
						writer.write("(");

						boolean first = true;
						for (int i = 0; i < numberOfElements; i++) {
							if (first) {
								first = false;
							} else {
								writer.write(", ");
							}

							writeType(writer, i);
							writer.write(" ");
							writeValue(writer, i);
						}

						writer.write(") {");
						writer.newLine();
					}

					{ // constructor body
						indentation++;

						for (int i = 0; i < numberOfElements; i++) {
							writeIndentation(writer, indentation);
							writer.write("this.");
							writeValue(writer, i);
							writer.write(" = ");
							writeValue(writer, i);
							writer.write(";");
							writer.newLine();
						}

						indentation--;
					}

					{ // constructor end
						writeIndentation(writer, indentation);
						writer.write("}");
						writer.newLine();
					}
				}

				{ // methods
					for (int i = 0; i < numberOfElements; i++) {
						{
							writer.newLine();
						}

						{ // method
							{ // method begin
								writeIndentation(writer, indentation);
								writer.write("public ");
								writeType(writer, i);
								writer.write(" ");
								writeGetter(writer, i);
								writer.write("() {");
								writer.newLine();
							}

							{ // method body
								indentation++;

								writeIndentation(writer, indentation);
								writer.write("return ");
								writeValue(writer, i);
								writer.write(";");
								writer.newLine();

								indentation--;
							}

							{ // method end
								writeIndentation(writer, indentation);
								writer.write("}");
								writer.newLine();
							}
						}
					}
				}

				indentation--;
			}

			{ // class end
				writeIndentation(writer, indentation);
				writer.write("}");
				writer.newLine();
			}
		}

	}

	private static void writeType(BufferedWriter writer, int i)
			throws IOException {
		writer.write("T");
		writer.write(Integer.toString(i));
	}

	private static void writeValue(BufferedWriter writer, int i)
			throws IOException {
		writer.write("value");
		writer.write(Integer.toString(i));
	}

	private static void writeGetter(BufferedWriter writer, int i)
			throws IOException {
		writer.write("get");
		writer.write(Integer.toString(i));
	}

	private static void writeIndentation(BufferedWriter writer, int indentation)
			throws IOException {
		for (int i = 0; i < indentation; i++) {
			writer.write('\t');
		}
	}
}
