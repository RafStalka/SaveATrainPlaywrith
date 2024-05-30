package pages;

import com.microsoft.playwright.Page;

/**
 * The ContactUsPage class represents a page that allows users to navigate to the contact us page.
 */
public class ContactUsPage {

    private static final String FIRST_NAME_INPUT_SELECTOR = "id=first_name";
    private static final String LAST_NAME_INPUT_SELECTOR = "id=last_name";
    private static final String EMAIL_INPUT_SELECTOR = "id=email";
    private static final String MESSAGE_INPUT_SELECTOR = "id=message";
    private static final String SUBMIT_BUTTON_SELECTOR = "xpath=//input[@type='submit']";
    private static final String CONTACT_US_FORM_ERROR_MESSAGE_EMPTY_INPUT = "xpath=//*[@id='message']//../div/span/span";
    private static final String CONTACT_US_FORM_ERROR_FIRST_NAME_EMPTY_INPUT = "xpath=//*[@id='first_name']//../div/span/span";
    private static final String CONTACT_US_FORM_ERROR_LAST_NAME_EMPTY_INPUT = "xpath=//*[@id='last_name']//../div/span/span";
    private static final String CONTACT_US_FORM_ERROR_EMAIL_EMPTY_INPUT = "xpath=//*[@id='email']//../div/span/span";
    private static final String CONTACT_US_SUCCESS_MESSAGE_SELECTOR = "xpath=/html/body/sat-root/div/main/sat-help-page/sat-contact-us/div[1]/p";

    private final Page page;

    public ContactUsPage(Page page) {
        this.page = page;
    }


    public String getContactUsPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getContactUsPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }

    public void fillContactUsForm(String name, String lastName, String email, String message) {
        page.fill(FIRST_NAME_INPUT_SELECTOR, name);
        page.fill(LAST_NAME_INPUT_SELECTOR, lastName);
        page.fill(EMAIL_INPUT_SELECTOR, email);
        page.fill(MESSAGE_INPUT_SELECTOR, message);
    }

    public void submitContactUsForm() {
        page.click(SUBMIT_BUTTON_SELECTOR);
    }

    public String getContactUsFormErrorFromEmptyMessageInputField() {
        return page.textContent(CONTACT_US_FORM_ERROR_MESSAGE_EMPTY_INPUT);
    }

    public String getContactUsFormErrorFromEmptyFirstNameInputField() {
        return page.textContent(CONTACT_US_FORM_ERROR_FIRST_NAME_EMPTY_INPUT);
    }

    public String getContactUsFormErrorFromEmptyLastNameInputField() {
        return page.textContent(CONTACT_US_FORM_ERROR_LAST_NAME_EMPTY_INPUT);
    }

    public String getContactUsFormErrorFromEmptyEmailInputField() {
        return page.textContent(CONTACT_US_FORM_ERROR_EMAIL_EMPTY_INPUT);
    }

    public String getSuccessMessage() {
        return page.textContent(CONTACT_US_SUCCESS_MESSAGE_SELECTOR);
    }
}
