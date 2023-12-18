package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The TermsAndConditionsPage class represents a page that contains terms and conditions.
 */
public class TermsAndConditionsPage {
    public Page page;
    private static final String TC_BUTTON_SELECTOR = "a:nth-of-type(2) > p";

    public TermsAndConditionsPage(Page page) {
        this.page = page;
    }

    public String termsAndConditionsButtonFunctionality() {
        navigateToSATHomePage();
        clickOnTermsAndConditionsButton();
        return getPageTitle();
    }

    private void navigateToSATHomePage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
    }

    private void clickOnTermsAndConditionsButton() {
        page.click(TC_BUTTON_SELECTOR);
    }

    private String getPageTitle() {
        String pageTitle = page.title();
        printPageTitle(pageTitle);
        return pageTitle;
    }

    private void printPageTitle(String title) {
        System.out.println("page title: " + title);
    }
}
