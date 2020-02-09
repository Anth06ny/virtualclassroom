DROP TABLE IF EXISTS 'USERS';
CREATE TABLE IF NOT EXISTS 'USERS' (
    'id_user' int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'user_name' varchar(200) NOT NULL,
    'user_lastname' varchar(200) NOT NULL,
    'user_email' varchar (200) NOT NULL,
    'user_password' varchar (50) NOT NULL,
    'isAdmin' boolean NOT NULL,
    'id_classroom' int,
    FOREIGN KEY ('id_classroom') REFERENCES 'QUESTIONS'('id_classroom')
);

DROP TABLE IF EXISTS 'CLASSROOMS';
CREATE TABLE IF NOT EXISTS 'CLASSROOMS' (
    'id_classroom' int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'classroom_name' varchar(50) NOT NULL
);

DROP TABLE IF EXISTS OPTIONS;
CREATE TABLE IF NOT EXISTS OPTIONS (
  id_option int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    option_content varchar(255) NOT NULL,
    id_question int,
    FOREIGN KEY (id_question) REFERENCES QUESTIONS(id_question)
);

CREATE TABLE INFORMATION(
        id_information    Int  Auto_increment  NOT NULL ,
        information_label Varchar (50) NOT NULL ,
        information_url   Varchar (255) NOT NULL ,
        id_classroom      Int NOT NULL
	,CONSTRAINT INFORMATION_PK PRIMARY KEY (id_information)

	,CONSTRAINT INFORMATION_CLASSROOMS_FK FOREIGN KEY (id_classroom) REFERENCES CLASSROOMS(id_classroom)
);


    DROP TABLE IF EXISTS `QUESTIONS`;
    CREATE TABLE IF NOT EXISTS `QUESTIONS` (
        `id_question` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `question_content` varchar(255) NOT NULL,
        `id_classroom` int NOT NULL,
         FOREIGN KEY (`id_classroom`) REFERENCES CLASSROOMS(`id_classroom`),
        `id_question_type` int NOT NULL,
         FOREIGN KEY (`id_question_type`) REFERENCES QUESTIONS_TYPES(`id_question_type`)
     );

    INSERT INTO `USERS` VALUES(NULL, 'Bob','Morane','bMorane@toto.com', 12345, false, NULL);
    INSERT INTO `CLASSROOMS` VALUES (NULL, 'DEV-1903');

