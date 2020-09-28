package com.selenium.demo.test.login;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.selenium.demo.page.BasePage;
import com.selenium.demo.page.HomePage;
import com.selenium.demo.page.LoginPage;
import com.selenium.demo.test.base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Project Name    : selenium-testng-page-factory-csv-data-driven-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 7/15/2019
 * Time            : 12:02 PM
 * Description     :
 **/

public class LoginTest extends TestBase {

    @Test
    public void verifyValidUserLogin() {
        String loginPageTitle = "Login - My Store";
        String myAccountPageTitle = "My account - My Store";

        String csvFilePath = "./src/test/resources/users.csv";
        CSVReader csvReader = null;
        String[] csvCell = null;

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        try {
            csvReader = new CSVReader(new FileReader(csvFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                assert csvReader != null;
                if ((csvCell = csvReader.readNext()) == null) break;
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }

            assert csvCell != null;
            String email = csvCell[0];
            String password = csvCell[1];
            String fullName = csvCell[2];

            Assert.assertEquals(basePage.getBrowserTabTitle(), loginPageTitle);
            loginPage.login(email, password);
            Assert.assertEquals(basePage.getBrowserTabTitle(), myAccountPageTitle);
            Assert.assertEquals(homePage.getLoggedInUsername(), fullName);
            homePage.clickOnLogoutLink();
            Assert.assertEquals(basePage.getBrowserTabTitle(), loginPageTitle);
        }
    }

}