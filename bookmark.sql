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

INSERT INTO `user` VALUES (1, 'zhangsan', '123456', 1);
INSERT INTO `user` VALUES (2, 'lisi', '234567', 2);

ALTER TABLE `bookmark`.`book`
CHANGE COLUMN `url` `url` TEXT NULL ,
CHANGE COLUMN `children` `children` TEXT NULL ,
CHANGE COLUMN `favorites` `favorites` INT NULL DEFAULT 0 ;


insert into book values(1,0,0,1,'书签夹1','http://...', '3,', 1, 0);
insert into book values(2,0,0,2,'书签夹2','http://...', '4,5,', 1, 0);

insert into book values(3,1,1,1,'书签1','http://...', '', 1, 0);
insert into book values(4,2,1,2,'书签2','http://...', '', 1, 0);
insert into book values(5,2,1,2,'书签3','http://...', '', 1, 0);