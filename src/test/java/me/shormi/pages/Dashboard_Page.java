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

public class Dashboard_Page extends CommonMethods {

    ExtentTest test;

    public Dashboard_Page(ExtentTest test) {
        PageFactory.initElements(PageDriver.getCurrentDriver(), this);
        this.test = test;
    }

    // Locators
    @FindBys({
            @FindBy(xpath = "//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module']")
    })
    WebElement dashboard_header;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[2]/a")
    })
    WebElement pim_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[1]/a")
    })
    WebElement admin_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[3]/a")
    })
    WebElement leave_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[4]/a")
    })
    WebElement time_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[5]/a")
    })
    WebElement recruitment_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[6]/a")
    })
    WebElement my_info_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[7]/a")
    })
    WebElement performance_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[8]/a")
    })
    WebElement pim_menu_2;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[9]/a")
    })
    WebElement directory_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[10]/a")
    })
    WebElement maintenance_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[11]/a")
    })
    WebElement claim_menu;

    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[12]/a")
    })
    WebElement buzz_menu;

    @FindBys({
            @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    })
    WebElement user_dropdown;

    @FindBys({
            @FindBy(xpath = "//a[contains(text(),'Logout')]")
    })
    WebElement logout_button;

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

    // Dashboard methods
    public void verifyDashboard() throws IOException, InterruptedException {
        try {
            test.info("Verifying Dashboard is loaded");
            if (dashboard_header.isDisplayed()) {
                String headerText = dashboard_header.getText();
                if (headerText.contains("Dashboard")) {
                    passCaseWithSC("Dashboard loaded successfully. Header: " + headerText, "dashboard_loaded");
                } else {
                    failCase("Dashboard header not found. Found: " + headerText, "dashboard_header_fail");
                }
            } else {
                failCase("Dashboard header is not displayed", "dashboard_not_displayed");
            }
        } catch (Exception e) {
            failCase("Error verifying dashboard: " + e.getMessage(), "dashboard_verification_error");
        }
    }

    public void navigateToPIM() throws IOException, InterruptedException {
        try {
            test.info("Navigating to PIM menu");
            if (pim_menu.isDisplayed()) {
                pim_menu.click();
                Thread.sleep(3000);
                passCase("Successfully navigated to PIM menu");
            } else {
                failCase("PIM menu is not displayed", "pim_menu_not_found");
            }
        } catch (Exception e) {
            failCase("Error navigating to PIM: " + e.getMessage(), "pim_navigation_error");
        }
    }

    public void navigateToAdmin() throws IOException, InterruptedException {
        try {
            test.info("Navigating to Admin menu");
            if (admin_menu.isDisplayed()) {
                admin_menu.click();
                Thread.sleep(3000);
                passCase("Successfully navigated to Admin menu");
            } else {
                failCase("Admin menu is not displayed", "admin_menu_not_found");
            }
        } catch (Exception e) {
            failCase("Error navigating to Admin: " + e.getMessage(), "admin_navigation_error");
        }
    }

    public void logout() throws IOException, InterruptedException {
        try {
            test.info("Logging out from the application");
            if (user_dropdown.isDisplayed()) {
                user_dropdown.click();
                Thread.sleep(2000);
                if (logout_button.isDisplayed()) {
                    logout_button.click();
                    Thread.sleep(3000);
                    passCaseWithSC("Successfully logged out", "logout_success");
                } else {
                    failCase("Logout button is not displayed", "logout_button_not_found");
                }
            } else {
                failCase("User dropdown is not displayed", "user_dropdown_not_found");
            }
        } catch (Exception e) {
            failCase("Error during logout: " + e.getMessage(), "logout_error");
        }
    }
}
