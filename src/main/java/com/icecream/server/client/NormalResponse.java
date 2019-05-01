package com.icecream.server.client;

public class NormalResponse {
  private String usage;
  private String message;
  private int msgCode;

  public NormalResponse(String usage) {
    this.usage = usage;
  }

  public String getUsage() {
    return usage;
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

  @Override
  public String toString() {
    return "NormalResponse{" +
            "usage='" + usage + '\'' +
            ", message='" + message + '\'' +
            ", msgCode=" + msgCode +
            '}';
  }
}
