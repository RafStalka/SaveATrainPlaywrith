package com.example.saveatrainplaywrith;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.MainPage;
import pages.PassengersDetailsPage;
import pages.ResultsPage;
import pages.SummaryPage;

// Subclasses will inherit PER_CLASS behavior.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaywrightTestBaseParallel {
    protected MainPage mainPage;
    protected ResultsPage resultsPage;
    protected PassengersDetailsPage passengersDetailsPage;
    protected SummaryPage summaryPage;
    protected String homeSAT = "https://sat-client-staging.saveatrain.com/en";
    protected String homePROD = "https://booking.saveatrain.com/en";
    // Shared between all tests in the class.
    Playwright playwright;
    Browser browser;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    void closeBrowser() {
        playwright.close();
    }

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}
