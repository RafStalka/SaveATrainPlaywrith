package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.MainPage;
import pages.PassengersDetailsPage;
import pages.ResultsPage;
import pages.SummaryPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static io.qameta.allure.SeverityLevel.CRITICAL;

/**
 * This class represents the end-to-end ACP provider tests for the Save A Train application.
 * It extends the PlaywrightTestBase class and inherits its protected fields and methods.
 */
public class SaveATrainE2EACPTests extends PlaywrightTestBase {

    public static final String BIRTH_DATE = "06/09/1985";
    public static final String MR = "Mr";
    public static final String YOUTH_AGE = "17";
    public static final String MRS = "Mrs";
    public static final String ZERO = "€0.00";
    public static final String INCORRECT_DEPARTURE_ON_SUMMARY_PAGE = "Incorrect departure on summary page.";
    public static final String INCORRECT_ARRIVAL_ON_SUMMARY_PAGE = "Incorrect arrival on summary page.";
    public static final String INCORRECT_DEPARTURE_DATE_AND_TIME_ON_SUMMARY_PAGE = "Incorrect departure date and time on summary page.";
    public static final String INCORRECT_ARRIVAL_DATE_AND_TIME_ON_SUMMARY_PAGE = "Incorrect arrival date and time on summary page.";
    public static final String INCORRECT_PRICE_ON_SUMMARY_PAGE = "Incorrect price on summary page.";
    public static final String INCORRECT_RESERVATION_PRICE_ON_SUMMARY_PAGE = "Incorrect reservation price on summary page.";
    public static final String INCORRECT_TOTAL_PRICE_ON_SUMMARY_PAGE = "Incorrect total price on summary page.";
    public static final String INCORRECT_PASSENGER_FIRST_NAME_ON_SUMMARY_PAGE = "Incorrect passenger first name on summary page.";
    public static final String INCORRECT_PASSENGER_LAST_NAME_ON_SUMMARY_PAGE = "Incorrect passenger last name on summary page.";
    public static final String INCORRECT_PASSENGER_BIRTH_DAY_ON_SUMMARY_PAGE = "Incorrect passenger birth day on summary page.";
    public static final String INCORRECT_PASSENGER_EMAIL_ON_SUMMARY_PAGE = "Incorrect passenger email on summary page.";
    public static final String INCORRECT_FARE_ON_SUMMARY_PAGE = "Incorrect fare on summary page.";
    public static final String INCORRECT_LENGTH_OF_THE_ORDER_CODE_ON_SUMMARY_PAGE = "Incorrect length of the order code on summary page.";

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
    @CsvSource({"Madrid, Toledo", "Cadiz, Madrid", "Madrid, Valencia", "Toledo, Madrid"})
    public void e2e_SAT_ACPForSpain_test(String origin, String destination) {
        mainPage.navigateToHomePage();
        mainPage.performSearch();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        String departureFirst = resultsPage.getDepartureStationFirstField_ResultPage().trim();

        String arrival = null;
        try {
            Locator arrivalSecond = page.locator("id=arrival-transfer-station-name-1-2");
            if (arrivalSecond.isVisible()) {
                arrival = resultsPage.getArrivalStationSecondField_ResultPage().strip(); // second
            } else {
                arrival = resultsPage.getArrivalStationFirstField_ResultPage().strip(); // first
            }
        } catch (Exception e) {
            System.err.println("Timeout while waiting for the element: " + e.getMessage());
        }

        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix(MR);
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterFirstAdultBirthDate(27);
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

        boolean compareDeparture = Objects.equals(departureFirst != null ?
                departureFirst.trim().toLowerCase() : null , finalDeparture != null ?
                finalDeparture.trim().toLowerCase() : null);
        boolean compareArrival = Objects.equals(arrival != null ? arrival.trim().toLowerCase() : null,
                finalArrival != null ? finalArrival.trim().toLowerCase() : null);

        Assertions.assertAll(
                () -> Assertions.assertTrue(compareDeparture, INCORRECT_DEPARTURE_ON_SUMMARY_PAGE),
                () -> Assertions.assertTrue(compareArrival, INCORRECT_ARRIVAL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, INCORRECT_DEPARTURE_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, INCORRECT_ARRIVAL_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(price, finalPrice, INCORRECT_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(ZERO, reservationPrice, INCORRECT_RESERVATION_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(price, totalPrice, INCORRECT_TOTAL_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), INCORRECT_PASSENGER_FIRST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), INCORRECT_PASSENGER_LAST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(expectedDateOfBirth(27), passengerBirthDay, INCORRECT_PASSENGER_BIRTH_DAY_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(email, passengerEmail, INCORRECT_PASSENGER_EMAIL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, INCORRECT_FARE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(6, orderCodeLength, INCORRECT_LENGTH_OF_THE_ORDER_CODE_ON_SUMMARY_PAGE)
        );

        System.out.println(page.url());

        summaryPage.completingAdyenForm();
    }

    @Test
    @DisplayName("ACP provider for Spain with round trip.")
    @Description("Test checking e2e path on ACP provider for Spain with round trip.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    public void e2e_SAT_ACPRoundTrip_ForSpain_test() {
        mainPage.navigateToHomePage();
        mainPage.performSearch();
        mainPage.complementingTheOriginStations("Madrid");
        mainPage.complementingTheDestinationStations("Toledo");
        mainPage.departureDate();
        mainPage.returnDate();
        mainPage.addOneAdultPassenger();
        mainPage.addOneYouthPassengerIn_17_YearsAge(YOUTH_AGE);
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();
        resultsPage.proceed();
        resultsPage.selectSecondSideConnection();
        resultsPage.proceedSecondSideConnection();

        // First passenger
        passengersDetailsPage.selectFirstPassengerPrefix(MR);
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterFirstAdultBirthDate(27);
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.choosePassengerNationality();
        passengersDetailsPage.choosePassengerBirthCountry();
        passengersDetailsPage.enterPassportNumber(passportNumber);

        // Second passenger
        passengersDetailsPage.secondPassengerPrefixDropdown(MRS);
        passengersDetailsPage.enter_SecondAdultPassenger_FirstAndLastName(firstNameSecondPassenger, lastNameSecondPassenger);
        passengersDetailsPage.enterSecondAdultPassengerBirthDate(28);
        passengersDetailsPage.choose_SecondAdultPassenger_Country();
        passengersDetailsPage.chooseSecondPassengerNationality();
        passengersDetailsPage.chooseSecondPassengerBirthCountry();
        passengersDetailsPage.enterSecondPassengerPassportNumber(passportNumberSecondPassenger);

        // Third passenger
        passengersDetailsPage.thirdPassengerPrefixDropdown(MR);
        passengersDetailsPage.enter_YouthPassenger_FirstAndLastName(firstNameThirdPassenger, lastNameThirdPassenger);
        passengersDetailsPage.enterYouthPassengerBirthDate(17);

        passengersDetailsPage.choose_YouthPassenger_Country();
        passengersDetailsPage.chooseYouthPassengerNationality();
        passengersDetailsPage.chooseYouthPassengerBirthCountry();
        passengersDetailsPage.enterThirdPassengerPassportNumber(passportNumberThirdPassenger);

        // Seat preference outbound
        page.getByLabel("Outbound seat preference").selectOption("Window");

        // Seat preference inbound
        page.getByLabel("Inbound seat").selectOption("Aisle");

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

        passengersDetailsPage.passengersDataSubmitButtonClick();

        page.waitForLoadState(LoadState.LOAD);
        System.out.println(page.url());

        summaryPage.completingAdyenForm();
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

        String departureFirst = resultsPage.getDepartureStationFirstField_ResultPage().trim();

        String arrival = null;
        try {
            Locator arrivalSecond = page.locator("id=arrival-transfer-station-name-1-2");
            if (arrivalSecond.isVisible()) {
                arrival = resultsPage.getArrivalStationSecondField_ResultPage().strip(); // second
            } else {
                arrival = resultsPage.getArrivalStationFirstField_ResultPage().strip(); // first
            }
        } catch (Exception e) {
            System.err.println("Timeout while waiting for the element: " + e.getMessage());
        }

        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime;
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix(MR);
        System.out.println(page.url());
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterFirstAdultBirthDate(27);
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

        page.waitForLoadState(LoadState.LOAD);

        boolean compareDeparture = Objects.equals(departureFirst != null ?
                departureFirst.trim().toLowerCase() : null , finalDeparture != null ?
                finalDeparture.trim().toLowerCase() : null);
        boolean compareArrival = Objects.equals(arrival != null ? arrival.trim().toLowerCase() : null,
                finalArrival != null ? finalArrival.trim().toLowerCase() : null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(compareDeparture).as(INCORRECT_DEPARTURE_ON_SUMMARY_PAGE).isTrue();
        softly.assertThat(compareArrival).as(INCORRECT_ARRIVAL_ON_SUMMARY_PAGE).isTrue();
        softly.assertThat(departureDate).withFailMessage(INCORRECT_DEPARTURE_DATE_AND_TIME_ON_SUMMARY_PAGE).isEqualTo(finalDepartureDate);
        softly.assertThat(arrivalDate).withFailMessage(INCORRECT_ARRIVAL_DATE_AND_TIME_ON_SUMMARY_PAGE).isEqualTo(finalArrivalDate);
        softly.assertThat(price).withFailMessage(INCORRECT_PRICE_ON_SUMMARY_PAGE).isEqualTo(finalPrice);
        softly.assertThat(reservationPrice).withFailMessage(INCORRECT_RESERVATION_PRICE_ON_SUMMARY_PAGE).isEqualTo(ZERO);
        softly.assertThat(totalPrice).withFailMessage(INCORRECT_TOTAL_PRICE_ON_SUMMARY_PAGE).isEqualTo(price);
        softly.assertThat(passengerFirstName).withFailMessage(INCORRECT_PASSENGER_FIRST_NAME_ON_SUMMARY_PAGE).isEqualTo(firstName.toUpperCase());
        softly.assertThat(passengerSurname).withFailMessage(INCORRECT_PASSENGER_LAST_NAME_ON_SUMMARY_PAGE).isEqualTo(lastName.toUpperCase());
        softly.assertThat(expectedDateOfBirth(27)).withFailMessage(INCORRECT_PASSENGER_BIRTH_DAY_ON_SUMMARY_PAGE).isEqualTo(passengerBirthDay);
        softly.assertThat(email).withFailMessage(INCORRECT_PASSENGER_EMAIL_ON_SUMMARY_PAGE).isEqualTo(passengerEmail);
        softly.assertThat(fare.toUpperCase()).withFailMessage(INCORRECT_FARE_ON_SUMMARY_PAGE).isEqualTo(fareSummaryPage);
        softly.assertThat(6).withFailMessage(INCORRECT_LENGTH_OF_THE_ORDER_CODE_ON_SUMMARY_PAGE).isEqualTo(orderCodeLength);

        System.out.println(page.url());
        summaryPage.completingAdyenForm();
    }

    public static String expectedDateOfBirth(int dateOfBirth) {
        LocalDate expectedDateOfBirth = LocalDate.now().minusYears(dateOfBirth);
        return expectedDateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
