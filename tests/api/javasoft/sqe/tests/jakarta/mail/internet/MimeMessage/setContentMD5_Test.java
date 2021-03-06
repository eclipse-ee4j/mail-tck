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

package javasoft.sqe.tests.jakarta.mail.internet.MimeMessage;

import java.util.*;
import java.io.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.jakarta.mail.util.MailTest;

/**
 * This class tests the <strong>setContentMD5()</strong> API.
 * It does by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 *		Set the "Content-MD5" header field of Message. <p>
 * api2test: public void setContentMD5(String)  <p>
 *
 * how2test: Call API, then check by calling getContentMD5(), if both the get and
 *           set values are the same then testcase passes, otherwise it fails.
 */

public class setContentMD5_Test extends MailTest {

    public static void main( String argv[] )
    {
        setContentMD5_Test test = new setContentMD5_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class MimeMessage: setContentMD5(String)\n");

        try {
          // Create a MimeMessage object
             Session session = Session.getInstance(properties, null);
             MimeMessage msg = new MimeMessage(session);

             if( msg == null ) {
                 log.println("WARNING: FAILED TO CREATE MESSAGE OBJECT");
                 return Status.failed("Failed to create Message object");
             }
          // BEGIN UNIT TEST:
	     String strvalue = "mind over matter";
             out.println("UNIT TEST 1: setContentMD5("+strvalue+")");

             msg.setContentMD5(strvalue);	// API TEST

	     if( msg.getContentMD5() != null ) {
                 if( strvalue.equals( msg.getContentMD5() ))
                     out.println("UNIT TEST 1: passed\n");
                 else {
                       out.println("UNIT TEST 1: FAILED\n");
                       errors++;
		 }
             } else
		   out.println("Warning: Null object returned by getContentMD5()");

             strvalue = "";
             out.println("UNIT TEST 2: setContentMD5("+strvalue+")");

             msg.setContentMD5(strvalue);       // API TEST

             if( msg.getContentMD5() != null ) {
                 if( strvalue.equals( msg.getContentMD5() ))
                     out.println("UNIT TEST 2: passed\n");
                 else {
                       out.println("UNIT TEST 2: FAILED\n");
                       errors++;
                 }
             } else
                   out.println("Warning: Null object returned by getContentMD5()");

          // END UNIT TEST:
             checkStatus();

        } catch ( Exception e ) {
	     handlException(e);
        }
	return status;
     }
}
