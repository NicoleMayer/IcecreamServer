package com.icecream.server.client;

import com.icecream.server.entity.Article;

import java.util.List;

public class ArticlesResponse {
  private String message;
  private int msgCode;
  private List<Article> data;

  public ArticlesResponse(String message, int msgCode, List<Article> data) {
    this.message = message;
    this.msgCode = msgCode;
    this.data = data;
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

  public List<Article> getData() {
    return data;
  }

  public void setData(List<Article> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "FeedsResponse{" +
            "message='" + message + '\'' +
            ", msgCode=" + msgCode +
            ", data=" + data +
            '}';
  }
}
