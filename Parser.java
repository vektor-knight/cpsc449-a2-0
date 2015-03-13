package a2_0;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.*;

public class Parser {

	private static final Class[] parameters = new Class[] {URL.class};

	public static void main (String [] args) throws IOException{
    
		Scanner input = new Scanner(System.in);	
		Main mainStuff = new Main();
		char[] inputAsArray = null;

		if (args.length == 0) {
			synopsis();
		} else if ((args[0].equals("-h") || args[0].equals("-?") || args[0].equals("--help")) && (args.length == 1)) {
			synopsis();
			System.out.println("\nThis program interprets commands of the format '(<method> {arg}*}' on the command line, finds corresponding methods in <class-name>, and executes them, printing the result to sysout. Terminate with ^D or \"exit\"");
		} else if ((args[0].equals("-h") || args[0].equals("-?") || args[0].equals("--help")) && (args.length != 1)) {
			System.out.println("Qualifier --help (-h, -?) should not appear with any command-line arguments.");
			synopsis();
		} else if (args[0].equals("-v") || args[0].equals("--verbose")) {
			Flags.setVerbose(true); //sets verbose mode in action
			System.out.println("Running in verbose mode...");		
			if ((args[1].contains(".jar"))) {			/* the args[1] (aka the jar file) is valid */
				if (true) {			/* valid, check if the args[2] (aka the class) is valid */
					/* if args[2] is valid, ~ENTER COMPILER PROGRAM~
					MORE TO DO HERE!! */
					
										/*	To load a class, it must be in the classpath
					Tell the class loader to add your new jar file to the classpath
					However, class loader deals with URL
						Therefore, we need to do the following conversion:
							.jar -> URI -> URL -> classloader
					1) There exists a method to convert .jar -> URI
					2) There exists a method to convert URI -> URL
					3) Use the URL to put into classloader!
					*/
				// Source: http://stackoverflow.com/questions/60764/how-should-i-load-jars-dynamically-at-runtime	
				File f = new File(args[1]);
				URL url = f.toURI().toURL();
				URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
				Class<?> sysclass = URLClassLoader.class;
			        try {
			            Method method = sysclass.getDeclaredMethod("addURL", parameters);
			            method.setAccessible(true);
			            method.invoke(sysloader, new Object[] {url});
			        } catch (Throwable t) {
			            t.printStackTrace();
			            throw new IOException("Error, could not add URL to system classloader");
			        }
					
					//We can assume that our .jar file is in the classpath for us to use and have access to
					
					mainStuff.mainMenu();
				} else {
					System.out.println("Could not find class: '" + inputAsArray[2]);
					synopsis();
				}
			} else {
				System.out.println("Could not find jar file: '" + inputAsArray[1]);
				synopsis();
			}
		} else {
			inputAsArray = args[0].toCharArray();
			if (Character.valueOf(inputAsArray[0]) == '-') {
				System.out.println("Unrecognized qualifier '" + inputAsArray[1] + "' in '" + args[0] + "'");
				synopsis();
			} else {
				System.out.println("Unrecognized qualifier '" + inputAsArray[0] + "' in '" + args[0] + "'");
				synopsis();
			}
		}
		
    }
	

	public static void synopsis() {
		System.out.println("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]");
		System.out.println("Arguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]");
		System.out.println("Qualifiers:\n  -v --verbose: Print out detailed errors, warnings, and tracking.\n  -h -? --help: Print out a detailed help message.");
		System.out.println("Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
		return;
	}
	
	
	public static void functions() {
		System.out.println("Function List:\n"
				+ "(add string string) : string\n"
				+ "(add float float) : float\n"
				+ "(add int int) : int\n"
				+ "(sub float float) : float\n"
				+ "(sub int int) : int\n"
				+ "(div int int) : int\n"
				+ "(div float float) : float\n"
				+ "(mul float float) : float\n"
				+ "(mul int int) : int\n"
				+ "(inc float) : float\n"
				+ "(inc int) : int\n"
				+ "(dec int) : int\n"
				+ "(dec float) : float\n"
				+ "(len string) : int\n");
		return;
	}
		
	public static void nonFatalError() {
		// whatever is enclosed by the <>, will be changed later once we get the logic to do so
		// will update for the future 
		System.out.println("Matching function for <function type> not found at offset <offset-number>\n"
				+ "<specific function>\n"
				+ "-----------^\n"
				+ "\n java.text.ParseException: Matching function for <function type> not found\n"
				+ "\t at Excecution.execute(Execution)"
				+ "\t at Method.<init>(Method)"
				+ "\t at Method.main(Methods.blah)");
		return; 
	}
}
