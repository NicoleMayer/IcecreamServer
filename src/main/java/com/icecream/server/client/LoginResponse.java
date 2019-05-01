package com.icecream.server.client;

public class LoginResponse {
  private String message;
  private int msgCode;
  private String token;


  public LoginResponse() {
  }

  public LoginResponse(String message, int msgCode, String token) {
    this.message = message;
    this.msgCode = msgCode;
    this.token = token;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getMsgCode() {
    return msgCode;
  }

  public void setMsgCode(int msgCode) {
    this.msgCode = msgCode;
  }

  @Override
  public String toString() {
    return "LoginResponse{" +
            "message='" + message + '\'' +
            ", msgCode=" + msgCode +
            ", token='" + token + '\'' +
            '}';
  }
}
