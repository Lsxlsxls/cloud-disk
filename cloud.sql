CREATE TABLE IF NOT EXISTS cloudpan.users3(
   userid int PRIMARY KEY NOT NULL AUTO_INCREMENT,
   username VARCHAR(32) NOT NULL unique,
   password VARCHAR(128) NOT NULL,
   sex char(4) DEFAULT '男' NOT NULL,
   admin integer DEFAULT 0 NOT NULL
)

CREATE TABLE cloudpan.t_files (
  id integer NOT NULL AUTO_INCREMENT,
  oldFileName varchar(200) DEFAULT NULL,
  newFileName varchar(300) DEFAULT NULL,
  ext varchar(20) DEFAULT NULL,
  path varchar(300) DEFAULT NULL,
  size varchar(200) DEFAULT NULL,
  type varchar(120) DEFAULT NULL,
  isImg varchar(8) DEFAULT NULL,
  downcounts integer DEFAULT NULL,
  uploadTime datetime DEFAULT NULL,
  userName varchar(32) DEFAULT NULL,
  isDelete integer DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (userName) REFERENCES cloudpan.users3(username)
)