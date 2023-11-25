package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class RailBookApiProductPage {

    public Page page;

    public RailBookApiProductPage(Page page) {
        this.page = page;
    }

    public String getRailBookApiPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rails-book-api");
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }

}
