package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
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
    @CsvSource({"Madrid, Leon", "Cadiz, Madrid", "Madrid, Valencia", "Leon, Madrid"})
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

        // First passenger
        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.choosePassengerNationality();
        passengersDetailsPage.choosePassengerBirthCountry();
        passengersDetailsPage.enterPassportNumber(passportNumber);

        // Second passenger
        passengersDetailsPage.secondPassengerPrefixDropdown("Mrs");
        passengersDetailsPage.enter_SecondAdultPassenger_FirstAndLastName(firstNameSecondPassenger, lastNameSecondPassenger);
        passengersDetailsPage.enter_SecondAdultPassenger_birthDate("06/09/1985");
        passengersDetailsPage.choose_SecondAdultPassenger_Country();
        passengersDetailsPage.chooseSecondPassengerNationality();
        passengersDetailsPage.chooseSecondPassengerBirthCountry();
        passengersDetailsPage.enterSecondPassengerPassportNumber(passportNumberSecondPassenger);

        // Third passenger
        passengersDetailsPage.thirdPassengerPrefixDropdown("Mr");
        passengersDetailsPage.enter_YouthPassenger_FirstAndLastName(firstNameThirdPassenger, lastNameThirdPassenger);
        passengersDetailsPage.enter_YouthPassenger_birthDate("06/09/2006");
        passengersDetailsPage.choose_YouthPassenger_Country();
        passengersDetailsPage.chooseYouthPassengerNationality();
        passengersDetailsPage.chooseYouthPassengerBirthCountry();
        passengersDetailsPage.enterThirdPassengerPassportNumber(passportNumberThirdPassenger);

        // Seat preference outbound
        page.getByLabel("Outbound seat preferenceWindowAisle").selectOption("Window");

        // Seat preference inbound
        page.getByLabel("Inbound seat preferenceWindowAisle").selectOption("Aisle");

        // Enter city
        passengersDetailsPage.enterCity(city);

        // Enter address
        passengersDetailsPage.enterStreet(street);

        // Enter postal code
        passengersDetailsPage.enterPostalCode(postalCode);

        // Enter mobile
        passengersDetailsPage.enterMobilePhone(phoneNumber);

        // Enter an email in the contact-info-input form control
        passengersDetailsPage.enterEmail(email);

        // Click on the "I agree, checkout" element
        passengersDetailsPage.passengersDataSubmitButtonClick();

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.getByText("Thank you for purchase!").textContent();

        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }

    @Disabled("Disabled until stations from Sweden will be imported.")
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
