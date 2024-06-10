package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.PassengersDetailsPage;
import pages.ResultsPage;
import pages.SummaryPage;

import static io.qameta.allure.SeverityLevel.CRITICAL;

/**
 * This class contains end-to-end tests for the SaveATrain website using Playwright framework.
 * It extends the PlaywrightTestBase class, which provides the base setup and teardown methods for the tests.
 */
public class SaveATrainE2EEurailTests extends PlaywrightTestBase {

    public static final String PAYMENT_WAS_SUCCESSFUL = "Your payment was successful";
    public static final String BIRTH_DATE = "06/09/1985";
    public static final String GLOBAL_PASS = "Global Pass";
    public static final String GMAIL_COM = "@gmail.com";
    public static final String TEST = "test_";
    public static final String COUNTRY_NUMBER = "48";

    @BeforeEach
    public void setUp() {
        // Assuming 'page' is initialized in your base class
        mainPage = new MainPage(page);
        passengersDetailsPage = new PassengersDetailsPage(page);
        resultsPage = new ResultsPage(page);
        summaryPage = new SummaryPage(page);
    }

    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String phoneNumber = COUNTRY_NUMBER + faker.number().numberBetween(111111111, 999999999);
    String email = TEST + faker.name().firstName() + GMAIL_COM;

    @Test
    @DisplayName("EURAIL provider.")
    @Description("Test checking e2e path on Eurail provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    public void e2e_SAT_EURAIL_test() {
        mainPage.navigateToHomePage();
        mainPage.performSearch();
        mainPage.passBookingTabClick();
        mainPage.fillCountryToVisit(GLOBAL_PASS);
        mainPage.fillCountryOfResidence();
        mainPage.findMyPassButtonClick();

        resultsPage.selectFirstOptionOnEurailPass();
        resultsPage.proceed();

        Locator genderDropdown = page.locator("#passenger-prefix");
        genderDropdown.selectOption(new SelectOption().setIndex(1));
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterBirthDate(BIRTH_DATE);
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.enterMobilePhone(phoneNumber);
        passengersDetailsPage.passengersDataSubmitButtonClick();
        // Sleep for 7 seconds
        page.waitForTimeout(10000);

        summaryPage.completingAdyenForm();

        // Sleep for 15 seconds
        page.waitForTimeout(15000);

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("xpath=/html/body/sat-root/div/main/sat-order/div/div/div[2]/div/div/p[1]").textContent().trim();

        // Use Assertions for the assertion
        Assertions.assertEquals(PAYMENT_WAS_SUCCESSFUL, actualHeaderText);
    }
}
