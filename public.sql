/*
 Navicat Premium Data Transfer

 Source Server         : zzz_localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 130004
 Source Host           : localhost:5432
 Source Catalog        : spring_security_examples
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130004
 File Encoding         : 65001

 Date: 07/03/2023 11:44:49
*/


-- ----------------------------
-- Sequence structure for ss_permission_permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ss_permission_permission_id_seq";
CREATE SEQUENCE "public"."ss_permission_permission_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for ss_role_permission_role_permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ss_role_permission_role_permission_id_seq";
CREATE SEQUENCE "public"."ss_role_permission_role_permission_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for ss_role_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ss_role_role_id_seq";
CREATE SEQUENCE "public"."ss_role_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for ss_user_auth_user_auth_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ss_user_auth_user_auth_id_seq";
CREATE SEQUENCE "public"."ss_user_auth_user_auth_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for ss_user_role_user_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ss_user_role_user_role_id_seq";
CREATE SEQUENCE "public"."ss_user_role_user_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for ss_user_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ss_user_user_id_seq";
CREATE SEQUENCE "public"."ss_user_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for ss_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_permission";
CREATE TABLE "public"."ss_permission" (
  "permission_id" int8 NOT NULL DEFAULT nextval('ss_permission_permission_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "permission_code" varchar(255) COLLATE "pg_catalog"."default",
  "permission_description" varchar(255) COLLATE "pg_catalog"."default",
  "permission_parent_id" int8,
  "permission_url" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."ss_permission"."permission_id" IS '权限id';
COMMENT ON COLUMN "public"."ss_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ss_permission"."create_user_id" IS '创建人id';
COMMENT ON COLUMN "public"."ss_permission"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."ss_permission"."update_user_id" IS '更新人id';
COMMENT ON COLUMN "public"."ss_permission"."permission_code" IS '权限code';
COMMENT ON COLUMN "public"."ss_permission"."permission_description" IS '权限描述';
COMMENT ON COLUMN "public"."ss_permission"."permission_parent_id" IS '父级权限id（权限分类）';
COMMENT ON COLUMN "public"."ss_permission"."permission_url" IS '权限url';
COMMENT ON TABLE "public"."ss_permission" IS '权限表';

-- ----------------------------
-- Records of ss_permission
-- ----------------------------
INSERT INTO "public"."ss_permission" VALUES (101001, NULL, NULL, NULL, NULL, 'test:add', '测试添加', NULL, '/api/shuishu/demo/test/add');
INSERT INTO "public"."ss_permission" VALUES (101002, NULL, NULL, NULL, NULL, 'test:page', '测试分页查询', NULL, '/api/shuishu/demo/test/page');
INSERT INTO "public"."ss_permission" VALUES (101003, NULL, NULL, NULL, NULL, 'test:details', '测试详情', NULL, '/api/shuishu/demo/test/details');
INSERT INTO "public"."ss_permission" VALUES (101004, NULL, NULL, NULL, NULL, 'test:update', '测试更新', NULL, '/api/shuishu/demo/test/update');
INSERT INTO "public"."ss_permission" VALUES (101005, NULL, NULL, NULL, NULL, 'test:delete', '测试删除', NULL, '/api/shuishu/demo/test/delete');

-- ----------------------------
-- Table structure for ss_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_role";
CREATE TABLE "public"."ss_role" (
  "role_id" int8 NOT NULL DEFAULT nextval('ss_role_role_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "role_code" varchar(255) COLLATE "pg_catalog"."default",
  "role_name" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."ss_role"."role_id" IS '角色id';
COMMENT ON COLUMN "public"."ss_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ss_role"."create_user_id" IS '创建人id';
COMMENT ON COLUMN "public"."ss_role"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."ss_role"."update_user_id" IS '更新人id';
COMMENT ON COLUMN "public"."ss_role"."role_code" IS '角色code';
COMMENT ON COLUMN "public"."ss_role"."role_name" IS '角色名称';
COMMENT ON TABLE "public"."ss_role" IS '角色表';

-- ----------------------------
-- Records of ss_role
-- ----------------------------
INSERT INTO "public"."ss_role" VALUES (10, '2023-03-07 10:24:20', NULL, '2023-03-07 10:24:25', NULL, 'system-super-role', '系统超级管理员');
INSERT INTO "public"."ss_role" VALUES (11, '2023-03-07 10:25:34', NULL, '2023-03-07 10:25:37', NULL, 'system-role', '系统管理员');
INSERT INTO "public"."ss_role" VALUES (12, '2023-03-07 10:26:44', NULL, '2023-03-07 10:26:46', NULL, 'general-role', '普通用户');

-- ----------------------------
-- Table structure for ss_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_role_permission";
CREATE TABLE "public"."ss_role_permission" (
  "role_permission_id" int8 NOT NULL DEFAULT nextval('ss_role_permission_role_permission_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "permission_id" int8,
  "role_id" int8
)
;
COMMENT ON COLUMN "public"."ss_role_permission"."role_permission_id" IS '角色权限关联id';
COMMENT ON COLUMN "public"."ss_role_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ss_role_permission"."create_user_id" IS '创建人id';
COMMENT ON COLUMN "public"."ss_role_permission"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."ss_role_permission"."update_user_id" IS '更新人id';
COMMENT ON COLUMN "public"."ss_role_permission"."permission_id" IS '权限id';
COMMENT ON COLUMN "public"."ss_role_permission"."role_id" IS '角色id';
COMMENT ON TABLE "public"."ss_role_permission" IS '角色权限关联表';

-- ----------------------------
-- Records of ss_role_permission
-- ----------------------------
INSERT INTO "public"."ss_role_permission" VALUES (100, NULL, NULL, NULL, NULL, 101001, 10);
INSERT INTO "public"."ss_role_permission" VALUES (101, NULL, NULL, NULL, NULL, 101002, 10);
INSERT INTO "public"."ss_role_permission" VALUES (102, NULL, NULL, NULL, NULL, 101003, 10);
INSERT INTO "public"."ss_role_permission" VALUES (103, NULL, NULL, NULL, NULL, 101004, 10);
INSERT INTO "public"."ss_role_permission" VALUES (104, NULL, NULL, NULL, NULL, 101005, 10);
INSERT INTO "public"."ss_role_permission" VALUES (105, NULL, NULL, NULL, NULL, 101001, 11);
INSERT INTO "public"."ss_role_permission" VALUES (106, NULL, NULL, NULL, NULL, 101002, 11);
INSERT INTO "public"."ss_role_permission" VALUES (107, NULL, NULL, NULL, NULL, 101003, 11);
INSERT INTO "public"."ss_role_permission" VALUES (108, NULL, NULL, NULL, NULL, 101004, 11);
INSERT INTO "public"."ss_role_permission" VALUES (109, NULL, NULL, NULL, NULL, 101002, 12);
INSERT INTO "public"."ss_role_permission" VALUES (110, NULL, NULL, NULL, NULL, 101003, 12);

-- ----------------------------
-- Table structure for ss_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_user";
CREATE TABLE "public"."ss_user" (
  "user_id" int8 NOT NULL DEFAULT nextval('ss_user_user_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "is_account_non_expired" bool,
  "is_account_non_locked" bool,
  "nickname" varchar(255) COLLATE "pg_catalog"."default",
  "user_about" varchar(255) COLLATE "pg_catalog"."default",
  "user_address" varchar(255) COLLATE "pg_catalog"."default",
  "user_auth_last_login_date" timestamp(6),
  "user_job" varchar(255) COLLATE "pg_catalog"."default",
  "user_photo" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."ss_user"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."ss_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ss_user"."create_user_id" IS '创建人id';
COMMENT ON COLUMN "public"."ss_user"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."ss_user"."update_user_id" IS '更新人id';
COMMENT ON COLUMN "public"."ss_user"."is_account_non_expired" IS '用户过期true：没有过期  false：过期';
COMMENT ON COLUMN "public"."ss_user"."is_account_non_locked" IS '用户锁定true：没有锁定  false：被锁定';
COMMENT ON COLUMN "public"."ss_user"."nickname" IS '昵称';
COMMENT ON COLUMN "public"."ss_user"."user_about" IS '个人简介';
COMMENT ON COLUMN "public"."ss_user"."user_address" IS '地址';
COMMENT ON COLUMN "public"."ss_user"."user_auth_last_login_date" IS '最后一次登录时间';
COMMENT ON COLUMN "public"."ss_user"."user_job" IS '职业';
COMMENT ON COLUMN "public"."ss_user"."user_photo" IS '头像';
COMMENT ON TABLE "public"."ss_user" IS '用户表';

-- ----------------------------
-- Records of ss_user
-- ----------------------------
INSERT INTO "public"."ss_user" VALUES (4, '2023-03-07 10:20:45', NULL, '2023-03-07 10:20:48', NULL, 't', 't', '小红', '关于小红', '小红地址', '2023-03-07 10:21:09', '餐饮', NULL);
INSERT INTO "public"."ss_user" VALUES (1, '2023-03-07 10:17:12', NULL, '2023-03-07 10:17:15', NULL, 't', 't', '小黑', '关于小黑', '小黑地址', '2023-03-07 10:18:14', '外卖员', NULL);
INSERT INTO "public"."ss_user" VALUES (3, '2023-03-07 10:19:59', NULL, '2023-03-07 10:20:02', NULL, 't', 't', '小明', '关于小明', '小明地址', '2023-03-07 10:20:25', '出行', NULL);
INSERT INTO "public"."ss_user" VALUES (2, '2023-03-07 10:19:06', NULL, '2023-03-07 10:19:09', NULL, 't', 't', '小白', '关于小白', '小白地址', '2023-03-07 10:19:31', '教师', NULL);

-- ----------------------------
-- Table structure for ss_user_auth
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_user_auth";
CREATE TABLE "public"."ss_user_auth" (
  "user_auth_id" int8 NOT NULL DEFAULT nextval('ss_user_auth_user_auth_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "user_auth_credential" varchar(255) COLLATE "pg_catalog"."default",
  "user_auth_identifier" varchar(255) COLLATE "pg_catalog"."default",
  "user_auth_nickname" varchar(255) COLLATE "pg_catalog"."default",
  "user_auth_photo" varchar(255) COLLATE "pg_catalog"."default",
  "user_auth_refresh_token" varchar(255) COLLATE "pg_catalog"."default",
  "user_auth_type" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" int8
)
;
COMMENT ON COLUMN "public"."ss_user_auth"."user_auth_id" IS '用户授权id';
COMMENT ON COLUMN "public"."ss_user_auth"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ss_user_auth"."create_user_id" IS '创建人id';
COMMENT ON COLUMN "public"."ss_user_auth"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."ss_user_auth"."update_user_id" IS '更新人id';
COMMENT ON COLUMN "public"."ss_user_auth"."user_auth_credential" IS '凭证信息';
COMMENT ON COLUMN "public"."ss_user_auth"."user_auth_identifier" IS '登录号：唯一识别码';
COMMENT ON COLUMN "public"."ss_user_auth"."user_auth_nickname" IS '授权昵称';
COMMENT ON COLUMN "public"."ss_user_auth"."user_auth_photo" IS '授权头像';
COMMENT ON COLUMN "public"."ss_user_auth"."user_auth_refresh_token" IS '刷新token';
COMMENT ON COLUMN "public"."ss_user_auth"."user_auth_type" IS '授权类型';
COMMENT ON COLUMN "public"."ss_user_auth"."user_id" IS '用户id';
COMMENT ON TABLE "public"."ss_user_auth" IS '用户授权表';

-- ----------------------------
-- Records of ss_user_auth
-- ----------------------------
INSERT INTO "public"."ss_user_auth" VALUES (100000002, NULL, NULL, NULL, NULL, 'b123', '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', '小白白', NULL, NULL, 'LOCAL', 2);
INSERT INTO "public"."ss_user_auth" VALUES (100000003, NULL, NULL, NULL, NULL, 'm123', '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', '小明明', NULL, NULL, 'LOCAL', 3);
INSERT INTO "public"."ss_user_auth" VALUES (100000004, NULL, NULL, NULL, NULL, 'h123', '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', '小红红', NULL, NULL, 'LOCAL', 4);
INSERT INTO "public"."ss_user_auth" VALUES (100000001, NULL, NULL, NULL, NULL, 'h123', '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', '小黑黑', NULL, NULL, 'LOCAL', 1);
INSERT INTO "public"."ss_user_auth" VALUES (100000005, NULL, NULL, NULL, NULL, '2124929779@qq.com', NULL, '小黑黑', NULL, NULL, 'EMAIL', 1);

-- ----------------------------
-- Table structure for ss_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_user_role";
CREATE TABLE "public"."ss_user_role" (
  "user_role_id" int8 NOT NULL DEFAULT nextval('ss_user_role_user_role_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "role_id" int8,
  "user_id" int8
)
;
COMMENT ON COLUMN "public"."ss_user_role"."user_role_id" IS '用户角色id';
COMMENT ON COLUMN "public"."ss_user_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ss_user_role"."create_user_id" IS '创建人id';
COMMENT ON COLUMN "public"."ss_user_role"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."ss_user_role"."update_user_id" IS '更新人id';
COMMENT ON COLUMN "public"."ss_user_role"."role_id" IS '角色id';
COMMENT ON COLUMN "public"."ss_user_role"."user_id" IS '用户id';
COMMENT ON TABLE "public"."ss_user_role" IS '用户角色表';

-- ----------------------------
-- Records of ss_user_role
-- ----------------------------
INSERT INTO "public"."ss_user_role" VALUES (200011, NULL, NULL, NULL, NULL, 11, 2);
INSERT INTO "public"."ss_user_role" VALUES (100010, NULL, NULL, NULL, NULL, 10, 1);
INSERT INTO "public"."ss_user_role" VALUES (300012, NULL, NULL, NULL, NULL, 12, 3);
INSERT INTO "public"."ss_user_role" VALUES (400012, NULL, NULL, NULL, NULL, 12, 4);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."ss_permission_permission_id_seq"
OWNED BY "public"."ss_permission"."permission_id";
SELECT setval('"public"."ss_permission_permission_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."ss_role_permission_role_permission_id_seq"
OWNED BY "public"."ss_role_permission"."role_permission_id";
SELECT setval('"public"."ss_role_permission_role_permission_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."ss_role_role_id_seq"
OWNED BY "public"."ss_role"."role_id";
SELECT setval('"public"."ss_role_role_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."ss_user_auth_user_auth_id_seq"
OWNED BY "public"."ss_user_auth"."user_auth_id";
SELECT setval('"public"."ss_user_auth_user_auth_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."ss_user_role_user_role_id_seq"
OWNED BY "public"."ss_user_role"."user_role_id";
SELECT setval('"public"."ss_user_role_user_role_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."ss_user_user_id_seq"
OWNED BY "public"."ss_user"."user_id";
SELECT setval('"public"."ss_user_user_id_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table ss_permission
-- ----------------------------
ALTER TABLE "public"."ss_permission" ADD CONSTRAINT "ss_permission_pkey" PRIMARY KEY ("permission_id");

-- ----------------------------
-- Primary Key structure for table ss_role
-- ----------------------------
ALTER TABLE "public"."ss_role" ADD CONSTRAINT "ss_role_pkey" PRIMARY KEY ("role_id");

-- ----------------------------
-- Primary Key structure for table ss_role_permission
-- ----------------------------
ALTER TABLE "public"."ss_role_permission" ADD CONSTRAINT "ss_role_permission_pkey" PRIMARY KEY ("role_permission_id");

-- ----------------------------
-- Primary Key structure for table ss_user
-- ----------------------------
ALTER TABLE "public"."ss_user" ADD CONSTRAINT "ss_user_pkey" PRIMARY KEY ("user_id");

-- ----------------------------
-- Primary Key structure for table ss_user_auth
-- ----------------------------
ALTER TABLE "public"."ss_user_auth" ADD CONSTRAINT "ss_user_auth_pkey" PRIMARY KEY ("user_auth_id");

-- ----------------------------
-- Primary Key structure for table ss_user_role
-- ----------------------------
ALTER TABLE "public"."ss_user_role" ADD CONSTRAINT "ss_user_role_pkey" PRIMARY KEY ("user_role_id");
