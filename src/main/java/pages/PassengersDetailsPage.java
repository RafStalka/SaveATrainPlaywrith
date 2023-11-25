package pages;

import com.microsoft.playwright.Page;

public class PassengersDetailsPage {
    private Page page;

    public PassengersDetailsPage(Page page) {
        this.page = page;
    }

    public String getPassengersDetailsPageTitle() {
        return page.title();
    }
}
