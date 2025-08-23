package me.shormi.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import me.shormi.basedrivers.PageDriver;
import me.shormi.utilities.CommonMethods;
import me.shormi.utilities.ExcelUtils;
import me.shormi.utilities.Screenshots;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class Login_Page extends CommonMethods {

    ExtentTest test;
    ExcelUtils excelUtils = new ExcelUtils();
    public Login_Page(ExtentTest test) {
        PageFactory.initElements(PageDriver.getCurrentDriver(), this);
        this.test = test;
    }

    //Locators
    @FindBys({
            @FindBy(xpath = "//input[@name='username']")
    })
    WebElement username;

    @FindBys({
            @FindBy(xpath = "//input[@name='password']")
    })
    WebElement password;

    @FindBys({
            @FindBy(xpath = "//button[@type='submit']")
    })
    WebElement login_button;

    @FindBys({
            @FindBy(xpath = "//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
    })
    WebElement error_message;

    @FindBys({
            @FindBy(xpath = "//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']")
    })
    WebElement required_field_message;

    @FindBys({
            @FindBy(xpath = "//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module']")
    })
    WebElement dashboard_header;

    //Screenshot+Report
    // Report
    public void passCase(String message) {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
    }

    public void passCaseWithSC(String message, String scName) throws IOException {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
        String screenShotPath = Screenshots.capture(PageDriver.getCurrentDriver(), scName); // Capture screenshot
        test.info("Screenshot saved at: " + screenShotPath); // Add file path info to the report
        test.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build()); // Attach screenshot
    }

    // Fail
    @SuppressWarnings("unused")
    public void failCase(String message, String scName) throws IOException {
        test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
        Throwable t = new InterruptedException("Exception");
        test.fail(t);
        String screenShotPath = Screenshots.capture(PageDriver.getCurrentDriver(), "" + scName + "");
        String dest = System.getProperty("user.dir") + "/screenshots/" + "" + scName + ".png";
        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
        // Removed driver.quit() to allow multiple tests to run
    }

    public void login() throws IOException {
        try{
            excelUtils.ReadExcel();
            loginWithCredentials(excelUtils.username, excelUtils.password);
        } catch (Exception e) {
            failCase("Error in login process: " + e.getMessage(), "login_process_error");
        }
    }

    public void loginWithCredentials(String usernameValue, String passwordValue) throws IOException {
        try{
            test.info("Entering username: " + usernameValue);
            if(username.isDisplayed()){
                username.clear();
                username.sendKeys(usernameValue);
                passCase("Successfully entered username: " + usernameValue);
                Thread.sleep(2000);

                test.info("Entering password: " + passwordValue);
                if(password.isDisplayed()){
                    password.clear();
                    password.sendKeys(passwordValue);
                    passCase("Successfully entered password");
                    Thread.sleep(2000);

                    test.info("Clicking Login Button");
                    if(login_button.isDisplayed()){
                        login_button.click();
                        Thread.sleep(3000);
                        verifyLoginResult();
                    } else {
                        failCase("Login Button was not locateable", "login_button_fail");
                    }
                } else {
                    failCase("Password field was not locateable", "password_fail");
                }
            } else {
                failCase("Username field was not locateable", "username_fail");
            }
        } catch (Exception e) {
            failCase("Error in login with credentials: " + e.getMessage(), "login_credentials_error");
        }
    }

    public void loginWithBlankCredentials() throws IOException {
        try{
            test.info("Testing login with blank credentials");
            if(login_button.isDisplayed()){
                login_button.click();
                Thread.sleep(2000);
                verifyRequiredFieldMessage();
            } else {
                failCase("Login Button was not locateable", "login_button_fail");
            }
        } catch (Exception e) {
            failCase("Error in blank credentials test: " + e.getMessage(), "blank_credentials_error");
        }
    }

    public void verifyLoginResult() throws IOException {
        try{
            test.info("Verifying login result");
            Thread.sleep(2000);
            
            // Check if login was successful (dashboard header is visible)
            if (dashboard_header.isDisplayed()) {
                String headerText = dashboard_header.getText();
                if (headerText.contains("Dashboard")) {
                    passCaseWithSC("Login successful! Redirected to Dashboard: " + headerText, "login_success");
                } else {
                    passCaseWithSC("Login successful! Current page: " + headerText, "login_success");
                }
            } else {
                // Check if there's an error message
                if (error_message.isDisplayed()) {
                    String errorText = error_message.getText();
                    passCaseWithSC("Login failed as expected. Error message: " + errorText, "login_failure_expected");
                } else {
                    failCase("Login result unclear - no dashboard or error message found", "login_result_unclear");
                }
            }
        } catch (Exception e) {
            failCase("Error verifying login result: " + e.getMessage(), "login_verification_error");
        }
    }

    public void verifyRequiredFieldMessage() throws IOException {
        try{
            test.info("Verifying required field message");
            Thread.sleep(2000);
            
            if (required_field_message.isDisplayed()) {
                String message = required_field_message.getText();
                passCaseWithSC("Required field validation working. Message: " + message, "required_field_validation");
            } else {
                failCase("Required field message not displayed", "required_field_message_not_found");
            }
        } catch (Exception e) {
            failCase("Error verifying required field message: " + e.getMessage(), "required_field_verification_error");
        }
    }

    public void clearLoginFields() throws IOException {
        try{
            test.info("Clearing login fields");
            if(username.isDisplayed()){
                username.clear();
                passCase("Username field cleared");
            }
            if(password.isDisplayed()){
                password.clear();
                passCase("Password field cleared");
            }
        } catch (Exception e) {
            failCase("Error clearing login fields: " + e.getMessage(), "clear_fields_error");
        }
    }


}
