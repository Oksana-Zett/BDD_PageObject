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
    private ArrayList cards;
  }

  public static AuthInfo getAuthInfo() {
    ArrayList cards=new ArrayList();
    cards.add("5559 0000 0000 0001");
    cards.add("5559 0000 0000 0002");
    return new AuthInfo("vasya", "qwerty123", cards);
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty", getAuthInfo().cards);
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }
}
