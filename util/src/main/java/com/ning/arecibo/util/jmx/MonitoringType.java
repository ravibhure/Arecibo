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

package com.ning.arecibo.util.jmx;

public enum MonitoringType
{
    /**
     * Interpret value directly (default)
     */
    VALUE("v"),

    /**
     * Interpret value as a rate of change per second
     * Tracked as delta between current and last sample / elapsed seconds
     */
    RATE("r"),

    /**
     * Denote that this metric should be assumed to be monotonically increasing
     * Any negative discontinuity should be be ignored
     * Applicable only in conjunction with the RATE type
     */
    COUNTER("c"),

    /**
     *  Denote that this metric can be used as a default healthcheck
     * Will result in monitoring and alerting based on this value
     * This metric should return one of 2 values: 1 for "OK" and 0 for "FAILURE"
     */
    HEALTHCHECK("h"),

    /**
     * Denote that this metric changes rarely, if ever
     * Useful for downstream optimization, compression
     */
    QUIESCENT("q")
    ;

    String code ;

    MonitoringType(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public String toString()
    {
        return getCode();
    }
}
