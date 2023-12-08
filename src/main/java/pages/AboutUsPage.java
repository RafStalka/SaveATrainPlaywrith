package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The class represents the About Us page of the application.
 * It provides methods to retrieve information about the page title, URL, and the functionality of the "Read More" button.
 */
public class AboutUsPage {
    private static final String ABOUT_US_TAB_SELECTOR = "id=about-us";
    private static final String READ_MORE_BUTTON_SELECTOR = ".services-body-desktop > .more-button";

    public Page page;

    public AboutUsPage(Page page) {
        this.page = page;
    }

    private void navigateToAboutUs() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on about us tab
        page.click(ABOUT_US_TAB_SELECTOR);
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
        page.click(READ_MORE_BUTTON_SELECTOR);
        // Checking title
        String readMoreRedirectedPageTitle = page.title();
        System.out.println("page title: " + readMoreRedirectedPageTitle);
        return readMoreRedirectedPageTitle;
    }

}
