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

package com.ning.arecibo.dashboard.guice;

import org.skife.config.Config;
import org.skife.config.Default;
import org.skife.config.DefaultNull;
import org.skife.config.Description;
import org.skife.config.TimeSpan;

public interface DashboardConfig
{
    @Config("arecibo.dashboard.serviceLocatorKlass")
    @Description("ServiceLocator implementation for announcements and discovery")
    @Default("com.ning.arecibo.util.service.DummyServiceLocator")
    String getServiceLocatorClass();

    @Config("arecibo.dashboard.serviceName")
    @Description("Arecibo service name, used for announcements and discovery")
    @Default("AreciboDashboardService")
    String getServiceName();

    @Config("arecibo.dashboard.extraGuiceModules")
    @Description("Extra Guice modules to be installed")
    @Default("")
    String getExtraGuiceModules();

    @Config("arecibo.dashboard.galaxy.updateInterval")
    @Description("Refresh interval for the list of galaxy cores")
    @Default("5m")
    TimeSpan getGalaxyUpdateInterval();

    @Config("arecibo.dashboard.alertHostOverride")
    @DefaultNull
    String getAlertHostOverride();

    @Config("arecibo.dashboard.alertManagerEnabled")
    @Default("false")
    boolean isAlertManagerEnabled();

    @Config("arecibo.dashboard.customGroupsFile")
    @Description("Path to the custom super groups file (optional)")
    @DefaultNull
    String getCustomGroupsFile();

    @Config("arecibo.dashboard.legendGroupsFile")
    @Description("Path to the legend configuration file (optional)")
    @DefaultNull
    String getLegendGroupsFile();

    @Config("arecibo.dashboard.legendFixturesFile")
    @Description("Path to the legend fixtures file (optional)")
    @DefaultNull
    String getLegendFixturesFile();

    @Config("arecibo.dashboard.sampleKindsUpdaterDelay")
    @Description("Delay between sample kinds updates to the collector")
    @Default("30s")
    TimeSpan getSampleKindsUpdaterDelay();
}
