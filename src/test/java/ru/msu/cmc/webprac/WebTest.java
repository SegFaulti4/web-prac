package ru.msu.cmc.webprac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTest {
    private WebDriver driver;
    private final boolean acceptNextAlert = true;
    private final StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver = new ChromeDriver();
        //driver.manage().window().setSize(new Dimension(860, 1080));
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testFindUserByName() {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.id("peopleListLink")).click();
        driver.get("http://localhost:8080/users");
        driver.findElement(By.id("search-input")).click();
        driver.findElement(By.id("search-input")).clear();
        driver.findElement(By.id("search-input")).sendKeys("Doom%");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            Assertions.assertEquals("Doom Slayer", driver.findElement(By.linkText("Doom Slayer")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Doom Slayer")).click();
        try {
            Assertions.assertEquals("Doom Slayer", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Doom Slayer'])[1]/following::h3[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("Role: moderator", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Doom Slayer'])[2]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("Status: rip and tear", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Doom Slayer'])[2]/following::p[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @Test
    public void testReadMessage() throws Exception {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Earth")).click();
        driver.findElement(By.id("search-input")).click();
        driver.findElement(By.id("search-input")).clear();
        driver.findElement(By.id("search-input")).sendKeys("Hell%");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            Assertions.assertEquals("Hell on Earth", driver.findElement(By.linkText("Hell on Earth")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("Hell on Earth")).click();
        try {
            Assertions.assertEquals("Not really.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Doom Slayer'])[1]/following::article[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testFindThreadUser() {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Mars")).click();
        driver.findElement(By.linkText("E1M1")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='E1M1'])[2]/following::button[1]")).click();
        driver.get("http://localhost:8080/users?partition=Mars&thread=E1M1");
        driver.findElement(By.id("search-input")).click();
        driver.findElement(By.id("search-input")).clear();
        driver.findElement(By.id("search-input")).sendKeys("Im%");
        driver.findElement(By.id("search-input")).sendKeys(Keys.ENTER);
        driver.get("http://localhost:8080/users?pattern=Im%25");
        try {
            Assertions.assertEquals("Imp", driver.findElement(By.linkText("Imp")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Imp")).click();
        driver.get("http://localhost:8080/users/Imp");
        try {
            Assertions.assertEquals("Imp", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Imp'])[1]/following::h3[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("Role: common", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Imp'])[2]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @Test
    public void testLoginLogout() {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("Imp");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("666");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("My Profile")).click();
        driver.get("http://localhost:8080/users/Imp");
        try {
            Assertions.assertEquals("Imp", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Imp'])[1]/following::h3[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Logout")).click();
        driver.get("http://localhost:8080/forum");
        try {
            Assertions.assertEquals("Login", driver.findElement(By.linkText("Login")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @Test
    public void testCreateDeleteThread() {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("Imp");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("666");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.id("rootLink")).click();
        try {
            Assertions.assertEquals("0", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Fortress of Doom'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Fortress of Doom")).click();
        try {
            Assertions.assertEquals("No threads found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Search'])[1]/following::div[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.id("thread-name")).click();
        driver.findElement(By.id("thread-name")).clear();
        driver.findElement(By.id("thread-name")).sendKeys("B-Day Party");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='No threads found.'])[1]/following::button[1]")).click();
        try {
            Assertions.assertEquals("B-Day Party", driver.findElement(By.linkText("B-Day Party")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("0", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='B-Day Party'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("Imp", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='msg'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("B-Day Party")).click();
        try {
            Assertions.assertEquals("No messages found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Search users'])[1]/following::div[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Fortress of Doom")).click();
        driver.findElement(By.linkText("Forum")).click();
        try {
            Assertions.assertEquals("1", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Fortress of Doom'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Fortress of Doom")).click();
        driver.findElement(By.linkText("B-Day Party")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='B-Day Party'])[2]/following::button[1]")).click();
        try {
            Assertions.assertEquals("No threads found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Search'])[1]/following::div[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Forum")).click();
        try {
            Assertions.assertEquals("0", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Fortress of Doom'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @Test
    public void testCreateDeleteMessage() throws Exception {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("Imp");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("666");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Mars")).click();
        try {
            Assertions.assertEquals("0", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='E1M5'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("E1M5")).click();
        try {
            Assertions.assertEquals("No messages found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Search users'])[1]/following::div[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("message-text")).click();
        driver.findElement(By.id("message-text")).clear();
        driver.findElement(By.id("message-text")).sendKeys("Hate this place");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            Assertions.assertEquals("Imp", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Search users'])[1]/following::article[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        try {
            Assertions.assertEquals("Hate this place", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Imp'])[1]/following::article[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("Mars")).click();
        try {
            Assertions.assertEquals("1", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='E1M5'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("E1M5")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reply'])[1]/following::button[1]")).click();
        try {
            Assertions.assertEquals("No messages found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Search users'])[1]/following::div[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("Mars")).click();
        try {
            Assertions.assertEquals("0", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='E1M5'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testReplyDeleteMessage() throws Exception {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("Doom Slayer");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("iddqd");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Hell")).click();
        driver.findElement(By.linkText("Nekravol")).click();
        driver.findElement(By.xpath("//button[@onclick=\"document.getElementById('reply-to').value = 8\"]")).click();
        driver.findElement(By.id("message-text")).click();
        driver.findElement(By.id("message-text")).click();
        driver.findElement(By.id("message-text")).clear();
        driver.findElement(By.id("message-text")).sendKeys("Not Really.");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            Assertions.assertEquals("#8", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Same question..'])[1]/following::small[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("reply to: #8", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Doom Slayer'])[1]/following::small[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reply'])[2]/following::button[1]")).click();
    }

    @Test
    public void testCreatePartition() {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("123");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.id("partition-name")).click();
        driver.findElement(By.id("partition-name")).clear();
        driver.findElement(By.id("partition-name")).sendKeys("ZZZ");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            Assertions.assertEquals("ZZZ", driver.findElement(By.linkText("ZZZ")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("123", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='themes'])[6]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        try {
            Assertions.assertEquals("0", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='ZZZ'])[1]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("ZZZ")).click();
        try {
            Assertions.assertEquals("No threads found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Search'])[1]/following::div[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @Test
    public void testConcealOpenPartition() {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("123");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Earth")).click();
        driver.findElement(By.xpath("//a[2]/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Events")).click();
        driver.findElement(By.xpath("//a[2]/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Fortress of Doom")).click();
        driver.findElement(By.xpath("//a[2]/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Hell")).click();
        driver.findElement(By.xpath("//a[2]/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Mars")).click();
        driver.findElement(By.xpath("//a[2]/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("ZZZ")).click();
        driver.findElement(By.xpath("//a[2]/button")).click();
        driver.findElement(By.linkText("Logout")).click();
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("Imp");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("666");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        try {
            Assertions.assertEquals("No partitions found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forum Partitions'])[1]/following::div[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Logout")).click();
        try {
            Assertions.assertEquals("No partitions found.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forum Partitions'])[1]/following::div[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("123");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Earth")).click();
        driver.findElement(By.xpath("//a/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Events")).click();
        driver.findElement(By.xpath("//a/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Fortress of Doom")).click();
        driver.findElement(By.xpath("//a/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Hell")).click();
        driver.findElement(By.xpath("//a/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("Mars")).click();
        driver.findElement(By.xpath("//a/button")).click();
        driver.findElement(By.linkText("Forum")).click();
        driver.findElement(By.linkText("ZZZ")).click();
        driver.findElement(By.xpath("//a/button")).click();
        driver.findElement(By.linkText("Logout")).click();
        try {
            Assertions.assertEquals("Earth", driver.findElement(By.linkText("Earth")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @Test
    public void testBanUnbanUser() throws Exception {
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("123");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.id("peopleListLink")).click();
        driver.get("http://localhost:8080/users");
        driver.findElement(By.linkText("Imp")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Set user role:'])[1]/following::button[1]")).click();
        try {
            Assertions.assertEquals("Role: banned", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Imp'])[2]/following::p[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("Logout")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("Imp");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("666");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            Assertions.assertEquals("Your username might be banned", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Wrong credentials'])[1]/following::h5[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("123");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.id("peopleListLink")).click();
        driver.get("http://localhost:8080/users");
        driver.findElement(By.linkText("Imp")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Banned'])[1]/following::button[1]")).click();
        driver.findElement(By.linkText("Logout")).click();
        driver.get("http://localhost:8080/forum");
        driver.findElement(By.linkText("Login")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("Imp");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("666");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/forum");
        try {
            Assertions.assertEquals("Forum Partitions", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forum'])[1]/following::h5[1]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assertions.fail(verificationErrorString);
        }
    }
}
