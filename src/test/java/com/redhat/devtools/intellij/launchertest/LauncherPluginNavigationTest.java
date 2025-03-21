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
import com.redhat.devtools.intellij.commonuitest.utils.constants.XPathDefinitions;
import com.intellij.remoterobot.fixtures.JTextFieldFixture;

import com.redhat.devtools.intellij.commonuitest.UITestRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.intellij.remoterobot.fixtures.JTreeFixture;

import java.time.Duration;
import java.util.logging.Logger;

/**
 * Test case for navigating to plugins marketplace
 */
public class LauncherPluginNavigationTest {
  private static final Logger LOGGER = Logger.getLogger(LauncherPluginNavigationTest.class.getName());
  private static final IntelliJVersion communityIdeaVersion = IntelliJVersion
      .getFromStringVersion(System.getProperty("communityIdeaVersion"));
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

    // welcomeFrame.createNewProject();

    // MainIdeWindow mainIdeWindow = remoteRobot.find(MainIdeWindow.class,
    // Duration.ofSeconds(30));
    // LOGGER.info("Main IDE window found");

    // div[@text='More via plugins…']

    JTreeFixture jTreeFixture = remoteRobot.find(JTreeFixture.class, byXpath(XPathDefinitions.TREE));
    jTreeFixture.findText("Plugins").click();
    LOGGER.info("Open tabs plugins");

    // Click on the Plugins button in the navigation bar
    JTextFieldFixture marketplaceInput = remoteRobot.find(
        JTextFieldFixture.class,
        byXpath("//div[@class='TextFieldWithProcessing']"),
        Duration.ofSeconds(10));
    marketplaceInput.setText("Gemini Code Assist");
    LOGGER.info("Clicked search plugin text field");

    CommonContainerFixture geminiPlugin = remoteRobot.find(
        CommonContainerFixture.class,
        byXpath(
            "//div[@accessiblename='Gemini Code Assist' and @class='ListPluginComponent']//div[@class='InstallButton']"),
        Duration.ofSeconds(10));
    geminiPlugin.click();

    CommonContainerFixture AcceptButton = remoteRobot.find(
        CommonContainerFixture.class,
        byXpath(
            "//div[@text='Accept']"),
        Duration.ofSeconds(10));
    AcceptButton.click();

    LOGGER.info("Clicked install button Gemini Code Assist");

    CommonContainerFixture restartButton = remoteRobot.find(
    CommonContainerFixture.class,
    byXpath("//div[@accessiblename='Gemini Code Assist' and @class='ListPluginComponent']//div[@class='RestartButton']"),
    Duration.ofSeconds(10));
    restartButton.click();
    LOGGER.info("Clicked restart button Gemini Code Assist");

    CommonContainerFixture restartButton1 = remoteRobot.find(
    CommonContainerFixture.class,
    byXpath("//div[@text='Restart']"),
    Duration.ofSeconds(10));
    restartButton1.click();

    // CommonContainerFixture pluginsPanel = remoteRobot.find(
    // CommonContainerFixture.class,
    // byXpath("//div[@class='JBSplitter' and .//div[@accessiblename='Plugins']]"),
    // Duration.ofSeconds(10));

    // try {
    // pluginsPanel.findText("Marketplace").click();
    // LOGGER.info("Clicked on Marketplace tab");
    // } catch (Exception e) {
    // LOGGER.info("Marketplace tab might already be selected or has a different
    // name");
    // }

    // CommonContainerFixture settingsDialog = remoteRobot.find(
    // CommonContainerFixture.class,
    // byXpath("//div[@class='MyDialog' and @title='Settings']"),
    // Duration.ofSeconds(10));

    // settingsDialog.button("Cancel").click();
    // LOGGER.info("Successfully navigated to plugins marketplace");
  }
}