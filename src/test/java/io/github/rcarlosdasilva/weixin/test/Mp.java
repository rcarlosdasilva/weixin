package io.github.rcarlosdasilva.weixin.test;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;

public class Mp {

  private String name;
  private String id;
  private String secret;

  /** . */
  public Mp(String name, String id, String secret) {
    this.name = name;
    this.id = id;
    this.secret = secret;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  /** . */
  public static void main(String[] args) {
    List<Mp> mps = Lists.newArrayList();
    mps.add(new Mp("齐齐哈尔教育云", "wx7b54b16181a88d2a", "e01fcd5d70a5680a66b150faa1ba4417"));
    mps.add(new Mp("鹤岗市教育云", "wx85214943cc5578ca", "c7417d3e0910920f5320033e5d247af1"));
    mps.add(new Mp("七台河市教育局", "wx8c987fb5ddf68cd9", "51c942e3773542af3f15cf1d35bbfb16"));
    mps.add(new Mp("西海岸新区教育", "wxe39c8f7d67ce8b7d", "cfc86744f537a98cb865a73706985cf9"));
    mps.add(new Mp("淮北市教育局", "wxd5f5146d4605066d", "1f710dfeaeb12bbfb23636f4a894a3bf"));
    mps.add(new Mp("菏泽高新区", "wx5bb598c3e959f75d", "596752e3e0c0a3aa611ec537d0e791ad"));
    mps.add(new Mp("林口县教育局", "wx217d6e7944dd80e2", "c4dc54fabb20a19a855289a73e6a9509"));
    mps.add(new Mp("相山区教育局", "wx455cb6e441ec48c6", "170a599a149386060e15d7f5f0817e7a"));
    mps.add(new Mp("潍坊高新区教育局", "wx295819358df58de9", "059c6900eddf3e1c5760e2c545673dc9"));
    mps.add(new Mp("青云谱区教育局", "wx5690983269d20ec9", "ed1f7f4a6733cf3adc1dbec3260ef883"));
    mps.add(new Mp("佳木斯市教育局", "wx3128e58ac8a7499d", "c6bfa53bed39f3ea3939824ead6bf10c"));
    mps.add(new Mp("滨州市教育局", "wx876cd11041b9697a", "bae2f613473140c9fb5f145366e6ad4f"));
    mps.add(new Mp("潍坊市坊子区教育信息化", "wx059e414180e1be21", "0773f0cc262ff3915bcbcfad20366a8b"));
    mps.add(new Mp("蚌埠淮上区教育局", "wx5c9aca511e748039", "4e719322e5259e484b65eb0a60ca84cd"));
    mps.add(new Mp("临沂兰山区教育局", "wx0b9fc759b613857f", "e39495b0f92c21517916f0fc5158be35"));
    mps.add(new Mp("绥化庆安县", "wxf89c0535c293c9fb", "a3ac45ec2b275eeb3627fb1b465e263d"));
    mps.add(new Mp("菏泽牡丹区教育局", "wx280589fbe567395d", "d50879e26b255b86025c75753a106451"));
    mps.add(new Mp("九江濂溪区教育局", "wxbc5fe3ae895d1fb7", "fcc317adf71f04a8a480e19ec34883e8"));
    mps.add(new Mp("辽源市教育局", "wx4869b4ec6bdbc0d6", "75ae6979a87b154cd2d5ef44a29e62d0"));
    mps.add(new Mp("相山区教育局", "wx8dba0065130a9ced", "d4bfbc2218fe0c99588532543388b56b"));
    mps.add(new Mp("濉溪县教育局", "wx3bb54bed3dc067bc", "ecbb884112c207baa3c511387dfb3512"));
    mps.add(new Mp("莱西市教育局", "wxfcc0dd9760c0b4b0", "659bfec2c06e663a59437a381abc1f2b"));
    mps.add(new Mp("赢信慧通演示", "wxc2cf57f67aadc121", "5f8dff78c7ff4866fe019a90b44bb6ed"));
    mps.add(new Mp("道外区教育局", "wxb0e3274a8655246e", "8810cbda030be6f508fa2f3130a4d268"));
    mps.add(new Mp("新乡市教育局", "wx5690983269d20ec9", "ed1f7f4a6733cf3adc1dbec3260ef883"));
    mps.add(new Mp("夏津县教育局", "wxfe0d459eb60bd876", "59e0efd3ec580b5189c61bb4ee36cc95"));
    mps.add(new Mp("彭州市教育局", "wxe9f076e697514e49", "e97d1c0d5072094b1e7d1c68486e1916"));
    mps.add(new Mp("菏泽高新区", "wx5bb598c3e959f75d", "596752e3e0c0a3aa611ec537d0e791ad"));
    mps.add(new Mp("牡丹发布", "wxf179fe892ac256c0", "c0e998b03458e4c324557d0589847d61"));
    mps.add(new Mp("菏泽高新区教育局", "wxfe895d6f01e2e8b4", "4a1ba969db3cf86072e87ab1ef77864f"));
    mps.add(new Mp("菏泽市鄄城县", "wx01056ea3f97e9c62", "7cda95a3732290d00b86225476459c42"));
    mps.add(new Mp("胶州市教育局", "wxecdc5c4b293dccb3", "37833568dbe6bd18c1585df13688c623"));
    mps.add(new Mp("哈尔滨市教育局", "wxecdc5c4b293dccb3", "37833568dbe6bd18c1585df13688c623"));
    mps.add(new Mp("宣汉县政府", "wxfcc0dd9760c0b4b0", "659bfec2c06e663a59437a381abc1f2b"));
    mps.add(new Mp("连云港市连云区教育", "wxff936a13e0ca828d", "1485cbcef4ddadbdc189f981783c7b1d"));
    mps.add(new Mp("安徽省教育局", "wxbd960f67d09e23f8", "a89f01e55cec561ed89b8d1f758a08e8"));
    mps.add(new Mp("青岛市教育局", "wx4869b4ec6bdbc0d6", "75ae6979a87b154cd2d5ef44a29e62d0"));
    mps.add(new Mp("青岛市教育局", "wxbc5fe3ae895d1fb7", "fcc317adf71f04a8a480e19ec34883e8"));
    mps.add(new Mp("阿城区教育局", "wx7b58348051a7dc2c", "dee9d60651ea31da742f16b79cb80044"));
    mps.add(new Mp("蚌埠市教育局", "wxdc88585365ae4b29", "bc17c7b82d254db43d8c468b8a701e58"));

    mps.add(new Mp("七台河市第九中学", "wxe8e6783f4a2c2c6f", "d78de68d2cff9f54767b6a1c6748bd3a"));
    mps.add(new Mp("七台河市第五小学", "wx88734550a47e81af", "dd4a2aa7db2a4f5591d2dfa6b4c02029"));
    mps.add(new Mp("七台河市第六小学", "wxbd6249b7b2e1ea26", "d9726debfd05e083f822c76215ddf837"));
    mps.add(new Mp("七台河市第二小学", "wx1c64553cfd549514", "9ec61485304c528881c884f38a070fe8"));
    mps.add(new Mp("黑龙江省七台河市第十小学", "wx16be5a45a68bb351", "6f8b62cf2db68e372b899d0360e7ac3a"));
    // mps.add(new Mp("七台河市第四中学", "wxf9ec8ac06594cd32",
    // "f1ce7dd3bc4cf8289ea7efbbcff9aaa1"));
    mps.add(new Mp("七台河市第九小学", "wxbafa8f770647e1d0", "59ea5d6858dea32919bb1318ef79b891"));
    mps.add(new Mp("黑龙江省七台河市第七小学", "wx51edfc9aa076f2b9", "00aa0eb0071196646de1071ba5966422"));
    mps.add(new Mp("青岛昊儿宝贝", "wx5a7b22d3332d98c0", "7a34ea341c4d55dc546f93689a41476e"));
    mps.add(new Mp("青岛永和路小学", "wxad6fff6c39a69d6e", "5b77dc1c8d46023713fec9ed449654d3"));
    mps.add(new Mp("君塘镇人民政府", "wx9a219272f9e9a61f", "1c02e85fb5558ba09bf6d4fd8b3412fc"));
    mps.add(new Mp("六汪镇五道口小学", "wxea8c4f9259e7fa5e", "834e8da884ec9bdca43c96db473d25e4"));
    mps.add(new Mp("卡贝少儿文化艺术中心", "wxd5c9b9955fd912c4", "22475af735ccdf859f2d74c7b4130b0d"));
    mps.add(new Mp("黄岛区亚森木业幼儿园", "wx8d30b016fff8e5cf", "8381fc6cbea5a010db74df727acfa020"));
    mps.add(new Mp("城阳区棘洪滩街道中华埠小学", "wxe194c3033f9ff325", "5217507bc42ed6d7755053f940e62112"));
    mps.add(new Mp("崂山区睿博优胜文化艺术活动中心", "wxbffc45330bbd6c9b", "794b4907d375c62b2d1b1b79beef22ab"));
    mps.add(new Mp("崂山区九水园", "wx5cff8c3687951f3a", "ec05542c346a69368c94ad255338cca3"));
    mps.add(new Mp("郭家瞳阳光园", "wxb3a8e166d4a9e528", "4410eeafe58bc78187c5323100aa255b"));
    mps.add(new Mp("平度市山水龙苑", "wx28dea77bf47d450a", "59ead25f8b52a7a6531f77a4928b844e"));
    mps.add(new Mp("黄岛区集合号幼儿园", "wxe2e19b1e2d9f74f7", "3e4592bf6ba9772136b4f385f0bb1806"));
    mps.add(new Mp("青岛双星海逸", "wx0b0dac77c551ed79", "a3f37a9519e6f91712efcfddbf295463"));
    mps.add(new Mp("青岛福林小学", "wx7c044435f4e5c371", "6a80524743284986b780f937ee933caf"));
    mps.add(new Mp("黄岛区胶河经济区后立柱小学", "wxcb425709d6f10103", "1caf508b9db5ebda01a59f2b6d27824a"));
    mps.add(new Mp("崂山区青山小学", "wxc44b82a38c13af88", "341e6811fa80064b225cf1763d985d91"));
    mps.add(new Mp("陵城区陵城镇明德小学", "wxee909cc25c9c37a7", "03ab7743f367a6ed653915ac0ed3024c"));
    mps.add(new Mp("尚志朝中", "wxcec47068a1ece9d1", "bde879408439cd4097fcea99be195709"));
    // mps.add(new Mp("拓东日盛", "wx1e43acce998e687b",
    // "c88d5e37e79daf5bbcccb833f05dc7c4"));

    for (Mp mp : mps) {
      WeixinRegistry.registryUnique(mp.getId(), mp.getSecret());
      UserOpenIdListResponse resp = Weixin.withUnique().user().listAllUsersOpenId();
      System.out.println("[ " + mp.getName() + " ] 总用户数：" + resp.getTotal());
    }
  }

}
