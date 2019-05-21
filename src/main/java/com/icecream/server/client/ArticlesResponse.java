package com.icecream.server.client;

import com.icecream.server.entity.Article;

import java.util.List;

/**
 * This class is a data class for article list response.
 *
 * @author NicoleMayer
 */
public class ArticlesResponse {
  private String message;
  private int msgCode;
  private List<Article> data;

  /**
   * This is a constructor for ArticlesResponse class.
   */
  public ArticlesResponse() {
  }

  /**
   * This is a constructor for ArticlesResponse class.
   *
   * @param message The description of the response.
   * @param msgCode The number for this message.
   * @param data    The data of article list if succeed.
   */
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

  /**
   * String representation of the article list response.
   *
   * @return String stands for the article list response.
   */
  @Override
  public String toString() {
    return "ArticlesResponse{" +
            "message='" + message + '\'' +
            ", msgCode=" + msgCode +
            ", data=" + data +
            '}';
  }
}
