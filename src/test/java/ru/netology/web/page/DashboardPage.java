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
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement button1 = $$(withText("Пополнить")).get(0);
    private SelenideElement button2 = $$(withText("Пополнить")).get(1);

    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String id) {
        String text = cards.find(text("**** **** **** "+id.substring(15, 19))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public ReplenishCardPage replenishButton1() {
        button1.click();
        return new ReplenishCardPage();
    }

    public ReplenishCardPage replenishButton2() {
        button2.click();
        return new ReplenishCardPage();
    }

//    public DashboardPage initialCardBalance() {
//        String firstCardNumber = (String) DataHelper.getInfoCard().getCards().get(1);
//        String secondCardNumber = (String) DataHelper.getInfoCard().getCards().get(2);
//        int firstCardBalance = getCardBalance(firstCardNumber);
//        int secondCardBalance = getCardBalance(secondCardNumber);
//        if (getCardBalance(firstCardNumber)>getCardBalance(secondCardNumber)){
//            var replenishSecondCardPage = replenishButton2();
//            replenishSecondCardPage.Replenish(String.valueOf(firstCardBalance-(firstCardBalance+secondCardBalance)/2),
//                    firstCardNumber);
//        } else {
//            var replenishFirstCardPage = replenishButton1();
//            replenishFirstCardPage.Replenish(String.valueOf(secondCardBalance-(firstCardBalance+secondCardBalance)/2),
//                    secondCardNumber);
//        }
//        return new DashboardPage();
//    }
//
//    public DashboardPage shouldBalanceEqual (){
//        String firstCardNumber = (String) DataHelper.getInfoCard().getCards().get(1);
//        String secondCardNumber = (String) DataHelper.getInfoCard().getCards().get(2);
//        int firstCardBalance = getCardBalance(firstCardNumber);
//        int secondCardBalance = getCardBalance(secondCardNumber);
//
//        Assertions.assertEquals(firstCardBalance, secondCardBalance);
//        return new DashboardPage();
//    }
}
