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

package com.ning.arecibo.aggregator.guice;

import java.util.UUID;
import org.skife.config.ConfigurationObjectFactory;
import org.weakref.jmx.guice.ExportBuilder;
import org.weakref.jmx.guice.MBeanModule;
import com.google.inject.AbstractModule;
import com.ning.arecibo.aggregator.dictionary.EventDictionary;
import com.ning.arecibo.aggregator.eventservice.EventServiceManager;
import com.ning.arecibo.aggregator.impl.AggregatorRegistry;
import com.ning.arecibo.aggregator.impl.AggregatorServer;
import com.ning.arecibo.aggregator.impl.AsynchronousUpdateWorker;
import com.ning.arecibo.aggregator.impl.ServiceDescriptorResource;
import com.ning.arecibo.aggregator.plugin.AreciboMonitoringPlugin;
import com.ning.arecibo.aggregator.rest.AggregatorRenderer;
import com.ning.arecibo.aggregator.rest.AggregatorsRenderer;
import com.ning.arecibo.aggregator.rest.EPStatementsRenderer;
import com.ning.arecibo.aggregator.rest.EsperStatementRenderer;
import com.ning.arecibo.aggregator.rest.EventAggregatorEndPoint;
import com.ning.arecibo.aggregator.rest.EventDefinitionRenderer;
import com.ning.arecibo.aggregator.rest.EventDictionaryEndPoint;
import com.ning.arecibo.aggregator.rest.EventDictionaryRenderer;
import com.ning.arecibo.aggregator.rest.EventStreamEndPoint;
import com.ning.arecibo.aggregator.rest.HashEndPoint;
import com.ning.arecibo.aggregator.rest.RawEventRenderer;
import com.ning.arecibo.client.RemoteAggregatorService;
import com.ning.arecibo.event.publisher.HdfsEventPublisher;
import com.ning.arecibo.event.publisher.RandomEventServiceChooser;
import com.ning.arecibo.util.Logger;

public class AggregatorModule extends AbstractModule
{
    final static Logger log = Logger.getLogger(AggregatorModule.class);

    @Override
	public void configure()
	{
        AggregatorConfig config = new ConfigurationObjectFactory(System.getProperties()).build(AggregatorConfig.class);

        bind(AggregatorConfig.class).toInstance(config);
		bind(UUID.class).annotatedWith(SelfUUID.class).toInstance(UUID.randomUUID());

	    bind(EventDictionary.class).asEagerSingleton();
	    bind(AggregatorRegistry.class).asEagerSingleton();
		bind(AsynchronousUpdateWorker.class).asEagerSingleton();
		bind(AggregatorServer.class).asEagerSingleton();
        bind(EventServiceManager.class).asEagerSingleton();

		bind(AreciboMonitoringPlugin.class).asEagerSingleton();
        bind(String[].class).annotatedWith(AggregatorNamespaces.class).toInstance(new String[]{AreciboMonitoringPlugin.NS, RemoteAggregatorService.DEFAULT_NS});

        bind(ServiceDescriptorResource.class).asEagerSingleton();
        bind(EventAggregatorEndPoint.class).asEagerSingleton();
        bind(EventDictionaryEndPoint.class).asEagerSingleton();
        bind(EventStreamEndPoint.class).asEagerSingleton();
        bind(HashEndPoint.class).asEagerSingleton();

        bind(EsperStatementRenderer.class).asEagerSingleton();
        bind(EPStatementsRenderer.class).asEagerSingleton();
        bind(EventDictionaryRenderer.class).asEagerSingleton();
        bind(EventDefinitionRenderer.class).asEagerSingleton();
        bind(AggregatorsRenderer.class).asEagerSingleton();
        bind(AggregatorRenderer.class).asEagerSingleton();
        bind(RawEventRenderer.class).asEagerSingleton();

        ExportBuilder builder = MBeanModule.newExporter(binder());

        builder.export(RandomEventServiceChooser.class).as("arecibo:type=HdfsEventServiceChooser");
        builder.export(HdfsEventPublisher.class).as("arecibo:name=HdfsEventPublisher");

        builder.export(EventDictionary.class).as("arecibo.aggregator:name=EventDictionary");
        builder.export(AggregatorRegistry.class).as("arecibo.aggregator:name=AggregatorRegistry");
        builder.export(AsynchronousUpdateWorker.class).as("arecibo.aggregator:name=AsynchronousUpdateWorker");
        builder.export(AggregatorServer.class).as("arecibo.aggregator:name=AggregatorServer");
        builder.export(EventServiceManager.class).as("arecibo.aggregator:name=EventServiceManager");
    }
}
