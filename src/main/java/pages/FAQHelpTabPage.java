package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The FAQHelpTabPage class represents the FAQ help tab page in a web application.
 * It provides methods to retrieve the title and URL of the FAQ page.
 */
public class FAQHelpTabPage {
    public Page page;

    private static final String HELP_DROPDOWN_LOCATOR = "id=help-dropdown";
    private static final String FAQ_LOCATOR = "id=faq";

    public FAQHelpTabPage(Page page) {
        this.page = page;
    }

    private void navigateToFAQPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on help tab
        page.click(HELP_DROPDOWN_LOCATOR);
        // User choose FAQ from dropdown
        page.click(FAQ_LOCATOR);
    }

    public String getFAQHelpTabPageTitle() {
        navigateToFAQPage();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getFAQPageURL() {
        navigateToFAQPage();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
