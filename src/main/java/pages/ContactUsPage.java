package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class ContactUsPage {

    public Page page;

    public ContactUsPage(Page page) {
        this.page = page;
    }

    public String getContactUsPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on about us tab
        page.click("id=contact-us");
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }
}
