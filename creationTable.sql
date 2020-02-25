CREATE USER 'admin'@'%' IDENTIFIED BY 'friends';
GRANT ALL PRIVILEGES ON virtualclassroom.* TO 'admin'@'%';
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE IF NOT EXISTS `USERS`
(
    `id_user`       int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_name`     varchar(200) NOT NULL,
    `user_lastname` varchar(200) NOT NULL,
    `user_email`    varchar(200) NOT NULL,
    `user_password` varchar(50)  NOT NULL,
    `isAdmin`       boolean
);
DROP TABLE IF EXISTS `CLASSROOMS`;
CREATE TABLE IF NOT EXISTS `CLASSROOMS`
(
    `id_classroom`   int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `classroom_name` varchar(50) NOT NULL
);
DROP TABLE IF EXISTS `OPTIONS`;
CREATE TABLE IF NOT EXISTS `OPTIONS`
(
    `id_option`      int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `option_content` varchar(255) NOT NULL,
    `isAdmin`        boolean
);
DROP TABLE IF EXISTS `INFORMATION`;
CREATE TABLE IF NOT EXISTS `INFORMATION`
(
    `id_information`    int AUTO_INCREMENT PRIMARY KEY,
    `information_label` Varchar(50)  NOT NULL,
    `information_url`   Varchar(255) NOT NULL
);
DROP TABLE IF EXISTS `QUESTIONS`;
CREATE TABLE IF NOT EXISTS `QUESTIONS`
(
    `id_question`      int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `question_content` varchar(255) NOT NULL,
    `isRadio`          boolean

);
DROP TABLE IF EXISTS `ANSWERS`;
CREATE TABLE IF NOT EXISTS `ANSWERS`
(
    `id_user`   int,
    `id_option` int
);
ALTER TABLE `QUESTIONS`
    ADD `id_classroom` int NOT NULL;
ALTER TABLE `QUESTIONS`
    ADD FOREIGN KEY (`id_classroom`) REFERENCES `CLASSROOMS` (`id_classroom`);
ALTER TABLE `USERS`
    ADD `id_classroom` int NOT NULL;
ALTER TABLE `USERS`
    ADD FOREIGN KEY (`id_classroom`) REFERENCES `CLASSROOMS` (`id_classroom`);
ALTER TABLE `OPTIONS`
    ADD `id_question` int NOT NULL;
ALTER TABLE `OPTIONS`
    ADD FOREIGN KEY (`id_question`) REFERENCES `QUESTIONS` (`id_question`);
ALTER TABLE `INFORMATION`
    ADD `id_classroom` int NOT NULL;
ALTER TABLE `INFORMATION`
    ADD FOREIGN KEY (`id_classroom`) REFERENCES `CLASSROOMS` (`id_classroom`);
ALTER TABLE `ANSWERS`
    ADD CONSTRAINT PK_Answers PRIMARY KEY (id_user, id_option);
ALTER TABLE `ANSWERS`
    ADD FOREIGN KEY (`id_user`) REFERENCES `USERS` (`id_user`);
ALTER TABLE `ANSWERS`
    ADD FOREIGN KEY (`id_option`) REFERENCES `OPTIONS` (`id_option`);

INSERT INTO `CLASSROOMS`
VALUES (NULL, 'Admin');