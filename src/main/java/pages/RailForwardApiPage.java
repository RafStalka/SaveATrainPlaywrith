package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class RailForwardApiPage {

    public Page page;

    public RailForwardApiPage(Page page) {
        this.page = page;
    }

    public String getRailForwardApiPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-forward-api");
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }
}
