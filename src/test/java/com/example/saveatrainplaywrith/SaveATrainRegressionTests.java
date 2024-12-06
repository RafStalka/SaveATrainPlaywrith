package com.example.saveatrainplaywrith;

import com.microsoft.playwright.Page;
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

import static constans.AppConstants.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SaveATrainRegressionTests is a class that contains regression tests for the Save A Train application.
 * It extends the PlaywrightTestBase class.
 * It provides methods for setting up the test environment and executing various test cases.
 */
public class SaveATrainRegressionTests extends PlaywrightTestBase {

    private MainPage mainPage;
    private PassengersDetailsPage passengersDetailsPage;
    private ResultsPage resultsPage;
    private SummaryPage summaryPage;
    private AboutUsPage aboutUsPage;
    private ContactUsPage contactUsPage;
    private ProductsPage productsPage;
    private RailSearchApiProductPage railSearchApiProductPage;
    private RailBookApiProductPage railBookApiProductPage;
    private RailForwardApiPage railForwardApiPage;
    private RailAgentDashboardProductPage railAgentDashboardProductPage;
    private WhiteLabelProductPage whiteLabelProductPage;
    private RailAffiliateProductPage railAffiliateProductPage;
    private RailEnrichmentAPIProductPage railEnrichmentProductPage;
    private EurailProductPage eurailProductPage;
    private ManageBookingsHelpTabPage manageBookingsHelpTabPage;
    private FAQHelpTabPage faqHelpTabPage;
    private PrivacyPolicyPage privacyPolicyPage;
    private TermsAndConditionsPage termsAndConditionsPage;
    private BlogPage blogPage;

    @BeforeEach
    public void setUp() {
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
        railEnrichmentProductPage = new RailEnrichmentAPIProductPage(page);
        eurailProductPage = new EurailProductPage(page);
        manageBookingsHelpTabPage = new ManageBookingsHelpTabPage(page);
        faqHelpTabPage = new FAQHelpTabPage(page);
        privacyPolicyPage = new PrivacyPolicyPage(page);
        termsAndConditionsPage = new TermsAndConditionsPage(page);
        blogPage = new BlogPage(page);
    }

    @Test
    @DisplayName("Main Page")
    @Description("Main Page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void mainPageTitleTest() {
        String actualTitle = mainPage.getMainPageTitle().trim();
        assertEquals(AppConstants.TRAIN_TICKET_BOOKING_AND_RESERVATION_SAVE_A_TRAIN, actualTitle);
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
        mainPage.navigateToAboutUsPage();
        String titleAboutUsPage = aboutUsPage.getAboutUsPageTitle().trim();
        assertEquals(ABOUT_US_SAVE_A_TRAIN, titleAboutUsPage);
    }

    @Test
    @DisplayName("About Us URL")
    @Description("About Us tab URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void aboutUsURL_TabChecking() {
        mainPage.navigateToAboutUsPage();
        String aboutUsPageUrl = aboutUsPage.getAboutUsPageURL();
        String expectedAboutUsPageUrl = "https://sat-client-staging.saveatrain.com/en(/shop)?/help/about";
        assertTrue(aboutUsPageUrl.matches(expectedAboutUsPageUrl), "Incorrect About Us Page URL");
    }

    @Test
    @DisplayName("Contact Us tab")
    @Description("Contact Us tab title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUs_TabChecking() {
        mainPage.navigateToContactUsPage();
        String contactUsTitle = contactUsPage.getContactUsPageTitle().trim();
        assertEquals("Contact Us | Save A Train", contactUsTitle);
    }

    @Test
    @DisplayName("Contact Us")
    @Description("Contact Us - fill form to contact without eny message - negative scenario.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUsWithoutMessageTextTest() {
        mainPage.navigateToHomePage();
        ContactUsPage contactUsPage = mainPage.navigateToContactUsPage();
        contactUsPage.fillContactUsForm("Test", "Testerski", "test@email.com", "");
        contactUsPage.submitContactUsForm();
        String expectedSuccessMessage = "Message can't be blank";
        String actualSuccessMessage = contactUsPage.getContactUsFormErrorFromEmptyMessageInputField().trim();

        assertEquals(expectedSuccessMessage, actualSuccessMessage,
                "Failed - The actual error message does not match the expected message!");
    }

    @Test
    @DisplayName("Contact Us")
    @Description("Contact Us - fill form to contact without first name - negative scenario.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUsWithoutFirstNameTextTest() {
        mainPage.navigateToHomePage();
        ContactUsPage contactUsPage = mainPage.navigateToContactUsPage();
        contactUsPage.fillContactUsForm("", "Testerski", "test@email.com", "Random message.");
        contactUsPage.submitContactUsForm();
        String expectedSuccessMessage = "Name can't be blank";
        String actualSuccessMessage = contactUsPage.getContactUsFormErrorFromEmptyFirstNameInputField().trim();

        Assertions.assertEquals(expectedSuccessMessage, actualSuccessMessage,
                "Failed - The actual error message does not match the expected message!");
    }

    @Test
    @DisplayName("Contact Us")
    @Description("Contact Us - fill form to contact without last name - negative scenario.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUsWithoutLastNameTextTest() {
        mainPage.navigateToHomePage();
        ContactUsPage contactUsPage = mainPage.navigateToContactUsPage();
        contactUsPage.fillContactUsForm("Test", "", "test@email.com", "Random message.");
        contactUsPage.submitContactUsForm();
        String expectedSuccessMessage = "Name can't be blank";
        String actualSuccessMessage = contactUsPage.getContactUsFormErrorFromEmptyLastNameInputField().trim();

        Assertions.assertEquals(expectedSuccessMessage, actualSuccessMessage,
                "Failed - The actual error message does not match the expected message!");
    }

    @Test
    @DisplayName("Contact Us")
    @Description("Contact Us - fill form to contact without email - negative scenario.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUsWithoutEmailTextTest() {
        mainPage.navigateToHomePage();
        ContactUsPage contactUsPage = mainPage.navigateToContactUsPage();
        contactUsPage.fillContactUsForm("Test", "Testerski", "", "Random message.");
        contactUsPage.submitContactUsForm();
        String expectedSuccessMessage = "Email can't be blank";
        String actualSuccessMessage = contactUsPage.getContactUsFormErrorFromEmptyEmailInputField().trim();

        Assertions.assertEquals(expectedSuccessMessage, actualSuccessMessage,
                "Failed - The actual error message does not match the expected message!");
    }

    @Test
    @DisplayName("Contact Us")
    @Description("Contact Us fill form to contact with all fields - positive scenario.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUsWithWholeTextTest() {
        mainPage.navigateToHomePage();
        ContactUsPage contactUsPage = mainPage.navigateToContactUsPage();
        contactUsPage.fillContactUsForm("Test", "Testerski", "test@email.com", "Some random message.");
        contactUsPage.submitContactUsForm();
    }

    @Test
    @DisplayName("Contact Us URL")
    @Description("Contact Us tab URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void contactUsURL_TabChecking() {
        mainPage.navigateToContactUsPage();
        String contactUsURL = contactUsPage.getContactUsPageURL();
        String expectedContactUsURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/help/contact";
        Assertions.assertTrue(contactUsURL.matches(expectedContactUsURL), "Incorrect Contact Us Page URL");
    }

    @Test
    @DisplayName("Products Page")
    @Description("Products Page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void productPageChecking() {
        mainPage.navigateToProductsPage();
        String productsPageTitle = productsPage.getProductsPageTitle().trim();
        Assertions.assertEquals(PRODUCTS_SAVE_A_TRAIN, productsPageTitle);
    }

    @Test
    @DisplayName("Products Page URL")
    @Description("Products Page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void productPageURLChecking() {
        mainPage.navigateToProductsPage();
        String productsPageURL = productsPage.getProductsPageURL();
        String expectedProductsPageURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/business/products";
        Assertions.assertTrue(productsPageURL.matches(expectedProductsPageURL), "Incorrect Products Page URL");
    }

    @Test
    @DisplayName("Rail Search API")
    @Description("Rail Search API page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railSearchApi_ProductChecking() {
        mainPage.navigateToRailSearchApiProductPage();
        String railSearchApiTitle = railSearchApiProductPage.getRailSearchApiPageTitle().trim();
        assertEquals(RAIL_SEARCH_API_SAVE_A_TRAIN, railSearchApiTitle);
    }

    @Test
    @DisplayName("Rail Search API URL")
    @Description("Rail Search API page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railSearchApi_ProductURLChecking() {
        mainPage.navigateToRailSearchApiProductPage();
        String railSearchApiURL = railSearchApiProductPage.getRailSearchApiPageURL();
        String expectedRailSearchApiURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/rail-api-search";
        Assertions.assertTrue(railSearchApiURL.matches(expectedRailSearchApiURL), "Incorrect Rail Search Api URL");
    }

    @Test
    @DisplayName("Rail Book API")
    @Description("Rail Book API page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railBookApi_ProductChecking() {
        mainPage.navigateToRailBookApiProductPage();
        String railBookApiTitle = railBookApiProductPage.getRailBookApiPageTitle().trim();
        assertEquals(RAIL_BOOK_API_SAVE_A_TRAIN, railBookApiTitle);
    }

    @Test
    @DisplayName("Rail Book API URL")
    @Description("Rail Book API page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railBookApi_ProductURLChecking() {
        mainPage.navigateToRailBookApiProductPage();
        String railBookApiURL = railBookApiProductPage.getRailBookApiPageURL();
        String expectedRailBookApiURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/rail-api-book";
        Assertions.assertTrue(railBookApiURL.matches(expectedRailBookApiURL), "Incorrect Rail Book Api URL");
    }

    @Test
    @DisplayName("Rail Forward API")
    @Description("Rail Forward API page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railForwardApi_ProductChecking() {
        mainPage.navigateToRailForwardApiPagePage();
        String railForwardApiTitle = railForwardApiPage.getRailForwardApiPageTitle().trim();
        assertEquals(RAILS_FORWARD_API_SAVE_A_TRAIN, railForwardApiTitle);
    }

    @Test
    @DisplayName("Rail Forward API URL")
    @Description("Rail Forward API page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railForwardApi_ProductURLChecking() {
        mainPage.navigateToRailForwardApiPagePage();
        String railForwardApiURL = railForwardApiPage.getRailForwardApiPageURL();
        String expectedRailForwardApiURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/rail-api-forward";
        assertTrue(railForwardApiURL.matches(expectedRailForwardApiURL), "Incorrect Rail Forward Api URL");
    }

    @Test
    @DisplayName("Rail enrichment API")
    @Description("Rail enrichment API page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railEnrichmentApi_ProductChecking() {
        mainPage.navigateToRailEnrichmentAPIProductPage();
        String railEnrichmentApiTitle = railEnrichmentProductPage.getRailEnrichmentAPIProductPageTitle();
        assertEquals(RAILS_ENRICHMENT_API_SAVE_A_TRAIN, railEnrichmentApiTitle);
    }

    @Test
    @DisplayName("Rail enrichment API")
    @Description("Rail enrichment API page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railEnrichmentApi_ProductURLChecking() {
        mainPage.navigateToRailEnrichmentAPIProductPage();
        String railEnrichmentApiURL = railEnrichmentProductPage.getRailEnrichmentAPIProductPageURL();
        String expectedRailEnrichmentApiURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/rail-api-enrichment";
        assertTrue(railEnrichmentApiURL.matches(expectedRailEnrichmentApiURL), "Incorrect Rail Enrichment Api URL");
    }

    @Test
    @DisplayName("Rail Agent Dashboard")
    @Description("Rail Agent Dashboard page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAgentDashboard_ProductChecking() {
        mainPage.navigateToRailAgentDashboardProductPage();
        String railAgentDashboardProductTitle = railAgentDashboardProductPage.getRailAgentDashboardProductPageTitle().trim();
        assertEquals(RAIL_AGENT_DASHBOARD_SAVE_A_TRAIN, railAgentDashboardProductTitle);
    }

    @Test
    @DisplayName("Rail Agent Dashboard URL")
    @Description("Rail Agent Dashboard page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAgentDashboard_ProductURLChecking() {
        mainPage.navigateToRailAgentDashboardProductPage();
        String railAgentDashboardProductURL = railAgentDashboardProductPage.getRailAgentDashboardProductPageURL();
        String expectedRailAgentDashboardProductURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/agent-dashboard";
        assertTrue(railAgentDashboardProductURL.matches(expectedRailAgentDashboardProductURL), "Incorrect Rail Agent Dashboard Product URL");
    }

    @Test
    @DisplayName("White Label")
    @Description("White Label page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void whiteLabel_ProductChecking() {
        mainPage.navigateToWhiteLabelProductPage();
        String whiteLabelProductTitle = whiteLabelProductPage.getWhiteLabelProductPageTitle().trim();
        assertEquals(WHITE_LABEL_SAVE_A_TRAIN, whiteLabelProductTitle);
    }

    @Test
    @DisplayName("White Label URL")
    @Description("White Label page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void whiteLabel_ProductURLChecking() {
        mainPage.navigateToWhiteLabelProductPage();
        String whiteLabelProductURL = whiteLabelProductPage.getWhiteLabelPageURL();
        String expectedWhiteLabelProductURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/white-label";
        assertTrue(whiteLabelProductURL.matches(expectedWhiteLabelProductURL), "Incorrect White Label Product URL");
    }

    @Test
    @DisplayName("Rail Affiliate")
    @Description("Rail Affiliate page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAffiliate_ProductChecking() {
        mainPage.navigateToRailAffiliateProductPage();
        String railAffiliateProductTitle = railAffiliateProductPage.getRailAffiliateProductPageTitle().trim();
        assertEquals(RAIL_AFFILIATE_SAVE_A_TRAIN, railAffiliateProductTitle);
    }

    @Test
    @DisplayName("Rail Affiliate URL")
    @Description("Rail Affiliate page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void railAffiliate_ProductURLChecking() {
        mainPage.navigateToRailAffiliateProductPage();
        String railAffiliateProductURL = railAffiliateProductPage.getRailAffiliatePageURL();
        String expectedRailAffiliateProductURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/rail-affiliat";
        assertTrue(railAffiliateProductURL.matches(expectedRailAffiliateProductURL), "Incorrect Rail Affiliate Product URL");
    }

    @Test
    @DisplayName("Eurail Pass")
    @Description("Eurail Pass page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void eurailPass_ProductChecking() {
        mainPage.navigateToEurailProductPage();
        String eurailProductTitle = eurailProductPage.getEurailProductPageTitle().trim();
        assertEquals(EURAIL_PASS_SAVE_A_TRAIN, eurailProductTitle);
    }

    @Test
    @DisplayName("Eurail Pass URL")
    @Description("Eurail Pass page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void eurailPass_ProductURLChecking() {
        mainPage.navigateToEurailProductPage();
        String eurailProductURL = eurailProductPage.getEurailPassPageURL();
        String expectedEurailProductURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/product/eurail";
        assertTrue(eurailProductURL.matches(expectedEurailProductURL), "Incorrect Eurail Product URL");
    }

    @Test
    @DisplayName("Manage Bookings")
    @Description("Manage Bookings page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void manageBookings_HelpTabChecking() {
        mainPage.navigateToManageBookingsHelpTabPage();
        String manageBookingsTitle = manageBookingsHelpTabPage.getManageBookingsHelpTabPageTitle().trim();
        assertEquals(MANAGE_BOOKINGS_SAVE_A_TRAIN, manageBookingsTitle);
    }

    @Test
    @DisplayName("Manage Bookings URL")
    @Description("Manage Bookings page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void manageBookings_HelpTabURLChecking() {
        mainPage.navigateToManageBookingsHelpTabPage();
        String manageBookingsURL = manageBookingsHelpTabPage.getManageBookingsPageURL();
        String expectedManageBookingsURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/reservations/find";
        assertTrue(manageBookingsURL.matches(expectedManageBookingsURL), "Incorrect Manage Bookings URL");
    }

    @Test
    @DisplayName("FAQ Page")
    @Description("FAQ page title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void FAQ_HelpTabChecking() {
        mainPage.navigateToFAQPage();
        String faqTitle = faqHelpTabPage.getFAQHelpTabPageTitle().trim();
        assertEquals(FAQ_SAVE_A_TRAIN, faqTitle);
    }

    @Test
    @DisplayName("FAQ Page URL")
    @Description("FAQ page URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void FAQ_HelpTabURLChecking() {
        mainPage.navigateToFAQPage();
        String faqURL = faqHelpTabPage.getFAQPageURL();
        String expectedFaqURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/help/faqs";
        assertTrue(faqURL.matches(expectedFaqURL), "Incorrect FAQ URL");
    }

    @Test
    @DisplayName("Read More button")
    @Description("Read More button functionality checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void readMore_ButtonChecking() {
        String readMoreTitle = aboutUsPage.readMoreButtonFunctionality().trim();
        assertEquals(ABOUT_US_SAVE_A_TRAIN, readMoreTitle);
    }

    @Test
    @DisplayName("Privacy Policy title")
    @Description("Privacy Policy title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void privacyPolicy_ButtonChecking() {
        mainPage.navigateToPrivacyPolicyPage();
        String privacyPolicyTitle = privacyPolicyPage.getPrivacyPolicyTitle().trim();
        assertEquals(TRAIN_TICKET_BOOKING_AND_RESERVATION_SAVE_A_TRAIN, privacyPolicyTitle);
    }

    @Test
    @DisplayName("Privacy Policy URL")
    @Description("Privacy Policy URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void privacyPolicy_URLChecking() {
        mainPage.navigateToPrivacyPolicyPage();
        String privacyPolicyURL = privacyPolicyPage.getPrivacyPolicyURL();
        String expectedPrivacyPolicyURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/page/privacy-policy";
        assertTrue(privacyPolicyURL.matches(expectedPrivacyPolicyURL), "Incorrect privacy policy URL");
    }

    @Test
    @DisplayName("Terms And Conditions title")
    @Description("Terms And Conditions title checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void termsAndConditions_ButtonChecking() {
        mainPage.navigateToTermsAndConditionsPage();
        String privacyPolicyTitle = termsAndConditionsPage.getTermsAndConditionsPageTitle();
        assertEquals(TRAIN_TICKET_BOOKING_AND_RESERVATION_SAVE_A_TRAIN, privacyPolicyTitle);
    }

    @Test
    @DisplayName("Terms And Conditions URL")
    @Description("Terms And Conditions URL checking.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void termsAndConditions_URLChecking() {
        mainPage.navigateToTermsAndConditionsPage();
        String termsAndConditionsURL = termsAndConditionsPage.getTermsAndConditionsURL();
        String expectedTermsAndConditionsURL = "https://sat-client-staging.saveatrain.com/en(/shop)?/page/terms-of-use";
        assertTrue(termsAndConditionsURL.matches(expectedTermsAndConditionsURL), "Incorrect Terms And Conditions URL");
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
        mainPage.navigateToHomePage();
        mainPage.performSearch();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();
        String searchPage_Origin = page.locator(".direction .direction-from").textContent().trim();
        assertEquals(origin, searchPage_Origin);
        String searchPage_Destination = page.locator(".direction .direction-to").textContent().trim();
        assertEquals(destination, searchPage_Destination);
    }

    @Test
    @DisplayName("Exchange button")
    @Description("Test checking correctness working of exchange button.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void _changingDestinationWithOrigin_FunctionalityButton() {
        mainPage.navigateToHomePage();
        mainPage.performSearch();
        mainPage.complementingTheOriginStations("Berlin Central Station");
        mainPage.complementingTheDestinationStations("Hamburg Central Station");
        String originPrimary = page.locator("#main-form-departure-station").getByPlaceholder("From").inputValue();
        String destinPrimary = page.locator("#main-form-destination-station").getByPlaceholder("To").inputValue();
        assertEquals("Berlin Central Station", originPrimary);
        assertEquals("Hamburg Central Station", destinPrimary);
        mainPage.clickExchangeButton();
        String originSecondary = page.locator("#main-form-departure-station").getByPlaceholder("From").inputValue();
        String destinSecondary = page.locator("#main-form-destination-station").getByPlaceholder("To").inputValue();
        assertEquals("Hamburg Central Station", originSecondary);
        assertEquals("Berlin Central Station", destinSecondary);
    }

    @Test
    @DisplayName("All products list size")
    @Description("Test checking products list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void checkingProductsTabListSize() {
        int listSize = mainPage.productListSizeChecking();
        assertEquals(listSize, 8, NUMBER_OF_AVAILABLE_PRODUCTS_IS_WRONG);
    }

    @Test
    @DisplayName("Help tab list size")
    @Description("Test checking help tab list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void checkingHelpTabListSize() {
        int listSize = mainPage.helpListSizeChecking();
        assertEquals(listSize, 2, NUMBER_OF_HELP_TAB_FUNCTIONALITIES_IS_WRONG);
    }

    @Test
    @DisplayName("All currency list size")
    @Description("Test checking available currency list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void currency_ListSizeChecking() {
        int currencyListSize = mainPage.currencyListSizeChecking();
        assertEquals(7, currencyListSize, NUMBER_OF_AVAILABLE_CURRENCIES_IS_WRONG);

    }

    @Test
    @DisplayName("All languages list size")
    @Description("Test available languages list size.")
    @Severity(NORMAL)
    @Owner("Save A Train")
    public void language_ListSizeChecking() {
        int languageListSize = mainPage.languageListSizeChecking();
        assertEquals(10, languageListSize, NUMBER_OF_AVAILABLE_LANGUAGES_IS_WRONG);
    }

    @Test
    public void mainPageToBlogTest() {
        mainPage.navigateToHomePage();
        Page page1 = page.waitForPopup(() -> {
            page.getByText("Blog").click();
        });
        String blogPageURL = page1.url();
        assertEquals("https://www.saveatrain.com/blog/", blogPageURL);

    }
}
