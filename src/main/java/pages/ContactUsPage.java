package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The ContactUsPage class represents a page that allows users to navigate to the contact us page.
 */
public class ContactUsPage {
    private final Page page;

    public ContactUsPage(Page page) {
        this.page = page;
    }

    public void navigateToContactUsPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.click("id=contact-us");
    }

    public String getContactUsPageTitle() {
        navigateToContactUsPage();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getContactUsPageURL() {
        navigateToContactUsPage();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
    public void fillContactUsFormWithoutCompanyName(String name, String lastName, String email, String message) {
        page.fill("id=first_name", name);
        page.fill("id=last_name", lastName);
        page.fill("id=email", email);
        page.fill("id=message", message);
    }

    public void submitContactUsForm() {
        page.click("xpath=//input[@type='submit']");
    }
}
