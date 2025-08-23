package me.shormi.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import me.shormi.basedrivers.PageDriver;
import me.shormi.utilities.CommonMethods;
import me.shormi.utilities.Screenshots;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class Search_Employee_Page extends CommonMethods {

    ExtentTest test;

    public Search_Employee_Page(ExtentTest test) {
        PageFactory.initElements(PageDriver.getCurrentDriver(), this);
        this.test = test;
    }

    // Locators
    @FindBys({
            @FindBy(xpath = "//a[contains(text(),'Employee List')]")
    })
    WebElement employee_list_link;

    @FindBys({
            @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    })
    WebElement employee_name_search;

    @FindBys({
            @FindBy(xpath = "//input[@class='oxd-input oxd-input--active']")
    })
    WebElement employee_id_search;

    @FindBys({
            @FindBy(xpath = "//button[@type='submit']")
    })
    WebElement search_button;

    @FindBys({
            @FindBy(xpath = "//button[@type='reset']")
    })
    WebElement reset_button;

    @FindBys({
            @FindBy(xpath = "//div[@class='oxd-table-body']//div[@class='oxd-table-row oxd-table-row--with-border']")
    })
    WebElement search_results;

    @FindBys({
            @FindBy(xpath = "//span[contains(text(),'No Records Found')]")
    })
    WebElement no_records_found;

    // Report methods
    public void passCase(String message) {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
    }

    public void passCaseWithSC(String message, String scName) throws IOException {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
        String screenShotPath = Screenshots.capture(PageDriver.getCurrentDriver(), scName);
        test.info("Screenshot saved at: " + screenShotPath);
        test.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
    }

    public void failCase(String message, String scName) throws IOException {
        test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
        Throwable t = new InterruptedException("Exception");
        test.fail(t);
        String screenShotPath = Screenshots.capture(PageDriver.getCurrentDriver(), "" + scName + "");
        String dest = System.getProperty("user.dir") + "/screenshots/" + "" + scName + ".png";
        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
        PageDriver.getCurrentDriver().quit();
    }

    // Search Employee methods
    public void navigateToEmployeeList() throws IOException, InterruptedException {
        try {
            test.info("Navigating to Employee List");
            if (employee_list_link.isDisplayed()) {
                employee_list_link.click();
                Thread.sleep(3000);
                passCase("Successfully navigated to Employee List");
            } else {
                failCase("Employee List link is not displayed", "employee_list_link_not_found");
            }
        } catch (Exception e) {
            failCase("Error navigating to Employee List: " + e.getMessage(), "employee_list_navigation_error");
        }
    }

    public void searchEmployeeByName(String employeeName) throws IOException, InterruptedException {
        try {
            test.info("Searching employee by name: " + employeeName);
            if (employee_name_search.isDisplayed()) {
                employee_name_search.clear();
                employee_name_search.sendKeys(employeeName);
                Thread.sleep(2000);
                passCase("Successfully entered employee name: " + employeeName);
            } else {
                failCase("Employee name search field is not displayed", "employee_name_search_not_found");
            }
        } catch (Exception e) {
            failCase("Error searching employee by name: " + e.getMessage(), "employee_name_search_error");
        }
    }

    public void searchEmployeeById(String employeeId) throws IOException, InterruptedException {
        try {
            test.info("Searching employee by ID: " + employeeId);
            if (employee_id_search.isDisplayed()) {
                employee_id_search.clear();
                employee_id_search.sendKeys(employeeId);
                Thread.sleep(2000);
                passCase("Successfully entered employee ID: " + employeeId);
            } else {
                failCase("Employee ID search field is not displayed", "employee_id_search_not_found");
            }
        } catch (Exception e) {
            failCase("Error searching employee by ID: " + e.getMessage(), "employee_id_search_error");
        }
    }

    public void clickSearchButton() throws IOException, InterruptedException {
        try {
            test.info("Clicking Search button");
            if (search_button.isDisplayed()) {
                search_button.click();
                Thread.sleep(3000);
                passCase("Successfully clicked Search button");
            } else {
                failCase("Search button is not displayed", "search_button_not_found");
            }
        } catch (Exception e) {
            failCase("Error clicking Search button: " + e.getMessage(), "search_button_click_error");
        }
    }

    public void clickResetButton() throws IOException, InterruptedException {
        try {
            test.info("Clicking Reset button");
            if (reset_button.isDisplayed()) {
                reset_button.click();
                Thread.sleep(3000);
                passCase("Successfully clicked Reset button");
            } else {
                failCase("Reset button is not displayed", "reset_button_not_found");
            }
        } catch (Exception e) {
            failCase("Error clicking Reset button: " + e.getMessage(), "reset_button_click_error");
        }
    }

    public void verifySearchResults() throws IOException, InterruptedException {
        try {
            test.info("Verifying search results");
            Thread.sleep(2000);
            
            if (no_records_found.isDisplayed()) {
                passCaseWithSC("Search completed. No records found.", "search_no_results");
            } else if (search_results.isDisplayed()) {
                passCaseWithSC("Search completed. Records found.", "search_with_results");
            } else {
                passCaseWithSC("Search completed.", "search_completed");
            }
        } catch (Exception e) {
            failCase("Error verifying search results: " + e.getMessage(), "search_results_verification_error");
        }
    }

    public void performEmployeeSearch(String employeeName, String employeeId) throws IOException, InterruptedException {
        try {
            navigateToEmployeeList();
            
            if (employeeName != null && !employeeName.isEmpty()) {
                searchEmployeeByName(employeeName);
            }
            
            if (employeeId != null && !employeeId.isEmpty()) {
                searchEmployeeById(employeeId);
            }
            
            clickSearchButton();
            verifySearchResults();
            
        } catch (Exception e) {
            failCase("Error performing employee search: " + e.getMessage(), "employee_search_error");
        }
    }
}
