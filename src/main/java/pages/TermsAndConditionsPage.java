package pages;

import com.microsoft.playwright.Page;

/**
 * The TermsAndConditionsPage class represents a page that contains terms and conditions.
 */
public class TermsAndConditionsPage {

    public Page page;

    public TermsAndConditionsPage(Page page) {
        this.page = page;
    }

    public String getTermsAndConditionsPageTitle() {
        String pageTitle = page.title();
        System.out.println("page title: " + pageTitle);
        return pageTitle;
    }

    public String getTermsAndConditionsURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
