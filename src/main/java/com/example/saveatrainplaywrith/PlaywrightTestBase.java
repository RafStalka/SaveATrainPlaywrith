package com.example.saveatrainplaywrith;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(PlaywrightTestBase.SaveArtifactsOnTestFailed.class)
public class PlaywrightTestBase {

    protected MainPage mainPage;
    protected AboutUsPage aboutUsPage;
    protected ContactUsPage contactUsPage;
    protected RailSearchApiProductPage railSearchApiProductPage;
    protected RailBookApiProductPage railBookApiProductPage;
    protected RailForwardApiPage railForwardApiPage;
    protected RailAgentDashboardProductPage railAgentDashboardProductPage;
    protected WhiteLabelProductPage whiteLabelProductPage;
    protected RailAffiliateProductPage railAffiliateProductPage;
    protected RailEnrichmentAPIProductPage railEnrichmentProductPage;
    protected EurailProductPage eurailProductPage;
    protected ManageBookingsHelpTabPage manageBookingsHelpTabPage;
    protected FAQHelpTabPage faqHelpTabPage;
    protected TermsAndConditionsPage termsAndConditionsPage;
    protected PrivacyPolicyPage privacyPolicyPage;
    protected ProductsPage productsPage;
    protected ResultsPage resultsPage;
    protected PassengersDetailsPage passengersDetailsPage;
    protected SummaryPage summaryPage;
    protected BlogPage blogPage;

    public static Playwright playwright;
    private static Browser browser;

    public Page page;
    private BrowserContext context;

    @BeforeAll
    static void initBrowser() {
        try {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(1000));
            System.out.println("Browser initialized.");
        } catch (Exception e) {
            System.err.println("Failed to initialize browser: " + e.getMessage());
            throw e;
        }
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
            System.out.println("Browser closed.");
        }
        if (playwright != null) {
            playwright.close();
            System.out.println("Playwright closed.");
        }
    }

    @BeforeEach
    void setUpEach() {
        try {
            Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions().setRecordVideoDir(Paths.get("target/playwright-videos"));
            customizeNewContextOptions(newContextOptions);
            this.context = browser.newContext(newContextOptions);
            this.page = context.newPage();
            if (context.tracing() != null) {
                context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
                System.out.println("Tracing started.");
            }
        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDownEach() {
        if (context != null) {
            context.close();
            System.out.println("Context closed after test.");
        }
    }

    protected void customizeNewContextOptions(Browser.NewContextOptions options) {
        // Customize as needed
    }

    protected static class SaveArtifactsOnTestFailed implements TestWatcher {

        private final String buildDirectory = System.getProperty("buildDirectory", "target");
        private final String directory = buildDirectory + "/playwright-artifacts";

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            PlaywrightTestBase testInstance = getTestInstance(context);

            if (testInstance.page != null) {
                String fileName = getFileName(context);
                saveScreenshot(testInstance, fileName);
                saveTrace(testInstance, fileName);
                saveVideo(testInstance, fileName);
            } else {
                System.err.println("Page is null, cannot save artifacts.");
            }

            cleanup(testInstance);
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            cleanup(getTestInstance(context));
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            cleanup(getTestInstance(context));
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            cleanup(getTestInstance(context));
        }

        private PlaywrightTestBase getTestInstance(ExtensionContext context) {
            return (PlaywrightTestBase) context.getRequiredTestInstance();
        }

        private String getFileName(ExtensionContext context) {
            return String.format("%s.%s-%s", context.getRequiredTestClass().getSimpleName(), context.getRequiredTestMethod().getName(),
                    new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(new Date()));
        }

        private void saveScreenshot(PlaywrightTestBase testInstance, String fileName) {
            try {
                byte[] screenshot = testInstance.page.screenshot();
                Files.write(Paths.get(directory, fileName + ".png"), screenshot);
                System.out.println("Screenshot saved.");
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }

        private void saveTrace(PlaywrightTestBase testInstance, String fileName) {
            try {
                if (testInstance.context.tracing() != null) {
                    testInstance.context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(directory, fileName + ".zip")));
                    System.out.println("Trace saved.");
                }
            } catch (Exception e) {
                System.err.println("Failed to save trace: " + e.getMessage());
            }
        }

        private void saveVideo(PlaywrightTestBase testInstance, String fileName) {
            try {
                testInstance.page.video().saveAs(Paths.get(directory, fileName + ".webm"));
                testInstance.page.video().delete();
                System.out.println("Video saved and deleted.");
            } catch (PlaywrightException e) {
                System.err.println("Failed to save or delete video: " + e.getMessage());
            }
        }

        private void cleanup(PlaywrightTestBase testInstance) {
            if (testInstance.context != null) {
                testInstance.context.close();
                System.out.println("Context closed during cleanup.");
            }
            if (testInstance.page != null && testInstance.page.video() != null) {
                testInstance.page.video().delete();
                System.out.println("Video deleted during cleanup.");
            }
        }
    }
}
