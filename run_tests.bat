@echo off
REM Selenium Test Runner Script for Windows
REM This script provides easy commands to run different test scenarios

echo ==========================================
echo Orange HRM Assignment Test Runner
echo ==========================================

if "%1"=="" goto show_usage
if "%1"=="help" goto show_usage
if "%1"=="-h" goto show_usage
if "%1"=="--help" goto show_usage

if "%1"=="all" goto run_all
if "%1"=="login" goto run_login
if "%1"=="leave" goto run_leave
if "%1"=="clean" goto clean_project
if "%1"=="report" goto open_report

echo Unknown option: %1
goto show_usage

:run_all
echo Running all assignment tests (Login + Leave)...
call mvn test -DsuiteXmlFile=test.xml
goto end

:run_login
echo Running login tests...
call mvn test -Dtest=me.shormi.tests.Login_Test
goto end

:run_leave
echo Running leave management tests...
call mvn test -Dtest=me.shormi.tests.Leave_Test
goto end

:clean_project
echo Cleaning and compiling project...
call mvn clean compile
goto end

:open_report
echo Opening test report...
if exist "reports\Report.html" (
    start reports\Report.html
) else (
    echo No report found. Please run tests first.
)
goto end

:show_usage
echo Usage: %0 [OPTION]
echo.
echo Assignment Options:
echo   all           Run all assignment tests (Login + Leave)
echo   login         Run login tests only
echo   leave         Run leave management tests only
echo   clean         Clean and compile project
echo   report        Open the latest test report
echo   help          Show this help message
echo.
echo Examples:
echo   %0 all
echo   %0 login
echo   %0 leave
goto end

:end
echo ==========================================
echo Test execution completed!
echo ==========================================
