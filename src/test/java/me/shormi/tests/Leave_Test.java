package me.shormi.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import me.shormi.basedrivers.PageDriver;
import me.shormi.pages.Dashboard_Page;
import me.shormi.pages.Leave_Page;
import me.shormi.pages.Login_Page;
import me.shormi.basedrivers.BaseDriver;
import me.shormi.utilities.ExtentFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Leave_Test extends BaseDriver {
    ExtentReports extent;
    ExtentTest parentTest;
    ExtentTest childTest;

    @BeforeClass
    public void open_url() throws InterruptedException {
        PageDriver.getCurrentDriver().get(url);
        Thread.sleep(5000);
        extent = ExtentFactory.getInstance();
        parentTest = extent.createTest("<p style=\"color:green; font-size:13px\"><b>Leave Management Test</b></p>").assignAuthor("Tester").assignDevice("Windows");
    }

    @Test(priority = 1, description = "Login to Orange HRM application")
    public void login_to_application() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Login</b></p>");
        Login_Page login_page = new Login_Page(childTest);
        login_page.loginWithCredentials("Admin", "admin123");
    }

    @Test(priority = 2, description = "Verify dashboard is loaded successfully")
    public void verify_dashboard() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Dashboard Verification</b></p>");
        Dashboard_Page dashboard_page = new Dashboard_Page(childTest);
        dashboard_page.verifyDashboard();
    }

    @Test(priority = 3, description = "TC-OR-05: Assign single-day leave")
    public void assign_single_day_leave() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-05: Assign Single Day Leave</b></p>");
        Leave_Page leave_page = new Leave_Page(childTest);
        leave_page.assignLeave("Peter Mac Anderson", "CAN - Personal", "2025-08-18", "2025-08-18", "Single day personal leave");
        // Verify success message: "Successfully Saved"
        leave_page.verifySuccessMessage();
    }

    @Test(priority = 4, description = "TC-OR-06: Assign multi-day leave")
    public void assign_multi_day_leave() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-06: Assign Multi Day Leave</b></p>");
        Leave_Page leave_page = new Leave_Page(childTest);
        leave_page.assignLeave("Peter Mac Anderson", "CAN - Personal", "2025-08-18", "2025-08-20", "Multi day vacation leave");
        // Verify success toast and leave list update
        leave_page.verifySuccessMessage();
    }

    @Test(priority = 5, description = "TC-OR-07: Assign leave without Employee Name (Negative)")
    public void assign_leave_without_employee() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-07: Assign Leave Without Employee</b></p>");
        Leave_Page leave_page = new Leave_Page(childTest);
        leave_page.navigateToLeaveMenu();
        leave_page.navigateToAssignLeave();
        // Don't select employee - test validation
        leave_page.selectLeaveType("CAN - Personal");
        leave_page.enterFromDate("2025-08-18");
        leave_page.enterToDate("2025-08-18");
        leave_page.enterComment("Leave without selecting employee");
        leave_page.clickAssignButton();
        leave_page.verifyRequiredFieldMessage();
    }

    @Test(priority = 6, description = "TC-OR-08: Assign leave with From > To date (Boundary)")
    public void assign_leave_with_invalid_date_range() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>TC-OR-08: Assign Leave Invalid Date Range</b></p>");
        Leave_Page leave_page = new Leave_Page(childTest);
        leave_page.navigateToLeaveMenu();
        leave_page.navigateToAssignLeave();
        leave_page.selectEmployee("Peter Mac Anderson");
        leave_page.selectLeaveType("CAN - Personal");
        leave_page.enterFromDate("2025-08-20"); // From date > To date
        leave_page.enterToDate("2025-08-18");   // To date < From date
        leave_page.enterComment("Invalid date range test");
        leave_page.clickAssignButton();
        // This should either show error or prevent submission
    }

    @Test(priority = 7, description = "Test Assign Leave with past dates")
    public void assign_leave_with_past_dates() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Assign Leave - Past Dates</b></p>");
        Leave_Page leave_page = new Leave_Page(childTest);
        leave_page.assignLeave("Peter Mac Anderson", "CAN - Personal", "2023-12-25", "2023-12-26", "Past date test");
    }

    @Test(priority = 9, description = "Test Assign Leave with long comment")
    public void assign_leave_with_long_comment() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Assign Leave - Long Comment</b></p>");
        Leave_Page leave_page = new Leave_Page(childTest);
        String longComment = "This is a very long comment to test the textarea functionality. " +
                           "It should handle multiple lines and longer text without any issues. " +
                           "Testing the maximum character limit and text wrapping functionality.";
        leave_page.assignLeave("Peter Mac Anderson", "CAN - Vacation", "2025-02-15", "2025-02-16", longComment);
    }

    @Test(priority = 10, description = "Test Cancel functionality")
    public void test_cancel_functionality() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Cancel Functionality</b></p>");
        Leave_Page leave_page = new Leave_Page(childTest);
        leave_page.navigateToLeaveMenu();
        leave_page.navigateToAssignLeave();
        leave_page.selectEmployee("Peter Mac Anderson");
        leave_page.selectLeaveType("CAN - Personal");
        leave_page.enterFromDate("2024-12-25");
        leave_page.enterToDate("2024-12-26");
        leave_page.enterComment("Test comment");
        leave_page.clickCancelButton();
    }

    @Test(priority = 11, description = "Logout from the application")
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
