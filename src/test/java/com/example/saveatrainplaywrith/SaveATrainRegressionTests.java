package com.example.saveatrainplaywrith;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import constans.AppConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.*;

public class SaveATrainRegressionTests extends PlaywrightTestBase {
    @BeforeEach
    public void setUp() {
        // Assuming 'page' is initialized in your base class
        mainPage = new MainPage(page);
        passengersDetailsPage = new PassengersDetailsPage(page);
        resultsPage = new ResultsPage(page);
        summaryPage = new SummaryPage(page);
        aboutUsPage = new AboutUsPage(page);
        contactUsPage = new ContactUsPage(page);
        railSearchApiProductPage = new RailSearchApiProductPage(page);
        railBookApiProductPage = new RailBookApiProductPage(page);
        railForwardApiPage = new RailForwardApiPage(page);
        railAgentDashboardProductPage = new RailAgentDashboardProductPage(page);
    }

    @Test
    public void mainPageTitleTest() {
        String actualTitle = mainPage.getMainPageTitle();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", actualTitle);
    }

    @Test
    public void mainPageURLTest() {
        String actualURL = mainPage.getMainPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE, actualURL);
    }

    @Test
    public void aboutUs_TabChecking() {
        // User should see rail search api product introduction page
        String titleAboutUsPage = aboutUsPage.getAboutUsPageTitle();
        Assertions.assertEquals("About Us | Save A Train", titleAboutUsPage);
    }

    @Test
    public void contactUs_TabChecking() {
        // User should see contact us page
        String contactUsTitle = contactUsPage.getContactUsPageTitle();
        Assertions.assertEquals("Contact Us | Save A Train", contactUsTitle);
    }

    @Test
    public void railSearchApi_ProductChecking() {
        // User should see rail search api product introduction page
        String railSearchApiTitle = railSearchApiProductPage.getRailSearchApiPageTitle();
        Assertions.assertEquals("Rail Search Api | Save A Train", railSearchApiTitle);
    }

    @Test
    public void railBookApi_ProductChecking() {
        // User should see rail search api product introduction page
        String railBookApiTitle = railBookApiProductPage.getRailBookApiPageTitle();
        Assertions.assertEquals("Rail Book Api | Save A Train", railBookApiTitle);
    }

    @Test
    public void railForwardApi_ProductChecking() {
        // User should see rail search api product introduction page
        String railForwardApiTitle = railForwardApiPage.getRailForwardApiPageTitle();
        Assertions.assertEquals("Rails Forward Api | Save A Train", railForwardApiTitle);
    }

    @Test
    public void railAgentDashboard_ProductChecking() {
        // User should see rail search api product introduction page
        String railAgentDashboardProductTitle = railAgentDashboardProductPage.getRailAgentDashboardProductPageTitle();
        Assertions.assertEquals("Rail Agent Dashboard | Save A Train", railAgentDashboardProductTitle);
    }

    @Test
    public void whiteLabel_ProductChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=white-label");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("White Label | Save A Train", title);
    }

    @ParameterizedTest
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station",
            "Rome Termini, Florence All Stations",
            "Malmo Central Station, Stockholm Central Station",
            "Madrid, Leon"})
    public void checking_CorrectnessOfSearchData(String origin, String destination) {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", origin);

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", destination);

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Click on the search button
        page.click(".search-btn");

        // Click on the departure date input
        page.click(".departure-date > .form-control");

        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(5) > .owl-dt-day-4 > .owl-dt-calendar-cell-content");

        // Click on the find my tickets button
        page.click("button[name='button']");

        String searchPage_Origin = page.locator(".direction .direction-from").textContent().trim();
        Assertions.assertEquals(origin, searchPage_Origin);

        String searchPage_Destination = page.locator(".direction .direction-to").textContent().trim();
        Assertions.assertEquals(destination, searchPage_Destination);

        // zrobic asercje na date
        // zrobic asercje na typ pasazerow
    }

    @Test
    public void _changingDestinationWithOrigin_FunctionalityButton() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", "Berlin Central Station");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", "Hamburg Central Station");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Find and interact with the elements
        String originPrimary = page.locator(".input-control-container > .origin").inputValue();
        String destinPrimary = page.locator(".destination:nth-child(1)").inputValue();

        Assertions.assertEquals("Berlin Central Station", originPrimary);
        Assertions.assertEquals("Hamburg Central Station", destinPrimary);

        page.locator("//i[contains(@class, 'md-36')]").click();

        // Find and interact with the elements
        String originSecondary = page.locator(".input-control-container > .origin").inputValue();
        String destinSecondary = page.locator(".destination:nth-child(1)").inputValue();

        Assertions.assertEquals("Hamburg Central Station", originSecondary);
        Assertions.assertEquals("Berlin Central Station", destinSecondary);
    }

    @Test
    public void checkingProductsTabListSize() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // Find the parent element containing the list of <li> elements
        Locator parentElement = page.locator("#products-dropdown");

        // Find all <li> elements within the parent element and convert to an array
        ElementHandle[] liElements = parentElement.locator("li").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = liElements.length;
        System.out.println("Number of products tab elements: " + listSize);

        Assertions.assertEquals(listSize, 7);
    }

    @Test
    public void railAffiliate_ProductChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-affiliate");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Affiliate | Save A Train", title);
    }

    @Test
    public void eurailPass_ProductChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=eurail-pass");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Eurail Pass | Save A Train", title);
    }

    @Test
    public void redirection_BlogTab() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

    }

    @Test
    public void checkingHelpTabListSize() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // Find the parent element containing the list of <li> elements
        Locator parentElement = page.locator("#help-dropdown");

        // Find all <li> elements within the parent element and convert to an array
        ElementHandle[] liElements = parentElement.locator("li").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = liElements.length;
        System.out.println("Number of help tab elements: " + listSize);

        Assertions.assertEquals(listSize, 2);
    }

    @Test
    public void manageBookings_HelpTabChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // User clicks on help tab
        page.click("id=help-dropdown");
        // User choose manage bookings from dropdown
        page.click("id=manage-booking");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Manage Bookings | Save A Train", title);
    }

    @Test
    public void FAQ_HelpTabChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // User clicks on help tab
        page.click("id=help-dropdown");
        // User choose manage bookings from dropdown
        page.click("id=faq");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("FAQ | Save A Train", title);
    }

    @Test
    public void readMore_ButtonChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

        // Click on Read more button
        page.click(".services-body-desktop > .more-button");
        // Checking title
        String readMoreTitle = page.title();
        Assertions.assertEquals("About Us | Save A Train", readMoreTitle);
    }

    @Test
    public void privacyPolicy_ButtonChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // Click on Privacy Policy button
        page.click("a:nth-of-type(1) > p");
        // Checking title
        String privacyPolicyTitle = page.title();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    public void termsAndConditions_ButtonChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // Click on Terms and Conditions button
        page.click("a:nth-of-type(2) > p");
        // Checking title
        String privacyPolicyTitle = page.title();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    public void currency_ListSizeChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.locator("#language-currency a").click();
        // Find the parent element containing the list of currency elements
        Locator parentElement = page.locator("id=currency");

        // Find all currency elements within the parent element and convert to an array
        ElementHandle[] currencyElements = parentElement.locator("option").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = currencyElements.length;
        System.out.println("Number of help tab elements: " + listSize);

        Assertions.assertEquals(7, listSize, "Number of available currencies is wrong.");

    }

    @Test
    public void language_ListSizeChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.locator("#language-currency a").click();
        // Find the parent element containing the list of language elements
        Locator parentElement = page.locator("id=language");

        // Find all currency elements within the parent element and convert to an array
        ElementHandle[] languageElements = parentElement.locator("option").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = languageElements.length;
        System.out.println("Number of help tab elements: " + listSize);

        Assertions.assertEquals(10, listSize, "Number of available languages is wrong.");

    }
}
