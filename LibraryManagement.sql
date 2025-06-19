/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : experiment3

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 18/06/2025 23:12:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `isbn` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `available` tinyint(1) NULL DEFAULT 1,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '其他',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES (1, 'Java 核心技术', 'Cay Horstmann', '9787302477830', 'Java 从入门到精通指南', 1, 'https://th.bing.com/th/id/R.130cdb68d14f85760e4cf8009242fd0f?rik=z2zZSAbqVjONSQ&riu=http%3a%2f%2fwww.tup.com.cn%2fupload%2fbigbookimg3%2f092543-01.jpg&ehk=PURXvQ8dQgScnV%2fnXn7j%2bZcyb8%2fZRX%2bwbMdi6ffAWuo%3d&risl=&pid=ImgRaw&r=0', '计算机');
INSERT INTO `books` VALUES (2, 'Vue 3 进阶指南', '尤雨溪', '9787302605646', 'Vue3 开发实战', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.Tg1SWZQiXmLdA1jTNEWVPwHaHa?rs=1&pid=ImgDetMain', '计算机');
INSERT INTO `books` VALUES (3, 'MySQL 必知必会', 'Ben Forta', '9787115322905', '数据库操作基础与实践', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.sCey7Sowj6seSlNMsXLTUQAAAA?w=149&h=216&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (4, '图解 HTTP', '上野 宣', '9787115357525', '通俗易懂地讲解 HTTP 协议原理', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.4kFnsNrjKei9TEAkbNgKZgHaHa?w=199&h=199&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (5, '算法图解', 'Aditya Bhargava', '9787115473911', '用图解方式讲清楚复杂算法', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.wcYSoAvsUHvitPQ-xcbfdwHaHa?w=203&h=203&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (6, '深入理解计算机系统', 'Randal E. Bryant', '9787115234093', '经典CSAPP教材，剖析底层机制', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.NyMDgBVYFu86pBQbAYxI4QHaKO?w=128&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (7, '现代操作系统', 'Andrew S. Tanenbaum', '9787115386631', '操作系统原理详解', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.sSGuz0YYHLJ-GqwCHY5mnQAAAA?w=148&h=211&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (8, 'Head First 设计模式', 'Eric Freeman', '9787111213825', '轻松理解设计模式，经典入门', 1, 'https://img.alicdn.com/bao/uploaded/i3/2455124912/O1CN01EyFoTH1m9kMX6WLy0_!!0-item_pic.jpg', '计算机');
INSERT INTO `books` VALUES (9, '敏捷软件开发：原则、模式与实践', 'Robert C. Martin', '9780132350884', '阐述敏捷开发原则与实践', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.cOKzFoVVeq1BO2BOdSGeDwHaKC?https://tse3-mm.cn.bing.net/th/id/OIP-C.cOKzFoVVeq1BO2BOdSGeDwHaKC?w=203&h=276&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (10, '企业应用架构模式', 'Martin Fowler', '9780321125217', '解析企业应用架构模式', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.5LcY9CMwuXkeDzQRmhCELQHaJL?w=142&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (11, '设计模式：可复用的面向对象软件元素', 'Erich Gamma', '9780201633610', '经典设计模式著作', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.3zpQS6lQsgpxdbB8MflFNQHaHa?w=192&h=192&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (12, 'C程序设计语言', 'Brian W. Kernighan', '9780131103627', 'C语言入门经典教材', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.-pNM6ME89NgB-GpSbQtOrQHaHa?w=203&h=203&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (13, 'C++程序设计语言（特别版）', 'Bjarne Stroustrup', '9780321714114', '深入讲解C++语言特性', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.m2eKVWS10B34XaXSITwj9gHaKs?w=203&h=293&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '计算机');
INSERT INTO `books` VALUES (17, '中餐烹饪大全', '李师傅', '9787515312020', '一本全面介绍中餐烹饪技巧的书籍', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.1UAFDd2Ag7uDReXFgYBG1gHaHa?w=191&h=191&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '美食');
INSERT INTO `books` VALUES (18, '世界美食地图', '全球美食协会', '9787556107746', '探索全球各地美食文化', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.V7wGUtGBjOYESyKQal5KKAHaKZ?w=129&h=181&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '美食');
INSERT INTO `books` VALUES (19, '现代艺术赏析', '王艺', '9787108052743', '一本探索现代艺术流派与作品的图书', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.PbgQ8yFtoUC6Lt_dA7J7KgHaFj?w=276&h=206&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '艺术');
INSERT INTO `books` VALUES (20, '艺术之眼', '徐静', '9787503952093', '帮助读者建立艺术鉴赏能力', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.kYMCle-a0VskF1TiBltUAgHaHn?w=138&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '艺术');
INSERT INTO `books` VALUES (21, '足球技术与战术', '李教练', '9787115420892', '足球训练与实战策略讲解', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.0yQpiee3bkzAFiRjG1nEcgHaHa?w=209&h=209&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '体育');
INSERT INTO `books` VALUES (22, '奥运史话', '刘体', '9787508745485', '奥林匹克运动发展全记录', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.RMg2cYYD7KtQ84ACNRLShAAAAA?w=238&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '体育');
INSERT INTO `books` VALUES (23, '时间简史', '斯蒂芬·霍金', '9787535726011', '关于宇宙起源和科学前沿的畅销书', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C._RLPePG0CbNkcRJNBgTs0gHaHa?w=219&h=219&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '其他');
INSERT INTO `books` VALUES (24, '人类简史', '尤瓦尔·赫拉利', '9787508682131', '从认知革命到人工智能的历史演进', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.aln0UG0NE2De46Q_Zxv6OQHaHa?w=212&h=212&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '其他');
INSERT INTO `books` VALUES (25, '川菜百味', '张大厨', '9787506538201', '川菜经典做法全收录', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.ProFRYGe96gk2pf2eeEAFAAAAA?w=179&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '美食');
INSERT INTO `books` VALUES (26, '粤菜点心制作', '李巧手', '9787535940509', '粤式小吃与点心指南', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.-PmJ0NO1VHHalBmiqaRvhgHaE6?w=279&h=185&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '美食');
INSERT INTO `books` VALUES (27, '家庭烘焙手册', '张甜心', '9787115320246', '适合新手的烘焙教程', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.9DP4UYfflx_IfKMtaH75WAHaHa?w=174&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '美食');
INSERT INTO `books` VALUES (28, '素食料理大全', '林清素', '9787515347558', '健康营养的素食做法合集', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.5f5aTJYUvwitUrWTwmOcpAHaHa?w=195&h=195&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '美食');
INSERT INTO `books` VALUES (29, '世界甜品图鉴', '甜点协会', '9787508756795', '全球甜品大全与文化解读', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.3-t8WDkDXwZFztn6nXDQEAHaJd?w=203&h=259&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '美食');
INSERT INTO `books` VALUES (30, '油画技法基础', '李画家', '9787532267104', '油画构图与技法详解', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.Ncul3NyB4cdpp-7Rnh5JnAHaHa?w=205&h=205&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '艺术');
INSERT INTO `books` VALUES (31, '水彩入门指南', '水蓝', '9787111231903', '水彩工具、技法与作品赏析', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.YBQBWx2zN0FTNMeZheOixwHaHa?w=206&h=206&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '艺术');
INSERT INTO `books` VALUES (32, '色彩构成艺术', '高大明', '9787551111440', '艺术设计专业经典教材', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.WVFqSBKrdFxyBdm--5VJHQHaHa?w=197&h=197&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '艺术');
INSERT INTO `books` VALUES (33, '摄影的艺术', 'Paul Hill', '9787805014213', '摄影理论与实践结合', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.RN2ieIuj_dpV1rOsZJz_qQHaIl?w=158&h=184&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3', '艺术');
INSERT INTO `books` VALUES (34, '现代雕塑赏析', '彭刻', '9787503913803', '西方现代雕塑经典作品解读', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.v5ZpnnVG2nWomp_bEbdDOgHaFP?w=261&h=184&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '艺术');
INSERT INTO `books` VALUES (35, '篮球战术指南', '李灌篮', '9787111203504', '篮球基础与战术讲解', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.7PMvg6xMWMLLgYkLtp-GdgHaHa?w=179&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '体育');
INSERT INTO `books` VALUES (36, '羽毛球实战技法', '张羽', '9787800008990', '从握拍到实战全面讲解', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.lT_UTyf0C1FmxPsUwmH22AHaKC?w=149&h=202&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '体育');
INSERT INTO `books` VALUES (37, '跑步圣经', '马拉松协会', '9787554506662', '适合所有人群的科学跑步指南', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.vc9v0RCsuO2_aFbl4imXpAAAAA?w=127&h=198&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '体育');
INSERT INTO `books` VALUES (38, '体能训练计划', '健身教练李军', '9787508257216', '打造个性化健身方案', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.VqSyB8wNrk_-kX03M_HQdwHaHa?w=194&h=194&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '体育');
INSERT INTO `books` VALUES (39, '围棋的智慧', '聂卫平', '9787546123895', '围棋入门到实战技巧解析', 1, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.dV_5-ET2JL3D7t1lyYA2GgHaHa?w=167&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '体育');
INSERT INTO `books` VALUES (40, '天文观测手册', 'NASA 编写组', '9787121416671', '夜空探索与天体知识入门', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.sRnW_vhsu-j6Njx9-Hl_xAHaHa?w=192&h=192&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '其他');
INSERT INTO `books` VALUES (41, '量子物理之谜', '弗曼', '9787518824650', '量子力学的启蒙之书', 1, 'https://tse2-mm.cn.bing.net/th/id/OIP-C.oosD36oJ6nmUx-HiiYbGBAHaHa?w=216&h=216&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '其他');
INSERT INTO `books` VALUES (42, '世界历史地图集', '史图出版社', '9787514017773', '可视化人类历史发展脉络', 1, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.UuvSxdFJzy3XBddqxkAucwHaHa?w=211&h=211&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '其他');
INSERT INTO `books` VALUES (43, '哲学的慰藉', '阿兰·德波顿', '9787508637322', '西方哲学的温柔疗愈力', 1, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.AGeLWpKcA-HfN3ZJXyHzKwAAAA?w=130&h=195&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3', '其他');

-- ----------------------------
-- Table structure for borrow_records
-- ----------------------------
DROP TABLE IF EXISTS `borrow_records`;
CREATE TABLE `borrow_records`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `borrow_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `return_time` timestamp NULL DEFAULT NULL,
  `returned` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `book_id`(`book_id` ASC) USING BTREE,
  CONSTRAINT `borrow_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `borrow_records_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow_records
-- ----------------------------
INSERT INTO `borrow_records` VALUES (1, 1, 1, '2025-06-14 11:03:27', '2025-06-15 02:48:04', 1);
INSERT INTO `borrow_records` VALUES (2, 1, 2, '2025-06-14 11:16:40', '2025-06-15 02:48:06', 1);
INSERT INTO `borrow_records` VALUES (3, 1, 3, '2025-06-14 11:22:03', '2025-06-15 02:48:07', 1);
INSERT INTO `borrow_records` VALUES (4, 1, 6, '2025-06-14 11:22:06', '2025-06-15 02:48:08', 1);
INSERT INTO `borrow_records` VALUES (5, 1, 5, '2025-06-14 11:22:24', '2025-06-15 02:48:08', 1);
INSERT INTO `borrow_records` VALUES (6, 1, 4, '2025-06-14 11:26:05', '2025-06-15 02:48:09', 1);
INSERT INTO `borrow_records` VALUES (7, 1, 7, '2025-06-14 11:27:22', '2025-06-15 02:48:09', 1);
INSERT INTO `borrow_records` VALUES (9, 1, 9, '2025-06-15 02:17:07', '2025-06-15 02:48:10', 1);
INSERT INTO `borrow_records` VALUES (11, 1, 1, '2025-06-15 02:48:49', '2025-06-15 02:50:54', 1);
INSERT INTO `borrow_records` VALUES (15, 1, 1, '2025-06-15 03:30:04', '2025-06-15 03:30:34', 1);
INSERT INTO `borrow_records` VALUES (20, 1, 2, '2025-06-15 15:38:58', '2025-06-15 15:40:09', 1);
INSERT INTO `borrow_records` VALUES (21, 1, 1, '2025-06-15 16:49:45', '2025-06-15 16:59:08', 1);
INSERT INTO `borrow_records` VALUES (22, 1, 21, '2025-06-15 16:49:47', '2025-06-15 16:59:09', 1);
INSERT INTO `borrow_records` VALUES (25, 1, 1, '2025-06-15 22:17:13', '2025-06-15 22:18:33', 1);
INSERT INTO `borrow_records` VALUES (26, 1, 2, '2025-06-15 22:17:16', '2025-06-15 22:18:33', 1);
INSERT INTO `borrow_records` VALUES (27, 12, 1, '2025-06-15 22:27:25', '2025-06-15 22:27:57', 1);
INSERT INTO `borrow_records` VALUES (28, 12, 2, '2025-06-15 22:27:25', '2025-06-15 22:27:57', 1);
INSERT INTO `borrow_records` VALUES (29, 12, 5, '2025-06-15 22:27:26', '2025-06-15 22:27:57', 1);
INSERT INTO `borrow_records` VALUES (30, 1, 1, '2025-06-15 22:35:06', '2025-06-15 22:37:36', 1);
INSERT INTO `borrow_records` VALUES (31, 11, 1, '2025-06-15 22:37:46', '2025-06-15 22:37:57', 1);
INSERT INTO `borrow_records` VALUES (32, 11, 1, '2025-06-15 22:38:38', '2025-06-15 22:44:23', 1);
INSERT INTO `borrow_records` VALUES (33, 11, 4, '2025-06-15 22:38:40', '2025-06-15 22:44:23', 1);
INSERT INTO `borrow_records` VALUES (37, 1, 1, '2025-06-16 09:34:15', '2025-06-16 09:35:02', 1);
INSERT INTO `borrow_records` VALUES (38, 1, 5, '2025-06-16 09:34:15', '2025-06-16 09:35:02', 1);
INSERT INTO `borrow_records` VALUES (39, 1, 6, '2025-06-16 09:34:20', '2025-06-16 09:35:03', 1);
INSERT INTO `borrow_records` VALUES (40, 11, 2, '2025-06-16 09:42:09', '2025-06-16 09:58:56', 1);
INSERT INTO `borrow_records` VALUES (41, 11, 1, '2025-06-16 09:42:09', '2025-06-16 09:58:57', 1);
INSERT INTO `borrow_records` VALUES (42, 11, 1, '2025-06-16 10:01:36', '2025-06-16 10:02:38', 1);
INSERT INTO `borrow_records` VALUES (43, 11, 2, '2025-06-16 10:01:37', '2025-06-16 10:02:38', 1);
INSERT INTO `borrow_records` VALUES (44, 11, 13, '2025-06-16 10:01:49', '2025-06-16 10:02:39', 1);
INSERT INTO `borrow_records` VALUES (45, 11, 18, '2025-06-16 10:01:53', '2025-06-16 10:02:39', 1);
INSERT INTO `borrow_records` VALUES (46, 1, 13, '2025-06-16 10:29:32', '2025-06-16 10:30:01', 1);
INSERT INTO `borrow_records` VALUES (47, 1, 17, '2025-06-16 10:29:33', '2025-06-16 10:34:29', 1);
INSERT INTO `borrow_records` VALUES (48, 1, 1, '2025-06-16 10:33:58', '2025-06-16 10:34:30', 1);
INSERT INTO `borrow_records` VALUES (49, 1, 2, '2025-06-16 10:33:58', '2025-06-16 10:34:30', 1);
INSERT INTO `borrow_records` VALUES (50, 1, 19, '2025-06-16 10:34:01', '2025-06-16 10:34:33', 1);
INSERT INTO `borrow_records` VALUES (51, 1, 1, '2025-06-16 10:40:55', '2025-06-16 10:43:39', 1);
INSERT INTO `borrow_records` VALUES (64, 19, 1, '2025-06-18 19:13:59', '2025-06-18 19:15:18', 1);
INSERT INTO `borrow_records` VALUES (65, 19, 5, '2025-06-18 19:14:00', '2025-06-18 19:15:18', 1);
INSERT INTO `borrow_records` VALUES (66, 19, 23, '2025-06-18 19:14:25', '2025-06-18 19:15:19', 1);
INSERT INTO `borrow_records` VALUES (67, 19, 24, '2025-06-18 19:14:25', '2025-06-18 19:15:47', 1);
INSERT INTO `borrow_records` VALUES (68, 1, 39, '2025-06-18 19:17:29', '2025-06-18 19:17:48', 1);
INSERT INTO `borrow_records` VALUES (69, 20, 21, '2025-06-18 19:24:04', '2025-06-18 19:24:58', 1);
INSERT INTO `borrow_records` VALUES (70, 20, 1, '2025-06-18 19:24:06', '2025-06-18 19:25:51', 1);
INSERT INTO `borrow_records` VALUES (71, 20, 23, '2025-06-18 19:24:08', '2025-06-18 19:24:58', 1);
INSERT INTO `borrow_records` VALUES (72, 1, 2, '2025-06-18 19:25:37', '2025-06-18 19:26:02', 1);
INSERT INTO `borrow_records` VALUES (73, 1, 1, '2025-06-18 22:18:35', '2025-06-18 22:18:50', 1);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'user',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '1', '1', 'user');
INSERT INTO `users` VALUES (7, '程康', '1', 'admin');
INSERT INTO `users` VALUES (11, '3', '3', 'user');
INSERT INTO `users` VALUES (12, '22', '1', 'user');
INSERT INTO `users` VALUES (19, '11', '1', 'user');
INSERT INTO `users` VALUES (20, 'user1', '1', 'user');

SET FOREIGN_KEY_CHECKS = 1;
