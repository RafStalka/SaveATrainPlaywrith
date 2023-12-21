package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
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
import pages.MainPage;
import pages.PassengersDetailsPage;
import pages.ResultsPage;
import pages.SummaryPage;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class SaveATrain_e2e_Tests extends PlaywrightTestBase {
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
    String firstNameSecondAdultPassenger = faker.name().firstName();
    String firstNameYouthPassenger = faker.name().firstName();
    String lastName = faker.name().lastName();
    String lastNameSecondAdultPassenger = faker.name().lastName();
    String lastNameYouthPassenger = faker.name().lastName();
    String email = "test_" + faker.name().firstName() + "@gmail.com";

    @ParameterizedTest
    @DisplayName("NSI provider.")
    @Description("Test checking e2e path on NSI provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station"})
    public void e2e_SAT_NSI_test(String origin, String destination) {
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.selectDepartureAisleOption();
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.passengersDataSubmitButtonClick();
        // Sleep for 15 seconds
        page.waitForTimeout(15000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent().trim();

        // Use Assertions for the assertion
        Assertions.assertEquals("Please, save the following information about your order:", actualHeaderText);

    }

    @ParameterizedTest
    @DisplayName("NSI provider 2xAdult and 1xYouth passenger.")
    @Description("Test checking e2e path on NSI provider with different types of passenger.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Berlin Central Station, Hamburg Central Station"})
    public void e2e_SAT_NSI_twoAdultsAndOneYouth_test(String origin, String destination) {
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.addOneAdultPassenger();
        mainPage.addOneYouthPassengerIn_17_YearsAge("17");
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("12/07/1985");
        passengersDetailsPage.choosePassengerCountry();

        passengersDetailsPage.secondPassengerPrefixDropdown("Mrs");
        passengersDetailsPage.enter_SecondAdultPassenger_FirstAndLastName(firstNameSecondAdultPassenger, lastNameSecondAdultPassenger);
        passengersDetailsPage.enter_SecondAdultPassenger_birthDate("22/09/1987");
        passengersDetailsPage.choose_SecondAdultPassenger_Country();

        passengersDetailsPage.thirdPassengerPrefixDropdown("Mr");
        passengersDetailsPage.enter_YouthPassenger_FirstAndLastName(firstNameYouthPassenger, lastNameYouthPassenger);
        passengersDetailsPage.enter_YouthPassenger_birthDate("01/01/2007");
        passengersDetailsPage.choose_YouthPassenger_Country();

        passengersDetailsPage.selectDepartureAisleOption();

        passengersDetailsPage.enterEmail(email);

        passengersDetailsPage.passengersDataSubmitButtonClick();

        // Sleep for 15 seconds
        page.waitForTimeout(15000);

        summaryPage.completingAdyenForm();
    }

    @ParameterizedTest
    @DisplayName("TI provider.")
    @Description("Test checking e2e path on TI provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Rome Termini, Florence All Stations",
            "Milan Central Station, Rimini Central Station"})
    public void e2e_SAT_TI_test(String origin, String destination) {
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.passengersDataSubmitButtonClick();

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent().trim();

        // Use Assertions for the assertion
        Assertions.assertEquals("Please, save the following information about your order:", actualHeaderText);

    }
}
