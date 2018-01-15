DROP DATABASE IF EXISTS taskmanager;

CREATE DATABASE IF NOT EXISTS taskmanager;

USE taskmanager;

CREATE TABLE users
(
  user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  email VARCHAR(30) NOT NULL UNIQUE,
  password VARCHAR(30) NOT NULL,
  firstname VARCHAR(30) NOT NULL,
  lastname VARCHAR(30) NOT NULL,
  mode VARCHAR(30) NOT NULL,
  confirm BOOLEAN NOT NULL
);

CREATE TABLE projects
(
  project_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE tasks
(
  task_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  title VARCHAR(200) NOT NULL,
  status VARCHAR(30) NOT NULL,
  project_id INT NOT NULL,
  FOREIGN KEY (project_id) REFERENCES projects (project_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE comments
(
  comment_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL,
  text VARCHAR(200) NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
  task_id INT NOT NULL,
  FOREIGN KEY (task_id) REFERENCES tasks (task_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE users_projects
(
  user_id    INT,
  project_id INT,
  PRIMARY KEY (user_id, project_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id),
  FOREIGN KEY (project_id) REFERENCES projects (project_id)
);

CREATE TABLE users_tasks
(
  user_id    INT,
  task_id INT,
  PRIMARY KEY (user_id, task_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id),
  FOREIGN KEY (task_id) REFERENCES tasks (task_id)
);




