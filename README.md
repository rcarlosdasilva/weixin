#Aha-Weixin
微信API的封装，支持多个公众号同时使用，操作简单。

Maven仓库
```
    <dependency>
      <groupId>io.github.rcarlosdasilva</groupId>
      <artifactId>weixin</artifactId>
      <version>0.1</version>
    </dependency>
```

##注册公众号信息
使用前，需要先将公众号注册一下
```
只有一个公众号时：
    WeixinRegistry.registryUnique("appid", "appsecret");
当有多个公众号时：
    WeixinRegistry.registry("key1", "appid", "appsecret");
    WeixinRegistry.registry("key2", "appid", "appsecret");
```
如果在公众平台官网的开发-基本设置页面设置了回调接口，那么需要设置token与EncodingAESKey信息。建议使用安全模式。
```
    WeixinRegistry.registry("key1", "appid", "appsecret")
        .setServerSecurity("token", "aesKey", EncryptionType.SAFETY);
```
##接口调用
使用
```
    Weixin.withUnique();
    或
    Weixin.with("key1");
```
可以开始调用各个功能的接口，例如``Weixin.withUnique().user()``。分别对应的是：
```
用户相关： .user();
用户组相关： .userGroup(); // 微信已弃用
用户标签相关： .userTag();
认证相关： .certificate();
公共api： .common(); // 例如获取微信服务器ip、生成短连接等
客服相关： .custom();
非api，帮助相关： .helper(); // 例如生成web授权链接，生成JS-SDK签名等
素材相关： .media();
自定义菜单相关： .menu();
消息相关： .message(); // 模板消息与图文消息
数据统计相关： .statistics();
模板消息相关： .template(); // 模板的消息推送在 message() 里
```
在不同的功能入口中再调用具体接口。所有入口都已将API 100%封装。
例如：
```
判断ip是否为微信服务器ip
    Weixin.withUnique().helper().isLegalRequestIp("you held ip");
生成web授权链接：
    Weixin.withUnique().helper().webAuthorize(WebAuthorizeScope.BASE, "url redirect to");
通过web授权code获取用户open_id：
    Weixin.withUnique().certificate().askWebAuthorizeAccessToken("code");
获取用户信息：
    Weixin.withUnique().user().getUserInfo("open_id");
```

##微信通知
微信在发送消息到“微信平台中设置的”回调接口时，需要将微信发送过来的内容进行解析。（微信发送消息是在request body中，另外会传msg_signature, timestamp, nonce三个参数）
假设获取到的内容变量为body，那么解析过程为：
```
    Notification notification = NotificationParser.parse(body);
    Account account = Weixin.withUnique().info();
    notification = Encryptor.decrypt(account.getToken(), account.getAesKey(),
        notification.getCiphertext(), signature, timestamp, nonce);
    // 以上代码获取到微信消息内容，并解密，notification中就是消息解析后的内容
    
    // 要响应微信的话
    NotificationResponseBuilder builder = Builder.buildNotificationResponse().with(notification); // 针对当前消息生成回复构建器
    NotificationResponsePlaintext response = builder.responseText("simple text response").build(); // 构建一个简单文本响应
    String plainText = NotificationParser.toXml(response); // 获取到加密前的回复xml
    String encrypted = Encryptor.encrypt(account.getAppId(), account.getToken(), account.getAesKey(), plainText); // 对回复加密
    return encrypted;
```
以上代码只能认为判断微信消息的类型与各种不同情况，为了方便处理微信发送的各种不同的消息，提供了NotificationHandlerProxy代理类与NotificationHandler接口，来代替上面的解析方案。NotificationHandler定义了所有微信可能的消息类型，可以实现该接口，并在各种不同的消息对应的方法中实现自己的流程。DefaultNotificationHandler是NotificationHandler的默认实现。
```
假设已经有实现NotificationHandler接口的类，并实例化为变量handler：
    NotificationHandlerProxy.proxy(handler); // 指定代理
    return NotificationHandlerProxy.instance().process(body); // 处理明文模式
    return NotificationHandlerProxy.instance().process(body, signature, timestamp, nonce); // 处理安全或兼容模式
```