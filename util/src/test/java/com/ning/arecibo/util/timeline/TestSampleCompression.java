/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.arecibo.util.timeline;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSampleCompression {

    @Test(groups="fast")
    public void testBasicDoubleCompression() throws Exception {

        checkDoubleCodedResult(1.0, SampleOpcode.BYTE_FOR_DOUBLE);
        checkDoubleCodedResult(1.005, SampleOpcode.BYTE_FOR_DOUBLE);
        checkDoubleCodedResult(127.2, SampleOpcode.BYTE_FOR_DOUBLE);
        checkDoubleCodedResult(-128.2, SampleOpcode.BYTE_FOR_DOUBLE);

        checkDoubleCodedResult(65503.0, SampleOpcode.HALF_FLOAT_FOR_DOUBLE);
        checkDoubleCodedResult(-65503.0, SampleOpcode.HALF_FLOAT_FOR_DOUBLE);
        checkDoubleCodedResult(6.1e-5, SampleOpcode.HALF_FLOAT_FOR_DOUBLE);
        checkDoubleCodedResult(-6.1e-5, SampleOpcode.HALF_FLOAT_FOR_DOUBLE);

        checkDoubleCodedResult(200.0, SampleOpcode.SHORT_FOR_DOUBLE);
        checkDoubleCodedResult(32767.0, SampleOpcode.SHORT_FOR_DOUBLE);
        checkDoubleCodedResult(-200.0, SampleOpcode.SHORT_FOR_DOUBLE);
        checkDoubleCodedResult(-32768.0, SampleOpcode.SHORT_FOR_DOUBLE);

        checkDoubleCodedResult((double)Float.MAX_VALUE, SampleOpcode.FLOAT_FOR_DOUBLE);
        checkDoubleCodedResult((double)Float.MIN_VALUE, SampleOpcode.FLOAT_FOR_DOUBLE);

        checkDoubleCodedResult(((double)Float.MAX_VALUE) * 10.0, SampleOpcode.DOUBLE);
    }

    @SuppressWarnings("unchecked")
    private void checkDoubleCodedResult(final double value, final SampleOpcode expectedOpcode) {
        final ScalarSample codedSample = SampleCoder.compressSample(new ScalarSample(SampleOpcode.DOUBLE, value));
        Assert.assertEquals(codedSample.getOpcode(), expectedOpcode);
        final double error = Math.abs((value - SampleCoder.getDoubleValue(codedSample)) / value);
        Assert.assertTrue(error <= SampleCoder.MAX_FRACTION_ERROR);
    }
}