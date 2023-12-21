package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

public class PassengersDetailsPage {
    private Page page;
    private final static String PASSENGER_DROPDOWN_SELECTOR = ".outbound-seat-select > .form-control";
    private final static String NG_PRISTINE_SELECTOR = ".ng-pristine";

    public PassengersDetailsPage(Page page) {
        this.page = page;
    }

    public void selectFirstPassengerPrefix(String maleValue) {
        page.locator("(//select[@id='passenger-prefix'])[1]").selectOption(maleValue);
    }

    public void secondPassengerPrefixDropdown(String maleValueSecondAdult) {
        page.locator("(//select[@id='passenger-prefix'])[2]").selectOption(maleValueSecondAdult);
    }

    public void thirdPassengerPrefixDropdown(String maleValueThirdPassenger) {
        page.locator("(//select[@id='passenger-prefix'])[3]").selectOption(maleValueThirdPassenger);
    }

    public void enterFirstAndLastName(String firstName, String lastName) {
        page.fill("(//input[@id='passenger-fname'])[1]", firstName);
        page.fill("(//input[@id='passenger-lname'])[1]", lastName);
    }

    public void enter_SecondAdultPassenger_FirstAndLastName(String secondAdultPassender_firstName, String secondAdultPassenger_lastName) {
        page.fill("(//input[@id='passenger-fname'])[2]", secondAdultPassender_firstName);
        page.fill("(//input[@id='passenger-lname'])[2]", secondAdultPassenger_lastName);
    }

    public void enter_YouthPassenger_FirstAndLastName(String youthPassender_firstName, String youthPassenger_lastName) {
        page.fill("(//input[@id='passenger-fname'])[3]", youthPassender_firstName);
        page.fill("(//input[@id='passenger-lname'])[3]", youthPassenger_lastName);
    }

    public void enterbirthDate(String birthDate) {
        page.fill("(//input[@id='passenger-date'])[1]", birthDate);
    }

    public void enter_SecondAdultPassenger_birthDate(String secondAdultPassenger_birthDate) {
        page.fill("(//input[@id='passenger-date'])[2]", secondAdultPassenger_birthDate);
    }

    public void enter_YouthPassenger_birthDate(String youthPassenger_birthDate) {
        page.fill("(//input[@id='passenger-date'])[3]", youthPassenger_birthDate);
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
        // Enter passport number
        page.fill("id=passenger-passport", passportNumber);
    }

    public void selectDepartureAisleOption() {
        Locator passengerDropdown = page.locator(PASSENGER_DROPDOWN_SELECTOR);
        selectDropdownOptionByIndex(passengerDropdown, 1);
        page.click(NG_PRISTINE_SELECTOR);
    }

    private void selectDropdownOptionByIndex(Locator locator, int index) {
        if (locator.isVisible()) {
            locator.selectOption(new SelectOption().setIndex(index));
        }
    }

    public void enterCity(String city) {
        // Enter city
        page.fill("//input[@placeholder='City']", city);
    }

    public void enterStreet( String street) {
        // Enter address
        page.fill("//input[@placeholder='Address']", street);
    }

    public void enterPostalCode(String postalCode) {
        // Enter postal code
        page.fill("//input[@placeholder='Postal Code']", postalCode);
    }

    public void enterMobilePhone(String phoneNumber) {
        // Enter mobile
        page.fill("//input[@placeholder='Mobile']", phoneNumber);
    }

    public void enterEmail(String email) {
        // Enter an email in the contact-info-input form control
        page.fill("//input[@placeholder='Email']", email);
    }

    public void passengersDataSubmitButtonClick() {
        // Click on the submit-button element
        page.click(".submit-button > .ng-star-inserted");
        // Sleep for 7 seconds
        page.waitForTimeout(5000);
    }
}
