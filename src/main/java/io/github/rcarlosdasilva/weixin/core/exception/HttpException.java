package io.github.rcarlosdasilva.weixin.core.exception;

public class HttpException extends RuntimeException {

  private static final long serialVersionUID = 4622790647531901448L;

  public HttpException(String message) {
    super(message);
  }

}
