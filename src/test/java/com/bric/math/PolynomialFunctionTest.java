/*
 * @(#)PolynomialFunctionTest.java
 *
 * $Date: 2014-12-04 12:16:50 +0100 (Do, 04 Dez 2014) $
 *
 * Copyright (c) 2014 by Jeremy Wood.
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Jeremy Wood. For details see accompanying license terms.
 * 
 * This software is probably, but not necessarily, discussed here:
 * https://javagraphics.java.net/
 * 
 * That site should also contain the most recent official version
 * of this software.  (See the SVN repository for more details.)
 */
package com.bric.math;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;

import com.bric.math.function.PolynomialFunction;

public class PolynomialFunctionTest extends TestCase {

	@Test
	public void testDerivative() {
		PolynomialFunction f;
		f = new PolynomialFunction(new double[] {
				5, -3, 7, 11
		});
		
		//technically we shouldn't compare against a string here,
		//but it made it easier to look at/think about...
		
		System.out.println(f.toString());
		System.out.println(f.getDerivative().toString());
		
		assertEquals(f.toString(), "y = 5.0*(x^3)+-3.0*(x^2)+7.0*x+11.0");
		assertEquals(f.getDerivative().toString(), "y = 15.0*(x^2)+-6.0*x+7.0");
	}
	
	@Test
	public void testSolve() {
		//first: test a super simple quadratic:
		
		//y = (x-3)(x+4)
		//y = (x+(-3+4)x-12)
		PolynomialFunction f = new PolynomialFunction(new double[] {1, 1, -12});
		double[] roots = f.solve();
		Arrays.sort(roots);
		assertEquals(roots[0], -4, .0001);
		assertEquals(roots[1], 3, .0001);
		
		//if that passed, throw thousands of tests at it:
		Random r = new Random();
		for(int trial = 0; trial<10000; trial++) {
			PolynomialFunction function = null;
			double y = 100*(r.nextDouble()-.5);
			double[] solutions = null;
			try {
				double[] coeffs = new double[ 2+r.nextInt(6) ];
				for(int a = 0; a<coeffs.length; a++) {
					coeffs[a] = 20*(r.nextDouble()-.5);
				}
				
				function = new PolynomialFunction(coeffs);
				
				solutions = function.evaluateInverse(y);
				for(int a = 0; a<solutions.length; a++) {
					assertEquals("trial #"+trial+", y="+y+", "+function, y, function.evaluate(solutions[a]), .1 );
				}
			} catch(RuntimeException e) {
				System.err.println("trial #"+trial+", y="+y+", "+function);
				throw e;
			} catch(Error e) {
				System.err.println("trial #"+trial+", y="+y+", "+function);
				throw e;
			}
		}
	}
}
