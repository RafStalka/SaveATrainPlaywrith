package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

/**
 * This class represents the page where passenger details are entered.
 */
public class PassengersDetailsPage {

    private static final String PASSENGER_DROPDOWN_SELECTOR = ".outbound-seat-select > .form-control";
    private static final String AISLE_SELECTOR = ".ng-pristine";
    private static final String FIRST_PASSENGER_PREFIX_SELECTOR = "(//select[@id='passenger-prefix'])[1]";
    private static final String SECOND_PASSENGER_PREFIX_SELECTOR = "(//select[@id='passenger-prefix'])[2]";
    private static final String THIRD_PASSENGER_PREFIX_SELECTOR = "(//select[@id='passenger-prefix'])[3]";
    private static final String FIRST_PASSENGER_FIRST_NAME_INPUT_SELECTOR = "(//input[@id='passenger-fname'])[1]";
    private static final String SECOND_PASSENGER_FIRST_NAME_INPUT_SELECTOR = "(//input[@id='passenger-fname'])[2]";
    private static final String FIRST_YOUTH_PASSENGER_FIRST_NAME_SELECTOR = "(//input[@id='passenger-fname'])[3]";
    private static final String FIRST_PASSENGER_LAST_NAME_INPUT_SELECTOR = "(//input[@id='passenger-lname'])[1]";
    private static final String SECOND_PASSENGER_LAST_NAME_SELECTOR = "(//input[@id='passenger-lname'])[2]";
    private static final String FIRST_YOUTH_PASSENGER_LAST_NAME_SELECTOR = "(//input[@id='passenger-lname'])[3]";
    private static final String FIRST_PASSENGER_BIRTH_DATE_SELECTOR = "(//input[@id='passenger-date'])[1]";
    private static final String SECOND_PASSENGER_BIRTHDATE_SELECTOR = "(//input[@id='passenger-date'])[2]";
    private static final String FIRST_YOUTH_PASSENGER_BIRTHDATE_SELECTOR = "(//input[@id='passenger-date'])[3]";
    private static final String CITY_INPUT_SELECTOR = "//input[@placeholder='City']";
    private static final String STREET_INPUT_SELECTOR = "//input[@placeholder='Address']";
    private static final String POSTAL_CODE_INPUT_SELECTOR = "//input[@placeholder='Postal Code']";
    private static final String MOBILE_PHONE_INPUT_SELECTOR = "id=phobe-number";
    private static final String EMAIL_INPUT_SELECTOR = "//input[@placeholder='Email']";
    private static final String SUBMIT_BUTTON_SELECTOR = ".submit-button > .ng-star-inserted";
    public static final String FIRST_PASSENGER_PASSPORT_INPUT_SELECTOR = "(//input[@id='passenger-passport'])[1]";
    public static final String SECOND_PASSENGER_PASSPORT_INPUT_SELECTOR = "(//input[@id='passenger-passport'])[2]";
    public static final String FIRST_YOUTH_PASSENGER_PASSPORT_INPUT_SELECTOR = "(//input[@id='passenger-passport'])[3]";
    private Page page;

    public PassengersDetailsPage(Page page) {
        this.page = page;
    }

    public void selectFirstPassengerPrefix(String maleValue) {
        page.locator(FIRST_PASSENGER_PREFIX_SELECTOR).selectOption(maleValue);
    }

    public void secondPassengerPrefixDropdown(String maleValueSecondAdult) {
        page.locator(SECOND_PASSENGER_PREFIX_SELECTOR).selectOption(maleValueSecondAdult);
    }

    public void thirdPassengerPrefixDropdown(String maleValueThirdPassenger) {
        page.locator(THIRD_PASSENGER_PREFIX_SELECTOR).selectOption(maleValueThirdPassenger);
    }

    public void enterFirstAndLastName(String firstName, String lastName) {
        page.fill(FIRST_PASSENGER_FIRST_NAME_INPUT_SELECTOR, firstName);
        page.fill(FIRST_PASSENGER_LAST_NAME_INPUT_SELECTOR, lastName);
    }

    public void enter_SecondAdultPassenger_FirstAndLastName(String secondAdultPassenger_firstName, String secondAdultPassenger_lastName) {
        page.fill(SECOND_PASSENGER_FIRST_NAME_INPUT_SELECTOR, secondAdultPassenger_firstName);
        page.fill(SECOND_PASSENGER_LAST_NAME_SELECTOR, secondAdultPassenger_lastName);
    }

    public void enter_YouthPassenger_FirstAndLastName(String youthPassenger_firstName, String youthPassenger_lastName) {
        page.fill(FIRST_YOUTH_PASSENGER_FIRST_NAME_SELECTOR, youthPassenger_firstName);
        page.fill(FIRST_YOUTH_PASSENGER_LAST_NAME_SELECTOR, youthPassenger_lastName);
    }

    public void enterBirthDate(String birthDate) {
        page.fill(FIRST_PASSENGER_BIRTH_DATE_SELECTOR, birthDate);
    }

    public void enter_SecondAdultPassenger_birthDate(String secondAdultPassenger_birthDate) {
        page.fill(SECOND_PASSENGER_BIRTHDATE_SELECTOR, secondAdultPassenger_birthDate);
    }

    public void enter_YouthPassenger_birthDate(String youthPassenger_birthDate) {
        page.fill(FIRST_YOUTH_PASSENGER_BIRTHDATE_SELECTOR, youthPassenger_birthDate);
    }

    public void choosePassengerCountry() {
        page.getByPlaceholder("Select country").first().click();
        page.getByRole(AriaRole.MAIN).getByRole(AriaRole.LIST).locator("li").
                filter(new Locator.FilterOptions().setHasText("Afghanistan")).click();
    }

    public void choose_SecondAdultPassenger_Country() {
        page.getByPlaceholder("Select country").nth(1).click();
        page.getByRole(AriaRole.MAIN).getByRole(AriaRole.LIST).locator("li").
                filter(new Locator.FilterOptions().setHasText("Afghanistan")).click();
    }

    public void choose_YouthPassenger_Country() {
        page.getByPlaceholder("Select country").nth(2).click();
        page.getByRole(AriaRole.MAIN).getByRole(AriaRole.LIST).locator("li").
                filter(new Locator.FilterOptions().setHasText("Afghanistan")).click();
    }

    public void choosePassengerNationality() {
        page.click(".ng-star-inserted:nth-child(1) > sat-passenger-info .nationality-country .input-control");
        Locator nationalityList = page.locator(".ng-star-inserted:nth-child(1) > sat-passenger-info .nationality-country .items-list .ng-star-inserted:first-of-type");
        nationalityList.click();
    }

    public void chooseSecondPassengerNationality() {
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .nationality-country .input-control");
        Locator nationalityItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .nationality-country .items-list .ng-star-inserted:first-of-type");
        nationalityItemsList.click();
    }

    public void chooseYouthPassengerNationality() {
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .nationality-country .input-control");
        Locator nationalityItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .nationality-country .items-list .ng-star-inserted:first-of-type");
        nationalityItemsListOfYouth.click();
    }

    public void choosePassengerBirthCountry() {
        page.click(".ng-star-inserted:nth-child(1) > sat-passenger-info .birth-country .input-control");
        Locator birthCountryList = page.locator(".ng-star-inserted:nth-child(1) > sat-passenger-info .birth-country .items-list .ng-star-inserted:first-of-type");
        birthCountryList.click();
    }

    public void chooseSecondPassengerBirthCountry() {
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .birth-country .input-control");
        Locator birthCountryItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .birth-country .items-list .ng-star-inserted:first-of-type");
        birthCountryItemsList.click();
    }

    public void chooseYouthPassengerBirthCountry() {
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .birth-country .input-control");
        Locator birthCountryItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .birth-country .items-list .ng-star-inserted:first-of-type");
        birthCountryItemsListOfYouth.click();
    }

    public void enterPassportNumber(String passportNumber) {
        page.fill(FIRST_PASSENGER_PASSPORT_INPUT_SELECTOR, passportNumber);
    }

    public void enterSecondPassengerPassportNumber(String passportNumber) {
        page.fill(SECOND_PASSENGER_PASSPORT_INPUT_SELECTOR, passportNumber);
    }

    public void enterThirdPassengerPassportNumber(String passportNumber) {
        page.fill(FIRST_YOUTH_PASSENGER_PASSPORT_INPUT_SELECTOR, passportNumber);
    }

    public void selectDepartureAisleOption() {
        Locator passengerDropdown = page.locator(PASSENGER_DROPDOWN_SELECTOR);
        selectDropdownOptionByIndex(passengerDropdown, 1);
        page.click(AISLE_SELECTOR);
    }

    private void selectDropdownOptionByIndex(Locator locator, int index) {
        if (locator.isVisible()) {
            locator.selectOption(new SelectOption().setIndex(index));
        }
    }

    public void enterCity(String city) {
        page.fill(CITY_INPUT_SELECTOR, city);
    }

    public void enterStreet( String street) {
        page.fill(STREET_INPUT_SELECTOR, street);
    }

    public void enterPostalCode(String postalCode) {
        page.fill(POSTAL_CODE_INPUT_SELECTOR, postalCode);
    }

    public void enterMobilePhone(String phoneNumber) {
        page.fill(MOBILE_PHONE_INPUT_SELECTOR, phoneNumber);
    }

    public void enterEmail(String email) {
        page.fill(EMAIL_INPUT_SELECTOR, email);
    }

    public void passengersDataSubmitButtonClick() {
        page.click(SUBMIT_BUTTON_SELECTOR);
        page.waitForTimeout(5000);
    }
}
