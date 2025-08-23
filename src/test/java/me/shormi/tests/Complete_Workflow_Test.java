package me.shormi.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import me.shormi.basedrivers.PageDriver;
import me.shormi.pages.Dashboard_Page;
import me.shormi.pages.Employee_Page;
import me.shormi.pages.Login_Page;
import me.shormi.pages.Search_Employee_Page;
import me.shormi.basedrivers.BaseDriver;
import me.shormi.utilities.ExtentFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Complete_Workflow_Test extends BaseDriver {
    ExtentReports extent;
    ExtentTest parentTest;
    ExtentTest childTest;

    @BeforeClass
    public void open_url() throws InterruptedException {
        PageDriver.getCurrentDriver().get(url);
        Thread.sleep(5000);
        extent = ExtentFactory.getInstance();
        parentTest = extent.createTest("<p style=\"color:green; font-size:13px\"><b>Complete Workflow Test</b></p>").assignAuthor("Tester").assignDevice("Windows");
    }

    @Test(priority = 1, description = "Login to Orange HRM application")
    public void login_to_application() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Login</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.login();
    }

    @Test(priority = 2, description = "Verify dashboard is loaded successfully")
    public void verify_dashboard() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Dashboard Verification</b></p>");
        Dashboard_Page dashboard_page = new Dashboard_Page(childTest);
        dashboard_page.verifyDashboard();
    }

    @Test(priority = 3, description = "Navigate to PIM module")
    public void navigate_to_pim() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>PIM Navigation</b></p>");
        Dashboard_Page dashboard_page = new Dashboard_Page(childTest);
        dashboard_page.navigateToPIM();
    }

    @Test(priority = 4, description = "Add a new employee")
    public void add_new_employee() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Add Employee</b></p>");
        Employee_Page employee_page = new Employee_Page(childTest);
        employee_page.add_employee();
    }

    @Test(priority = 5, description = "Search for the added employee")
    public void search_employee() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Search Employee</b></p>");
        Search_Employee_Page search_employee_page = new Search_Employee_Page(childTest);
        search_employee_page.performEmployeeSearch("John", null);
    }

    @Test(priority = 6, description = "Navigate to Admin module")
    public void navigate_to_admin() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Admin Navigation</b></p>");
        Dashboard_Page dashboard_page = new Dashboard_Page(childTest);
        dashboard_page.navigateToAdmin();
    }

    @Test(priority = 7, description = "Logout from the application")
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
