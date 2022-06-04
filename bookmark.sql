create database bookmark;
use bookmark;

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

INSERT INTO `user` VALUES (1, 'zhangsan', '123456', 0);
INSERT INTO `user` VALUES (2, 'lisi', '234567', 1);
