package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {

    public static void main(String[] args) throws InterruptedException {

        ChromeOptions options = new ChromeOptions();

        // Required for Jenkins (Linux headless execution)
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Actions actions = new Actions(driver);

        // 🔹 Step 1: SauceDemo Login
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        System.out.println("SauceDemo login successful");

        // 🔹 Step 2: Automation Exercise (NO new tab)
        driver.get("https://automationexercise.com/products");

        driver.findElement(By.id("search_product")).sendKeys("Men Tshirt");
        driver.findElement(By.id("submit_search")).click();

        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("a[data-product-id='2']")
                )
        );

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", product);

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", product);

        WebElement viewCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("#cartModal a[href='/view_cart']")
                )
        );

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", viewCart);

        System.out.println("Automation Exercise product added to cart");

        // 🔹 Step 3: Practice Test Automation (NO new tab)
        driver.get("https://practicetestautomation.com/practice-test-login/");

        Thread.sleep(2000);

        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("submit")).click();

        System.out.println("Practice Test Automation login successful");

        Thread.sleep(5000);

        driver.quit();
    }
}
