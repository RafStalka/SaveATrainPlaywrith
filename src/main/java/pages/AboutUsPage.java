package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class AboutUsPage {
    public Page page;

    public AboutUsPage(Page page) {
        this.page = page;
    }

    private void navigateToAboutUs() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on about us tab
        page.click("id=about-us");
    }

    public String getAboutUsPageTitle() {
        navigateToAboutUs();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getAboutUsPageURL() {
        navigateToAboutUs();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }

    public String readMoreButtonFunctionality() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // Click on Read More button
        page.click(".services-body-desktop > .more-button");
        // Checking title
        String readMoreRedirectedPageTitle = page.title();
        System.out.println("page title: " + readMoreRedirectedPageTitle);
        return readMoreRedirectedPageTitle;
    }
}
