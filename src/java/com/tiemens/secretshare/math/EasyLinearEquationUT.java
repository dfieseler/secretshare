/*******************************************************************************
 * $Id: $
 * Copyright (c) 2009-2010 Tim Tiemens.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * Contributors:
 *     Tim Tiemens - initial API and implementation
 ******************************************************************************/
package com.tiemens.secretshare.math;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.tiemens.secretshare.math.EasyLinearEquation.EasySolve;

public class EasyLinearEquationUT
    extends TestCase
{


 // ==================================================
    // class static data
    // ==================================================

    // ==================================================
    // class static methods
    // ==================================================

    // ==================================================
    // instance data
    // ==================================================

    // ==================================================
    // factories
    // ==================================================

    // ==================================================
    // constructors
    // ==================================================

    protected void setUp()
        throws Exception
    {
        super.setUp();
    }

    // ==================================================
    // public methods
    // ==================================================

    public void testFirst()
    {
        EasyLinearEquation ele = null;
        
        ele = EasyLinearEquation
        .create(new int[][]
                          {
                           { 33, 1,   2,   3},
                           { 81, 4,   5,   6},
                           { 52, 3,   2,   4}
                          });
        
        EasySolve solve = ele.solve();
        Assert.assertEquals("1 should be 6", 
                            BigInteger.valueOf(6), solve.getAnswer(1));
        Assert.assertEquals("2 should be 3", 
                            BigInteger.valueOf(3), solve.getAnswer(2));
        Assert.assertEquals("3 should be 7", 
                            BigInteger.valueOf(7), solve.getAnswer(3));
    }
    
    public void testJavadocExample() 
        throws IOException
    {
        if (false)
        {
            enableLogging();
        }
        
        EasyLinearEquation ele = null;
        
        ele = EasyLinearEquation
            .create(new int[][]
                   {
                    { 1491,  83,   32,   22},
                    { 329,    5,   13,   22},
                    { 122,    3,    2,   19}
                   });
        EasySolve solve = ele.solve();
        System.out.println("Output testJavadocExample test case.");
        System.out.println("answer(1)=" + solve.getAnswer(1));
        System.out.println("answer(2)=" + solve.getAnswer(2));
        System.out.println("answer(3)=" + solve.getAnswer(3));
        Assert.assertEquals("1 should be 11", 
                            BigInteger.valueOf(11), solve.getAnswer(1));
        Assert.assertEquals("2 should be 16", 
                            BigInteger.valueOf(16), solve.getAnswer(2));
        Assert.assertEquals("3 should be 3", 
                            BigInteger.valueOf(3), solve.getAnswer(3));
    }

    
    /**
     * Test case from data generated by SecretShareUT,
     * where there was no modulus.
     * 
     */
    public void testFromRealSecret() 
        throws IOException
    {
        if (false)
        {
            enableLogging();
        }
    
        EasyLinearEquation ele = null;

        ele = EasyLinearEquation
            .create(new int[][]
               {
                { 45662,1,1,1},
                { 45684,1,2,4},
                { 45720,1,3,9}
               });
        EasySolve solve = ele.solve();
        System.out.println("Output testReal test case.");
        System.out.println("answer(1)=" + solve.getAnswer(1));
        System.out.println("answer(2)=" + solve.getAnswer(2));
        System.out.println("answer(3)=" + solve.getAnswer(3));
        Assert.assertEquals("1 should be 45654", 
                            BigInteger.valueOf(45654), solve.getAnswer(1));
        Assert.assertEquals("2 should be 1", 
                            BigInteger.valueOf(1), solve.getAnswer(2));
        Assert.assertEquals("3 should be 7", 
                            BigInteger.valueOf(7), solve.getAnswer(3));
    }

    /**
     * Test case from data generated by SecretShareUT,
     * where there was a modulus, and
     * the coefficients came from random-long [i.e. were big].
     * 
     */
    public void testFromRealSecretLong() 
        throws IOException
    {
        if (false)
        {
            enableLogging();
        }
        
        EasyLinearEquation ele = null;

        //    EQ: PolyEqImpl = 45654 + x^1 * -6519408338692630574 + x^2 * -897291810407650440 
        //      :    modulus = 59561
        BigInteger modulus = BigInteger.valueOf(59561);
        ele = EasyLinearEquation
            .create(new int[][]
                              {
                    { 24689,1,1,1},
                    { 34394,1,2,4},
                    { 15208,1,3,9}
                              });
        ele = ele.createWithPrimeModulus(modulus);
        EasySolve solve = ele.solve();
        System.out.println("Output testRealLong test case.");
        System.out.println("answer(1)=" + solve.getAnswer(1));
        System.out.println("answer(2)=" + solve.getAnswer(2));
        System.out.println("answer(3)=" + solve.getAnswer(3));
        Assert.assertEquals("1 should be 45654",   // the secret
                            BigInteger.valueOf(45654), solve.getAnswer(1));
        // without the modulus, the answer would be -36300
        BigInteger two = BigInteger.valueOf(-36300).mod(modulus);
        Assert.assertEquals("2 should be " + two,
                            two, solve.getAnswer(2));
        Assert.assertEquals("3 should be 15335", 
                            BigInteger.valueOf(15335), solve.getAnswer(3));
    }
    
    
    public void testMod()
    {
        BigInteger a = BigInteger.valueOf(-28891);
        BigInteger b = BigInteger.valueOf(59561);
        System.out.println("a mod b = " + a.mod(b));  // 30670
        
        a = BigInteger.valueOf(-16689);
        b = BigInteger.valueOf(59561);
        System.out.println("a mod b = " + a.mod(b));  // 42872
    }
    

    public void testTrial()
    {
        BigInteger original = new BigInteger("-56707112967300178048284537440768814" +
                                             "86333610354980171978558655397960925457678336000");
        BigInteger divideby = new BigInteger("1908360529573854283038720000");
        BigInteger mod  = new BigInteger("14976407493557531125525728362448106789840013430353915016137");
        BigInteger expect = new BigInteger("4056711593111911745804512723290207574");
        
        System.out.println("GCD(original, divideby)=" + original.gcd(divideby));
        
        BigInteger c2   = new BigInteger("14976407493557531125503522315394253513660216320718149750143");
        BigInteger c4   = new BigInteger("14976407493557531125480553635111457689577554428988214795753");
        BigInteger c5   = new BigInteger("14976407493557531125525440847502616718686555765114601802327");
    }
    /**
     * Taken from wikipedia example at Shamir's_Secret_Sharing.
     * This is a n=6, k=3 example, with a secret of '1234' 
     * f(x) = 1234 + 166x + 94x^2
     * 
     * f(1) = 1494   f(2) = 1942   f(3) = 2578   f(4) = 3402   f(5) = 4414   f(6) = 5614
     * The wiki page selects secrets numbered: 2, 4 and 5
     *   and so does this test.
     * Note that the wiki does not use modulus in its example.
     */
    public void testFirstPolynomial()
    {
        EasyLinearEquation ele = null;
        
        BigInteger[] xarray = new BigInteger[] 
                                             {
                BigInteger.valueOf(2),
                BigInteger.valueOf(4),
                BigInteger.valueOf(5),
                                             };
        
        BigInteger[] fofxarray = new BigInteger[] 
                                             {
                BigInteger.valueOf(1942),
                BigInteger.valueOf(3402),
                BigInteger.valueOf(4414),
                                             };
        ele = EasyLinearEquation.createForPolynomial(xarray, fofxarray);
        
        EasySolve solve = ele.solve();
        Assert.assertEquals("1 should be 1234", 
                            BigInteger.valueOf(1234), solve.getAnswer(1));
        Assert.assertEquals("2 should be 166", 
                            BigInteger.valueOf(166), solve.getAnswer(2));
        Assert.assertEquals("3 should be 94", 
                            BigInteger.valueOf(94), solve.getAnswer(3));
    }
    

    // ANSWER:               5735816763073004597640754983969
    // Initial rows (modulus=5735816763073004597640754984037)
    //1850754426074252348194079284258,1,3,9,27,81,243
    //2703974504531035633031309163217,1,5,25,125,625,3125
    //4213349963476448859179505555376,1,4,16,64,256,1024
    //4158691342818266964856305942800,1,2,4,8,16,32
    //2979478334150024875183925248793,1,6,36,216,1296,7776
    //2037862997682741816600558705416,1,1,1,1,1,1    
    
    // ==================================================
    // non public methods
    // ==================================================

    public static void enableLogging()
    {
        // example code to turn on ALL logging 
        //
        // To see logging:
        // [a] set the handler's level
        // [b] add the handler
        // [c] set the logger's level
        
        Logger l = EasyLinearEquation.logger;
        Handler lh = new ConsoleHandler();
        lh.setFormatter(oneLineFormatter());
        // don't forget to do this:
        lh.setLevel(Level.ALL);
        
        // alternative: write log to file:
        //lh = new FileHandler("log.txt");
        
        // need this too:
        l.addHandler(lh);
        // and this:
        l.setLevel(Level.ALL);
        if (EasyLinearEquation.logger.isLoggable(Level.FINE))
        {
            System.out.println("ok");
            EasyLinearEquation.logger.fine("Hi there");
        }
        else
        {
            System.out.println("failed");
        }
    }

    public static Formatter oneLineFormatter()
    {
        return new SimpleFormatter()
        {
            public synchronized String format(LogRecord record) 
            {
                StringBuffer sb = new StringBuffer();
                String message = formatMessage(record);
                //sb.append(record.getLevel().getLocalizedName());
                //sb.append(": ");
                sb.append(message);
                sb.append("\n");
                if (record.getThrown() != null) {
                    try {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        record.getThrown().printStackTrace(pw);
                        pw.close();
                    sb.append(sw.toString());
                    } catch (Exception ex) {
                    }
                }
                return sb.toString();
                }
            
        };
    }
}
