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

package com.ning.arecibo.util.service;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class ServiceSelector implements Selector, Serializable
{
    static final long serialVersionUID = 6476522335558352705L;

    private final String serviceName;

    public ServiceSelector(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public boolean match(ServiceDescriptor sd)
    {
        return StringUtils.equals(sd.getName(), serviceName);
    }
}
