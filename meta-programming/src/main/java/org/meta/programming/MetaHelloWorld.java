package org.meta.programming;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * Hello world!
 *
 */
public class MetaHelloWorld {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append("public class App                              ");
        builder.append("{                                             ");
        builder.append("    public static void main( String[] args )  ");
        builder.append("    {                                         ");
        builder.append("        System.out.println( 'Hello World!' ); ".replaceAll("'", "\""));
        builder.append("    }                                         ");
        builder.append("}                                             ");

        String uri = "string:///" + "App" + Kind.SOURCE.extension;
        SimpleJavaFileObject jfo = new SimpleJavaFileObject(URI.create(uri), Kind.SOURCE) {
            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
                return builder.toString();
            }
        };
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticListener<JavaFileObject> dl = new DiagnosticListener<JavaFileObject>() {

            @Override
            public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
                System.out.println("Line Number->" + diagnostic.getLineNumber());
                System.out.println("code->" + diagnostic.getCode());
                System.out.println("Message->"
                                   + diagnostic.getMessage(Locale.ENGLISH));
                System.out.println("Source->" + diagnostic.getSource());
                System.out.println(" ");
            }
          
        };
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(dl,
                Locale.ENGLISH,
                null);
        CompilationTask task = compiler.getTask(null, fileManager, dl, null, null, Arrays.asList(jfo));
        
        if(task.call())
            System.out.println("--Done--");

    }
}
