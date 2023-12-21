package com.example.saveatrainplaywrith;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import constans.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.*;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

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
    @DisplayName("Main Page")
    @Description("Main Page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void mainPageTitleTest() {
        String actualTitle = mainPage.getMainPageTitle().trim();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", actualTitle);
    }

    @Test
    @DisplayName("Main Page URL")
    @Description("Main Page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void mainPageURLTest() {
        String actualURL = mainPage.getMainPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE, actualURL);
    }

    @Test
    @DisplayName("About Us")
    @Description("About Us tab title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void aboutUs_TabChecking() {
        // User should see About Us introduction page
        String titleAboutUsPage = aboutUsPage.getAboutUsPageTitle().trim();
        Assertions.assertEquals("About Us | Save A Train", titleAboutUsPage);
    }

    @Test
    @DisplayName("About Us URL")
    @Description("About Us tab URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void aboutUsURL_TabChecking() {
        // User should see About Us introduction page
        String urlAboutUsPage = aboutUsPage.getAboutUsPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/help/about", urlAboutUsPage);
    }

    @Test
    @DisplayName("Contact Us")
    @Description("Contact Us tab title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUs_TabChecking() {
        // User should see Contact Us page
        String contactUsTitle = contactUsPage.getContactUsPageTitle().trim();
        Assertions.assertEquals("Contact Us | Save A Train", contactUsTitle);
    }

    @Test
    @DisplayName("Contact Us URL")
    @Description("Contact Us tab URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUsURL_TabChecking() {
        // User should see Contact Us page
        String contactUsURL = contactUsPage.getContactUsPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/help/contact", contactUsURL);
    }

    @Test
    @DisplayName("Products Page")
    @Description("Products Page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void productPageChecking() {
        // User should see Products introduction page
        String productsPageTitle = productsPage.getProductsPageTitle().trim();
        Assertions.assertEquals("Products | Save A Train", productsPageTitle);
    }

    @Test
    @DisplayName("Products Page URL")
    @Description("Products Page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void productPageURLChecking() {
        // User should see Products introduction page
        String productsPageURL = productsPage.getProductsPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/business/products", productsPageURL);
    }

    @Test
    @DisplayName("Rail Search API")
    @Description("Rail Search API page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railSearchApi_ProductChecking() {
        // User should see Rail Search API product introduction page
        String railSearchApiTitle = railSearchApiProductPage.getRailSearchApiPageTitle().trim();
        Assertions.assertEquals("Rail Search Api | Save A Train", railSearchApiTitle);
    }

    @Test
    @DisplayName("Rail Search API URL")
    @Description("Rail Search API page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railSearchApi_ProductURLChecking() {
        // User should see Rail Search API product introduction page
        String railSearchApiURL = railSearchApiProductPage.getRailSearchApiPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/product/rail-api-search", railSearchApiURL);
    }

    @Test
    @DisplayName("Rail Book API")
    @Description("Rail Book API page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railBookApi_ProductChecking() {
        // User should see Rail Book API product introduction page
        String railBookApiTitle = railBookApiProductPage.getRailBookApiPageTitle().trim();
        Assertions.assertEquals("Rail Book Api | Save A Train", railBookApiTitle);
    }

    @Test
    @DisplayName("Rail Book API URL")
    @Description("Rail Book API page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railBookApi_ProductURLChecking() {
        // User should see Rail Book API product introduction page
        String railBookApiURL = railBookApiProductPage.getRailBookApiPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/product/rail-api-book", railBookApiURL);
    }

    @Test
    @DisplayName("Rail Forward API")
    @Description("Rail Forward API page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railForwardApi_ProductChecking() {
        // User should see Rail Forward product introduction page
        String railForwardApiTitle = railForwardApiPage.getRailForwardApiPageTitle().trim();
        Assertions.assertEquals("Rails Forward Api | Save A Train", railForwardApiTitle);
    }

    @Test
    @DisplayName("Rail Forward API URL")
    @Description("Rail Forward API page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railForwardApi_ProductURLChecking() {
        // User should see Rail Forward product introduction page
        String railForwardApiURL = railForwardApiPage.getRailForwardApiPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/product/rail-api-forward", railForwardApiURL);
    }

    @Test
    @DisplayName("Rail Agent Dashboard")
    @Description("Rail Agent Dashboard page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAgentDashboard_ProductChecking() {
        // User should see Rail Agent Dashboard product introduction page
        String railAgentDashboardProductTitle = railAgentDashboardProductPage.getRailAgentDashboardProductPageTitle().trim();
        Assertions.assertEquals("Rail Agent Dashboard | Save A Train", railAgentDashboardProductTitle);
    }

    @Test
    @DisplayName("Rail Agent Dashboard URL")
    @Description("Rail Agent Dashboard page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAgentDashboard_ProductURLChecking() {
        // User should see Rail Agent Dashboard product introduction page
        String railAgentDashboardProductURL = railAgentDashboardProductPage.getRailAgentDashboardProductPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/product/agent-dashboard", railAgentDashboardProductURL);
    }

    @Test
    @DisplayName("White Label")
    @Description("White Label page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void whiteLabel_ProductChecking() {
        // User should see White Label product introduction page
        String whiteLabelProductTitle = whiteLabelProductPage.getWhiteLabelProductPageTitle().trim();
        Assertions.assertEquals("White Label | Save A Train", whiteLabelProductTitle);
    }

    @Test
    @DisplayName("White Label URL")
    @Description("White Label page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void whiteLabel_ProductURLChecking() {
        // User should see White Label product introduction page
        String whiteLabelProductURL = whiteLabelProductPage.getWhiteLabelPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/product/white-label", whiteLabelProductURL);
    }

    @Test
    @DisplayName("Rail Affiliate")
    @Description("Rail Affiliate page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAffiliate_ProductChecking() {
        // User should see Rail Affiliate product introduction page
        String railAffiliateProductTitle = railAffiliateProductPage.getRailAffiliateProductPageTitle().trim();
        Assertions.assertEquals("Rail Affiliate | Save A Train", railAffiliateProductTitle);
    }

    @Test
    @DisplayName("Rail Affiliate URL")
    @Description("Rail Affiliate page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAffiliate_ProductURLChecking() {
        // User should see Rail Affiliate product introduction page
        String railAffiliateProductURL = railAffiliateProductPage.getRailAffiliatePageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/product/rail-affiliat", railAffiliateProductURL);
    }

    @Test
    @DisplayName("Eurail Pass")
    @Description("Eurail Pass page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void eurailPass_ProductChecking() {
        // User should see Eurail product introduction page
        String eurailProductTitle = eurailProductPage.getEurailProductPageTitle().trim();
        Assertions.assertEquals("Eurail Pass | Save A Train", eurailProductTitle);
    }
    @Test
    @DisplayName("Eurail Pass URL")
    @Description("Eurail Pass page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void eurailPass_ProductURLChecking() {
        // User should see Eurail product introduction page
        String eurailProductURL = eurailProductPage.getEurailPassPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/product/eurail", eurailProductURL);
    }

    @Test
    @DisplayName("Manage Bookings")
    @Description("Manage Bookings page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void manageBookings_HelpTabChecking() {
        // User should see Manage Bookings in Help tab introduction page
        String manageBookingsTitle = manageBookingsHelpTabPage.getManageBookingsHelpTabPageTitle().trim();
        Assertions.assertEquals("Manage Bookings | Save A Train", manageBookingsTitle);
    }

    @Test
    @DisplayName("Manage Bookings URL")
    @Description("Manage Bookings page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void manageBookings_HelpTabURLChecking() {
        // User should see Manage Bookings in Help tab introduction page
        String manageBookingsURL = manageBookingsHelpTabPage.getManageBookingsPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/reservations/find", manageBookingsURL);
    }

    @Test
    @DisplayName("FAQ Page")
    @Description("FAQ page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void FAQ_HelpTabChecking() {
        // User should see FAQ page in Help tab introduction page
        String faqTitle = faqHelpTabPage.getFAQHelpTabPageTitle().trim();
        Assertions.assertEquals("FAQ | Save A Train", faqTitle);
    }

    @Test
    @DisplayName("FAQ Page URL")
    @Description("FAQ page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void FAQ_HelpTabURLChecking() {
        // User should see FAQ page in Help tab introduction page
        String faqURL = faqHelpTabPage.getFAQPageURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/help/faqs", faqURL);
    }

    @Test
    @DisplayName("Read More button")
    @Description("Read More button functionality checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void readMore_ButtonChecking() {
        // Checking Read More button functionality
        String readMoreTitle = aboutUsPage.readMoreButtonFunctionality().trim();
        Assertions.assertEquals("About Us | Save A Train", readMoreTitle);
    }

    @Test
    @DisplayName("Privacy Policy button")
    @Description("Privacy Policy button functionality checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void privacyPolicy_ButtonChecking() {
        // Checking Privacy Policy title
        String privacyPolicyTitle = privacyPolicyPage.navigateToPrivacyPolicyPage().trim();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    @DisplayName("Privacy Policy URL")
    @Description("Privacy Policy URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void privacyPolicy_URLChecking() {
        // Checking Privacy Policy title
        String privacyPolicyURL = privacyPolicyPage.getPrivacyPolicyURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/page/privacy-policy", privacyPolicyURL);
    }

    @Test
    @DisplayName("Terms And Conditions button")
    @Description("Terms And Conditions button functionality checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void termsAndConditions_ButtonChecking() {
        // Checking Terms And Conditions title
        String privacyPolicyTitle = termsAndConditionsPage.termsAndConditionsButtonFunctionality().trim();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    @DisplayName("Terms And Conditions URL")
    @Description("Terms And Conditions URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void termsAndConditions_URLChecking() {
        // Checking Terms And Conditions URL
        String termsAndConditionsURL = termsAndConditionsPage.getTermsAndConditionsURL();
        Assertions.assertEquals(AppConstants.SAT_HOME_PAGE + "/page/terms-of-use", termsAndConditionsURL);
    }

    @ParameterizedTest
    @DisplayName("Correctness search data")
    @Description("Test checking correctness search data.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
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
    @DisplayName("Exchange button")
    @Description("Test checking correctness working of exchange button.")
    @Severity(NORMAL)
    @Owner("Save A Train")
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
    @DisplayName("All products list size")
    @Description("Test checking products list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void checkingProductsTabListSize() {
        int listSize = mainPage.productListSizeChecking();
        Assertions.assertEquals(listSize, 7, "Number of available products is wrong.");
    }

    @Test
    @DisplayName("Help tab list size")
    @Description("Test checking help tab list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void checkingHelpTabListSize() {
        int listSize = mainPage.helpListSizeChecking();
        Assertions.assertEquals(listSize, 2, "Number of help tab functionalities is wrong.");
    }

    @Test
    @DisplayName("All currency list size")
    @Description("Test checking available currency list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void currency_ListSizeChecking() {
        int currencyListSize = mainPage.currencyListSizeChecking();
        Assertions.assertEquals(7, currencyListSize, "Number of available currencies is wrong.");

    }

    @Test
    @DisplayName("All languages list size")
    @Description("Test available languages list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void language_ListSizeChecking() {
        int languageListSize = mainPage.languageListSizeChecking();
        Assertions.assertEquals(10, languageListSize, "Number of available languages is wrong.");
    }
}
