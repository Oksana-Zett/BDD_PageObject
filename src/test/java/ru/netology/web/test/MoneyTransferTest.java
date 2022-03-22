package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    public DashboardPage shouldBalanceEqual (DashboardPage dashboardPage){
        String firstCardNumber = (String) DataHelper.getInfoCard().getCards().get(1);
        String secondCardNumber = (String) DataHelper.getInfoCard().getCards().get(2);
        int firstCardBalance = dashboardPage.getCardBalance(firstCardNumber);
        int secondCardBalance = dashboardPage.getCardBalance(secondCardNumber);

        Assertions.assertEquals(firstCardBalance, secondCardBalance);
        return new DashboardPage();
    }

    public DashboardPage initialCardBalance(DashboardPage dashboardPage) {
        String firstCardNumber = (String) DataHelper.getInfoCard().getCards().get(1);
        String secondCardNumber = (String) DataHelper.getInfoCard().getCards().get(2);
        int firstCardBalance = dashboardPage.getCardBalance(firstCardNumber);
        int secondCardBalance = dashboardPage.getCardBalance(secondCardNumber);
        if (dashboardPage.getCardBalance(firstCardNumber)>dashboardPage.getCardBalance(secondCardNumber)){
            var replenishSecondCardPage = dashboardPage.replenishButton2();
            replenishSecondCardPage.Replenish(String.valueOf(firstCardBalance-(firstCardBalance+secondCardBalance)/2),
                    firstCardNumber);
        } else {
            var replenishFirstCardPage = dashboardPage.replenishButton1();
            replenishFirstCardPage.Replenish(String.valueOf(secondCardBalance-(firstCardBalance+secondCardBalance)/2),
                    secondCardNumber);
        }
        return new DashboardPage();
    }
    
    
    @BeforeEach
    void setup() {
        Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish("3500", (String) infoCard.getCards().get(2));
        Assertions.assertEquals(firstCardBalance + 3500, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance - 3500, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish("5000", (String) infoCard.getCards().get(1));
        Assertions.assertEquals(secondCardBalance - 3500 + 5000, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(firstCardBalance + 3500 - 5000, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldSumLatin() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        String valueSumCard1 = replenishFirstCardPage.setSomeValueSum("sdfhgsd");
        Assertions.assertEquals("", valueSumCard1);
        replenishFirstCardPage.clickCancel();
        var replenishSecondCardPage = dashboardPage.replenishButton2();
        String valueSumCard2 = replenishSecondCardPage.setSomeValueSum("sdfhgsd");
        Assertions.assertEquals("", valueSumCard2);
        replenishSecondCardPage.clickCancel();
    }

    @Test
    void shouldSumCyrillic() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        String valueSumCard1 = replenishFirstCardPage.setSomeValueSum("рпрпо");
        Assertions.assertEquals("", valueSumCard1);
        replenishFirstCardPage.clickCancel();
        var replenishSecondCardPage = dashboardPage.replenishButton2();
        String valueSumCard2 = replenishSecondCardPage.setSomeValueSum("рпрпо");
        Assertions.assertEquals("", valueSumCard2);
        replenishSecondCardPage.clickCancel();
    }

    @Test
    void shouldSumSpecialSymbol() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        String valueSumCard1 = replenishFirstCardPage.setSomeValueSum("%&*^*^&");
        Assertions.assertEquals("", valueSumCard1);
        replenishFirstCardPage.clickCancel();
        var replenishSecondCardPage = dashboardPage.replenishButton2();
        String valueSumCard2 = replenishSecondCardPage.setSomeValueSum("%&*^*^&");
        Assertions.assertEquals("", valueSumCard2);
        replenishSecondCardPage.clickCancel();
    }

    @Test
    void shouldSumEmpty() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish("", (String) infoCard.getCards().get(2));
        Assertions.assertEquals(firstCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish("", (String) infoCard.getCards().get(1));
        Assertions.assertEquals(secondCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(firstCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldSumZero() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish("0", (String) infoCard.getCards().get(2));
        Assertions.assertEquals(firstCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish("0", (String) infoCard.getCards().get(1));
        Assertions.assertEquals(secondCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(firstCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldSumMoreBalance() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish(String.valueOf(secondCardBalance + 1), (String) infoCard.getCards().get(2));
        Assertions.assertEquals(-1, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(firstCardBalance + secondCardBalance + 1, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish(String.valueOf(firstCardBalance + 10), (String) infoCard.getCards().get(1));
        Assertions.assertEquals(-10, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance + firstCardBalance + 10, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldSumMax() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish(String.valueOf("999 999 999"), (String) infoCard.getCards().get(2));
        Assertions.assertEquals(secondCardBalance - 999999999, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(firstCardBalance + 999999999, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish(String.valueOf("999 999 999"), (String) infoCard.getCards().get(1));
        Assertions.assertEquals(firstCardBalance - 999999999, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance + 999999999, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldSumNegative() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish(String.valueOf("-100"), (String) infoCard.getCards().get(2));
        Assertions.assertEquals(secondCardBalance - 100, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(firstCardBalance + 100, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish(String.valueOf("-100"), (String) infoCard.getCards().get(1));
        Assertions.assertEquals(firstCardBalance - 100, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance + 100, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldSumEqualBalance() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish(String.valueOf(secondCardBalance), (String) infoCard.getCards().get(2));
        Assertions.assertEquals(0, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(firstCardBalance + secondCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish(String.valueOf(firstCardBalance), (String) infoCard.getCards().get(1));
        Assertions.assertEquals(0, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance + firstCardBalance, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldSumDecimalBalance() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        int firstCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1)));
        int secondCardBalance = dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2)));
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.Replenish(String.valueOf("100,50"), (String) infoCard.getCards().get(2));
        Assertions.assertEquals(String.valueOf(secondCardBalance - 100.50), dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));
        Assertions.assertEquals(String.valueOf(firstCardBalance + 100.50), dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));

        initialCardBalance(dashboardPage);

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.Replenish(String.valueOf("100,50"), (String) infoCard.getCards().get(1));
        Assertions.assertEquals(firstCardBalance - 100.50, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(1))));
        Assertions.assertEquals(secondCardBalance + 100.50, dashboardPage.getCardBalance(String.valueOf(infoCard.getCards().get(2))));

        initialCardBalance(dashboardPage);
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldFromLatin() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("100");
        String actual = replenishFirstCardPage.setSomeValueFrom("gjhk");
        Assertions.assertEquals("", actual);
        replenishFirstCardPage.clickReplenish();
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("100");
        actual = replenishSecondCardPage.setSomeValueFrom("gjhk");
        Assertions.assertEquals("", actual);
        replenishSecondCardPage.clickReplenish();
        replenishSecondCardPage.clickCancel();
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldFromCyrillic() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("100");
        String actual = replenishFirstCardPage.setSomeValueFrom("пррпт");
        Assertions.assertEquals("", actual);
        replenishFirstCardPage.clickReplenish();
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("100");
        actual = replenishSecondCardPage.setSomeValueFrom("пррпт");
        Assertions.assertEquals("", actual);
        replenishSecondCardPage.clickReplenish();
        replenishSecondCardPage.clickCancel();
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldFromSpecialSymbol() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("100");
        String actual = replenishFirstCardPage.setSomeValueFrom("%^&&*");
        Assertions.assertEquals("", actual);
        replenishFirstCardPage.clickReplenish();
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("100");
        actual = replenishSecondCardPage.setSomeValueFrom("%^&&*");
        Assertions.assertEquals("", actual);
        replenishSecondCardPage.clickReplenish();
        replenishSecondCardPage.clickCancel();
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldFromEmpty() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("100");
        String actual = replenishFirstCardPage.setSomeValueFrom("");
        Assertions.assertEquals("", actual);
        replenishFirstCardPage.clickReplenish();
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("100");
        actual = replenishSecondCardPage.setSomeValueFrom("");
        Assertions.assertEquals("", actual);
        replenishSecondCardPage.clickReplenish();
        replenishSecondCardPage.clickCancel();
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldFromOneNum() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("100");
        String actual = replenishFirstCardPage.setSomeValueFrom("0");
        Assertions.assertEquals("0", actual);
        replenishFirstCardPage.clickReplenish();
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("100");
        actual = replenishSecondCardPage.setSomeValueFrom("0");
        Assertions.assertEquals("0", actual);
        replenishSecondCardPage.clickReplenish();
        replenishSecondCardPage.clickCancel();
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldFromFiveNum() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("100");
        String actual = replenishFirstCardPage.setSomeValueFrom("01234");
        Assertions.assertEquals("0123 4", actual);
        replenishFirstCardPage.clickReplenish();
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("100");
        actual = replenishSecondCardPage.setSomeValueFrom("01234");
        Assertions.assertEquals("0123 4", actual);
        replenishSecondCardPage.clickReplenish();
        replenishSecondCardPage.clickCancel();
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldFromSeventeenNum() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("100");
        String actual = replenishFirstCardPage.setSomeValueFrom("01234567894569878");
        Assertions.assertEquals("0123 4567 8945 6987", actual);
        replenishFirstCardPage.clickReplenish();
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("100");
        actual = replenishSecondCardPage.setSomeValueFrom("01234567894569878");
        Assertions.assertEquals("0123 4567 8945 6987", actual);
        replenishSecondCardPage.clickReplenish();
        replenishSecondCardPage.clickCancel();
        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldButtonCancelWhenEmptyField() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("");
        replenishFirstCardPage.setSomeValueFrom("");
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("");
        replenishSecondCardPage.setSomeValueFrom("");
        replenishSecondCardPage.clickCancel();

        shouldBalanceEqual(dashboardPage);
    }

    @Test
    void shouldButtonCancelWhenValidData() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var infoCard = DataHelper.getInfoCard();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        initialCardBalance(dashboardPage);
        var replenishFirstCardPage = dashboardPage.replenishButton1();
        replenishFirstCardPage.setSomeValueSum("3500");
        replenishFirstCardPage.setSomeValueFrom((String) infoCard.getCards().get(1));
        replenishFirstCardPage.clickCancel();

        var replenishSecondCardPage = dashboardPage.replenishButton2();
        replenishSecondCardPage.setSomeValueSum("5000");
        replenishSecondCardPage.setSomeValueFrom((String) infoCard.getCards().get(2));
        replenishSecondCardPage.clickCancel();

        shouldBalanceEqual(dashboardPage);
    }
}

