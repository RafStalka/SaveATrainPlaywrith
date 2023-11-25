package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class RailAgentDashboardProductPage {

    public Page page;

    public RailAgentDashboardProductPage(Page page) {
        this.page = page;
    }

    public String getRailAgentDashboardProductPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-agent-dashboard");
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }
}
