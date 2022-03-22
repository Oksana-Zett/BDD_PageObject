package ru.netology.web.data;

import lombok.Value;
import lombok.val;

import java.util.ArrayList;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  @Value
  public static class InfoCard {
    private ArrayList cards;
  }

  public static InfoCard getInfoCard() {
    ArrayList cards = new ArrayList();
    cards.add("");
    cards.add("5559 0000 0000 0001");
    cards.add("5559 0000 0000 0002");
    return new InfoCard(cards);
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }
}
