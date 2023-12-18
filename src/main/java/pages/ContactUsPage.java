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

    private void navigateToContactUsPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on about us tab
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
}
