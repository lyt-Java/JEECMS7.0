DROP TABLE IF EXISTS `jc_acquisition`;
CREATE TABLE `jc_acquisition` (
  `acquisition_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `channel_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `acq_name` varchar(50) NOT NULL COMMENT '采集名称',
  `start_time` datetime default NULL COMMENT '开始时间',
  `end_time` datetime default NULL COMMENT '停止时间',
  `status` int(11) NOT NULL default '0' COMMENT '当前状态(0:静止;1:采集;2:暂停)',
  `curr_num` int(11) NOT NULL default '0' COMMENT '当前号码',
  `curr_item` int(11) NOT NULL default '0' COMMENT '当前条数',
  `total_item` int(11) NOT NULL default '0' COMMENT '每页总条数',
  `pause_time` int(11) NOT NULL default '0' COMMENT '暂停时间(毫秒)',
  `page_encoding` varchar(20) NOT NULL default 'GBK' COMMENT '页面编码',
  `plan_list` longtext COMMENT '采集列表',
  `dynamic_addr` varchar(255) default NULL COMMENT '动态地址',
  `dynamic_start` int(11) default NULL COMMENT '页码开始',
  `dynamic_end` int(11) default NULL COMMENT '页码结束',
  `linkset_start` varchar(255) default NULL COMMENT '内容链接区开始',
  `linkset_end` varchar(255) default NULL COMMENT '内容链接区结束',
  `link_start` varchar(255) default NULL COMMENT '内容链接开始',
  `link_end` varchar(255) default NULL COMMENT '内容链接结束',
  `title_start` varchar(255) default NULL COMMENT '标题开始',
  `title_end` varchar(255) default NULL COMMENT '标题结束',
  `keywords_start` varchar(255) default NULL COMMENT '关键字开始',
  `keywords_end` varchar(255) default NULL COMMENT '关键字结束',
  `description_start` varchar(255) default NULL COMMENT '描述开始',
  `description_end` varchar(255) default NULL COMMENT '描述结束',
  `content_start` varchar(255) default NULL COMMENT '内容开始',
  `content_end` varchar(255) default NULL COMMENT '内容结束',
  `pagination_start` varchar(255) default NULL COMMENT '内容分页开始',
  `pagination_end` varchar(255) default NULL COMMENT '内容分页结束',
  `queue` int(11) NOT NULL default '0' COMMENT '队列',
  `repeat_check_type` varchar(20) NOT NULL default 'NONE' COMMENT '重复类型',
  `img_acqu` tinyint(1) NOT NULL default '0' COMMENT '是否采集图片',
  `content_prefix` varchar(255) default NULL COMMENT '内容地址补全url',
  `img_prefix` varchar(255) default NULL COMMENT '图片地址补全url',
  `view_start` varchar(255) default NULL COMMENT '浏览量开始',
  `view_end` varchar(255) default NULL COMMENT '浏览量结束',
  `view_id_start` varchar(255) default NULL COMMENT 'id前缀',
  `view_id_end` varchar(255) default NULL COMMENT 'id后缀',
  `view_link` varchar(255) default NULL COMMENT '浏览量动态访问地址',
  `releaseTime_start` varchar(255) default NULL COMMENT '发布时间开始',
  `releaseTime_end` varchar(255) default NULL COMMENT '发布时间结束',
  `author_start` varchar(255) default NULL COMMENT '作者开始',
  `author_end` varchar(255) default NULL COMMENT '作者结束',
  `origin_start` varchar(255) default NULL COMMENT '来源开始',
  `origin_end` varchar(255) default NULL COMMENT '来源结束',
  `releaseTime_format` varchar(255) default NULL COMMENT '发布时间格式',
  PRIMARY KEY  (`acquisition_id`),
  KEY `fk_jc_acquisition_channel` (`channel_id`),
  KEY `fk_jc_acquisition_contenttype` (`type_id`),
  KEY `fk_jc_acquisition_site` (`site_id`),
  KEY `fk_jc_acquisition_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS采集表';

DROP TABLE IF EXISTS `jc_acquisition_history`;
CREATE TABLE `jc_acquisition_history` (
  `history_id` int(11) NOT NULL auto_increment,
  `channel_url` varchar(255) NOT NULL default '' COMMENT '栏目地址',
  `content_url` varchar(255) NOT NULL default '' COMMENT '内容地址',
  `title` varchar(255) default NULL COMMENT '标题',
  `description` varchar(20) NOT NULL default '' COMMENT '描述',
  `acquisition_id` int(11) default NULL COMMENT '采集源',
  `content_id` int(11) default NULL COMMENT '内容',
  PRIMARY KEY  (`history_id`),
  KEY `fk_acquisition_history_acquisition` (`acquisition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集历史记录表';

DROP TABLE IF EXISTS `jc_acquisition_temp`;
CREATE TABLE `jc_acquisition_temp` (
  `temp_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL,
  `channel_url` varchar(255) NOT NULL default '' COMMENT '栏目地址',
  `content_url` varchar(255) NOT NULL default '' COMMENT '内容地址',
  `title` varchar(255) default NULL COMMENT '标题',
  `finish_percent` int(3) NOT NULL default '0' COMMENT '百分比',
  `description` varchar(20) NOT NULL default '' COMMENT '描述',
  `seq` int(3) NOT NULL default '0' COMMENT '顺序',
  PRIMARY KEY  (`temp_id`),
  KEY `fk_jc_temp_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集进度临时表';

DROP TABLE IF EXISTS `jc_advertising`;
CREATE TABLE `jc_advertising` (
  `advertising_id` int(11) NOT NULL auto_increment,
  `adspace_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `ad_name` varchar(100) NOT NULL COMMENT '广告名称',
  `category` varchar(50) NOT NULL COMMENT '广告类型',
  `ad_code` longtext COMMENT '广告代码',
  `ad_weight` int(11) NOT NULL default '1' COMMENT '广告权重',
  `display_count` bigint(20) NOT NULL default '0' COMMENT '展现次数',
  `click_count` bigint(20) NOT NULL default '0' COMMENT '点击次数',
  `start_time` date default NULL COMMENT '开始时间',
  `end_time` date default NULL COMMENT '结束时间',
  `is_enabled` char(1) NOT NULL default '1' COMMENT '是否启用',
  PRIMARY KEY  (`advertising_id`),
  KEY `fk_jc_advertising_site` (`site_id`),
  KEY `fk_jc_space_advertising` (`adspace_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='CMS广告表';

INSERT INTO `jc_advertising` VALUES (1,1,1,'banner','image',NULL,1,130,0,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (2,2,1,'通栏广告1','image',NULL,1,123,2,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (3,3,1,'视频广告上','image',NULL,1,0,0,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (4,4,1,'视频广告下','image',NULL,1,0,0,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (5,5,1,'留言板本周热点广告','image',NULL,1,0,0,'2014-07-19','2014-07-01','1');
DROP TABLE IF EXISTS `jc_advertising_attr`;
CREATE TABLE `jc_advertising_attr` (
  `advertising_id` int(11) NOT NULL,
  `attr_name` varchar(50) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_params_advertising` (`advertising_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS广告属性表';

INSERT INTO `jc_advertising_attr` VALUES (1,'image_title','查看JEECMS官方网站');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_url','/v6/u/cms/www/201404/30140543hzlx.gif');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_link','http://www.jeecms.com');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_width','735');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_height','70');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_title','JEECMS官方网站');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_url','/r/cms/www/red/img/banner1.jpg');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_link','http://www.jeecms.com');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_width','960');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_height','60');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_height','89');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_link','http://');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_url','/u/cms/www/201112/17144805im1p.jpg');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_width','980');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_height','90');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_link','http://');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_url','/u/cms/www/201112/17145028j3bj.jpg');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_width','980');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_height','109');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_link','http://3x.jeecms.com');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_url','/u/cms/www/201112/18155751wi1k.gif');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_width','215');
DROP TABLE IF EXISTS `jc_advertising_space`;
CREATE TABLE `jc_advertising_space` (
  `adspace_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `ad_name` varchar(100) NOT NULL COMMENT '名称',
  `description` varchar(255) default NULL COMMENT '描述',
  `is_enabled` char(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY  (`adspace_id`),
  KEY `fk_jc_adspace_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='CMS广告版位表';

INSERT INTO `jc_advertising_space` VALUES (1,1,'页头banner','全站页头banner','1');
INSERT INTO `jc_advertising_space` VALUES (2,1,'通栏广告','页面中间通栏广告','1');
INSERT INTO `jc_advertising_space` VALUES (3,1,'视频广告上','','1');
INSERT INTO `jc_advertising_space` VALUES (4,1,'视频广告下','','1');
INSERT INTO `jc_advertising_space` VALUES (5,1,'留言板本周热点广告','留言板本周热点广告','1');
INSERT INTO `jc_advertising_space` VALUES (6,1,'顶上撒的','顶上撒的','0');
DROP TABLE IF EXISTS `jc_channel`;
CREATE TABLE `jc_channel` (
  `channel_id` int(11) NOT NULL auto_increment,
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `parent_id` int(11) default NULL COMMENT '父栏目ID',
  `channel_path` varchar(30) default NULL COMMENT '访问路径',
  `lft` int(11) NOT NULL default '1' COMMENT '树左边',
  `rgt` int(11) NOT NULL default '2' COMMENT '树右边',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `has_content` tinyint(1) NOT NULL default '1' COMMENT '是否有内容',
  `is_display` tinyint(1) NOT NULL default '1' COMMENT '是否显示',
  PRIMARY KEY  (`channel_id`),
  KEY `fk_jc_channel_model` (`model_id`),
  KEY `fk_jc_channel_parent` (`parent_id`),
  KEY `fk_jc_channel_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='CMS栏目表';

INSERT INTO `jc_channel` VALUES (1,1,1,NULL,'news',1,18,1,1,1);
INSERT INTO `jc_channel` VALUES (9,4,1,NULL,'download',19,26,4,1,1);
INSERT INTO `jc_channel` VALUES (10,2,1,NULL,'about',27,28,10,0,0);
INSERT INTO `jc_channel` VALUES (11,1,1,1,'gnxw',14,15,10,1,1);
INSERT INTO `jc_channel` VALUES (12,1,1,1,'gjxw',2,3,10,1,1);
INSERT INTO `jc_channel` VALUES (13,1,1,1,'shehui',4,5,10,1,1);
INSERT INTO `jc_channel` VALUES (14,1,1,1,'review',6,7,10,1,1);
INSERT INTO `jc_channel` VALUES (15,1,1,1,'photo',8,9,10,1,1);
INSERT INTO `jc_channel` VALUES (37,4,1,9,'system',18,19,10,1,1);
INSERT INTO `jc_channel` VALUES (38,4,1,9,'network',20,21,10,1,1);
INSERT INTO `jc_channel` VALUES (39,4,1,9,'media',22,23,10,1,1);
INSERT INTO `jc_channel` VALUES (40,1,1,1,'jjsd',10,11,10,1,1);
INSERT INTO `jc_channel` VALUES (41,1,1,1,'cjbd',12,13,10,1,1);
INSERT INTO `jc_channel` VALUES (42,5,1,NULL,'picture',29,36,2,1,1);
INSERT INTO `jc_channel` VALUES (43,5,1,42,'wyty',30,31,10,1,1);
INSERT INTO `jc_channel` VALUES (44,5,1,42,'mrzx',32,33,10,1,1);
INSERT INTO `jc_channel` VALUES (45,5,1,42,'whxy',34,35,10,1,1);
INSERT INTO `jc_channel` VALUES (46,6,1,NULL,'veido',37,44,3,1,1);
INSERT INTO `jc_channel` VALUES (49,6,1,46,'tv',38,39,10,1,1);
INSERT INTO `jc_channel` VALUES (50,6,1,46,'jlp',40,41,10,1,1);
INSERT INTO `jc_channel` VALUES (51,6,1,46,'mv',42,43,10,1,1);
INSERT INTO `jc_channel` VALUES (57,4,1,9,'syzs',24,25,10,1,1);
INSERT INTO `jc_channel` VALUES (60,1,1,NULL,'wldc',45,46,9,1,1);
INSERT INTO `jc_channel` VALUES (61,8,1,NULL,'job',47,48,8,1,1);
INSERT INTO `jc_channel` VALUES (69,1,1,1,'ffcl',16,17,10,1,1);
DROP TABLE IF EXISTS `jc_channel_attr`;
CREATE TABLE `jc_channel_attr` (
  `channel_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目扩展属性表';

DROP TABLE IF EXISTS `jc_channel_count`;
CREATE TABLE `jc_channel_count` (
  `channel_id` int(11) NOT NULL,
  `views` int(11) NOT NULL default '0' COMMENT '总访问数',
  `views_month` int(11) NOT NULL default '0' COMMENT '月访问数',
  `views_week` int(11) NOT NULL default '0' COMMENT '周访问数',
  `views_day` int(11) NOT NULL default '0' COMMENT '日访问数',
  PRIMARY KEY  (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目访问量计数表';

INSERT INTO `jc_channel_count` VALUES (1,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (9,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (10,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (11,286,0,0,0);
INSERT INTO `jc_channel_count` VALUES (12,6,0,0,0);
INSERT INTO `jc_channel_count` VALUES (13,9,0,0,0);
INSERT INTO `jc_channel_count` VALUES (14,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (15,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (37,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (38,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (39,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (40,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (41,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (42,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (43,4,0,0,0);
INSERT INTO `jc_channel_count` VALUES (44,3,0,0,0);
INSERT INTO `jc_channel_count` VALUES (45,5,0,0,0);
INSERT INTO `jc_channel_count` VALUES (46,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (49,10,0,0,0);
INSERT INTO `jc_channel_count` VALUES (50,20,0,0,0);
INSERT INTO `jc_channel_count` VALUES (51,30,0,0,0);
INSERT INTO `jc_channel_count` VALUES (57,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (60,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (61,0,0,0,0);
INSERT INTO `jc_channel_count` VALUES (69,0,0,0,0);
DROP TABLE IF EXISTS `jc_channel_ext`;
CREATE TABLE `jc_channel_ext` (
  `channel_id` int(11) NOT NULL,
  `channel_name` varchar(100) NOT NULL COMMENT '名称',
  `final_step` tinyint(4) default '2' COMMENT '终审级别',
  `after_check` tinyint(4) default NULL COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_static_channel` char(1) NOT NULL default '0' COMMENT '是否栏目静态化',
  `is_static_content` char(1) NOT NULL default '0' COMMENT '是否内容静态化',
  `is_access_by_dir` char(1) NOT NULL default '1' COMMENT '是否使用目录访问',
  `is_list_child` char(1) NOT NULL default '0' COMMENT '是否使用子栏目列表',
  `page_size` int(11) NOT NULL default '20' COMMENT '每页多少条记录',
  `channel_rule` varchar(150) default NULL COMMENT '栏目页生成规则',
  `content_rule` varchar(150) default NULL COMMENT '内容页生成规则',
  `link` varchar(255) default NULL COMMENT '外部链接',
  `tpl_channel` varchar(100) default NULL COMMENT '栏目页模板',
  `tpl_content` varchar(100) default NULL COMMENT '内容页模板',
  `title_img` varchar(100) default NULL COMMENT '缩略图',
  `content_img` varchar(100) default NULL COMMENT '内容图',
  `has_title_img` tinyint(1) NOT NULL default '0' COMMENT '内容是否有缩略图',
  `has_content_img` tinyint(1) NOT NULL default '0' COMMENT '内容是否有内容图',
  `title_img_width` int(11) NOT NULL default '139' COMMENT '内容标题图宽度',
  `title_img_height` int(11) NOT NULL default '139' COMMENT '内容标题图高度',
  `content_img_width` int(11) NOT NULL default '310' COMMENT '内容内容图宽度',
  `content_img_height` int(11) NOT NULL default '310' COMMENT '内容内容图高度',
  `comment_control` int(11) NOT NULL default '0' COMMENT '评论(0:匿名;1:会员;2:关闭)',
  `allow_updown` tinyint(1) NOT NULL default '1' COMMENT '顶踩(true:开放;false:关闭)',
  `is_blank` tinyint(1) NOT NULL default '1' COMMENT '是否新窗口打开',
  `title` varchar(255) default NULL COMMENT 'TITLE',
  `keywords` varchar(255) default NULL COMMENT 'KEYWORDS',
  `description` varchar(255) default NULL COMMENT 'DESCRIPTION',
  `allow_share` tinyint(1) NOT NULL default '0' COMMENT '分享(true:开放;false:关闭)',
  `allow_score` tinyint(1) NOT NULL default '0' COMMENT '评分(true:开放;false:关闭)',
  PRIMARY KEY  (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目内容表';

INSERT INTO `jc_channel_ext` VALUES (1,'新闻',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'新闻资讯','新闻资讯','新闻资讯',0,0);
INSERT INTO `jc_channel_ext` VALUES (9,'下载',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'','',NULL,NULL,0,0,139,139,310,310,0,1,0,'下载中心','下载中心','下载中心',0,0);
INSERT INTO `jc_channel_ext` VALUES (10,'关于我们',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'','',NULL,NULL,0,0,139,139,310,310,0,1,0,'关于我们','关于我们','关于我们',0,0);
INSERT INTO `jc_channel_ext` VALUES (11,'国内新闻',NULL,NULL,'0','0','0','0',5,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,1,1,400,200,310,310,0,1,0,'国内新闻','国内新闻','国内新闻',1,1);
INSERT INTO `jc_channel_ext` VALUES (12,'国际新闻',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'国际新闻','国际新闻','国际新闻',0,0);
INSERT INTO `jc_channel_ext` VALUES (13,'社会热点',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'社会热点','社会热点','社会热点',0,0);
INSERT INTO `jc_channel_ext` VALUES (14,'时事评论',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'时事评论','时事评论','时事评论',0,0);
INSERT INTO `jc_channel_ext` VALUES (15,'图片新闻',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,1,0,139,139,310,310,0,1,0,'图片新闻','图片新闻','图片新闻',0,0);
INSERT INTO `jc_channel_ext` VALUES (37,'系统软件',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/download_child.html',NULL,NULL,NULL,1,1,48,48,139,98,0,1,0,'系统软件','系统软件','系统软件',0,0);
INSERT INTO `jc_channel_ext` VALUES (38,'网络游戏',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/download_child.html',NULL,NULL,NULL,1,1,48,48,139,98,0,1,0,'网络游戏','网络工具','网络游戏',0,0);
INSERT INTO `jc_channel_ext` VALUES (39,'媒体工具',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/download_child.html',NULL,NULL,NULL,1,1,48,48,139,98,0,1,0,'媒体工具','媒体工具','媒体工具',0,0);
INSERT INTO `jc_channel_ext` VALUES (40,'基金视点',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (41,'财经报道',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (42,'图库',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'','',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (43,'文娱体育',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/pic_child.html',NULL,NULL,NULL,1,0,67,50,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (44,'美容资讯',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/pic_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (45,'文化 校园',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/pic_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (46,'视频',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'','',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (49,'电视剧',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/vedio_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (50,'纪录片',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/vedio_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (51,'电影',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/vedio_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (57,'实用助手',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/download_child.html',NULL,NULL,NULL,1,1,48,48,180,120,0,1,0,NULL,NULL,NULL,0,0);
INSERT INTO `jc_channel_ext` VALUES (60,'网络调查',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_wldc.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'网络调查','网络调查','网络调查',0,0);
INSERT INTO `jc_channel_ext` VALUES (61,'招聘',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'','',NULL,NULL,0,0,139,139,310,310,0,1,0,'招聘','招聘','招聘',0,0);
INSERT INTO `jc_channel_ext` VALUES (69,'反腐倡廉',NULL,NULL,'0','0','0','0',10,NULL,NULL,NULL,'/WEB-INF/t/cms/www/default/channel/news_child.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'反腐倡廉','反腐倡廉','反腐倡廉',0,0);
DROP TABLE IF EXISTS `jc_channel_model`;
CREATE TABLE `jc_channel_model` (
  `channel_id` int(11) NOT NULL,
  `model_id` int(11) NOT NULL COMMENT '模型id',
  `tpl_content` varchar(100) default NULL COMMENT '内容模板',
  `priority` int(11) NOT NULL default '10' COMMENT '排序',
  PRIMARY KEY  (`channel_id`,`priority`),
  KEY `fk_jc_model_channel_m` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目可选内容模型表';

DROP TABLE IF EXISTS `jc_channel_txt`;
CREATE TABLE `jc_channel_txt` (
  `channel_id` int(11) NOT NULL,
  `txt` longtext COMMENT '栏目内容',
  `txt1` longtext COMMENT '扩展内容1',
  `txt2` longtext COMMENT '扩展内容2',
  `txt3` longtext COMMENT '扩展内容3',
  PRIMARY KEY  (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目文本表';

INSERT INTO `jc_channel_txt` VALUES (10,'<p><font size=\"2\">&nbsp;&nbsp;&nbsp; JEECMS是JavaEE版网站管理系统（Java Enterprise Edition Content Manage System）的简称。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;Java凭借其强大、稳定、安全、高效等多方面的优势，一直是企业级应用的首选。在国外基于JavaEE技术的CMS已经发展的相当成熟，但授权费昂贵，一般需几十万一套；而国内在这方面一直比较薄弱，至今没有一款基于JavaEE技术的开源免费CMS产品。这次我们本着&quot;大气开源，诚信图强&quot;的原则将我们开发的这套JEECMS系统源码完全公布，希望能为国内JavaEE技术的发展尽自己的一份力量。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;JEECMS使用目前java主流技术架构：hibernate3+spring3+freemarker。AJAX使用jquery和json实现。视图层并没有使用传统的 JSP技术，而是使用更为专业、灵活、高效freemarker。 数据库使用MYSQL，并可支持orcale、DB2、SQLServer等主流数据库。应用服务器使用tomcat，并支持其他weblogic、 websphere等应用服务器。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;JEECMS并不是一个只追求技术之先进，而不考虑用户实际使用的象牙塔CMS。系统的设计宗旨就是从用户的需求出发，提供最便利、合理的使用方式，懂html就能建站，从设计上满足搜索引擎优化，最小性能消耗满足小网站要求、可扩展群集满足大网站需要。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;很多人觉得java、jsp难掌握，技术门槛高。jeecms具有强大的模板机制。所有前台页面均由模板生成，通过在线编辑模板轻松调整页面显示。模板内容不涉及任何java和jsp技术，只需掌握html语法和jeecms标签即可完成动态网页制作。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;强大、灵活的标签。提供两种风格的标签，一种风格的标签封装了大量互联网上常见的显示样式，通过调整参数就可实现文章列表、图文混排、图文滚动、跑马灯、焦点图等效果。这种标签的优势在于页面制作简单、效率高，对js、css、html不够精通和希望快速建站的用户非常适用。并且各种效果的内容不使用js生成，对搜索引擎非常友好。另一种风格的标签只负责读取数据，由用户自己控制显示内容和显示方式，想到什么就能做到什么，对于技术能力高和追求个性化的用户，可谓如鱼得水。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;采用完全生成静态页面技术，加快页面访问速度，提升搜索引擎友好性；采用扁平的、可自定义的路径结构。对于有特别需求者，可自定义页面后缀，如.php,.asp,.aspx等。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;站群设计，对于大型的网站，往往需要通过次级域名建立子站群，各个子站后台管理权限可以分离，程序和附件分离，前台用户实现单点登录，大规模网站轻松建设。</font></p>',NULL,NULL,NULL);
DROP TABLE IF EXISTS `jc_channel_user`;
CREATE TABLE `jc_channel_user` (
  `channel_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`user_id`),
  KEY `fk_jc_channel_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目用户关联表';

DROP TABLE IF EXISTS `jc_chnl_group_contri`;
CREATE TABLE `jc_chnl_group_contri` (
  `channel_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`group_id`),
  KEY `fk_jc_channel_group_c` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目投稿会员组关联表';

INSERT INTO `jc_chnl_group_contri` VALUES (1,1);
INSERT INTO `jc_chnl_group_contri` VALUES (1,2);
INSERT INTO `jc_chnl_group_contri` VALUES (11,1);
INSERT INTO `jc_chnl_group_contri` VALUES (11,2);
INSERT INTO `jc_chnl_group_contri` VALUES (12,1);
INSERT INTO `jc_chnl_group_contri` VALUES (13,1);
INSERT INTO `jc_chnl_group_contri` VALUES (14,1);
INSERT INTO `jc_chnl_group_contri` VALUES (15,1);
INSERT INTO `jc_chnl_group_contri` VALUES (40,1);
INSERT INTO `jc_chnl_group_contri` VALUES (41,1);
INSERT INTO `jc_chnl_group_contri` VALUES (42,1);
INSERT INTO `jc_chnl_group_contri` VALUES (42,2);
INSERT INTO `jc_chnl_group_contri` VALUES (43,1);
INSERT INTO `jc_chnl_group_contri` VALUES (43,2);
INSERT INTO `jc_chnl_group_contri` VALUES (44,1);
INSERT INTO `jc_chnl_group_contri` VALUES (44,2);
INSERT INTO `jc_chnl_group_contri` VALUES (45,1);
INSERT INTO `jc_chnl_group_contri` VALUES (45,2);
INSERT INTO `jc_chnl_group_contri` VALUES (46,2);
INSERT INTO `jc_chnl_group_contri` VALUES (49,2);
INSERT INTO `jc_chnl_group_contri` VALUES (50,2);
INSERT INTO `jc_chnl_group_contri` VALUES (51,2);
INSERT INTO `jc_chnl_group_contri` VALUES (60,2);
INSERT INTO `jc_chnl_group_contri` VALUES (61,2);
INSERT INTO `jc_chnl_group_contri` VALUES (69,1);
INSERT INTO `jc_chnl_group_contri` VALUES (69,2);
DROP TABLE IF EXISTS `jc_chnl_group_view`;
CREATE TABLE `jc_chnl_group_view` (
  `channel_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`group_id`),
  KEY `fk_jc_channel_group_v` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目浏览会员组关联表';

INSERT INTO `jc_chnl_group_view` VALUES (1,1);
INSERT INTO `jc_chnl_group_view` VALUES (11,1);
INSERT INTO `jc_chnl_group_view` VALUES (12,1);
INSERT INTO `jc_chnl_group_view` VALUES (13,1);
INSERT INTO `jc_chnl_group_view` VALUES (14,1);
INSERT INTO `jc_chnl_group_view` VALUES (15,1);
INSERT INTO `jc_chnl_group_view` VALUES (40,1);
INSERT INTO `jc_chnl_group_view` VALUES (41,1);
INSERT INTO `jc_chnl_group_view` VALUES (69,1);
DROP TABLE IF EXISTS `jc_comment`;
CREATE TABLE `jc_comment` (
  `comment_id` int(11) NOT NULL auto_increment,
  `comment_user_id` int(11) default NULL COMMENT '评论用户ID',
  `reply_user_id` int(11) default NULL COMMENT '回复用户ID',
  `content_id` int(11) NOT NULL COMMENT '内容ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `reply_time` datetime default NULL COMMENT '回复时间',
  `ups` smallint(6) NOT NULL default '0' COMMENT '支持数',
  `downs` smallint(6) NOT NULL default '0' COMMENT '反对数',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推荐',
  `is_checked` tinyint(1) NOT NULL default '0' COMMENT '是否审核',
  `score` int(11) default NULL COMMENT '评分',
  PRIMARY KEY  (`comment_id`),
  KEY `fk_jc_comment_content` (`content_id`),
  KEY `fk_jc_comment_reply` (`reply_user_id`),
  KEY `fk_jc_comment_site` (`site_id`),
  KEY `fk_jc_comment_user` (`comment_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='CMS评论表';

INSERT INTO `jc_comment` VALUES (1,1,NULL,512,1,'2014-03-14 17:31:49',NULL,0,0,0,1,NULL);
INSERT INTO `jc_comment` VALUES (2,1,NULL,445,1,'2014-03-14 17:31:58',NULL,0,0,0,1,NULL);
INSERT INTO `jc_comment` VALUES (4,NULL,NULL,564,1,'2014-04-19 16:42:02',NULL,0,0,0,0,NULL);
INSERT INTO `jc_comment` VALUES (5,NULL,NULL,564,1,'2014-04-19 17:11:37',NULL,0,0,0,0,NULL);
DROP TABLE IF EXISTS `jc_comment_ext`;
CREATE TABLE `jc_comment_ext` (
  `comment_id` int(11) NOT NULL,
  `ip` varchar(50) default NULL COMMENT 'IP地址',
  `text` longtext COMMENT '评论内容',
  `reply` longtext COMMENT '回复内容',
  KEY `fk_jc_ext_comment` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS评论扩展表';

INSERT INTO `jc_comment_ext` VALUES (1,'127.0.0.1','121212','');
INSERT INTO `jc_comment_ext` VALUES (2,'127.0.0.1','asdfasd',NULL);
INSERT INTO `jc_comment_ext` VALUES (4,'127.0.0.1','adfd',NULL);
INSERT INTO `jc_comment_ext` VALUES (5,'127.0.0.1','adsfa',NULL);
DROP TABLE IF EXISTS `jc_config`;
CREATE TABLE `jc_config` (
  `config_id` int(11) NOT NULL,
  `context_path` varchar(20) default '/JeeCms' COMMENT '部署路径',
  `servlet_point` varchar(20) default NULL COMMENT 'Servlet挂载点',
  `port` int(11) default NULL COMMENT '端口',
  `db_file_uri` varchar(50) NOT NULL default '/dbfile.svl?n=' COMMENT '数据库附件访问地址',
  `is_upload_to_db` tinyint(1) NOT NULL default '0' COMMENT '上传附件至数据库',
  `def_img` varchar(255) NOT NULL default '/JeeCms/r/cms/www/default/no_picture.gif' COMMENT '图片不存在时默认图片',
  `login_url` varchar(255) NOT NULL default '/login.jspx' COMMENT '登录地址',
  `process_url` varchar(255) default NULL COMMENT '登录后处理地址',
  `mark_on` tinyint(1) NOT NULL default '1' COMMENT '开启图片水印',
  `mark_width` int(11) NOT NULL default '120' COMMENT '图片最小宽度',
  `mark_height` int(11) NOT NULL default '120' COMMENT '图片最小高度',
  `mark_image` varchar(100) default '/r/cms/www/watermark.png' COMMENT '图片水印',
  `mark_content` varchar(100) NOT NULL default 'www.jeecms.com' COMMENT '文字水印内容',
  `mark_size` int(11) NOT NULL default '20' COMMENT '文字水印大小',
  `mark_color` varchar(10) NOT NULL default '#FF0000' COMMENT '文字水印颜色',
  `mark_alpha` int(11) NOT NULL default '50' COMMENT '水印透明度（0-100）',
  `mark_position` int(11) NOT NULL default '1' COMMENT '水印位置(0-5)',
  `mark_offset_x` int(11) NOT NULL default '0' COMMENT 'x坐标偏移量',
  `mark_offset_y` int(11) NOT NULL default '0' COMMENT 'y坐标偏移量',
  `count_clear_time` date NOT NULL COMMENT '计数器清除时间',
  `count_copy_time` datetime NOT NULL COMMENT '计数器拷贝时间',
  `download_code` varchar(32) NOT NULL default 'jeecms' COMMENT '下载防盗链md5混淆码',
  `download_time` int(11) NOT NULL default '12' COMMENT '下载有效时间（小时）',
  `email_host` varchar(50) default NULL COMMENT '邮件发送服务器',
  `email_encoding` varchar(20) default NULL COMMENT '邮件发送编码',
  `email_username` varchar(100) default NULL COMMENT '邮箱用户名',
  `email_password` varchar(100) default NULL COMMENT '邮箱密码',
  `email_personal` varchar(100) default NULL COMMENT '邮箱发件人',
  `email_validate` tinyint(1) default '0' COMMENT '开启邮箱验证',
  `view_only_checked` tinyint(1) NOT NULL default '0' COMMENT '只有终审才能浏览内容页',
  PRIMARY KEY  (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置表';

INSERT INTO `jc_config` VALUES (1,NULL,NULL,8080,'/dbfile.svl?n=',0,'/r/cms/www/no_picture.gif','/login.jspx',NULL,1,120,120,'/r/cms/www/watermark.png','www.jeecms.com',40,'#FF0000',100,1,0,0,'2014-09-02','2014-09-02 17:17:16','jeecms',12,NULL,NULL,NULL,NULL,NULL,1,0);
DROP TABLE IF EXISTS `jc_config_attr`;
CREATE TABLE `jc_config_attr` (
  `config_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_config` (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置属性表';

INSERT INTO `jc_config_attr` VALUES (1,'password_min_len','3');
INSERT INTO `jc_config_attr` VALUES (1,'username_reserved','');
INSERT INTO `jc_config_attr` VALUES (1,'register_on','true');
INSERT INTO `jc_config_attr` VALUES (1,'member_on','true');
INSERT INTO `jc_config_attr` VALUES (1,'username_min_len','3');
INSERT INTO `jc_config_attr` VALUES (1,'version','jeecms-v7');
INSERT INTO `jc_config_attr` VALUES (1,'user_img_height','98');
INSERT INTO `jc_config_attr` VALUES (1,'user_img_width','143');
INSERT INTO `jc_config_attr` VALUES (1,'check_on','false');
INSERT INTO `jc_config_attr` VALUES (1,'new_picture','/r/cms/www/new.gif');
INSERT INTO `jc_config_attr` VALUES (1,'day','3');
INSERT INTO `jc_config_attr` VALUES (1,'preview','false');
INSERT INTO `jc_config_attr` VALUES (1,'qqEnable','true');
INSERT INTO `jc_config_attr` VALUES (1,'sinaKey','2c64512a12f2e7f6a5cfb5f5b5d8b128');
INSERT INTO `jc_config_attr` VALUES (1,'sinaEnable','true');
INSERT INTO `jc_config_attr` VALUES (1,'qqID','101139646');
INSERT INTO `jc_config_attr` VALUES (1,'qqKey','13f23bebda25f9f26c5c082d017f74ee');
INSERT INTO `jc_config_attr` VALUES (1,'sinaID','2510334929');
INSERT INTO `jc_config_attr` VALUES (1,'qqWeboEnable','true');
INSERT INTO `jc_config_attr` VALUES (1,'qqWeboKey','c6b0a6d1ce972f6be1a9113c3afbea2f');
INSERT INTO `jc_config_attr` VALUES (1,'qqWeboID','801526383');
INSERT INTO `jc_config_attr` VALUES (1,'weixinEnable','false');
INSERT INTO `jc_config_attr` VALUES (1,'weixinKey','b442bcedc2ab8508caaa63841e33686e');
INSERT INTO `jc_config_attr` VALUES (1,'weixinID','wxfc9f068ff05339f1');
DROP TABLE IF EXISTS `jc_config_item`;
CREATE TABLE `jc_config_item` (
  `modelitem_id` int(11) NOT NULL auto_increment,
  `config_id` int(11) NOT NULL,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `item_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '70' COMMENT '排列顺序',
  `def_value` varchar(255) default NULL COMMENT '默认值',
  `opt_value` varchar(255) default NULL COMMENT '可选项',
  `text_size` varchar(20) default NULL COMMENT '长度',
  `area_rows` varchar(3) default NULL COMMENT '文本行数',
  `area_cols` varchar(3) default NULL COMMENT '文本列数',
  `help` varchar(255) default NULL COMMENT '帮助信息',
  `help_position` varchar(1) default NULL COMMENT '帮助位置',
  `data_type` int(11) NOT NULL default '1' COMMENT '数据类型 "1":"字符串文本","2":"整型文本","3":"浮点型文本","4":"文本区","5":"日期","6":"下拉列表","7":"复选框","8":"单选框"',
  `is_required` tinyint(1) NOT NULL default '0' COMMENT '是否必填',
  `category` int(11) NOT NULL default '1' COMMENT '模型项目所属分类（1注册模型）',
  PRIMARY KEY  (`modelitem_id`),
  KEY `fk_jc_item_config` (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='CMS配置模型项表';

INSERT INTO `jc_config_item` VALUES (1,1,'realname','真实姓名',1,'','','','3','30','','',1,0,1);
INSERT INTO `jc_config_item` VALUES (2,1,'phone','手机号码',2,NULL,NULL,NULL,'3','30',NULL,NULL,1,0,1);
INSERT INTO `jc_config_item` VALUES (3,1,'xingqu','兴趣标签',3,'','新闻,娱乐,房产,生活,文化,体育,财经,时尚,汽车,IT','','3','30','','',7,0,1);
DROP TABLE IF EXISTS `jc_content`;
CREATE TABLE `jc_content` (
  `content_id` int(11) NOT NULL auto_increment,
  `channel_id` int(11) NOT NULL COMMENT '栏目ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `type_id` int(11) NOT NULL COMMENT '属性ID',
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `sort_date` datetime NOT NULL COMMENT '排序日期',
  `top_level` tinyint(4) NOT NULL default '0' COMMENT '固顶级别',
  `has_title_img` tinyint(1) NOT NULL default '0' COMMENT '是否有标题图',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推荐',
  `status` tinyint(4) NOT NULL default '2' COMMENT '状态(0:草稿;1:审核中;2:审核通过;3:回收站；4:投稿)',
  `views_day` int(11) NOT NULL default '0' COMMENT '日访问数',
  `comments_day` smallint(6) NOT NULL default '0' COMMENT '日评论数',
  `downloads_day` smallint(6) NOT NULL default '0' COMMENT '日下载数',
  `ups_day` smallint(6) NOT NULL default '0' COMMENT '日顶数',
  `score` int(11) NOT NULL default '0' COMMENT '得分',
  PRIMARY KEY  (`content_id`),
  KEY `fk_jc_content_site` (`site_id`),
  KEY `fk_jc_content_type` (`type_id`),
  KEY `fk_jc_content_user` (`user_id`),
  KEY `fk_jc_contentchannel` (`channel_id`),
  KEY `fk_jc_content_model` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=565 DEFAULT CHARSET=utf8 COMMENT='CMS内容表';

INSERT INTO `jc_content` VALUES (172,39,1,1,4,1,'2013-09-27 09:55:24',0,1,0,2,0,0,1,0,0);
INSERT INTO `jc_content` VALUES (173,39,1,1,4,1,'2013-09-27 09:55:05',0,1,0,2,0,0,1,0,0);
INSERT INTO `jc_content` VALUES (326,38,1,1,4,1,'2013-09-27 09:52:39',0,1,0,2,0,0,2,0,0);
INSERT INTO `jc_content` VALUES (328,38,1,1,4,1,'2013-09-27 09:51:14',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (329,38,1,1,4,1,'2013-09-27 09:50:33',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (330,38,1,1,4,1,'2013-09-27 09:49:36',0,1,0,2,0,0,1,0,0);
INSERT INTO `jc_content` VALUES (331,39,1,1,4,1,'2013-09-27 09:54:05',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (332,39,1,1,4,1,'2013-09-27 09:53:14',0,1,0,2,0,0,1,0,0);
INSERT INTO `jc_content` VALUES (334,57,1,1,4,1,'2013-09-27 09:58:05',0,1,0,2,0,0,1,0,0);
INSERT INTO `jc_content` VALUES (335,57,1,1,4,1,'2013-09-27 09:57:15',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (336,57,1,1,4,1,'2013-09-27 09:56:51',0,1,0,2,0,0,1,0,0);
INSERT INTO `jc_content` VALUES (337,57,1,1,4,1,'2013-09-27 09:55:49',0,1,0,2,0,0,3,0,0);
INSERT INTO `jc_content` VALUES (436,13,1,1,1,1,'2013-07-08 15:34:08',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (437,13,1,1,1,1,'2013-07-08 15:34:09',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (438,13,1,1,1,1,'2013-07-08 15:34:11',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (439,13,1,1,1,1,'2013-07-08 15:34:11',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (440,13,1,1,1,1,'2013-07-08 15:34:12',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (441,13,1,1,1,1,'2013-07-08 15:34:13',0,0,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (442,13,1,1,1,1,'2013-07-08 15:34:14',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (443,13,1,1,1,1,'2013-07-08 15:34:15',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (444,11,1,1,1,1,'2013-07-08 15:34:17',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (445,13,1,1,1,1,'2013-07-08 15:34:18',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (446,11,1,1,1,1,'2013-07-08 15:34:18',0,0,0,2,0,0,0,0,1);
INSERT INTO `jc_content` VALUES (447,12,1,1,1,1,'2013-07-08 15:34:20',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (448,11,1,1,1,1,'2013-07-08 15:34:21',0,0,0,2,0,0,0,0,4);
INSERT INTO `jc_content` VALUES (473,49,1,3,6,1,'2013-08-20 11:30:47',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (474,49,1,2,6,1,'2013-08-20 11:36:14',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (475,49,1,2,6,1,'2013-08-20 11:36:43',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (476,49,1,2,6,1,'2013-08-20 11:43:16',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (477,49,1,2,6,1,'2013-08-20 11:44:30',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (478,49,1,2,6,1,'2013-08-20 11:45:42',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (479,49,1,2,6,1,'2013-08-20 11:46:57',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (480,49,1,2,6,1,'2013-08-20 11:47:53',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (481,50,1,2,6,1,'2013-08-20 11:56:55',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (482,50,1,2,6,1,'2013-08-20 12:02:03',0,1,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (483,50,1,2,6,1,'2013-08-20 12:05:52',0,1,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (484,50,1,2,6,1,'2013-08-20 12:09:02',0,1,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (487,43,1,2,5,1,'2013-08-21 11:34:29',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (488,43,1,2,5,1,'2013-08-21 11:59:29',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (489,43,1,2,5,1,'2013-08-21 13:40:11',0,0,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (490,43,1,2,5,1,'2013-08-21 14:35:35',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (491,43,1,2,5,1,'2013-08-21 14:47:08',0,0,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (492,43,1,2,5,1,'2013-08-21 14:49:52',0,0,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (494,43,1,2,5,1,'2013-08-21 14:58:46',0,0,1,2,1,0,0,0,0);
INSERT INTO `jc_content` VALUES (495,44,1,1,1,1,'2013-08-21 15:12:25',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (496,44,1,1,1,1,'2013-08-21 15:19:59',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (497,44,1,1,6,1,'2013-08-21 15:24:11',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (498,44,1,1,1,1,'2013-08-22 09:13:41',0,0,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (499,45,1,1,1,1,'2013-08-22 09:21:00',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (500,45,1,1,1,1,'2013-08-22 09:28:09',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (501,45,1,1,1,1,'2013-08-22 09:45:50',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (502,49,1,2,6,1,'2013-09-09 16:51:46',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (503,49,1,2,6,1,'2013-09-09 16:55:35',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (504,49,1,2,6,1,'2013-09-09 16:59:13',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (505,49,1,2,6,1,'2013-09-09 17:00:23',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (506,11,1,3,5,1,'2013-09-10 09:22:39',0,0,1,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (507,11,1,3,1,1,'2013-09-10 09:50:40',0,0,0,2,0,0,0,0,4);
INSERT INTO `jc_content` VALUES (508,11,1,3,1,1,'2013-09-10 09:53:55',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (509,11,1,1,1,1,'2013-09-10 10:28:25',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (510,11,1,3,5,1,'2013-09-10 10:38:52',0,0,0,2,1,0,0,0,0);
INSERT INTO `jc_content` VALUES (511,12,1,3,5,1,'2013-09-10 10:45:34',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (512,13,1,2,1,1,'2013-09-10 14:40:05',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (513,45,1,2,5,1,'2013-09-12 10:59:15',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (514,45,1,2,5,1,'2013-09-12 11:04:04',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (515,45,1,2,5,1,'2013-09-12 11:08:22',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (516,45,1,2,5,1,'2013-09-12 11:12:45',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (517,45,1,2,5,1,'2013-09-12 11:19:03',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (518,45,1,2,5,1,'2013-09-12 11:22:50',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (519,44,1,3,5,1,'2013-09-12 12:07:42',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (520,44,1,3,5,1,'2013-09-12 13:43:06',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (521,44,1,2,5,1,'2013-09-12 13:45:55',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (522,44,1,2,5,1,'2013-09-12 13:49:36',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (523,44,1,2,5,1,'2013-09-12 13:52:46',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (524,44,1,3,5,1,'2013-09-12 13:58:30',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (525,44,1,3,5,1,'2013-09-12 14:08:58',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (526,44,1,3,5,1,'2013-09-12 14:14:53',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (527,44,1,3,5,1,'2013-09-12 14:23:30',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (528,44,1,2,5,1,'2013-09-12 14:28:33',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (529,44,1,2,5,1,'2013-09-12 14:33:47',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (530,44,1,2,5,1,'2013-09-12 14:57:01',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (531,44,1,3,5,1,'2013-09-12 15:07:03',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (532,44,1,3,5,1,'2013-09-12 15:11:43',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (533,44,1,3,5,1,'2013-09-12 15:23:00',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (534,44,1,2,5,1,'2013-09-12 15:36:56',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (535,44,1,2,5,1,'2013-09-12 15:43:13',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (536,12,1,3,5,1,'2013-09-12 15:48:01',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (539,50,1,2,6,1,'2013-09-13 14:12:47',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (540,50,1,2,6,1,'2013-09-13 14:24:03',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (541,50,1,2,6,1,'2013-09-13 14:29:31',0,1,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (559,12,1,1,1,1,'2013-09-27 10:18:25',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (560,12,1,1,1,1,'2013-09-27 10:18:47',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (561,14,1,1,1,1,'2013-09-27 10:19:30',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (562,14,1,1,1,1,'2013-09-27 10:19:51',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (563,14,1,1,1,1,'2013-09-27 10:20:09',0,0,0,2,0,0,0,0,0);
INSERT INTO `jc_content` VALUES (564,14,1,1,1,1,'2013-09-27 10:20:31',0,0,0,2,0,0,0,0,0);
DROP TABLE IF EXISTS `jc_content_attachment`;
CREATE TABLE `jc_content_attachment` (
  `content_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `attachment_path` varchar(255) NOT NULL COMMENT '附件路径',
  `attachment_name` varchar(100) NOT NULL COMMENT '附件名称',
  `filename` varchar(100) default NULL COMMENT '文件名',
  `download_count` int(11) NOT NULL default '0' COMMENT '下载次数',
  KEY `fk_jc_attachment_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容附件表';

INSERT INTO `jc_content_attachment` VALUES (172,0,'/u/cms/www/201309/19115513v9k3.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (173,0,'/u/cms/www/201309/19115459jnds.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (326,0,'/u/cms/www/201309/19114320y7x2.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (328,0,'/u/cms/www/201309/19120206ddre.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (329,0,'/u/cms/www/201309/19132320u46d.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (330,0,'/u/cms/www/201309/19134542a8qu.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (331,0,'/u/cms/www/201309/19135345g1s7.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (332,0,'/u/cms/www/201309/19140010z9z1.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (334,0,'/u/cms/www/201309/191418286eoi.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (335,0,'/u/cms/www/201309/19142201umby.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (336,0,'/u/cms/www/201309/191425405rka.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (337,0,'/u/cms/www/201309/19143017qxs3.zip','111.zip','111.zip',0);
DROP TABLE IF EXISTS `jc_content_attr`;
CREATE TABLE `jc_content_attr` (
  `content_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展属性表';

INSERT INTO `jc_content_attr` VALUES (172,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (172,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (172,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (172,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (173,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (173,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (173,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (173,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (326,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (326,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (326,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (326,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (328,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (328,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (328,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (328,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (329,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (329,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (329,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (329,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (330,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (330,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (330,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (330,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (331,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (331,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (331,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (331,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (332,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (332,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (332,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (332,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (334,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (334,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (334,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (334,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (335,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (335,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (335,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (335,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (336,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (336,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (336,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (336,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (337,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (337,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (337,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (337,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (473,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (473,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (474,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (474,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (475,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (475,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (476,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (476,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (477,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (477,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (478,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (478,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (479,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (479,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (480,'Starring','张国强 濮存昕 李菁菁 刘威葳 高亚麟');
INSERT INTO `jc_content_attr` VALUES (480,'Director','康洪雷');
INSERT INTO `jc_content_attr` VALUES (481,'Starring','未知');
INSERT INTO `jc_content_attr` VALUES (481,'Director','未知');
INSERT INTO `jc_content_attr` VALUES (482,'Starring','未知');
INSERT INTO `jc_content_attr` VALUES (482,'Director','未知');
INSERT INTO `jc_content_attr` VALUES (483,'Starring','未知');
INSERT INTO `jc_content_attr` VALUES (483,'Director','未知');
INSERT INTO `jc_content_attr` VALUES (484,'Starring','未知');
INSERT INTO `jc_content_attr` VALUES (484,'Director','未知');
INSERT INTO `jc_content_attr` VALUES (497,'Starring','');
INSERT INTO `jc_content_attr` VALUES (497,'Director','');
INSERT INTO `jc_content_attr` VALUES (502,'VideoType','都市,喜剧');
INSERT INTO `jc_content_attr` VALUES (502,'Starring','文章 马伊琍 朱佳煜 王耀庆 张子萱');
INSERT INTO `jc_content_attr` VALUES (502,'Director','文章');
INSERT INTO `jc_content_attr` VALUES (502,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (503,'VideoType','都市,喜剧');
INSERT INTO `jc_content_attr` VALUES (503,'Starring','文章 马伊琍 朱佳煜 王耀庆 张子萱');
INSERT INTO `jc_content_attr` VALUES (503,'Director','文章');
INSERT INTO `jc_content_attr` VALUES (503,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (504,'VideoType','都市,喜剧');
INSERT INTO `jc_content_attr` VALUES (504,'Starring','文章 马伊琍 朱佳煜 王耀庆 张子萱');
INSERT INTO `jc_content_attr` VALUES (504,'Director','文章');
INSERT INTO `jc_content_attr` VALUES (504,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (505,'VideoType','都市,喜剧');
INSERT INTO `jc_content_attr` VALUES (505,'Starring','文章 马伊琍 朱佳煜 王耀庆 张子萱');
INSERT INTO `jc_content_attr` VALUES (505,'Director','文章');
INSERT INTO `jc_content_attr` VALUES (505,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (480,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (480,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (479,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (479,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (478,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (478,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (477,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (477,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (473,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (473,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (474,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (474,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (475,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (475,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (476,'VideoType','都市');
INSERT INTO `jc_content_attr` VALUES (476,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (484,'VideoType','古装');
INSERT INTO `jc_content_attr` VALUES (484,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (483,'VideoType','惊悚');
INSERT INTO `jc_content_attr` VALUES (483,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (482,'VideoType','历史');
INSERT INTO `jc_content_attr` VALUES (482,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (481,'VideoType','谍战,战争');
INSERT INTO `jc_content_attr` VALUES (481,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (539,'VideoType','历史');
INSERT INTO `jc_content_attr` VALUES (539,'Starring','');
INSERT INTO `jc_content_attr` VALUES (539,'Director','上海纪实频道.');
INSERT INTO `jc_content_attr` VALUES (539,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (540,'VideoType','伦理,战争');
INSERT INTO `jc_content_attr` VALUES (540,'Starring','');
INSERT INTO `jc_content_attr` VALUES (540,'Director','旅游卫视');
INSERT INTO `jc_content_attr` VALUES (540,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (541,'VideoType','都市,伦理');
INSERT INTO `jc_content_attr` VALUES (541,'Starring','');
INSERT INTO `jc_content_attr` VALUES (541,'Director','江西卫视.');
INSERT INTO `jc_content_attr` VALUES (541,'Video','正片');
INSERT INTO `jc_content_attr` VALUES (509,'t1','好的哦');
INSERT INTO `jc_content_attr` VALUES (509,'t2','2');
INSERT INTO `jc_content_attr` VALUES (507,'t2','120');
INSERT INTO `jc_content_attr` VALUES (507,'t1','');
INSERT INTO `jc_content_attr` VALUES (560,'t2','');
INSERT INTO `jc_content_attr` VALUES (560,'t1','');
DROP TABLE IF EXISTS `jc_content_channel`;
CREATE TABLE `jc_content_channel` (
  `channel_id` int(11) NOT NULL,
  `content_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`content_id`),
  KEY `fk_jc_channel_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容栏目关联表';

INSERT INTO `jc_content_channel` VALUES (11,444);
INSERT INTO `jc_content_channel` VALUES (11,446);
INSERT INTO `jc_content_channel` VALUES (11,448);
INSERT INTO `jc_content_channel` VALUES (11,506);
INSERT INTO `jc_content_channel` VALUES (11,507);
INSERT INTO `jc_content_channel` VALUES (11,508);
INSERT INTO `jc_content_channel` VALUES (11,509);
INSERT INTO `jc_content_channel` VALUES (11,510);
INSERT INTO `jc_content_channel` VALUES (11,560);
INSERT INTO `jc_content_channel` VALUES (12,447);
INSERT INTO `jc_content_channel` VALUES (12,511);
INSERT INTO `jc_content_channel` VALUES (12,536);
INSERT INTO `jc_content_channel` VALUES (12,559);
INSERT INTO `jc_content_channel` VALUES (12,560);
INSERT INTO `jc_content_channel` VALUES (13,436);
INSERT INTO `jc_content_channel` VALUES (13,437);
INSERT INTO `jc_content_channel` VALUES (13,438);
INSERT INTO `jc_content_channel` VALUES (13,439);
INSERT INTO `jc_content_channel` VALUES (13,440);
INSERT INTO `jc_content_channel` VALUES (13,441);
INSERT INTO `jc_content_channel` VALUES (13,442);
INSERT INTO `jc_content_channel` VALUES (13,443);
INSERT INTO `jc_content_channel` VALUES (13,444);
INSERT INTO `jc_content_channel` VALUES (13,445);
INSERT INTO `jc_content_channel` VALUES (13,446);
INSERT INTO `jc_content_channel` VALUES (13,447);
INSERT INTO `jc_content_channel` VALUES (13,448);
INSERT INTO `jc_content_channel` VALUES (13,512);
INSERT INTO `jc_content_channel` VALUES (14,561);
INSERT INTO `jc_content_channel` VALUES (14,562);
INSERT INTO `jc_content_channel` VALUES (14,563);
INSERT INTO `jc_content_channel` VALUES (14,564);
INSERT INTO `jc_content_channel` VALUES (38,326);
INSERT INTO `jc_content_channel` VALUES (38,328);
INSERT INTO `jc_content_channel` VALUES (38,329);
INSERT INTO `jc_content_channel` VALUES (38,330);
INSERT INTO `jc_content_channel` VALUES (39,172);
INSERT INTO `jc_content_channel` VALUES (39,173);
INSERT INTO `jc_content_channel` VALUES (39,331);
INSERT INTO `jc_content_channel` VALUES (39,332);
INSERT INTO `jc_content_channel` VALUES (43,487);
INSERT INTO `jc_content_channel` VALUES (43,488);
INSERT INTO `jc_content_channel` VALUES (43,489);
INSERT INTO `jc_content_channel` VALUES (43,490);
INSERT INTO `jc_content_channel` VALUES (43,491);
INSERT INTO `jc_content_channel` VALUES (43,492);
INSERT INTO `jc_content_channel` VALUES (43,494);
INSERT INTO `jc_content_channel` VALUES (44,495);
INSERT INTO `jc_content_channel` VALUES (44,496);
INSERT INTO `jc_content_channel` VALUES (44,497);
INSERT INTO `jc_content_channel` VALUES (44,498);
INSERT INTO `jc_content_channel` VALUES (44,519);
INSERT INTO `jc_content_channel` VALUES (44,520);
INSERT INTO `jc_content_channel` VALUES (44,521);
INSERT INTO `jc_content_channel` VALUES (44,522);
INSERT INTO `jc_content_channel` VALUES (44,523);
INSERT INTO `jc_content_channel` VALUES (44,524);
INSERT INTO `jc_content_channel` VALUES (44,525);
INSERT INTO `jc_content_channel` VALUES (44,526);
INSERT INTO `jc_content_channel` VALUES (44,527);
INSERT INTO `jc_content_channel` VALUES (44,528);
INSERT INTO `jc_content_channel` VALUES (44,529);
INSERT INTO `jc_content_channel` VALUES (44,530);
INSERT INTO `jc_content_channel` VALUES (44,531);
INSERT INTO `jc_content_channel` VALUES (44,532);
INSERT INTO `jc_content_channel` VALUES (44,533);
INSERT INTO `jc_content_channel` VALUES (44,534);
INSERT INTO `jc_content_channel` VALUES (44,535);
INSERT INTO `jc_content_channel` VALUES (45,499);
INSERT INTO `jc_content_channel` VALUES (45,500);
INSERT INTO `jc_content_channel` VALUES (45,501);
INSERT INTO `jc_content_channel` VALUES (45,513);
INSERT INTO `jc_content_channel` VALUES (45,514);
INSERT INTO `jc_content_channel` VALUES (45,515);
INSERT INTO `jc_content_channel` VALUES (45,516);
INSERT INTO `jc_content_channel` VALUES (45,517);
INSERT INTO `jc_content_channel` VALUES (45,518);
INSERT INTO `jc_content_channel` VALUES (49,473);
INSERT INTO `jc_content_channel` VALUES (49,474);
INSERT INTO `jc_content_channel` VALUES (49,475);
INSERT INTO `jc_content_channel` VALUES (49,476);
INSERT INTO `jc_content_channel` VALUES (49,477);
INSERT INTO `jc_content_channel` VALUES (49,478);
INSERT INTO `jc_content_channel` VALUES (49,479);
INSERT INTO `jc_content_channel` VALUES (49,480);
INSERT INTO `jc_content_channel` VALUES (49,502);
INSERT INTO `jc_content_channel` VALUES (49,503);
INSERT INTO `jc_content_channel` VALUES (49,504);
INSERT INTO `jc_content_channel` VALUES (49,505);
INSERT INTO `jc_content_channel` VALUES (50,481);
INSERT INTO `jc_content_channel` VALUES (50,482);
INSERT INTO `jc_content_channel` VALUES (50,483);
INSERT INTO `jc_content_channel` VALUES (50,484);
INSERT INTO `jc_content_channel` VALUES (50,539);
INSERT INTO `jc_content_channel` VALUES (50,540);
INSERT INTO `jc_content_channel` VALUES (50,541);
INSERT INTO `jc_content_channel` VALUES (57,334);
INSERT INTO `jc_content_channel` VALUES (57,335);
INSERT INTO `jc_content_channel` VALUES (57,336);
INSERT INTO `jc_content_channel` VALUES (57,337);
DROP TABLE IF EXISTS `jc_content_check`;
CREATE TABLE `jc_content_check` (
  `content_id` int(11) NOT NULL,
  `check_step` tinyint(4) NOT NULL default '0' COMMENT '审核步数',
  `check_opinion` varchar(255) default NULL COMMENT '审核意见',
  `is_rejected` tinyint(1) NOT NULL default '0' COMMENT '是否退回',
  `reviewer` int(11) default NULL COMMENT '终审者',
  `check_date` datetime default NULL COMMENT '终审时间',
  PRIMARY KEY  (`content_id`),
  KEY `fk_jc_content_check_user` (`reviewer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容审核信息表';

INSERT INTO `jc_content_check` VALUES (172,2,NULL,0,1,'2011-01-04 14:34:23');
INSERT INTO `jc_content_check` VALUES (173,2,NULL,0,1,'2011-01-04 14:44:02');
INSERT INTO `jc_content_check` VALUES (326,2,NULL,0,1,'2011-12-19 11:42:28');
INSERT INTO `jc_content_check` VALUES (328,2,NULL,0,1,'2011-12-19 12:02:07');
INSERT INTO `jc_content_check` VALUES (329,2,NULL,0,1,'2011-12-19 13:17:53');
INSERT INTO `jc_content_check` VALUES (330,2,NULL,0,1,'2011-12-19 13:45:17');
INSERT INTO `jc_content_check` VALUES (331,2,NULL,0,1,'2011-12-19 13:53:49');
INSERT INTO `jc_content_check` VALUES (332,2,NULL,0,1,'2011-12-19 13:57:03');
INSERT INTO `jc_content_check` VALUES (334,2,NULL,0,1,'2011-12-19 14:18:12');
INSERT INTO `jc_content_check` VALUES (335,2,NULL,0,1,'2011-12-19 14:21:32');
INSERT INTO `jc_content_check` VALUES (336,2,NULL,0,1,'2011-12-19 14:25:26');
INSERT INTO `jc_content_check` VALUES (337,2,NULL,0,1,'2011-12-19 14:30:04');
INSERT INTO `jc_content_check` VALUES (436,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (437,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (438,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (439,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (440,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (441,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (442,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (443,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (444,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (445,3,NULL,0,1,'2014-08-27 11:09:48');
INSERT INTO `jc_content_check` VALUES (446,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (447,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (448,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (473,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (474,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (475,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (476,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (477,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (478,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (479,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (480,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (481,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (482,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (483,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (484,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (487,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (488,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (489,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (490,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (491,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (492,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (494,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (495,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (496,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (497,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (498,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (499,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (500,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (501,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (502,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (503,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (504,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (505,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (506,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (507,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (508,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (509,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (510,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (511,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (512,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (513,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (514,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (515,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (516,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (517,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (518,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (519,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (520,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (521,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (522,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (523,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (524,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (525,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (526,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (527,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (528,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (529,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (530,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (531,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (532,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (533,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (534,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (535,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (536,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (539,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (540,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (541,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (559,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (560,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (561,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (562,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (563,2,NULL,0,NULL,NULL);
INSERT INTO `jc_content_check` VALUES (564,2,NULL,0,NULL,NULL);
DROP TABLE IF EXISTS `jc_content_count`;
CREATE TABLE `jc_content_count` (
  `content_id` int(11) NOT NULL,
  `views` int(11) NOT NULL default '0' COMMENT '总访问数',
  `views_month` int(11) NOT NULL default '0' COMMENT '月访问数',
  `views_week` int(11) NOT NULL default '0' COMMENT '周访问数',
  `views_day` int(11) NOT NULL default '0' COMMENT '日访问数',
  `comments` int(11) NOT NULL default '0' COMMENT '总评论数',
  `comments_month` int(11) NOT NULL default '0' COMMENT '月评论数',
  `comments_week` smallint(6) NOT NULL default '0' COMMENT '周评论数',
  `comments_day` smallint(6) NOT NULL default '0' COMMENT '日评论数',
  `downloads` int(11) NOT NULL default '0' COMMENT '总下载数',
  `downloads_month` int(11) NOT NULL default '0' COMMENT '月下载数',
  `downloads_week` smallint(6) NOT NULL default '0' COMMENT '周下载数',
  `downloads_day` smallint(6) NOT NULL default '0' COMMENT '日下载数',
  `ups` int(11) NOT NULL default '0' COMMENT '总顶数',
  `ups_month` int(11) NOT NULL default '0' COMMENT '月顶数',
  `ups_week` smallint(6) NOT NULL default '0' COMMENT '周顶数',
  `ups_day` smallint(6) NOT NULL default '0' COMMENT '日顶数',
  `downs` int(11) NOT NULL default '0' COMMENT '总踩数',
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容计数表';

INSERT INTO `jc_content_count` VALUES (172,4,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (173,7,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (326,14,0,0,0,0,0,0,0,2,2,1,2,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (328,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (329,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1);
INSERT INTO `jc_content_count` VALUES (330,8,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (331,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (332,11,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (334,4,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (335,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (336,6,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (337,21,0,0,0,0,0,0,0,3,3,1,3,1,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (436,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (437,4,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (438,19,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (439,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (440,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (441,16,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (442,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (443,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (444,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (445,21,8,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (446,14,6,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (447,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (448,57,4,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (473,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (474,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (475,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (476,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (477,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (478,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (479,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (480,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (481,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (482,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (483,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (484,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (487,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (488,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (489,7,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (490,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (491,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (492,19,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (494,28,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (495,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (496,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (497,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (498,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (499,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (500,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (501,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (502,118,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (503,91,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (504,19,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (505,27,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (506,10,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (507,20,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (508,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (509,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (510,29,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (511,34,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (512,63,4,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (513,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (514,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (515,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (516,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (517,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (518,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (519,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (520,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (521,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (522,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (523,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (524,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (525,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (526,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (527,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (528,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (529,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (530,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (531,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (532,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (533,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (534,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (535,44,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (536,44,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (539,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (540,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (541,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (559,3,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (560,40,22,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (561,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (562,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (563,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (564,15,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0);
DROP TABLE IF EXISTS `jc_content_ext`;
CREATE TABLE `jc_content_ext` (
  `content_id` int(11) NOT NULL,
  `title` varchar(150) NOT NULL COMMENT '标题',
  `short_title` varchar(150) default NULL COMMENT '简短标题',
  `author` varchar(100) default NULL COMMENT '作者',
  `origin` varchar(100) default NULL COMMENT '来源',
  `origin_url` varchar(255) default NULL COMMENT '来源链接',
  `description` varchar(255) default NULL COMMENT '描述',
  `release_date` datetime NOT NULL COMMENT '发布日期',
  `media_path` varchar(255) default NULL COMMENT '媒体路径',
  `media_type` varchar(20) default NULL COMMENT '媒体类型',
  `title_color` varchar(10) default NULL COMMENT '标题颜色',
  `is_bold` tinyint(1) NOT NULL default '0' COMMENT '是否加粗',
  `title_img` varchar(100) default NULL COMMENT '标题图片',
  `content_img` varchar(100) default NULL COMMENT '内容图片',
  `type_img` varchar(100) default NULL COMMENT '类型图片',
  `link` varchar(255) default NULL COMMENT '外部链接',
  `tpl_content` varchar(100) default NULL COMMENT '指定模板',
  `need_regenerate` tinyint(1) NOT NULL default '1' COMMENT '需要重新生成静态页',
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展表';

INSERT INTO `jc_content_ext` VALUES (172,'酷乐影音','酷乐影音2010 1.0.3.6',NULL,NULL,NULL,'随点随看','2013-09-27 09:55:26',NULL,NULL,NULL,0,'/u/cms/www/201309/19141318apz1.jpg','http://img.duote.com/softImg/soft/25902_s.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (173,'千千静听','千千静听(TTPlayer) V5.7 BETA1',NULL,NULL,NULL,'千千静听是一款完全免费的音乐播放软件，拥有自主研发的全新音频引擎，集播放、音效、转换、歌词等众多功能于一身。其小巧精致、操作简捷、功能强大的特点，深得用户喜爱，被网友评为中国十大优秀软件之一，并且成为目前最受欢迎的音乐播放软件。','2013-09-27 09:55:07',NULL,NULL,NULL,0,'/u/cms/www/201309/19141200ip5c.jpg','http://ttplayer.qianqian.com/upload/100902/100902162707s.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (326,'魔兽世界',NULL,NULL,NULL,NULL,'大地的裂变','2013-09-27 09:52:41',NULL,NULL,NULL,0,'/u/cms/www/201309/19114043tp85.jpg','/u/cms/www/201309/19114201tdir.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (328,'天下3',NULL,NULL,NULL,NULL,'6大革新，精彩无穷无尽','2013-09-27 09:51:16',NULL,NULL,NULL,0,'/u/cms/www/201309/191201449moh.jpg','/u/cms/www/201309/191203538tdp.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (329,'神魔大陆',NULL,NULL,NULL,NULL,'暮光之城','2013-09-27 09:50:35',NULL,NULL,NULL,0,'/u/cms/www/201309/19131809acqm.jpg','/u/cms/www/201309/19131949r2ge.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (330,'永恒之塔',NULL,NULL,NULL,NULL,'神之竞技场','2013-09-27 09:49:38',NULL,NULL,NULL,0,'/u/cms/www/201309/191342393mlg.jpg','/u/cms/www/201309/19134448qvza.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (331,'腾讯QQ ',NULL,NULL,NULL,NULL,'2011 正式版','2013-09-27 09:54:07',NULL,NULL,NULL,0,'/u/cms/www/201309/191351590e53.jpg','/u/cms/www/201309/19135821ij0m.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (332,'风行网络电影',NULL,NULL,NULL,NULL,'风行网络电影','2013-09-27 09:53:16',NULL,NULL,NULL,0,'/u/cms/www/201309/19135642zjak.jpg','/u/cms/www/201309/19135645ge7r.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (334,'搜狗拼音输入',NULL,NULL,NULL,NULL,'准确快速','2013-09-27 09:58:07',NULL,NULL,NULL,0,'/u/cms/www/201309/19141700opui.jpg','/u/cms/www/201309/19141756u9sa.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (335,'有道词典',NULL,NULL,NULL,NULL,'翻译多语种网络词典','2013-09-27 09:57:18',NULL,NULL,NULL,0,'/u/cms/www/201309/19142041eu8x.jpg','/u/cms/www/201309/19142206y73m.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (336,'迅雷7',NULL,NULL,NULL,NULL,'迅雷7.0','2013-09-27 09:56:53',NULL,NULL,NULL,0,'/u/cms/www/201309/19142430589t.jpg','/u/cms/www/201309/19142451945q.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (337,'多玩YY',NULL,NULL,NULL,NULL,'YY4.0是多玩YY语音的全新版本，活动中心盛大起航，汇集YY上最好最优质的频道和活动，提供YY上最有价值的内容，不需再为找好 频道而费尽心机。','2013-09-27 09:55:50',NULL,NULL,NULL,0,'/u/cms/www/201309/19142818yvty.jpg','/u/cms/www/201309/19142840ycm6.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (436,'内地夫妇因航班延误闹香港机场袭警被拘捕',NULL,NULL,NULL,NULL,'南都讯记者石秋菊发自香港航班延误，乘客鼓噪，闹事打砸，这一幕发生在7月5日的香港机场。一对内地夫妇的男事主砸国泰航空的柜台更泼汽水，被香港警方拘捕时，妻子更涉嫌推倒一名警员，最后夫妇两人都被警方带走调查。香港警方表示，涉案夫妇中丈夫姓徐，称\"','2013-07-08 15:34:08',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (437,'男子嫖娼后用迷药抢劫卖淫女',NULL,NULL,NULL,NULL,'晨报讯(记者颜斐)王某嫖娼后骗卖淫女喝下他放入安眠药的饮料，然后待对方熟睡后实施抢劫。近日朝阳区检察院以涉嫌抢劫罪对抢劫卖淫女的王某批准逮捕。今年5月，王某通过微信与张某取得联系，并在微信上谈妥了卖淫嫖娼的价钱。6月2日晚，王某在支付了嫖资后\"','2013-07-08 15:34:09',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (438,'骗子假冒导演谎称能安排演员诈骗获缓刑',NULL,NULL,NULL,NULL,'晨报讯(记者李庭煊)尹某谎称自己是中影数字基地的执行导演，以安排彭某做特邀演员为诱饵，骗取其1万元。近日怀柔法院以诈骗罪判处尹某拘役4个月，缓刑6个月。去年3月，尹某在怀柔区杨宋镇安乐庄村租了一个群众演员大院，招募群众演员。9月彭某前来应聘，\"','2013-07-08 15:34:11',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (439,'司机穿拖鞋开车低头点烟致追尾造成1人死亡',NULL,NULL,NULL,NULL,'扬子晚报讯(记者郭一鹏通讯员张晓冬)穿着拖鞋开车，还在行驶中低头点烟，结果抬头时突然发现前面有辆电动车，司机赶紧踩了一脚刹车，哪知拖鞋打滑，车辆没有控制住直接撞了上去。电动车上两名老人倒地后，其中一人因伤势过重不幸死亡。前不久，南京大厂发生\"','2013-07-08 15:34:11',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (440,'男子非法拘禁妻子情夫 殴打侮辱并索钱被诉',NULL,NULL,NULL,NULL,'晨报讯(记者颜斐)发现妻子有了情人郭某，徐某以讨债为名，伙同两个朋友将郭某拘禁3天，其间不仅实施殴打和侮辱行为，还向郭某索要3000万元补偿费。近日，徐某因涉嫌非法拘禁罪和敲诈勒索罪被公诉到朝阳法院，两同伙也因非法拘禁罪被公诉。据指控，徐某\"','2013-07-08 15:34:12',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (441,'伪造国字头研究院承接水电项目 曾通过政府审查',NULL,NULL,NULL,NULL,'本报记者赵志锋中国区域特色经济研究院，听起来多么唬人的机构。如果不是法院在审理民事案件中查清它的真实面目，不知道这个国字头机构还要存在多久。在法院司法建议的推动下，今年5月，这个活动在甘肃省的中国区域特色经济研究院西部中心(以下简称西部中心\"\r\n','2013-07-08 15:34:13',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (442,'城管局司机袒护无证幼儿园踢飞凳子阻挠采访',NULL,NULL,NULL,NULL,'法制网记者张冲法制网通讯员郭佳音一段摄录于6月25日的视频，清晰地记录了黑龙江省哈尔滨市城管局一名职工的粗暴行为。画面显示：城管督察车上下来的男子，走到麻辣烫摊前，趁女记者孙晓卓不备一脚将她坐的塑料凳踢飞，孙重重地摔在地上。目前，这名城管局职\"\r\n','2013-07-08 15:34:14',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (443,'2名女乘客因航班延误殴打地勤人员被拘留5天',NULL,NULL,NULL,NULL,'温都讯昨晚，市公安局机场分局对“6·28瑞安籍乘客殴打国航地勤人员”一事作出处理：对殴打国航地勤人员朱某某(女)的陈某某(女)、刘某某(女)均给予行政拘留5日并处200元罚款的处罚。记者了解到，昨天这两名女乘客从北京返回温州后，到候机楼派出\"\r\n','2013-07-08 15:34:15',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (444,'假记者向百名官员寄艳照敲诈信被快递员识破',NULL,NULL,NULL,NULL,NULL,'2013-07-08 15:34:17',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `jc_content_ext` VALUES (445,'男子欠债万元没钱还 自导绑架案骗老婆付赎金',NULL,NULL,NULL,NULL,'据江淮晨报报道，铜陵一男子在外花天酒地，借债一万元无钱归还，竟与朋友合伙骗自己老婆。7月3日，该男子声称自己被人“绑架”，要求妻子拿“赎金”一万元救自己。最后老婆没骗到，自己的朋友却因吸毒被公安机关依法行政拘留。7月3日，铜陵市长江路派出所在\"\r\n','2013-07-08 15:34:18',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (446,'新娘婚宴上逃婚 新郎起诉索要精神损失费',NULL,NULL,NULL,NULL,NULL,'2013-07-08 15:34:18',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `jc_content_ext` VALUES (447,'醉酒男子强奸孕妇未遂 持刀返回找手机伤人',NULL,NULL,NULL,NULL,'东南网7月8日讯(海峡导报记者蔡晶晶通讯员杨媛林曼娜)酒后翻墙进别人家，不顾女主人怀有9个月身孕，竟想实施强奸，没能得逞的他还用刀划伤孕妇的婆婆。昨日，漳州市漳浦县检察院以涉嫌强奸罪对黄某龙依法批准逮捕。4月19日晚上，家住漳浦县深土镇的犯\">\r\n','2013-07-08 15:34:20',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (448,'两名少年为挣钱在多个省市抢劫杀死7人',NULL,NULL,NULL,NULL,NULL,'2013-07-08 15:34:21',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `jc_content_ext` VALUES (473,'推拿 第一集',NULL,NULL,NULL,NULL,'沙宗琪推拿中心是位于南京的一家小型盲人推拿中心，生意红火，预约电话不断，可接连出事。主力盲人技师曲芒来因与相恋的女朋友邓晓梅分手而把自己关在屋里声嘶力竭地唱了一天，急火攻心导致吐血。刚把芒来送到医院，沙复明又接到电话，他的盲人徒弟马跃，爬上了烟囱顶要自杀。','2013-08-20 11:30:47','/v6/u/cms/www/201308/20112956tday.mp4','FLV',NULL,0,'/u/cms/www/201309/1316285322ks.jpg',NULL,'/u/cms/www/201309/13163306pqvc.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (474,'推拿 第二集',NULL,NULL,NULL,NULL,'王泉和孔佳玉到南京后，虽然王泉的父母对两人回家感到高兴，但却不知道两人回来就不打算走了。从小被惯大的弟弟王海觉得哥哥和没过门的嫂子回来就是吃闲饭抢房子的，这让孔佳玉很别扭。一顿尴尬的午饭过后使得王泉有些绝望，不得不对孔佳玉说了实话，原本打算开店的钱都买了基金被套住了。两人只好把开店的梦想暂且搁置。','2013-08-20 11:36:14','/v6/u/cms/www/201308/20113559a76z.mp4','FLV',NULL,0,'/u/cms/www/201309/13162904gqxm.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (475,'推拿 第三集',NULL,NULL,NULL,NULL,'王泉找到沙复明，提出要跟孔佳玉到推拿院上班。沙复明满口答应，他开出的各种条件待遇王泉都能接受，可唯独有一条，就是王泉和孔佳玉必须服从推拿中心管理，男女分开住宿舍。孔佳玉尽管不乐意和王泉分开，但还是跟王泉一起来到了推拿中心，成为正式的盲人技师。他们对中心的工作、生活环境也渐渐适应起来。','2013-08-20 11:36:43','/v6/u/cms/www/201308/20114137qyaa.mp4','FLV',NULL,0,'/u/cms/www/201309/13162913da42.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (476,'推拿 第四集',NULL,NULL,NULL,NULL,'王泉和孔佳玉的到来，让推拿中心一下子热闹起来。他们结识了最会逗闷子的张一光，还有整天围着芒来转的金嫣，和最小最帅的马跃。马跃是推拿中心第一个帮助孔佳玉的人，孔佳玉把他看做自己的弟弟，而马跃对孔佳玉却有种说不出亲近感。推拿中心里一团和气，高唯却越发觉得不对劲。','2013-08-20 11:43:16','/v6/u/cms/www/201308/201142546ray.mp4','FLV',NULL,0,'/u/cms/www/201309/131629222u39.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (477,'推拿 第五集',NULL,NULL,NULL,NULL,'洗衣间里，金嫣给芒来手洗衣服，遇到高唯。高唯一直搞不明白，金嫣怎么就死心塌地地看上芒来了，她可是看不到芒来有什么好。金嫣很认真告诉高唯，她十岁时得了黄斑病变，家人花了不少钱给她治疗，从此视力越来越差，在那痛苦的日子里，她突然意识到，这辈子别的什么都可以没有但要有爱情，而芒来正是她心目中爱的化身。','2013-08-20 11:44:30','/v6/u/cms/www/201308/201144219ddf.mp4','FLV',NULL,0,'/u/cms/www/201309/13162931ofsn.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (478,'推拿 第六集',NULL,NULL,NULL,NULL,'孔佳玉心烦，拉着马跃上街吃冰淇淋。两人路过影楼时碰到了在外拉客的员工。员工发现他们是盲人，就提出为他们免费拍婚纱照。孔佳玉一听是免费就带马跃进店里了。','2013-08-20 11:45:42','/v6/u/cms/www/201308/2011452316m3.mp4','FLV',NULL,0,'/u/cms/www/201309/13162941ipg2.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (479,'推拿 第七集',NULL,NULL,NULL,NULL,'都红已经做好了被沙复明拒绝的准备，她自信地向沙复明申明自己可以在一个月的时间里学会打扫、做饭、和推拿。但经过再三考虑，沙复明终于同意留下都红，算作试用。','2013-08-20 11:46:57','/v6/u/cms/www/201308/20114641p1fb.mp4','FLV',NULL,0,'/u/cms/www/201309/13163101ccd3.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (480,'推拿 第八集',NULL,NULL,NULL,NULL,'马跃和孔佳玉出去吃饭的事儿，经张一光的嘴说起来就显得神神秘秘，仿佛马跃和孔佳玉之间发生了什么似的，听得王泉像心头捅了刀子似的难受。马跃父亲到推拿中心找马跃。因平时在各地出差没时间给马跃过生日，马父心里很愧对马跃，就拿了两万块钱给儿子。遭马跃拒绝后，马父把钱交给沙复明，算作给马跃买画画颜料用的。','2013-08-20 11:47:53','/v6/u/cms/www/201308/20114740aye9.mp4','FLV',NULL,0,'/u/cms/www/201309/13163113pd3s.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (481,'51区：美国最神秘的军事基地',NULL,NULL,NULL,NULL,'冷战时期进行秘密任务的51区，向来是阴谋论者关注的目标，也让一般人好奇不已。地下隧道、隐藏的敌机、政府的飞碟秘密档案，在沉默数十年之后，知道51区内情的人首度开口，揭开这个神秘之地的面纱。','2013-08-20 11:56:55','/u/cms/www/201308/20115647nzph.mp4','FLV',NULL,0,'/u/cms/www/201308/20115532h8tv.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (482,'斯大林格勒大血战70周年祭',NULL,NULL,NULL,NULL,'2012年5月16日，《非常时空》华丽转身，《新纪录》从此诞生，同样的播出时间，同样的精彩。 全新视角，经典纪录。从失落的古老文明到尖端的现代科技，从悲喜交加的自然故事，到波澜壮阔的人类历史。大千世界，尽收眼底！BTV科教频道，每天都有《新纪录》。','2013-08-20 12:02:03','/u/cms/www/201308/20120155enlg.mp4','FLV',NULL,0,'/u/cms/www/201308/201159459afm.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (483,'纽约男子：刺青、摩托、救援',NULL,NULL,NULL,NULL,'在纽约的各个地下室和黑暗的陋巷中，有许多落难动物身患疾病、痛苦而又孤单，它们急需救援。2007年，8名纽约男子，发现他们除了刺青和摩托车外，还有一个共同点，就是痛恨虐待动物的恶行，于是他们组织了一个独一无二的救援团队——动物救难队，对动物的热爱，让他们敢于尝试别人不敢做的事情，对落难动物伸出援手。','2013-08-20 12:05:52','/u/cms/www/201308/20120529auxr.mp4','FLV',NULL,0,'/u/cms/www/201308/20120531bbei.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (484,'镖行天下：揭秘镖局',NULL,NULL,NULL,NULL,'《经典传奇》借助《传奇故事》的经验，同时又是一档大型化的历史人文故事节目。继承《传奇故事》的人性化讲述，同时力求新的突破。内容将具有《传奇故事》“加”美国《探索》纪实的新鲜风格。节目最大的亮点还是在于选题的“升级”，选题集中在重大历史问题，时代人物，动人心魄的政治军事斗争，离奇事件。选题在“传奇性”的基础上，还具有鲜明的“经典性”、“热点性”、“阶段火爆性”的特点。','2013-08-20 12:09:02','/u/cms/www/201308/2012085643sz.mp4','FLV',NULL,0,'/u/cms/www/201308/20120732ybv8.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (487,'金马影展公布焦点导演',NULL,NULL,NULL,NULL,'金马50惊喜连连，金马国际影展也端出超级菜色，继公布蔡明亮新作《郊游》担任开幕片，昨天（9月9日）发布的首波片单，聚焦两位当今最受期待的大师候选人，一位是甫以《蓝色是最温暖的颜色》荣获本届戛纳影展金棕榈奖的法国导演阿布戴柯西胥，一位是以《分居风暴》囊括柏林影展金熊奖与奥斯卡最佳外语片的伊朗导演阿斯哈法哈蒂。金马影展不仅会放映他们最新得奖作品，还把两人所有影片一网打尽，让影迷完整见证他们创作历程，探勘大师如何养成。','2013-08-21 11:34:29',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/111745157ps9.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (488,'“蛊术”首现《狄仁杰》 9月28日登陆大银幕',NULL,NULL,NULL,NULL,'由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。','2013-08-21 11:59:29',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/11173804j6hj.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (489,'《金刚王》曝解密功夫秘籍',NULL,NULL,NULL,NULL,'“亚洲舞王”南贤俊演绎魔尸金刚“疯猿”','2013-08-21 13:40:11',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/11173334yvdd.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (490,'《大明劫》曝概念海报 冯远征戴立忍末日战',NULL,NULL,NULL,NULL,'《大明劫》曝概念海报 冯远征戴立忍末日战','2013-08-21 14:35:35',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/11172219hw2t.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (491,'《抹布女》收视攀升 海清张译激情床照曝光',NULL,NULL,NULL,NULL,'网易娱乐9月11日报道 由海清、张译、芦芳生联袂主演的都市生活轻喜剧《抹布女也有春天》，以火爆网络的“女汉子”都市新女性造型，及热门的抹布女话题新一轮收视正在节节攀升至0.9。该剧接连不断的搞笑故事情节和主演们夸张的漫画式表演，新婚之夜海清和张译二人在床上甜蜜一吻时，海清身下的床板突然塌陷，两人只能相视的尴尬一笑。后续中并上演着激情床戏部份，颠覆帅气女汉子。','2013-08-21 14:47:08',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/1117164754x7.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (492,'《老公的春天》播出过半',NULL,NULL,NULL,NULL,'4月19日报道 陈好产后首部作品《新编辑部故事》将于本月20日全国上星播出。日前片方再度公布一组剧照，女一号安妮的扮演者陈好一袭墨绿旗袍出镜，墨镜、皮草、奢侈品手包尽显运营总监的时尚女魔头气派，而陈好的婀娜身段和姣好面容让人很难相信这是她产后拍摄的作品。另外cosplay风格的照片尽显六人搞怪趣味，陈好还与黄海波穿上凤冠霞帔上演成亲戏码，用百变造型来诠释重口味喜剧，时尚造型与搞怪的视觉冲撞也让观众的期待值高涨。','2013-08-21 14:49:52',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/11171211kmih.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (494,'《桐柏英雄》画册曝光',NULL,NULL,NULL,NULL,NULL,'2013-08-21 14:58:46',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/11170224nw94.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (495,'刘晓庆今日大婚 第四任老公系将门之后政协委员',NULL,NULL,NULL,NULL,'8月20日早上，刘晓庆团队透露刘晓庆与第四任丈夫已与去年注册结婚，声明中还称其丈夫比刘晓庆大，是将门之后，事业成功。晚些时分，得知刘晓庆将于美国时间8月20日在美国举行婚礼。','2013-08-21 15:12:25',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (496,'赵本山女儿正式进军娱乐圈 将与潘长江女儿比拼',NULL,NULL,NULL,NULL,'浙江卫视宣布推出全国首档青年励志节目，陈宝国儿子陈月末、潘长江女儿潘阳等30多位明星子女均已确定加盟。赵本山女儿赵一涵也有望加盟，藉此正式进军娱乐圈，并将与潘长江女儿潘阳同台对决。','2013-08-21 15:19:59',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (497,'《全民目击》曝预告 孙红雷郭富城上演猫鼠游戏',NULL,NULL,NULL,NULL,'剧情预告片开场以余男扮演的律师质问：“到底是谁开车撞死了杨丹？”随着法庭质证的深入，罪案真相却愈发扑朔迷离，赵立新扮演的司机、邓家佳扮演的“林萌萌”还是孙红雷扮演的“林泰”，究竟谁才是真正的凶手？预告片将每位嫌疑人矛盾的内心处理得很细腻，带领观众进入杀人后的恐惧、无奈，透过高频率的剧情反转完成对案情真相的诠释。','2013-08-21 15:24:11','/u/cms/www/201308/21152255jhhm.mp4','FLV',NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (498,'冯小刚定春晚相声小品类基调：唱赞歌一律不要',NULL,NULL,NULL,NULL,'前日的碰头会上，冯小刚大胆地提出要摒弃那些煽情、唱赞歌的东西，所有创作者要放开手脚，敢写敢说，呈现出带尖带刺、有棱有角的相声和小品来。','2013-08-22 09:13:41',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (499,'朱为群：大数据时代亟须透明税制',NULL,NULL,NULL,NULL,NULL,'2013-08-22 09:21:00',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (500,'宋石男：谣言是世界上最古老的传媒',NULL,NULL,NULL,NULL,'专栏作家，西南民族大学教师，为《南方周末》、《新京报》、《东方早报》、《看历史》等平媒撰稿，现在《看天下》开有备受好评的“风物志”叙事专栏，在《南方都市报》开有个论专栏及历史评论专栏，在《新快报》开有“微言宋听”一周微博点评专栏。','2013-08-22 09:28:09',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (501,'俞天任：麦克阿瑟在日本到底做了什么？',NULL,NULL,NULL,NULL,NULL,'2013-08-22 09:45:50',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (502,'小爸爸 第一集',NULL,NULL,NULL,NULL,'由于齐大胜生病住院，李三弟找不到男模，他提议让于果顶替齐大胜，李三妹不太同意李三弟的主意，但又找不到其它更好的办法，她是担心遭到泰勒反对，要是于果参加走秀活动，到时自然就会跟夏天同台表演，如果泰勒发现父子两人又走在了一起，自然会暴怒万分。李三弟认为于果是一个好父亲，他指出之前夏天生病的时候，于果跑了很多家药店买药，李三妹不否认于果对夏天的好，但是她又不能表态支持于果，不然就成了泰勒的敌人。\r\n.','2013-09-09 16:51:46','/v6/u/cms/www/201309/09165108yyn6.mp4','FLV',NULL,0,'/u/cms/www/201309/09165026tk5e.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (503,'小爸爸 第二集',NULL,NULL,NULL,NULL,'由于齐大胜生病住院，李三弟找不到男模，他提议让于果顶替齐大胜，李三妹不太同意李三弟的主意，但又找不到其它更好的办法，她是担心遭到泰勒反对，要是于果参加走秀活动，到时自然就会跟夏天同台表演，如果泰勒发现父子两人又走在了一起，自然会暴怒万分。李三弟认为于果是一个好父亲，他指出之前夏天生病的时候，于果跑了很多家药店买药，李三妹不否认于果对夏天的好，但是她又不能表态支持于果，不然就成了泰勒的敌人。\r\n.','2013-09-09 16:55:35','/v6/u/cms/www/201309/09165453tawt.mp4','FLV',NULL,0,'/u/cms/www/201309/091655240woj.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (504,'小爸爸 第三集',NULL,NULL,NULL,NULL,'由于齐大胜生病住院，李三弟找不到男模，他提议让于果顶替齐大胜，李三妹不太同意李三弟的主意，但又找不到其它更好的办法，她是担心遭到泰勒反对，要是于果参加走秀活动，到时自然就会跟夏天同台表演，如果泰勒发现父子两人又走在了一起，自然会暴怒万分。李三弟认为于果是一个好父亲，他指出之前夏天生病的时候，于果跑了很多家药店买药，李三妹不否认于果对夏天的好，但是她又不能表态支持于果，不然就成了泰勒的敌人。\r\n.','2013-09-09 16:59:13','/v6/u/cms/www/201309/09165859nuu5.mp4','FLV',NULL,0,'/u/cms/www/201309/09165821s06r.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (505,'小爸爸 第四集',NULL,NULL,NULL,NULL,'由于齐大胜生病住院，李三弟找不到男模，他提议让于果顶替齐大胜，李三妹不太同意李三弟的主意，但又找不到其它更好的办法，她是担心遭到泰勒反对，要是于果参加走秀活动，到时自然就会跟夏天同台表演，如果泰勒发现父子两人又走在了一起，自然会暴怒万分。李三弟认为于果是一个好父亲，他指出之前夏天生病的时候，于果跑了很多家药店买药，李三妹不否认于果对夏天的好，但是她又不能表态支持于果，不然就成了泰勒的敌人。\r\n.','2013-09-09 17:00:23','/v6/u/cms/www/201309/09165948qmff.mp4','FLV',NULL,0,'/u/cms/www/201309/09170006n0hs.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (506,'云南山洪冲断路桥 致4人遇难7人失踪',NULL,NULL,NULL,NULL,'9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。','2013-09-10 09:22:39',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/10092229195q.jpg',NULL,NULL,0);
INSERT INTO `jc_content_ext` VALUES (507,'广州又现“楼歪歪” 三栋居民楼倾斜',NULL,'胥柏波',NULL,NULL,'上月初，广州番禺区厦滘村一居民楼因地基下陷，房屋倾斜被拆除，5日晚至今，当地又连续有三栋居民楼发生倾斜。目前，周边十余栋受影响的房屋村民已经全部疏散，当地街道办开放临时安置点接纳受影响群众，而疑似事故元凶的附近工地已停止施工','2013-09-10 09:50:40',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/100950366ij3.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (508,'海口酒行发生爆炸两人受伤 玻璃碎片炸飞50米外',NULL,'毛鑫',NULL,NULL,'今天早上8点40分左右，海口市蓝天路一家酒行发生爆炸，两人受伤被送往医院。现场破坏严重，50米外能看到被炸飞的玻璃碎片，停在路边的多部车辆受损。目前警方已介入调查','2013-09-10 09:53:55',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/100953460bvo.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (509,'朱镕基新书曾提前印样本向中央领导征求意见',NULL,NULL,NULL,NULL,'京华时报讯 由人民出版社出版的《朱镕基上海讲话实录》于8月12日向全国发行。昨天，人民出版社常务副社长任超做客中新网时介绍，该书首印110万册全部发出，并已取得65万册的销售佳绩。任超表示，该书出版过程中，朱镕基同志要求实事求是，如实呈现当时的一些情况','2013-09-10 10:28:25','/u/cms/www/201309/10102800aa0b.mp4','FLV',NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (510,'官方称翻越澳门大学新校区围墙属偷渡行为',NULL,NULL,NULL,NULL,'澳门大学横琴新校区围墙外，围栏上间隔一段就悬挂了“严禁翻越澳门大学围墙，违者将予以行政处罚”字样的警示横幅。','2013-09-10 10:38:52',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/10103806oifr.jpg',NULL,NULL,0);
INSERT INTO `jc_content_ext` VALUES (511,'毛泽东逝世37周年 亲属及工作人员现身纪念',NULL,NULL,NULL,NULL,'9月9日，毛泽东亲属、身边工作人员来到毛泽东纪念堂。','2013-09-10 10:45:34',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/10104531ukj5.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (512,'房价上涨最快的城市排行 去那买房让你赚翻',NULL,NULL,NULL,NULL,NULL,'2013-09-10 14:40:05',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/10144918u244.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (513,'北影开学典礼新生睡倒一片 红衣女抢镜',NULL,NULL,NULL,NULL,'网易娱乐9月6日报道（图/李道忠 文/小易）9月5日，北京电影学院开学典礼如期举行。2013届新生和老师们挤满了学院礼堂，其中一位红衣女生相当抢镜。在不到30分钟的校领导演讲后，新生们终于还是难抵瞌睡虫袭击，睡倒一片。据知，今年的表演系招收人数超过了往年，众多学生中年龄最小的仅有16岁，全校各系共招573人。据在校生表示，表演系之所以考的人数众多也和高考文化课分数低有关，所以很多报考的学生并非成绩很好。','2013-09-12 10:59:15',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12105908yuqz.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (514,'北影表演系新生自曝K歌照',NULL,NULL,NULL,NULL,'网易娱乐9月10日报道(图文/小易) 日前，几名认证为“北京电影学院2013级表演本科”的新生，在微博上曝光了同学们聚在一起唱歌的照片，还感慨“中国好同学，我们班唱歌都太好听了。”','2013-09-12 11:04:04',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/121104013vts.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (515,'中戏办开学典礼 2013级新生首曝光',NULL,NULL,NULL,NULL,'网易娱乐9月10日报道（图文/小易）9月9日上午，中央戏剧学院2013—2014学年开学典礼在学院昌平校区举行，来自中国乃至世界各地的570名新生参加了典礼。','2013-09-12 11:08:22',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12110817ejy1.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (516,'高考36年变迁珍贵影像',NULL,NULL,NULL,NULL,'1977年冬天，中断了十年又重新恢复的高考制度，开始改变这个庞大国家无数人的命运。一纸试卷废除了“推荐上大学”，给当时渴望改变命运的人们一个公平竞争的机会。很多人借此叩开了另一个世界的大门，走上辉煌的人生道路。','2013-09-12 11:12:45',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/1211124392sy.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (517,'美国富二代大学生的奢侈生活',NULL,NULL,NULL,NULL,' 在美国，日趋上涨的大学花费已经严重威胁到了低收入家庭的学生，大量学生即使在毕业四五年后还身背学债。而另一方面，一种迎合富人家庭大学生的服务新模式已经出现。','2013-09-12 11:19:03',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/1211190051if.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (518,'华中师范大学新生家长“打地铺”过夜',NULL,NULL,NULL,NULL,'2013年9月7日，武汉华中师范大学新生入学首日，学校在该校佑铭体育馆内为新生家长们提供免费住宿。当晚有400多名学生家长在体育馆打地铺过夜。这是该校连续第8年在新生开学报到时为家长提供免费住宿。','2013-09-12 11:22:50',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12112247lfhe.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (519,'李冰冰时尚大片',NULL,NULL,NULL,NULL,'网易娱乐9月12日报道(图文/小易)日前，李冰冰为某知名时尚杂志拍摄的封面大片曝光。','2013-09-12 12:07:42',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12140033b5mx.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (520,'52岁刘德华上海开唱与舞伴玩借位吻',NULL,NULL,NULL,NULL,'网易娱乐9月12日报道(图文/CFP) 9月11日，刘德华上海演唱会举行。为搏粉丝开心他也是下足了工夫，仅靠4条钢索支撑的Gondola-无盖吊篮缓缓升上五层楼高再沿著舞台顶层钢架横行。表演中刘德华不仅与舞伴玩借位吻，还大秀八块肌肉情。情绪激动的刘德华最后更泣不成声。','2013-09-12 13:43:06',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12135944xk9f.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (521,'全智贤着衬衫秀长腿',NULL,NULL,NULL,NULL,'网易娱乐9月12日报道(图文/东星) 全智贤日前离开香港返韩国。当天，全智贤穿上长恤衫服装，大秀长腿配戴墨镜和黑鞋现身香港机场，助手为她辨理登机手续后，在外籍保安护送下离港入闸，最后还跟保安握手及道谢，便向传媒挥手离开。','2013-09-12 13:45:55',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12134552bk69.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (522,'王智写真演绎复古风情',NULL,NULL,NULL,NULL,'网易娱乐9月12日报道(图文/小易) 正值中秋来临之际，新生代功夫女星王智推出一组复古写真。','2013-09-12 13:49:36',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12134933sxsx.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (523,'宋祖英升任团长后首带队演出',NULL,NULL,NULL,NULL,'网易娱乐9月12日报道(图文/小易) 10日，宋祖英升任团长后首次带队海政文工团到海校献唱，获得热烈掌声。今年8月，宋祖英由海政文工团副团长升任正团长，此前曾传她会减少演艺工作。','2013-09-12 13:52:46',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12135243x57l.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (524,'杨幂亮相纽约时装周',NULL,NULL,NULL,NULL,'网易娱乐9月11日报道(图/小易) 近日，杨幂亮相纽约时装周。','2013-09-12 13:58:30',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12135827xy0y.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (525,'王丽坤纯美大片',NULL,NULL,NULL,NULL,'网易娱乐9月11日报道(图文/小易) 近日，素颜女神王丽坤应某知名杂志邀约拍摄了一组纯美大片。','2013-09-12 14:08:58',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12140851yp14.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (526,'张智霖签新公司获直升机接送 空姐护驾',NULL,NULL,NULL,NULL,'网易娱乐9月11日报道(图文/CFP) 10日，张智霖高价签约中国3D数码娱乐有限公司。因为《冲上云霄2》而身价倍增的张智霖签约新公司获空姐陪伴、飞机接送。','2013-09-12 14:14:53',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/121414505xah.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (527,'何晟铭出席敦煌保护活动 首谈《盗墓笔记》',NULL,NULL,NULL,NULL,'网易娱乐9月10日报道(图文/小易) 9月9日，敦煌保护基金启动仪式在杭州举行，网易CEO丁磊、演员何晟铭等出席活动并呼吁社会各界共同关注敦煌、保护文化，让敦煌文明代代相传。何晟铭作为整场活动唯一表演嘉宾，现场颇为应景的演唱一曲《佛说》。而主持人也在采访中问到：“听说您要出演《盗墓笔记》？会不会到敦煌来采景呢？”何晟铭则幽默的回答：“大早上的别提晚上的事，要盗墓也得去长白山。敦煌文化需要大家共同保护，如果有机会希望能亲自去敦煌拍片。”','2013-09-12 14:23:30',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12142322wa0z.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (528,'宋佳封面大片曝光 恬淡清新演绎早秋时尚',NULL,NULL,NULL,NULL,'网易娱乐9月9日报道（图文/小易）近日，小宋佳曝光一组封面大片。照片中著名演员小宋佳恬淡清新，将早秋时尚演绎的淋漓尽致。据悉，由小宋佳主演的《四十九日祭》已完美收官，将于2014年播出。','2013-09-12 14:28:33',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12142830poeb.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (529,'吴秀波大奔头亮相 尽显迷人绅士范儿',NULL,NULL,NULL,NULL,'网易娱乐9月9日报道（图文/小易）近日，吴秀波出席某品牌活动，当晚吴秀波身着一身黑色西装，内搭彩色长丝巾尽显儒雅绅士风度，梳着黑色大奔儿头，整个造性让人眼前一亮。','2013-09-12 14:33:47',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12143342sfmg.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (530,'曾黎复古造型亮相纽约时装周 红唇成亮点',NULL,NULL,NULL,NULL,'网易娱乐9月10日报道(文/小易) 女星曾黎近日受邀赴美出席2014纽约春夏时装周，秀场上以一袭复古抹胸裙成功抢镜，性感红唇更是亮点所在。而秀场上的性感佳人曾黎，近日也被曝私下其实是大大咧咧的性格，反差极大。','2013-09-12 14:57:01',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/121456599tnd.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (531,'关之琳穿超短裙庆生',NULL,NULL,NULL,NULL,'网易娱乐9月8日报道 (图文/CFP) 9月07日，关之琳现身北京出席某慈善晚宴，主办方特意为关之琳庆生。','2013-09-12 15:07:03',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12150701tmkd.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (532,'黄晓明现身俄罗斯中国电影节',NULL,NULL,NULL,NULL,'网易娱乐9月7日报道(图文/CFP) 圣彼得堡当地时间9月4日晚，俄罗斯2013中国电影节在圣彼得堡开幕，开幕式现场星光熠熠，成龙携手章子怡作为推广大使到场助阵，开幕影片《中国合伙人》主创导演陈可、主演黄晓明、佟大为、邓超、杜鹃等也专程赶来出席开幕式。仪式开始前，章子怡与好友佟大为、黄晓明在异国相逢开心合影，勾肩搭背尽显亲密无间。','2013-09-12 15:11:43',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12151139jrv6.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (533,'景甜与范冰冰相谈甚欢',NULL,NULL,NULL,NULL,'2013 Jade Cool Guy全国总决赛在北京举行，景甜作为神秘颁奖嘉宾出席了活动。红毯结束后，景甜同范冰冰一起入座。两人在台下相谈甚欢。','2013-09-12 15:23:00',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/12152257va9g.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (534,'小宋佳时尚大片',NULL,NULL,NULL,NULL,'日前，小宋佳曝光了一组最新时尚大片。','2013-09-12 15:36:56',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/121536505s2h.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (535,'熊乃瑾清新写真',NULL,NULL,NULL,NULL,'日前，熊乃瑾曝光了一组清新写真。','2013-09-12 15:43:13',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/121543059cct.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (536,'章子怡献唱公益节目讲述梦想',NULL,NULL,NULL,NULL,'9月1日，一年一度的大型公益节目「开学第一课」将如约和全国青少年见面。今年，以＂梦想＂为主题的节目上，章子怡作为讲述人，为全国青少年献上＂梦想的坚持＂为主题的第一课，并在节目结尾献唱歌曲「梦想长大了」。中国女航天员王亚平、轮椅上的舞者寥智、钢琴家郎朗、88岁高龄的资深老师潘其华为＂梦想＂讲述他们心中的第一课。','2013-09-12 15:48:01',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201309/121547582f5t.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (539,'中苏外交档案解密 兵戎相见',NULL,NULL,NULL,NULL,'上海纪实频道《档案》作为揭秘性的讲述栏目，利用人类与生俱来的天性--好奇心，而引起观众的追看与共鸣。选题广泛而深刻，包括涉及中外交往和引起国际关注的内容；第二次世界大战以来，国际国内已经解密的高等级军事档案、公安档案、安全档案；与中国相关的近、现代国际关系等内容，通过解密档案中的历史秘密，探寻解读各种历史人物和事件的缘由脉络，告诉观众一个又一个惊人的事件和传奇背后的真实故事','2013-09-13 14:12:47','/u/cms/www/201309/13141246x4my.mp4','FLV',NULL,0,'/u/cms/www/201309/13141220dfer.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (540,'战后10年的伊拉克现在怎么样',NULL,NULL,NULL,NULL,'周一至周五， 每晚21点50分，旅游卫视《行者》， 以个人化脚步，丈量世界地图；以旅行者视角，发现环球风景。','2013-09-13 14:24:03','/u/cms/www/201309/131422491chy.mp4','FLV',NULL,0,'/u/cms/www/201309/131423399l1z.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (541,'女博士于娟和她的抗癌日记',NULL,NULL,NULL,NULL,'《传奇故事》以讲故事为主，每晚为观众讲述一个涉及真善美、德义理的社会故事。它的素材主要来自全国各地传媒所采编的优秀节目。因此，有专家戏称《传奇故事》栏目是全国采编人员最多——有全国上百个栏目的记者在为它制作节目，而同时又是最讲究成本效益的栏目——因为它选的都是全国最好的节目。通过对这些节目的再度整理挖掘加工，形成了现在每晚十点播出的《传奇故事》。\r\n','2013-09-13 14:29:31','/u/cms/www/201309/131422491chy.mp4','FLV',NULL,0,'/u/cms/www/201309/13142927kzr3.jpg',NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (559,'安倍：日自卫队将跟随美军到世界任何一个角落',NULL,NULL,NULL,NULL,NULL,'2013-09-27 10:18:25',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (560,'巴基斯坦7.7级地震震中地区超9成房屋倒塌',NULL,NULL,NULL,NULL,NULL,'2013-09-27 10:18:47',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (561,'凌寒：劝禁食遭打，莫让公共规范变私人恩怨',NULL,NULL,NULL,NULL,NULL,'2013-09-27 10:19:30',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (562,'邓聿文：激活批评和自我批评这个武器',NULL,NULL,NULL,NULL,NULL,'2013-09-27 10:19:51',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (563,'牛新春：要下决心恢复自行车路权',NULL,NULL,NULL,NULL,NULL,'2013-09-27 10:20:09',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (564,'刘迎秋：新自由主义究竟是什么',NULL,NULL,NULL,NULL,NULL,'2013-09-27 10:20:31',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
DROP TABLE IF EXISTS `jc_content_group_view`;
CREATE TABLE `jc_content_group_view` (
  `content_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY  (`content_id`,`group_id`),
  KEY `fk_jc_content_group_v` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容浏览会员组关联表';

DROP TABLE IF EXISTS `jc_content_picture`;
CREATE TABLE `jc_content_picture` (
  `content_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `img_path` varchar(100) NOT NULL COMMENT '图片地址',
  `description` varchar(255) default NULL COMMENT '描述',
  PRIMARY KEY  (`content_id`,`priority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容图片表';

INSERT INTO `jc_content_picture` VALUES (487,0,'/u/cms/www/201309/11174436pnoi.jpg','金马50惊喜连连，金马国际影展也端出超级菜色，继公布蔡明亮新作《郊游》担任开幕片，昨天（9月9日）发布的首波片单，聚焦两位当今最受期待的大师候选人，一位是甫以《蓝色是最温暖的颜色》荣获本届戛纳影展金棕榈奖的法国导演阿布戴柯西胥，一位是以《分居风暴》囊括柏林影展金熊奖与奥斯卡最佳外语片的伊朗导演阿斯哈法哈蒂。金马影展不仅会放映他们最新得奖作品，还把两人所有影片一网打尽，让影迷完整见证他们创作历程，探勘大师如何养成。');
INSERT INTO `jc_content_picture` VALUES (487,1,'/u/cms/www/201309/11174439eb3l.jpg','《海滩的那一天》让法哈蒂首夺柏林影展最佳导演');
INSERT INTO `jc_content_picture` VALUES (487,2,'/u/cms/www/201309/1117444317kg.jpg','阿布戴柯西胥（Abdellatif Kechiche）是有北非血統的法籍导演');
INSERT INTO `jc_content_picture` VALUES (487,3,'/u/cms/www/201309/111744466aux.jpg','阿斯哈法哈蒂（Asghar Farhadi）是把伊朗从单纯写实带向错综复杂的代表性人物');
INSERT INTO `jc_content_picture` VALUES (488,0,'/u/cms/www/201309/111737053v9i.jpg','由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES (488,1,'/u/cms/www/201309/11173708ffx2.jpg','由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES (488,2,'/u/cms/www/201309/11173711toqx.jpg','由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES (488,3,'/u/cms/www/201309/11173719ykxk.jpg','由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES (488,4,'/u/cms/www/201309/11173718do4k.jpg','');
INSERT INTO `jc_content_picture` VALUES (488,5,'/u/cms/www/201309/11173724ul7o.jpg','由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES (489,0,'/u/cms/www/201309/11173244ggfv.jpg','“亚洲舞王”南贤俊演绎魔尸金刚“疯猿”');
INSERT INTO `jc_content_picture` VALUES (489,1,'/u/cms/www/201309/1117323496nx.jpg','《金刚王?死亡救赎》三战之“伤坛”海报');
INSERT INTO `jc_content_picture` VALUES (489,2,'/u/cms/www/201309/11173222qknp.jpg','金刚王释延能神情凝重');
INSERT INTO `jc_content_picture` VALUES (489,3,'/u/cms/www/201309/111732181p5u.jpg','魔尸金刚“疯猿”阴狠毒辣');
INSERT INTO `jc_content_picture` VALUES (489,4,'/u/cms/www/201309/11173213qlec.jpg','释延能、南贤俊“伤坛”对决');
INSERT INTO `jc_content_picture` VALUES (489,5,'/u/cms/www/201309/11173208w8ji.jpg','释延能、南贤俊水下激烈对打');
INSERT INTO `jc_content_picture` VALUES (490,0,'/u/cms/www/201309/11172127ltqx.jpg','集中焚烧染病而死的士兵');
INSERT INTO `jc_content_picture` VALUES (490,1,'/u/cms/www/201309/111721308eyj.jpg','军营病况危急 士兵带面巾制药');
INSERT INTO `jc_content_picture` VALUES (490,2,'/u/cms/www/201309/11172134wikw.jpg','满城瘟疫弥漫 士兵屠宰战马');
INSERT INTO `jc_content_picture` VALUES (490,3,'/u/cms/www/201309/11172137nwy5.jpg','孙传庭检阅出战士兵');
INSERT INTO `jc_content_picture` VALUES (491,0,'/u/cms/www/201309/11171546aux9.jpg','网易娱乐9月11日报道 由海清、张译、芦芳生联袂主演的都市生活轻喜剧《抹布女也有春天》，以火爆网络的“女汉子”都市新女性造型，及热门的抹布女话题新一轮收视正在节节攀升至0.9。该剧接连不断的搞笑故事情节和主演们夸张的漫画式表演，新婚之夜海清和张译二人在床上甜蜜一吻时，海清身下的床板突然塌陷，两人只能相视的尴尬一笑。后续中并上演着激情床戏部份，颠覆帅气女汉子。');
INSERT INTO `jc_content_picture` VALUES (491,1,'/u/cms/www/201309/111715499z6t.jpg','海清张译裸戏搞笑');
INSERT INTO `jc_content_picture` VALUES (491,2,'/u/cms/www/201309/11171553t5kf.jpg','甜蜜爱情温暖相拥');
INSERT INTO `jc_content_picture` VALUES (491,3,'/u/cms/www/201309/11171558kiit.jpg','《抹布女》剧照');
INSERT INTO `jc_content_picture` VALUES (491,4,'/u/cms/www/201309/11171603em6o.jpg','张译上裸爆笑床照');
INSERT INTO `jc_content_picture` VALUES (491,5,'/u/cms/www/201309/11171606wnmy.jpg','夫妻共枕');
INSERT INTO `jc_content_picture` VALUES (492,0,'/u/cms/www/201309/11171054ja61.jpg','张国立紧紧抱住王雅捷');
INSERT INTO `jc_content_picture` VALUES (492,1,'/u/cms/www/201309/11171058td8f.jpg','艾娇娇让人心生爱怜');
INSERT INTO `jc_content_picture` VALUES (492,2,'/u/cms/www/201309/11171102ae78.jpg','王雅捷大闹新居');
INSERT INTO `jc_content_picture` VALUES (492,3,'/u/cms/www/201309/11171106qaui.jpg','王雅捷深陷情感纠葛');
INSERT INTO `jc_content_picture` VALUES (492,4,'/u/cms/www/201309/11171109m2sr.jpg','王雅捷内心痛楚');
INSERT INTO `jc_content_picture` VALUES (494,0,'/u/cms/www/201309/11165640djk4.jpg',' 网易娱乐9月11日报道 由上海好剧影视有限公司、上海宜辰工作室联合出品，何润东、李沁、阚清子、张勋杰联袂主演的大型情感励志剧《璀璨人生》正在湖南卫视金鹰独播剧场热播。剧中，“高富帅”何润东爱上了“灰姑娘”李沁，在童话般的国度——瑞士浪漫谱写“璀璨人生”。  ');
INSERT INTO `jc_content_picture` VALUES (494,1,'/u/cms/www/201309/111656366w4b.jpg','何润东与李沁');
INSERT INTO `jc_content_picture` VALUES (494,2,'/u/cms/www/201309/11165633po01.jpg','何润东携手李沁寻真爱');
INSERT INTO `jc_content_picture` VALUES (494,3,'/u/cms/www/201309/111656292jx9.jpg','何润东恋上李沁');
INSERT INTO `jc_content_picture` VALUES (494,4,'/u/cms/www/201309/11165624319n.jpg','何润东李沁异国浪漫恋爱');
INSERT INTO `jc_content_picture` VALUES (494,5,'/u/cms/www/201309/11165619lkm8.jpg','何润东李沁情定瑞士');
INSERT INTO `jc_content_picture` VALUES (506,0,'/u/cms/www/201309/22100555lytj.jpg','9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES (506,1,'/u/cms/www/201309/22100558gfsb.jpg','9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES (506,2,'/u/cms/www/201309/22100601l1us.jpg','9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES (506,3,'/u/cms/www/201309/22100606t8mw.jpg','9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES (506,4,'/u/cms/www/201309/22100611o2gl.jpg','9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES (510,0,'/u/cms/www/201309/22094752xoxd.jpg','澳门大学横琴新校区围墙外，围栏上间隔一段就悬挂了“严禁翻越澳门大学围墙，违者将予以行政处罚”字样的警示横幅。\r\n ');
INSERT INTO `jc_content_picture` VALUES (510,1,'/u/cms/www/201309/22094906lrj8.jpg','澳门大学横琴新校区图书馆。新华社记者张金加摄');
INSERT INTO `jc_content_picture` VALUES (510,2,'/u/cms/www/201309/22094911xe9x.jpg','2013年7月19日拍摄的横琴岛澳门大学新校区。横琴校区采用澳门法律管治，以围栏和人工河与横琴岛其他区域隔离，并在澳门路氹城和横琴校区之间设置唯一的出入口，透过一条澳门首条的人车两用海底隧道连接两岸，方便澳大教师学生在毋须过关的情况下便可以上班上学。 新华社记者 魏蒙 摄\r\n');
INSERT INTO `jc_content_picture` VALUES (510,3,'/u/cms/www/201309/22094915t8h1.jpg',' 澳门大学横琴新校区大会堂。新华社记者张金加摄');
INSERT INTO `jc_content_picture` VALUES (510,4,'/u/cms/www/201309/22094918gnze.jpg','7月19日，工人们在横琴岛澳门大学新校区清运建筑垃圾。澳门大学校园的水、电、燃气、通讯、警察、消防和邮政等服务均由澳门提供，与澳门本地无异。 新华社记者 魏蒙 摄');
INSERT INTO `jc_content_picture` VALUES (511,0,'/u/cms/www/201309/22093458gynd.jpg','9月9日，毛泽东亲属、身边工作人员来到毛泽东纪念堂。他们是：毛泽东侄女毛小青（右五）、侄外孙女毛雅慧（左二），毛泽东身边工作人员钱嗣杰（右四）、张木齐（左四）、刘学骞（左三）、曾文（右三）。（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES (511,1,'/u/cms/www/201309/22093502mmft.jpg','女儿李敏率子孙向毛泽东敬献的花篮。（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES (511,2,'/u/cms/www/201309/22093506l8pv.jpg','毛泽东身边工作人员在毛主席纪念堂。自左至右为：曾文、周福明、林增升、张木齐、王笃恭。（中红网郑全摄）');
INSERT INTO `jc_content_picture` VALUES (511,3,'/u/cms/www/201309/22093509qm3l.jpg','毛泽东身边工作人员王笃恭（右三）、耿福东（右四）、张木齐（左二）和中国毛体书法艺术研究会常务副会长刘桂芳（左一）在毛主席纪念堂。（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES (511,4,'/u/cms/www/201309/22093513srmf.jpg',' 毛岸英生前妻子刘思齐（左一）与毛泽东侄外孙女毛雅慧在毛主席纪念堂。（中）（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES (513,0,'/u/cms/www/201309/12105715powr.jpg','北影开学第一天');
INSERT INTO `jc_content_picture` VALUES (513,1,'/u/cms/www/201309/12105715u0i2.jpg','北影开学第一天');
INSERT INTO `jc_content_picture` VALUES (513,2,'/u/cms/www/201309/12105716f0ks.jpg','北影开学第一天');
INSERT INTO `jc_content_picture` VALUES (513,3,'/u/cms/www/201309/12105716avso.jpg','北影开学第一天');
INSERT INTO `jc_content_picture` VALUES (513,4,'/u/cms/www/201309/12105716u081.jpg','北影开学第一天');
INSERT INTO `jc_content_picture` VALUES (513,5,'/u/cms/www/201309/12105717rgdv.jpg','北影开学第一天');
INSERT INTO `jc_content_picture` VALUES (514,0,'/u/cms/www/201309/12110240hrzh.jpg','北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES (514,1,'/u/cms/www/201309/12110145r34m.jpg','北影表演系新生K歌竖中指');
INSERT INTO `jc_content_picture` VALUES (514,2,'/u/cms/www/201309/121101457hlo.jpg','北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES (514,3,'/u/cms/www/201309/12110146fexh.jpg','北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES (514,4,'/u/cms/www/201309/12110146ynj3.jpg','北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES (515,0,'/u/cms/www/201309/12110726rqp4.jpg','中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES (515,1,'/u/cms/www/201309/12110726kcxc.jpg','中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES (515,2,'/u/cms/www/201309/1211072762wj.jpg','中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES (515,3,'/u/cms/www/201309/121107270owq.jpg','中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES (515,4,'/u/cms/www/201309/12110728cz8z.jpg','中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES (515,5,'/u/cms/www/201309/12110728hvna.jpg','中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES (516,0,'/u/cms/www/201309/12111106zad3.jpg','1994年8月27日，三峡坝区三斗坪镇东岳庙村10组移民黎开英的儿子望军，以651分的好成绩考入清华大学汽车工程系，该村的乡亲们纷纷来到他家祝贺。摄影/周国强/东方IC');
INSERT INTO `jc_content_picture` VALUES (516,1,'/u/cms/www/201309/12111106zedy.jpg','1977年冬天，中断了十年又重新恢复的高考制度，开始改变这个庞大国家无数人的命运。一纸试卷废除了“推荐上大学”，给当时渴望改变命运的人们一个公平竞争的机会。很多人借此叩开了另一个世界的大门，走上辉煌的人生道路。');
INSERT INTO `jc_content_picture` VALUES (516,2,'/u/cms/www/201309/12111106lobh.jpg','1977年的招生对象为工人、农民、上山下乡和回乡知识青年、复原军人、干部和应届毕业生。');
INSERT INTO `jc_content_picture` VALUES (516,3,'/u/cms/www/201309/12111106jy1l.jpg','1980年，开考前两名女生在交谈。她们的笑容给沉闷的考场带来一丝轻松的气息。摄影/任曙林');
INSERT INTO `jc_content_picture` VALUES (516,4,'/u/cms/www/201309/12111107ctqg.jpg','1980年，高中教师于大卫在考场外鼓励学生。摄影/任曙林');
INSERT INTO `jc_content_picture` VALUES (516,5,'/u/cms/www/201309/12111107l4wg.jpg','1986年，江苏省滨海县只有宋红斌一人通过艺术院校的复试，有参加高考的资格，因此考场里只有他一个人。这张准考证属于宋红斌。');
INSERT INTO `jc_content_picture` VALUES (517,0,'/u/cms/www/201309/12111518vk7v.jpg','针对富人家的孩子，已经有一整套完善的产业链为他们服务，从高中的升学咨询指导到上文提到的大学管家式服务，而且获利不菲，例如帮助学生申请大学的升学指导服务的收费约为28995美元');
INSERT INTO `jc_content_picture` VALUES (517,1,'/u/cms/www/201309/12111518l3p5.jpg','在美国，日趋上涨的大学花费已经严重威胁到了低收入家庭的学生，大量学生即使在毕业四五年后还身背学债。而另一方面，一种迎合富人家庭大学生的服务新模式已经出现。');
INSERT INTO `jc_content_picture` VALUES (517,2,'/u/cms/www/201309/12111518igwr.jpg','216华尔街日报最近就报道了一家新成立的公司“波士顿大学顾问集团');
INSERT INTO `jc_content_picture` VALUES (517,3,'/u/cms/www/201309/12111518kvd2.jpg','几个波士顿地区的学生接受采访时说，他们雇用“波士顿大学顾问集团”来帮他们完成任务，比如说买300瓶MerleNorman的香水并运到沙特阿拉伯，等待水管工，支付超速罚单等等');
INSERT INTO `jc_content_picture` VALUES (517,4,'/u/cms/www/201309/121115195muw.jpg','“波士顿大学顾问集团”的创始人AJRich告诉赫芬顿邮报说，这种类型的管家式服务已经早就出现了');
INSERT INTO `jc_content_picture` VALUES (517,5,'/u/cms/www/201309/12111519tyc2.jpg','他认为他的公司所做的是“赋予学生更多的权利”而不是把他们当婴儿一样纵容，他还说公司的服务事实上很划算：“我们的同行收费可是我们的三倍”。');
INSERT INTO `jc_content_picture` VALUES (518,0,'/u/cms/www/201309/12112124imh5.jpg','9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES (518,1,'/u/cms/www/201309/121121253f1z.jpg','9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES (518,2,'/u/cms/www/201309/121121265tk1.jpg','9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES (518,3,'/u/cms/www/201309/121121267h0z.jpg','9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES (518,4,'/u/cms/www/201309/12112127795n.jpg','9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES (518,5,'/u/cms/www/201309/12112128ltfy.jpg','9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES (519,0,'/u/cms/www/201309/12120435rmvm.jpg','李冰冰');
INSERT INTO `jc_content_picture` VALUES (519,1,'/u/cms/www/201309/12120436on4w.jpg','李冰冰');
INSERT INTO `jc_content_picture` VALUES (519,2,'/u/cms/www/201309/121204384v5d.jpg','李冰冰');
INSERT INTO `jc_content_picture` VALUES (519,3,'/u/cms/www/201309/121204408a3o.jpg','李冰冰');
INSERT INTO `jc_content_picture` VALUES (519,4,'/u/cms/www/201309/1212044200gm.jpg','李冰冰');
INSERT INTO `jc_content_picture` VALUES (520,0,'/u/cms/www/201309/12134158qzrx.jpg','刘德华');
INSERT INTO `jc_content_picture` VALUES (520,1,'/u/cms/www/201309/12134158q84h.jpg','刘德华');
INSERT INTO `jc_content_picture` VALUES (520,2,'/u/cms/www/201309/12134159kbxa.jpg','刘德华');
INSERT INTO `jc_content_picture` VALUES (520,3,'/u/cms/www/201309/12134159robe.jpg','刘德华');
INSERT INTO `jc_content_picture` VALUES (520,4,'/u/cms/www/201309/121341597c2x.jpg','刘德华');
INSERT INTO `jc_content_picture` VALUES (520,5,'/u/cms/www/201309/12134200cwyl.jpg','刘德华');
INSERT INTO `jc_content_picture` VALUES (521,0,'/u/cms/www/201309/12134450mwvm.jpg','全智贤');
INSERT INTO `jc_content_picture` VALUES (521,1,'/u/cms/www/201309/12134450lo6n.jpg','全智贤');
INSERT INTO `jc_content_picture` VALUES (521,2,'/u/cms/www/201309/12134451j9p9.jpg','全智贤');
INSERT INTO `jc_content_picture` VALUES (521,3,'/u/cms/www/201309/12134451k1zj.jpg','全智贤');
INSERT INTO `jc_content_picture` VALUES (521,4,'/u/cms/www/201309/12134452b2g1.jpg','全智贤');
INSERT INTO `jc_content_picture` VALUES (521,5,'/u/cms/www/201309/12134452bb5z.jpg','全智贤');
INSERT INTO `jc_content_picture` VALUES (522,0,'/u/cms/www/201309/121348109k92.jpg','王智');
INSERT INTO `jc_content_picture` VALUES (522,1,'/u/cms/www/201309/121348103ux5.jpg','王智');
INSERT INTO `jc_content_picture` VALUES (522,2,'/u/cms/www/201309/12134811y9du.jpg','王智');
INSERT INTO `jc_content_picture` VALUES (522,3,'/u/cms/www/201309/121348110tfi.jpg','王智');
INSERT INTO `jc_content_picture` VALUES (522,4,'/u/cms/www/201309/12134812unit.jpg','王智');
INSERT INTO `jc_content_picture` VALUES (523,0,'/u/cms/www/201309/12135148im9s.jpg','宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES (523,1,'/u/cms/www/201309/12135149bhlg.jpg','宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES (523,2,'/u/cms/www/201309/12135149s2wm.jpg','宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES (523,3,'/u/cms/www/201309/12135149ar88.jpg','宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES (524,0,'/u/cms/www/201309/12135606e58j.jpg','杨幂');
INSERT INTO `jc_content_picture` VALUES (524,1,'/u/cms/www/201309/12135607vqhp.jpg','杨幂');
INSERT INTO `jc_content_picture` VALUES (524,2,'/u/cms/www/201309/12135607ftvv.jpg','杨幂');
INSERT INTO `jc_content_picture` VALUES (524,3,'/u/cms/www/201309/12135608l3ai.jpg','杨幂');
INSERT INTO `jc_content_picture` VALUES (524,4,'/u/cms/www/201309/12135608ad8p.jpg','杨幂');
INSERT INTO `jc_content_picture` VALUES (524,5,'/u/cms/www/201309/12135609wexl.jpg','杨幂');
INSERT INTO `jc_content_picture` VALUES (525,0,'/u/cms/www/201309/12140434n4th.jpg','');
INSERT INTO `jc_content_picture` VALUES (525,1,'/u/cms/www/201309/12140434ixtu.jpg','');
INSERT INTO `jc_content_picture` VALUES (525,2,'/u/cms/www/201309/12140435relm.jpg','');
INSERT INTO `jc_content_picture` VALUES (525,3,'/u/cms/www/201309/12140436rua8.jpg','');
INSERT INTO `jc_content_picture` VALUES (525,4,'/u/cms/www/201309/12140436wwsc.jpg','');
INSERT INTO `jc_content_picture` VALUES (526,0,'/u/cms/www/201309/12141311nuua.jpg','张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES (526,1,'/u/cms/www/201309/12141311xxbj.jpg','张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES (526,2,'/u/cms/www/201309/12141312d3p6.jpg','张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES (526,3,'/u/cms/www/201309/12141312r6eb.jpg','张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES (526,4,'/u/cms/www/201309/1214131399h0.jpg','张智霖签新公司获直升机接送');
INSERT INTO `jc_content_picture` VALUES (526,5,'/u/cms/www/201309/12141313ndlq.jpg','张智霖签新公司获直升机接送');
INSERT INTO `jc_content_picture` VALUES (527,0,'/u/cms/www/201309/12142118fe7p.jpg','何晟铭出席敦煌保护活动');
INSERT INTO `jc_content_picture` VALUES (527,1,'/u/cms/www/201309/12142119m8y5.jpg','何晟铭出席敦煌保护活动');
INSERT INTO `jc_content_picture` VALUES (527,2,'/u/cms/www/201309/121421197sla.jpg','何晟铭演唱');
INSERT INTO `jc_content_picture` VALUES (527,3,'/u/cms/www/201309/12142120u2tx.jpg','何晟铭演唱');
INSERT INTO `jc_content_picture` VALUES (527,4,'/u/cms/www/201309/12142120loqu.jpg','何晟铭');
INSERT INTO `jc_content_picture` VALUES (527,5,'/u/cms/www/201309/1214212172bk.jpg','何晟铭、韩栋');
INSERT INTO `jc_content_picture` VALUES (528,0,'/u/cms/www/201309/12142734jwmh.jpg','宋佳');
INSERT INTO `jc_content_picture` VALUES (528,1,'/u/cms/www/201309/12142735jqmu.jpg','宋佳');
INSERT INTO `jc_content_picture` VALUES (528,2,'/u/cms/www/201309/12142735rhib.jpg','宋佳');
INSERT INTO `jc_content_picture` VALUES (528,3,'/u/cms/www/201309/1214273617d5.jpg','宋佳');
INSERT INTO `jc_content_picture` VALUES (528,4,'/u/cms/www/201309/12142736ue6c.jpg','宋佳');
INSERT INTO `jc_content_picture` VALUES (528,5,'/u/cms/www/201309/12142737cr9y.jpg','宋佳');
INSERT INTO `jc_content_picture` VALUES (529,0,'/u/cms/www/201309/1214325480ue.jpg','吴秀波');
INSERT INTO `jc_content_picture` VALUES (529,1,'/u/cms/www/201309/12143254y1wh.jpg','吴秀波');
INSERT INTO `jc_content_picture` VALUES (529,2,'/u/cms/www/201309/12143255ukxu.jpg','吴秀波');
INSERT INTO `jc_content_picture` VALUES (529,3,'/u/cms/www/201309/121432556ttu.jpg','吴秀波');
INSERT INTO `jc_content_picture` VALUES (529,4,'/u/cms/www/201309/12143255ryzh.jpg','吴秀波');
INSERT INTO `jc_content_picture` VALUES (529,5,'/u/cms/www/201309/12143256g1bo.jpg','吴秀波');
INSERT INTO `jc_content_picture` VALUES (530,0,'/u/cms/www/201309/12145434qkiz.jpg','曾黎');
INSERT INTO `jc_content_picture` VALUES (530,1,'/u/cms/www/201309/12145434959z.jpg','曾黎');
INSERT INTO `jc_content_picture` VALUES (530,2,'/u/cms/www/201309/121454345gdz.jpg','曾黎');
INSERT INTO `jc_content_picture` VALUES (530,3,'/u/cms/www/201309/12145435lmi6.jpg','曾黎');
INSERT INTO `jc_content_picture` VALUES (530,4,'/u/cms/www/201309/12145435u9c1.jpg','曾黎');
INSERT INTO `jc_content_picture` VALUES (531,0,'/u/cms/www/201309/12150556da16.jpg','关之琳');
INSERT INTO `jc_content_picture` VALUES (531,1,'/u/cms/www/201309/12150556inf7.jpg','关之琳');
INSERT INTO `jc_content_picture` VALUES (531,2,'/u/cms/www/201309/12150557etcp.jpg','关之琳');
INSERT INTO `jc_content_picture` VALUES (531,3,'/u/cms/www/201309/12150557dzrz.jpg','关之琳');
INSERT INTO `jc_content_picture` VALUES (532,0,'/u/cms/www/201309/12150936264i.jpg','《中国合伙人》剧组');
INSERT INTO `jc_content_picture` VALUES (532,1,'/u/cms/www/201309/121509368460.jpg','黄晓明章子怡佟大为');
INSERT INTO `jc_content_picture` VALUES (532,2,'/u/cms/www/201309/12150936cnpa.jpg','《中国合伙人》剧组');
INSERT INTO `jc_content_picture` VALUES (532,3,'/u/cms/www/201309/12150937xe3g.jpg','佟大为黄晓明邓超');
INSERT INTO `jc_content_picture` VALUES (532,4,'/u/cms/www/201309/121509371kyv.jpg','《中国合伙人》剧组');
INSERT INTO `jc_content_picture` VALUES (533,0,'/u/cms/www/201309/12152115n7ee.jpg','景甜亮相红毯');
INSERT INTO `jc_content_picture` VALUES (533,1,'/u/cms/www/201309/12152115dymp.jpg','景甜与范冰冰在台下');
INSERT INTO `jc_content_picture` VALUES (533,2,'/u/cms/www/201309/1215211539ic.jpg','景甜与范冰冰在台下');
INSERT INTO `jc_content_picture` VALUES (533,3,'/u/cms/www/201309/12152115oqbj.jpg','景甜与范冰冰在台下');
INSERT INTO `jc_content_picture` VALUES (533,4,'/u/cms/www/201309/12152116am9n.jpg','景甜作为颁奖嘉宾登台');
INSERT INTO `jc_content_picture` VALUES (534,0,'/u/cms/www/201309/12153550461x.jpg','小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES (534,1,'/u/cms/www/201309/12153550ryso.jpg','小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES (534,2,'/u/cms/www/201309/12153551r5kz.jpg','小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES (534,3,'/u/cms/www/201309/12153551igy5.jpg','小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES (534,4,'/u/cms/www/201309/12153551fflz.jpg','小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES (534,5,'/u/cms/www/201309/121535526foq.jpg','小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES (535,0,'/u/cms/www/201309/121542148jkc.jpg','熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES (535,1,'/u/cms/www/201309/121542159xil.jpg','熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES (535,2,'/u/cms/www/201309/12154215ryyk.jpg','熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES (535,3,'/u/cms/www/201309/12154215y02n.jpg','熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES (535,4,'/u/cms/www/201309/121542158t1n.jpg','熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES (535,5,'/u/cms/www/201309/12154216egtt.jpg','熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES (536,0,'/u/cms/www/201309/12154708we6w.jpg','章子怡');
INSERT INTO `jc_content_picture` VALUES (536,1,'/u/cms/www/201309/12154708kkn6.jpg','章子怡');
INSERT INTO `jc_content_picture` VALUES (536,2,'/u/cms/www/201309/12154708nsye.jpg','章子怡');
INSERT INTO `jc_content_picture` VALUES (536,3,'/u/cms/www/201309/12154709iod3.jpg','章子怡');
INSERT INTO `jc_content_picture` VALUES (536,4,'/u/cms/www/201309/12154709ypkl.jpg','章子怡');
DROP TABLE IF EXISTS `jc_content_tag`;
CREATE TABLE `jc_content_tag` (
  `tag_id` int(11) NOT NULL auto_increment,
  `tag_name` varchar(50) NOT NULL COMMENT 'tag名称',
  `ref_counter` int(11) NOT NULL default '1' COMMENT '被引用的次数',
  PRIMARY KEY  (`tag_id`),
  UNIQUE KEY `ak_tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容TAG表';

DROP TABLE IF EXISTS `jc_content_topic`;
CREATE TABLE `jc_content_topic` (
  `content_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  PRIMARY KEY  (`content_id`,`topic_id`),
  KEY `fk_jc_content_topic` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS专题内容关联表';

INSERT INTO `jc_content_topic` VALUES (334,5);
INSERT INTO `jc_content_topic` VALUES (508,6);
INSERT INTO `jc_content_topic` VALUES (511,6);
DROP TABLE IF EXISTS `jc_content_txt`;
CREATE TABLE `jc_content_txt` (
  `content_id` int(11) NOT NULL,
  `txt` longtext COMMENT '文章内容',
  `txt1` longtext COMMENT '扩展内容1',
  `txt2` longtext COMMENT '扩展内容2',
  `txt3` longtext COMMENT '扩展内容3',
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容文本表';

INSERT INTO `jc_content_txt` VALUES (172,'<p>【基本介绍】<br />\r\n\t无需付费购买，到官方指定下载地址下载安装后即可以使用<br />\r\n\t具有灵活点播的功能，随点随看，时间自由掌握<br />\r\n\t操作简单，界面简洁明了<br />\r\n\t掌握最先进的P2SP传输技术，传输速度更快，更节省带宽<br />\r\n\t占用CPU、内存、带宽等系统资源占用少，不影响其它操作。<br />\r\n\t支持多种视频文件格式<br />\r\n\t播放流畅，首次连接速度快<br />\r\n\t采用最新解码技术，使网络观看拖动无延时，达到播放本地文件的效果<br />\r\n\t最高清画面，支持网络高清点播，最高码率达到1300。<br />\r\n\t采用脉冲式连接和自动重连策略，保证用户稳定、流畅的观看。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (173,'<p>【基本介绍】<br />\r\n\t千千静听是一款完全免费的音乐播放软件，拥有自主研发的全新音频引擎，集播放、音效、转换、歌词等众多功能于一身。其小巧精致、操作简捷、功能强大的特点，深得用户喜爱，被网友评为中国十大优秀软件之一，并且成为目前最受欢迎的音乐播放软件。<br />\r\n\t<br />\r\n\t【软件特色】<br />\r\n\t拥有自主研发的全新音频引擎，支持DirectSound、Kernel Streaming和ASIO等高级音频流输出方式、64比特混音、AddIn插件扩展技术，具有资源占用低、运行效率高，扩展能力强等特点。<br />\r\n\t<br />\r\n\t千千静听支持几乎所有常见的音频格式，包括MP/mp3PRO、AAC/AAC+、M4A/MP4、WMA、APE、MPC、OGG、WAVE、CD、 FLAC、RM、TTA、AIFF、AU等音频格式以及多种MOD和MIDI音乐，以及AVI、VCD、DVD等多种视频文件中的音频流，还支持CUE音轨索引文件。<br />\r\n\t<br />\r\n\t通过简单便捷的操作，可以在多种音频格式之间进行轻松转换，包括上述所有格式（以及CD或DVD中的音频流）到WAVE、MP3、APE、WMA等格式的转换；通过基于COM接口的AddIn插件或第三方提供的命令行编码器还能支持更多格式的播放</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (329,'<p>暮光之城</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (330,'<p>神魔大陆</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (331,'<p>万众期待，翘首以盼的QQ2011 正式版现在全员发布啦！13大全新强大功能挑战你的娱乐精神，重量级功能玩法让你乐翻天！速来体验手写、语音输入，多人视频，语音消息留言、好友桌面等酷炫新功能带来的神奇！</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (334,'<p class=\"detailPic\"><img alt=\"\" height=\"500\" src=\"http://y2.ifengimg.com/a/2014_19/a0a4fd008ceab2c.jpg\" width=\"344\" /></p>\r\n<p class=\"picIntro\"><span>李克强夫人程虹（据新国际，即新华社国际新闻即时播报微博）</span></p>\r\n<p>中国国务院总理<a href=\"http://renwuku.news.ifeng.com/index/detail/2/likeqiang\" target=\"_blank\">李克强</a>4号至11号，将访问埃塞俄比亚、尼日利亚、安哥拉、肯尼亚非洲四国，并访问非盟总部以及参加第24届世界经济论坛非洲峰会全会。访问期间，中非双方将签署近60份合作文件。这次也是李克强首次以总理身份访问非洲。来看本台记者发自埃塞俄比亚的现场报道。&nbsp;</p>\r\n<p>陈琳：中国总理李克强的专机会在埃塞俄比亚当地时间4号下午抵达首都亚的斯亚贝巴，开始他这次非洲之行的第一站。而在这次<strong>李克强将偕夫人程虹共同外访，这也是他就任中国总理之后第一次偕夫人外访。</strong>在 埃塞俄比亚期间，李克强将与当地进行双边交流，包括与总统、总理会谈，并且与总理见证两国签署多个合作文本，他还将特别到访非盟设在亚的斯亚贝巴的总部， 并在那里进行一场演讲，阐述中国对非洲合作的意愿和努力。在埃塞俄比亚期间，李克强还会参观由多个中资来承建和援建的纪念项目以及与中资机构座谈。在6号 他就会离开当地，转往下一站尼日利亚，之后还会先后访问安哥拉和肯尼亚。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (335,'<dl>\r\n\t<dd>\r\n\t\t新增网页翻译功能，在翻译框内输入网址点击翻译，即可得到翻译后的该网址页面。</dd>\r\n\t<dd>\r\n\t\t具有多国语言发音功能，日韩法语全部标准朗读。</dd>\r\n\t<dd>\r\n\t\t轻松囊括互联网最新流行的词汇。</dd>\r\n\t<dd>\r\n\t\t中英日韩法五国语言轻松查询。</dd>\r\n\t<dd>\r\n\t\t通过网络查询最新翻译，无限容量词库，翻译永不过时。</dd>\r\n</dl>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (336,'<p>迅雷使用先进的超线程技术基于网格原理，能够将存在于第三方服务器和计算机上的数据文件进行有效整合，通过这种先进的超线程技术，用户能够以更快的速度从 第三方服务器和计算机获取所需的数据文件。这种超线程技术还具有互联网下载负载均衡功能，在不降低用户体验的前提下，迅雷网络可以对服务器资源进行均衡， 有效降低了服务器负载。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (337,'<h2 class=\"leftboxfutitle\">\r\n\tYY4.0是多玩YY语音的全新版本，活动中心盛大起航，汇集YY上最好最优质的频道和活动，提供YY上最有价值的内容，不需再为找好 频道而费尽心机。</h2>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (436,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- 功能注释标记：插入视频列表模块begin --></div>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>\r\n\t<link href=\"http://ent.sina.com.cn/css/470/20120928/style_videolist.css\" rel=\"stylesheet\" />\r\n</p>\r\n<div class=\"artical-player-wrap\" style=\"display: block\">\r\n\t<div class=\"a-p-hd\">\r\n\t\t<div id=\"J_Article_Player\">视频加载中，请稍候...</div>\r\n\t\t<div class=\"a-p-info\"><label class=\"fl\" style=\"display: none\" suda-uatrack=\"key=videoq&amp;value=autoplay\"><input checked=\"checked\" id=\"J_Video_Autoplay\" type=\"checkbox\" /> 自动播放</label><span id=\"J_Video_Source\">&nbsp;</span></div>\r\n\t</div>\r\n\t<div class=\"a-p-bd a-p-bd-b\" id=\"J_Play_List_Wrap\" style=\"display: none\">\r\n\t\t<div class=\"a-p-slide\">\r\n\t\t\t<div class=\"a-p-s-list clearfix\" id=\"J_Play_List\">\r\n\t\t\t\t<div class=\"a-p-s-item J_Play_Item\" play-data=\"109132441-1-0\" source-data=\"广东卫视《广东早晨》\" title-data=\"内地夫妇香港机场袭警\" url-data=\"http://video.sina.com.cn/p/news/c/v/2013-07-08/081462644129.html\"><a class=\"a-p-s-img\" href=\"javascript:\" title=\"内地夫妇香港机场袭警\"><img alt=\"内地夫妇香港机场袭警\" height=\"90\" src=\"/jeecmsv5/u/cms/www/201307/08153408gpvd.jpg\" width=\"120\" /> <i class=\"a-p-s-play\">play</i> <span class=\"a-p-s-txt\">内地夫妇香港机场袭警</span> </a></div>\r\n\t\t\t</div>\r\n\t\t\t<a class=\"a-p-s-prev\" href=\"javascript:;\" id=\"J_Player_Prev\">向前</a> <a class=\"a-p-s-next\" href=\"javascript:;\" id=\"J_Player_Next\">向后</a></div>\r\n\t</div>\r\n\t<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/ty/sinaui/scrollpic/scrollpic2012070701.min.js\"></script><script type=\"text/javascript\" src=\"http://ent.sina.com.cn/js/470/20120928/videolist.js\"></script><script type=\"text/javascript\">\r\n                                        /*自动播放1*/\r\n                                        var AUTOPLAY = 1;\r\n                                        /*连播1*/\r\n                                        var CONTIPLAY = 1;\r\n                                       /*处理自动播放选项和cookie*/\r\n                                        (function(){\r\n                                            var Tool = CommonTool;\r\n                                            var chk = Tool.byId(\'J_Video_Autoplay\');\r\n                                            var ua = navigator.userAgent.toLowerCase();\r\n                                            var isIOS = /\\((iPhone|iPad|iPod)/i.test(ua);\r\n                                            if(isIOS){\r\n                                              console.log(chk.parentNode.style.display);\r\n                                              chk.parentNode.style.display = \'none\';\r\n                                              return;\r\n                                            }\r\n                                            chk.parentNode.style.display = \'\';\r\n                                            var clickCookie=function(){\r\n                                                Tool.bindEvent(chk,\'change\',function(){\r\n                                                    var chked = chk.checked;\r\n                                                    Tool.writeCookie(\'ArtiVAuto\',(chked?1:0),24*365*10,\'/\',\'.sina.com.cn\');\r\n                                                });\r\n                                            }\r\n                                            var byCookie=function(){\r\n                                                var coo = Tool.readCookie(\'ArtiVAuto\');\r\n                                                if(coo){\r\n                                                    if(parseInt(coo)==0){\r\n                                                        chk.checked = false;\r\n                                                        AUTOPLAY = 0;\r\n                                                    }\r\n                                                }\r\n                                            };\r\n                                            clickCookie();\r\n                                            byCookie();\r\n                                        })();\r\n\r\n                                        /*获取第一个视频vid*/\r\n                                        var firstItem = CommonTool.byClass(\'J_Play_Item\',\'J_Play_List\')[0];\r\n                                        var fInfo = firstItem.getAttribute(\'play-data\').split(\'-\');\r\n                                        var fVid = fInfo[0];\r\n                                        var fPid = fInfo[1];\r\n\r\n                                        var sinaBokePlayerConfig_o = {\r\n                                          container: \"J_Article_Player\",  //Div容器的id\r\n\t\t\t\t\t\t\t\t\t\t  width:525,\r\n\t\t\t\t\t\t\t\t\t\t  height:430,\r\n                                          playerWidth: 525,   //宽\r\n                                          playerHeight: 430,    //高\r\n                                          autoLoad: 1,  //自动加载\r\n                                          autoPlay: AUTOPLAY, //自动播放\r\n                                          as: 1,  //广告\r\n                                          pid: fPid,\r\n                                          tjAD: 0,  //显示擎天柱广告\r\n                                          tj: 1,  //片尾推荐\r\n                                          continuePlayer : 1, //连续播放\r\n                                          casualPlay: 1,  //任意拖动视频\r\n                                          head: 0,  //播放片头动画\r\n                                          logo: 0,  //显示logo\r\n                                          share :0,\r\n                                          thumbUrl: \"http://p.v.iask.com/95/343/109132441_2.jpg\"\r\n                                        };\r\n                                        window.__onloadFun__ = function(){\r\n                                          SinaBokePlayer_o.addVars(\'HTML5Player_controlBar\', true);\r\n                                          SinaBokePlayer_o.addVars(\'HTML5Player_autoChangeBGColor\', false);\r\n                                          //SinaBokePlayer_o.addVars(\"vid\", fVid);\r\n                                          //SinaBokePlayer_o.addVars(\"pid\", fPid);\r\n                                          SinaBokePlayer_o.showFlashPlayer();\r\n\r\n                                        };\r\n                                      </script><script src=\"http://video.sina.com.cn/js/sinaFlashLoad.js\" charset=\"utf-8\" type=\"text/javascript\"></script><script type=\"text/javascript\">\r\n                                        (function(){\r\n                                          var toggle = function(id,hide){\r\n                                            var e = CommonTool.byId(id);\r\n                                            var par = e.parentNode;\r\n                                            if(hide){\r\n                                              CommonTool.addClass(par,e.className+\'_disabled\');\r\n                                            }else{\r\n                                              CommonTool.removeClass(par,e.className+\'_disabled\');\r\n                                            }\r\n                                          }\r\n                                          var scroll = new ScrollPic();\r\n                                          scroll.scrollContId   = \"J_Play_List\"; //内容容器ID\r\n                                          scroll.arrLeftId      = \"J_Player_Prev\";//左箭头ID\r\n                                          scroll.arrRightId     = \"J_Player_Next\"; //右箭头ID\r\n                                          scroll.listEvent      = \"onclick\"; //切换事件\r\n                                          scroll.frameWidth     = 532;//显示框宽度 **显示框宽度必须是翻页宽度的倍数\r\n\r\n                                          scroll.pageWidth      = 133*3; //翻页宽度\r\n                                          scroll.upright        = false; //垂直滚动\r\n                                          scroll.speed          = 10; //移动速度(单位毫秒，越小越快)\r\n                                          scroll.space          = 15; //每次移动像素(单位px，越大越快)\r\n                                          scroll.autoPlay       = false; //自动播放\r\n                                          scroll.autoPlayTime   = 5; //自动播放间隔时间(秒)\r\n                                          scroll.circularly     = false;\r\n                                          scroll._move = scroll.move;\r\n                                          scroll.move = function(num,quick){\r\n                                                scroll._move(num,quick);\r\n                                                toggle(scroll.arrRightId,scroll.eof);\r\n                                                toggle(scroll.arrLeftId,scroll.bof);\r\n                                          };\r\n                                          scroll.initialize(); //初始化\r\n                                          toggle(scroll.arrLeftId,scroll.bof);\r\n                                        })();\r\n                                      </script><script type=\"text/javascript\">\r\n                                      var VideoList1 = new ArticalVideoList(\'J_Play_List\',{\r\n                                        index : 0,\r\n                                        autoPlay : AUTOPLAY,\r\n                                        contiPlay : CONTIPLAY,\r\n                                        itemClass : \'J_Play_Item\'\r\n                                      });\r\n                                      VideoList1.init();\r\n                                      function playCompleted(tag){\r\n                                        VideoList1.playNext();                                          \r\n                                      };\r\n                                      </script></div>\r\n<!-- 功能注释标记：插入视频列表模块end --><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603391\' f_id=\'3\' --><!-- video_play_list_html <table id=\"video_play_list\" border=\"1\" cellspacing=\"0px\" cellpadding=\"1px\">\r\n<tbody>\r\n\t<tr>\r\n\t<td>标题</td>\r\n\t<td>vid</td>\r\n\t<td>是否打开广告</td>\r\n\t<td>媒体来源</td>\r\n\t<td>视频截图</td>\r\n\t<td>是否联播</td>\r\n\t<td>地址</td>\r\n</tr>\r\n\t<tr class=\"video_tr\">\r\n\t<td>内地夫妇香港机场袭警</td>\r\n\t<td>109132441</td>\r\n\t<td>0</td>\r\n\t<td>广东卫视《广东早晨》</td>\r\n\t<td title=\"/jeecmsv5/u/cms/www/201307/08153408gpvd.jpg\"></td>\r\n\t<td>1</td>\r\n\t<td>http://video.sina.com.cn/p/news/c/v/2013-07-08/081462644129.html</td>\r\n</tr>\r\n</tbody>\r\n</table> video_play_list_html_end --><!-- video_play_list_data 内地夫妇香港机场袭警|109132441|0|广东卫视《广东早晨》||1|http://video.sina.com.cn/p/news/c/v/2013-07-08/081462644129.html<br> video_play_list_data_end -->\r\n<p>　　南都讯 记者石秋菊 发自香港 航班延误，乘客鼓噪，闹事打砸，这一幕发生在7月5日的香港机场。一对内地夫妇的男事主砸国泰航空的柜台更泼汽水，被香港警方拘捕时，妻子更涉嫌推倒一名警员，最后夫妇两人都被警方带走调查。</p>\r\n<p>　　香港警方表示，涉案夫妇中丈夫姓徐，称任职图书管理员，35岁，妻子姓万，34岁，两人来自江苏，在香港旅行后，原定于乘搭7月5日下午5点35分，香港国泰的CX 364航班，从香港返回上海浦东。</p>\r\n<p>　　据了解，当日下午3点多，夫妻两人到达机场国泰柜台办理好登记手续。随后由于上海浦东发出航空交通流量管制，航班延误，这个航班未能如期出发，航班上一共238名旅客之后滞留在候机大堂。</p>\r\n<p>　　航班延误至当天晚上9点多，徐在向国泰柜位职员查询航班最新情况时，与职员发生了争执，随后情绪激动，用手扫翻柜台上的电脑键盘，一名24岁的郑姓女地勤人员上前制止时，再被他用汽水淋泼，弄污衣服，旁边的32岁周姓男性地勤职员也被汽水泼中，之后两人报警。</p>\r\n<p>　　警员接到报案到场调查后，以涉嫌刑事毁坏及普通伤人将徐拘捕。徐的妻子见到丈夫被捕，也激动地意欲阻止警员，混乱间一名警员被人推倒在地上，之后万妇被其他警员制服，也被以涉嫌袭警罪拘捕。</p>\r\n<p>　　之后国泰发言人回应媒体，有关航班一共延误了16个小时，在7月6日早上9点起飞，航班延误期间，已经安排受影响乘客入住酒店及提供膳食，有关伤人事件也已经交由警方处理。</p>\r\n<p align=\"right\">（稿件来源于南方都市报 原标题：航班延误闹香港机场内地夫妇袭警被拘捕）</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (437,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603466\' f_id=\'3\' -->\r\n\t<p>　　晨报讯(记者 颜斐) 王某嫖娼后骗卖淫女喝下他放入安眠药的饮料，然后待对方熟睡后实施抢劫。近日朝阳区检察院以涉嫌抢劫罪对抢劫卖淫女的王某批准逮捕。</p>\r\n\t<p>&nbsp;&nbsp;&nbsp; 今年5月，王某通过微信与张某取得联系，并在微信上谈妥了卖淫嫖娼的价钱。6月2日晚，王某在支付了嫖资后与张某发生了性关系。之后，王某将随身携带的强效安眠药放入饮料中并让张某喝下，趁对方熟睡之机抢走她5000元。张某后报警，王某即被警方抓获。王某还交代，他在今年2月10日还以同样的方法抢走一名卖淫女6000元。</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (438,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603695\' f_id=\'3\' -->\r\n\t<p>　　晨报讯(记者 李庭煊) 尹某谎称自己是中影数字基地的执行导演，以安排彭某做特邀演员为诱饵，骗取其1万元。近日怀柔法院以诈骗罪判处尹某拘役4个月，缓刑6个月。</p>\r\n\t<p>　　去年3月，尹某在怀柔区杨宋镇安乐庄村租了一个群众演员大院，招募群众演员。9月彭某前来应聘，尹某谎称自己是影视基地的执行导演，在收取彭某1万元后称可帮彭某做特邀演员。随后尹某安排了10场群众演员的戏给彭某。因自己总没台词，发现自己不是特邀演员后，彭某要求尹某退还她1万元遭绝。为此彭某报警，面对警方的询问，尹某承认了自己的诈骗行为。</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (439,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603778\' f_id=\'3\' -->\r\n\t<p>　　扬子晚报讯 (记者 郭一鹏 通讯员 张晓冬) 穿着拖鞋开车，还在行驶中低头点烟，结果抬头时突然发现前面有辆电动车，司机赶紧踩了一脚刹车，哪知拖鞋打滑，车辆没有控制住直接撞了上去。电动车上两名老人倒地后，其中一人因伤势过重不幸死亡。前不久，南京大厂发生了这样一幕惨剧。</p>\r\n\t<p>　　6月底一天清晨5点半，交警十大队接到报警称，有辆轿车追尾电动自行车，撞死了一名老人。司机会不会是酒后驾车？交警对肇事车司机汤某进行酒精测试，结果发现他并未饮酒。随后，交警又对事故原因进行询问，汤某指着自己脚上的拖鞋和一根掉在驾驶座附近的香烟，懊恼地说：&ldquo;都怪我点烟时没在意车子跑偏方向，发现情况后又没踩住刹车。&rdquo;原来，汤某行车至毕洼路时，有些犯困，便想抽烟提神，他一手扶方向盘，另一只手拿着打火机，低头点烟。就在刚点上火时，汤某觉得车子有点跑偏，他抬头一看，发现车子竟然驶进了非机动车道，右前方还有一辆电动自行车。情急之下，汤某赶紧用力踩下刹车，谁知拖鞋突然打滑，竟然没踩住刹车，他再次踩下刹车时，自己的车已经把电动自行车撞翻了。据了解，该事故汤某将承担全部事故责任。</p>\r\n\t<p align=\"right\">(原标题：穿拖鞋开车还低头点烟 轿车失控追尾致人死亡)</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (440,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27604006\' f_id=\'3\' -->\r\n\t<p>　　晨报讯&nbsp;(记者 颜斐) 发现妻子有了情人郭某，徐某以讨债为名，伙同两个朋友将郭某拘禁3天，其间不仅实施殴打和侮辱行为，还向郭某索要3000万元补偿费。近日，徐某因涉嫌非法拘禁罪和敲诈勒索罪被公诉到朝阳法院，两同伙也因非法拘禁罪被公诉。</p>\r\n\t<p>　　据指控，徐某发现妻子与郭某存在不正当关系，遂谎称与郭某存在债务纠纷，找来禹某和孙某帮忙讨债。徐某与禹某事先在朝阳区租了一房屋。今年2月16日晚，徐某三人在一酒店房间内将郭某装入箱子，并带至承租房间内，郭某被拘禁直至2月19日下午6点。在此期间，徐某殴打、侮辱郭某，并录音录像，后向他索要补偿费。郭某在承诺给付3000万元后被送回酒店。后徐某、禹某、孙某被抓获归案。公安机关随案查处钳子、擀面杖、剪刀等工具。</p>\r\n\t<p>　　公诉机关认为，徐某非法拘禁他人，且有殴打、侮辱情节，同时在此过程中向被害人勒索钱财，数额特别巨大，其行为应当以非法拘禁罪、敲诈勒索罪追究刑事责任。考虑到敲诈勒索罪系未遂，建议可以比照既遂犯从轻或减轻处罚。禹某、孙某非法限制他人人身自由，应当以非法拘禁罪追究刑事责任。</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (441,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27604829\' f_id=\'3\' -->\r\n\t<p>　　本报记者赵志锋</p>\r\n\t<p>　　中国区域特色经济研究院，听起来多么唬人的机构。如果不是法院在审理民事案件中查清它的真实面目，不知道这个国字头机构还要存在多久。</p>\r\n\t<p>　　在法院司法建议的推动下，今年5月，这个活动在甘肃省的中国区域特色经济研究院西部中心(以下简称西部中心)被注销。此时，它已存在长达8年之久了。在这8年中，不仅兰州市招商局为它办理了外地驻兰办事机构登记证，陇南市招商局还让其承接了水电项目。</p>\r\n\t<p>　　而在事过境迁之后，人们发现，西部中心得以注册并从事商业活动的手段一点儿都不高明。</p>\r\n\t<p>　　那么，是什么让一个虚构的机构得以从容地通过招商局、发改委两家政府部门的审查呢？</p>\r\n\t<p>　<strong>　号称经党中央国务院批准</strong></p>\r\n\t<p>　　兰州商人丁某与中国区域特色经济研究院西部中心签订了水电项目&ldquo;内部股权转让合同&rdquo;和&ldquo;联合开发协议&rdquo;，并依约支付了部分定金和转让款。</p>\r\n\t<p>　　后来，丁某发现，西部中心并未取得水电项目的立项及批复，遂提出退还定金及转让款的请求，未果。</p>\r\n\t<p>　　于是，丁某任总经理的甘肃某商贸公司将中国区域特色经济研究院西部中心、中心主任王某、中国区域特色经济研究院起诉到法院，要求判令三被告双倍返还定金及转让款。</p>\r\n\t<p>　　这起诉讼无意间揭开了一个惊人的秘密：法院发现，在甘肃陇南承接水电项目的中国区域特色经济研究院西部中心上级单位&mdash;&mdash;中国区域特色经济研究院根本不存在。</p>\r\n\t<p>　　这时，丁某才意识到自己被骗，开始在网上发帖反映情况，并希望有关部门能够查实情况，挽回他公司的经济损失。</p>\r\n\t<p>　　兰州城关区法院2012年6月和7月作出的一份民事裁定书及两份司法建议，揭开了兰州市民王某利用伪造的营业执照及文件材料，申请成立西部中心的事实。</p>\r\n\t<p>　　2004年8月14日，兰州招商局(现兰州市经济合作服务局)同意了中国区域特色经济研究院在兰州市设立西部中心。</p>\r\n\t<p>　　申请设西部中心提交的材料中有一份营业执照和申请报告。这份中国区域特色经济研究院的营业执照显示，登记机关为北京市工商局，住所为北京市圆明园西路2号，企业类型为国有经济。</p>\r\n\t<p>　　申请报告这样描述中国区域特色经济研究院：&ldquo;是经党中央、国务院批准成立的专门从事区域特色经济的研究机构，该院已获得&lsquo;国家重大委托课题&rsquo;地位，这是国家最高级课题地位，关系到国家的重要决策。&rdquo;</p>\r\n\t<p>　　西部中心设立以后，2009年12月4日，兰州市招商局为其办理了外地驻兰办事机构登记证，负责人为王某，类别为事业，业务范围为经济研究、资料收集、会议组织、新闻发布。</p>\r\n\t<p>　　有了登记证后，王某开始在甘肃陇南洽谈开发水电项目，得到了陇南市发改委一位副主任的信任。陇南市发改委将一些水电项目交给了西部中心。</p>\r\n\t<p><strong>　　西部中心印章居然有国徽</strong></p>\r\n\t<p>　　丁某所在的甘肃某商贸公司是在2010年初与西部中心及其负责人王某产生交集的。当丁某发现西部中心并未取得水电项目的立项及批复时，一纸诉状将西部中心及其负责人王某起诉至兰州市城关区法院。</p>\r\n\t<p>　　2012年6月4日，城关区法院作出了民事裁定书。该裁定书称，根据中国区域特色经济研究院提供的营业执照到北京市工商局、国家工商局调取工商档案材料时，发现该研究院没有注册，其营业执照不真实。</p>\r\n\t<p>　　法院在向兰州市招商局了解中国区域特色经济研究院西部中心办理驻兰办登记材料时，发现其向兰州市招商局所提供的驻兰办证文件、材料、印章均不真实。</p>\r\n\t<p>　　&ldquo;被告中国区域特色经济研究院不存在，西部中心所提供的办证材料不真实，所谓的西部中心形同虚设。&rdquo;民事裁定书称，被告王某是以中国区域特色经济研究院西部中心名称签订涉案合同，故应确认该案无民事案件具备的平等主体。</p>\r\n\t<p>　　据此，城关区法院驳回了原告甘肃某商贸公司的起诉。</p>\r\n\t<p>　　民事裁定书还载明：&ldquo;该案涉嫌公文、国徽印章等情形。&rdquo;记者在西部中心所签的合同中发现，该中心使用的印章中确实带有国徽。</p>\r\n\t<p>　　记者了解到，最高人民法院关于在审理经济纠纷案件中涉及经济犯罪嫌疑若干问题的规定中明确要求，法院作为经济纠纷受理的案件，经审理认为不属经济纠纷案件而有经济犯罪嫌疑的，应当裁定驳回起诉，将有关材料移送公安机关或检察机关。</p>\r\n\t<p>　　记者查看该民事裁定书，没发现有将案件材料移送到公安机关或检察院的字样。但兰州市城关区法院于2012年7月3日，分别向兰州市经济合作服务局(原兰州市招商局)和陇南市发改委发出了司法建议书。</p>\r\n\t<p>　<strong>　司法建议书直指政府部门失职</strong></p>\r\n\t<p>　　在向兰州市经济合作服务局发出的司法建议书中，城关区法院明确指出，&ldquo;中国区域特色经济研究院&rdquo;营业执照、文件等材料均属伪造，其法人主体虚构。</p>\r\n\t<p>　　上述司法建议称，&ldquo;因你局办理的驻兰机构证照，使中国区域特色经济研究院西部中心在甘肃陇南承接开发水电项目，并随意签订转让合同，收取转让费等违法行为，在当事人发生纠纷时，又无法找到合法的法人主体进行追偿。&rdquo;</p>\r\n\t<p>　　该司法建议中所称的&ldquo;随意签订转让合同&rdquo;，是指西部中心于2010年7月与甘肃某商贸公司签订了&ldquo;马家坝水电站联合开发协议&rdquo;后，又于同年11月和陕西某矿业公司签订了&ldquo;马家坝水电站联合开发协议&rdquo;，将马家坝水电项目控股权再次转让。</p>\r\n\t<p>　　城关区法院建议，兰州市经济合作服务局在办理外地驻兰机构登记证时，应该认真审查外地机构的合法性，不能只是形式审查。</p>\r\n\t<p>　　在向陇南市发改委发出的司法建议书中，城关区法院也建议，在立项审核水电站工程时，应该认真审查机构的合法性及经济实力，并公开招标，不能只是形式审查。</p>\r\n\t<p><strong>　　陇南发改委一副主任涉嫌受贿</strong></p>\r\n\t<p>　　记者从检察机关了解到，陇南市发改委负责上述水电项目的那位副主任，因涉嫌受贿，去年底已被甘肃检察机关立案调查，目前案件还在进一步侦查之中。</p>\r\n\t<p>　　甘肃某商贸公司的起诉被驳回后，总经理丁某遂向兰州警方报案，称王某涉嫌构成伪造国家公文、证件、印章及合同诈骗。据了解，截至目前，警方还未立案。</p>\r\n\t<p>　　记者还获悉，王某因与湖南一家公司签订项目开发合作合同而被兰州警方立案侦查，并将案件移送至兰州市检察机关，检察机关因事实不清等原因退回警方补充侦查。记者从兰州公安部门了解到，该案目前仍在侦查之中。</p>\r\n\t<p>　　据了解，兰州市经济合作服务局已经注销了&ldquo;中国区域特色经济研究院西部中心&rdquo;，收回了《外地驻兰办事机构登记证》。今年5月21日，在该局向陇南市发改委发的一份函中称，西部中心在陇南从事了超业务范围经营。</p>\r\n\t<p>　　兰州城关区法院的两份司法建议直指兰州市招商局和陇南市发改委有&ldquo;管理漏洞&rdquo;。这一事件发人深省：兰州市招商局缘何让一个伪造材料申请的西部中心披上合法的外衣？陇南市发改委缘何能让一个利用虚假材料申请注册的&ldquo;外地驻兰办事机构&rdquo;轻易拿到水电项目的开发权？</p>\r\n\t<p>　　本报兰州7月7日电</p>\r\n\t<p align=\"right\">(原标题：伪造国字头研究院承接水电项目)</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (442,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27605794\' f_id=\'3\' -->\r\n\t<p>　　法制网记者张冲 法制网通讯员郭佳音</p>\r\n\t<p>　　一段摄录于6月25日的视频，清晰地记录了黑龙江省哈尔滨市城管局一名职工的粗暴行为。</p>\r\n\t<p>　　画面显示：城管督察车上下来的男子，走到麻辣烫摊前，趁女记者孙晓卓不备一脚将她坐的塑料凳踢飞，孙重重地摔在地上。</p>\r\n\t<p>　　目前，这名城管局职工因受到行政拘留10天的处罚，正关押在看守所里。他叫王雷，今年30岁，系哈尔滨市城市管理局职工。</p>\r\n\t<p>　　孙晓卓是哈尔滨电视台记者。当天，哈尔滨市道里区卫生监督所工作人员正在对一家无证经营的幼儿园进行检查，孙晓卓和另外一名东北网的记者在场采访。</p>\r\n\t<p>　　<strong>无证幼儿园扰民被举报</strong></p>\r\n\t<p>　　今年6月8日，有网民在哈尔滨市道里区政府的官方网页&ldquo;民生民意&rdquo;栏目当中留言，&ldquo;我是道里区海富康城小区B高1的居民，我家旁边开了个宝贝幼儿园，每天老师和孩子的吵闹声搞得我退休在家的母亲烦躁难熬，今年已住院两次。我详细了解到：宝贝幼儿园无执照，这样的幼儿园是不是该取谛！老百姓们求您去调查调查这个黑幼儿园吧！谢谢您了！&rdquo;</p>\r\n\t<p>　　6月13日，道里区教育局给予回复：&ldquo;教育局相关工作人员于2013年6月7日深入该园，严肃告知办园人，其擅自开办幼儿园的行为已违反了《幼儿园管理条例》第二章第十一条规定，要求其停止违法办园行为，停止招生，对其下发了&lsquo;违法行为限期改正通知书&rsquo;。&rdquo;</p>\r\n\t<p>　　幼儿园管理条例》第十一条规定：国家实行幼儿园登记注册制度，未经登记注册，任何单位和个人不得举办幼儿园。</p>\r\n\t<p>　　但教育局的《违法行为限期改正通知书》似乎并未起到应有的作用，幼儿园依旧我行我素。</p>\r\n\t<p>　　6月17日和6月21日仍然不断有网友在网上反映该家黑幼儿园的情况，道里区教育局的回复分别是&ldquo;关于您反映的情况，教育局相关工作人员多次踏查，积极与办园人沟通，责令其改正非法办园行为。&rdquo;&ldquo;如限期不能改正，将联合相关部门联合执法。&rdquo;</p>\r\n\t<p>　　6月25日下午，当地两家媒体东北网民生栏目记者和哈尔滨电视台方圆之间栏目记者实地暗访，发现这家黑幼儿园仍在经营。随后记者分别向道里区卫生监督部门和城管12319热线进行了反映，卫生监督部门派出3名执法人员很快到达幼儿园进行现场检查。</p>\r\n\t<p>　　<strong>记者遭质问&ldquo;凭什么采访&rdquo;</strong></p>\r\n\t<p>　　6月25日下午4时50分左右，一辆牌照为黑ALP757、内部编号3005的&ldquo;城管督察&rdquo;车停到了幼儿园门前。</p>\r\n\t<p>　　现场采访的记者本以为是接到城管指挥热线来查看&ldquo;幼儿园私扒承重墙&rdquo;情况的。岂料车上走下两名身着便装的男子，其中驾驶员进入幼儿园后先是质问卫生监督所执法人员&ldquo;你们凭什么检查？&rdquo;&ldquo;我是孩子家长，你们凭什么采访？&rdquo;随后该男子又将矛头指向了两名记者，&ldquo;你们别管我是幼儿园的员工还是孩子家长，我就问你们凭什么采访，你们把证件拿出来我看看。&rdquo;该男情绪颇为激动，现场一度混乱。</p>\r\n\t<p>　　据哈尔滨电视台记者孙晓卓讲，当天的采访其实已经结束了，有卫生监督所的执法检查，所拍摄的画面足够成片了。正准备要离开时这辆城管督察车停到了幼儿园门前，驾驶员下车后有些气急败坏。</p>\r\n\t<p>　　<strong>&ldquo;警用数码鹰&rdquo;录下全程</strong></p>\r\n\t<p>　　当日17时，道里区康安路派出所接到报警后有三名警员赶到现场维持秩序，其中一名民警胸前佩戴的编号为92780000的&ldquo;警用数码鹰&rdquo;清晰记录当天发生的情况。</p>\r\n\t<p>　　《法制日报》记者在视频中看到，因现场混乱，哈尔滨电视台记者孙晓卓先是站在距幼儿园门前20米远的麻辣烫摊前，随后坐到了麻辣烫摊前的塑料凳子上。</p>\r\n\t<p>　　民警要求双方一同到派出所解决问题，城管督察车上下来的男子仍然火气很大，走到麻辣烫摊前，趁孙晓卓不备一脚将其坐的塑料凳踢飞，孙重重地摔在地上。&ldquo;这麻辣烫是我家开的，你凭什么坐？！&rdquo;男子口中振振有词，民警见状立即将该男子控制带回派出所。</p>\r\n\t<p>　　<strong>城管局职工被拘留10天</strong></p>\r\n\t<p>　　经过民警调查证实，攻击电视台记者孙晓卓的男子叫王雷，今年30岁，工作单位系哈尔滨市城市管理局。</p>\r\n\t<p>　　孙晓卓的诊断书上记载：&ldquo;尾骨裂隙性骨折不除外&rdquo;。哈尔滨市公安局出具的法医鉴定结果为&ldquo;轻微伤&rdquo;。</p>\r\n\t<p>　　根据《中华人民共和国治安管理处罚法》第43条之规定，公安机关对王雷作出了行政拘留10天并处罚金500元的处罚决定。</p>\r\n\t<p>　　孙晓卓提供的另外一份录像显示，事发当日23时许，被传唤到派出所的宝贝幼儿园园长及老师做完笔录后，仍然是被白天那辆内部编号为3005的城管督察轿车接走，面对孙晓卓家人的质疑，驾驶该车的男子自称&ldquo;正在出夜勤&rdquo;。</p>\r\n\t<p>　　孙晓卓对《法制日报》记者说，当天晚上开城管督察车去派出所接幼儿园园长的男子在事发当天下午也出现过，与王雷一同来到幼儿园，当时从副驾驶下车，始终站在一旁观望整个过程。</p>\r\n\t<p>　　《法制日报》记者调查了解到，王雷的单位是哈尔滨市城管局下属的城市环境综合整治办公室(简称环卫办)，这是一个参照公务员标准的财政全额拨款事业单位。而他所驾驶编号3005号城管督察车户籍属于环卫办管辖的一家清洁公司，而这样的城管督察车在环卫办还有七八辆。</p>\r\n\t<p>　　<strong>伤人男子系城管局司机</strong></p>\r\n\t<p>　　7月3日下午，《法制日报》记者来到环卫办，在该单位的院内，记者看到多辆喷涂蓝白相间颜色的城管督察车辆，如果去掉醒目的&ldquo;城管督察&rdquo;四个字，外形和颜色与警用车辆极为相似。且每辆车上都有内部编号，但并未找到3005号车。</p>\r\n\t<p>　　此时，环卫办党组书记郭东升正在院内打电话，听闻记者要了解员工王雷被行拘的事件，连忙说&ldquo;这情况我知道，但我马上要去开会，去发改委开会&rdquo;。&ldquo;那能不能找一个熟悉情况领导给介绍一下情况。&rdquo;</p>\r\n\t<p>　　郭东升回到办公楼内，不一会儿，有工作人员通知记者去四楼会议室&ldquo;郭书记正在等候&rdquo;。</p>\r\n\t<p>　　记者：王雷在环卫办担任什么职务？</p>\r\n\t<p>　　郭东升：以前是我们粪便处理场的工人，前不久刚刚调到办里当司机，我们打算下一步给他退回去。</p>\r\n\t<p>　　记者：他当天是在执行公务当中吗？</p>\r\n\t<p>　　郭东升：不是，据我们了解他是开车拉着其他职工去垃圾场检查工作，返回单位的途中接到什么人的电话，个人临时决定去幼儿园的。</p>\r\n\t<p>　　记者：录像当中显示王雷穿着普通的短裤和T恤，驾驶执法车辆不需要统一着装吗？</p>\r\n\t<p>　　郭东升：我们环卫办没有统一的制服，即使开城管督察的车也不需要，因为我们的车辆虽然叫城管督察，但实际上根本没有任何执法权，执法权都在行政执法局那边。</p>\r\n\t<p>　　记者：这家幼儿园和门前的麻辣烫摊与王雷本人有关系吗？</p>\r\n\t<p>　　郭东升：这个还不清楚，王雷被拘留还没出来，出来后我们会好好调查一下。国家有规定，单位职工不允许从事第二职业，那个麻辣烫摊好像是王雷他妈开的。</p>\r\n\t<p>　　<strong>幼儿园园长疑为科长妻子</strong></p>\r\n\t<p>　　法制日报》记者随后请郭东升书记看了一下录像。问：当天晚上有人驾驶城管督察编号3005号车去派出所接幼儿园园长，还自称&ldquo;出夜勤&rdquo;，这人是谁？是否属于公车私用？</p>\r\n\t<p>　　郭东升：谭宏利，我们固废科的职工，等我们把事情查清楚了都会作出处理。</p>\r\n\t<p>　　离开环卫办后，有知情人向《法制日报》记者透露，谭宏利是环卫办固废科科长，幼儿园是其妻子所开，而且谭宏利的妻子也是环卫办职工。王雷是谭宏利提拔到固废科给自己开车的司机，当天接到幼儿园被检查的电话的人不是王雷，是谭宏利。这一说法尚未得到环卫办的证实，本报将持续关注此事进展。</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (443,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606256\' f_id=\'3\' -->\r\n\t<p>　　温都讯 昨晚，市公安局机场分局对&ldquo;<a href=\"http://news.sina.com.cn/s/p/2013-07-01/031927539435.shtml\" target=\"_blank\">6&middot;28瑞安籍乘客殴打国航地勤人员</a>&rdquo;一事作出处理：对殴打国航地勤人员朱某某(女)的陈某某(女)、刘某某(女)均给予行政拘留5日并处200元罚款的处罚。</p>\r\n\t<p>　　记者了解到，昨天这两名女乘客从北京返回温州后，到候机楼派出所接受处理。据候机楼派出所相关负责人介绍，此前殴打一事发生后，他们调取现场的监控录像，并向现场目击者了解情况，最终作出了这一处理决定。</p>\r\n\t<p>　　机场分局认定，两名女乘客存在殴打朱某某的事实，但殴打结果不构成轻伤，因此对两人给予行政拘留及罚款的处罚。</p>\r\n\t<p>　　6月28日晚，瑞安一批旅客搭乘的国航CA1812，原计划于当晚9时飞往北京。因北京天气的原因，到了晚上11时30分，国航决定取消该航班，次日不补班。旅客陈某某、刘某某对航空公司的安排不满，在5号登机口与前来安排航班后续工作的国航工作人员朱某某发生争执，随后两人殴打朱某某。</p>\r\n\t<p>　　最近几天，国内多个地方因雷雨天气，出现大面积航班延误或取消。7月6日凌晨，上海虹桥个别旅客因航班取消情绪激动，殴打航空公司地勤人员，还有人哄抢民警工作证件。机场警方将相关当事人带走调查，随后对4名肇事者分别处以拘留及罚款。温都记者 吕进科</p>\r\n\t<p>&nbsp;</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (444,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\r\n\t<div class=\"img_wrapper\" style=\"text-align: center\"><img alt=\"\" src=\"/u/cms/www/201308/281533525eqt.jpg\" style=\"width: 450px; height: 600px\" /></div>\r\n\t<div class=\"img_wrapper\" style=\"text-align: center\"><span class=\"img_descr\">男子包裹里全是此类艳照敲诈信</span></div>\r\n\t<div class=\"img_wrapper\" style=\"text-align: center\"><img alt=\"\" src=\"/u/cms/www/201308/28153415wq4t.jpg\" style=\"width: 450px; height: 600px\" /></div>\r\n\t<div class=\"img_wrapper\" style=\"text-align: center\"><span class=\"img_descr\">艳照敲诈信</span></div>\r\n\t<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606407\' f_id=\'3\' -->\r\n\t<p>　　荆楚网讯 (记者翟方)&ldquo;你好！我是xxx新闻网的记者，我已查到你生活中不为人知的另一面，只要我在网上一传，你将前途尽毁甚至面临牢狱之灾&hellip;&hellip;三天内汇给我25万元人民币，我就毁灭证据。&rdquo;</p>\r\n\t<p>　　7月3日下午2时54分，一中年男子走进襄阳市樊城区一家快递公司，准备向多个地区快递100多封有这样内容的信件，收件人全部是一些单位的领导干部，信件内还附有这些官员的&ldquo;艳照&rdquo;。</p>\r\n\t<p>　　监控录像显示，该男子戴着眼镜，身着灰色休闲短袖上衣和短裤，脚穿拖鞋，背着一个黑色包。该男子自称是襄阳某网站记者，要寄邀请函。随后，他从包中拿出了一个塑料袋，袋内装了100多封已封好的信件。快递员发现，信件收件人全是各单位的主要领导，而男子神情有异，于是进行试探，要求他自行打开信封接受检查，但男子称会弄坏邀请函，拒绝打开。</p>\r\n\t<p>　　快递员掏出电话欲报警，让人意想不到的是，该男子撒腿就逃，工作人员死死抓住门把手不让他出门。僵持几分钟后，该男子丢下物品，跳窗逃走。</p>\r\n\t<p>　　民警赶到后检查发现，该男子要邮寄的信件全是对各地官员的敲诈信，信中称有人对收件官员恨之入骨，于是私下花重金雇请该男子对其调查取证，目的是拿到证据后整垮收件官员。敲诈信中称，&ldquo;已查到你生活中不为人知的另一面&rdquo;，并威胁将会把资料传到网上。</p>\r\n\t<p>　　同样在信中，该男子又变脸对收件官员&ldquo;大发慈悲&rdquo;，说双方无冤无仇，只要收件官员三天内汇款25万元，便会销毁所有证据或转交给对方，绝不会有第三人知晓。举报信中还留下了一个银行账号，户主名为&ldquo;刘细勇&rdquo;。信件中附有&ldquo;涉事官员&rdquo;裸体怀抱美女的艳照、记者证的封面照片、U盘照片等。快递员称，这些照片都很相似，疑为合成的。</p>\r\n\t<p>　　警方当场扣下了这些信件。至昨日，寄信男子尚未落网，相关案情正在进一步调查中。</p>\r\n</div>\r\n<p>&nbsp;</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (445,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606839\' f_id=\'3\' -->\r\n\t<p>　　据江淮晨报报道，铜陵一男子在外花天酒地，借债一万元无钱归还，竟与朋友合伙骗自己老婆。7月3日，该男子声称自己被人&ldquo;绑架&rdquo;，要求妻子拿&ldquo;赎金&rdquo;一万元救自己。最后老婆没骗到，自己的朋友却因吸毒被公安机关依法行政拘留。</p>\r\n\t<p>　　7月3日，铜陵市长江路派出所在日常接处警过程中，接到李女士报警，称自己的老公被人绑架了，对方要求她拿一万元钱去赎人。接警后，民警及时与李女士取得了联系。李女士称&ldquo;绑匪&rdquo;打电话给她，声称她老公吴某在他们手上，要求李女士马上拿一万元到某派出所门口，会有人跟她联系，和她接头之后，找她拿钱放人。</p>\r\n\t<p>　　民警根据李女士提供的信息，在某派出所门口将前来拿赎金的&ldquo;绑匪&rdquo;王某抓获。通过对王某审讯，民警知道了&ldquo;绑匪&rdquo;的具体藏身地点。民警带着王某来到了某小区某栋某室，民警让王某敲开门，进入室内后，警方立即将房间里的三人控制住。在民警寻找被&ldquo;绑架&rdquo;的吴某时，有一人声称自己就是吴某，民警遂将三人带到了派出所。</p>\r\n\t<p>　　经讯问，事情的结果让办案民警瞠目结舌。原来让人担心的&ldquo;绑架&rdquo;情节，竟然是吴某自导自演的一场闹剧，那几个所谓的&ldquo;绑匪&rdquo;都是他的朋友，目的就是骗老婆的钱，想不到老婆会报警。得知真相后，李女士告诉警方，她对老公非常失望。最后，吴某的朋友因吸毒被公安机关依法行政拘留。(记者 方佳伟 通讯员 汪江 吴彬)</p>\r\n\t<p align=\"right\">(原标题：男子自导自演绑架为骗老婆一万)</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (446,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606943\' f_id=\'3\' -->\r\n\t<p>　　本报讯 李展辉 钟玲珑 首席记者程呈报道：新娘婚宴上逃婚，新郎痛苦不已索精神损害赔偿。7月4日，泰和县人民法院一审审理了一起婚约纠纷。</p>\r\n\t<p>　　2012年9月，吴子华与女子黄晓菊谈起了恋爱，期间，黄晓菊不断向吴子华索要钱财，吴子华先后10次通过银行汇款近3万元给黄晓菊。双方商定2013年4月举行婚礼。结婚当日近百人到现场祝贺，黄晓菊却不同意结婚，并驾车迅速逃离。吴子华将黄晓菊起诉至法院，要求黄晓菊返还其财产2.95万元，并赔偿其精神损失2万元。</p>\r\n\t<p>　　法院一审认为吴子华提出要求被告黄晓菊赔偿精神损害赔偿符合法律规定，但其诉请过高，应予核减，故判令黄晓菊返还吴子华财产2.95万元，支付精神损害赔偿5000元。(文中人物均属化名)</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (447,'<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606968\' f_id=\'3\' -->\r\n\t<p>　　东南网7月8日讯(海峡导报记者 蔡晶晶 通讯员 杨媛 林曼娜)酒后翻墙进别人家，不顾女主人怀有9个月身孕，竟想实施强奸，没能得逞的他还用刀划伤孕妇的婆婆。昨日，漳州市漳浦县检察院以涉嫌强奸罪对黄某龙依法批准逮捕。</p>\r\n\t<p>　　4月19日晚上，家住漳浦县深土镇的犯罪嫌疑人黄某龙酒后经过邻居王某家，见王某房间的灯还亮着，于是翻墙而入径直从楼梯上二楼王某的房间。见到王某在屋里看电视，黄某龙关灯将王某按倒，打算强奸她，全然不顾王某已怀有身孕9个月，在王某奋力挣扎并苦苦求饶时，黄某龙仍揪住王某的头发把她拖到床边。此时，睡在床上的王某女儿惊醒，见母亲嘴角流血，便大声哭喊，黄某龙害怕孩子的哭声引来他人，才翻墙离开。</p>\r\n\t<p>　　离开王家不一会儿，黄某龙发现自己的手机不见了，怀疑掉落在王某家中，竟在路边捡了把菜刀又折回王某家，打算和王某家人&ldquo;理论&rdquo;，其间挥刀划伤王某的婆婆。后来在邻居的劝架下黄某龙才离开，王某一家随即报警。</p>\r\n</div>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (448,'<p>本报深度记者 朱洪蕾</p>\r\n<p>&ldquo;短时间内杀了七条命，那么多警察，竟然抓不到我，老天助我也，看来我还要继续作案杀人!&rdquo;湖南武冈少年刘洋(化名)安然地坐在网吧里的电脑前敲出这句话。</p>\r\n<p>两个星期内，他和同伴付云强(化名)连续在湖南、广东、云南等地下手，就像是与时间在赛跑。</p>\r\n<p>这场&ldquo;杀人游戏&rdquo;的唯一赌注仅仅是为了获取更多的钱，在他俩看来，只要比打工赚得多。</p>\r\n<p>[NextPage][/NextPage]</p>\r\n<p>而在游戏开始前的半年，两个少年都还坐在学校里，身上并没有显露丝毫邪恶的印迹。</p>\r\n<p>刘洋和付云强曾经是同班同学，同住一个宿舍的上下铺让他俩有了更多的共同话题，相似的家庭背景让两人有了共同的语言。</p>\r\n<p>刘洋出生于1994年，付云强比他还小一岁。父母常年在外打工，刘洋从小就跟着外公外婆长大，只在暑假的时候，才会去爷爷奶奶家住一段时间，与父母相处的时间就更少了。</p>\r\n<p>付云强的家里还要困难，母亲患有眼疾，经常看不清东西，村里人背后就叫她&ldquo;瞎子&rdquo;，没法出去打工，挣钱的任务就交给了付云强的父亲和姐姐。</p>\r\n<p>即便如此，两个家庭都希望孩子能考上大学，用知识来改变生活。</p>\r\n<p>刘洋的成绩曾经在班里名列前茅，还是老师眼里的乖孩子和小才子，作文写得好，画也画得好。</p>\r\n<p>眼看着初中要读完了，一切却都变了。他的一个叔叔和疼爱他的奶奶接连去世，刘洋深受打击，他常想&ldquo;为什么好人不长寿&rdquo;，他的心思也跟着变了，上大学不再是追求的目标。初三下学期，刘洋开始迷恋于上网，经常课也不上就泡在网吧里。幸亏之前的学习底子还比较牢固，顺利帮助他升入高中。</p>\r\n<p>在高中，刘洋遇到了付云强。付云强有些孤僻，不怎么喜欢跟别人交流，老是心事重重，即便老师问他，他也不愿意多说。</p>\r\n<div style=\"page-break-after: always;\"><span style=\"display: none;\">&nbsp;</span></div>\r\n<p>经常上网的刘洋高中成绩一路下滑，甚至受到了老师的劝退。父母觉得是自己没照顾好他，就把他接到身边，希望他能好好读书，但刘洋依然我行我素。</p>\r\n<p>付云强的成绩一直垫底，眼见考大学无望，家里又不富裕，跟父亲商量后，2012年过完春节后不久，他就辍学到外地打工去了。</p>\r\n<p>2012年8月的一天，两个少年又在网上相遇。彼时的刘洋正拿着从家里偷来的几百元钱，泡在县城的网吧里，而不是按学校的要求乖乖呆在学校里补课。</p>\r\n<p>半年未见，付云强聊起了自己的打工生活。他认为，外面的世界很精彩，但是通过打工赚大钱实在太难了。两人在网上一拍即合，决定结伴儿出走，跟电影里演的黑社会一样，通过不正当的手段弄钱花。</p>\r\n<p>不几天，付云强怀揣打工挣来的几百块钱回到了武冈，一来就先给拮据的刘洋买了一堆吃的，让他改善生活。</p>\r\n<p>两人坐下来商量，打工赚钱不好赚，还经常有人拖欠工资，想要赚钱的话，抢劫才是最快捷的方式。考虑到两人都不高，而且身形偏瘦，在城里抢劫肯定不易下手，就把视线转向了农村。</p>\r\n<p>说干就干，付云强买来两把刀，8月21日，两人冒冒失失地到武冈市湾头镇的张某家里打劫。张某夫妇声称没钱，只给了两人90多块钱。一怒之下，他们对着夫妇俩刺了数刀，夫妇俩当场殒命。其实付云强一开始还不想杀人，但怕刘洋说他不够义气，就合力制造了这场血案。</p>\r\n<p>之后，两人赶紧动身来到东莞，继续寻找作案目标。8月25日，以热水器坏了为由，两人将女房东骗至出租屋，持刀威胁并绑住女房东，搜走了现金3000余元和银行卡，在女房东拒绝说出银行卡密码后，两人将她当场刺死。一不做二不休，等到男房东晚上回家，他们又以换房为由将他骗出，逼他说出银行卡密码后将他捅死。</p>\r\n<p>刘洋和付云强像是着了魔。8月29日，两人跑到云南钻进曲靖市富源县大河镇唐某家，将唐某捆绑后抢到90元钱，怕他报警，就将他勒死，然后扬长而去。</p>\r\n<p>是夜，两人住到富源县营上镇吴某家的旅馆。第二天上午，两人让吴某帮忙换牙刷，吴某就背着两岁大的女儿来到两人住的房间。在房间里，他们将怀孕五个月的吴某杀死，看到吴某两岁的女儿在一旁啼哭不止，担心引人注意，又用被子将小女孩捂死，然后来到吴某的卧室，将所有值钱的东西都拿走，再辗转逃到深圳。</p>\r\n<p>其间，两人不忘在网上跟网友分享他们的杀人经历，还收到了&ldquo;真酷!&rdquo;的一致评论。</p>\r\n<p>来到深圳后，两人打算消停一段时间，准备安心打工。刘洋戴着眼镜看着还比较斯文，就被一家饭店留下了，而付云强一时找不到工作，随后决定到上海去投靠一个朋友。</p>\r\n<p>9月6日傍晚，火车还在江西境内，付云强就被铁路公安抓获。随后，刘洋也在深圳落网。</p>\r\n<p>面对警方，两人非常坦然：&ldquo;我杀了人，我在湖南、广东、云南都杀了人。&rdquo;两人面无惧色地将作案情节从头到尾做了完整的复述。</p>\r\n<p>[NextPage][/NextPage]</p>\r\n<p>这一切让人感到无法理解。在后来的庭审中，休庭后，法庭特意邀请心理咨询师对二人分别进行心理评估。</p>\r\n<p>在与心理咨询师沟通后，两人才开始意识到自己的错误。社会调查员通过跟两人接触，认为两人都是留守儿童，父母关爱不够，最后走错路，很大程度上应归结于整个社会的大环境。</p>\r\n<p>在被看押期间，刘洋对自己之前的行为写了一些感受，在感受中，他提到，&ldquo;过去不会思考别人，只会想到自己&rdquo;，&ldquo;自己的冲动做法，害了这么多家庭&rdquo;，&ldquo;不管如何，都无法弥补了&rdquo;。</p>\r\n<p>确实无法弥补，在一审被判死刑后，刘洋会很快走到生命的尽头;付云强被判无期，以后的日子可能都得在监狱里度过了。</p>\r\n<p align=\"center\"><img align=\"\" alt=\"\" border=\"0\" src=\"http://demo7.11077.net/jeecmsv5/u/cms/www/201307/101732351isd.jpg\" /></p>\r\n','<p>\r\n\t<img alt=\"\" src=\"/jeecmsv5/u/cms/www/201307/101732073o7k.jpg\" style=\"width: 600px; height: 900px;\" /></p>\r\n',NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (495,'<p style=\"text-align: center\"><span><font color=\"#000000\"><img _fcksavedurl=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8498180/640\" alt=\"刘晓庆今日大婚 第四任老公系将门之后政协委员\" src=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8498180/640\" /></font></span></p>\r\n<p style=\"text-align: center\"><span><a class=\"a-tips-Article-QQ\" href=\"http://datalib.ent.qq.com/star/182/index.shtml\" target=\"_blank\"><!--/keyword--><font color=\"#000000\">刘晓庆<!--keyword--></font></a><!--/keyword-->晒穿花裙子照。</span></p>\r\n<p align=\"center\" style=\"text-transform: none; font: 14px Arial, Verdana, sans-serif; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">&nbsp;</p>\r\n<p style=\"text-align: center\"><img _fcksavedurl=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500820/640\" alt=\"刘晓庆今日大婚 第四任老公系将门之后政协委员\" src=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500820/640\" /></p>\r\n<p align=\"center\" style=\"text-transform: none; font: 10pt 宋体; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">刘晓庆团队公布的William先生照片</p>\r\n<p align=\"center\" style=\"text-transform: none; font: 14px Arial, Verdana, sans-serif; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">&nbsp;</p>\r\n<p style=\"text-align: center\"><img _fcksavedurl=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500821/640\" alt=\"刘晓庆今日大婚 第四任老公系将门之后政协委员\" src=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500821/640\" /></p>\r\n<p align=\"center\" style=\"text-transform: none; font: 10pt 宋体; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">2010年，王晓玉以全国政协委员身份接受新华网采访</p>\r\n<p style=\"text-indent: 2em\">中新网8月21日报道，近日，关于刘晓庆秘密赴美完婚的新闻传得沸沸扬扬，占据各大娱乐版块。8月20日早上，刘晓庆团队发表声明，透露期第四任丈夫王晓玉的情况。声明中透露刘晓庆与第四任丈夫已与去年注册结婚，声明中还称其丈夫比刘晓庆大，是将门之后，事业成功。晚些时分，记者致电刘晓庆身边工作人员，得知刘晓庆将于美国时间8月20日在美国举行婚礼。</p>\r\n<p style=\"text-indent: 2em\"><strong>老公揭秘：政协委员王晓玉</strong></p>\r\n<p style=\"text-indent: 2em\">据知情人士爆料，刘晓庆现任丈夫为商人王晓玉，2010年还担任过全国政协委员。</p>\r\n<p style=\"text-indent: 2em\">爆料称，刘晓庆现任丈夫为香港商人王晓玉，资料显示，1942年出生于安徽的王晓玉拥有众多头衔，包括中华海外联谊会理事、中华全国侨联委员、安徽省政协常委、香港安徽联谊总会会长、顺德锡山家具有限公司董事长、华美贸易公司总经理、第十届全国政协委员。据悉，王晓玉是黄埔后代。其父王剑秋是黄埔武汉分校七期毕业生。王晓玉投资家具厂多年，如今厂已经遍布广东、浙江、上海、山东、天津等地。资料显示，锡山家具专注于高端家具生产，多半出口海外，刘晓庆也曾入股家具连锁店。当时还打起了广告噱头，&ldquo;交一万元订金就可免费乘包机到成都，买2万元家具就可在本月19日与明星刘晓庆共进鸡尾酒会和看烟花。&rdquo;</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (496,'<p style=\"text-align: center\"><span class=\"infoMblog\"><img alt=\"赵本山女儿正式进军娱乐圈 将与潘长江女儿比拼\" src=\"http://img1.gtimg.com/ent/pics/hv1/150/212/1397/90894135.jpg\" /></span></p>\r\n<p style=\"text-align: center\"><span class=\"infoMblog\">潘阳</span></p>\r\n<p align=\"center\">&nbsp;</p>\r\n<p style=\"text-align: center\"><img alt=\"赵本山女儿正式进军娱乐圈 将与潘长江女儿比拼\" src=\"http://img1.gtimg.com/ent/pics/hv1/151/212/1397/90894136.jpg\" /></p>\r\n<p align=\"center\" style=\"font-family: 宋体; font-size: 10.5pt\">赵本山与女儿赵一涵</p>\r\n<p style=\"text-indent: 2em\">新快报8月21日讯 日前，浙江卫视宣布推出全国首档青年励志节目《我不是明星》，<!--keyword--><!--/keyword-->陈宝国<!--keyword-->儿子陈月末、潘长江女儿潘阳等30多位明星子女均已确定加盟。据透露，赵本山女儿赵一涵也有望加盟，藉此正式进军娱乐圈，并将与潘长江女儿潘阳同台对决。</p>\r\n<div __curdisplay=\"block\" class=\"mbSourceCardInfo\" style=\"display: none\">\r\n\t<div class=\"arrowBox\">\r\n\t\t<div calss=\"arrow\">&nbsp;</div>\r\n\t</div>\r\n\t<div __curdisplay=\"block\" class=\"mbloading\" style=\"display: none\">&nbsp;</div>\r\n\t<div class=\"mbCardUserDetail\">\r\n\t\t<div class=\"userPic\"><span class=\"infoMblog \"><a bosszone=\"followalltx2\" href=\"http://t.qq.com/chenbaoguo?pref=qqcom.keywordtx2\" rel=\"陈宝国(@chenbaoguo)\" target=\"_blank\" title=\"陈宝国(@chenbaoguo)\"><img src=\"http://t2.qlogo.cn/mbloghead/ec4910a28b805eb55ee4/50\" /></a></span></div>\r\n\t\t<div class=\"userInfo\">\r\n\t\t\t<div class=\"nick\"><span class=\"infoMblog \"><a bosszone=\"followallname\" href=\"http://t.qq.com/chenbaoguo?pref=qqcom.keywordname\" target=\"_blank\" title=\"陈宝国(@chenbaoguo)\"><span><font size=\"3\">陈宝国</font></span></a></span></div>\r\n\t\t\t<div class=\"follower\"><span class=\"infoMblog \"><a bosszone=\"followalltz\" href=\"http://t.qq.com/chenbaoguo/follower?pref=qqcom.keywordtz\" target=\"_blank\" title=\"听众：896459人\"><span>听众：</span><span>896459人</span></a></span></div>\r\n\t\t\t<div class=\"attentBoxWrap\" follow=\"0\" uid=\"chenbaoguo\"><span class=\"infoMblog \"><a bosszone=\"followallst\" class=\"addAttention\" href=\"javascript:;\" title=\"立即收听\"><span>+收听</span></a><a class=\"delAttention\" href=\"http://ent.qq.com/a/20130821/002333.htm#\" style=\"display: none\" title=\"已收听\"><span><font color=\"#666666\">已收听</font></span></a></span></div>\r\n\t\t</div>\r\n\t\t<div class=\"userNew\">\r\n\t\t\t<div class=\"titleBox\"><span class=\"infoMblog \"><span>最新消息</span> <span class=\"timer\" rel=\"1322634633\" title=\"2011年11月30日 14:30\"><font color=\"#999999\">2011年11月30日 14:30</font></span></span></div>\r\n\t\t\t<div class=\"news\"><span class=\"infoMblog \">感谢诸位朋友的关心，最近在家调整休息。近来天冷了，大家注意身体。<a bosszone=\"followallmore\" href=\"http://t.qq.com/chenbaoguo?pref=qqcom.keywordmore\" target=\"_blank\"><font color=\"#0b3b8c\">更多</font></a></span></div>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 《我不是明星》将邀请娱乐明星、社会名人子女来参赛，有哪些&ldquo;星二代&rdquo;来参赛？据节目组官方微博透露，参赛的选手父母都是众人皆知的一线大牌明星，目前起码有两个&ldquo;影帝&rdquo;、三个一线歌星以及三个上过央视<!--keyword--><!--/keyword-->春晚<!--keyword--><!--/keyword-->小品演员的子女都有意向参加。当中，既有小有名气的，也有尚不出名准备进军演艺圈的。其中&ldquo;影帝&rdquo;陈宝国的儿子陈月末、&ldquo;小品王&rdquo;潘长江的女儿潘阳等已确定加盟，并参与首期节目录制。另据知情人透露，成龙、<!--keyword--><!--/keyword-->曾志伟<!--keyword--><!--/keyword-->、<!--keyword--><!--/keyword-->王菲<!--keyword--><!--/keyword-->、赵本山、谢贤、赵雅芝、<!--keyword--><!--/keyword-->郭德纲<!--keyword--><!--/keyword-->等大腕明星子女也都在节目组的邀请之列，有传赵本山女儿赵一涵也有意借该节目正式进军娱乐圈。</p>\r\n<p style=\"text-indent: 2em\">据悉，赵本山女儿赵一涵多才多艺，几年前远赴新加坡一家知名演艺学校求学，这次加盟也是其国内综艺节目的首秀。值得一提的是，同为东北两大&ldquo;小品王&rdquo;后代，赵一涵和潘阳两人同台对决也备受外界期待，网友笑言，这回两人不仅拼才艺还拼爹。对于赵一涵加盟的消息，节目组表示正积极接洽中。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (498,'<p>&nbsp;</p>\r\n<p style=\"text-align: center\"><!--/keyword--><img alt=\"冯小刚定春晚相声小品类基调：唱赞歌一律不要\" src=\"http://img1.gtimg.com/ent/pics/hv1/10/183/1398/90951625.jpg\" /></p>\r\n<p style=\"text-align: center\">冯小刚<!--keyword--></p>\r\n<p style=\"text-indent: 2em\">成都商报8月21日报道，前日，刚从国外归来的马年<!--keyword--><!--/keyword-->春晚<!--keyword--><!--/keyword-->总导演冯小刚与副总导演赵本山、艺术顾问张国立、刘恒和策划张和平等春晚智囊团首次召开了语言类节目碰头会。此次碰头会从早上开到晚上7点半，&ldquo;贺岁王&rdquo;与&ldquo;小品王&rdquo;在走马上任后的首次碰面为春晚小品、相声等语言类节目的总基调&ldquo;一锤定音&rdquo;。记者获悉本次开会气氛非常轻松，而总导演冯小刚则把敢说敢言的个性展现得淋漓尽致。前日他明确表示，相声小品不能束手束脚，要解放思想，颂赞歌的作品一律不要出现，最喜欢有尖有刺的东西。</p>\r\n<p style=\"text-indent: 2em\">作为电视节目导演界的&ldquo;新人&rdquo;，冯小刚不改其在春晚发布会上的态度，坚决要把针砭时弊的东西带入到语言类节目创作中去，冯小刚曾对春晚语言类节目提出了一个四字方针&mdash;&mdash;&ldquo;干预生活&rdquo;，希望作品能回归讽刺辛辣幽默。前日的碰头会上，冯小刚大胆地提出要摒弃那些煽情、唱赞歌的东西，所有创作者要放开手脚，敢写敢说，呈现出带尖带刺、有棱有角的相声和小品来。冯小刚举例子说，他特别推崇上个世纪八十年代马季和姜昆的讽刺相声，他更举了马季的《宇宙牌香烟》、姜昆的《虎口遐想》和《电梯风波》等作品，认为这才是真正针砭时弊，贴近生活的好作品。</p>\r\n<p style=\"text-indent: 2em\">据悉，赵本山在会上并未发表过多见解，相反多是倾听冯小刚的意见。而尹琪在接受记者采访时则表示，往年春晚作品的创作时间大概在11月份，自己参加本次碰头会也意味着&ldquo;赵氏&rdquo;春晚作品创作时间提前到八月下旬，整整提前了三个多月。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (499,'<p style=\"text-indent: 2em\">我观察到一个现象，就是目前在很多领域，大家对于终极价值的关怀是不够的。人们往往会较多关注法律规定对现实活动的具体影响，而容易忽略法律规定背后所应遵循的基本准则，特别是容易忽略法律的终极价值或伦理价值，这样就缺乏一个最终端的评价标准。</p>\r\n<p style=\"text-indent: 2em\"><strong>社会要公正，就必须透明</strong></p>\r\n<p style=\"text-indent: 2em\">首先是判断终极价值的现实体现，即正义是社会制度的首要价值。正义是非常抽象的，我们要把它落到现实当中去，需要通过具体的原则和方法。在现实社会当中很多发达国家不大讲公开，他们讲&ldquo;透明&rdquo;。透明这个词更加鲜活、精准。我认为一个社会要公正，就必须透明。</p>\r\n<p style=\"text-indent: 2em\">我们交了税以后，要获得的是一种公共产品或者公共服务。如果我们交了税以后，政府不能提供给我们最起码的公共服务，这个财政的正义就缺乏了，这是最基本的要求。比如说农村公共设施是非常落后的，怎么来保障财政正义的实现呢？首先在制度层面必须要明确不同种类的公共产品，在不同政府的分工。我们并没有看到这么一种明确的法律规定，这样的规定应该列入《宪法》。在操作层面、技术层面，我们很希望看到有一个清单，能够告诉民众哪样的公共服务由哪一级政府来提供。</p>\r\n<p style=\"text-indent: 2em\">比如义务教育的问题在于供应不足。在2000年以前，我们义务教育的经费是乡镇政府来保障的，乡镇政府来为义务教育提供服务。后来改为县政府来提供，义务教育到底应该由哪一级政府提供呢？我认为这是中央政府的责任，这个钱可以由中央来出，事情由地方来做。</p>\r\n<p style=\"text-indent: 2em\">政府除了收费、收税之外，它还掌握了一个公共资源。它的收费、租金，包括转让的价格都应该基于市场原则，也就是说政府应该把那些资源给出价最高的人。如果是低价给人家，或者说免费提供给经营者就侵害了老百姓的利益。</p>\r\n<p style=\"text-indent: 2em\">公共罚没收入应以促进法律遵从为原则，如果变成了以收入为目的的话，就完全背离了正义。开车的人都特别担心被贴罚单。什么叫&ldquo;违章停车&rdquo;？有两种原则来定义：一种是交管部门划出一个特殊的区域，说这里是可以停车的，其他地方不能停车。还有一种是划出一个区域，说这个地方不能停，其他地方都可以停车。我们现在采用的是第一种原则，如果采用第一种原则，我们被罚的概率就会增加很多。交管部门很容易就会抓到你，这个执法的弹性就很大。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;罚款发工资&rdquo;是什么意思呢？有一些工作的人手不够，就要聘一些临时工，他们就是靠罚款来养这些人。如果是这样的话，我认为就违背了正义，如果这些临时工确实是工作需要的，就应该让他进编制，你不应该用罚款来保障他。当公共处罚以谋求收入为目的的时候，就与正义背道而驰了。量能课税应合乎比例原则，分担税负的两个原则是受益原则与能力原则：首先采用受益原则将各种特别公共基金与一般公共基金相区别；然后，对于一般公共基金采用能力原则。量能负担原则的衡量指标选择，是收入、消费还是财产？</p>\r\n<p style=\"text-indent: 2em\">量能负担原则的基本含义是没有能力的不交税，能力强的要比能力弱的多交税。所以收入多的人应该比收入少的人多交税，消费多的人应该比消费少的人多交税，财产多的人应该比财产少的人多交税。问题是怎么来确定具体的数额？人头税是否公平呢？当人人都差不多的情况下是公平的，但是现实中人和人的差别很大，所以人头税是不公平的。比例税是不管收入、财产、消费数额多大，都是统一比例的。还有一种标准是累进税，就是说你的收入超过的部分越高，就逐步增加。有的人说累进好，有的人说比例好。比例和累进都能够体现能力原则，当然程度不一样。累进的程度可能更多一点，但是累进不能解释一个能力强的人和一个能力弱的人相比，在多大程度上应该多交多少税。我们人类的理性无法回答这个问题，就只有在实践当中，大家通过互相博弈来解决这个问题。也就是说写在法律当中的，看起来很美，很有逻辑的累进税制，可以说暗含着一些不公平，有一定的武断性。比例税制由于不歧视，对所有人都一样，某种程度上又是公正的。而且比例税制还有一个好处，就是可以消除累进税制规避税收的机会。我的倾向性意见是认为比例税制比较好。</p>\r\n<p style=\"text-indent: 2em\">以上是一些财税正义的具体制度，可是我们发现财和税是不可分割的。你征税是为了某种支出，我们前面讲的是收入取得过程当中的正义。你在收入的时候很正当，但是你拿了钱以后做不正当的事情，我们会发现这都是虚假的。所以财政的收入应该从整体上考虑，也就是说必须要有收支相连的思路来考虑。如果把收和支割裂开，只考虑收入的公正不考虑支出的公正，或者反过来说，我认为在逻辑上会有混乱，而且在实践当中会产生问题。收支相连才能全面落实纳税人的权利，充分体现财税制度的正当性。</p>\r\n<p style=\"text-indent: 2em\"><strong>财税正义应该贯穿终生</strong></p>\r\n<p style=\"text-indent: 2em\">现在提出了一个&ldquo;环保税&rdquo;，有多少人是赞成&ldquo;环保税&rdquo;的？一般认为需要征的逻辑是觉得中国的环境很差，需要征税来保护环境。中国现在环境问题如此的严重，是不是因为征税不足造成的？好像不是，如果不是的话，你这个征税到底能在多大程度上缓解，或者说解决这个环保问题呢？这就让我们思考一个问题，就是为什么要征税？我认为政府向老百姓征税还有其他的原因，它没有说出来的原因就是为了拿钱。钱不够向大家要税，这是不是该有一个正当理由？如果政府告诉大家现在我提供这么多公共服务，确实是钱不够了，老百姓是同意的。你已经收了很多的钱，你又不能说缺钱，你又不能说清楚已经收的钱都已经得到了充分的使用，没有浪费、贪污，你为什么还要向我征税？调控是不是一个正当的理由？在我看来调控论有一定的道理，但不是所有的事实都能支撑它。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;公款不得私用&rdquo;好像是一个尝试，而不是一个原则。什么时候这种公款私用是正当的？第一，个人生活极端的贫困，处在我们贫困线以下，所以我们拿一部分公款来救助他。我认为这就是公正的，因为每个人可能都有这种活不下去的时候。第二，如果个人遭受了公共权力机构的侵害，他应该得到一个补偿，这个给他也是正当的。第三，人人有份，就像澳门一样发一个年终红包。超出这些范围的，我觉得就不太正当了。</p>\r\n<p>[NextPage][/NextPage]</p>\r\n<p style=\"text-indent: 2em\"><strong>下面我举例讨论：</strong></p>\r\n<p style=\"text-indent: 2em\">1.经济适用房由政府买单是否涉嫌公款不当私用？谁在买经济适用房？能买得起经济适用房的人最起码不是最穷的人，在大城市里面这个补贴是很大一部分资金。</p>\r\n<p style=\"text-indent: 2em\">2.公共奖励金及其免税是否具有正当性？奥运会得奖了，我们要不要拿钱来奖励他？中科院的院士每年有1万元的津贴，我们给他免税，你认为正当吗？</p>\r\n<p style=\"text-indent: 2em\">3.政府官员公车补贴是否正当？这就变成了一种隐性的福利，为什么他们不坐公交车，不骑自行车。如果公车公用的话，我认为一个城市里面可以有一个集中的统一调动中心，为什么每个干部都配公车？</p>\r\n<p style=\"text-indent: 2em\">4.政府招商引资时实行的&ldquo;财政返还&rdquo;政策是否具有正当性？这些都是公款，很多政府在花钱的时候，到底有没有正当性的支持？现在我们都默认了，随便怎么用。</p>\r\n<p style=\"text-indent: 2em\">不同财政收支系统的自我平衡原则。财政有不同的收支系统，比如说社保是一个系统，政府经费是另外一个系统，政府有一些基金项目也是独立运行的系统。社保基金的钱不可以跟其他的基金划转，如果划转了就破坏了公正，这也是非常重要的整体原则。</p>\r\n<p style=\"text-indent: 2em\">真正的财税正义，不仅仅是在某个时点或特定时期内的公平正义，应该是终生的。如果有一个办法可以把不同的人，一生当中所得到的财政利益和财政负担计算出来，这个终生正义就有一个基础了。税收优惠是非常普遍的一个现象，这个税收优惠的正当性在哪里？到底该还是不该？有些行业经常会说我这个行业需要发展，当然，特殊的阶段性，短期考虑是有理由的。所以从这个意义上讲，优惠是可以，但是必须要有期限。你不能永远给它优惠，必须要有期限的优惠，没有期限的优惠我认为都是有问题的。</p>\r\n<p style=\"text-indent: 2em\">代际间的公平公正，也是社会制度需要考量的重要方面。社会要发展，人类要进步，所以当代人为后代人多做点努力是正当的。如果反过来说，当代人去侵害后代人的利益就有问题了。当代人忽视后代人的利益，因为后代人还没有出现，你就把他们本该拥有的东西给侵占了，这就违反了代际正义。这种情况一定要通过制度约束，约束当代人过度侵害后代人的情况。这些在财政方面也有一些考虑，但是还不够。比方说资本预算制度就是这样一种考量，如果借钱的话就要通过资本运算来解决。</p>\r\n<p style=\"text-indent: 2em\"><strong>技术手段支持构建&ldquo;税制梦&rdquo;</strong></p>\r\n<p style=\"text-indent: 2em\">最后我想谈一谈我的&ldquo;税制梦&rdquo;，就是基于我前面对正义的考虑。我觉得在现代网络化大数据的时代，可以构想出一个简单透明的制度。就是在国家统一构建一个集中信息处理系统，任何一个单位、个人都在这个系统里面联起来，都有唯一的账号，我觉得在技术上是可以实现的。</p>\r\n<p style=\"text-indent: 2em\">有了这个东西之后，这个账户跟所有的金融机构联网，还要考虑和所有的物流系统联网。联起来以后，征税就会变得非常简单。我们每个人在银行里面都开户，只要有一笔钱进到任何一家银行，那个统一系统的账户里面就可以反映出来。征税方式就是系统自动扣缴，这个时候就不需要税务局了，因为系统可以自动实现把你的税划转到国库。这样就不需要企业交税了，所有的税都可以让个人去交。只要个人有收入，银行系统就自动给你划转。只要你有消费，消费的时候划卡，自动跟你的收款机联起来了，系统里面就自动显示出你买了东西。这样就不用带任何的现金，都是通过网络来实现。我觉得技术上应该是可以实现的，在我的构想当中，这个事情好像很简单。如果这样做的话，你连贪污都不可以了，因为都透明了。</p>\r\n<p style=\"text-indent: 2em\">如果这个构想可以实现，第一，不需要税务局；第二，不需要申报；第三，中介机构不需要有了，逃避税的空间狭窄了。这都是一种构想，不过我认为它不是一个空想，不是一个乌托邦的空想，因为它有技术的可行性支撑。以往很多的构想实现不了，因为它没有技术的条件。我这个设想只是基于现有的基础条件，说不定将来全球一统，没有国家的差别也有可能。我认为这些挑战主要问题还不是技术方面，主要问题是制度、法律、文化、观念、利益阻挠等。这种变革一定是有一个很长的过程，但是不管怎么说，有了这种大方向和大目标的引导，我们就可以通过代代相传的努力来实现它。有了方向的引导，我们才知道那才是我们应该去的地方。所以希望我的税制梦不是梦，希望它成为一个现实。</p>\r\n<p style=\"text-indent: 2em\"><strong>嘉宾简介：</strong></p>\r\n<p style=\"text-indent: 2em\">朱为群，上海财经大学公共经济与管理学院教授、税收系主任、税收学博士生导师，兼任中国税务学会理事、中国国际税收研究会理事、中国财税法研究会理事、全国财政学教学研究会常务理事等职。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (500,'<p style=\"text-align: center\"><img src=\"http://t2.qpic.cn/mblogpic/26276de504f16f64b6f0/2000\" /></p>\r\n<p>&nbsp;</p>\r\n<p>几千年来，谣言不绝如缕。</p>\r\n<p>在古代，因为交通、通讯、传媒、人口等限制，谣言传播的速度与规模不能与近世相提并论。1768年（清乾隆三十三年），一则关于妖术的谣言竟迅速演化成一场全国性的大恐慌，影响波及数千万人，孔飞力在其名著《叫魂&mdash;&mdash;1768年中国妖术大恐慌》中对之作出了精彩叙述与分析。</p>\r\n<p>乾隆帝残酷查处了&ldquo;叫魂案&rdquo;，一颗颗人头像韭菜一样被割掉，并且不可能再长出来。乾隆帝恐惧的并非谣言本身，而是谣言导致的社会集体心理波动与行为变异，更重要的是，通过对谣言案的深入观察，乾隆帝发现自己很可能已经在常规领域失去对官僚的有效控制。上述种种，都对这个异族政权的合法性和稳定性形成威胁，统治者必须严肃应对。</p>\r\n<p>乾隆朝是所谓清代最后一个盛世，嘉道之后，乱世来临，大规模谣言泉涌而出。</p>\r\n<p>在太平天国与清军的对战中，双方都毫不客气地使用了谣言这种非常规武器。洪秀全散播谣言说：&ldquo;予细查满鞑子之始末，其祖宗乃一白狐、一赤狗，交媾成精，遂产妖人，种类日滋，自相配合，并无人伦风化&rdquo;&ldquo;前伪妖康熙暗令鞑子一人管十家，淫乱中国之女子，是欲中国之人尽为胡种也。&rdquo;</p>\r\n<p>清军也立即展开谣言反击战，散播说：&ldquo;（太平军）所过之境，船只无论大小，人民无论贫富，一概抢掠罄尽，寸草不留&rdquo;、&ldquo;其虏入贼中者，剥取衣服，搜括银钱，银满五两而不献贼者，即行斩首。&rdquo;据说曾国藩还炮制了民谣，派人四处传说：&ldquo;天父杀天兄，江山打不通。长毛非正主，依旧让咸丰。&rdquo;</p>\r\n<p>我们当然不能将曾国藩的胜利归于造谣术，但如果他未能有效化解对方的谣言战术，并在己方谣言之上制造并传播新的意识形态（其根基就在《讨粤匪檄》一文），可能胜利会来得更晚。</p>\r\n<p>谣言本身或无法改变历史，但很可能成为历史巨变中的一个关键因素。社会运动有时不需要真相，一个谣传引发的骚动，也可能改变历史。很多人内心追求的未必是真相，而是一场巨变。</p>\r\n<p>1911年10月9日下午，革命党人在汉口不慎引爆炸药。大约同时，&ldquo;清政府正在捕杀革命党人&rdquo;的谣言，于新军中广传。到10月10日，这个谣言更加具体了&mdash;&mdash;&ldquo;清政府正在捉拿没有辫子的革命党人&rdquo;&ldquo;官员已经掌握革命党人的花名册&rdquo;。</p>\r\n<p>当时的新军士兵，不少人都没有辫子，传说中的花名册又都没见过，谁知道自己在不在其中呢？恐惧在新军中蔓延，恐惧滋生新的谣言，新的谣言反过来又加深恐惧。这时候，参加兵变就成了多数士兵自保的最优选择。10日薄暮，一个排长查哨时的普通纠纷，竟激成哗变，最终引发连锁反应，导致辛亥鼎革。</p>\r\n<p>谣言为何能轻易地使人接受并参与传播？1942年美国两位学者做了一个谣言传播与接受的研究，计算出一个&ldquo;信谣指数&rdquo;，结果发现：穷人比富人更易信谣，45岁以上的人比年轻人更易信谣，犹太人比非犹太人更易信谣。</p>\r\n<p>穷人更易信谣是因为他们渴望改变现状；45以上的人更易信谣是因为他们的信息渠道与信息分析能力相对落后；犹太人更易信谣则是因为在战时，犹太人比一般人更缺乏安全感，而当时的谣言往往又是让人恐惧的那种。</p>\r\n<p>法国学者让-诺埃尔《谣言&mdash;&mdash;世界最古老的传媒》一书，对谣言有新颖而独到的见解。他认为，谣言经常是&ldquo;真实的&rdquo;，它之所以令人不舒服，是因为权力无法控制这种信息。</p>\r\n<p>在任何一个地区，当人们希望了解某事而得不到官方答复时，谣言便会甚嚣尘上。谣言是信息的黑市。辟谣往往制止不了谣言，因为谣言不是福尔摩斯，对真相充满感情，谣言是聚集着仇恨的女巫，它只说出人们认为应该如此的&ldquo;事实&rdquo;。人们看上去是在传播新的谣言，实际上是在清算旧账。辟谣注定是无力的，因为辟谣会破除人们的幻想，给狂热者当头浇一盆雪水，唤他们回到平庸的现实中来，爱做白日梦的人们当然不肯买账！</p>\r\n<p>谣言既是社会现象，也是政治现象，它是一种反权力，揭露秘密，提出假设，迫使当局开口说话。谣言还是社会群体心理结构的镜子，因此不论真假，谣言都是有价值的。</p>\r\n<p>在我看来，谣言不但是社会的、政治的，也是历史的。谣言不但可能成为历史事变的导火线，并且可能成为历史事变的解说者。历史中充斥着太多谣言，有些被当场击毙，有些则轻松逃脱，在漫长的时间河流中演变成都市传奇或历史神话。</p>\r\n<p>都市传奇是谣言的连续剧。譬如针刺狂的谣言，1922年在法国巴黎一度盛行，80多年后在中国大陆又化身&ldquo;艾滋针刺狂&rdquo;的传说不胫而走。</p>\r\n<p>神话则是谣言的终极形式。譬如义和团运动，在1901-1920期间曾被认为是愚昧、迷信、野蛮的神话；在1924-1937却被认为是饱含民族自尊与抗击热情的反帝国主义的正义神话；在文革期间更被指认为反封建、反帝国主义的伟大群众运动的神话，且间接为红卫兵哺乳；上世纪80年代以降，它又重返愚昧、野蛮、疯狂的神话，只是不时仍蒙着一层爱国主义的遮羞布。</p>\r\n<p>那么，历史真相究竟如何？然而，&ldquo;绝对客观的历史真相&rdquo;，本身也许就是一个最大的历史神话。</p>\r\n<p>【笔者按：据京华时报报道，昨天全国公安机关集中打击网络有组织制造传播谣言等犯罪专项行动拉开序幕。秦火火、立二拆四等推手被北京警方刑拘，其网络推手公司被查。】</p>\r\n<p>附，《叫魂&mdash;&mdash;1768年中国妖术大恐慌》《谣言&mdash;&mdash;世界最古老的传媒》相关信息：</p>\r\n<p>（1）《叫魂&mdash;&mdash;1768年中国妖术大恐慌》</p>\r\n<p>作者：（美）孔飞力；译者：陈兼/刘昶；出版社：生活&middot;读书&middot;新知三联书店；出版年：2012-5；ISBN：9787108037909</p>\r\n<p>（2）《谣言&mdash;&mdash;世界最古老的传媒》</p>\r\n<p>作者：（法）让-诺埃尔&middot;卡普费雷；译者：郑若麟；出版社：上海人民出版社；出版年：2008-12；ISBN：9787208076495</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (501,'<p style=\"text-indent: 2em\"><strong>【编者按】</strong>二战战败，日本在屈辱和不情愿中，被迫接受了麦克阿瑟的改革。麦克阿瑟通过修改宪法、财阀解体、土地改革等一连串的政策，使满目疮痍的日本迅速崛起。麦克阿瑟在日本到底做了什么？8月14日下午北京涵芬楼书店，旅日作家俞天任先生做客燕山大讲堂发表题为&ldquo;谁在统治日本&rdquo;的演讲，以下为文字实录：</p>\r\n<p style=\"text-indent: 2em\">1945年8月30日道格拉斯&middot;麦克阿瑟五星上将乘坐的&ldquo;巴丹号&rdquo;降落在神奈川县厚木海军飞机场，那时候日本并没有解除武装，他心里没有底，而且在太平洋战争中见识过喊着&ldquo;天皇陛下万岁&rdquo;，冒着枪林弹雨不知道&ldquo;死&rdquo;是什么往上冲的日本兵，虽然美国胜利了，但日本军队那种疯狂对麦克阿瑟来讲是恐怖的。所以于麦克阿瑟而言最重要的是不再让一个疯狂的日本出现。</p>\r\n<p style=\"text-indent: 2em\">他通过审判战犯、修改宪法、财阀解体、土地改革一连串的政策，使日本成为一个和平的国家。现在的日本，不管是言论还是举动，作为一个国家来讲，在这六十几年没干过什么坏事，一个军人把一个国家弄成这样，这是一个奇迹。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟让占领军中的一些战前职业为律师的年轻军人们起草了一部&ldquo;和平宪法&rdquo;，这部宪法所反映的，实际上就是年轻人对于国家存在方式的政治理想。最后在一定程度上，麦克阿瑟还使用了刺刀，逼着日本人接受了这部宪法，来代替俗称为&ldquo;明治宪法&rdquo;的那部《大日本帝国宪法》。宪法只是描述了麦克阿瑟脑海里日本社会应有的形象，要达到这个目的，还得进行具体的工作，麦克阿瑟改造日本的过程，几乎就是分别对这六个权力集团采取不同的行动以瓦解或消除他们力量的过程。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟采取的第一个行动就是解除日本的军事武装，这是在《波茨坦公告》中已经明言了的条件，因此麦克阿瑟没有遇到任何军事抵抗。根据联合国占领军总部的命令，日本帝国大本营于1945年9月13日废止，其中枢部分的参谋本部和军令部于10月30日正式消失，陆军省和海军省也于12月1日被撤销。日本军部，这半个世纪以来，亚洲和太平洋地区人的噩梦，终于退出了历史舞台。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟对于宫廷集团所采取的行动是让日本天皇自己发布所谓&ldquo;人间宣言&rdquo;，从&ldquo;现人神&rdquo;的位置上走下来，这样宫廷集团就失去了赖以存在的理由，而且日后也不能再有人来使用&ldquo;效忠天皇&rdquo;的口号蛊惑人心，煽动骚乱。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟当然知道以三菱、三井、住友、安田这四大财阀为首的日本财阀集团，在日本军阀对外扩张的行动中所起的重要作用，也知道他们实际上控制着很大一部分国家权力。麦克阿瑟采取的对策是以占领军的强大武力为后盾，进行&ldquo;财阀解体&rdquo;的行动，把这些庞大复杂的康采恩和辛迪加，分割成了一个个再也无力影响国策的普通企业。</p>\r\n<p style=\"text-indent: 2em\">战前日本农村的土地兼并现象非常严重，由此而产生的无地农民问题，成了日本向外扩张的借口。为了解决这个问题，麦克阿瑟毫无顾忌地从日本的地主那里把土地抢了过来，分给无地的农民们以安定农村局势。</p>\r\n<p style=\"text-indent: 2em\">通过释放政治犯，举行民主选举，鼓励成立工会，推进妇女参政等手段，麦克阿瑟在日本一边推广美国式民主自由的思想，建立新的政党政治，同时也采取将与原政权有关系的人开除公职的方法，摧毁了原来的政治结构。虽然这些做法中，有很多因为朝鲜半岛战争而中断或者发生了变化，但整个方向没有变。在日本一直有一个很有趣的猜想，就是如果没有朝鲜半岛战争的爆发，持坚决反共意识形态的麦克阿瑟，最终会把日本鼓捣成一个什么样的国家？</p>\r\n<p style=\"text-indent: 2em\">在军部、财阀、地主、政党、宫廷这些昔日的权力集团全部瓦解之后，麦克阿瑟准备如何处理日本帝国的官僚队伍呢？现在这个国家已经不再是&ldquo;天皇的神国&rdquo;了，麦克阿瑟教给了日本人一句&ldquo;主权在民&rdquo;的口号。但是和过去的天皇不可能自己去执掌权力一样，民众也不可能自身去直接使用权力，还得靠官僚来代表他们行使国家权力，但原来那支被称为&ldquo;天皇的官吏&rdquo;，为天皇服务的旧文官队伍现在能不能作为为国民服务的&ldquo;公仆&rdquo;（public servant，这也是个麦克阿瑟带来的新名词）而被继续使用呢？如果不能继续使用，已经搭起了&ldquo;民主日本&rdquo;框架的麦克阿瑟，又准备上哪儿去寻找实际运作这个国家的行政官员呢？</p>\r\n<p style=\"text-indent: 2em\">来看看占领军总司令部GHQ对 &ldquo;帝国高等文官&rdquo;们的评价。GHQ是这样评价他们的：&ldquo;这些官僚，也就是说高等文官的大部分出身于东京帝国大学法学部。他们在大学里接受了完整的法律训练，他们被教育为忠诚天皇和同僚的人，他们甚至受过如何谈判和讨价还价的教育。但是在东京帝国大学法学部的课程中行政学却是选修课目，而且几乎没有人选修这门课目，因为高等文官的考试中从来没有出现过行政学的试题。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">这就是麦克阿瑟对他们的评价，麦克阿瑟知道他们忠君的政治倾向，但麦克阿瑟并不认为这是很大的问题。麦克阿瑟直截了当地说：&ldquo;世界上任何国家的官僚集团的本能都是替付工资的人工作。&rdquo;可能对某个官僚来说存在着意识形态的问题，但对于整个官僚集团来说，&ldquo;有奶便是娘&rdquo;才是本能。何况麦克阿瑟除了依靠这支官僚队伍之外也没有别的选择，他在日本不可能找出来能够从事行政管理工作的其他人了，精英化的后果就是除了精英之外就没有了有资格的人。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (507,'<p style=\"text-align: center\"><img alt=\"广州又现“楼歪歪” 三栋居民楼倾斜(图)\" src=\"http://img1.gtimg.com/news/pics/hv1/45/129/1413/91913265.jpg\" /></p>\r\n<p>&nbsp;</p>\r\n<p align=\"center\" class=\"pictext\">居民楼倾斜现场图。 肖雄 摄</p>\r\n<p style=\"text-indent: 2em\">上月初，广州番禺区厦滘村一居民楼因地基下陷，房屋倾斜被拆除，5日晚至今，当地又连续有三栋居民楼发生倾斜。目前，周边十余栋受影响的房屋村民已经全部疏散，当地街道办开放临时安置点接纳受影响群众，而疑似事故元凶的附近工地已停止施工。</p>\r\n<p style=\"text-indent: 2em\">昨日（7日）上午，记者在厦滘村沙滘中路看到，14巷、16巷、18巷都已经被封锁，拉起了警戒线。在现场可以清楚地看到，16巷10号楼高六层，已经向东倾斜约15度，地面下沉约50厘米并裂开，14巷发生沉降的楼房分别有五六层高，楼底地面有多处开裂，其中14巷7号整体下沉明显，房子的大门被突起的地面抵住，已经无法完全打开，隔壁的14巷9号楼房墙角处的地面开裂非常明显，裂缝蜿蜒盘旋了很长，整栋房屋有被推高的趋势。</p>\r\n<p style=\"text-indent: 2em\">昨日下午，番禺洛浦街通报称，将继续全力做好厦滘村房屋下沉倾斜应急处置工作，重点做好排危、警戒及安置工作，继续开放临时安置点接纳受影响群众，并做好临时安置款的发放工作。</p>\r\n<p style=\"text-indent: 2em\">接连出现的沉降事故，让村民们惶恐不安，他们将造成这一切的矛头指向了旁边的&ldquo;厦滘商业大厦&rdquo;工地，怀疑是因为工地挖地基太深，造成了水土流失。&ldquo;厦滘商业大厦&rdquo;项目部的负责人在接受媒体采访时表示，这个项目是经过相关职能部门的审批，证件齐全，并不存在违法施工的行为，而且他们已经采取了相应的措施确保村民的安全。如果项目施工完成后，还会对受损的房屋作施工后的安全鉴定，届时如确定是开发商的责任他们将主动承担。（记者 胥柏波）</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (508,'<p style=\"text-align: center\"><img alt=\"海口酒行发生爆炸两人受伤 玻璃碎片炸飞50米外\" src=\"http://img1.gtimg.com/news/pics/hv1/240/127/1413/91912950.jpg\"/></p><p>&nbsp;</p><p style=\"text-indent: 2em\">【海口一酒行发生爆炸两人受伤】今天早上8点40分左右，海口市蓝天路一家酒行发生爆炸，两人受伤被送往医院。现场破坏严重，50米外能看到被炸飞的玻璃碎片，停在路边的多部车辆受损。目前警方已介入调查。（央视记者毛鑫陈龙）</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (509,'<p style=\"text-indent: 2em\">京华时报讯 由<span class=\"infoMblog\">人民出版社</span>出版的《朱镕基上海讲话实录》于8月12日向全国发行。昨天，人民出版社常务副社长任超做客中新网时介绍，该书首印110万册全部发出，并已取得65万册的销售佳绩。任超表示，该书出版过程中，朱镕基同志要求实事求是，如实呈现当时的一些情况。</p>\r\n<p style=\"text-indent: 2em\"><strong>首印110万册全发出</strong></p>\r\n<p style=\"text-indent: 2em\">任超透露，《朱镕基上海讲话实录》首印110万册全部批发出去。截至前天，实际销售已达65万册。</p>\r\n<p style=\"text-indent: 2em\">任超介绍，朱镕基退休后共出版三本书，均由人民出版社出版。其中，《朱镕基答记者问》2009年出版，首印25万册，发行当日再次加印，半个月后，印数达百万。而《朱镕基讲话实录》2011年出版，首印50万套，一个多月内售罄。</p>\r\n<p style=\"text-indent: 2em\">任超说，由于前两本书出版社对销量的估计不足，首印偏少，造成了一段时间市场脱销，此次为充分地满足读者的需求，所以做出了首印110万册的决定。</p>\r\n<p style=\"text-indent: 2em\"><strong>朱镕基要求尊重原貌</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>任超介绍，该书出版过程中，朱镕基非常注意听取大家意见，1000余本样书多送给中央领导同志、有关专家及当时和他在上海共事过的同事征求意见。而朱镕基同志本人则要求实事求是，如实呈现当时的一些情况。</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>任超透露，朱镕基同志对自己所写的文章都是认真地思考，每篇文章他都要过。他要求尊重原貌，如实呈现当时的一些情况，是怎么回事就怎么回事，实事求是。任超说，朱镕基曾说过，&ldquo;如果人家拿着我的录音和我书里面的内容对不上，这不好&rdquo;。</strong></p>\r\n<p style=\"text-indent: 2em\">任超介绍，他跟朱镕基身边的一个工作人员了解到一个小故事。这本书里面，最后一篇文章是关于街道工作的，有专家提出来，这篇文章可不可以不说了。但是朱镕基认为街道工作是社会管理工作的一个非常重要的基础性工作，这篇要发表，就把这一篇报道出来了。</p>\r\n<p style=\"text-indent: 2em\"><strong>领导人出书将增多</strong></p>\r\n<p style=\"text-indent: 2em\">近年来，我国多位退休领导人都不止一次出版了自己的专著。对此，任超表示，这是一个好事，表明更加开放，更加透明。同时，党和国家领导人处理问题的方法带有普遍性意义，读者可以从中悟到一些东西。&ldquo;过去我们对党中央的一些决策部署和过程不一定很了解，现在通过一些著述，我们可以比较清晰地看到一些决策的脉络。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">任超认为，今后领导人出书可能会越来越多，大家以后也会习以为常。</p>\r\n<p style=\"text-indent: 2em\">京华时报记者张然 综合中新社</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (512,'<p>&nbsp;</p>\r\n<p style=\"margin: 0px 0px 20px; padding: 0px; color: rgb(51, 51, 51); font-family: \'microsoft yahei\'; font-size: 16px; line-height: 30px; text-align: justify; \">9月4日下午，融创集团以21亿元地价款、22.24亿元配建异地医院的出价拿下备受瞩目的北京朝阳区农展馆北路8号0304-622地块，折算楼面地价高达7.3万元/平方米，再度刷新了北京单价地王记录。</p>\r\n<p style=\"margin: 0px 0px 20px; padding: 0px; color: rgb(51, 51, 51); font-family: \'microsoft yahei\'; font-size: 16px; line-height: 30px; text-align: justify; \">　　以此同时，在9月2日，中国指数研究院发布了《2013年8月中国房地产(行情专区)指数系统百城价格指数报告》，报告显示：2013年8月，全国100个城市(新建)住宅平均价格为10442元/平方米，环比7月上涨0.92%。自2012年6月以来连续第15个月环比上涨，涨幅比上月扩大0.05个百分点，其中71个城市环比上涨，29个城市环比下跌。在上涨的城市中，涨幅居前三位的是：福州（楼盘）、徐州（楼盘）、日照（楼盘），而在上涨前十名的城市中，北京是唯一一个一线城市入选的。由此看出，上涨幅度最大的是二、三线城市。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (559,'<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">【环球时报综合报道】日本国内也有人对安倍的发言提出批评。据时事通讯社报道，民主党干事长大畠章宏26日表示，安倍在联大上自称&ldquo;右翼军国主义者&rdquo;的说法是不恰当的，&ldquo;作为国家首脑不应该有这样的言论&rdquo;。而执政同盟公明党内也有干部表示，首相的发言&ldquo;非常欠斟酌&rdquo;，反而有损日本的国际形象。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　安倍所鼓吹的集体自卫权在日本国内更是面临巨大争议。《朝日新闻》8月份进行的民调显示，支持者只占27%，反对者高达59%。自民党的执政盟友公明党也一直对修改宪法持谨慎态度。韩国YTN电视台26日称，虽然安倍政权盘算着明年春天通过以宪法解释的名义将行使集体自卫权合法化，但最大绊脚石来自执政伙伴公明党和日本舆论。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　中国国际问题研究所所长曲星26日接受《环球时报》记者采访时表示，日本之所以被剥夺联合国宪章规定的集体自卫权，是因为它在历史上发动了侵略战争。现在日本的行为仍远远不能让世界人民放心。战争罪犯还在靖国神社像神一样被祭拜，安倍还说出&ldquo;不同的人对侵略有不同解释&rdquo;这样的话，日本政客还公开为慰安妇罪行辩护&hellip;&hellip;</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　&ldquo;日本集体自卫权是新冷战的前奏&rdquo;，韩国《中央日报》25日以此为题对纵容日本扩充武装的美国提出质疑。文章称，如今集体自卫权貌似已成为美日两国为牵制中国的必须选择。美国在二战中让日本投降，却在不到一个世纪的时间里再次让战犯国拿起枪，这真是历史的反讽。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　虽然面对众多反对和质疑，但安倍仍表现出一意孤行的态度。日本新闻网以&ldquo;安倍称自卫队会去地球背面&rdquo;为题说，<strong>安倍抵达纽约接受日本记者团采访时表示，恐怖活动在世界任何地方都会发生，为了世界的安定与和平，自卫队的活动不应该考虑地理因素限制。因此表明了自卫队将跟随美军到世界任何一个角落去行使集体自卫权，而不是像执政的自民党内部分议员提出的&ldquo;限定条件，限定区域&rdquo;的妥协方案。</strong></p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (560,'<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">中新社北京9月27日电 伊斯兰堡消息：巴基斯坦官方26日证实，发生在该国俾路支省的7.7级地震已造成至少355人遇难，另有近700人受伤，数百间房屋坍塌。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　巴基斯坦国家灾害管理局26日发布的声明说，救援人员仍在努力搜救幸存者。有消息说地震实际遇难人数将超过500人。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　据美联社报道，此次地震震中的阿瓦兰是巴基斯坦最贫穷的地区之一，地震摧毁当地90%以上的房屋，受灾居民无家可归，只能用木棍和床单搭建临时避难所过夜。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　地震发生时，家住阿瓦兰的努尔&middot;艾哈迈德正在工作，当他赶回家里时，发现房子已经被夷为平地，妻子和儿子也在地震中遇难，&ldquo;我已经一无所有了&rdquo;，他对媒体记者说。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　卡塔尔半岛电视台网站26日消息说，灾区目前急缺食物、水、帐篷等必需品，由于缺乏药品，前往灾区的医生无法开展及时救治，只能先做当地居民的心理安抚工作。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　&ldquo;我们需要更多帐篷、更多药品和更多食物&rdquo;，俾路支省发言人穆罕默德说，他透露，已有2000包帐篷和食物送达灾区。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　在其他地理位置偏远、基础设施不健全的受灾区域，救援工作很难开展，灾民只能在废墟中翻找食物果腹。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　巴基斯坦军方消息说，已有60辆军车于当地时间25日运载一批救援人员从卡拉奇出发前往灾区，军方还连夜派出1000人的部队和直升机抵达受灾地区。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　英国广播公司消息说，巴基斯坦政府称他们有能力处理这一灾难，目前尚未请求国际援助。(完)</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (561,'<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">当一项规则得到民众首肯并有十分规整的条例、到位的监督力量来照章执行，确保&ldquo;地铁禁食令&rdquo;落地生根时，相关法规才能逐渐转化为公众的自觉自律行为。&nbsp;&nbsp;&nbsp;&nbsp;</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　24日，杭州地铁一号线的九堡站站台上两名女子上演了一场追逐大战。原来一位孕妇在车厢内见一名中年妇女不停地吃东西上前劝说，但对方不听，于是她拿起手机拍照想在网上曝光，结果被对方愤怒追打。这与此前武汉地铁&ldquo;热干面&rdquo;事件如出一辙，拍照曝光是否妥当再次引起网民热议。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　前有武汉泼面姐，后有杭州追打女，关于该不该在地铁上吃东西，该如何劝阻，又一次引发轩然大波。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　吃完饼干吃葡萄，不听劝阻，被拍后恼羞成怒，追打别人，肯定是缺乏修养的野蛮行为；而孕妇在当众劝阻之时，还顺便开了句玩笑，&ldquo;小心等会儿被人打&rdquo;，加上偷拍曝光，未必不会引起对方的逆反心理，也就是说，在这场打斗中，双方都有可待商榷之处。这也给大众提出一个问题：个人的守规意识与法规执行之间、现实生活中的个人自由与公共监督之间往往存在激烈矛盾。比如，在人们意识中，禁食令主要是针对那些味道&ldquo;冲&rdquo;的食品，那么饼干、葡萄、水是否当禁？谁来界定？这禁令到底该由谁来执行？谁来监督？为什么乘客不及时向相关人员报告，或者说地铁监管部门为何不能及时出面，制止纠纷？正是因为缺少有力、到位、明晰的执行条件，地铁禁食及公共规范不由自主地演变成私人恩怨，甚至可能造成人身伤害。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　因此，当地方法规不能单纯依靠个人道德自律和舆论谴责时，就需要客观中立、强有力的第三方约束力量来加以规范。听取大多数民意，细化相关法则，扩大宣传，强化规则意识，让地铁禁食令入脑入心。当一项规则得到民众首肯并有十分规整的条例、到位的监督力量来照章执行，确保&ldquo;地铁禁食令&rdquo;落地生根时，相关法规才能逐渐转化为公众的自觉自律行为。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　同时，我们也要加强公共交通的人性化服务，除了微笑告知、广贴标语、循环播报禁食令外，还不妨在禁食区入口处设立进食区，提醒和帮助脚步匆匆的乘客处理不慎带入的食品，这样，犯规的人就会少一些，关于此类小事的纠葛或戾气也会少一些，市民素养和城市文明也会更上一个台阶。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (562,'<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">&ldquo;我自我检查主要是三条：第一条就是有点急于求成，急于求变心切，一切从人民利益出发的政绩观树得不牢。第二个问题，有时候有些主观决策，知人不深，一切从实际出发的思想路线树立坚持不够。第三个问题，主要是斗志有些松懈，做工不够，缺乏那种拼命苦干实干劲头，拼命劲头在消减。&rdquo;&ldquo;我爱批评人，自己感觉主意正，感觉办法多，就把自己摆在了干部群众之上，奢靡之风我觉得还是有的。比如说，在八项规定之前我们也搞了很多活动，这些活动讲排场，摆好的宴席。&rdquo;做这个自我批评的，不是一般党员干部，而是河北省两位党政主要领导人&mdash;&mdash;省委书记周本顺和省长张庆伟。&nbsp;&nbsp;&nbsp;&nbsp;</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　近日，在河北省委常委党的群众路线教育实践活动专题民主生活会上，人们看到中共久违的批评与自我批评重又出现。党的总书记习近平也专程用四个半天参加了这个民主生活会，并就&ldquo;批评与自我批评&rdquo;对于改进党的作风的重要意义做了讲话，强调批评和自我批评是解决党内矛盾的有力武器，全党同志特别是各级领导干部要本着对自己、对同志、对班子、对党高度负责的精神，大胆使用、经常使用这个武器，切实提高领导班子发现和解决自身问题的能力。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　&ldquo;批评与自我批评&rdquo;是中共在长期的革命斗争中创造的一种解决党员干部工作作风的方式，被誉为共产党的三大优良作风之一。所谓批评，是指对别人的缺点或错误提出意见；所谓自我批评，是指党组织或个人对自己的缺点或错误进行的自我揭露和剖析。从延安整风起，60多年来，&ldquo;批评与自我批评&rdquo;对帮助共产党保持和发扬优良传统和作风，警惕党内出现的各种错误倾向和不正之风，巩固和发展党的团结和统一，起到了一定或者说很大作用。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　不过，不知从什么时候起，&ldquo;批评与自我批评&rdquo;这个共产党的&ldquo;得力武器&rdquo;，在很多领导干部那儿丢失了，虽然党的文件还照样强调要贯彻和发扬&ldquo;批评与自我批评&rdquo;的优良传统，但实际上，真正能够做到批评和自我批评的领导干部极其稀少。大家都是你好我好他好，愿意做&ldquo;好好先生&rdquo;，无论是批评 别人还是自我批评，都言不由衷，怕得罪别人，也怕被别人抓到&ldquo;小辫子&rdquo;，总之，是一团和气。而党风、政风和社会风气也就在这&ldquo;一团和气&rdquo;中遭受破坏。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　因此，在这一优良作风正在消失之当前，此次群众路线教育实践活动重提&ldquo;批评与自我批评&rdquo;，有助于教育实践活动不流于形式和走过场。从新闻报道来看，河北省委常委在班子民主生活会上，在检讨自己工作中存在的不足，以及批评别的常委违反&ldquo;四风&rdquo;要求情况时，还是很认真，触及到了一些问题的，不那么避实求虚。如省委书记周本顺批评秦皇岛市委书记田向利急于求成，急于证明自己，急于让领导认可，而纪委书记臧胜业则批评省长张庆伟听汇报时没耐心，不够尊重干部。这种批评在平时很难听到，尤其是下级批评上级，几乎是没有的。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　但是，在肯定党重拾&ldquo;批评与自我批评&rdquo;这一&ldquo;武器&rdquo;的同时，又必须谨防地方把它作为一阵&ldquo;风&rdquo;来对待，也就是说，只是在教育实践活动时期出于贯彻中央的要求而不得不这样做，整风活动过去后，&ldquo;批评与自我批评&rdquo;又成为文件里的用语。倘若如此，&ldquo;批评与自我批评&rdquo;的效果也就只能管一时。要避免这种情况的出现，我们需要思考三个问题：一，在目前时代，&ldquo;批评与自我批评&rdquo;作为一种解决党的战斗力的方法是否还具可行性；二，如果可行，如何在党和政府的日常工作中保证它得到贯彻落实；三，有没有一种办法，使得&ldquo;批评与自我批评&rdquo;成为党员干部的自觉要求。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　目前看来，尽管时代条件发生变化，但&ldquo;批评与自我批评&rdquo;作为党员干部自我教育和改进工作作风的工具价值并未失去，尤其是在整党这种特定的情况下。当然，要使它得到贯彻落实，成为党员干部的自觉要求，需要制度来保障，它包括这样一些内容：首先，对领导干部提意见和揭露出来的问题，不能抓小辫子，搞打击报复，进行&ldquo;秋后算账&rdquo;；其次，对党委领导班子的民主生活会，要确立议事规则，所有领导班子在生活会上的批评权都是一样的，没有大小之分，批评内容必须在单位或社会公布，真正使生活会成为一个&ldquo;民主&rdquo;的生活会；第三，最重要的是，约束和分解一把手的权力，一把手权力过大，是不可能有真正的&ldquo;批评与自我批评&rdquo;的。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　党应该用制度化和民主化来激活这一被休眠的&ldquo;武器&rdquo;，使&ldquo;批评与自我批评&rdquo;起到长久监督领导干部的作用。</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (563,'<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　近来，北京等大城市都在想狠招恢复蓝天白云，交通污染再度成为众矢之的。各种各样的传闻纷至沓来：减少摇号中签率，实行单双号限行，征收拥堵费。然而，在众多治堵的倡议中，却没有人提到自行车出行，实在不应该，也实在对不住自行车王国的优良传统。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　现在提议恢复自行车路权具有紧迫性。在绿色出行的大环境下，都市内短距离出行选择自行车，最环保、经济和便捷。然而，大部分发达国家已经积重难返，想要恢复自行车路权代价非常高。早在半个世纪前，西方发达国家伴随着汽车化，已经在法理上取消了自行车路权，所有都市道路都划成汽车道。道路设施具有一定的稳定性，一旦规划建设完成，重新改造的成本会相当高。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　相比之下，中国大都市的路权之争正处在十字路口。过去30年，北京的道路规划是一个车进人退的过程。汽车占用的道路越来越宽，自行车的路越走越窄。但是，法理上北京的自行车仍然有自己的路权，道路规划中仍有自行车道。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　但事实上，自行车的路权已经名存实亡。多数自行车道可以随意被划为机动车停车区域，可以随意被汽车占道。在这个十字路口，再往前走一步，就会步发达国家后尘；往后退一步，则恢复自行车路权，使自行车变成最安全、高效、环保和经济的出行方式。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　现在提议恢复自行车路权，具有可行性。其一，公民的环保意识、环保责任感已经比较成熟，自行车出行已成为一种正义选择。只要公权力能够提供合适的环境和途径，只要能保证自行车出行的安全性和便捷性，大部分公民还是愿意为北京的环保作贡献。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　其二，汽车出行的成本快速攀高，自行车出行已成为一种经济的选择。现在北京摇号中签率是130∶1，油价、停车费冷酷上升，养车的成本越来越高。大部分人养车是为了出远门。短距离出行，自行车的成本最合算。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　其三，道路越来越堵，自行车出行已成为一种效率的选择。过去几年，治堵措施的落实总是赶不上汽车保有量的增加，上班族耗在路上的时间成倍增长。越是堵车，自行车方便快速的优点越明显。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　其四，随着汽车越来越普及，人们的面子观已经发生变化，自行车出行已成为一种现代思想的选择。过去，汽车是一种身份地位的象征，自行车是穷人、失败者的标志。现在，汽车日趋普及，象征身份地位的功能下降，而骑自行车作为一种健康、环保的活动，反而变得时髦起来。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　恢复自行车路权看起来是一件小事，实际上体现出在解决现代化难题时，政府要考虑舒适性与环境承受力的平衡，要保护弱势群体和弱势出行者的权益。在涉及环保等公共权益问题时，不能随任市场做主，政府公权力要通过制度发挥主导作用。当年，自行车王国是落后的象征；如今，自行车王国是现代的旗帜。▲(作者是中国现代国际关系研究院中东研究所所长)</p>\r\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (564,'<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">近年来，在我国可能没有哪个理论术语或概念比&ldquo;新自由主义&rdquo;更让人感到扑朔迷离、甚至混乱不堪的了。之所以如此，既与新自由主义所涉及的内容庞杂有关，也与人们对新自由主义研究不深、归纳不准有关。其实，只要仔细读一读其代表人物的主要作品就不难发现，人们谈论的新自由主义既非一个典型的经济学范畴，也非一个确切的经济学流派，而是一个经济理论、社会思潮和政策主张的&ldquo;混合体&rdquo;，或者用更通俗的话说是一个&ldquo;大杂烩&rdquo;。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　首先，人们通常所谈论的新自由主义，是一个以新自由主义经济理论为主要载体而存在和演进的思想范畴。翻阅一下新自由主义主要代表人物、特别是狭义新自由主义代表人物哈耶克、弗里德曼等人的主要论著便可发现，他们不仅在很大程度上接受和继承了古典经济学和新古典经济学，而且在很多问题上做出了新的解释和正确发挥。例如，哈耶克关于商业循环和货币、信用政策效应的分析以及弗里德曼关于货币理论的阐述等，起到了为现代经济理论建设添砖加瓦、促进经济理论研究深化的作用，他们也因此先后获得诺贝尔经济学奖。然而，他们的很多理论和政策主张、特别是&ldquo;去政府&rdquo;论却充满了不当甚至谬误。归纳起来，他们的&ldquo;去政府&rdquo;论主要包括三个方面内容。一是小政府或不要政府论。哈耶克一直反对政府干预经济。他认为，任何形式的中央集权都是幼稚和有害的，追求计划经济必然导致极权主义。他主张把包括货币发行权在内的市场权力统统归还给参与市场竞争的私人。不过，他的这些理论观点和政策主张直到20世纪70年代&ldquo;滞胀&rdquo;发生后，随着凯恩斯主义的消退，才获得广泛重视。特别是他于1974年获得诺贝尔经济学奖后，在美国，他甚至成了&ldquo;公民拥有充分自由权&rdquo;运动的领袖；在英国，保守的撒切尔夫人也自称是哈耶克的信徒。弗里德曼则从现代货币理论角度，论证和阐明了关于政府干预经济活动会加剧经济波动的观点，并据此提出政府不必干预经济的理论主张。二是私有产权有效论。以科斯为代表的产权理论提出并分析了产权、特别是私有产权对于交易边界的确定及其对于降低交易成本、提升市场效率的意义。三是公共物品供给市场化论。与主流经济学关于政府提供公共物品的理论逻辑不同，弗里德曼等人着力分析和阐述了公共物品和服务(包括教育、公共卫生、社会保险等)市场化的理论观点和政策主张。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　其次，新自由主义又常常以具有特定政治诉求的社会思潮形式表现出来。新自由主义思潮是一种以&ldquo;凯恩斯革命的反革命&rdquo;为主要特征、以夸大的形式把原本具有一定学术价值的经济理论推向市场极端的社会思潮。新自由主义思潮具有多种存在形态，其中，最具典型意义的当推盛行一时的市场万能论。其突出特点是宣扬和鼓吹&ldquo;市场是最有效的资源配置机制&rdquo;，片面夸大市场自修正和自复衡功能，否定政府干预对于弥补市场缺陷、克服市场失灵的积极作用，甚至认为除了维护法制和社会秩序以外的任何形式的政府干预都有损于市场效率及市场的健康运行。哈耶克作为新自由主义思潮的代表人物，早在19世纪40年代初就著书立说，把集体主义和社会主义视为&ldquo;通往奴役之路&rdquo;。他曾不遗余力地宣扬&ldquo;自由主义只关注交换正义，而不关注所谓的分配正义或现在更为盛行的&lsquo;社会&rsquo;正义&rdquo;，甚至声称&ldquo;坚定的自由主义者&hellip;&hellip;必须拒斥分配正义&rdquo;。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　最后，新自由主义还是以具有特定目标指向的政策主张形式存在和发展的。新自由主义政策主张的典型表现形式是&ldquo;华盛顿共识&rdquo;。1989年，由美国国际经济研究所牵头，在美国财政部的支持下，在华盛顿召开了一次旨在解决拉美国家经济衰退的国际研讨会。会后，美国国际经济研究所高级研究员约翰&middot;威廉姆森将会议取得的收获进行了总结和概括，并称之为&ldquo;华盛顿共识&rdquo;。华盛顿共识主要由三大方面内容组成：一是3项改革措施，主要包括加强财经纪律，把政府开支的重点转向经济效益高的领域和有利于改善收入分配的文教卫生和基础设施建设领域，开展包括降低边际税率和扩大税基的改革等；二是4项市场开放原则，主要包括实行利率自由化和更具竞争性的汇率制度以及贸易自由化和放松外国直接投资限制等；三是3项&ldquo;去政府&rdquo;干预要求，主要包括国有企业私有化、放松进入和退出的政府管制以及有效保护私人财产权等。这10个方面的政策主张虽然不乏合理因素，但矛盾和问题同样十分突出，特别是其中的4项市场开放原则和3项&ldquo;去政府&rdquo;干预要求，更是严重脱离了拉美国家的实际，着实误导了这些国家的发展，甚至进一步加深了这些国家的危机。华盛顿共识不仅集中体现了新自由主义思潮的基本要求，而且集中反映了以哈耶克和弗里德曼为代表的&ldquo;狭义新自由主义&rdquo;的政治主张。</p>\r\n<p style=\"margin: 15px 0px; padding: 0px; font-size: 14px; line-height: 23px; color: rgb(51, 51, 51); font-family: 宋体;\">　　一般来说，理论总是通过对实践或事物发展过程的系统观察、分析和研究，借助于特定理论范畴进行抽象和概括形成的能够表明事物内在联系的系统的思想和观点。而思潮通常是特定社会群体对特定理论产生思想共鸣后加以引申、夸大(有时也有误读)而形成的一种影响面较大、传播较快的思想潮、意识流或倾向。特定思潮的形成往往与特定社会群体的利益诉求及心理要求密切相关。因此，与理论相比，思潮更具漫画化特征和形式易变性、界限模糊性、传播迅速性和影响广泛性等特点。至于政策主张，则具有指向明确、要求清晰、强调实践等特点。一定的政策主张是在特定环境下由特定理论和思潮推动形成的特定要求或诉求。新自由主义作为经济理论、社会思潮和政策主张的混合体，带有更大的理论欺骗性和社会影响力。不管新自由主义者所阐述的经济理论有多少合理成分，我们都要高度警惕新自由主义思潮可能带来的社会影响，而且要特别注意防止新自由主义政策主张可能造成的社会危害。▲(作者是中国社会科学院民营经济研究中心主任、研究员、博士生导师)</p>\r\n',NULL,NULL,NULL);
DROP TABLE IF EXISTS `jc_content_type`;
CREATE TABLE `jc_content_type` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(20) NOT NULL COMMENT '名称',
  `img_width` int(11) default NULL COMMENT '图片宽',
  `img_height` int(11) default NULL COMMENT '图片高',
  `has_image` tinyint(1) NOT NULL default '0' COMMENT '是否有图片',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  PRIMARY KEY  (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容类型表';

INSERT INTO `jc_content_type` VALUES (1,'普通',100,100,0,0);
INSERT INTO `jc_content_type` VALUES (2,'图文',143,98,1,0);
INSERT INTO `jc_content_type` VALUES (3,'焦点',280,200,1,0);
INSERT INTO `jc_content_type` VALUES (4,'头条',0,0,0,0);
DROP TABLE IF EXISTS `jc_contenttag`;
CREATE TABLE `jc_contenttag` (
  `content_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL,
  KEY `fk_jc_content_tag` (`tag_id`),
  KEY `fk_jc_tag_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容标签关联表';

DROP TABLE IF EXISTS `jc_dictionary`;
CREATE TABLE `jc_dictionary` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL COMMENT 'name',
  `value` varchar(255) NOT NULL COMMENT 'value',
  `type` varchar(255) NOT NULL COMMENT 'type',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='字典表';

INSERT INTO `jc_dictionary` VALUES (1,'10-20人','10-20人','scale');
INSERT INTO `jc_dictionary` VALUES (2,'20-50人','20-50人','scale');
INSERT INTO `jc_dictionary` VALUES (3,'50-10人','50-10人','scale');
INSERT INTO `jc_dictionary` VALUES (4,'100人以上','100人以上','scale');
INSERT INTO `jc_dictionary` VALUES (5,'私企','私企','nature');
INSERT INTO `jc_dictionary` VALUES (6,'股份制','股份制','nature');
INSERT INTO `jc_dictionary` VALUES (7,'国企','国企','nature');
INSERT INTO `jc_dictionary` VALUES (8,'互联网','互联网','industry');
INSERT INTO `jc_dictionary` VALUES (9,'房地产','房地产','industry');
INSERT INTO `jc_dictionary` VALUES (10,'加工制造','加工制造','industry');
INSERT INTO `jc_dictionary` VALUES (11,'服务行业','服务行业','industry');
DROP TABLE IF EXISTS `jc_directive_tpl`;
CREATE TABLE `jc_directive_tpl` (
  `tpl_id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL COMMENT '标签名称',
  `description` varchar(1000) default NULL COMMENT '标签描述',
  `code` text COMMENT '标签代码',
  `user_id` int(11) NOT NULL COMMENT '标签创建者',
  PRIMARY KEY  (`tpl_id`),
  KEY `fk_jc_directive_tpl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FComment';

DROP TABLE IF EXISTS `jc_file`;
CREATE TABLE `jc_file` (
  `file_path` varchar(255) NOT NULL default '' COMMENT '文件路径',
  `file_name` varchar(255) default '' COMMENT '文件名字',
  `file_isvalid` tinyint(1) NOT NULL default '0' COMMENT '是否有效',
  `content_id` int(11) default NULL COMMENT '内容id',
  PRIMARY KEY  (`file_path`),
  KEY `fk_jc_file_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `jc_file` VALUES ('/u/cms/www/09093304hc6x.jpg','09093304hc6x.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09093632txyy.jpg','09093632txyy.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/090938248x1c.jpg','090938248x1c.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/090940173zaj.jpg','090940173zaj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09094140f562.jpg','09094140f562.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09094302sldg.jpg','09094302sldg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09094430w0xt.jpg','09094430w0xt.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09151507n6i1.jpg','09151507n6i1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09152518pzoq.jpg','09152518pzoq.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09152931cgps.jpg','09152931cgps.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/091552426die.jpg','091552426die.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/0915541140xt.jpg','0915541140xt.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09160120meel.jpg','09160120meel.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/091602465aop.jpg','091602465aop.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09165026tk5e.jpg','09165026tk5e.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/091655240woj.jpg','091655240woj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09165821s06r.jpg','09165821s06r.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09170006n0hs.jpg','09170006n0hs.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09174523xkvt.jpg','09174523xkvt.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/09174527lkok.jpg','09174527lkok.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/10092229195q.jpg','10092229195q.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/100950366ij3.jpg','100950366ij3.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/100953460bvo.jpg','100953460bvo.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/10103806oifr.jpg','10103806oifr.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/10104531ukj5.jpg','10104531ukj5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/10144918u244.jpg','10144918u244.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11165619lkm8.jpg','11165619lkm8.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11165624319n.jpg','11165624319n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111656292jx9.jpg','111656292jx9.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11165633po01.jpg','11165633po01.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111656366w4b.jpg','111656366w4b.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11165640djk4.jpg','11165640djk4.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11165849ahx1.jpg','11165849ahx1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11170224nw94.jpg','11170224nw94.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11170651qc2f.jpg','11170651qc2f.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11170656zz9b.jpg','11170656zz9b.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111707001w2k.jpg','111707001w2k.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11170703j2h2.jpg','11170703j2h2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111707071tn0.jpg','111707071tn0.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11170712hp37.jpg','11170712hp37.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111707541g63.jpg','111707541g63.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171054ja61.jpg','11171054ja61.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171058td8f.jpg','11171058td8f.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171102ae78.jpg','11171102ae78.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171106qaui.jpg','11171106qaui.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171109m2sr.jpg','11171109m2sr.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171211kmih.jpg','11171211kmih.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171546aux9.jpg','11171546aux9.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111715499z6t.jpg','111715499z6t.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171553t5kf.jpg','11171553t5kf.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171558kiit.jpg','11171558kiit.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171603em6o.jpg','11171603em6o.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11171606wnmy.jpg','11171606wnmy.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1117164754x7.jpg','1117164754x7.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11172127ltqx.jpg','11172127ltqx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111721308eyj.jpg','111721308eyj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11172134wikw.jpg','11172134wikw.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11172137nwy5.jpg','11172137nwy5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11172219hw2t.jpg','11172219hw2t.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173208w8ji.jpg','11173208w8ji.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173213qlec.jpg','11173213qlec.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111732181p5u.jpg','111732181p5u.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173222qknp.jpg','11173222qknp.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1117323496nx.jpg','1117323496nx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173244ggfv.jpg','11173244ggfv.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173334yvdd.jpg','11173334yvdd.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111737053v9i.jpg','111737053v9i.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173708ffx2.jpg','11173708ffx2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173711toqx.jpg','11173711toqx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173718do4k.jpg','11173718do4k.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173719ykxk.jpg','11173719ykxk.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173724ul7o.jpg','11173724ul7o.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11173804j6hj.jpg','11173804j6hj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11174436pnoi.jpg','11174436pnoi.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/11174439eb3l.jpg','11174439eb3l.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1117444317kg.jpg','1117444317kg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111744466aux.jpg','111744466aux.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/111745157ps9.jpg','111745157ps9.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12105715powr.jpg','12105715powr.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12105715u0i2.jpg','12105715u0i2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12105716avso.jpg','12105716avso.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12105716f0ks.jpg','12105716f0ks.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12105716u081.jpg','12105716u081.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12105717rgdv.jpg','12105717rgdv.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12105908yuqz.jpg','12105908yuqz.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121101457hlo.jpg','121101457hlo.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121101459s0t.jpg','121101459s0t.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110145r34m.jpg','12110145r34m.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110146fexh.jpg','12110146fexh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110146v1cq.jpg','12110146v1cq.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110146ynj3.jpg','12110146ynj3.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110240hrzh.jpg','12110240hrzh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121104013vts.jpg','121104013vts.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110726kcxc.jpg','12110726kcxc.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110726rqp4.jpg','12110726rqp4.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121107270owq.jpg','121107270owq.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1211072762wj.jpg','1211072762wj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110728cz8z.jpg','12110728cz8z.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110728hvna.jpg','12110728hvna.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12110817ejy1.jpg','12110817ejy1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111106jy1l.jpg','12111106jy1l.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111106lobh.jpg','12111106lobh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111106zad3.jpg','12111106zad3.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111106zedy.jpg','12111106zedy.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111107ctqg.jpg','12111107ctqg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111107l4wg.jpg','12111107l4wg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1211124392sy.jpg','1211124392sy.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111518igwr.jpg','12111518igwr.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111518kvd2.jpg','12111518kvd2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111518l3p5.jpg','12111518l3p5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111518vk7v.jpg','12111518vk7v.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121115195muw.jpg','121115195muw.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12111519tyc2.jpg','12111519tyc2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1211190051if.jpg','1211190051if.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12112124imh5.jpg','12112124imh5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121121253f1z.jpg','121121253f1z.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121121265tk1.jpg','121121265tk1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121121267h0z.jpg','121121267h0z.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12112127795n.jpg','12112127795n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12112128ltfy.jpg','12112128ltfy.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12112247lfhe.jpg','12112247lfhe.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12120435rmvm.jpg','12120435rmvm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12120436on4w.jpg','12120436on4w.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121204384v5d.jpg','121204384v5d.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121204408a3o.jpg','121204408a3o.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1212044200gm.jpg','1212044200gm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121207389dlf.jpg','121207389dlf.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134158q84h.jpg','12134158q84h.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134158qzrx.jpg','12134158qzrx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121341597c2x.jpg','121341597c2x.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134159kbxa.jpg','12134159kbxa.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134159robe.jpg','12134159robe.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134200cwyl.jpg','12134200cwyl.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134300ap1h.jpg','12134300ap1h.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134450lo6n.jpg','12134450lo6n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134450mwvm.jpg','12134450mwvm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134451j9p9.jpg','12134451j9p9.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134451k1zj.jpg','12134451k1zj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134452b2g1.jpg','12134452b2g1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134452bb5z.jpg','12134452bb5z.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134552bk69.jpg','12134552bk69.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121348103ux5.jpg','121348103ux5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121348109k92.jpg','121348109k92.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121348110tfi.jpg','121348110tfi.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134811y9du.jpg','12134811y9du.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134812unit.jpg','12134812unit.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12134933sxsx.jpg','12134933sxsx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135148im9s.jpg','12135148im9s.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135149ar88.jpg','12135149ar88.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135149bhlg.jpg','12135149bhlg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135149s2wm.jpg','12135149s2wm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135243x57l.jpg','12135243x57l.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135606e58j.jpg','12135606e58j.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135607ftvv.jpg','12135607ftvv.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135607vqhp.jpg','12135607vqhp.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135608ad8p.jpg','12135608ad8p.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135608l3ai.jpg','12135608l3ai.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135609wexl.jpg','12135609wexl.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135827xy0y.jpg','12135827xy0y.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12135944xk9f.jpg','12135944xk9f.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12140033b5mx.jpg','12140033b5mx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12140434ixtu.jpg','12140434ixtu.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12140434n4th.jpg','12140434n4th.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12140435relm.jpg','12140435relm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12140436rua8.jpg','12140436rua8.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12140436wwsc.jpg','12140436wwsc.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12140851yp14.jpg','12140851yp14.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12141311nuua.jpg','12141311nuua.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12141311xxbj.jpg','12141311xxbj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12141312d3p6.jpg','12141312d3p6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12141312r6eb.jpg','12141312r6eb.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1214131399h0.jpg','1214131399h0.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12141313ndlq.jpg','12141313ndlq.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121414505xah.jpg','121414505xah.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142118fe7p.jpg','12142118fe7p.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121421197sla.jpg','121421197sla.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142119m8y5.jpg','12142119m8y5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142120loqu.jpg','12142120loqu.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142120u2tx.jpg','12142120u2tx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1214212172bk.jpg','1214212172bk.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142322wa0z.jpg','12142322wa0z.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142734jwmh.jpg','12142734jwmh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142735jqmu.jpg','12142735jqmu.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142735rhib.jpg','12142735rhib.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1214273617d5.jpg','1214273617d5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142736ue6c.jpg','12142736ue6c.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142737cr9y.jpg','12142737cr9y.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12142830poeb.jpg','12142830poeb.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1214325480ue.jpg','1214325480ue.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12143254y1wh.jpg','12143254y1wh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121432556ttu.jpg','121432556ttu.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12143255ryzh.jpg','12143255ryzh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12143255ukxu.jpg','12143255ukxu.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12143256g1bo.jpg','12143256g1bo.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12143342sfmg.jpg','12143342sfmg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121454345gdz.jpg','121454345gdz.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12145434959z.jpg','12145434959z.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12145434qkiz.jpg','12145434qkiz.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12145435lmi6.jpg','12145435lmi6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12145435u9c1.jpg','12145435u9c1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121456599tnd.jpg','121456599tnd.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150556da16.jpg','12150556da16.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150556inf7.jpg','12150556inf7.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150557dzrz.jpg','12150557dzrz.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150557etcp.jpg','12150557etcp.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150701tmkd.jpg','12150701tmkd.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150936264i.jpg','12150936264i.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121509368460.jpg','121509368460.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150936cnpa.jpg','12150936cnpa.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121509371kyv.jpg','121509371kyv.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12150937xe3g.jpg','12150937xe3g.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12151139jrv6.jpg','12151139jrv6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1215211539ic.jpg','1215211539ic.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12152115dymp.jpg','12152115dymp.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12152115n7ee.jpg','12152115n7ee.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12152115oqbj.jpg','12152115oqbj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12152116am9n.jpg','12152116am9n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12152257va9g.jpg','12152257va9g.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12153550461x.jpg','12153550461x.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12153550ryso.jpg','12153550ryso.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12153551fflz.jpg','12153551fflz.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12153551igy5.jpg','12153551igy5.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12153551r5kz.jpg','12153551r5kz.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121535526foq.jpg','121535526foq.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121536505s2h.jpg','121536505s2h.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121542148jkc.jpg','121542148jkc.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121542158t1n.jpg','121542158t1n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121542159xil.jpg','121542159xil.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154215ryyk.jpg','12154215ryyk.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154215y02n.jpg','12154215y02n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154216egtt.jpg','12154216egtt.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121543059cct.jpg','121543059cct.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154708kkn6.jpg','12154708kkn6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154708nsye.jpg','12154708nsye.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154708we6w.jpg','12154708we6w.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154709iod3.jpg','12154709iod3.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/12154709ypkl.jpg','12154709ypkl.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/121547582f5t.jpg','121547582f5t.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1311420498g8.jpg','1311420498g8.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/131203117zrn.jpg','131203117zrn.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/131257327chh.jpg','131257327chh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13141220dfer.jpg','13141220dfer.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/131423399l1z.jpg','131423399l1z.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13142927kzr3.jpg','13142927kzr3.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/1316285322ks.jpg','1316285322ks.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13162904gqxm.jpg','13162904gqxm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13162913da42.jpg','13162913da42.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/131629222u39.jpg','131629222u39.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13162931ofsn.jpg','13162931ofsn.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13162941ipg2.jpg','13162941ipg2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13163101ccd3.jpg','13163101ccd3.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13163113pd3s.jpg','13163113pd3s.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13163306pqvc.jpg','13163306pqvc.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13165112t47d.jpg','13165112t47d.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13165118lr7r.jpg','13165118lr7r.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13165348xokj.jpg','13165348xokj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/131656557m43.jpg','131656557m43.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13165702cchp.jpg','13165702cchp.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13170123ywvv.jpg','13170123ywvv.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13170129mx9q.jpg','13170129mx9q.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13170427k084.jpg','13170427k084.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/131706089h4w.jpg','131706089h4w.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13170759d8ow.jpg','13170759d8ow.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/13170940lu1h.jpg','13170940lu1h.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19110822fin2.jpg','19110822fin2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19112623820c.jpg','19112623820c.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19112700bypf.jpg','19112700bypf.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19114043tp85.jpg','19114043tp85.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19114201tdir.jpg','19114201tdir.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/191201449moh.jpg','191201449moh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/191203538tdp.jpg','191203538tdp.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19131809acqm.jpg','19131809acqm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19131949r2ge.jpg','19131949r2ge.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/191342393mlg.jpg','191342393mlg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19134448qvza.jpg','19134448qvza.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/191351590e53.jpg','191351590e53.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19135642zjak.jpg','19135642zjak.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19135645ge7r.jpg','19135645ge7r.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19135821ij0m.jpg','19135821ij0m.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19140340fri2.jpg','19140340fri2.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19140601kgid.jpg','19140601kgid.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19140803w9fg.jpg','19140803w9fg.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/191408471iyj.jpg','191408471iyj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19141200ip5c.jpg','19141200ip5c.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19141318apz1.jpg','19141318apz1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19141700opui.jpg','19141700opui.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19141756u9sa.jpg','19141756u9sa.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19142041eu8x.jpg','19142041eu8x.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19142206y73m.jpg','19142206y73m.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19142430589t.jpg','19142430589t.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19142451945q.jpg','19142451945q.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19142818yvty.jpg','19142818yvty.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/19142840ycm6.jpg','19142840ycm6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201139137vu6.jpg','201139137vu6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20114003od1n.jpg','20114003od1n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201140399nrc.jpg','201140399nrc.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201143116bd3.jpg','201143116bd3.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20114348t1z8.jpg','20114348t1z8.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20114520rqti.jpg','20114520rqti.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20114607jim6.jpg','20114607jim6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20114824s9bf.jpg','20114824s9bf.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20115532h8tv.jpg','20115532h8tv.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201159459afm.jpg','201159459afm.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20120531bbei.jpg','20120531bbei.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/20120732ybv8.jpg','20120732ybv8.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308','201308',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309','201309',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201312/301119254w80.flv','/u/cms/www/201312/301119254w80.flv',0,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22093458gynd.jpg','22093458gynd.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22093502mmft.jpg','22093502mmft.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22093506l8pv.jpg','22093506l8pv.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22093509qm3l.jpg','22093509qm3l.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22093513srmf.jpg','22093513srmf.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22094752xoxd.jpg','22094752xoxd.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22094906lrj8.jpg','22094906lrj8.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22094911xe9x.jpg','22094911xe9x.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22094915t8h1.jpg','22094915t8h1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22094918gnze.jpg','22094918gnze.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22100555lytj.jpg','22100555lytj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22100558gfsb.jpg','22100558gfsb.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22100601l1us.jpg','22100601l1us.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22100606t8mw.jpg','22100606t8mw.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/22100611o2gl.jpg','22100611o2gl.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172935t4sb.jpg','23172935t4sb.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172936acob.jpg','23172936acob.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172937bli1.jpg','23172937bli1.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172937r23n.jpg','23172937r23n.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172939ln5a.jpg','23172939ln5a.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/231729407e1v.jpg','231729407e1v.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/231729434x7h.jpg','231729434x7h.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172944o38x.jpg','23172944o38x.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172944vvdh.jpg','23172944vvdh.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172946mzqx.jpg','23172946mzqx.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/23172947nrrl.jpg','23172947nrrl.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/24102446b7al.jpg','24102446b7al.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/24102503z9wj.jpg','24102503z9wj.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/2416455972ro.jpg','2416455972ro.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/241646340nq6.jpg','241646340nq6.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/24164707ksjq.jpg','24164707ksjq.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/26115537aper.jpg','26115537aper.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/26115537rs0f.jpg','26115537rs0f.jpg',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/26150136kryi.txt','26150136kryi.txt',1,NULL);
INSERT INTO `jc_file` VALUES ('/u/cms/www/Thumbs.db','Thumbs.db',1,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/13135536pnt2.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/131359296e9c.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/1314055350gc.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/13140606bjte.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/13140635xydz.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/13140702pfne.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/13140909s64p.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/13141723ywh0.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/13142644qpdm.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/131429269ikl.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/131431450xia.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201401/131431578u9o.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/1316123792p0.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13163251c8d5.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/131634322gqg.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/131636277cxi.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13163934xmp0.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13164248ng73.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13165347xxty.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13170503bwpw.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13170603q1bw.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/131706463pqt.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13172446osvy.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13172626x71c.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13173752etj3.zip','ChromeSetup.zip',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/13173805fii8.zip','ChromeSetup.zip',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14091703u57k.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14091741h0w0.zip','ChromeSetup.zip',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14092030cnyx.mp4','18183451i5bi.mp4',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14092835ljai.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14095531y5tg.zip','ChromeSetup.zip',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14095543za61.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14095551kk1a.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14095759mdqw.docx','0_開發需求_.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14102936l7gw.txt','freemarker.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14104521p9ey.zip','bbs-update-2012-11-2.zip',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14105137jxtn.mp4','/v6/u/cms/www/201403/14105137jxtn.mp4',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14112725dsar.zip','/v6/u/cms/www/201403/14112725dsar.zip',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14130702z3xz.png','/v6/u/cms/www/201403/14130702z3xz.png',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/14165746s64i.pdf','1.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201403/1914315903bj.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/20151821gj8y.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/2015202141s0.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/211128348kuw.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/2111305770l1.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/21113452hsxh.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/211137096eif.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/211149148tx0.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/21115759y8sm.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/211511276v74.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/21151948k1fh.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/21154820xtfs.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/2115485050rw.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/21155236mk9b.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22084701xqxy.doc','/v6/u/cms/www/201404/22084701xqxy.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22084833ys27.doc','/v6/u/cms/www/201404/22084833ys27.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22111217hftv.doc','Linux开启mysql远程连接.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22111332dwt3.doc','Linux开启mysql远程连接.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22114143pw1a.doc','Linux开启mysql远程连接.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22132355vqdf.txt','IBM LDAP.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22133304pnoh.txt','/v6/u/cms/www/201404/22133304pnoh.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22133312pz3j.exe','wpp.exe',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22135235m1m1.txt','cmstop.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22135348pbqp.swf','2.swf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/221353537d0o.doc','discuz_2.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22140446zkgv.txt','jeecms新bug.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22140629jx27.txt','360检测.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22140634er4n.doc','Discuz_X2.0数据字典(数据库表作用解释).doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22140716e5bk.flv','/v6/u/cms/www/201404/22140716e5bk.flv',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22141101f9tv.txt','360检测.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22141404irh6.docx','Apache_Shiro_使用手册.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22142207xrqx.doc','Discuz_X2.0数据字典(数据库表作用解释).doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22145137jigp.docx','Apache_Shiro_使用手册.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22145142drkb.docx','Apache_Shiro_使用手册.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/221501301rkt.docx','Apache_Shiro_使用手册.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22151702mcwi.docx','Apache_Shiro_使用手册.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22152145c9do.doc','Linux开启mysql远程连接.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22152231wfrv.mp4','/v6/u/cms/www/201404/22152231wfrv.mp4',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22155743mgws.txt','bug平台推荐.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22155756p9of.txt','flot柱状图.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/22155756scdn.txt','flot使用笔记.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/28134316erf3.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/281402505i20.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/28140346452f.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201404/30140543hzlx.gif','webLogo.gif',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/08091845sh2l.jpg','1.JPG',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/08092143cyap.jpg','1.JPG',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/08092249ype2.jpg','1.JPG',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/08092924h3fr.jpg','1.JPG',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/08093111b3jm.jpg','1.JPG',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/09083819wiab.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/090840146ik7.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201405/090918028k13.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201406/09144419786f.txt','bbs好的功能.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201406/09145849ap2u.docx','1.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201406/09151219pj5s.doc','2.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201406/0915215434ij.txt','cmstop.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/121650299xv9.doc','DiscuzX2文件说明,目录说明.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/12165642273e.doc','Discuz_X2.0数据字典(数据库表作用解释).doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/12170522j2ct.jpg','1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/30093714q9ic.mp4','/v6/u/cms/www/201407/30093714q9ic.mp4',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/30130947bmm3.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/30170627hj1m.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/31114444inln.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/31114945lp9t.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/31115246futn.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/31115423u7e6.jpg','mv1.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/u/cms/www/201407/31132254zdh7.jpg','mv.jpg',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201312/241031214kys.txt','/v6/wenku/www/201312/241031214kys.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201401/16164720tu2n.doc','/v6/wenku/www/201401/16164720tu2n.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201401/17083718btie.doc','/v6/wenku/www/201401/17083718btie.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201401/17084032522j.doc','/v6/wenku/www/201401/17084032522j.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201401/170842070sy2.doc','/v6/wenku/www/201401/170842070sy2.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201401/17084442fd08.doc','/v6/wenku/www/201401/17084442fd08.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201401/17085101zvcg.doc','/v6/wenku/www/201401/17085101zvcg.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201401/25095002cniq.doc','/v6/wenku/www/201401/25095002cniq.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201403/10115402nl6c.docx','/v6/wenku/www/201403/10115402nl6c.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201403/1011550974pv.docx','/v6/wenku/www/201403/1011550974pv.docx',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201403/101155409soa.txt','/v6/wenku/www/201403/101155409soa.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201403/10115750uxwh.txt','/v6/wenku/www/201403/10115750uxwh.txt',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/04132656esvy.pdf','/v6/wenku/www/201406/04132656esvy.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/04133131ky4f.pdf','/v6/wenku/www/201406/04133131ky4f.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/04133159jryy.pdf','/v6/wenku/www/201406/04133159jryy.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/04133312rszg.pdf','/v6/wenku/www/201406/04133312rszg.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/04133437555h.pdf','/v6/wenku/www/201406/04133437555h.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/04133611h3sa.pdf','/v6/wenku/www/201406/04133611h3sa.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/041340029cmx.pdf','/v6/wenku/www/201406/041340029cmx.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/0413413473t5.pdf','/v6/wenku/www/201406/0413413473t5.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/041353368k3b.doc','/v6/wenku/www/201406/041353368k3b.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201406/04135541s2ay.doc','/v6/wenku/www/201406/04135541s2ay.doc',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201408/08112543cj83.pdf','/v6/wenku/www/201408/08112543cj83.pdf',0,NULL);
INSERT INTO `jc_file` VALUES ('/v6/wenku/www/201408/08131514njo0.pdf','/v6/wenku/www/201408/08131514njo0.pdf',0,NULL);
DROP TABLE IF EXISTS `jc_friendlink`;
CREATE TABLE `jc_friendlink` (
  `friendlink_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_id` int(11) NOT NULL,
  `site_name` varchar(150) NOT NULL COMMENT '网站名称',
  `domain` varchar(255) NOT NULL COMMENT '网站地址',
  `logo` varchar(150) default NULL COMMENT '图标',
  `email` varchar(100) default NULL COMMENT '站长邮箱',
  `description` varchar(255) default NULL COMMENT '描述',
  `views` int(11) NOT NULL default '0' COMMENT '点击次数',
  `is_enabled` char(1) NOT NULL default '1' COMMENT '是否显示',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  PRIMARY KEY  (`friendlink_id`),
  KEY `fk_jc_ctg_friendlink` (`friendlinkctg_id`),
  KEY `fk_jc_friendlink_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='CMS友情链接';

INSERT INTO `jc_friendlink` VALUES (1,1,1,'JEECMS官网','http://www.jeecms.com',NULL,'jeecms@163.com','JEECMS是JavaEE版网站管理系统（Java Enterprise Edition Content Manage System）的简称。Java凭借其强大、稳定、安全、高效等多方面的优势，一直是企业级应用的首选。',34,'1',1);
INSERT INTO `jc_friendlink` VALUES (2,1,1,'JEEBBS论坛','http://bbs.jeecms.com',NULL,'jeecms@163.com','JEEBBS论坛',3,'1',10);
INSERT INTO `jc_friendlink` VALUES (3,1,2,'京东商城','http://www.360buy.com/','/u/cms/www/201112/1910271036lr.gif','','',3,'1',10);
INSERT INTO `jc_friendlink` VALUES (4,1,2,'当当网','http://www.dangdang.com/','/u/cms/www/201112/191408344rwj.gif','','',1,'1',10);
INSERT INTO `jc_friendlink` VALUES (5,1,2,'亚马逊','http://www.amazon.cn/','/u/cms/www/201112/19141012lh2q.gif','','',1,'1',10);
INSERT INTO `jc_friendlink` VALUES (6,1,2,'ihush','http://www.ihush.com/','/u/cms/www/201112/19141255yrfb.gif','','',1,'1',10);
DROP TABLE IF EXISTS `jc_friendlink_ctg`;
CREATE TABLE `jc_friendlink_ctg` (
  `friendlinkctg_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_name` varchar(50) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  PRIMARY KEY  (`friendlinkctg_id`),
  KEY `fk_jc_friendlinkctg_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS友情链接类别';

INSERT INTO `jc_friendlink_ctg` VALUES (1,1,'文字链接',1);
INSERT INTO `jc_friendlink_ctg` VALUES (2,1,'品牌专区（图片链接）',2);
DROP TABLE IF EXISTS `jc_group`;
CREATE TABLE `jc_group` (
  `group_id` int(11) NOT NULL auto_increment,
  `group_name` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `need_captcha` tinyint(1) NOT NULL default '1' COMMENT '是否需要验证码',
  `need_check` tinyint(1) NOT NULL default '1' COMMENT '是否需要审核',
  `allow_per_day` int(11) NOT NULL default '4096' COMMENT '每日允许上传KB',
  `allow_max_file` int(11) NOT NULL default '1024' COMMENT '每个文件最大KB',
  `allow_suffix` varchar(255) default 'jpg,jpeg,gif,png,bmp' COMMENT '允许上传的后缀',
  `is_reg_def` tinyint(1) NOT NULL default '0' COMMENT '是否默认会员组',
  PRIMARY KEY  (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS会员组表';

INSERT INTO `jc_group` VALUES (1,'普通会员',10,1,1,0,0,'',1);
INSERT INTO `jc_group` VALUES (2,'高级组',10,1,1,0,0,'',0);
DROP TABLE IF EXISTS `jc_guestbook`;
CREATE TABLE `jc_guestbook` (
  `guestbook_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `guestbookctg_id` int(11) NOT NULL,
  `member_id` int(11) default NULL COMMENT '留言会员',
  `admin_id` int(11) default NULL COMMENT '回复管理员',
  `ip` varchar(50) NOT NULL COMMENT '留言IP',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  `replay_time` datetime default NULL COMMENT '回复时间',
  `is_checked` tinyint(1) NOT NULL default '0' COMMENT '是否审核',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推荐',
  PRIMARY KEY  (`guestbook_id`),
  KEY `fk_jc_ctg_guestbook` (`guestbookctg_id`),
  KEY `fk_jc_guestbook_admin` (`admin_id`),
  KEY `fk_jc_guestbook_member` (`member_id`),
  KEY `fk_jc_guestbook_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='CMS留言';

INSERT INTO `jc_guestbook` VALUES (1,1,1,NULL,1,'127.0.0.1','2014-01-01 15:02:19',NULL,1,0);
INSERT INTO `jc_guestbook` VALUES (2,1,1,1,NULL,'127.0.0.1','2014-04-19 16:04:19',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (3,1,1,1,NULL,'127.0.0.1','2014-04-19 16:04:34',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (4,1,1,NULL,NULL,'127.0.0.1','2014-04-19 17:11:55',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (5,1,2,1,NULL,'127.0.0.1','2014-04-21 09:28:01',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (6,1,1,1,NULL,'127.0.0.1','2014-04-21 10:52:45',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (7,1,1,1,NULL,'127.0.0.1','2014-04-21 11:20:16',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (8,1,1,1,1,'127.0.0.1','2014-04-21 11:23:25','2014-05-07 17:33:05',0,1);
INSERT INTO `jc_guestbook` VALUES (9,1,1,1,NULL,'127.0.0.1','2014-05-04 16:06:02',NULL,0,1);
DROP TABLE IF EXISTS `jc_guestbook_ctg`;
CREATE TABLE `jc_guestbook_ctg` (
  `guestbookctg_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `ctg_name` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `description` varchar(255) default NULL COMMENT '描述',
  PRIMARY KEY  (`guestbookctg_id`),
  KEY `fk_jc_guestbookctg_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS留言类别';

INSERT INTO `jc_guestbook_ctg` VALUES (1,1,'普通',1,'普通留言');
INSERT INTO `jc_guestbook_ctg` VALUES (2,1,'投诉',10,'投诉');
DROP TABLE IF EXISTS `jc_guestbook_ext`;
CREATE TABLE `jc_guestbook_ext` (
  `guestbook_id` int(11) NOT NULL,
  `title` varchar(255) default NULL COMMENT '留言标题',
  `content` longtext COMMENT '留言内容',
  `reply` longtext COMMENT '回复内容',
  `email` varchar(100) default NULL COMMENT '电子邮件',
  `phone` varchar(100) default NULL COMMENT '电话',
  `qq` varchar(50) default NULL COMMENT 'QQ',
  KEY `fk_jc_ext_guestbook` (`guestbook_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS留言内容';

INSERT INTO `jc_guestbook_ext` VALUES (1,'111111111111111','11','11111111111111111',NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (2,'aa','a',NULL,NULL,'aa',NULL);
INSERT INTO `jc_guestbook_ext` VALUES (3,'aa','a',NULL,NULL,'aa',NULL);
INSERT INTO `jc_guestbook_ext` VALUES (4,'asdfsadf','asdfasdfasdf',NULL,NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (5,'aaaa','aaaaa',NULL,NULL,'a',NULL);
INSERT INTO `jc_guestbook_ext` VALUES (6,NULL,'asdfasd',NULL,NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (7,NULL,'aaa',NULL,NULL,'a',NULL);
INSERT INTO `jc_guestbook_ext` VALUES (8,'asdf','asdf','asdf',NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (9,'<script>alert(\"dd\")</script> ','<script>alert(\"dd\")</script> ',NULL,NULL,NULL,NULL);
DROP TABLE IF EXISTS `jc_job_apply`;
CREATE TABLE `jc_job_apply` (
  `job_apply_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content_id` int(11) NOT NULL COMMENT '职位id',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  PRIMARY KEY  (`job_apply_id`),
  KEY `fk_jc_job_apply_user` (`user_id`),
  KEY `fk_jc_job_apply_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位申请表';

DROP TABLE IF EXISTS `jc_keyword`;
CREATE TABLE `jc_keyword` (
  `keyword_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL COMMENT '站点ID',
  `keyword_name` varchar(100) NOT NULL COMMENT '名称',
  `url` varchar(255) NOT NULL COMMENT '链接',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  PRIMARY KEY  (`keyword_id`),
  KEY `fk_jc_keyword_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS内容关键词表';

INSERT INTO `jc_keyword` VALUES (1,NULL,'内容管理系统','<a href=\"http://www.jeecms.com/\" target=\"_blank\">内容管理系统</a>',0);
DROP TABLE IF EXISTS `jc_log`;
CREATE TABLE `jc_log` (
  `log_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `site_id` int(11) default NULL,
  `category` int(11) NOT NULL COMMENT '日志类型',
  `log_time` datetime NOT NULL COMMENT '日志时间',
  `ip` varchar(50) default NULL COMMENT 'IP地址',
  `url` varchar(255) default NULL COMMENT 'URL地址',
  `title` varchar(255) default NULL COMMENT '日志标题',
  `content` varchar(255) default NULL COMMENT '日志内容',
  PRIMARY KEY  (`log_id`),
  KEY `fk_jc_log_site` (`site_id`),
  KEY `fk_jc_log_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS日志表';

DROP TABLE IF EXISTS `jc_message`;
CREATE TABLE `jc_message` (
  `msg_id` int(11) NOT NULL auto_increment COMMENT '消息id',
  `msg_title` varchar(255) NOT NULL default '' COMMENT '标题',
  `msg_content` longtext COMMENT '站内信息内容',
  `send_time` timestamp NULL default NULL COMMENT '发送时间',
  `msg_send_user` int(11) NOT NULL default '1' COMMENT '发信息人',
  `msg_receiver_user` int(11) NOT NULL default '0' COMMENT '接收人',
  `site_id` int(11) NOT NULL default '1' COMMENT '站点',
  `msg_status` tinyint(1) NOT NULL default '0' COMMENT '消息状态0未读，1已读',
  `msg_box` int(2) NOT NULL default '1' COMMENT '消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱',
  PRIMARY KEY  (`msg_id`),
  KEY `fk_jc_message_user_send` (`msg_send_user`),
  KEY `fk_jc_message_user_receiver` (`msg_receiver_user`),
  KEY `fk_jc_message_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信';

DROP TABLE IF EXISTS `jc_model`;
CREATE TABLE `jc_model` (
  `model_id` int(11) NOT NULL,
  `model_name` varchar(100) NOT NULL COMMENT '名称',
  `model_path` varchar(100) NOT NULL COMMENT '路径',
  `tpl_channel_prefix` varchar(20) default NULL COMMENT '栏目模板前缀',
  `tpl_content_prefix` varchar(20) default NULL COMMENT '内容模板前缀',
  `title_img_width` int(11) NOT NULL default '139' COMMENT '栏目标题图宽度',
  `title_img_height` int(11) NOT NULL default '139' COMMENT '栏目标题图高度',
  `content_img_width` int(11) NOT NULL default '310' COMMENT '栏目内容图宽度',
  `content_img_height` int(11) NOT NULL default '310' COMMENT '栏目内容图高度',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `has_content` tinyint(1) NOT NULL default '1' COMMENT '是否有内容',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  `is_def` tinyint(1) NOT NULL default '0' COMMENT '是否默认模型',
  PRIMARY KEY  (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS模型表';

INSERT INTO `jc_model` VALUES (1,'新闻','1','news','news',139,139,310,310,1,1,0,1);
INSERT INTO `jc_model` VALUES (2,'单页','2','alone','alone',139,139,310,310,2,0,0,0);
INSERT INTO `jc_model` VALUES (4,'下载','4','download','download',139,139,310,310,4,1,0,0);
INSERT INTO `jc_model` VALUES (5,'图库','5','pic','pic',139,139,310,310,5,1,0,0);
INSERT INTO `jc_model` VALUES (6,'视频','6','vedio','vedio',139,139,310,310,10,1,0,0);
INSERT INTO `jc_model` VALUES (8,'招聘','job','job','job',139,139,310,310,10,1,0,0);
DROP TABLE IF EXISTS `jc_model_item`;
CREATE TABLE `jc_model_item` (
  `modelitem_id` int(11) NOT NULL auto_increment,
  `model_id` int(11) NOT NULL,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `item_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '70' COMMENT '排列顺序',
  `def_value` varchar(255) default NULL COMMENT '默认值',
  `opt_value` varchar(255) default NULL COMMENT '可选项',
  `text_size` varchar(20) default NULL COMMENT '长度',
  `area_rows` varchar(3) default NULL COMMENT '文本行数',
  `area_cols` varchar(3) default NULL COMMENT '文本列数',
  `help` varchar(255) default NULL COMMENT '帮助信息',
  `help_position` varchar(1) default NULL COMMENT '帮助位置',
  `data_type` int(11) NOT NULL default '1' COMMENT '数据类型',
  `is_single` tinyint(1) NOT NULL default '1' COMMENT '是否独占一行',
  `is_channel` tinyint(1) NOT NULL default '1' COMMENT '是否栏目模型项',
  `is_custom` tinyint(1) NOT NULL default '1' COMMENT '是否自定义',
  `is_display` tinyint(1) NOT NULL default '1' COMMENT '是否显示',
  PRIMARY KEY  (`modelitem_id`),
  KEY `fk_jc_item_model` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=426 DEFAULT CHARSET=utf8 COMMENT='CMS模型项表';

INSERT INTO `jc_model_item` VALUES (1,1,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (2,1,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (3,1,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (4,1,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (5,1,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (6,1,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (7,1,'tplContent','选择模型模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (8,1,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (9,1,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (10,1,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (11,1,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (12,1,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (14,1,'afterCheck','审核后',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (15,1,'commentControl','评论',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (16,1,'allowUpdown','顶踩',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (17,1,'viewGroupIds','浏览权限',12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (18,1,'contriGroupIds','投稿权限',12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (20,1,'link','外部链接',12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (21,1,'titleImg','标题图',12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (23,1,'title','标题',9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (24,1,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (25,1,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (26,1,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (27,1,'description','摘要',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (28,1,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (29,1,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (30,1,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (31,1,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (32,1,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (33,1,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (34,1,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (35,1,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (36,1,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (37,1,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (38,1,'attachments','附件',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (39,1,'media','多媒体',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (40,1,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (42,2,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (43,2,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (44,2,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (45,2,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (46,2,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (47,2,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (48,2,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (49,2,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (50,2,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (51,2,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (52,2,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (53,2,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (93,4,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (94,4,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (95,4,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (96,4,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (97,4,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (98,4,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (99,4,'tplContent','选择模型模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (100,4,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (101,4,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (102,4,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (103,4,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (104,4,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (105,4,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (107,4,'channelId','栏目',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (108,4,'title','软件名称',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (109,4,'shortTitle','软件简称',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (110,4,'titleColor','标题颜色',4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (111,4,'description','摘要',5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (112,4,'author','发布人',6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (113,4,'viewGroupIds','浏览权限',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (114,4,'topLevel','固顶级别',8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (115,4,'releaseDate','发布时间',9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (116,4,'typeId','内容类型',21,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (117,4,'tplContent','指定模板',22,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (118,4,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (119,4,'attachments','资源上传',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (120,4,'txt','软件介绍',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (121,4,'softType','软件类型',12,'国产软件','国产软件,国外软件','100','3','30',NULL,NULL,6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (122,4,'warrant','软件授权',13,'免费版','免费版,共享版','','3','30','','',6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (123,4,'relatedLink','相关链接',14,'http://','','50','3','30','','',1,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (124,4,'demoUrl','演示地址',15,'http://',NULL,'60','3','30',NULL,NULL,1,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (125,5,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (126,5,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (127,5,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (128,5,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (129,5,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (130,5,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (131,5,'tplContent','选择模型模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (132,5,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (133,5,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (134,5,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (135,5,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (136,5,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (138,5,'afterCheck','审核后',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (139,5,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (140,5,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (141,5,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (142,5,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (144,5,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (145,5,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (146,5,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (147,5,'channelId','栏目',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (148,5,'title','标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (149,5,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (150,5,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (151,5,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (152,5,'description','摘要',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (153,5,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (154,5,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (155,5,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (156,5,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (157,5,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (158,5,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (159,5,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (160,5,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (161,5,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (162,5,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (163,5,'pictures','图片集',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (164,6,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (165,6,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (166,6,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (167,6,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (168,6,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (169,6,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (170,6,'tplContent','选择模型模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (171,6,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (172,6,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (173,6,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (174,6,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (175,6,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (177,6,'afterCheck','审核后',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (178,6,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (179,6,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (180,6,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (181,6,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (183,6,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (184,6,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (185,6,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (186,6,'channelId','栏目',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (187,6,'title','标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (188,6,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (189,6,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (190,6,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (191,6,'description','内容简介',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (192,6,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (193,6,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (194,6,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (195,6,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (196,6,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (197,6,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (198,6,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (199,6,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (200,6,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (201,6,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (202,6,'attachments','附件',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (203,6,'media','多媒体',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (246,4,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (249,8,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (250,8,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (251,8,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (252,8,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (253,8,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (254,8,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (255,8,'tplContent','选择模型模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (256,8,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (257,8,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (258,8,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (259,8,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (260,8,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (262,8,'afterCheck','审核后',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (263,8,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (264,8,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (265,8,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (266,8,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (268,8,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (269,8,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (270,8,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (271,8,'channelId','栏目',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (272,8,'title','岗位名称',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (275,8,'tagStr','Tag标签',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (279,8,'viewGroupIds','浏览权限',8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (280,8,'topLevel','固顶级别',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (281,8,'releaseDate','发布时间',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (282,8,'typeId','内容类型',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (283,8,'tplContent','指定模板',8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (289,8,'txt','职位描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (290,8,'deadline','截止日期',2,NULL,NULL,NULL,'3','30','留空永久有效',NULL,5,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (291,8,'experience','工作经验',3,NULL,'1-3年,3-5年,5年以上,不限',NULL,'3','30',NULL,NULL,6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (292,8,'education','最低学历',3,'','专科,本科,硕士,不限','','3','30','','',6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (293,8,'salary','职位月薪',4,NULL,'1500-2000,2000-3000,3000-5000,5000-8000,8000+,面议',NULL,'3','30',NULL,NULL,6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (294,8,'workplace','工作地点',4,'','北京,上海,深圳,广州,重庆,南京,杭州,西安,南昌','','3','30','','',7,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (295,8,'nature','工作性质',5,'','全职,兼职','','3','30','','',8,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (296,8,'hasmanage','管理经验',5,'','要求,不要求','','3','30','','',8,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (297,8,'nums','招聘人数',6,'','1-3人,3-5人,5-10人,若干','','3','30','','',6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (298,8,'category','职位类别',6,NULL,'项目主管,java高级工程师,java工程师,网页设计师,测试人员,技术支持',NULL,'3','30',NULL,NULL,6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (344,1,'channelId','栏目',8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (403,6,'Director','导演',10,'','','','1','2','','',1,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (404,6,'Starring','主演',10,'','','','1','30','','',1,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (405,6,'VideoType','视频类型',10,NULL,'历史,古装,都市,喜剧,悬疑,动作,谍战,伦理,战争,惊悚',NULL,'3','30',NULL,NULL,7,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (406,6,'Video','影片信息',10,'','正片,预告片','','3','30','','',6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (408,1,'contentImg','内容图',12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (409,5,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (410,6,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (412,1,'allowShare','分享',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (413,1,'allowScore','评分',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (414,1,'pictures','图片集',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (415,1,'finalStep','终审级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (420,6,'finalStep','终审级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (421,6,'allowShare','分享',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (422,6,'allowScore','评分',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (423,8,'finalStep','终审级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (424,8,'allowShare','分享',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (425,8,'allowScore','评分',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
DROP TABLE IF EXISTS `jc_origin`;
CREATE TABLE `jc_origin` (
  `origin_id` int(11) NOT NULL auto_increment,
  `origin_name` varchar(255) NOT NULL COMMENT '来源名称',
  `ref_count` int(11) NOT NULL default '0' COMMENT '来源文章总数',
  PRIMARY KEY  (`origin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='来源';

INSERT INTO `jc_origin` VALUES (1,'新浪微博',0);
INSERT INTO `jc_origin` VALUES (2,'百度',0);
INSERT INTO `jc_origin` VALUES (3,'百度论坛',0);
INSERT INTO `jc_origin` VALUES (4,'百度贴吧',0);
INSERT INTO `jc_origin` VALUES (5,'新浪新闻',0);
INSERT INTO `jc_origin` VALUES (6,'腾讯新闻',0);
DROP TABLE IF EXISTS `jc_plug`;
CREATE TABLE `jc_plug` (
  `plug_id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL COMMENT '插件名称',
  `path` varchar(255) NOT NULL default '' COMMENT '文件路径',
  `description` varchar(2000) default NULL COMMENT '描述',
  `author` varchar(255) default NULL COMMENT '作者',
  `upload_time` datetime NOT NULL COMMENT '上传时间',
  `install_time` datetime default NULL COMMENT '安装时间',
  `uninstall_time` datetime default NULL COMMENT '卸载时间',
  `file_conflict` tinyint(1) NOT NULL default '0' COMMENT '包含文件是否冲突',
  `is_used` tinyint(1) NOT NULL default '0' COMMENT '使用状态(0未使用,1使用中)',
  `plug_perms` varchar(255) default '' COMMENT '插件权限（,分隔各个权限配置）',
  PRIMARY KEY  (`plug_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='插件';

INSERT INTO `jc_plug` VALUES (4,'测试1','/WEB-INF/plug/test.zip','测试测试','TOM','2014-01-04 16:49:47','2014-02-19 09:49:31','2014-02-19 09:49:15',0,0,'test:*');
DROP TABLE IF EXISTS `jc_receiver_message`;
CREATE TABLE `jc_receiver_message` (
  `msg_re_id` int(11) NOT NULL auto_increment COMMENT '消息id',
  `msg_title` varchar(255) NOT NULL default '' COMMENT '标题',
  `msg_content` longtext COMMENT '站内信息内容',
  `send_time` timestamp NULL default NULL COMMENT '发送时间',
  `msg_send_user` int(11) NOT NULL default '1' COMMENT '发信息人',
  `msg_receiver_user` int(11) NOT NULL default '0' COMMENT '接收人',
  `site_id` int(11) NOT NULL default '1' COMMENT '站点',
  `msg_status` tinyint(1) NOT NULL default '0' COMMENT '消息状态0未读，1已读',
  `msg_box` int(2) NOT NULL default '1' COMMENT '消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱',
  `msg_id` int(11) default NULL COMMENT '发信的信件id',
  PRIMARY KEY  (`msg_re_id`),
  KEY `jc_receiver_message_user_send` (`msg_send_user`),
  KEY `jc_receiver_message_user_receiver` (`msg_receiver_user`),
  KEY `jc_receiver_message_site` (`site_id`),
  KEY `jc_receiver_message_message` (`msg_re_id`),
  KEY `fk_jc_receiver_message_message` (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='站内信收信表';

INSERT INTO `jc_receiver_message` VALUES (1,'aa','aaa','2014-01-22 09:44:32',1,1,1,1,0,NULL);
INSERT INTO `jc_receiver_message` VALUES (2,'aa','aaa','2014-01-22 09:44:32',1,1,1,0,3,NULL);
DROP TABLE IF EXISTS `jc_role`;
CREATE TABLE `jc_role` (
  `role_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `is_super` char(1) NOT NULL default '0' COMMENT '拥有所有权限',
  PRIMARY KEY  (`role_id`),
  KEY `fk_jc_role_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS角色表';

INSERT INTO `jc_role` VALUES (1,NULL,'管理员',10,'1');
DROP TABLE IF EXISTS `jc_role_permission`;
CREATE TABLE `jc_role_permission` (
  `role_id` int(11) NOT NULL,
  `uri` varchar(100) NOT NULL,
  KEY `fk_jc_permission_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS角色授权表';

INSERT INTO `jc_role_permission` VALUES (1,'/top.do');
INSERT INTO `jc_role_permission` VALUES (1,'/right.do');
INSERT INTO `jc_role_permission` VALUES (1,'/main.do');
INSERT INTO `jc_role_permission` VALUES (1,'/left.do');
INSERT INTO `jc_role_permission` VALUES (1,'/index.do');
INSERT INTO `jc_role_permission` VALUES (1,'/message/v_countUnreadMsg.do');
INSERT INTO `jc_role_permission` VALUES (1,'/content/o_generateTags.do');
INSERT INTO `jc_role_permission` VALUES (1,'/map.do');
INSERT INTO `jc_role_permission` VALUES (1,'/statistic_member/v_list.do');
INSERT INTO `jc_role_permission` VALUES (1,'admin_global:v_list:*');
INSERT INTO `jc_role_permission` VALUES (1,'admin_local:v_list:*');
INSERT INTO `jc_role_permission` VALUES (1,'/admin_local/v_edit/2');
INSERT INTO `jc_role_permission` VALUES (1,'admin_local:v_list');
DROP TABLE IF EXISTS `jc_score_group`;
CREATE TABLE `jc_score_group` (
  `score_group_id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL COMMENT '分组名',
  `description` varchar(1000) default NULL COMMENT '描述',
  `enable` tinyint(1) NOT NULL default '0' COMMENT '是否启用',
  `def` tinyint(3) NOT NULL default '0' COMMENT '是否默认',
  `site_id` int(11) NOT NULL COMMENT '站点',
  PRIMARY KEY  (`score_group_id`),
  KEY `fk_jc_score_group_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='FComment';

INSERT INTO `jc_score_group` VALUES (1,'心情组','心情组',1,1,1);
INSERT INTO `jc_score_group` VALUES (2,'星级评分','星级评分',1,0,1);
DROP TABLE IF EXISTS `jc_score_item`;
CREATE TABLE `jc_score_item` (
  `score_item_id` int(11) NOT NULL auto_increment,
  `score_group_id` int(11) NOT NULL COMMENT '评分组',
  `name` varchar(255) NOT NULL COMMENT '评分名',
  `score` int(11) NOT NULL COMMENT '分值',
  `image_path` varchar(255) default NULL COMMENT '评分图标路径',
  `priority` int(11) NOT NULL default '10' COMMENT '排序',
  PRIMARY KEY  (`score_item_id`),
  KEY `fk_jc_score_item_group` (`score_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='评分项';

INSERT INTO `jc_score_item` VALUES (1,1,'开心',1,'/r/cms/smilies/m1.gif',1);
INSERT INTO `jc_score_item` VALUES (2,1,'板砖',1,'/r/cms/smilies/m2.gif',2);
INSERT INTO `jc_score_item` VALUES (3,1,'感动',1,'/r/cms/smilies/m3.gif',3);
INSERT INTO `jc_score_item` VALUES (4,1,'有用',1,'/r/cms/smilies/m4.gif',4);
INSERT INTO `jc_score_item` VALUES (5,1,'疑问',1,'/r/cms/smilies/m5.gif',5);
INSERT INTO `jc_score_item` VALUES (6,1,'难过',1,'/r/cms/smilies/m6.gif',6);
INSERT INTO `jc_score_item` VALUES (7,1,'无聊',1,'/r/cms/smilies/m7.gif',7);
INSERT INTO `jc_score_item` VALUES (8,1,'震惊',1,'/r/cms/smilies/m8.gif',8);
INSERT INTO `jc_score_item` VALUES (9,2,'非常差',1,'',1);
INSERT INTO `jc_score_item` VALUES (10,2,'差',2,'',2);
INSERT INTO `jc_score_item` VALUES (11,2,'一般',3,'',3);
INSERT INTO `jc_score_item` VALUES (12,2,'好',4,'',4);
INSERT INTO `jc_score_item` VALUES (13,2,'非常好',5,'',5);
DROP TABLE IF EXISTS `jc_score_record`;
CREATE TABLE `jc_score_record` (
  `score_record_id` int(11) NOT NULL auto_increment,
  `score_item_id` int(11) NOT NULL COMMENT '评分项',
  `content_id` int(11) NOT NULL COMMENT '内容',
  `score_count` int(11) NOT NULL COMMENT '评分次数',
  PRIMARY KEY  (`score_record_id`),
  KEY `fk_jc_record_score_item` (`score_item_id`),
  KEY `index_score_record_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评分纪录';

DROP TABLE IF EXISTS `jc_search_words`;
CREATE TABLE `jc_search_words` (
  `word_id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL COMMENT '搜索词汇',
  `hit_count` int(11) NOT NULL default '0' COMMENT '搜索次数',
  `priority` int(11) NOT NULL default '10' COMMENT '优先级',
  `name_initial` varchar(500) NOT NULL default '' COMMENT '拼音首字母',
  PRIMARY KEY  (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='搜索热词';

INSERT INTO `jc_search_words` VALUES (1,'国内新闻',0,2,'gnxw');
INSERT INTO `jc_search_words` VALUES (4,'中大云锦',0,1,'zdyj');
INSERT INTO `jc_search_words` VALUES (5,'中国建设',60,2,'zgjs');
INSERT INTO `jc_search_words` VALUES (6,'中国农业银行',0,2,'zgnyyx');
INSERT INTO `jc_search_words` VALUES (7,'中国建设银行',12,10,'zgjsyx');
INSERT INTO `jc_search_words` VALUES (8,'江西',1,10,'jx');
INSERT INTO `jc_search_words` VALUES (9,'南昌',22,10,'nc');
INSERT INTO `jc_search_words` VALUES (10,'新闻',9,10,'xw');
INSERT INTO `jc_search_words` VALUES (11,'家',3,10,'j');
INSERT INTO `jc_search_words` VALUES (13,'毛泽东',8,10,'mzd');
INSERT INTO `jc_search_words` VALUES (14,'刘晓庆',10,10,'lxq');
INSERT INTO `jc_search_words` VALUES (15,'广西 ',1,10,'gx');
INSERT INTO `jc_search_words` VALUES (17,'国内',16,10,'gn');
INSERT INTO `jc_search_words` VALUES (18,'巴基斯坦',1,10,'bjst');
DROP TABLE IF EXISTS `jc_sensitivity`;
CREATE TABLE `jc_sensitivity` (
  `sensitivity_id` int(11) NOT NULL auto_increment,
  `search` varchar(255) NOT NULL COMMENT '敏感词',
  `replacement` varchar(255) NOT NULL COMMENT '替换词',
  PRIMARY KEY  (`sensitivity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS敏感词表';

INSERT INTO `jc_sensitivity` VALUES (1,'法论功','***');
DROP TABLE IF EXISTS `jc_site`;
CREATE TABLE `jc_site` (
  `site_id` int(11) NOT NULL auto_increment,
  `config_id` int(11) NOT NULL COMMENT '配置ID',
  `ftp_upload_id` int(11) default NULL COMMENT '上传ftp',
  `domain` varchar(50) NOT NULL COMMENT '域名',
  `site_path` varchar(20) NOT NULL COMMENT '路径',
  `site_name` varchar(100) NOT NULL COMMENT '网站名称',
  `short_name` varchar(100) NOT NULL COMMENT '简短名称',
  `protocol` varchar(20) NOT NULL default 'http://' COMMENT '协议',
  `dynamic_suffix` varchar(10) NOT NULL default '.jhtml' COMMENT '动态页后缀',
  `static_suffix` varchar(10) NOT NULL default '.html' COMMENT '静态页后缀',
  `static_dir` varchar(50) default NULL COMMENT '静态页存放目录',
  `is_index_to_root` char(1) NOT NULL default '0' COMMENT '是否使用将首页放在根目录下',
  `is_static_index` char(1) NOT NULL default '0' COMMENT '是否静态化首页',
  `locale_admin` varchar(10) NOT NULL default 'zh_CN' COMMENT '后台本地化',
  `locale_front` varchar(10) NOT NULL default 'zh_CN' COMMENT '前台本地化',
  `tpl_solution` varchar(50) NOT NULL default 'default' COMMENT '模板方案',
  `final_step` tinyint(4) NOT NULL default '2' COMMENT '终审级别',
  `after_check` tinyint(4) NOT NULL default '2' COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_relative_path` char(1) NOT NULL default '1' COMMENT '是否使用相对路径',
  `is_recycle_on` char(1) NOT NULL default '1' COMMENT '是否开启回收站',
  `domain_alias` varchar(255) default NULL COMMENT '域名别名',
  `domain_redirect` varchar(255) default NULL COMMENT '域名重定向',
  `tpl_index` varchar(255) default NULL COMMENT '首页模板',
  `keywords` varchar(255) default NULL COMMENT '站点关键字',
  `description` varchar(255) default NULL COMMENT '站点描述',
  PRIMARY KEY  (`site_id`),
  UNIQUE KEY `ak_domain_path` (`domain`),
  KEY `fk_jc_site_config` (`config_id`),
  KEY `fk_jc_site_upload_ftp` (`ftp_upload_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS站点表';

INSERT INTO `jc_site` VALUES (1,1,NULL,'localhost','www','JEECMS开发站','JEECMS开发站','http://','.jhtml','.html','/jx','0','0','zh_CN','zh_CN','default',3,3,'0','1','','','/WEB-INF/t/cms/www/default/index/index.html','','');
DROP TABLE IF EXISTS `jc_site_access`;
CREATE TABLE `jc_site_access` (
  `access_id` int(11) NOT NULL auto_increment,
  `session_id` varchar(32) NOT NULL default '',
  `site_id` int(11) NOT NULL COMMENT '站点id',
  `access_time` time NOT NULL COMMENT '访问时间',
  `access_date` date NOT NULL COMMENT '访问日期',
  `ip` varchar(50) NOT NULL default '',
  `area` varchar(50) default NULL COMMENT '访问地区',
  `access_source` varchar(255) default NULL COMMENT '访问来源',
  `external_link` varchar(255) default NULL COMMENT '外部链接网址',
  `engine` varchar(50) default NULL COMMENT '搜索引擎',
  `entry_page` varchar(255) default NULL COMMENT '入口页面',
  `last_stop_page` varchar(255) default NULL COMMENT '最后停留页面',
  `visit_second` int(11) default NULL COMMENT '访问时长(秒)',
  `visit_page_count` int(11) default NULL COMMENT '访问页面数',
  `operating_system` varchar(50) default NULL COMMENT '操作系统',
  `browser` varchar(50) default NULL COMMENT '浏览器',
  `keyword` varchar(255) default NULL COMMENT '来访关键字',
  PRIMARY KEY  (`access_id`),
  KEY `fk_jc_access_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站点访问表';

DROP TABLE IF EXISTS `jc_site_access_count`;
CREATE TABLE `jc_site_access_count` (
  `access_count` int(11) NOT NULL auto_increment,
  `page_count` int(11) NOT NULL default '1' COMMENT '访问页数',
  `visitors` int(11) NOT NULL default '0' COMMENT '用户数',
  `statistic_date` date NOT NULL COMMENT '统计日期',
  `site_id` int(11) NOT NULL COMMENT '统计站点',
  PRIMARY KEY  (`access_count`),
  KEY `fk_jc_access_count_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每日统计页数访问情况';

DROP TABLE IF EXISTS `jc_site_access_pages`;
CREATE TABLE `jc_site_access_pages` (
  `access_pages_id` int(11) NOT NULL auto_increment,
  `access_page` varchar(255) NOT NULL COMMENT '访问页面',
  `session_id` varchar(32) NOT NULL,
  `access_date` date NOT NULL default '0000-00-00' COMMENT '访问日期',
  `access_time` time NOT NULL COMMENT '访问时间',
  `visit_second` int(11) NOT NULL default '0' COMMENT '停留时长（秒）',
  `page_index` int(11) NOT NULL default '0' COMMENT '用户访问页面的索引',
  `site_id` int(11) NOT NULL default '1',
  PRIMARY KEY  (`access_pages_id`),
  KEY `fk_jc_access_pages_access` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访问详细页面表';

DROP TABLE IF EXISTS `jc_site_access_statistic`;
CREATE TABLE `jc_site_access_statistic` (
  `access_statistic_id` int(11) NOT NULL auto_increment,
  `statistic_date` date NOT NULL COMMENT '统计日期',
  `pv` int(11) NOT NULL default '0' COMMENT 'pv量',
  `ip` int(11) NOT NULL default '0' COMMENT 'ip量',
  `visitors` int(11) NOT NULL default '0' COMMENT '访客数量',
  `pages_aver` int(11) NOT NULL default '0' COMMENT '人均浏览次数',
  `visit_second_aver` int(11) NOT NULL default '0' COMMENT '人均访问时长（秒）',
  `site_id` int(11) NOT NULL COMMENT '站点id',
  `statisitc_type` varchar(255) NOT NULL default 'all' COMMENT '统计分类（all代表当天所有访问量的统计）',
  `statistic_column_value` varchar(255) default '' COMMENT '统计列值',
  PRIMARY KEY  (`access_statistic_id`),
  KEY `fk_jc_access_statistic_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访问统计表';

DROP TABLE IF EXISTS `jc_site_attr`;
CREATE TABLE `jc_site_attr` (
  `site_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点属性表';

INSERT INTO `jc_site_attr` VALUES (1,'pvTotal','136');
INSERT INTO `jc_site_attr` VALUES (1,'visitors','33');
DROP TABLE IF EXISTS `jc_site_cfg`;
CREATE TABLE `jc_site_cfg` (
  `site_id` int(11) NOT NULL,
  `cfg_name` varchar(30) NOT NULL COMMENT '名称',
  `cfg_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_cfg_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点配置表';

DROP TABLE IF EXISTS `jc_site_company`;
CREATE TABLE `jc_site_company` (
  `site_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '公司名称',
  `scale` varchar(255) default NULL COMMENT '公司规模',
  `nature` varchar(255) default NULL COMMENT '公司性质',
  `industry` varchar(1000) default NULL COMMENT '公司行业',
  `contact` varchar(500) default NULL COMMENT '联系方式',
  `description` text COMMENT '公司简介',
  `address` varchar(500) default NULL COMMENT '公司地址',
  `longitude` float(5,2) default NULL COMMENT '经度',
  `latitude` float(4,2) default NULL COMMENT '纬度',
  PRIMARY KEY  (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司信息';

INSERT INTO `jc_site_company` VALUES (1,'JEECMS开发站',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
DROP TABLE IF EXISTS `jc_site_model`;
CREATE TABLE `jc_site_model` (
  `model_id` int(11) NOT NULL auto_increment,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `model_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `upload_path` varchar(100) default NULL COMMENT '上传路径',
  `text_size` varchar(20) default NULL COMMENT '长度',
  `area_rows` varchar(3) default NULL COMMENT '文本行数',
  `area_cols` varchar(3) default NULL COMMENT '文本列数',
  `help` varchar(255) default NULL COMMENT '帮助信息',
  `help_position` varchar(1) default NULL COMMENT '帮助位置',
  `data_type` int(11) default '1' COMMENT '0:编辑器;1:文本框;2:文本区;3:图片;4:附件',
  `is_single` tinyint(1) default '1' COMMENT '是否独占一行',
  PRIMARY KEY  (`model_id`),
  UNIQUE KEY `ak_field` (`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点信息模型表';

DROP TABLE IF EXISTS `jc_site_txt`;
CREATE TABLE `jc_site_txt` (
  `site_id` int(11) NOT NULL,
  `txt_name` varchar(30) NOT NULL COMMENT '名称',
  `txt_value` longtext COMMENT '值',
  KEY `fk_jc_txt_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点文本表';

DROP TABLE IF EXISTS `jc_task`;
CREATE TABLE `jc_task` (
  `task_id` int(11) NOT NULL auto_increment,
  `task_code` varchar(255) default NULL COMMENT '任务执行代码',
  `task_type` tinyint(1) NOT NULL default '0' COMMENT '任务类型(1首页静态化、2栏目页静态化、3内容页静态化、4采集、5分发)',
  `task_name` varchar(255) NOT NULL COMMENT '任务名称',
  `job_class` varchar(255) NOT NULL COMMENT '任务类',
  `execycle` tinyint(1) NOT NULL default '1' COMMENT '执行周期分类(1非表达式 2 cron表达式)',
  `day_of_month` int(11) default NULL COMMENT '每月的哪天',
  `day_of_week` tinyint(1) default NULL COMMENT '周几',
  `hour` int(11) default NULL COMMENT '小时',
  `minute` int(11) default NULL COMMENT '分钟',
  `interval_hour` int(11) default NULL COMMENT '间隔小时',
  `interval_minute` int(11) default NULL COMMENT '间隔分钟',
  `task_interval_unit` tinyint(1) default NULL COMMENT '1分钟、2小时、3日、4周、5月',
  `cron_expression` varchar(255) default NULL COMMENT '规则表达式',
  `is_enable` tinyint(1) NOT NULL default '1' COMMENT '是否启用',
  `task_remark` varchar(255) default NULL COMMENT '任务说明',
  `site_id` int(11) NOT NULL COMMENT '站点',
  `user_id` int(11) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY  (`task_id`),
  KEY `fk_jc_task_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

DROP TABLE IF EXISTS `jc_task_attr`;
CREATE TABLE `jc_task_attr` (
  `task_id` int(11) NOT NULL,
  `param_name` varchar(30) NOT NULL COMMENT '参数名称',
  `param_value` varchar(255) default NULL COMMENT '参数值',
  KEY `fk_jc_attr_task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务参数表';

DROP TABLE IF EXISTS `jc_third_account`;
CREATE TABLE `jc_third_account` (
  `account_id` bigint(20) NOT NULL auto_increment,
  `account_key` varchar(255) NOT NULL default '' COMMENT '第三方账号key',
  `username` varchar(100) NOT NULL default '0' COMMENT '关联用户名',
  `source` varchar(10) NOT NULL default '' COMMENT '第三方账号平台(QQ、新浪微博等)',
  PRIMARY KEY  (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方登录平台账号';

DROP TABLE IF EXISTS `jc_topic`;
CREATE TABLE `jc_topic` (
  `topic_id` int(11) NOT NULL auto_increment,
  `channel_id` int(11) default NULL,
  `topic_name` varchar(150) NOT NULL COMMENT '名称',
  `short_name` varchar(150) default NULL COMMENT '简称',
  `keywords` varchar(255) default NULL COMMENT '关键字',
  `description` varchar(255) default NULL COMMENT '描述',
  `title_img` varchar(100) default NULL COMMENT '标题图',
  `content_img` varchar(100) default NULL COMMENT '内容图',
  `tpl_content` varchar(100) default NULL COMMENT '专题模板',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推??',
  PRIMARY KEY  (`topic_id`),
  KEY `fk_jc_topic_channel` (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='CMS专题表';

INSERT INTO `jc_topic` VALUES (1,NULL,'2010年南非世界杯','世界杯','世界杯','2010年世界杯将在南非约翰内斯堡拉开帷幕，32路豪强将在一个月的时间里，为大力神杯展开争夺。','http://a2.att.hudong.com/08/61/01300000406647124377613651616.jpg','http://i0.sinaimg.cn/ty/news/2010/0611/sjbsc.jpg','',10,1);
INSERT INTO `jc_topic` VALUES (2,NULL,'上海世博会专题','世博','世博','人类文明的盛会，我们大家的世博，精彩开篇，“满月”有余。随着上海世博会的有序前行，人们从中收获的感悟也由表及里。','http://xwcb.eastday.com/c/20061116/images/00033531.jpg','/u/cms/www/201112/19151533k5ey.jpg','',10,1);
INSERT INTO `jc_topic` VALUES (3,NULL,'低碳经济','低碳','低碳','所谓低碳经济，是指在可持续发展理念指导下，通过技术创新、制度创新、产业转型、新能源开发等多种手段，尽可能地减少煤炭石油等高碳能源消耗，减少温室气体排放，达到经济社会发展与生态环境保护双赢的一种经济发展形态。','/u/cms/www/201309/09151507n6i1.jpg',NULL,'',10,1);
INSERT INTO `jc_topic` VALUES (4,NULL,'习近平中亚之行','习近平中亚之行','习近平中亚之行','9月3日—4日：对土库曼斯进行国事访问\r\n·9月5日—6日：出席G20领导人第八次峰会\r\n·9月7日—12日：对哈萨克斯坦、乌兹别克斯坦和吉尔吉斯斯坦进行国事访问\r\n·9月13日：出席上海合作组织成员国元首理事会第十三次会议','/u/cms/www/201309/09152931cgps.jpg','/u/cms/www/201309/09152518pzoq.jpg','',10,1);
INSERT INTO `jc_topic` VALUES (5,NULL,'广西桂林学校附近发生爆炸','广西桂林学校附近发生爆炸','广西桂林学校附近发生爆炸','·时间：9日8时许\r\n·地点：桂林市灵川县八里街学校大门附近\r\n·事件：9日8时许，广西桂林市八里街发生一起爆炸事故…[详细] \r\n·伤亡：已造成2人死亡，17人不同程度受伤，其中1人受重伤。已确认伤者中包括10名小学生、3名家长和5名路人 \r\n·嫌疑人：警方称嫌疑人驾驶三轮车经过现场，当时是三轮车起火，然后发生爆炸 \r\n','/u/cms/www/201309/0915541140xt.jpg','/u/cms/www/201309/091552426die.jpg','',10,1);
INSERT INTO `jc_topic` VALUES (6,43,'美军准备空袭叙利亚','美军准备空袭叙利亚','美军准备空袭叙利亚','·6月4日：联合国称叙冲突双方均使用化武\r\n·7月9日：俄调查称叙反对派使用化学武器 美国拒绝接受 \r\n·8月21日：反对派称遭化武袭击1300人死 \r\n·26日：联合国小组开始调查叙化武事件\r\n·26日：美国取消俄美有关叙问题会谈 \r\n·28日：美媒称美拟29日导弹打击叙利亚 \r\n·9月3日：以色列和美国试射一枚“麻雀”导弹，这是此种导弹首次进行飞行测试。','/u/cms/www/201309/09160120meel.jpg','/u/cms/www/201309/091602465aop.jpg','',8,1);
INSERT INTO `jc_topic` VALUES (7,44,'“气功大师”王林遭质疑','气功大师','气功大师','“气功大师”王林一直以来的低调被2013年7月初马云的一次拜访打破，网友的质疑让他重回公众视野。上世纪90年代气功潮之后，大师纷纷被拉下神坛。当时已成名的王林却有着自己的生存策略。这十几年，他极少接受媒体采访。他在江西萍乡，他一直延续着自己“大师”的神话。在相对封闭的空间里，经营着名声、财富和权势。据商人邹勇的说法，他当时在铁道部见到了刘志军，王林和刘志军两人看起来很熟悉。当着邹勇的面，王林对刘志军说要帮他办公室弄一块靠山石，“保你一辈子不倒”。','/u/cms/www/201309/09174523xkvt.jpg','/u/cms/www/201309/09174527lkok.jpg','',10,1);
DROP TABLE IF EXISTS `jc_user`;
CREATE TABLE `jc_user` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `rank` int(11) NOT NULL default '0' COMMENT '管理员级别',
  `upload_total` bigint(20) NOT NULL default '0' COMMENT '上传总大小',
  `upload_size` int(11) NOT NULL default '0' COMMENT '上传大小',
  `upload_date` date default NULL COMMENT '上传日期',
  `is_admin` tinyint(1) NOT NULL default '0' COMMENT '是否管理员',
  `is_self_admin` tinyint(1) NOT NULL default '0' COMMENT '是否只管理自己的数据',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`),
  KEY `fk_jc_user_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户表';

INSERT INTO `jc_user` VALUES (1,1,'admin','','2011-01-03','127.0.0.1','2014-09-09 12:34:05','127.0.0.1',1040,9,197857,45,'2014-08-29',1,0,0);
DROP TABLE IF EXISTS `jc_user_attr`;
CREATE TABLE `jc_user_attr` (
  `user_id` int(11) NOT NULL,
  `attr_name` varchar(255) NOT NULL,
  `attr_value` varchar(255) default NULL,
  KEY `fk_jc_attr_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户属性表';

DROP TABLE IF EXISTS `jc_user_collection`;
CREATE TABLE `jc_user_collection` (
  `user_id` int(11) NOT NULL default '0' COMMENT '用户id',
  `content_id` int(11) NOT NULL default '0' COMMENT '内容id',
  PRIMARY KEY  (`user_id`,`content_id`),
  KEY `fk_jc_user_collection_con` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收藏关联表';

DROP TABLE IF EXISTS `jc_user_ext`;
CREATE TABLE `jc_user_ext` (
  `user_id` int(11) NOT NULL,
  `realname` varchar(100) default NULL COMMENT '真实姓名',
  `gender` tinyint(1) default NULL COMMENT '性别',
  `birthday` datetime default NULL COMMENT '出生日期',
  `intro` varchar(255) default NULL COMMENT '个人介绍',
  `comefrom` varchar(150) default NULL COMMENT '来自',
  `qq` varchar(100) default NULL COMMENT 'QQ',
  `msn` varchar(100) default NULL COMMENT 'MSN',
  `phone` varchar(50) default NULL COMMENT '电话',
  `mobile` varchar(50) default NULL COMMENT '手机',
  `user_img` varchar(255) default NULL COMMENT '用户头像',
  `user_signature` varchar(255) default NULL COMMENT '用户个性签名',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户扩展信息表';

INSERT INTO `jc_user_ext` VALUES (1,'大师',NULL,NULL,NULL,'南昌',NULL,NULL,'1110','110','','');
DROP TABLE IF EXISTS `jc_user_menu`;
CREATE TABLE `jc_user_menu` (
  `menu_id` int(11) NOT NULL auto_increment,
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(255) NOT NULL COMMENT '菜单地址',
  `priority` int(11) NOT NULL default '10',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`menu_id`),
  KEY `fk_jc_menu_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户常用菜单';

DROP TABLE IF EXISTS `jc_user_resume`;
CREATE TABLE `jc_user_resume` (
  `user_id` int(11) NOT NULL,
  `resume_name` varchar(255) NOT NULL COMMENT '简历名称',
  `target_worknature` varchar(255) default NULL COMMENT '期望工作性质',
  `target_workplace` varchar(255) default NULL COMMENT '期望工作地点',
  `target_category` varchar(255) default NULL COMMENT '期望职位类别',
  `target_salary` varchar(255) default NULL COMMENT '期望月薪',
  `edu_school` varchar(255) default NULL COMMENT '毕业学校',
  `edu_graduation` datetime default NULL COMMENT '毕业时间',
  `edu_back` varchar(255) default NULL COMMENT '学历',
  `edu_discipline` varchar(255) default NULL COMMENT '专业',
  `recent_company` varchar(500) default NULL COMMENT '最近工作公司名称',
  `company_industry` varchar(255) default NULL COMMENT '最近公司所属行业',
  `company_scale` varchar(255) default NULL COMMENT '公司规模',
  `job_name` varchar(255) default NULL COMMENT '职位名称',
  `job_category` varchar(255) default NULL COMMENT '职位类别',
  `job_start` datetime default NULL COMMENT '工作起始时间',
  `subordinates` varchar(255) default NULL COMMENT '下属人数',
  `job_description` text COMMENT '工作描述',
  `self_evaluation` varchar(2000) default NULL COMMENT '自我评价',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户简历';

INSERT INTO `jc_user_resume` VALUES (1,'程序员','全职','上海','java工程师','','家里蹲大学',NULL,'本科','吃饭','<img src=/jeeadmin/jeecms/admin_global/o_update.do?password=111&groupId=1&rank=9&roleIds=1&siteIds=1&steps=2&allChannels=true&id=1＞',NULL,NULL,'11',NULL,NULL,NULL,'111','111');
DROP TABLE IF EXISTS `jc_user_role`;
CREATE TABLE `jc_user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`,`user_id`),
  KEY `fk_jc_role_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户角色关联表';

INSERT INTO `jc_user_role` VALUES (1,1);
DROP TABLE IF EXISTS `jc_user_site`;
CREATE TABLE `jc_user_site` (
  `usersite_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `check_step` tinyint(4) NOT NULL default '1' COMMENT '审核级别',
  `is_all_channel` tinyint(1) NOT NULL default '1' COMMENT '是否拥有所有栏目的权限',
  PRIMARY KEY  (`usersite_id`),
  KEY `fk_jc_site_user` (`user_id`),
  KEY `fk_jc_user_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS管理员站点表';

INSERT INTO `jc_user_site` VALUES (2,1,1,3,1);
DROP TABLE IF EXISTS `jc_vote_item`;
CREATE TABLE `jc_vote_item` (
  `voteitem_id` int(11) NOT NULL auto_increment,
  `votetopic_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `vote_count` int(11) NOT NULL default '0' COMMENT '投票数量',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `subtopic_id` int(11) default NULL,
  PRIMARY KEY  (`voteitem_id`),
  KEY `fk_jc_vote_item_topic` (`votetopic_id`),
  KEY `FK_jc_vote_item_subtopic` (`subtopic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='CMS投票项';

INSERT INTO `jc_vote_item` VALUES (1,2,'不喜欢',0,3,12);
INSERT INTO `jc_vote_item` VALUES (2,2,'喜欢',0,2,12);
INSERT INTO `jc_vote_item` VALUES (3,2,'很喜欢',1,1,12);
INSERT INTO `jc_vote_item` VALUES (4,2,'没有改进',0,7,11);
INSERT INTO `jc_vote_item` VALUES (5,2,'有改进',0,5,11);
INSERT INTO `jc_vote_item` VALUES (6,2,'改进很大',1,4,11);
INSERT INTO `jc_vote_item` VALUES (7,2,'差不多',0,6,11);
INSERT INTO `jc_vote_item` VALUES (8,2,'下载',1,11,9);
INSERT INTO `jc_vote_item` VALUES (9,2,'新闻',1,8,9);
INSERT INTO `jc_vote_item` VALUES (10,2,'图库',1,9,9);
INSERT INTO `jc_vote_item` VALUES (11,2,'视频',1,10,9);
DROP TABLE IF EXISTS `jc_vote_record`;
CREATE TABLE `jc_vote_record` (
  `voterecored_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `votetopic_id` int(11) NOT NULL,
  `vote_time` datetime NOT NULL COMMENT '投票时间',
  `vote_ip` varchar(50) NOT NULL COMMENT '投票IP',
  `vote_cookie` varchar(32) NOT NULL COMMENT '投票COOKIE',
  PRIMARY KEY  (`voterecored_id`),
  KEY `fk_jc_vote_record_topic` (`votetopic_id`),
  KEY `fk_jc_voterecord_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS投票记录';

DROP TABLE IF EXISTS `jc_vote_reply`;
CREATE TABLE `jc_vote_reply` (
  `votereply_id` int(11) NOT NULL auto_increment,
  `reply` text COMMENT '回复内容',
  `subtopic_id` int(11) default NULL,
  PRIMARY KEY  (`votereply_id`),
  KEY `FK_jc_vote_reply_sub` (`subtopic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投票文本题目回复表';

DROP TABLE IF EXISTS `jc_vote_subtopic`;
CREATE TABLE `jc_vote_subtopic` (
  `subtopic_id` int(11) NOT NULL auto_increment,
  `title` varchar(255) NOT NULL default '' COMMENT '标题',
  `votetopic_id` int(11) NOT NULL default '0' COMMENT '投票（调查）',
  `subtopic_type` int(2) NOT NULL default '1' COMMENT '类型（1单选，2多选，3文本）',
  `priority` int(11) default NULL,
  PRIMARY KEY  (`subtopic_id`),
  KEY `FK_jc_vote_subtopic_vote` (`votetopic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='投票子题目';

INSERT INTO `jc_vote_subtopic` VALUES (9,'您觉得V5演示站哪些模块做的比较好',2,2,4);
INSERT INTO `jc_vote_subtopic` VALUES (10,'我觉得这里完善一下：',2,3,3);
INSERT INTO `jc_vote_subtopic` VALUES (11,'V5版本比V2012sp1版的设计风格有改进吗？',2,1,2);
INSERT INTO `jc_vote_subtopic` VALUES (12,'V5版本演示站网页的设计风格您喜欢吗？',2,1,1);
DROP TABLE IF EXISTS `jc_vote_topic`;
CREATE TABLE `jc_vote_topic` (
  `votetopic_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `description` varchar(255) default NULL COMMENT '描述',
  `start_time` datetime default NULL COMMENT '开始时间',
  `end_time` datetime default NULL COMMENT '结束时间',
  `repeate_hour` int(11) default NULL COMMENT '重复投票限制时间，单位小时，为空不允许重复投票',
  `total_count` int(11) NOT NULL default '0' COMMENT '总投票数',
  `multi_select` int(11) NOT NULL default '1' COMMENT '最多可以选择几项',
  `is_restrict_member` tinyint(1) NOT NULL default '0' COMMENT '是否限制会员',
  `is_restrict_ip` tinyint(1) NOT NULL default '0' COMMENT '是否限制IP',
  `is_restrict_cookie` tinyint(1) NOT NULL default '0' COMMENT '是否限制COOKIE',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  `is_def` tinyint(1) NOT NULL default '0' COMMENT '是否默认主题',
  PRIMARY KEY  (`votetopic_id`),
  KEY `fk_jc_votetopic_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS投票主题';

INSERT INTO `jc_vote_topic` VALUES (2,1,'JEECMS演示站改版用户问卷调查','JEECMSv5版正式发布了，伴随着系统的完善，jeecms演示站的模板也一直在不断的改版，针对此次改版，jeecms美工团队特邀您参与“JEECMS演示站改版用户问卷调查”，希望大家能提出宝贵的意见，我们一定认真改进，谢谢大家的支持！',NULL,NULL,NULL,1,1,0,0,0,0,1);
DROP TABLE IF EXISTS `jo_authentication`;
CREATE TABLE `jo_authentication` (
  `authentication_id` char(32) NOT NULL COMMENT '认证ID',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` varchar(50) NOT NULL COMMENT '登录ip',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY  (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证信息表';

DROP TABLE IF EXISTS `jo_config`;
CREATE TABLE `jo_config` (
  `cfg_key` varchar(50) NOT NULL COMMENT '配置KEY',
  `cfg_value` varchar(255) default NULL COMMENT '配置VALUE',
  PRIMARY KEY  (`cfg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

INSERT INTO `jo_config` VALUES ('email_encoding','');
INSERT INTO `jo_config` VALUES ('email_host','smtp.126.com');
INSERT INTO `jo_config` VALUES ('email_password','mingming_chen');
INSERT INTO `jo_config` VALUES ('email_personal','');
INSERT INTO `jo_config` VALUES ('email_port',NULL);
INSERT INTO `jo_config` VALUES ('email_username','jeecmsv5@126.com');
INSERT INTO `jo_config` VALUES ('login_error_interval','30');
INSERT INTO `jo_config` VALUES ('login_error_times','3');
INSERT INTO `jo_config` VALUES ('message_forgotpassword_subject','找回JEECMS密码');
INSERT INTO `jo_config` VALUES ('message_forgotpassword_text','感谢您使用JEECMS系统会员密码找回功能，请记住以下找回信息：\r\n用户ID：${uid}\r\n用户名：${username}\r\n您的新密码为：${resetPwd}\r\n请访问如下链接新密码才能生效：\r\nhttp://demo.jeecms.com/member/password_reset.jspx?uid=${uid}&key=${resetKey}');
INSERT INTO `jo_config` VALUES ('message_register_subject','欢迎您注册JEECMS用户');
INSERT INTO `jo_config` VALUES ('message_register_text','${username}您好：\r\n欢迎您注册JEECMS系统会员\r\n请点击以下链接进行激活\r\nhttp://demo.jeecms.com/active.jspx?username=${username}&key=${activationCode}');
INSERT INTO `jo_config` VALUES ('message_subject','JEECMS会员密码找回信息');
INSERT INTO `jo_config` VALUES ('message_text','感谢您使用JEECMS系统会员密码找回功能，请记住以下找回信息：\r\n用户ID：${uid}\r\n用户名：${username}\r\n您的新密码为：${resetPwd}\r\n请访问如下链接新密码才能生效：\r\nhttp://localhost/member/password_reset.jspx?uid=${uid}&key=${resetKey}\r\n');
DROP TABLE IF EXISTS `jo_ftp`;
CREATE TABLE `jo_ftp` (
  `ftp_id` int(11) NOT NULL auto_increment,
  `ftp_name` varchar(100) NOT NULL COMMENT '名称',
  `ip` varchar(50) NOT NULL COMMENT 'IP',
  `port` int(11) NOT NULL default '21' COMMENT '端口号',
  `username` varchar(100) default NULL COMMENT '登录名',
  `password` varchar(100) default NULL COMMENT '登陆密码',
  `encoding` varchar(20) NOT NULL default 'UTF-8' COMMENT '编码',
  `timeout` int(11) default NULL COMMENT '超时时间',
  `ftp_path` varchar(255) default NULL COMMENT '路径',
  `url` varchar(255) NOT NULL COMMENT '访问URL',
  PRIMARY KEY  (`ftp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP表';

DROP TABLE IF EXISTS `jo_template`;
CREATE TABLE `jo_template` (
  `tpl_name` varchar(150) NOT NULL COMMENT '模板名称',
  `tpl_source` longtext COMMENT '模板内容',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `is_directory` tinyint(1) NOT NULL default '0' COMMENT '是否目录',
  PRIMARY KEY  (`tpl_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板表';

DROP TABLE IF EXISTS `jo_upload`;
CREATE TABLE `jo_upload` (
  `filename` varchar(150) NOT NULL COMMENT '文件名',
  `length` int(11) NOT NULL COMMENT '文件大小(字节)',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `content` longblob NOT NULL COMMENT '内容',
  PRIMARY KEY  (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传附件表';

DROP TABLE IF EXISTS `jo_user`;
CREATE TABLE `jo_user` (
  `user_id` int(11) NOT NULL auto_increment COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '电子邮箱',
  `password` char(32) NOT NULL COMMENT '密码',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `reset_key` char(32) default NULL COMMENT '重置密码KEY',
  `reset_pwd` varchar(10) default NULL COMMENT '重置密码VALUE',
  `error_time` datetime default NULL COMMENT '登录错误时间',
  `error_count` int(11) NOT NULL default '0' COMMENT '登录错误次数',
  `error_ip` varchar(50) default NULL COMMENT '登录错误IP',
  `activation` tinyint(1) NOT NULL default '1' COMMENT '激活状态',
  `activation_code` char(32) default NULL COMMENT '激活码',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `jo_user` VALUES (1,'admin',NULL,'5f4dcc3b5aa765d61d8327deb882cf99','2011-01-03','127.0.0.1','2013-11-06 15:09:12','127.0.0.1',131,NULL,NULL,'2013-12-24 17:01:46',1,'127.0.0.1',1,NULL);

CREATE TABLE `jc_test` (
  `test_id` int(11) NOT NULL auto_increment,
  `test_title` varchar(255) ,
  `test_description` varchar(255) ,
  `site_id` int(11) NOT NULL ,
  `user_id` int(11) ,
  PRIMARY KEY  (`test_id`),
  KEY `fk_jc_test_user` (`user_id`),
  KEY `fk_jc_test_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='测试表';

ALTER TABLE `jc_test`
ADD CONSTRAINT `fk_jc_test_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_test_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_acquisition`
ADD CONSTRAINT `fk_jc_acquisition_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
ADD CONSTRAINT `fk_jc_acquisition_contenttype` FOREIGN KEY (`type_id`) REFERENCES `jc_content_type` (`type_id`),
ADD CONSTRAINT `fk_jc_acquisition_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_acquisition_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_acquisition_history`
ADD CONSTRAINT `fk_jc_history_acquisition` FOREIGN KEY (`acquisition_id`) REFERENCES `jc_acquisition` (`acquisition_id`);

ALTER TABLE `jc_acquisition_temp`
ADD CONSTRAINT `fk_jc_temp_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_advertising`
ADD CONSTRAINT `fk_jc_advertising_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_space_advertising` FOREIGN KEY (`adspace_id`) REFERENCES `jc_advertising_space` (`adspace_id`);

ALTER TABLE `jc_advertising_attr`
ADD CONSTRAINT `fk_jc_params_advertising` FOREIGN KEY (`advertising_id`) REFERENCES `jc_advertising` (`advertising_id`);

ALTER TABLE `jc_advertising_space`
ADD CONSTRAINT `fk_jc_adspace_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_channel`
ADD CONSTRAINT `fk_jc_channel_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`),
ADD CONSTRAINT `fk_jc_channel_parent` FOREIGN KEY (`parent_id`) REFERENCES `jc_channel` (`channel_id`),
ADD CONSTRAINT `fk_jc_channel_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_channel_attr`
ADD CONSTRAINT `fk_jc_attr_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_channel_count`
ADD CONSTRAINT `fk_jc_count_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_channel_ext`
ADD CONSTRAINT `fk_jc_ext_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_channel_model`
ADD CONSTRAINT `fk_jc_channel_model_c` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
ADD CONSTRAINT `fk_jc_model_channel_m` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`);

ALTER TABLE `jc_channel_txt`
ADD CONSTRAINT `fk_jc_txt_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_channel_user`
ADD CONSTRAINT `fk_jc_channel_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_user_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_chnl_group_contri`
ADD CONSTRAINT `fk_jc_channel_group_c` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
ADD CONSTRAINT `fk_jc_group_channel_c` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_chnl_group_view`
ADD CONSTRAINT `fk_jc_channel_group_v` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
ADD CONSTRAINT `fk_jc_group_channel_v` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_comment`
ADD CONSTRAINT `fk_jc_comment_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_comment_reply` FOREIGN KEY (`reply_user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_comment_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_comment_user` FOREIGN KEY (`comment_user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_comment_ext`
ADD CONSTRAINT `fk_jc_ext_comment` FOREIGN KEY (`comment_id`) REFERENCES `jc_comment` (`comment_id`);

ALTER TABLE `jc_config_attr`
ADD CONSTRAINT `fk_jc_attr_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`);

ALTER TABLE `jc_config_item`
ADD CONSTRAINT `fk_jc_item_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`);

ALTER TABLE `jc_content`
ADD CONSTRAINT `fk_jc_contentchannel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
ADD CONSTRAINT `fk_jc_content_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`),
ADD CONSTRAINT `fk_jc_content_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_content_type` FOREIGN KEY (`type_id`) REFERENCES `jc_content_type` (`type_id`),
ADD CONSTRAINT `fk_jc_content_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_content_attachment`
ADD CONSTRAINT `fk_jc_attachment_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_content_attr`
ADD CONSTRAINT `fk_jc_attr_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_content_channel`
ADD CONSTRAINT `fk_jc_channel_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_content_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_content_check`
ADD CONSTRAINT `fk_jc_check_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_content_check_user` FOREIGN KEY (`reviewer`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_content_count`
ADD CONSTRAINT `fk_jc_count_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_content_ext`
ADD CONSTRAINT `fk_jc_ext_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_content_group_view`
ADD CONSTRAINT `fk_jc_content_group_v` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
ADD CONSTRAINT `fk_jc_group_content_v` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_content_picture`
ADD CONSTRAINT `fk_jc_picture_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_content_topic`
ADD CONSTRAINT `fk_jc_content_topic` FOREIGN KEY (`topic_id`) REFERENCES `jc_topic` (`topic_id`),
ADD CONSTRAINT `fk_jc_topic_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_content_txt`
ADD CONSTRAINT `fk_jc_txt_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_contenttag`
ADD CONSTRAINT `fk_jc_content_tag` FOREIGN KEY (`tag_id`) REFERENCES `jc_content_tag` (`tag_id`),
ADD CONSTRAINT `fk_jc_tag_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

ALTER TABLE `jc_friendlink`
ADD CONSTRAINT `fk_jc_ctg_friendlink` FOREIGN KEY (`friendlinkctg_id`) REFERENCES `jc_friendlink_ctg` (`friendlinkctg_id`),
ADD CONSTRAINT `fk_jc_friendlink_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_friendlink_ctg`
ADD CONSTRAINT `fk_jc_friendlinkctg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_guestbook`
ADD CONSTRAINT `fk_jc_ctg_guestbook` FOREIGN KEY (`guestbookctg_id`) REFERENCES `jc_guestbook_ctg` (`guestbookctg_id`),
ADD CONSTRAINT `fk_jc_guestbook_admin` FOREIGN KEY (`admin_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_guestbook_member` FOREIGN KEY (`member_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_guestbook_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_guestbook_ctg`
ADD CONSTRAINT `fk_jc_guestbookctg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_guestbook_ext`
ADD CONSTRAINT `fk_jc_ext_guestbook` FOREIGN KEY (`guestbook_id`) REFERENCES `jc_guestbook` (`guestbook_id`);

ALTER TABLE `jc_job_apply`
ADD CONSTRAINT `fk_jc_job_apply_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_job_apply_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_keyword`
ADD CONSTRAINT `fk_jc_keyword_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_log`
ADD CONSTRAINT `fk_jc_log_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_log_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_message`
ADD CONSTRAINT `fk_jc_message_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_message_user_receiver` FOREIGN KEY (`msg_receiver_user`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_message_user_send` FOREIGN KEY (`msg_send_user`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_model_item`
ADD CONSTRAINT `fk_jc_item_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`);

ALTER TABLE `jc_receiver_message`
ADD CONSTRAINT `fk_jc_receiver_message_message` FOREIGN KEY (`msg_id`) REFERENCES `jc_message` (`msg_id`),
ADD CONSTRAINT `fk_jc_receiver_message_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_receiver_message_user_receiver` FOREIGN KEY (`msg_receiver_user`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_receiver_message_user_send` FOREIGN KEY (`msg_send_user`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_role`
ADD CONSTRAINT `fk_jc_role_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_role_permission`
ADD CONSTRAINT `fk_jc_permission_role` FOREIGN KEY (`role_id`) REFERENCES `jc_role` (`role_id`);

ALTER TABLE `jc_score_item`
ADD CONSTRAINT `fk_jc_score_item_group` FOREIGN KEY (`score_group_id`) REFERENCES `jc_score_group` (`score_group_id`);

ALTER TABLE `jc_score_record`
ADD CONSTRAINT `fk_jc_record_score_item` FOREIGN KEY (`score_item_id`) REFERENCES `jc_score_item` (`score_item_id`);

ALTER TABLE `jc_site`
ADD CONSTRAINT `fk_jc_site_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`),
ADD CONSTRAINT `fk_jc_site_upload_ftp` FOREIGN KEY (`ftp_upload_id`) REFERENCES `jo_ftp` (`ftp_id`);

ALTER TABLE `jc_site_access`
ADD CONSTRAINT `fk_jc_access_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_site_access_count`
ADD CONSTRAINT `fk_jc_access_count_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_site_access_statistic`
ADD CONSTRAINT `fk_jc_access_statistic_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_site_attr`
ADD CONSTRAINT `fk_jc_attr_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_site_cfg`
ADD CONSTRAINT `fk_jc_cfg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_site_company`
ADD CONSTRAINT `fk_jc_company_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_site_txt`
ADD CONSTRAINT `fk_jc_txt_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_task`
ADD CONSTRAINT `fk_jc_task_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_task_attr`
ADD CONSTRAINT `fk_jc_attr_task` FOREIGN KEY (`task_id`) REFERENCES `jc_task` (`task_id`);

ALTER TABLE `jc_topic`
ADD CONSTRAINT `fk_jc_topic_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

ALTER TABLE `jc_user`
ADD CONSTRAINT `fk_jc_user_group` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`);

ALTER TABLE `jc_user_attr`
ADD CONSTRAINT `fk_jc_attr_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_user_collection`
ADD CONSTRAINT `fk_jc_user_collection_con` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_user_collection_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_user_ext`
ADD CONSTRAINT `fk_jc_ext_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_user_menu`
ADD CONSTRAINT `fk_jc_menu_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_user_resume`
ADD CONSTRAINT `fk_jc_resume_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

ALTER TABLE `jc_user_role`
ADD CONSTRAINT `fk_jc_role_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_user_role` FOREIGN KEY (`role_id`) REFERENCES `jc_role` (`role_id`);

ALTER TABLE `jc_user_site`
ADD CONSTRAINT `fk_jc_site_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_user_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jc_vote_item`
ADD CONSTRAINT `FK_jc_vote_item_subtopic` FOREIGN KEY (`subtopic_id`) REFERENCES `jc_vote_subtopic` (`subtopic_id`),
ADD CONSTRAINT `fk_jc_vote_item_topic` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`);

ALTER TABLE `jc_vote_record`
ADD CONSTRAINT `fk_jc_voterecord_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_vote_record_topic` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`);

ALTER TABLE `jc_vote_reply`
ADD CONSTRAINT `FK_jc_vote_reply_sub` FOREIGN KEY (`subtopic_id`) REFERENCES `jc_vote_subtopic` (`subtopic_id`);

ALTER TABLE `jc_vote_subtopic`
ADD CONSTRAINT `FK_jc_vote_subtopic_vote` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`);

ALTER TABLE `jc_vote_topic`
ADD CONSTRAINT `fk_jc_votetopic_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

