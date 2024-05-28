package pages;

import com.microsoft.playwright.Page;

/**
 * The PrivacyPolicyPage class represents the privacy policy page of a web application.
 * It provides methods to navigate to the privacy policy page and interact with its elements.
 */
public class PrivacyPolicyPage {

    public Page page;

    public PrivacyPolicyPage(Page page) {
        this.page = page;
    }

    public String getPrivacyPolicyTitle() {
        String privacyPolicyPageTitle = page.title();
        System.out.println("page title: " + privacyPolicyPageTitle);
        return privacyPolicyPageTitle;
    }

    public String getPrivacyPolicyURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
