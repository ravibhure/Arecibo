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

package com.ning.arecibo.alert.confdata;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class NotifGroup
{
    private final String groupName;
    private final boolean enabled;
    private final int id;

    @JsonCreator
    public NotifGroup(@JsonProperty("label") final String groupName,
                      @JsonProperty("enabled") final String enabled,
                      @JsonProperty("id") final int id)
    {
        this.groupName = groupName;
        this.enabled = !(enabled != null && enabled.equals("0"));
        this.id = id;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public int getId()
    {
        return id;
    }
}