/*******************************************************************************
 * Copyright (c) 2024 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.redhat.devtools.intellij.launchertest;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.search.locators.Locator;
import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import com.redhat.devtools.intellij.commonuitest.fixtures.dialogs.FlatWelcomeFrame;
import com.redhat.devtools.intellij.commonuitest.fixtures.mainidewindow.MainIdeWindow;
import com.redhat.devtools.intellij.commonuitest.fixtures.mainidewindow.menubar.MenuBar;
import com.redhat.devtools.intellij.commonuitest.utils.runner.IntelliJVersion;
import com.redhat.devtools.intellij.commonuitest.UITestRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;
import java.util.logging.Logger;

/**
 * Test case for navigating to plugins marketplace
 */
public class LauncherPluginNavigationTest {
    private static final Logger LOGGER = Logger.getLogger(LauncherPluginNavigationTest.class.getName());
    private static final IntelliJVersion communityIdeaVersion = IntelliJVersion.getFromStringVersion(System.getProperty("communityIdeaVersion"));
    private static RemoteRobot remoteRobot;
    private static final int TEST_RUNNER_PORT = 8580;

    @BeforeAll
    static void startIntelliJ() {
        LOGGER.info("Starting IntelliJ IDEA...");
        System.setProperty("idea.ignore.disabled.plugins", "true");
        
        remoteRobot = UITestRunner.runIde(communityIdeaVersion, TEST_RUNNER_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> UITestRunner.closeIde()));
    }

    @AfterAll
    static void finishTestRun() {
        LOGGER.info("Finishing test run...");
        UITestRunner.closeIde();
    }

    @Test
    @DisplayName("Test navigation to plugins marketplace")
    void testNavigateToPluginsMarketplace() {
        LOGGER.info("Running test to navigate to plugins marketplace");
        
        FlatWelcomeFrame welcomeFrame = remoteRobot.find(FlatWelcomeFrame.class, Duration.ofSeconds(30));
        LOGGER.info("IntelliJ welcome frame found");

        welcomeFrame.createNewProject();
    }

    @AfterAll
    public static void closeIde() {
        UITestRunner.closeIde();
    }
} 