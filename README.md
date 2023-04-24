# demoweather

-- demoweather.weather definition

CREATE TABLE `weather` (
  `SEQ` int NOT NULL AUTO_INCREMENT,
  `AREA` varchar(20) NOT NULL,
  `ICON` int DEFAULT NULL,
  `TEMP` double DEFAULT NULL,
  `REG_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`SEQ`)
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
