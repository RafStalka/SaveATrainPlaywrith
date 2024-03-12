package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.ElementHandle;
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

/**
 * This class represents the end-to-end ACP provider tests for the Save A Train application.
 * It extends the PlaywrightTestBase class and inherits its protected fields and methods.
 */
public class SaveATrainE2EACPTests extends PlaywrightTestBase {

    @BeforeEach
    public void setUp() {
        mainPage = new MainPage(page);
        passengersDetailsPage = new PassengersDetailsPage(page);
        resultsPage = new ResultsPage(page);
        summaryPage = new SummaryPage(page);
    }
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String firstNameSecondPassenger = faker.name().firstName();
    String firstNameThirdPassenger = faker.name().firstName();
    String lastName = faker.name().lastName();
    String lastNameSecondPassenger = faker.name().lastName();
    String lastNameThirdPassenger = faker.name().lastName();
    String passportNumber = faker.number().digits(28);
    String passportNumberSecondPassenger = faker.number().digits(28);
    String passportNumberThirdPassenger = faker.number().digits(28);
    String city = faker.address().cityName();
    String street = faker.address().streetName();
    String postalCode = faker.address().zipCode();
    String phoneNumber = "48" + faker.number().numberBetween(111111111, 999999999);
    String email = "test_" + faker.name().firstName() + "@gmail.com";

    @ParameterizedTest
    @DisplayName("ACP provider for Spain.")
    @Description("Test checking e2e path on ACP provider for Spain.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Madrid, Leon", "Cadiz, Madrid", "Barcelona El Prat Airport, Madrid", "Leon, Madrid"})
    public void e2e_SAT_ACPForSpain_test(String origin, String destination) {
        mainPage.navigateToHomePage();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.performSearch();
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();
        String departure = resultsPage.getDepartureStation_ResultPage().strip();
        String arrival = resultsPage.getArrivalStation_ResultPage();
        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();
        String durationTime = resultsPage.getDurationTime_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.choosePassengerNationality();
        passengersDetailsPage.choosePassengerBirthCountry();
        passengersDetailsPage.enterPassportNumber(passportNumber);
        passengersDetailsPage.selectDepartureAisleOption();
        passengersDetailsPage.enterCity(city);
        passengersDetailsPage.enterStreet(street);
        passengersDetailsPage.enterPostalCode(postalCode);
        passengersDetailsPage.enterMobilePhone(phoneNumber);
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.passengersDataSubmitButtonClick();

        String finalDeparture = summaryPage.getDepartureStation_SummaryPage().strip();
        String finalArrival = summaryPage.getArrivalStation_SummaryPage().strip();
        String finalDepartureDate = summaryPage.getDepartureDate_SummaryPage().strip();
        String finalArrivalDate = summaryPage.getArrivalDate_SummaryPage().strip();
        String finalPrice = summaryPage.getPrice_SummaryPage().strip();
        String reservationPrice = summaryPage.getReservationPrice_SummaryPage().strip();
        String totalPrice = summaryPage.getFinalPrice_SummaryPage().strip();
        String orderCode = summaryPage.getOrderCode().strip();
        int orderCodeLength = orderCode.length();
        String fareSummaryPage = summaryPage.getFare().strip();
        String passengerFirstName = summaryPage.getPassengerName_SummaryPage().strip();
        String passengerSurname = summaryPage.getPassengerSurname_SummaryPage();
        String passengerBirthDay = summaryPage.getPassengerBirthDate().strip();
        String passengerEmail = summaryPage.getPassengerEmail().strip();

        Assertions.assertAll(
                () -> Assertions.assertTrue(finalDeparture.contains(departure.toUpperCase()), "Incorrect departure on summary page."),
                () -> Assertions.assertTrue(finalArrival.contains(arrival.toUpperCase()), "Incorrect arrival on summary page."),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, "Incorrect departure date and time on summary page."),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, "Incorrect arrival date and time on summary page."),
                () -> Assertions.assertEquals(price, finalPrice, "Incorrect price on summary page."),
                () -> Assertions.assertEquals("€0.00", reservationPrice, "Incorrect reservation price on summary page."),
                () -> Assertions.assertEquals(price, totalPrice, "Incorrect total price on summary page."),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), "Incorrect passenger first name on summary page."),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), "Incorrect passenger last name on summary page."),
                () -> Assertions.assertEquals("06/09/1985", passengerBirthDay, "Incorrect passenger birth day on summary page."),
                () -> Assertions.assertEquals(email, passengerEmail, "Incorrect passenger email on summary page."),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, "Incorrect fare on summary page."),
                () -> Assertions.assertEquals(6, orderCodeLength, "Incorrect length of the order code on summary page.")
        );

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.getByText("Thank you for purchase!").textContent();

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);
    }

    @Test
    @DisplayName("ACP provider for Spain with round trip.")
    @Description("Test checking e2e path on ACP provider for Spain with round trip.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    public void e2e_SAT_ACPRoundTrip_ForSpain_test() {
        mainPage.navigateToHomePage();
        mainPage.complementingTheOriginStations("Madrid");
        mainPage.complementingTheDestinationStations("Leon");
        mainPage.performSearch();
        mainPage.departureDate();
        mainPage.returnDate();
        mainPage.addOneAdultPassenger();
        mainPage.addOneYouthPassengerIn_17_YearsAge("17");
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();
        resultsPage.proceed();
        resultsPage.selectSecondSideConnection();
        resultsPage.proceedSecondSideConnection();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        // Click on the input-control passenger country element first Adult passenger
        passengersDetailsPage.choosePassengerCountry();
        // Click and select on the input-control nationality element first Adult passenger
        passengersDetailsPage.choosePassengerNationality();
        // Click and select on the input-control birth country element first Adult passenger
        passengersDetailsPage.choosePassengerBirthCountry();
        // Enter passport number first Adult passenger
        passengersDetailsPage.enterPassportNumber(passportNumber);

        // Click on the passenger male dropdown and select - Mrs second Adult passenger
        page.locator("#passenger-prefix").nth(1).selectOption("Mrs");

        // Click and enter first name second Adult passenger
        page.getByPlaceholder("First name").nth(1).click();
        page.getByPlaceholder("First name").nth(1).fill(firstNameSecondPassenger);

        // Click and enter last name second Adult passenger
        page.getByPlaceholder("Last name").nth(1).click();
        page.getByPlaceholder("Last name").nth(1).fill(lastNameSecondPassenger);

        // Click and enter birth day second Adult passenger
        page.getByPlaceholder("DD/MM/YYYY").nth(1).click();
        page.getByPlaceholder("DD/MM/YYYY").nth(1).fill("06/09/1985");

        // Click and select on the input-control second Adult passenger country
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .passenger-date-country .input-control");
        Locator countryItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .passenger-date-country .items-list > .ng-star-inserted:first-of-type");
        countryItemsList.click();

        // Click and select on the input-control nationality element second Adult passenger
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .nationality-country .input-control");
        Locator nationalityItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .nationality-country .items-list .ng-star-inserted:first-of-type");
        nationalityItemsList.click();


        // Click and select on the input-control birth country element second Adult passenger
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .birth-country .input-control");
        Locator birthCountryItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .birth-country .items-list .ng-star-inserted:first-of-type");
        birthCountryItemsList.click();

        // Enter passport number second Adult passenger
        page.getByPlaceholder("Passport Number").nth(1).click();
        page.getByPlaceholder("Passport Number").nth(1).fill(passportNumberSecondPassenger);

        // Select the second option - Mr - in the dropdown third passenger -Youth
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).locator("#passenger-prefix").selectOption("Mr");

        // Click and enter first name Youth passenger
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("First name").click();
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("First name").fill(firstNameThirdPassenger);

        // Click and enter last name Youth passenger
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("Last name").click();
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("Last name").fill(lastNameThirdPassenger);

        // Click and enter birth day Youth passenger (age 17)
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("DD/MM/YYYY").click();
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("DD/MM/YYYY").fill("06/09/2006");

        // Click and select on the input-control Youth passenger country
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .passenger-date-country .input-control");
        Locator countryItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .passenger-date-country .items-list > .ng-star-inserted:first-of-type");
        countryItemsListOfYouth.click();

        // Click and select on the input-control nationality element Youth passenger
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .nationality-country .input-control");
        Locator nationalityItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .nationality-country .items-list .ng-star-inserted:first-of-type");
        nationalityItemsListOfYouth.click();


        // Click and select on the input-control birth country element Youth passenger
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .birth-country .input-control");
        Locator birthCountryItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .birth-country .items-list .ng-star-inserted:first-of-type");
        birthCountryItemsListOfYouth.click();

        // Enter passport number Youth passenger
        page.getByPlaceholder("Passport Number").nth(2).click();
        page.getByPlaceholder("Passport Number").nth(2).fill(passportNumberThirdPassenger);

        // Seat preference outbound
        page.getByLabel("Outbound seat preferenceWindowAisle").selectOption("Window");

        // Seat preference inbound
        page.getByLabel("Inbound seat preferenceWindowAisle").selectOption("Aisle");

        // Enter city
        page.getByPlaceholder("City").click();
        page.getByPlaceholder("City").fill(city);

        // Enter address
        page.getByPlaceholder("Address").click();
        page.getByPlaceholder("Address").fill(street);

        // Enter postal code
        page.getByPlaceholder("Postal Code").click();
        page.getByPlaceholder("Postal Code").fill(postalCode);

        // Enter mobile
        page.getByPlaceholder("Mobile").click();
        page.getByPlaceholder("Mobile").fill(phoneNumber);

        // Enter an email in the contact-info-input form control
        page.getByPlaceholder("Email").click();
        page.getByPlaceholder("Email").fill(email);

        // Click on the "I agree, checkout" element
        page.click(".submit-button > .ng-star-inserted");

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.getByText("Thank you for purchase!").textContent();

        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }

    @ParameterizedTest
    @DisplayName("ACP provider for Sweden.")
    @Description("Test checking e2e path on ACP provider for Sweden.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Malmo Central Station, Stockholm Central Station"})
    public void e2e_SAT_ACPForSweden_test(String origin, String destination) {
        mainPage.navigateToHomePage();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.performSearch();
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();
        String departure = resultsPage.getDepartureStation_ResultPage().strip();
        String arrival = resultsPage.getArrivalStation_ResultPage();
        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime;
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();
        String durationTime = resultsPage.getDurationTime_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.choosePassengerNationality();
        passengersDetailsPage.choosePassengerBirthCountry();
        passengersDetailsPage.enterPassportNumber(passportNumber);
        passengersDetailsPage.selectDepartureAisleOption();
        passengersDetailsPage.enterCity(city);
        passengersDetailsPage.enterStreet(street);
        passengersDetailsPage.enterPostalCode(postalCode);
        passengersDetailsPage.enterMobilePhone(phoneNumber);
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.passengersDataSubmitButtonClick();

        String finalDeparture = summaryPage.getDepartureStation_SummaryPage().strip();
        String finalArrival = summaryPage.getArrivalStation_SummaryPage().strip();
        String finalDepartureDate = summaryPage.getDepartureDate_SummaryPage().strip();
        String finalArrivalDate = summaryPage.getArrivalDate_SummaryPage().strip();
        String finalPrice = summaryPage.getPrice_SummaryPage().strip();
        String reservationPrice = summaryPage.getReservationPrice_SummaryPage().strip();
        String totalPrice = summaryPage.getFinalPrice_SummaryPage().strip();
        String orderCode = summaryPage.getOrderCode().strip();
        int orderCodeLength = orderCode.length();
        String fareSummaryPage = summaryPage.getFare().strip();
        String passengerFirstName = summaryPage.getPassengerName_SummaryPage().strip();
        String passengerSurname = summaryPage.getPassengerSurname_SummaryPage();
        String passengerBirthDay = summaryPage.getPassengerBirthDate().strip();
        String passengerEmail = summaryPage.getPassengerEmail().strip();

        Assertions.assertAll(
                () -> Assertions.assertTrue(finalDeparture.contains(departure.toUpperCase()), "Incorrect departure on summary page."),
                () -> Assertions.assertTrue(finalArrival.contains(arrival.toUpperCase()), "Incorrect arrival on summary page."),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, "Incorrect departure date and time on summary page."),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, "Incorrect arrival date and time on summary page."),
                () -> Assertions.assertEquals(price, finalPrice, "Incorrect price on summary page."),
                () -> Assertions.assertEquals("€0.00", reservationPrice, "Incorrect reservation price on summary page."),
                () -> Assertions.assertEquals(price, totalPrice, "Incorrect total price on summary page."),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), "Incorrect passenger first name on summary page."),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), "Incorrect passenger last name on summary page."),
                () -> Assertions.assertEquals("06/09/1985", passengerBirthDay, "Incorrect passenger birth day on summary page."),
                () -> Assertions.assertEquals(email, passengerEmail, "Incorrect passenger email on summary page."),
                () -> Assertions.assertEquals(fare, fareSummaryPage, "Incorrect fare on summary page."),
                () -> Assertions.assertEquals(6, orderCodeLength, "Incorrect length of the order code on summary page.")
        );

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.getByText("Thank you for purchase!").textContent();

        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }
}
