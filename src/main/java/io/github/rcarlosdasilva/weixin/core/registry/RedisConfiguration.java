package io.github.rcarlosdasilva.weixin.core.registry;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisConfiguration {

  private JedisPoolConfig config = new JedisPoolConfig();
  private String host = Protocol.DEFAULT_HOST;
  private int port = Protocol.DEFAULT_PORT;
  private int timeout = Protocol.DEFAULT_TIMEOUT;
  private String password = null;
  private int database = Protocol.DEFAULT_DATABASE;
  private boolean useSsl = false;

  public JedisPoolConfig getConfig() {
    return config;
  }

  public RedisConfiguration setConfig(JedisPoolConfig config) {
    this.config = config;
    return this;
  }

  public String getHost() {
    return host;
  }

  public RedisConfiguration setHost(String host) {
    this.host = host;
    return this;
  }

  public int getPort() {
    return port;
  }

  public RedisConfiguration setPort(int port) {
    this.port = port;
    return this;
  }

  public int getTimeout() {
    return timeout;
  }

  public RedisConfiguration setTimeout(int timeout) {
    this.timeout = timeout;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public RedisConfiguration setPassword(String password) {
    this.password = password;
    return this;
  }

  public int getDatabase() {
    return database;
  }

  public RedisConfiguration setDatabase(int database) {
    this.database = database;
    return this;
  }

  public boolean isUseSsl() {
    return useSsl;
  }

  public RedisConfiguration setUseSsl(boolean useSsl) {
    this.useSsl = useSsl;
    return this;
  }

}
