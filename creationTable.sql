#Nom de la base : virtualclassroom

CREATE TABLE IF NOT EXISTS `answers` (
  `id_user` int(11) NOT NULL DEFAULT '0',
  `id_option` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_user`,`id_option`),
  KEY `id_option` (`id_option`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `classrooms` (
  `id_classroom` int(11) NOT NULL AUTO_INCREMENT,
  `classroom_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id_classroom`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

CREATE TABLE IF NOT EXISTS `information` (
  `id_information` int(11) NOT NULL AUTO_INCREMENT,
  `information_label` varchar(50) NOT NULL,
  `information_url` varchar(255) NOT NULL,
  `id_classroom` int(11) NOT NULL,
  PRIMARY KEY (`id_information`),
  KEY `id_classroom` (`id_classroom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `options` (
  `id_option` int(11) NOT NULL AUTO_INCREMENT,
  `option_content` varchar(255) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  `id_question` int(11) NOT NULL,
  PRIMARY KEY (`id_option`),
  KEY `id_question` (`id_question`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `questions` (
  `id_question` int(11) NOT NULL AUTO_INCREMENT,
  `question_content` varchar(255) NOT NULL,
  `isRadio` tinyint(1) DEFAULT NULL,
  `id_classroom` int(11) NOT NULL,
  PRIMARY KEY (`id_question`),
  KEY `id_classroom` (`id_classroom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) NOT NULL,
  `user_lastname` varchar(200) NOT NULL,
  `user_email` varchar(200) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  `id_classroom` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  KEY `id_classroom` (`id_classroom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Contraintes pour la table `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_2` FOREIGN KEY (`id_option`) REFERENCES `options` (`id_option`),
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

--
-- Contraintes pour la table `information`
--
ALTER TABLE `information`
  ADD CONSTRAINT `information_ibfk_1` FOREIGN KEY (`id_classroom`) REFERENCES `classrooms` (`id_classroom`);

--
-- Contraintes pour la table `options`
--
ALTER TABLE `options`
  ADD CONSTRAINT `options_ibfk_1` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id_question`);

--
-- Contraintes pour la table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`id_classroom`) REFERENCES `classrooms` (`id_classroom`);

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`id_classroom`) REFERENCES `classrooms` (`id_classroom`);
