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
        productsPage = new ProductsPage(page);
        railSearchApiProductPage = new RailSearchApiProductPage(page);
        railBookApiProductPage = new RailBookApiProductPage(page);
        railForwardApiPage = new RailForwardApiPage(page);
        railAgentDashboardProductPage = new RailAgentDashboardProductPage(page);
        whiteLabelProductPage = new WhiteLabelProductPage(page);
        railAffiliateProductPage = new RailAffiliateProductPage(page);
        eurailProductPage = new EurailProductPage(page);
        manageBookingsHelpTabPage = new ManageBookingsHelpTabPage(page);
        faqHelpTabPage = new FAQHelpTabPage(page);
        privacyPolicyPage = new PrivacyPolicyPage(page);
        termsAndConditionsPage = new TermsAndConditionsPage(page);
    }

    @Test
    public void mainPageTitleTest() {
        String actualTitle = mainPage.getMainPageTitle().trim();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", actualTitle);
    }

    @Test
    public void mainPageURLTest() {
        String actualURL = mainPage.getMainPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE, actualURL);
    }

    @Test
    public void aboutUs_TabChecking() {
        // User should see About Us introduction page
        String titleAboutUsPage = aboutUsPage.getAboutUsPageTitle().trim();
        Assertions.assertEquals("About Us | Save A Train", titleAboutUsPage);
    }

    @Test
    public void contactUs_TabChecking() {
        // User should see Contact Us page
        String contactUsTitle = contactUsPage.getContactUsPageTitle().trim();
        Assertions.assertEquals("Contact Us | Save A Train", contactUsTitle);
    }

    @Test
    public void productPageChecking() {
        // User should see Products introduction page
        String productsPageTitle = productsPage.getProductsPageTitle().trim();
        Assertions.assertEquals("Products | Save A Train", productsPageTitle);
    }

    @Test
    public void railSearchApi_ProductChecking() {
        // User should see Rail Search API product introduction page
        String railSearchApiTitle = railSearchApiProductPage.getRailSearchApiPageTitle().trim();
        Assertions.assertEquals("Rail Search Api | Save A Train", railSearchApiTitle);
    }

    @Test
    public void railBookApi_ProductChecking() {
        // User should see Rail Book API product introduction page
        String railBookApiTitle = railBookApiProductPage.getRailBookApiPageTitle().trim();
        Assertions.assertEquals("Rail Book Api | Save A Train", railBookApiTitle);
    }

    @Test
    public void railForwardApi_ProductChecking() {
        // User should see Rail Forward product introduction page
        String railForwardApiTitle = railForwardApiPage.getRailForwardApiPageTitle().trim();
        Assertions.assertEquals("Rails Forward Api | Save A Train", railForwardApiTitle);
    }

    @Test
    public void railAgentDashboard_ProductChecking() {
        // User should see Rail Agent Dashboard product introduction page
        String railAgentDashboardProductTitle = railAgentDashboardProductPage.getRailAgentDashboardProductPageTitle().trim();
        Assertions.assertEquals("Rail Agent Dashboard | Save A Train", railAgentDashboardProductTitle);
    }

    @Test
    public void whiteLabel_ProductChecking() {
        // User should see White Label product introduction page
        String whiteLabelProductTitle = whiteLabelProductPage.getWhiteLabelProductPageTitle().trim();
        Assertions.assertEquals("White Label | Save A Train", whiteLabelProductTitle);
    }

    @Test
    public void railAffiliate_ProductChecking() {
        // User should see Rail Affiliate product introduction page
        String railAffiliateProductTitle = railAffiliateProductPage.getRailAffiliateProductPageTitle().trim();
        Assertions.assertEquals("Rail Affiliate | Save A Train", railAffiliateProductTitle);
    }

    @Test
    public void eurailPass_ProductChecking() {
        // User should see Eurail product introduction page
        String eurailProductTitle = eurailProductPage.getEurailProductPageTitle().trim();
        Assertions.assertEquals("Eurail Pass | Save A Train", eurailProductTitle);
    }

    @Test
    public void manageBookings_HelpTabChecking() {
        // User should see Manage Bookings in Help tab introduction page
        String manageBookingsTitle = manageBookingsHelpTabPage.getManageBookingsHelpTabPageTitle().trim();
        Assertions.assertEquals("Manage Bookings | Save A Train", manageBookingsTitle);
    }

    @Test
    public void FAQ_HelpTabChecking() {
        // User should see FAQ page in Help tab introduction page
        String faqTitle = faqHelpTabPage.getFAQHelpTabPageTitle().trim();
        Assertions.assertEquals("FAQ | Save A Train", faqTitle);
    }

    @Test
    public void readMore_ButtonChecking() {
        // Checking Read More button functionality
        String readMoreTitle = aboutUsPage.readMoreButtonFunctionality().trim();
        Assertions.assertEquals("About Us | Save A Train", readMoreTitle);
    }

    @Test
    public void privacyPolicy_ButtonChecking() {
        // Checking Privacy Policy title
        String privacyPolicyTitle = privacyPolicyPage.navigateToPrivacyPolicyPage().trim();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    public void termsAndConditions_ButtonChecking() {
        // Checking Terms And Conditions title
        String privacyPolicyTitle = termsAndConditionsPage.termsAndConditionsButtonFunctionality().trim();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @ParameterizedTest
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station",
            "Rome Termini, Florence All Stations",
            "Malmo Central Station, Stockholm Central Station",
            "Madrid, Leon"})
    public void checking_CorrectnessOfSearchData(String origin, String destination) {
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.performSearch();
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();
        String searchPage_Origin = page.locator(".direction .direction-from").textContent().trim();
        Assertions.assertEquals(origin, searchPage_Origin);
        String searchPage_Destination = page.locator(".direction .direction-to").textContent().trim();
        Assertions.assertEquals(destination, searchPage_Destination);
    }

    @Test
    public void _changingDestinationWithOrigin_FunctionalityButton() {
        mainPage.complementingTheOriginStations("Berlin Central Station");
        mainPage.complementingTheDestinationStations("Hamburg Central Station");
        // Find and interact with the elements
        String originPrimary = page.locator(".input-control-container > .origin").inputValue();
        String destinPrimary = page.locator(".destination:nth-child(1)").inputValue();
        Assertions.assertEquals("Berlin Central Station", originPrimary);
        Assertions.assertEquals("Hamburg Central Station", destinPrimary);
        mainPage.clickExchangeButton();
        // Find and interact with the elements
        String originSecondary = page.locator(".input-control-container > .origin").inputValue();
        String destinSecondary = page.locator(".destination:nth-child(1)").inputValue();
        Assertions.assertEquals("Hamburg Central Station", originSecondary);
        Assertions.assertEquals("Berlin Central Station", destinSecondary);
    }

    @Test
    public void checkingProductsTabListSize() {
        int listSize = mainPage.productListSizeChecking();
        Assertions.assertEquals(listSize, 7, "Number of available products is wrong.");
    }

    @Test
    public void checkingHelpTabListSize() {
        int listSize = mainPage.helpListSizeChecking();
        Assertions.assertEquals(listSize, 2, "Number of help tab functionalities is wrong.");
    }

    @Test
    public void currency_ListSizeChecking() {
        int currencyListSize = mainPage.currencyListSizeChecking();
        Assertions.assertEquals(7, currencyListSize, "Number of available currencies is wrong.");

    }

    @Test
    public void language_ListSizeChecking() {
        int languageListSize = mainPage.languageListSizeChecking();
        Assertions.assertEquals(10, languageListSize, "Number of available languages is wrong.");
    }
}
