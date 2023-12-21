package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.ElementHandle;
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
public class SaveATrain_e2e_Eurail_Tests extends PlaywrightTestBase {
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
    String passportNumber = faker.number().digits(28);
    String city = faker.address().cityName();
    String street = faker.address().streetName();
    String postalCode = faker.address().zipCode();
    String phoneNumber = "48" + faker.number().numberBetween(111111111, 999999999);
    String email = "test_" + faker.name().firstName() + "@gmail.com";

    @Test
    @DisplayName("EURAIL provider.")
    @Description("Test checking e2e path on Eurail provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    public void e2e_SAT_EURAIL_test() {
        mainPage.performSearch();
        mainPage.passBookingTabClick();
        mainPage.fillCountryToVisit("Global Pass");


        Locator formElement = page.locator(".pass-booking-content-form");
        formElement.click();

        Locator inputElement2 = page.locator(".input-group:nth-child(3) .input-control");
        inputElement2.type("Pakistan");

        formElement.click();

        Locator buttonElement = page.locator(".btn > p");
        buttonElement.click();

        page.click("id=pass-1");
        page.click(".proceed-btn");

        // Select the second option in the dropdown
        Locator genderDropdown = page.locator("#passenger-prefix");
        genderDropdown.selectOption(new SelectOption().setIndex(1));

        // Click on the passenger-fname element
        page.click("#passenger-fname");

        // Type "Test" into the passenger-fname input
        page.fill("#passenger-fname", firstName);

        // Type "Tester" into the passenger-lname input
        page.fill("#passenger-lname", lastName);

        // Click on the passenger-date element
        page.click("#passenger-date");

        // Type the date into the passenger-date input
        page.fill("#passenger-date", "06/09/1985");

        // Click on the input-control element
        page.click(".input-control");

        // Find and hover over the items-list element
        Locator itemsList = page.locator(".items-list > .ng-star-inserted:first-of-type");
        itemsList.click();

        // Fill passenger contact number
        page.click("id=passenger-contact-number");
        page.fill("id=passenger-contact-number", phoneNumber);

        // Click on the ng-pristine element
        page.click(".ng-pristine");

        // Enter an email in the contact-info-input form control
        page.fill(".contact-info-input > .form-control", email);

        // Click on the submit-button element
        page.click(".submit-button > .ng-star-inserted");

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Sleep for 15 seconds
        page.waitForTimeout(15000);

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle element = page.querySelector(".common-order-data");

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);
    }
}
