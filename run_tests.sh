#!/bin/bash

# Selenium Test Runner Script
# This script provides easy commands to run different test scenarios

echo "=========================================="
echo "Orange HRM Assignment Test Runner"
echo "=========================================="

# Function to display usage
show_usage() {
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Assignment Options:"
    echo "  all           Run all assignment tests (Login + Leave)"
    echo "  login         Run login tests only"
    echo "  leave         Run leave management tests only"
    echo "  clean         Clean and compile project"
    echo "  report        Open the latest test report"
    echo "  help          Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 all"
    echo "  $0 login"
    echo "  $0 leave"
}

# Function to run tests
run_tests() {
    local test_type=$1
    echo "Running $test_type tests..."
    
    case $test_type in
        "all")
            mvn test -DsuiteXmlFile=test.xml
            ;;
        "login")
            mvn test -Dtest=me.shormi.tests.Login_Test
            ;;
        "leave")
            mvn test -Dtest=me.shormi.tests.Leave_Test
            ;;
        *)
            echo "Unknown test type: $test_type"
            echo "Available options: all, login, leave"
            show_usage
            exit 1
            ;;
    esac
}

# Function to clean and compile
clean_project() {
    echo "Cleaning and compiling project..."
    mvn clean compile
}

# Function to open report
open_report() {
    local report_path="./reports/Report.html"
    if [ -f "$report_path" ]; then
        echo "Opening test report..."
        if command -v xdg-open > /dev/null; then
            xdg-open "$report_path"
        elif command -v open > /dev/null; then
            open "$report_path"
        else
            echo "Report generated at: $report_path"
            echo "Please open it manually in your browser."
        fi
    else
        echo "No report found. Please run tests first."
    fi
}

# Main script logic
case $1 in
    "all"|"login"|"leave")
        run_tests $1
        ;;
    "clean")
        clean_project
        ;;
    "report")
        open_report
        ;;
    "help"|"-h"|"--help"|"")
        show_usage
        ;;
    *)
        echo "Unknown option: $1"
        show_usage
        exit 1
        ;;
esac

echo "=========================================="
echo "Test execution completed!"
echo "=========================================="
