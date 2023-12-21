package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The PrivacyPolicyPage class represents the privacy policy page of a web application.
 * It provides methods to navigate to the privacy policy page and interact with its elements.
 */
public class PrivacyPolicyPage {
    private static final String PRIVACY_POLICY_BUTTON_SELECTOR = "a:nth-of-type(1) > p";
    public Page page;

    public PrivacyPolicyPage(Page page) {
        this.page = page;
    }

    public String navigateToPrivacyPolicyPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        clickPrivacyPolicyButton();
        String privacyPolicyPageTitle = page.title();
        printPageTitle(privacyPolicyPageTitle);
        return privacyPolicyPageTitle;
    }

    private void clickPrivacyPolicyButton() {
        page.click(PRIVACY_POLICY_BUTTON_SELECTOR);
    }

    private void printPageTitle(String title) {
        System.out.println("page title: " + title);
    }
    public String getPrivacyPolicyURL() {
        navigateToPrivacyPolicyPage();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
