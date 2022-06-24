# create database bookmark1;
use bookmark1;

create table `user` (
	uid int(11) NOT NULL auto_increment,
    `name` text NOT NULL,
    `password` text NOT NULL,
    rootID int(11) NOT NULL,
    primary key (`uid`)
);

create table `book` (
	ID int(11) NOT NULL,
    parentID int(11) NOT NULL,
    `type` int(11) NOT NULL,
    uid int(11) NOT NULL,
    `title` text NOT NULL,
    `url` text NOT NULL,
    `children` text NOT NULL,
    isPublic int(11) NOT NULL default 0,
    favorites int(11) NOT NULL,
    primary key (ID)
);

ALTER TABLE `bookmark1`.`book`
CHANGE COLUMN `url` `url` TEXT NULL ,
CHANGE COLUMN `children` `children` TEXT NULL ,
CHANGE COLUMN `favorites` `favorites` INT NULL DEFAULT 0 ;

insert into `user` values
(1, 'zhangsan', 123456, 1),
(2, 'lisi', 234567, 2),
(3, 'kk', 123456, 7),
(4, 'wangwu1', 123456, 6);

insert into book values
(1,0,0,1,'书签夹1','http://...', '3,', 1, 0),
(2,0,0,2,'书签夹2','http://...', '4,5,', 1, 0),
(3,1,1,1,'书签1','http://...', '', 1, 0),
(4,2,1,2,'书签2','http://...', '', 1, 0),
(5,2,1,2,'书签3','http://...', '', 1, 0),
(6, 0, 0, 4, 'wangwu1的书签夹', '', '19,', 1, 0),
(7, 0, 0, 3, 'kk的书签夹', '', '8,13,', 1, 0),
(8, 7, 0, 3, '收藏夹栏', '', '9,10,11,12,', 1, 0),
(9, 8, 1, 3, '核心介绍 - injected-script - 《Chrome插件(扩展)开发全攻略》 - 书栈网 · BookStack', 'https://www.bookstack.cn/read/chrome-plugin-develop/spilt.6.spilt.4.8bdb1aac68bbdc44.md', '', 1, 0),
(10, 8, 1, 3, 'Vite App', 'http://47.96.41.120:10028/login', '', 1, 0),
(11, 8, 1, 3, '爱奇艺VIP会员限时优惠', 'https://vip.iqiyi.com/cps_pc.html?fv=eu_9cf981acdf1335b1&fr_version=euid%253D198386556', '', 1, 0),
(12, 8, 1, 3, '爱淘宝PC新版', 'https://ai.taobao.com/?pid=mm_1183900030_1813100136_112219550255&union_lens=lensId%3APUB%401653623469%402104ee54_0a06_18103a3b432_0431%4001', '', 1, 0),
(13, 7, 0, 3, '其他收藏夹', '', '14,15,17,', 1, 0),
(14, 13, 1, 3, '哔哩哔哩 (゜-゜)つロ 干杯~-bilibili', 'https://www.bilibili.com/', '', 1, 0),
(15, 13, 0, 3, 'test01', '', '16,', 1, 0),
(16, 15, 1, 3, 'AcgFun资源网-动漫游戏下载 (=￣ω￣=)喵了个咪~', 'https://www.acgfun.net/', '', 1, 0),
(17, 13, 1, 3, '清单文件--扩展开发文档', 'https://open.chrome.360.cn/extension_dev/manifest.html#manifest_version', '', 1, 0);