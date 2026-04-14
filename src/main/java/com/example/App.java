package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class App {

    public static void main(String[] args) {

        //  Setup ChromeDriver automatically (NO version issues)
        WebDriverManager.chromedriver().setup();

        //  Chrome options for Jenkins (VERY IMPORTANT)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");          // Run without UI
        options.addArguments("--no-sandbox");            // Required for Linux
        options.addArguments("--disable-dev-shm-usage"); // Prevent crashes
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // ============================
            // 🔹 Step 1: SauceDemo Login
            // ============================
            driver.get("https://www.saucedemo.com/");

            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            System.out.println("SauceDemo login successful");

            // ============================
            // 🔹 Step 2: Automation Exercise
            // ============================
            driver.get("https://automationexercise.com/products");

            driver.findElement(By.id("search_product")).sendKeys("Men Tshirt");
            driver.findElement(By.id("submit_search")).click();

            WebElement product = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("a[data-product-id='2']")
                    )
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", product);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", product);

            WebElement viewCart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("#cartModal a[href='/view_cart']")
                    )
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", viewCart);

            System.out.println("Product added to cart");

            // ============================
            // 🔹 Step 3: Practice Test Login
            // ============================
            driver.get("https://practicetestautomation.com/practice-test-login/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));

            driver.findElement(By.id("username")).sendKeys("student");
            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("submit")).click();

            System.out.println("Practice Test login successful");

            // Wait to observe execution (optional)
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            //  Always close browser
            driver.quit();
        }
    }
}
