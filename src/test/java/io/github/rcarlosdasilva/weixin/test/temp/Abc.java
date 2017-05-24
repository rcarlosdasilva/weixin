package io.github.rcarlosdasilva.weixin.test.temp;

import io.github.rcarlosdasilva.weixin.core.encryption.Encryptor;
import io.github.rcarlosdasilva.weixin.core.parser.NotificationParser;
import io.github.rcarlosdasilva.weixin.model.notification.Notification;

public class Abc {

  public static void main(String[] args) {
    String content = "<xml>" +
    "<AppId><![CDATA[wxbd1db15d564fc127]]></AppId>" +
    "<Encrypt><![CDATA[5TZg3XF+nT7kIDd96H/9yN5E8wBH7TjFbnNhCN/5+tKWVEx2HNdcmI2VPoGkg4gPQr3uDo8k7mpPylH87dbZqU7FjJW1E6VuoX2u4pmjERpfKmeCcrDIe5HA8yFqiM+9UYfimMrYp2c5eust2gKTmH/6LcB8zzOsjm+PXZjdyz2wMixP8C8dSHSA4kjmSkXtg+mOOTtEfzkG14D4pt3EJmSryRdTrHZaO8zEvWm8wSNIsB0EDNGbfEy+o2C2OHMoUlbaMX/2WQULJzKsLTH+0V+gbdHd2Sbhrrup+hkZ+3jw1EqwwiQlRSQ4+1CfdbPvYNoWd4S7enwxWejQaDKE6zYpDX6/+EGmZQ76MYMnSdqGoeRAbC9tKHoS3ylQRlem+5XbkyCp1P4FhBq9NUL93DhlXjIJc47RE/C1OiNgvrOiz/BopJdFPcL4k+5jg5gQ86ElQNw4zXYgYuyGDke24w==]]></Encrypt>" +
"</xml>";
    Notification notification = NotificationParser.parse(content);
    System.out.println(notification);

    String token = "abc";
    String aeskey = "UR3IhaHhpQk2cYSrtJ53HuEdI8AdqtLqptWCkaAaedO";
    String signature = "28460895b2f1003463cec461135b478c2fb0a42b";
    long timestamp = 1495531419;
    String nonce = "317455291";
    notification = Encryptor.decrypt(token, aeskey, notification.getCiphertext(), signature,
        timestamp, nonce);

    System.out.println(notification);
  }

}
