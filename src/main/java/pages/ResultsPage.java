package pages;

import com.microsoft.playwright.Page;

public class ResultsPage {
    private Page page;

    public ResultsPage(Page page) {
        this.page = page;
    }

    public String getResultsPageTitle() {
        return page.title();
    }
}
