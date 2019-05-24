package com.icecream.server.client;

/**
 * This class is a data class for normal response.
 *
 * @author NicoleMayer
 */
public class NormalResponse {
  private String usage;
  private String message;
  private int msgCode;

  /**
   * This is a constructor for NormalResponse class.
   *
   * @param usage The description of the usage of this response.
   */
  public NormalResponse(String usage) {
    this.usage = usage;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getMsgCode() {
    return msgCode;
  }

  public void setMsgCode(int msgCode) {
    this.msgCode = msgCode;
  }

  /**
   * String representation of the normal response.
   *
   * @return String stands for the normal response.
   */
  @Override
  public String toString() {
    return "NormalResponse{"
            + "usage='" + usage + '\''
            + ", message='" + message + '\''
            + ", msgCode=" + msgCode
            + '}';
  }
}
