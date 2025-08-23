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

public class Leave_Page extends CommonMethods {

    ExtentTest test;

    public Leave_Page(ExtentTest test) {
        PageFactory.initElements(PageDriver.getCurrentDriver(), this);
        this.test = test;
    }

    // Leave Menu Locators
    @FindBys({
            @FindBy(xpath = "//ul[@class='oxd-main-menu']/li[3]/a")
    })
    WebElement leave_menu;

    @FindBys({
            @FindBy(xpath = "//a[normalize-space()='Assign Leave']")
    })
    WebElement assign_leave_link;

    // Employee Name Field (Required for Assign Leave)
    @FindBys({
            @FindBy(xpath = "//label[text()='Employee Name']/following-sibling::div//input[@placeholder='Type for hints...']")
    })
    WebElement employee_name_input;

    @FindBys({
            @FindBy(xpath = "//div[@role='listbox']//span[contains(@class,'oxd-autocomplete-option')]")
    })
    WebElement employee_suggestion;

    // Assign Leave Form Locators
    @FindBys({
            @FindBy(xpath = "//label[text()='Leave Type']/following-sibling::div//div[contains(@class,'oxd-select-text')]")
    })
    WebElement leave_type_dropdown;

    @FindBys({
            @FindBy(xpath = "//div[@role='listbox']//span[contains(text(),'CAN - Personal')]")
    })
    WebElement canada_personal_leave;

    @FindBys({
            @FindBy(xpath = "//div[@role='listbox']//span[contains(text(),'CAN - Vacation')]")
    })
    WebElement canada_vacation_leave;

    @FindBys({
            @FindBy(xpath = "//div[@role='listbox']//span[contains(text(),'US - Vacation')]")
    })
    WebElement us_vacation_leave;

    @FindBys({
            @FindBy(xpath = "//label[text()='From Date']/following-sibling::div//input[@placeholder='yyyy-mm-dd']")
    })
    WebElement from_date_input;

    @FindBys({
            @FindBy(xpath = "//label[text()='To Date']/following-sibling::div//input[@placeholder='yyyy-mm-dd']")
    })
    WebElement to_date_input;

    // Partial Days Field (Assign Leave specific)
    @FindBys({
            @FindBy(xpath = "//label[text()='Partial Days']/following-sibling::div//div[@class='oxd-select-text-input']")
    })
    WebElement partial_days_dropdown;

    @FindBys({
            @FindBy(xpath = "//div[@role='listbox']//span[text()='All Days']")
    })
    WebElement all_days_option;

    @FindBys({
            @FindBy(xpath = "//label[text()='Comments']/following-sibling::div//textarea")
    })
    WebElement comment_textarea;

    @FindBys({
            @FindBy(xpath = "//button[@type='submit']")
    })
    WebElement assign_button;

    @FindBys({
            @FindBy(xpath = "//button[@type='button'][contains(@class,'oxd-button--ghost')]")
    })
    WebElement cancel_button;

    @FindBys({
            @FindBy(xpath = "//div[@class='oxd-toast-container']//p[contains(text(),'Successfully Saved')]")
    })
    WebElement success_message;

    @FindBys({
            @FindBy(xpath = "//div[@class='oxd-toast-container']//p[contains(text(),'Required')]")
    })
    WebElement required_field_message;

    @FindBys({
            @FindBy(xpath = "//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']")
    })
    WebElement error_message;

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
        // Removed PageDriver.getCurrentDriver().quit() to prevent session termination
    }

    // Leave functionality methods
    public void navigateToLeaveMenu() throws IOException, InterruptedException {
        try {
            test.info("Navigating to Leave menu");
            if (leave_menu.isDisplayed()) {
                leave_menu.click();
                Thread.sleep(3000);
                passCase("Successfully navigated to Leave menu");
            } else {
                failCase("Leave menu is not displayed", "leave_menu_not_found");
            }
        } catch (Exception e) {
            failCase("Error navigating to Leave menu: " + e.getMessage(), "leave_menu_navigation_error");
        }
    }

    public void navigateToAssignLeave() throws IOException, InterruptedException {
        try {
            test.info("Navigating to Assign Leave");
            if (assign_leave_link.isDisplayed()) {
                assign_leave_link.click();
                Thread.sleep(3000);
                passCase("Successfully navigated to Assign Leave page");
            } else {
                failCase("Assign Leave link is not displayed", "assign_leave_link_not_found");
            }
        } catch (Exception e) {
            failCase("Error navigating to Assign Leave: " + e.getMessage(), "assign_leave_navigation_error");
        }
    }

    public void selectEmployee(String employeeName) throws IOException, InterruptedException {
        try {
            test.info("Selecting employee: " + employeeName);
            if (employee_name_input.isDisplayed()) {
                employee_name_input.click();
                employee_name_input.clear();
                employee_name_input.sendKeys(employeeName);
                Thread.sleep(3000); // Wait for suggestions
                
                if (employee_suggestion.isDisplayed()) {
                    employee_suggestion.click();
                    Thread.sleep(2000);
                    passCase("Successfully selected employee: " + employeeName);
                } else {
                    failCase("Employee suggestion not found for: " + employeeName, "employee_suggestion_not_found");
                }
            } else {
                failCase("Employee name input is not displayed", "employee_name_input_not_found");
            }
        } catch (Exception e) {
            failCase("Error selecting employee: " + e.getMessage(), "employee_selection_error");
        }
    }

    public void selectLeaveType(String leaveType) throws IOException, InterruptedException {
        try {
            test.info("Selecting leave type: " + leaveType);
            if (leave_type_dropdown.isDisplayed()) {
                leave_type_dropdown.click();
                Thread.sleep(2000);
                
                WebElement selectedOption = null;
                switch (leaveType.toLowerCase()) {
                    case "can - personal":
                    case "canada personal":
                    case "personal":
                        selectedOption = canada_personal_leave;
                        break;
                    case "can - vacation":
                    case "canada vacation":
                    case "vacation":
                        selectedOption = canada_vacation_leave;
                        break;
                    case "us - vacation":
                    case "us vacation":
                        selectedOption = us_vacation_leave;
                        break;
                    default:
                        selectedOption = canada_personal_leave;
                        break;
                }
                
                if (selectedOption != null && selectedOption.isDisplayed()) {
                    selectedOption.click();
                    Thread.sleep(2000);
                    passCase("Successfully selected leave type: " + leaveType);
                } else {
                    failCase("Leave type option not found: " + leaveType, "leave_type_option_not_found");
                }
            } else {
                failCase("Leave type dropdown is not displayed", "leave_type_dropdown_not_found");
            }
        } catch (Exception e) {
            failCase("Error selecting leave type: " + e.getMessage(), "leave_type_selection_error");
        }
    }

    public void enterFromDate(String fromDate) throws IOException, InterruptedException {
        try {
            test.info("Entering from date: " + fromDate);
            if (from_date_input.isDisplayed()) {
                from_date_input.clear();
                from_date_input.sendKeys(fromDate);
                Thread.sleep(2000);
                passCase("Successfully entered from date: " + fromDate);
            } else {
                failCase("From date input field is not displayed", "from_date_input_not_found");
            }
        } catch (Exception e) {
            failCase("Error entering from date: " + e.getMessage(), "from_date_entry_error");
        }
    }

    public void enterToDate(String toDate) throws IOException, InterruptedException {
        try {
            test.info("Entering to date: " + toDate);
            if (to_date_input.isDisplayed()) {
                to_date_input.clear();
                to_date_input.sendKeys(toDate);
                Thread.sleep(2000);
                passCase("Successfully entered to date: " + toDate);
            } else {
                failCase("To date input field is not displayed", "to_date_input_not_found");
            }
        } catch (Exception e) {
            failCase("Error entering to date: " + e.getMessage(), "to_date_entry_error");
        }
    }

    public void enterComment(String comment) throws IOException, InterruptedException {
        try {
            test.info("Entering comment: " + comment);
            if (comment_textarea.isDisplayed()) {
                comment_textarea.clear();
                comment_textarea.sendKeys(comment);
                Thread.sleep(2000);
                passCase("Successfully entered comment: " + comment);
            } else {
                failCase("Comment textarea is not displayed", "comment_textarea_not_found");
            }
        } catch (Exception e) {
            failCase("Error entering comment: " + e.getMessage(), "comment_entry_error");
        }
    }

    public void selectPartialDays(String partialDaysOption) throws IOException, InterruptedException {
        try {
            test.info("Selecting partial days option: " + partialDaysOption);
            if (partial_days_dropdown.isDisplayed()) {
                partial_days_dropdown.click();
                Thread.sleep(2000);
                
                if ("All Days".equals(partialDaysOption) && all_days_option.isDisplayed()) {
                    all_days_option.click();
                    Thread.sleep(2000);
                    passCase("Successfully selected partial days: " + partialDaysOption);
                } else {
                    failCase("Partial days option not found: " + partialDaysOption, "partial_days_option_not_found");
                }
            } else {
                failCase("Partial days dropdown is not displayed", "partial_days_dropdown_not_found");
            }
        } catch (Exception e) {
            failCase("Error selecting partial days: " + e.getMessage(), "partial_days_selection_error");
        }
    }

    public void clickAssignButton() throws IOException, InterruptedException {
        try {
            test.info("Clicking Assign button");
            if (assign_button.isDisplayed()) {
                assign_button.click();
                Thread.sleep(3000);
                passCase("Successfully clicked Assign button");
            } else {
                failCase("Assign button is not displayed", "assign_button_not_found");
            }
        } catch (Exception e) {
            failCase("Error clicking Assign button: " + e.getMessage(), "assign_button_click_error");
        }
    }

    public void clickCancelButton() throws IOException, InterruptedException {
        try {
            test.info("Clicking Cancel button");
            if (cancel_button.isDisplayed()) {
                cancel_button.click();
                Thread.sleep(3000);
                passCase("Successfully clicked Cancel button");
            } else {
                failCase("Cancel button is not displayed", "cancel_button_not_found");
            }
        } catch (Exception e) {
            failCase("Error clicking Cancel button: " + e.getMessage(), "cancel_button_click_error");
        }
    }

    public void verifySuccessMessage() throws IOException, InterruptedException {
        try {
            test.info("Verifying success message");
            Thread.sleep(2000);
            
            if (success_message.isDisplayed()) {
                String message = success_message.getText();
                passCaseWithSC("Leave assignment submitted successfully. Message: " + message, "leave_assignment_success");
            } else {
                failCase("Success message is not displayed", "success_message_not_found");
            }
        } catch (Exception e) {
            failCase("Error verifying success message: " + e.getMessage(), "success_message_verification_error");
        }
    }

    public void verifyRequiredFieldMessage() throws IOException, InterruptedException {
        try {
            test.info("Verifying required field message");
            Thread.sleep(2000);
            
            if (required_field_message.isDisplayed() || error_message.isDisplayed()) {
                String message = "";
                if (required_field_message.isDisplayed()) {
                    message = required_field_message.getText();
                } else if (error_message.isDisplayed()) {
                    message = error_message.getText();
                }
                passCaseWithSC("Required field validation working. Message: " + message, "required_field_validation");
            } else {
                failCase("Required field message is not displayed", "required_field_message_not_found");
            }
        } catch (Exception e) {
            failCase("Error verifying required field message: " + e.getMessage(), "required_field_verification_error");
        }
    }

    public void assignLeave(String employeeName, String leaveType, String fromDate, String toDate, String comment) throws IOException, InterruptedException {
        try {
            navigateToLeaveMenu();
            navigateToAssignLeave();
            selectEmployee(employeeName);
            selectLeaveType(leaveType);
            enterFromDate(fromDate);
            enterToDate(toDate);
            selectPartialDays("All Days"); // Default to All Days
            enterComment(comment);
            clickAssignButton();
            verifySuccessMessage();
        } catch (Exception e) {
            failCase("Error in assign leave process: " + e.getMessage(), "assign_leave_process_error");
        }
    }

    public void assignLeaveWithoutRequiredFields() throws IOException, InterruptedException {
        try {
            navigateToLeaveMenu();
            navigateToAssignLeave();
            // Skip employee selection - test validation
            clickAssignButton();
            verifyRequiredFieldMessage();
        } catch (Exception e) {
            failCase("Error in assign leave without required fields: " + e.getMessage(), "assign_leave_validation_error");
        }
    }
}
