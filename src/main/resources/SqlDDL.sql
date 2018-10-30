CREATE DATABASE db_wechatserver;
--
CREATE TABLE resp_message_news(
	news_id int AUTO_INCREMENT=1001,
	to_username varchar(50) NOT NULL,
	from_username vrachar(50) NOT NULL,
	create_time int(14) NOT NULL,
	msg_type varchar(10) NOT NULL,
	article_count int NOT NULL,
	article_id varchar(10) NOT NULL ,
	PRIMARY KEY (news_id),
	UNIQUE (article_id),
	CHECK (article_count>0)
);
CREATE INDEX index_news
ON resp_message_news (news_id);
--
CREATE TABLE resp_message_news_dtl(
	dtl_id int PRIMARY KEY AUTO_INCREMENT=1001
	article_id varchar(10) int NOT NULL,
	article_title varchar(255) DEFAULT '',
	article_description varchar(255) DEFAULT '',
	article_picurl varchar(255) NOT NULL,
	article_url varchar(255) NOT NULL,
	PRIMARY KEY (dtl_id)
);
CREATE INDEX index_news_dtl
ON resp_message_news_dtl (article_id);