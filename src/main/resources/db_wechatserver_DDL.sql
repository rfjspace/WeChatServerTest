CREATE DATABASE db_wechatserver;
--
CREATE TABLE db_wechatserver.resp_message_main(
	increment_id int AUTO_INCREMENT,
	to_username varchar(50) NOT NULL,
	from_username varchar(50) NOT NULL,
	create_time bigint(17) NOT NULL,
	msg_type varchar(10) NOT NULL,
	article_count int,
	article_id varchar(17),
	image_id varchar(17),
	music_id varchar(17),
	video_id varchar(17),
	voice_id varchar(17),
	text_id varchar(17),
	UNIQUE (article_id),
	UNIQUE (image_id),
	UNIQUE (music_id),
	UNIQUE (video_id),
	UNIQUE (voice_id),
	UNIQUE (text_id),
	CHECK (article_count>0),
	CONSTRAINT pk_message PRIMARY KEY (increment_id,to_username,from_username)
);
CREATE INDEX index_news
ON db_wechatserver.resp_message_main (to_username);
--
CREATE TABLE db_wechatserver.resp_message_news_dtl(
	increment_id int AUTO_INCREMENT,
	article_id varchar(17) NOT NULL,
	article_title varchar(255),
	article_picurl varchar(255) NOT NULL,
	article_url varchar(255) NOT NULL,
	article_description varchar(255),
	CONSTRAINT pk_newsdtl PRIMARY KEY (increment_id,article_id)
);
CREATE INDEX index_news_dtl
ON db_wechatserver.resp_message_news_dtl (article_id);
--
CREATE TABLE db_wechatserver.resp_message_image_dtl(
	increment_id int AUTO_INCREMENT,
	image_id varchar(17) NOT NULL,
	image_mediaid varchar(255) NOT NULL,
	image_description varchar(255),
	UNIQUE(image_id),
	CONSTRAINT pk_imagedtl PRIMARY KEY (increment_id,image_id)
);
CREATE INDEX index_image_dtl
ON db_wechatserver.resp_message_image_dtl (image_id);
--
CREATE TABLE db_wechatserver.resp_message_music_dtl(
	increment_id int AUTO_INCREMENT,
	music_id varchar(17) NOT NULL,
	music_title varchar(255) NOT NULL,
	music_musicurl varchar(255) NOT NULL,
	music_hqmusicurl varchar(255) NOT NULL,
	music_thumbmediaid varchar(255),
	music_description varchar(255),
	UNIQUE(music_id),
	CONSTRAINT pk_musicdtl PRIMARY KEY (increment_id,music_id)
);
CREATE INDEX index_music_dtl
ON db_wechatserver.resp_message_music_dtl (music_id);
--
CREATE TABLE db_wechatserver.resp_message_video_dtl(
	increment_id int AUTO_INCREMENT,
	video_id varchar(17) NOT NULL,
	video_mediaid varchar(255) NOT NULL,
	video_title varchar(255) NOT NULL,
	video_description varchar(255),
	UNIQUE(video_id),
	CONSTRAINT pk_videodtl PRIMARY KEY (increment_id,video_id)
);
CREATE INDEX index_video_dtl
ON db_wechatserver.resp_message_video_dtl (video_id);
--
CREATE TABLE db_wechatserver.resp_message_voice_dtl(
	increment_id int AUTO_INCREMENT,
	voice_id varchar(17) NOT NULL,
	voice_mediaid varchar(255) NOT NULL,
	voice_description varchar(255),
	UNIQUE(voice_id),
	CONSTRAINT pk_voicedtl PRIMARY KEY (increment_id,voice_id)
);
CREATE INDEX index_voice_dtl
ON db_wechatserver.resp_message_voice_dtl (voice_id);
--
CREATE TABLE db_wechatserver.resp_message_text_dtl(
	increment_id int AUTO_INCREMENT,
	text_id varchar(17) NOT NULL,
	text_content varchar(255) NOT NULL,
	text_description varchar(255),
	UNIQUE(text_id),
	CONSTRAINT pk_voicedtl PRIMARY KEY (increment_id,text_id)
);
CREATE INDEX index_text_dtl
ON db_wechatserver.resp_message_text_dtl (text_id);
--
CREATE TABLE db_wechatserver.info_accounts(
	username varchar(11) NOT NULL,
	password varchar(10) NOT NULL,
	createtime varchar(17) NOT NULL,
	open_id varchar(35),
	UNIQUE(open_id),
	CONSTRAINT pk_accounts PRIMARY KEY (username)
);
CREATE INDEX index_account
ON db_wechatserver.info_accounts (username,open_id);
--
CREATE TABLE db_wechatserver.info_users(
	open_id varchar(35) NOT NULL,
	nick_name varchar(255) NOT NULL,
	sex varchar(1) NOT NULL,
	province varchar(50),
	city varchar(50),
	country varchar(50),
	headimg_url varchar(255),
	privilege varchar(255),
	union_id varchar(35),
	language varchar(10),
	CONSTRAINT pk_user PRIMARY KEY (open_id)
);
CREATE INDEX index_user
ON db_wechatserver.info_users (open_id);
--
CREATE table db_wechatserver.info_menu(
	increment_id int AUTO_INCREMENT,
	button_type varchar(5) NOT NULL,
	button_name varchar(10) NOT NULL,
	click_key varchar(50),
	view_url  varchar(255),
	menu_level int(1) NOT NULL,
	super_name varchar(10),
	display int,
	CONSTRAINT pk_menu PRIMARY KEY(increment_id)
);
CREATE INDEX index_menu 
ON db_wechatserver.info_menu(increment_id);




