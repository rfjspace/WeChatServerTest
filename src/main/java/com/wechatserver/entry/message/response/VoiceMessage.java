package com.wechatserver.entry.message.response;

/**
 * ClassName: VoiceMessage
 * 
 * @Description: 语音消息
 */
public class VoiceMessage extends BaseMessage {
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

}
