package me.shormi.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import me.shormi.basedrivers.PageDriver;
import me.shormi.pages.Employee_Page;
import me.shormi.pages.Login_Page;
import me.shormi.basedrivers.BaseDriver;
import me.shormi.utilities.ExtentFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Add_Employee extends BaseDriver {
    ExtentReports extent;
    ExtentTest parentTest;
    ExtentTest childTest;

    @BeforeClass
    public void open_url() throws InterruptedException {
        extent = ExtentFactory.getInstance();
        parentTest = extent.createTest("<p style=\"color:green; font-size:13px\"><b>Orange HRM</b></p>").assignAuthor("Tester").assignDevice("Windows");
    }

    @Test
    public void addEmployee() throws IOException, InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:green; font-size:13px\"><b>Add Emp</b></p>");
        Employee_Page employee_page = new Employee_Page(childTest);
        employee_page.add_employee();
    }

    @AfterClass
    public void report(){
        extent.flush();
    }

}
