package me.shormi.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import me.shormi.basedrivers.PageDriver;
import me.shormi.pages.Login_Page;
import me.shormi.basedrivers.BaseDriver;
import me.shormi.utilities.ExtentFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Login_Test extends BaseDriver {
    ExtentReports extent;
    ExtentTest parentTest;
    ExtentTest childTest;

    @BeforeClass
    public void open_url() throws InterruptedException {
        PageDriver.getCurrentDriver().get(url);
        Thread.sleep(5000);
        extent = ExtentFactory.getInstance();
        parentTest = extent.createTest("<p style=\"color:green; font-size:13px\"><b>Orange HRM</b></p>").assignAuthor("Tester").assignDevice("Windows");
    }

    @Test(priority = 1, description = "TC-OR-01: Valid login with correct credentials")
    public void validLoginWithCorrectCredentials() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-01: Valid Login</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.loginWithCredentials("Admin", "admin123");
    }

    @Test(priority = 2, description = "TC-OR-02: Valid username with invalid password")
    public void validUsernameWithInvalidPassword() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-02: Invalid Password</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.loginWithCredentials("Admin", "wrongpass");
    }

    @Test(priority = 3, description = "TC-OR-03: Invalid username with valid password")
    public void invalidUsernameWithValidPassword() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-03: Invalid Username</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.loginWithCredentials("wrongname", "admin123");
    }

    @Test(priority = 4, description = "TC-OR-04: Blank username and password")
    public void blankUsernameAndPassword() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-04: Blank Credentials</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.loginWithBlankCredentials();
    }

    @Test(priority = 5, description = "TC-OR-05: Login with Excel data", enabled = false)
    public void loginWithExcelData() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-05: Excel Data Login</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.login();
    }

    @Test(priority = 6, description = "TC-OR-06: Clear login fields functionality")
    public void clearLoginFields() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-06: Clear Fields</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.clearLoginFields();
    }

    @AfterClass
    public void report(){
        extent.flush();
    }

}
