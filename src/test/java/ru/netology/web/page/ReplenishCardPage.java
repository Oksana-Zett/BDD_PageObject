package ru.netology.web.page;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class ReplenishCardPage {
    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement sum = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement buttonReplenish = $("[data-test-id='action-transfer']");
    private SelenideElement fieldTextSum =$("[data-test-id='amount'] input.input__control");
    private SelenideElement buttonCancel =$("[data-test-id='action-cancel']");
    private SelenideElement fieldTextCard =$("[data-test-id='from'] input.input__control");

    public ReplenishCardPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage clickCancel(){
        buttonCancel.click();
        return new DashboardPage();
    }

    public String setSomeValueSum(String value){
        sum.sendKeys(Keys.CONTROL + "a");
        sum.sendKeys(value);
        return fieldTextSum.getAttribute("value");
    }

    public String setSomeValueFrom(String value){
        from.sendKeys(Keys.CONTROL + "a");
        from.sendKeys(value);
        return fieldTextCard.getAttribute("value");
    }

    public ErrorNotificationPage clickReplenish (){
        buttonReplenish.click();
        return new ErrorNotificationPage ();
    }

    public DashboardPage Replenish(String sumForReplenish, String fromForReplenish){
        sum.sendKeys(Keys.CONTROL + "a");
        sum.sendKeys(String.valueOf(sumForReplenish));
        from.sendKeys(Keys.CONTROL + "a");
        from.sendKeys(fromForReplenish);
        buttonReplenish.click();
        return new DashboardPage();
    }
}
