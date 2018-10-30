package com.wechatserver.dispatcher;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.wechatserver.entry.menu.ButtonKeys;
import com.wechatserver.entry.message.response.Article;
import com.wechatserver.entry.message.response.Image;
import com.wechatserver.entry.message.response.ImageMessage;
import com.wechatserver.entry.message.response.Music;
import com.wechatserver.entry.message.response.MusicMessage;
import com.wechatserver.entry.message.response.NewsMessage;
import com.wechatserver.entry.message.response.TextMessage;
import com.wechatserver.entry.message.response.Video;
import com.wechatserver.entry.message.response.VideoMessage;
import com.wechatserver.entry.message.response.Voice;
import com.wechatserver.entry.message.response.VoiceMessage;
import com.wechatserver.util.MsgHandleUtil;
import com.wechatserver.util.ResourceLoadUtil;
import com.wechatserver.util.WeChatApiUtil;
import com.wechatserver.util.XStreamUtil;

/**
 * ClassName: EventDispatcher
 * 
 * @Description: 事件消息业务分发器
 */
public class EventDispatcher {
	public static String processEvent(Map<String, String> map, HttpServletResponse resp) {
		// 获取事件类型
		String msgType = map.get("Event").toString();
		// base消息
		String toUserName = map.get("FromUserName").toString();
		String fromUserName = map.get("ToUserName").toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyymmddHHmmss");
		Long createTime = Long.parseLong(format.format(new Date()));
		switch (msgType) {

		case MsgHandleUtil.EVENT_TYPE_SUBSCRIBE:// 关注事件
		{
			TextMessage tm = new TextMessage();
			tm.setToUserName(toUserName);
			tm.setFromUserName(fromUserName);
			tm.setCreateTime(createTime);
			tm.setMsgType("text");
			tm.setContent("感谢关注，真好！");
			return XStreamUtil.toXML(tm);
		}
		case MsgHandleUtil.EVENT_TYPE_UNSUBSCRIBE:// 取消关注事件
		{
			System.out.println("取消关注事件");
			break;
		}
		case MsgHandleUtil.EVENT_TYPE_SCAN:// 扫描二维码事件
		{
			// TODO
			System.out.println("扫描二维码事件");
			break;
		}
		case MsgHandleUtil.EVENT_TYPE_LOCATION:// 位置上报事件
		{
			// TODO
			System.out.println("位置上报事件");
			break;
		}
		case MsgHandleUtil.EVENT_TYPE_CLICK:// 自定义菜单点击事件
		{
			String clickKey = map.get("EventKey");
			switch (clickKey) {
			case ButtonKeys.BUTTON_KEYS_F001:// 最新消息
				NewsMessage newsMsg = new NewsMessage();
				newsMsg.setToUserName(toUserName);
				newsMsg.setFromUserName(fromUserName);
				newsMsg.setCreateTime(createTime);
				newsMsg.setMsgType("news");
				newsMsg.setArticleCount(3);
				List<Article> articles = new ArrayList<Article>();
				Article art1 = new Article();
				art1.setTitle("微信公众平台图片最合适尺寸大小01");
				art1.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art1.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art1);
				Article art2 = new Article();
				art2.setTitle("微信公众平台图片最合适尺寸大小02");
				art2.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art2.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art2);
				Article art3 = new Article();
				art3.setTitle("微信公众平台图片最合适尺寸大小03");
				art3.setPicUrl(
						"https://imgsa.baidu.com/exp/w=500/sign=6f5c5be5d91b0ef46ce8985eedc651a1/78310a55b319ebc47cdb65528926cffc1f17165d.jpg");
				art3.setUrl("https://jingyan.baidu.com/article/a378c960ea051eb329283070.html");
				articles.add(art3);
				newsMsg.setArticles(articles);
				return XStreamUtil.toXML(newsMsg);
			case ButtonKeys.SUBBUTTON_KEYS_S032:// 客户服务
				TextMessage serverKF = new TextMessage();
				serverKF.setFromUserName(fromUserName);
				serverKF.setToUserName(toUserName);
				serverKF.setCreateTime(createTime);
				serverKF.setMsgType("text");
				serverKF.setContent("对不起，客服跑了！");
				return XStreamUtil.toXML(serverKF);
			case ButtonKeys.SUBBUTTON_KEYS_S021:// 文本测试
				TextMessage textMsg = new TextMessage();
				textMsg.setFromUserName(fromUserName);
				textMsg.setToUserName(toUserName);
				textMsg.setCreateTime(createTime);
				textMsg.setMsgType("text");
				textMsg.setContent("文本测试");
				return XStreamUtil.toXML(textMsg);
			case ButtonKeys.SUBBUTTON_KEYS_S022:// 图片测试
				File file01 = new ResourceLoadUtil().fileLoad("/images/image01.jpg");
				String mediaId01 = WeChatApiUtil.getUploadMediaId(file01, "image");
				if (mediaId01 == "") {
					return "";
				}
				ImageMessage imageMsg = new ImageMessage();
				imageMsg.setFromUserName(fromUserName);
				imageMsg.setToUserName(toUserName);
				imageMsg.setCreateTime(createTime);
				imageMsg.setMsgType("image");
				Image image = new Image();
				image.setMediaId(mediaId01);
				imageMsg.setImage(image);
				return XStreamUtil.toXML(imageMsg);
			case ButtonKeys.SUBBUTTON_KEYS_S023:// 音乐测试
				File file02 = new ResourceLoadUtil().fileLoad("/images/image02.jpg");
				String mediaId02 = WeChatApiUtil.getUploadMediaId(file02, "image");
				MusicMessage musicMsg = new MusicMessage();
				musicMsg.setFromUserName(fromUserName);
				musicMsg.setToUserName(toUserName);
				musicMsg.setCreateTime(createTime);
				musicMsg.setMsgType("music");
				Music music = new Music();
				music.setTitle("这座城市-蒋雪儿");
				music.setMusicUrl("http://www.ytmp3.cn/down/54178.mp3");
				music.setThumbMediaId(mediaId02);
				music.setHQMusicUrl("http://www.ytmp3.cn/down/54178.mp3");
				music.setDescription("音乐测试");
				musicMsg.setMusic(music);
				return XStreamUtil.toXML(musicMsg);
			case ButtonKeys.SUBBUTTON_KEYS_S024:// 视频测试
				File file03 = new ResourceLoadUtil().fileLoad("/videos/video01.mp4");
				String mediaId03 = WeChatApiUtil.getUploadMediaId(file03, "video");
				if (mediaId03 == "") {
					return "";
				}
				VideoMessage videoMsg = new VideoMessage();
				videoMsg.setFromUserName(fromUserName);
				videoMsg.setToUserName(toUserName);
				videoMsg.setCreateTime(createTime);
				videoMsg.setMsgType("video");
				Video video = new Video();
				video.setTitle("视频测试");
				video.setMediaId(mediaId03);
				video.setDescription("视频测试");
				videoMsg.setVideo(video);
				return XStreamUtil.toXML(videoMsg);
			case ButtonKeys.SUBBUTTON_KEYS_S025:// 语音测试
				File file04 = new ResourceLoadUtil().fileLoad("/voices/voice01.mp3");
				String mediaId04 = WeChatApiUtil.getUploadMediaId(file04, "voice");
				if (mediaId04 == "") {
					return "";
				}
				VoiceMessage voiceMsg = new VoiceMessage();
				voiceMsg.setFromUserName(fromUserName);
				voiceMsg.setToUserName(toUserName);
				voiceMsg.setCreateTime(createTime);
				voiceMsg.setMsgType("voice");
				Voice voice = new Voice();
				voice.setMediaId(mediaId04);
				voiceMsg.setVoice(voice);
				return XStreamUtil.toXML(voiceMsg);
			default:
				break;
			}
		}
		case MsgHandleUtil.EVENT_TYPE_VIEW: // 自定义菜单View事件
		{
			try {
				boolean checkLogin = false;
				if (checkLogin) {
					resp.sendRedirect("/WeChatServerTest/login.jsp");
				} else {
					resp.sendRedirect("/WeChatServerTest/register.jsp");
				}
				System.out.println("自定义菜单 View 事件");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		default:
			break;
		}
		return "";
	}
}
