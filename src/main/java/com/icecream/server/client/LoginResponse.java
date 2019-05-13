package com.icecream.server.client;

/**
 * This class is a data class for login response.
 *
 * @author NicoleMayer
 */
public class LoginResponse {
  private String message;
  private int msgCode;
  private String token;

  /**
   * This is a constructor for LoginResponse class.
   */
  public LoginResponse() {
  }

  /**
   * This is a constructor for LoginResponse class.
   * @param message The description of the response.
   * @param msgCode The number for this message.
   * @param token The unique string for user identification.
   */
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

  /**
   * String representation of the login response.
   *
   * @return String stands for the login response.
   */
  @Override
  public String toString() {
    return "LoginResponse{" +
            "message='" + message + '\'' +
            ", msgCode=" + msgCode +
            ", token='" + token + '\'' +
            '}';
  }
}
