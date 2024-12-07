import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;


public class testM {

    WebDriver driver;

    @DataProvider(name = "test-data")
    public Object[][] excelDP() throws IOException{
//We are creating an object from the Excel sheet data by calling a method that reads data from the Excel stored locally in our system
        Object[][] arrObj = getExcelData("Testdata/Testdata.xlsx","Sheet1");
        return arrObj;
    }
    public String[][] getExcelData(String fileName, String sheetName){
        String[][] data = null;
        try
        {
            FileInputStream fs = new FileInputStream("Testdata/Testdata.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row = sheet.getRow(0);
            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfColumns = row.getLastCellNum();
            Cell cell;
            data = new String[noOfRows-1][noOfColumns];
            for(int i =1; i<noOfRows;i++){
                for(int j=0;j<noOfColumns;j++){
                    row = sheet.getRow(i);
                    cell= row.getCell(j);
                    data[i-1][j] = cell.getStringCellValue();
                }
            }
        }
        catch (Exception e) {
            Reporter.log("The exception is: " +e.getMessage());
        }
        return data;
    }

    public testM() throws IOException {
    }

    @Parameters("browser")
    @BeforeTest
    public void setup(String browser) throws InterruptedException {
        System.out.println("Browser Name is : " + browser);
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("safari")){
            driver = new SafariDriver();
            driver.manage().window().maximize();
        }

            driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @Test(dataProvider ="test-data")
    public void login(String Username, String Password) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("username"))));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("submit"));
        try {
            usernameField.sendKeys(Username);
        }
        catch (NoSuchElementException e) {
            usernameField = driver.findElement(By.id("username"));
            usernameField.sendKeys(Username);
        }
        passwordField.sendKeys(Password);
        submitButton.click();
        try {

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("error"))));
            System.out.println("Login Unsuccessful : " + errorMessage.getText());
            Reporter.log("Login Unsuccessful : " + errorMessage.getText());
        } catch (Exception e) {
            WebElement loginSuccessMessage = wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.xpath("//*[@id=\"loop-container\"]/div/article/div[1]/h1"))));
            Reporter.log(loginSuccessMessage.getText());
            WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"loop-container\"]/div/article/div[2]/div/div/div/a"));
            logoutButton.click();
            Reporter.log("User has successfully Logged Out.");
        }
    }

    @AfterTest
    public void teardown() {
           driver.close();
           Reporter.log("Closing the Browser");
        }

        }

