-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema club-runner
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `club-runner` ;

-- -----------------------------------------------------
-- Schema club-runner
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `club-runner` DEFAULT CHARACTER SET utf8 ;
USE `club-runner` ;

-- -----------------------------------------------------
-- Table `club-runner`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `club-runner`.`user` ;

CREATE TABLE IF NOT EXISTS `club-runner`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `club-runner`.`club`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `club-runner`.`club` ;

CREATE TABLE IF NOT EXISTS `club-runner`.`club` (
  `club_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(150) NULL,
  PRIMARY KEY (`club_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `club-runner`.`run_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `club-runner`.`run_status` ;

CREATE TABLE IF NOT EXISTS `club-runner`.`run_status` (
  `run_status_id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`run_status_id`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `club-runner`.`run`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `club-runner`.`run` ;

CREATE TABLE IF NOT EXISTS `club-runner`.`run` (
  `run_id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  `max_capacity` INT NULL,
  `start_time` TIME NOT NULL,
  `latitude` DECIMAL(8,6) NOT NULL,
  `longitude` DECIMAL(9,6) NOT NULL,
  `user_id` INT NOT NULL,
  `club_id` INT NOT NULL,
  `run_status_id` INT NOT NULL,
  PRIMARY KEY (`run_id`),
  INDEX `fk_run_user_idx` (`user_id` ASC) INVISIBLE,
  INDEX `fk_run_club_idx` (`club_id` ASC) VISIBLE,
  INDEX `fk_run_run_status_idx` (`run_status_id` ASC) INVISIBLE,
  CONSTRAINT `fk_run_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `club-runner`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_run_club`
    FOREIGN KEY (`club_id`)
    REFERENCES `club-runner`.`club` (`club_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_run_run_status`
    FOREIGN KEY (`run_status_id`)
    REFERENCES `club-runner`.`run_status` (`run_status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `club-runner`.`runner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `club-runner`.`runner` ;

CREATE TABLE IF NOT EXISTS `club-runner`.`runner` (
  `runner_id` INT NOT NULL AUTO_INCREMENT,
  `run_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`runner_id`, `run_id`, `user_id`),
  INDEX `fk_runner_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_runner_run_idx` (`run_id` ASC) VISIBLE,
  CONSTRAINT `fk_runner_run`
    FOREIGN KEY (`run_id`)
    REFERENCES `club-runner`.`run` (`run_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_runner_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `club-runner`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `club-runner`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `club-runner`.`member` ;

CREATE TABLE IF NOT EXISTS `club-runner`.`member` (
  `member_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `club_id` INT NOT NULL,
  `isAdmin` TINYINT(1) NOT NULL,
  PRIMARY KEY (`member_id`, `user_id`, `club_id`),
  INDEX `fk_member_club_idx` (`club_id` ASC) VISIBLE,
  INDEX `fk_member_user_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `club_id_UNIQUE` (`club_id` ASC, `user_id` ASC) INVISIBLE,
  CONSTRAINT `fk_member_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `club-runner`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_member_club`
    FOREIGN KEY (`club_id`)
    REFERENCES `club-runner`.`club` (`club_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `club-runner`.`table1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `club-runner`.`table1` ;

CREATE TABLE IF NOT EXISTS `club-runner`.`table1` (
)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
