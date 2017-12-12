package io.github.rcarlosdasilva.weixin.core.inspect;

public interface Worker<T> {

  boolean support(ProblemObject<T> problem);

  void doit(ProblemObject<T> problem);

}
