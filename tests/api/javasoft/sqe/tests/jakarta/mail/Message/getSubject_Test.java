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

package javasoft.sqe.tests.jakarta.mail.Message;

import java.util.*;
import java.io.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.jakarta.mail.util.MailTest;

/**
 * This class tests the <strong>getSubject()</strong> API.
 * It does this by invoking the test api and then checking
 * the type of the returned object.	<p>
 *
 *		Get the subject of this message. <p>
 * api2test: public String getSubject()  <p>
 *
 * how2test: Call this API on given message object, verify that it returns a string
 *	     object, write this to stdio. If this operation is successfull then this
 *	     testcase passes, otherwise it fails. <p>
 *
 *	     Returns the value of the "Subject" header field. Returns null
 *	     if the subject field is unavailable or its value is absent.
 */

public class getSubject_Test extends MailTest {

    public static void main( String argv[] )
    {
        getSubject_Test test = new getSubject_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class Message: getSubject()\n");

        try {
          // Connect to host server
             Store store = connect2host(protocol, host, user, password);

          // Get a Folder object
	     Folder root = getRootFolder(store);
             Folder folder = root.getFolder(mailbox);

             if( folder == null ) {
                 return Status.failed("Invalid folder object!");
             }
             folder.open(Folder.READ_ONLY);

	     if( msgcount == -1 ) {
                 msgcount = folder.getMessageCount();
                 if( msgcount < 1 )
                     return Status.failed("Mail folder is empty!");
             }

             for (int i = 1; i <= msgcount; i++)
             {
            // Get the message
               MimeMessage msg =  (MimeMessage)folder.getMessage(i);

	       if( msg == null ) {
		   log.println("WARNING: FAILED TO GET MESSAGE NUMBER: "+ i);
		   continue;
	       }
	    // BEGIN UNIT TEST:
	       // Get the value of subject header field
	       out.println("UNIT TEST "+ i +":  getSubject()");

	       String subject = msg.getSubject();	// API TEST

	       if( subject != null ) {
	           out.println("getSubject() :=> '"+ subject +"'");
                   out.println("UNIT TEST "+ i +":  passed\n");
	       } else {
		       out.println("WARNING: Message "+ i +" has null Subject header");
	       }
	    // END UNIT TEST:
	     }
	     folder.close(false);
	     store.close();
	     status = Status.passed("OKAY");

        } catch ( Exception e ) {
	     handlException(e);
        }
	return status;
     }
}
