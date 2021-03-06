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

package com.ning.arecibo.alertmanager.resources;

import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;
import com.ning.arecibo.alert.client.AlertClient;
import com.ning.arecibo.alert.confdata.NotifConfig;
import com.ning.arecibo.alert.confdata.Person;
import com.ning.arecibo.alertmanager.models.PeopleAndAliasesModel;
import com.ning.arecibo.util.Logger;
import com.ning.jersey.metrics.TimedResource;
import com.sun.jersey.api.view.Viewable;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Singleton
@Path("/ui/people")
public class PeopleAndAliasesResource
{
    private static final Logger log = Logger.getLogger(PeopleAndAliasesResource.class);

    private final AlertClient client;

    @Inject
    public PeopleAndAliasesResource(final AlertClient client)
    {
        this.client = client;
    }

    @GET
    @TimedResource
    public Viewable getPeople()
    {
        final Iterable<Person> peopleAndGroups = client.findAllPeopleAndGroups();
        final Map<String, Iterable<NotifConfig>> notificationsForPersonOrGroup = new HashMap<String, Iterable<NotifConfig>>();

        // Retrieve notifications for this person
        for (final Person person : peopleAndGroups) {
            final String nickName = person.getNickName();
            final Integer personId = person.getId();

            if (personId != null) {
                final Iterator<NotifConfig> iterator = client.findNotificationsForPersonOrGroupId(personId).iterator();
                notificationsForPersonOrGroup.put(nickName, ImmutableList.copyOf(iterator));
            }
        }

        return new Viewable("/jsp/people.jsp", new PeopleAndAliasesModel(peopleAndGroups, notificationsForPersonOrGroup));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @TimedResource
    public Response createPerson(@FormParam("is_group") final String isGroup,
                                 @FormParam("nick_name") final String nickName,
                                 @FormParam("first_name") final String firstName,
                                 @FormParam("last_name") final String lastName,
                                 @FormParam("email") final String email,
                                 @FormParam("notification_type") final String notificationType)
    {
        final int personId;
        if (isGroup == null) {
            personId = client.createPerson(firstName, lastName, nickName);
        }
        else {
            personId = client.createGroup(nickName);
        }

        if (notificationType.equals("REGULAR_EMAIL")) {
            final int emailNotificationId = client.createEmailNotificationForPersonOrGroup(personId, email);
            log.info("Created person %s (id=%d) with email notification %s (id=%d)", nickName, personId, email, emailNotificationId);
        }
        else {
            final int smsNotificationId = client.createSmsNotificationForPersonOrGroup(personId, email);
            log.info("Created person %s (id=%d) with sms notification %s (id=%d)", nickName, personId, email, smsNotificationId);
        }

        return Response.seeOther(URI.create("/ui/people")).build();
    }
}
