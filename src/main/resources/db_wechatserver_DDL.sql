CREATE DATABASE db_wechatserver;
--
CREATE TABLE db_wechatserver.resp_message_news(
	news_id int AUTO_INCREMENT,
	to_username varchar(50) NOT NULL,
	from_username varchar(50) NOT NULL,
	create_time int(14) NOT NULL,
	msg_type varchar(10) NOT NULL,
	article_count int NOT NULL,
	article_id varchar(10) NOT NULL ,
	PRIMARY KEY (news_id),
	UNIQUE (article_id),
	CHECK (article_count>0)
);
CREATE INDEX index_news
ON db_wechatserver.resp_message_news (news_id);
--
CREATE TABLE db_wechatserver.resp_message_news_dtl(
	dtl_id int AUTO_INCREMENT,
	article_id varchar(10) int NOT NULL,
	article_title varchar(255),
	article_description varchar(255),
	article_picurl varchar(255) NOT NULL,
	article_url varchar(255) NOT NULL,
	PRIMARY KEY (dtl_id)
);
CREATE INDEX index_news_dtl
ON db_wechatserver.resp_message_news_dtl (dtl_id);