/* HEADER */

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

import java.io.*;

/**
 * Generate large specification.
 *
 * @version 	$ProjectVersion: 0.393 $
 * @author 	Mark Richters
 */

final class GenSpec {

    public static void main(String args[]) {
	writeSpecs();
    }

    private static void writeSpecs() {
	int numClasses = 50;
	PrintWriter out = null;
	try {
	    out = new PrintWriter(new BufferedWriter(new FileWriter("Test1.use")));
	    out.println("model Test1");
	    for (int i = 0; i < numClasses; i++) {
		out.println("class C" + i);
		out.println("attributes");
		out.println("a1 : Integer;");
		out.println("end");
	    }

	    // connect each class with each other
	    for (int i = 0; i < numClasses; i++) {
		for (int j = 0; j < numClasses; j++) {
		    if ( i != j ) {
			String aName = "A_" + i + "_" + j;
			out.println("association " + aName + " between");
			out.println("C" + i + "[*] role " + aName + "C" + i + ";");
			out.println("C" + j + "[*] role " + aName + "C" + j + ";");
			out.println("end");
		    }
		}
	    }
	} catch (IOException e) {
	    System.err.println(e.getMessage());
	} finally {
	    if ( out != null ) 
		out.close();
	}
    }

}
