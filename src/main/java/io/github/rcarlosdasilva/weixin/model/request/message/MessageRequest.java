package io.github.rcarlosdasilva.weixin.model.request.message;

import io.github.rcarlosdasilva.weixin.model.request.message.bean.Card;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Image;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Music;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsExternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsInternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Text;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Video;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Voice;

public interface MessageRequest {

  void setText(Text text);

  void setImage(Image image);

  void setVoice(Voice voice);

  void setVideo(Video video);

  void setMusic(Music music);

  void setNewsExternal(NewsExternal newsExternal);

  void setNewsInternal(NewsInternal newsInternal);

  void setCard(Card card);

}
