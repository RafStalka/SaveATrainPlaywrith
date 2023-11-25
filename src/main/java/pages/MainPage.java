package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class MainPage {
    public Page page;

    public MainPage(Page page) {
        this.page = page;
    }

    public String getMainPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getMainPageURL() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.waitForTimeout(2000);
        String url =  page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
