CREATE TABLE `t_report_temple` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_code` varchar(255) NOT NULL COMMENT '表单号',
  `table_name` varchar(255) DEFAULT NULL COMMENT '表单名称',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  `start_date` date DEFAULT NULL COMMENT '生效开始日期',
  `end_date` date DEFAULT NULL COMMENT '生效结束日期',
  `currency_unit` varchar(255) DEFAULT NULL COMMENT '货币单位',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件存放路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

CREATE TABLE `t_label_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label_code` varchar(255) NOT NULL COMMENT '指标代码',
  `label_name` varchar(255) DEFAULT NULL COMMENT '指标名称',
  `table_code` varchar(255) DEFAULT NULL COMMENT '表号',
  `row_val` varchar(255) DEFAULT NULL COMMENT '行值',
  `column_val` varchar(255) DEFAULT NULL COMMENT '列值',
  `version` varchar(255) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_labelcode` (`label_code`,`version`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1548 DEFAULT CHARSET=utf8;


CREATE TABLE `t_input_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `label_code` varchar(255) DEFAULT NULL COMMENT '指标代码',
  `value` decimal(10,2) DEFAULT NULL COMMENT '值',
  `version` varchar(255) DEFAULT NULL COMMENT '版本号',
  `input_date` date DEFAULT NULL COMMENT '报送日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8;




