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

        resultsPage.selectFirstResult();

        // On this moment we can check checkboxes functionality
        //page.check("lokator"); - zaznaczenie checkboxa
        //page.uncheck("lokator"); - odznaczenie checkboxa

        passengersDetailsPage.passengerPrefixDropdown("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.chooseAislePlace();
        passengersDetailsPage.enterEmeil(email);
        passengersDetailsPage.passangersDataSubmitButtonClick();

        // Sleep for 15 seconds
        page.waitForTimeout(15000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle ticketElement = page.querySelector(".tickets-order-data");

        if (ticketElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = ticketElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Locate the element from which you want to extract text
        ElementHandle orderElement = page.querySelector(".common-order-data");

        if (orderElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = orderElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);


        // trip rout //p[@class='trip-route']
        // passenger info //span[@class='property-value']
        // price //span[@class='property-value price-value']
        // seat reserve fee //span[@class='property-value outbound-seat-fee-value']
        // final price //span[@class='property-value total-discounted-price-value']
        // order data //div[@class='common-order-data']

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

        resultsPage.selectFirstResult();

        passengersDetailsPage.passengerPrefixDropdown("Mr");
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

        passengersDetailsPage.chooseAislePlace();

        passengersDetailsPage.enterEmeil(email);

        passengersDetailsPage.passangersDataSubmitButtonClick();

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

        resultsPage.selectFirstResult();

        // On this moment we can check checkboxes functionality
        //page.check("lokator"); - zaznaczenie checkboxa
        //page.uncheck("lokator"); - odznaczenie checkboxa

        passengersDetailsPage.passengerPrefixDropdown("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.enterEmeil(email);
        passengersDetailsPage.passangersDataSubmitButtonClick();

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle element = page.querySelector(".common-order-data");

        if (element != null) {
            // Use the textContent method to get the text from the element
            String elementText = element.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }
}
