package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InteractionsPage extends BasePage {
    private final By source = By.id("draggable");
    private final By target = By.id("droppable");

    public InteractionsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Открыть страницу Droppable")
    public void openDroppable() {
        driver.get("https://demoqa.com/droppable");
    }

    @Step("Перетащить элемент в область назначения")
    public void dragAndDrop() {
        WebElement sourceElement = visible(source);
        WebElement targetElement = visible(target);
        int startX = 0;
        int startY = 0;
        int endX = targetElement.getLocation().getX() - sourceElement.getLocation().getX()
            + (targetElement.getSize().getWidth() - sourceElement.getSize().getWidth()) / 2 - startX;
        int endY = targetElement.getLocation().getY() - sourceElement.getLocation().getY()
            + (targetElement.getSize().getHeight() - sourceElement.getSize().getHeight()) / 2 - startY;

        Actions actions = new Actions(driver)
            .moveToElement(sourceElement, startX, startY)
            .pause(Duration.ofMillis(300))
            .clickAndHold()
            .pause(Duration.ofMillis(300));
        for (int step = 1; step <= 12; step++) {
            actions.moveByOffset(endX / 12, endY / 12).pause(Duration.ofMillis(80));
        }
        actions.release().perform();
    }

    public String targetText() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(target, "Dropped!"));
        return visible(target).getText();
    }
}