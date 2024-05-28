package pages;

import com.microsoft.playwright.Page;

/**
 * The ManageBookingsHelpTabPage class represents a help tab page for managing bookings.
 * It provides methods to navigate to the manage bookings page and retrieve page title and URL.
 */
public class ManageBookingsHelpTabPage {

    public Page page;

    public ManageBookingsHelpTabPage(Page page) {
        this.page = page;
    }


    public String getManageBookingsHelpTabPageTitle() {
        return page.title();
    }

    public String getManageBookingsPageURL() {
        return page.url();
    }
}
