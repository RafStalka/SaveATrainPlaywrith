package com.example.saveatrainplaywrith;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.BeforeEachCallback;
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
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(1000));
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    protected void customizeNewContextOptions(Browser.NewContextOptions options) {
    }

    protected static class SaveArtifactsOnTestFailed implements TestWatcher, BeforeEachCallback {

        private final String buildDirectory = System.getProperty("buildDirectory", "target");
        private final String directory = buildDirectory + "/playwright-artifacts";

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PlaywrightTestBase testInstance = getTestInstance(context);

            Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions().setRecordVideoDir(Paths.get(directory));
            testInstance.customizeNewContextOptions(newContextOptions);
            testInstance.context = PlaywrightTestBase.browser.newContext(newContextOptions);
            testInstance.context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));

            testInstance.page = testInstance.context.newPage();
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            PlaywrightTestBase testInstance = getTestInstance(context);

            String fileName = getFileName(context);

            saveScreenshot(testInstance, fileName);
            saveTrace(testInstance, fileName);

            testInstance.context.close();

            saveVideo(testInstance, fileName);
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            PlaywrightTestBase testInstance = getTestInstance(context);
            testInstance.context.close();
            testInstance.page.video().delete();
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            PlaywrightTestBase testInstance = getTestInstance(context);
            testInstance.context.close();
            testInstance.page.video().delete();
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            PlaywrightTestBase testInstance = getTestInstance(context);
            testInstance.context.close();
            testInstance.page.video().delete();
        }

        private PlaywrightTestBase getTestInstance(ExtensionContext context) {
            return (PlaywrightTestBase) context.getRequiredTestInstance();
        }

        private String getFileName(ExtensionContext context) {
            return String.format("%s.%s-%s", context.getRequiredTestClass().getSimpleName(), context.getRequiredTestMethod().getName(),
                    new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(new Date()));
        }

        private void saveScreenshot(PlaywrightTestBase testInstance, String fileName) {
            byte[] screenshot = testInstance.page.screenshot();
            try {
                Files.write(Paths.get(directory, fileName + ".png"), screenshot);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void saveTrace(PlaywrightTestBase testInstance, String fileName) {
            testInstance.context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(directory, fileName + ".zip")));
        }

        private void saveVideo(PlaywrightTestBase testInstance, String fileName) {
            testInstance.page.video().saveAs(Paths.get(directory, fileName + ".webm"));
            testInstance.page.video().delete();
        }
    }
}
