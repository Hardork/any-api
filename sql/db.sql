create database my_api;
use my_api;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    `accessKey` varchar(512) not null comment 'accessKey',
    `secretKey` varchar(512) not null comment 'secretKey',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';


-- 接口分组表
create table my_api.`interface_group`(
    id int not null primary key auto_increment comment '主键',
    name varchar(128) not null comment '接口分类名称',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
);

-- 接口信息
drop table if exists my_api.interface_info;
create table if not exists my_api.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    `requestParams` text null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    requestExample text null comment '请求示例',
    returnFormat varchar(512) default 'JSON' comment '返回格式',
    responseParams text comment '响应参数',
    avatarUrl varchar(1024) comment '接口头像',
    `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
    `method` varchar(256) not null comment '请求类型',
    `path`   varchar(256) not null comment '请求路径',
    `userId` bigint not null comment '创建人',
    hotVal  double  not null default 0 comment '接口热度值',
    reduceScore int default 0 comment '消耗积分',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口信息';


insert into interface_info(`id`, `name`, `url`, `userId`, `method`, `requestParams`, `reduceScore`, `requestExample`,
                                  `requestHeader`, `responseHeader`, `description`, `status`, `avatarUrl`,
                                  `returnFormat`, `responseParams`, `createTime`, `updateTime`, `isDelete`, `path`)
values (1705234447153963010, '随机毒鸡汤', 'https://gateway.qimuu.icu/api/poisonousChickenSoup', 1698354419367571457, 'GET',
        NULL, 1, 'http://121.36.48.205:8123/api/poisonousChickenSoup', NULL, NULL, '随机毒鸡汤', 1, '', 'JSON',
        '[{\"id\":\"1695051685885\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695052930602\",\"fieldName\":\"data.text\",\"type\":\"string\",\"desc\":\"随机毒鸡汤\"},{\"id\":\"1695052955781\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}]',
        '2023-09-22 22:55:48', '2023-09-23 11:52:06', 0, '/poisonousChickenSoup'),
       (1705237990061580289, '随机壁纸', 'http://121.36.48.205:8123/api/randomWallpaper', 1698354419367571457, 'GET',
        '[{\"id\":\"1695032007961\",\"fieldName\":\"method\",\"type\":\"string\",\"desc\":\"输出壁纸端[mobile|pc|zsy]默认为pc\",\"required\":\"否\"},{\"id\":\"1695032018924\",\"fieldName\":\"lx\",\"type\":\"string\",\"desc\":\"选择输出分类[meizi|dongman|fengjing|suiji]，为空随机输出\",\"required\":\"否\"}]',
        1, 'http://121.36.48.205:8123/api/randomWallpaper?lx=dongman', NULL, NULL, '获取随机壁纸', 1,
        'https://img.qimuu.icu/typory/logo.jpg', 'JSON',
        '[{\"id\":\"1695051751595\",\"fieldName\":\"code\",\"type\":\"string\",\"desc\":\"响应码\"},{\"id\":\"1695051832571\",\"fieldName\":\"data.imgurl\",\"type\":\"string\",\"desc\":\"返回的壁纸地址\"},{\"id\":\"1695051861456\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应消息\"}]',
        '2023-09-22 23:09:53', '2023-09-23 11:52:06', 0, '/randomWallpaper'),
       (1705238841173942274, '每日星座运势', 'http://121.36.48.205:8123/api/horoscope', 1698354419367571457, 'GET',
        '[{\"id\":\"1695382662346\",\"fieldName\":\"type\",\"type\":\"string\",\"desc\":\"十二星座对应英文小写，aries, taurus, gemini, cancer, leo, virgo, libra, scorpio, sagittarius, capricorn, aquarius, pisces\",\"required\":\"是\"},{\"id\":\"1695382692472\",\"fieldName\":\"time\",\"type\":\"string\",\"desc\":\"今日明日一周等运势,today, nextday, week, month, year, love\",\"required\":\"是\"}]',
        1, 'http://121.36.48.205:8123/api/horoscope?type=scorpio&time=nextday', NULL, NULL, '获取每日星座运势', 1,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/r2X9jsoT-horoscope2.png', 'JSON',
        '[{\"id\":\"1695382701088\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695382720309\",\"fieldName\":\"data.todo.yi\",\"type\":\"string\",\"desc\":\"宜做\"},{\"id\":\"1695382741895\",\"fieldName\":\"data.todo.ji\",\"type\":\"string\",\"desc\":\"忌做\"},{\"id\":\"1695382783855\",\"fieldName\":\"data.fortunetext.all\",\"type\":\"string\",\"desc\":\"整体运势\"},{\"id\":\"1695382810906\",\"fieldName\":\"data.fortunetext.love\",\"type\":\"string\",\"desc\":\"爱情运势\"},{\"id\":\"1695382836727\",\"fieldName\":\"data.fortunetext.work\",\"type\":\"string\",\"desc\":\"工作运势\"},{\"id\":\"1695382864966\",\"fieldName\":\"data.fortunetext.money\",\"type\":\"string\",\"desc\":\"财运运势\"},{\"id\":\"1695382888169\",\"fieldName\":\"data.fortunetext.health\",\"type\":\"string\",\"desc\":\"健康运势\"},{\"id\":\"1695382912920\",\"fieldName\":\"data.fortune.all\",\"type\":\"int\",\"desc\":\"整体运势评分\"},{\"id\":\"1695382931057\",\"fieldName\":\"data.fortune.love\",\"type\":\"int\",\"desc\":\"爱情运势评分\"},{\"id\":\"1695382952540\",\"fieldName\":\"data.fortune.work\",\"type\":\"int\",\"desc\":\"工作运势评分\"},{\"id\":\"1695382975321\",\"fieldName\":\"data.fortune.money\",\"type\":\"int\",\"desc\":\"财运运势评分\"},{\"id\":\"1695382999222\",\"fieldName\":\"data.fortune.health\",\"type\":\"int\",\"desc\":\"健康运势评分\"},{\"id\":\"1695383027074\",\"fieldName\":\"data.shortcomment\",\"type\":\"string\",\"desc\":\"简评\"},{\"id\":\"1695383057554\",\"fieldName\":\"data.luckycolor\",\"type\":\"string\",\"desc\":\"幸运颜色\"},{\"id\":\"1695383079261\",\"fieldName\":\"data.index.all\",\"type\":\"string\",\"desc\":\"整体指数\"},{\"id\":\"1695383102324\",\"fieldName\":\"data.index.love\",\"type\":\"string\",\"desc\":\"爱情指数\"},{\"id\":\"1695383125487\",\"fieldName\":\"data.index.work\",\"type\":\"string\",\"desc\":\"工作指数\"},{\"id\":\"1695383149310\",\"fieldName\":\"data.index.money\",\"type\":\"string\",\"desc\":\"财运指数\"},{\"id\":\"1695383173441\",\"fieldName\":\"data.index.health\",\"type\":\"string\",\"desc\":\"健康指数\"},{\"id\":\"1695383203920\",\"fieldName\":\"data.luckynumber\",\"type\":\"string\",\"desc\":\"幸运数字\"},{\"id\":\"1695383227323\",\"fieldName\":\"data.time\",\"type\":\"string\",\"desc\":\"日期\"},{\"id\":\"1695383314942\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}]',
        '2023-09-22 23:13:16', '2023-09-23 11:52:06', 0, '/horoscope'),
       (1705239469589733378, '随机土味情话', 'http://121.36.48.205:8123/api/loveTalk', 1698354419367571457, 'GET', NULL, 1,
        'http://121.36.48.205:8123/api/loveTalk', NULL, NULL, '获取土味情话', 1,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/g8FTal0P-love.png', 'JSON',
        '[{\"id\":\"1695382126371\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695382173816\",\"fieldName\":\"data.value\",\"type\":\"string\",\"desc\":\"随机土味情话\"},{\"id\":\"1695382205869\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"返回信息描述\"}]',
        '2023-09-22 23:15:46', '2023-09-23 11:52:06', 0, '/loveTalk'),
       (1705239928861827073, '获取IP信息归属地', 'http://121.36.48.205:8123/api/ipInfo', 1698354419367571457, 'GET',
        '[{\"id\":\"1695388304868\",\"fieldName\":\"ip\",\"type\":\"string\",\"desc\":\"输入IP地址\",\"required\":\"是\"}]',
        1, 'http://121.36.48.205:8123/api/ipInfo?ip=58.154.0.0', NULL, NULL, '获取IP信息归属地详细版', 1,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/6DPoYYZe-ipInfo.png', 'JSON',
        '[{\"id\":\"1695382701088\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695382720309\",\"fieldName\":\"data.ip\",\"type\":\"string\",\"desc\":\"IP地址\"},{\"id\":\"1695382741895\",\"fieldName\":\"data.info.country\",\"type\":\"string\",\"desc\":\"国家\"},{\"id\":\"1695382783855\",\"fieldName\":\"data.info.prov\",\"type\":\"string\",\"desc\":\"省份\"},{\"id\":\"1695382810906\",\"fieldName\":\"data.info.city\",\"type\":\"string\",\"desc\":\"城市\"},{\"id\":\"1695382836727\",\"fieldName\":\"data.info.lsp\",\"type\":\"string\",\"desc\":\"运营商\"},{\"id\":\"1695382864966\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}]',
        '2023-09-22 23:17:35', '2023-09-23 12:18:29', 0, '/ipInfo'),
       (1705240565347459073, '获取天气信息', 'http://121.36.48.205:8123/api/weather', 1698354419367571457, 'GET',
        '[{\"id\":\"1695392098947\",\"fieldName\":\"city\",\"type\":\"string\",\"desc\":\"输入城市或县区\",\"required\":\"否\"},{\"id\":\"1695392118560\",\"fieldName\":\"ip\",\"type\":\"string\",\"desc\":\"输入IP\",\"required\":\"否\"},{\"id\":\"1695392127763\",\"fieldName\":\"type\",\"type\":\"string\",\"desc\":\"默认一天，可配置 week获取周\",\"required\":\"否\"}]',
        1, 'http://121.36.48.205:8123/api/weather?ip=180.149.130.16', NULL, NULL, '获取每日每周的天气信息', 1,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/gYNay1Y0-weather.png', 'JSON',
        '[\n  {\"id\":\"1695382701088\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},\n  {\"id\":\"1695382720309\",\"fieldName\":\"data.city\",\"type\":\"string\",\"desc\":\"城市\"},\n  {\"id\":\"1695382741895\",\"fieldName\":\"data.info.date\",\"type\":\"string\",\"desc\":\"日期\"},\n  {\"id\":\"1695382783855\",\"fieldName\":\"data.info.week\",\"type\":\"string\",\"desc\":\"星期几\"},\n  {\"id\":\"1695382810906\",\"fieldName\":\"data.info.type\",\"type\":\"string\",\"desc\":\"天气类型\"},\n  {\"id\":\"1695382836727\",\"fieldName\":\"data.info.low\",\"type\":\"string\",\"desc\":\"最低温度\"},\n  {\"id\":\"1695382864966\",\"fieldName\":\"data.info.high\",\"type\":\"string\",\"desc\":\"最高温度\"},\n  {\"id\":\"1695382892007\",\"fieldName\":\"data.info.fengxiang\",\"type\":\"string\",\"desc\":\"风向\"},\n  {\"id\":\"1695382918740\",\"fieldName\":\"data.info.fengli\",\"type\":\"string\",\"desc\":\"风力\"},\n  {\"id\":\"1695382951685\",\"fieldName\":\"data.info.night.type\",\"type\":\"string\",\"desc\":\"夜间天气类型\"},\n  {\"id\":\"1695382977506\",\"fieldName\":\"data.info.night.fengxiang\",\"type\":\"string\",\"desc\":\"夜间风向\"},\n  {\"id\":\"1695383004749\",\"fieldName\":\"data.info.night.fengli\",\"type\":\"string\",\"desc\":\"夜间风力\"},\n  {\"id\":\"1695383032988\",\"fieldName\":\"data.info.air.aqi\",\"type\":\"int\",\"desc\":\"空气质量指数\"},\n  {\"id\":\"1695383052210\",\"fieldName\":\"data.info.air.aqi_level\",\"type\":\"int\",\"desc\":\"空气质量指数级别\"},\n  {\"id\":\"1695383072789\",\"fieldName\":\"data.info.air.aqi_name\",\"type\":\"string\",\"desc\":\"空气质量指数名称\"},\n  {\"id\":\"1695383098609\",\"fieldName\":\"data.info.air.co\",\"type\":\"string\",\"desc\":\"一氧化碳浓度\"},\n  {\"id\":\"1695383124245\",\"fieldName\":\"data.info.air.no2\",\"type\":\"string\",\"desc\":\"二氧化氮浓度\"},\n  {\"id\":\"1695383149267\",\"fieldName\":\"data.info.air.o3\",\"type\":\"string\",\"desc\":\"臭氧浓度\"},\n  {\"id\":\"1695383173716\",\"fieldName\":\"data.info.air.pm10\",\"type\":\"string\",\"desc\":\"PM10浓度\"},\n  {\"id\":\"1695383198557\",\"fieldName\":\"data.info.air.pm25\",\"type\":\"string\",\"desc\":\"PM2.5浓度\"},\n  {\"id\":\"1695383222398\",\"fieldName\":\"data.info.air.so2\",\"type\":\"string\",\"desc\":\"二氧化硫浓度\"},\n  {\"id\":\"1695383249835\",\"fieldName\":\"data.info.tip\",\"type\":\"string\",\"desc\":\"提示信息\"},\n  {\"id\":\"1695383275472\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}\n]',
        '2023-09-22 23:20:07', '2023-09-23 11:52:06', 0, '/weather');



-- 用户调用接口关系表
create table if not exists my_api.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';

-- 用户每日领取免费调用次数记录表
create table if not exists user_interface_free_daily_record(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
);

--
create index url_path_index on interface_info(url, method);

-- 接口调用表
create table interface_info_invoke_info (
    `id` bigint not null auto_increment comment '主键' primary key,
    `interfaceInfoId` bigint not null comment '接口 id',
    `initHotValue` int not null default 0 comment '接口热度初始值',
    `invokeNum` int default 0 not null comment '接口被调用次数',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
);

-- 接口访问监控表（用于实时监控）
CREATE TABLE `interface_access_stats`
(
    `id`             bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `interfaceInfoId` bigint DEFAULT NULL COMMENT '接口信息表',
    `date`           date         DEFAULT NULL COMMENT '日期',
    `pv`             int(11) DEFAULT NULL COMMENT '接口访问量',
    `uv`             int(11) DEFAULT NULL COMMENT '接口独立访客数',
    `uip`            int(11) DEFAULT NULL COMMENT '接口独立IP数',
    `hour`           int(3) DEFAULT NULL COMMENT '小时',
    `weekday`        int(3) DEFAULT NULL COMMENT '星期',
    `createTime`    datetime     DEFAULT NULL COMMENT '创建时间',
    `updateTime`    datetime     DEFAULT NULL COMMENT '修改时间',
    `isDelete`       tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_access_stats` (`interfaceInfoId`,`date`,`hour`) # 使用联合索引加快检索 where interfaceId = 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into interface_access_stats(interfaceInfoId, date, pv, uv, uip, hour, weekday, createTime, updateTime)
values (1,'2024-6-30',0,0,0,1,7,now(),now()) ON DUPLICATE KEY UPDATE pv = pv + 1, uv = uv + 1, uip = uip + 1;

-- 接口访问日志（用于查询地区信息）
CREATE TABLE `interface_access_logs`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `interfaceInfoId`    bigint DEFAULT NULL COMMENT '接口ID',
    `userId`         bigint DEFAULT NULL COMMENT '用户信息',
    `ip`             varchar(64)  DEFAULT NULL COMMENT 'IP',
    `locale`         varchar(256) DEFAULT NULL COMMENT '地区',
    `createTime`    datetime  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`    datetime  DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `isDelete`       tinyint DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    KEY              `idx_full_short_url` (`interfaceInfoId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 接口监控地址
CREATE TABLE `interface_locale_stats`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `interfaceInfoId` bigint DEFAULT NULL COMMENT '接口ID',
    `date`           date         DEFAULT NULL COMMENT '日期',
    `cnt`            int(11) DEFAULT NULL COMMENT '访问量',
    `province`       varchar(64)  DEFAULT NULL COMMENT '省份名称',
    `city`           varchar(64)  DEFAULT NULL COMMENT '市名称',
    `adcode`         varchar(64)  DEFAULT NULL COMMENT '城市编码',
    `country`        varchar(64)  DEFAULT NULL COMMENT '国家标识',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`       tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_locale_stats` (`interfaceInfoId`,`date`,`adcode`,`province`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;