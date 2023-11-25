package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class RailSearchApiProductPage {

    public Page page;

    public RailSearchApiProductPage(Page page) {
        this.page = page;
    }

    public String getRailSearchApiPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail search api
        page.click("id=rail-search-api");
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }
}
