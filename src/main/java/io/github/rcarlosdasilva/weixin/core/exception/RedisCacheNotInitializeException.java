package io.github.rcarlosdasilva.weixin.core.exception;

public class RedisCacheNotInitializeException extends RuntimeException {

  private static final long serialVersionUID = 218157389779602941L;

  public RedisCacheNotInitializeException(String message) {
    super(message);
  }

}
