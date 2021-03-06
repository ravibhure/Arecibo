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

package com.ning.arecibo.alert.endpoint;

import com.google.inject.Inject;
import com.ning.arecibo.alert.confdata.dao.ConfDataDAO;
import com.ning.arecibo.alert.confdata.objects.ConfDataNotifMapping;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Mapping between Notification Groups and Notification Configs.
 * <p/>
 * From a database perspective, notif_mapping is a join table between notif_group and notif_config
 */
@Path("/xn/rest/1.0/NotifMapping")
public class NotifMappingEndPoint extends ConfDataEndPoint<ConfDataNotifMapping>
{
    @Inject
    public NotifMappingEndPoint(final ConfDataDAO dao)
    {
        super(dao, ConfDataNotifMapping.TYPE_NAME, ConfDataNotifMapping.class);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getById(@PathParam("id") final Long id)
    {
        return findConfDataObjectById(id);
    }

    @GET
    @Path("/NotifGroup/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> getAllByGroupId(@PathParam("id") final Long notifGroupId)
    {
        return findConfDataObjectById("notif_group_id", notifGroupId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final ConfDataNotifMapping notifMapping)
    {
        return createConfDataObject(notifMapping);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") final Long id)
    {
        return deleteConfDataObjectById(id);
    }
}
