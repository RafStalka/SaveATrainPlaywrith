package com.example.saveatrainplaywrith;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SaveATrainRegressionTests extends PlaywrightTestBase {

    @ParameterizedTest
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station",
            "Rome Termini, Florence All Stations",
            "Malmo Central Station, Stockholm Central Station",
            "Madrid, Leon"})
    public void checking_CorrectnessOfSearchData(String origin, String destination) {
        page.navigate(homeSAT);

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
        page.navigate(homeSAT);

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
        page.navigate(homeSAT);

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
    public void railSearchApi_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail search api
        page.click("id=rail-search-api");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Search Api | Save A Train", title);
    }

    @Test
    public void railBookApi_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rails-book-api");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Book Api | Save A Train", title);
    }

    @Test
    public void railForwardApi_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-forward-api");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rails Forward Api | Save A Train", title);
    }

    @Test
    public void railAgentDashboard_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-agent-dashboard");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Agent Dashboard | Save A Train", title);
    }

    @Test
    public void whiteLabel_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=white-label");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("White Label | Save A Train", title);
    }

    @Test
    public void railAffiliate_ProductChecking() {
        page.navigate(homeSAT);

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
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=eurail-pass");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Eurail Pass | Save A Train", title);
    }

    @Test
    public void aboutUs_TabChecking() {
        page.navigate(homeSAT);

        // User clicks on about us tab
        page.click("id=about-us");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("About Us | Save A Train", title);
    }

    @Test
    public void redirection_BlogTab() {
        page.navigate(homeSAT);

    }

    @Test
    public void contactUs_TabChecking() {
        page.navigate(homeSAT);

        // User clicks on about us tab
        page.click("id=contact-us");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Contact Us | Save A Train", title);
    }

    @Test
    public void checkingHelpTabListSize() {
        page.navigate(homeSAT);

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
        page.navigate(homeSAT);

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
        page.navigate(homeSAT);

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
        page.navigate(homeSAT);

        // Click on Read more button
        page.click(".services-body-desktop > .more-button");
        // Checking title
        String readMoreTitle = page.title();
        Assertions.assertEquals("About Us | Save A Train", readMoreTitle);
    }

    @Test
    public void privacyPolicy_ButtonChecking() {
        page.navigate(homeSAT);
        // Click on Privacy Policy button
        page.click("a:nth-of-type(1) > p");
        // Checking title
        String privacyPolicyTitle = page.title();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    public void termsAndConditions_ButtonChecking() {
        page.navigate(homeSAT);
        // Click on Terms and Conditions button
        page.click("a:nth-of-type(2) > p");
        // Checking title
        String privacyPolicyTitle = page.title();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    public void languageAndCurrency_FunctionalityChecking() {
        page.navigate(homeSAT);

        // Click on current language box
        page.click(".current-language-box");
        //page.pause();

    }
}
