package com.wechatserver.dispatcher;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.wechatserver.entry.menu.ButtonKeys;
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
import com.wechatserver.mapper.MaterialFileMapper;
import com.wechatserver.mapper.RespNewsMapper;
import com.wechatserver.util.MsgHandleUtil;
import com.wechatserver.util.MybatisUtil;
import com.wechatserver.util.ResourceLoadUtil;
import com.wechatserver.util.WeChatApiUtil;
import com.wechatserver.util.XStreamUtil;

/**
 * ClassName: EventDispatcher
 * 
 * @Description: 事件消息业务分发器
 */
public class EventDispatcher {
	// 执行数据库操作的session
	static SqlSession sqlSession = MybatisUtil.getSession();

	public static String processEvent(Map<String, String> map) {
		// 获取事件类型
		String msgType = map.get("Event").toString();
		// base消息
		String toUserName = map.get("FromUserName").toString();
		String fromUserName = map.get("ToUserName").toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSss");
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
			{
				RespNewsMapper respNM = sqlSession.getMapper(RespNewsMapper.class);
				NewsMessage newsMsg = respNM.selectLastNewsMessage();
				newsMsg.setToUserName(toUserName);
				newsMsg.setFromUserName(fromUserName);
				sqlSession.commit();
				return XStreamUtil.toXML(newsMsg);
			}
			case ButtonKeys.SUBBUTTON_KEYS_S032:// 客户服务
			{
				TextMessage serverKF = new TextMessage();
				serverKF.setFromUserName(fromUserName);
				serverKF.setToUserName(toUserName);
				serverKF.setCreateTime(createTime);
				serverKF.setMsgType("text");
				serverKF.setContent("对不起，客服跑了！");
				return XStreamUtil.toXML(serverKF);
			}
			case ButtonKeys.SUBBUTTON_KEYS_S021:// 文本测试
			{
				TextMessage textMsg = new TextMessage();
				textMsg.setFromUserName(fromUserName);
				textMsg.setToUserName(toUserName);
				textMsg.setCreateTime(createTime);
				textMsg.setMsgType("text");
				textMsg.setContent("文本测试");
				return XStreamUtil.toXML(textMsg);
			}
			case ButtonKeys.SUBBUTTON_KEYS_S022:// 图片测试
			{
				ImageMessage imageMsg = new ImageMessage();
				imageMsg.setFromUserName(fromUserName);
				imageMsg.setToUserName(toUserName);
				imageMsg.setCreateTime(createTime);
				imageMsg.setMsgType("image");
				Image image = new Image();
				image.setMediaId(getMediaId("image01.jpg", "image"));
				imageMsg.setImage(image);
				return XStreamUtil.toXML(imageMsg);
			}
			case ButtonKeys.SUBBUTTON_KEYS_S023:// 音乐测试
			{
				// File file02 = new ResourceLoadUtil().fileLoad("/images/image02.jpg");
				// String mediaId02 = WeChatApiUtil.getUploadMediaId(file02, "image");
				MusicMessage musicMsg = new MusicMessage();
				musicMsg.setFromUserName(fromUserName);
				musicMsg.setToUserName(toUserName);
				musicMsg.setCreateTime(createTime);
				musicMsg.setMsgType("music");
				Music music = new Music();
				music.setTitle("这座城市-蒋雪儿");
				music.setMusicUrl("http://www.ytmp3.cn/down/54178.mp3");
				music.setThumbMediaId(getMediaId("image02.jpg", "image"));
				music.setHQMusicUrl("http://www.ytmp3.cn/down/54178.mp3");
				music.setDescription("音乐测试");
				musicMsg.setMusic(music);
				return XStreamUtil.toXML(musicMsg);
			}
			case ButtonKeys.SUBBUTTON_KEYS_S024:// 视频测试
			{
				// File file03 = new ResourceLoadUtil().fileLoad("/videos/video01.mp4");
				// String mediaId03 = WeChatApiUtil.getUploadMediaId(file03, "video");
				// if (mediaId03 == "") {
				// return "";
				// }
				VideoMessage videoMsg = new VideoMessage();
				videoMsg.setFromUserName(fromUserName);
				videoMsg.setToUserName(toUserName);
				videoMsg.setCreateTime(createTime);
				videoMsg.setMsgType("video");
				Video video = new Video();
				video.setTitle("视频测试");
				video.setMediaId(getMediaId("video01.mp4", "video"));
				video.setDescription("视频测试");
				videoMsg.setVideo(video);
				return XStreamUtil.toXML(videoMsg);
			}
			case ButtonKeys.SUBBUTTON_KEYS_S025:// 语音测试
			{
				// File file04 = new ResourceLoadUtil().fileLoad("/voices/voice01.mp3");
				// String mediaId04 = WeChatApiUtil.getUploadMediaId(file04, "voice");
				// if (mediaId04 == "") {
				// return "";
				// }
				VoiceMessage voiceMsg = new VoiceMessage();
				voiceMsg.setFromUserName(fromUserName);
				voiceMsg.setToUserName(toUserName);
				voiceMsg.setCreateTime(createTime);
				voiceMsg.setMsgType("voice");
				Voice voice = new Voice();
				voice.setMediaId(getMediaId("voice01.mp3", "voice"));
				voiceMsg.setVoice(voice);
				return XStreamUtil.toXML(voiceMsg);
			}
			default:
				break;
			}

		}
		case MsgHandleUtil.EVENT_TYPE_VIEW: // 自定义菜单View事件
		{
			System.out.println("自定义菜单 View 事件");
			break;
		}
		default:
			break;
		}
		return "";
	}

	private static String getMediaId(String name, String type) {
		MaterialFileMapper mfm = sqlSession.getMapper(MaterialFileMapper.class);
		Map<String, String> fileInfo = mfm.selectFileByName(name);
		String location = String.format("/%ss/", type); 
		String mediaId = "";
		if (null == fileInfo || diffDate(fileInfo.get("mat_timestamp"), 3.0D)) {
			if (null != fileInfo) {
				mfm.deleteFileByName(name);
			}
			File file = new ResourceLoadUtil().fileLoad(location.concat(name));
			mediaId = WeChatApiUtil.getUploadMediaId(file, type);
			if (mediaId == "") {
				return "";
			}
			Map<String, String> fInfo = new HashMap<String, String>();
			fInfo.put("matName", name);
			fInfo.put("matType", type);
			fInfo.put("matId", mediaId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String curDate = sdf.format(new Date());
			fInfo.put("matTimestamp", curDate);
			mfm.insertFile(fInfo);
			sqlSession.commit();
		} else {
			mediaId = fileInfo.get("mat_id");
		}
		return mediaId;
	}

	private static boolean diffDate(String dateStr, Double timeFlg) {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date oldDate = null;
		try {
			oldDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Double dateDiff = ((curDate.getTime() - oldDate.getTime()) / (1000 * 60 * 60 * 24.0));
		if (dateDiff > timeFlg)
			return true;
		else
			return false;
	}
}
