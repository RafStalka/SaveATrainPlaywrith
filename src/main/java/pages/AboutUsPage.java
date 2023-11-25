package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class AboutUsPage {

    public Page page;

    public AboutUsPage(Page page) {
        this.page = page;
    }

    public String getAboutUsPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on about us tab
        page.click("id=about-us");
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }
}
