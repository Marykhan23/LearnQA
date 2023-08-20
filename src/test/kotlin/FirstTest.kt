
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

class FirstTest {
    private var driver: AppiumDriver<MobileElement>? = null

    fun waitForElement(by: By): WebElement{
        val wait = WebDriverWait(driver, 5)
        wait.withMessage("Can't find the element: $by")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun waitForElementTimeout(by: By, timeInSec: Long ): WebElement{
        val wait = WebDriverWait(driver, timeInSec)
        wait.withMessage("Can't find the element: $by")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun assertElementHasText(by: By, text: String){
        val textElement = waitForElement(by).getAttribute("text")
        Assert.assertEquals("The text doesn't match the $by  element text", text, textElement)
    }

    fun waitForElementNotPresent(by: By): Boolean{
        val wait = WebDriverWait(driver, 5)
        wait.withMessage("The element: $by is visible")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }

    @Before
    @Throws(Exception::class)
    fun setUp()
    {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("platformVersion", "8.1")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("app", "/Users/marikh/Documents/app_builds/org.wikipedia.apk")

        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }

    @After
    fun tearDown()
    {
        driver?.quit()
    }

    @Test
    fun searchForWordThenClearResults(){
        waitForElement(By.xpath("//*[contains(@text,'Search Wikipedia')]"))?.click()
        waitForElement(By.id("search_src_text"))?.sendKeys("Java")
        assertElementHasText(By.id("search_src_text"), "Java")
        assertElementHasText(By.xpath("//*[contains(@text,'Java')]"), "Java")
        assertElementHasText(By.xpath("//*[contains(@text,'JavaScript')]"), "JavaScript")
        waitForElement(By.id("search_close_btn"))?.click()
        assertElementHasText(By.id("search_src_text"), "Search…")
        waitForElementNotPresent(By.xpath("//*[contains(@text,'JavaScript')]"))
    }

    @Test
    fun searchAndCheckList(){
        waitForElement(By.xpath("//*[contains(@text,'Search Wikipedia')]"))?.click()
        waitForElement(By.id("search_src_text"))?.sendKeys("Java")
        assertElementHasText(By.id("search_src_text"), "Java")

        val elements : MutableList<MobileElement>? = driver?.findElements(By.id("page_list_item_title"))
        for (element in elements!!){
            val text = element.getAttribute("text")
            println(text)
            Assert.assertTrue("It doesn't include the text $text", text.lowercase().contains("java"))
        }
//        elements.forEach { el -> el.getAttribute("text").lowercase().contains("java") }
    }

}