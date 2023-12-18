package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The ManageBookingsHelpTabPage class represents a help tab page for managing bookings.
 * It provides methods to navigate to the manage bookings page and retrieve page title and URL.
 */
public class ManageBookingsHelpTabPage {
    public Page page;

    // Extracted element selectors as constants.
    private static final String HELP_DROPDOWN_ID = "id=help-dropdown";
    private static final String MANAGE_BOOKING_ID = "id=manage-booking";

    public ManageBookingsHelpTabPage(Page page) {
        this.page = page;
    }

    private void navigateToHomePage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
    }

    private void selectOptionFromHelpDropdown() {
        page.click(HELP_DROPDOWN_ID);
        page.click(MANAGE_BOOKING_ID);
    }

    private void navigateToManageBookingsPage() {
        navigateToHomePage();
        // User clicks on help tab and chooses manage bookings from dropdown
        selectOptionFromHelpDropdown();
    }

    public String getManageBookingsHelpTabPageTitle() {
        navigateToManageBookingsPage();
        return page.title();
    }

    public String getManageBookingsPageURL() {
        navigateToManageBookingsPage();
        return page.url();
    }
}
