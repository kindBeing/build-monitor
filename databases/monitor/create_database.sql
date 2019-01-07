DROP DATABASE IF EXISTS monitor_dev;
DROP DATABASE IF EXISTS monitor_test;

CREATE DATABASE monitor_dev;
CREATE DATABASE monitor_test;

CREATE USER IF NOT EXISTS 'monitor'@'localhost'
  IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON monitor_dev.* TO 'monitor' @'localhost';
GRANT ALL PRIVILEGES ON monitor_test.* TO 'monitor' @'localhost';