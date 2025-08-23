package me.shormi.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import me.shormi.basedrivers.PageDriver;
import me.shormi.pages.Dashboard_Page;
import me.shormi.pages.Login_Page;
import me.shormi.pages.Search_Employee_Page;
import me.shormi.basedrivers.BaseDriver;
import me.shormi.utilities.ExtentFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Search_Employee_Test extends BaseDriver {
    ExtentReports extent;
    ExtentTest parentTest;
    ExtentTest childTest;

    @BeforeClass
    public void open_url() throws InterruptedException {
        PageDriver.getCurrentDriver().get(url);
        Thread.sleep(5000);
        extent = ExtentFactory.getInstance();
        parentTest = extent.createTest("<p style=\"color:green; font-size:13px\"><b>Search Employee Test</b></p>").assignAuthor("Tester").assignDevice("Windows");
    }

    @Test(priority = 1)
    public void login_to_application() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Login</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.login();
    }

    @Test(priority = 2)
    public void verify_dashboard() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Dashboard Verification</b></p>");
        Dashboard_Page dashboard_page = new Dashboard_Page(childTest);
        dashboard_page.verifyDashboard();
    }

    @Test(priority = 3)
    public void search_employee_by_name() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Search by Name</b></p>");
        Search_Employee_Page search_employee_page = new Search_Employee_Page(childTest);
        search_employee_page.performEmployeeSearch("John", null);
    }

    @Test(priority = 4)
    public void search_employee_by_id() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Search by ID</b></p>");
        Search_Employee_Page search_employee_page = new Search_Employee_Page(childTest);
        search_employee_page.performEmployeeSearch(null, "1234");
    }

    @Test(priority = 5)
    public void search_employee_by_name_and_id() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Search by Name and ID</b></p>");
        Search_Employee_Page search_employee_page = new Search_Employee_Page(childTest);
        search_employee_page.performEmployeeSearch("John", "1234");
    }

    @Test(priority = 6)
    public void reset_search() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Reset Search</b></p>");
        Search_Employee_Page search_employee_page = new Search_Employee_Page(childTest);
        search_employee_page.navigateToEmployeeList();
        search_employee_page.clickResetButton();
    }

    @Test(priority = 7)
    public void logout_from_application() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Logout</b></p>");
        Dashboard_Page dashboard_page = new Dashboard_Page(childTest);
        dashboard_page.logout();
    }

    @AfterClass
    public void report(){
        extent.flush();
    }
}
