/*
 * Copyright (c) 2002, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javasoft.sqe.tests.jakarta.mail.internet.HeaderTokenizer;

import java.io.*;
import java.util.Vector;
import jakarta.mail.*;
import jakarta.mail.internet.HeaderTokenizer;
import jakarta.mail.internet.ParseException;
import com.sun.javatest.*;
import javasoft.sqe.tests.jakarta.mail.util.MailTest;

/**
 * This class tests the <strong>next()</strong> APIs.
 * It does by passing various valid input values and then checking
 * the type of the returned object.     <p>
 *
 *		Return the next token from the parse stream.<p>
 * api2test: public HeaderTokenizer.Token next()  <p>
 *
 * how2test: Call API, if it returns HeaderTokenizer.Token object
 *	     values then testcase passes otherwise it fails.
 */

public class next_Test extends MailTest {

    static boolean return_comments = false;	// return comments as tokens
    static boolean mime = false;		// use MIME specials

    public String value = "ggere, /tmp/mail.out, +mailbox, ~user/mailbox, ~/mailbox, /PN=x400.address/PRMD=ibmmail/ADMD=ibmx400/C=us/@mhs-mci.ebay, C'est bien moche <paris@france>, Mad Genius <george@boole>, two@weeks (It Will Take), /tmp/mail.out, laborious (But Bug Free), cannot@waste (My, Intellectual, Cycles), users:get,what,they,deserve;, it (takes, no (time, at) all), if@you (could, see (almost, as, (badly, you) would) agree), famous <French@physicists>, it@is (brilliant (genius, and) superb), confused (about, being, french)";

    static class TestCase {
	public TestCase(char endOfAtom, String test, String expected) {
	    this.endOfAtom = endOfAtom;
	    this.test = test;
	    this.expected = expected;
	}

	public char endOfAtom;
	public String test;
	public String expected;
    };

    private TestCase[] testCases = {
	new TestCase(';', "a=b c", "b c"),
	new TestCase(';', "a=b c; d=e f", "b c"),
	new TestCase(';', "a=\"b c\"; d=e f", "b c")
    };

    private TestCase[] testCasesEsc = {
	new TestCase(';', "a=b \\c", "b \\c"),
	new TestCase(';', "a=b c; d=e f", "b c"),
	new TestCase(';', "a=\"b \\c\"; d=e f", "b \\c")
    };

    public static void main( String argv[] )
    {
        next_Test test = new next_Test();
        Status s = test.run(argv, System.err, System.out);
        s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
        out.println("\nTesting class HeaderTokenizer: next()\n");

	try {
	   // Create HeaderTokenizer object
	      HeaderTokenizer ht = new HeaderTokenizer(value,
			mime ? HeaderTokenizer.MIME : HeaderTokenizer.RFC822,
			!return_comments);

	      if( ht == null )
		  return Status.failed("Failed to create HeaderTokenizer object!");

	      HeaderTokenizer.Token tok;

	   // BEGIN UNIT TEST 1:
	      /*
	       * XXX - this test is completely lame, it doesn't actually
	       * check that the correct values are returned.
	       */
	      out.println("UNIT TEST 1: next()");

	      while((tok = ht.next()).getType() != HeaderTokenizer.Token.EOF) {  // API TEST
		  if (tok.getType() == 0 || tok.getValue() == null) {
		    out.println("\t" + type(tok.getType()) +
				"\t" + tok.getValue());
		    out.println("UNIT TEST 1: Failed.\n");
		    errors++;
		    break;
		  }
	      }
	      if (errors == 0)
		  out.println("UNIT TEST 1: passed.\n");
	   // END UNIT TEST 1:

	   // BEGIN UNIT TEST 2:
	      out.println("UNIT TEST 2: next(endOfAtom)");

	      int start = errors;
	      for (TestCase tc : testCases) {
		  ht = new HeaderTokenizer(tc.test, HeaderTokenizer.MIME, true);
		  tok = ht.next();
		  if (tok.getType() != HeaderTokenizer.Token.ATOM || 
		    !tok.getValue().equals("a")) {
			out.println("\t" + type(tok.getType()) +
				    "\t" + tok.getValue());
			out.println("UNIT TEST 2: Failed.\n");
			errors++;
			break;
		  } else {
		      tok = ht.next();
		      if (tok.getType() != '=') {
			    out.println("\t" + type(tok.getType()) +
					"\t" + tok.getValue());
			    out.println("UNIT TEST 2: Failed.\n");
			    errors++;
			    break;
		      } else {
			  tok = ht.next(tc.endOfAtom);
			  if (tok.getType() != HeaderTokenizer.Token.QUOTEDSTRING || 
			    !tok.getValue().equals(tc.expected)) {
				out.println("\t" + type(tok.getType()) +
					    "\t" + tok.getValue());
				out.println("UNIT TEST 2: Failed.\n");
				errors++;
				break;
			  }
		      }
		  }
		}
		if (errors == start)
		    out.println("UNIT TEST 2: passed.\n");
	   // END UNIT TEST 2:

	   // BEGIN UNIT TEST 3:
	      out.println("UNIT TEST 3: next(endOfAtom, true)");

	      start = errors;
	      for (TestCase tc : testCasesEsc) {
		  ht = new HeaderTokenizer(tc.test, HeaderTokenizer.MIME, true);
		  tok = ht.next();
		  if (tok.getType() != HeaderTokenizer.Token.ATOM || 
		    !tok.getValue().equals("a")) {
			out.println("\t" + type(tok.getType()) +
				    "\t" + tok.getValue());
			out.println("UNIT TEST 3: Failed.\n");
			errors++;
			break;
		  } else {
		      tok = ht.next();
		      if (tok.getType() != '=') {
			    out.println("\t" + type(tok.getType()) +
					"\t" + tok.getValue());
			    out.println("UNIT TEST 3: Failed.\n");
			    errors++;
			    break;
		      } else {
			  tok = ht.next(tc.endOfAtom, true);
			  if (tok.getType() != HeaderTokenizer.Token.QUOTEDSTRING || 
			    !tok.getValue().equals(tc.expected)) {
				out.println("\t" + type(tok.getType()) +
					    "\t" + tok.getValue());
				out.println("UNIT TEST 3: Failed.\n");
				errors++;
				break;
			  }
		      }
		  }
		}
	        if (errors == start)
		    out.println("UNIT TEST 3: passed.\n");
	   // END UNIT TEST 3:

	      checkStatus();
	} catch (Exception e) {
	      handlException(e);
	}
	return status;
    }

    private static String type(int t) {
	if (t == HeaderTokenizer.Token.ATOM)
	    return "ATOM";
	else if (t == HeaderTokenizer.Token.QUOTEDSTRING)
	    return "QUOTEDSTRING";
	else if (t == HeaderTokenizer.Token.COMMENT)
	    return "COMMENT";
	else if (t == HeaderTokenizer.Token.EOF)
	    return "EOF";
	else if (t < 0)
	    return "UNKNOWN";
	else
	    return "SPECIAL";
    }

    private static int type(String s) {
	if (s.equals("ATOM"))
	    return HeaderTokenizer.Token.ATOM;
	else if (s.equals("QUOTEDSTRING"))
	    return HeaderTokenizer.Token.QUOTEDSTRING;
	else if (s.equals("COMMENT"))
	    return HeaderTokenizer.Token.COMMENT;
	else if (s.equals("EOF"))
	    return HeaderTokenizer.Token.EOF;
	else // if (s.equals("SPECIAL"))
	    return 0;
    }
}
