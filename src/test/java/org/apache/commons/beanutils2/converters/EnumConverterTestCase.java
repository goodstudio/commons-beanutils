/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.beanutils2.converters;

import org.apache.commons.beanutils2.ConversionException;
import org.apache.commons.beanutils2.Converter;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test Case for the EnumConverter class.
 *
 */
public class EnumConverterTestCase extends TestCase {

    public enum PizzaStatus {
        ORDERED, READY, DELIVERED;
    }

    public static TestSuite suite() {
        return new TestSuite(EnumConverterTestCase.class);
    }

    private Converter<Enum<PizzaStatus>> converter;

    public EnumConverterTestCase(final String name) {
        super(name);
    }

    protected Class<?> getExpectedType() {
        return Enum.class;
    }

    protected Converter<Enum<PizzaStatus>> makeConverter() {
        return new EnumConverter<>();
    }

    @Override
    public void setUp() throws Exception {
        converter = makeConverter();
    }

    @Override
    public void tearDown() throws Exception {
        converter = null;
    }

    public void testSimpleConversion() throws Exception {
        final String[] message = { "from String", "from String", "from String", "from String", "from String", "from String", "from String", "from String", };

        final Object[] input = { "DELIVERED", "ORDERED", "READY" };

        final PizzaStatus[] expected = { PizzaStatus.DELIVERED, PizzaStatus.ORDERED, PizzaStatus.READY };

        for (int i = 0; i < expected.length; i++) {
            assertEquals(message[i] + " to Enum", expected[i], converter.convert(PizzaStatus.class, input[i]));
        }

        for (int i = 0; i < expected.length; i++) {
            assertEquals(input[i] + " to String", input[i], converter.convert(String.class, expected[i]));
        }
    }

    /**
     * Tests a conversion to an unsupported type.
     */
    public void testUnsupportedType() {
        try {
            converter.convert(Integer.class, "http://www.apache.org");
            fail("Unsupported type could be converted!");
        } catch (final ConversionException cex) {
            // expected result
        }
    }
}
