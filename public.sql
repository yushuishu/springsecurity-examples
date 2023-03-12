-- ----------------------------
-- Records of ss_permission
-- ----------------------------
INSERT INTO "public"."ss_permission"(permission_id, create_time, create_user_id, update_time, update_user_id,permission_code, permission_description, permission_parent_id, permission_url, is_need_authorization) VALUES (101001, NULL, NULL, NULL, NULL, 'test:add', '测试添加', NULL, '/api/shuishu/demo/test/add', 't');
INSERT INTO "public"."ss_permission"(permission_id, create_time, create_user_id, update_time, update_user_id,permission_code, permission_description, permission_parent_id, permission_url, is_need_authorization) VALUES (101002, NULL, NULL, NULL, NULL, 'test:page', '测试分页查询', NULL, '/api/shuishu/demo/test/page', 't');
INSERT INTO "public"."ss_permission"(permission_id, create_time, create_user_id, update_time, update_user_id,permission_code, permission_description, permission_parent_id, permission_url, is_need_authorization) VALUES (101003, NULL, NULL, NULL, NULL, 'test:details', '测试详情', NULL, '/api/shuishu/demo/test/details', 't');
INSERT INTO "public"."ss_permission"(permission_id, create_time, create_user_id, update_time, update_user_id,permission_code, permission_description, permission_parent_id, permission_url, is_need_authorization) VALUES (101004, NULL, NULL, NULL, NULL, 'test:update', '测试更新', NULL, '/api/shuishu/demo/test/update', 't');
INSERT INTO "public"."ss_permission"(permission_id, create_time, create_user_id, update_time, update_user_id,permission_code, permission_description, permission_parent_id, permission_url, is_need_authorization) VALUES (101005, NULL, NULL, NULL, NULL, 'test:delete', '测试删除', NULL, '/api/shuishu/demo/test/delete', 't');
INSERT INTO "public"."ss_permission"(permission_id, create_time, create_user_id, update_time, update_user_id,permission_code, permission_description, permission_parent_id, permission_url, is_need_authorization) VALUES (101006, NULL, NULL, NULL, NULL, 'test:enable', '测试放开的权限', NULL, '/api/shuishu/demo/test/enable', 'f');

-- ----------------------------
-- Records of ss_role
-- ----------------------------
INSERT INTO "public"."ss_role"(role_id, create_time, create_user_id, update_time, update_user_id, role_code, role_name) VALUES (10, '2023-03-07 10:24:20', NULL, '2023-03-07 10:24:25', NULL, 'system-super-role', '系统超级管理员');
INSERT INTO "public"."ss_role"(role_id, create_time, create_user_id, update_time, update_user_id, role_code, role_name) VALUES (11, '2023-03-07 10:25:34', NULL, '2023-03-07 10:25:37', NULL, 'system-role', '系统管理员');
INSERT INTO "public"."ss_role"(role_id, create_time, create_user_id, update_time, update_user_id, role_code, role_name) VALUES (12, '2023-03-07 10:26:44', NULL, '2023-03-07 10:26:46', NULL, 'general-role', '普通用户');

-- ----------------------------
-- Records of ss_role_permission
-- ----------------------------
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (100, NULL, NULL, NULL, NULL, 101001, 10);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (101, NULL, NULL, NULL, NULL, 101002, 10);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (102, NULL, NULL, NULL, NULL, 101003, 10);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (103, NULL, NULL, NULL, NULL, 101004, 10);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (104, NULL, NULL, NULL, NULL, 101005, 10);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (105, NULL, NULL, NULL, NULL, 101001, 11);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (106, NULL, NULL, NULL, NULL, 101002, 11);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (107, NULL, NULL, NULL, NULL, 101003, 11);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (108, NULL, NULL, NULL, NULL, 101004, 11);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (109, NULL, NULL, NULL, NULL, 101002, 12);
INSERT INTO "public"."ss_role_permission"(role_permission_id, create_time, create_user_id, update_time, update_user_id, permission_id, role_id) VALUES (110, NULL, NULL, NULL, NULL, 101003, 12);

-- ----------------------------
-- Records of ss_user
-- ----------------------------
INSERT INTO "public"."ss_user"(user_id, create_time, create_user_id, update_time, update_user_id, user_is_account_non_expired, user_is_account_non_locked, nickname, user_about, user_address, user_last_login_date, user_job, user_photo, user_max_login_client_number) VALUES (4, '2023-03-07 10:20:45', NULL, '2023-03-07 10:20:48', NULL, 't', 't', '小蓝', '关于小蓝', '小蓝地址', '2023-03-07 10:21:09', '餐饮', NULL, 2);
INSERT INTO "public"."ss_user"(user_id, create_time, create_user_id, update_time, update_user_id, user_is_account_non_expired, user_is_account_non_locked, nickname, user_about, user_address, user_last_login_date, user_job, user_photo, user_max_login_client_number) VALUES (2, '2023-03-07 10:19:06', NULL, '2023-03-07 10:19:09', NULL, 't', 't', '小白', '关于小白', '小白地址', '2023-03-07 10:19:31', '教师', NULL, 2);
INSERT INTO "public"."ss_user"(user_id, create_time, create_user_id, update_time, update_user_id, user_is_account_non_expired, user_is_account_non_locked, nickname, user_about, user_address, user_last_login_date, user_job, user_photo, user_max_login_client_number) VALUES (3, '2023-03-07 10:19:59', NULL, '2023-03-07 10:20:02', NULL, 't', 't', '小明', '关于小明', '小明地址', '2023-03-07 10:20:25', '出行', NULL, 2);
INSERT INTO "public"."ss_user"(user_id, create_time, create_user_id, update_time, update_user_id, user_is_account_non_expired, user_is_account_non_locked, nickname, user_about, user_address, user_last_login_date, user_job, user_photo, user_max_login_client_number) VALUES (1, '2023-03-07 10:17:12', NULL, '2023-03-07 10:17:15', NULL, 't', 't', '小黑', '关于小黑', '小黑地址', '2023-03-07 10:18:14', '外卖员', NULL, 2);

-- ----------------------------
-- Records of ss_user_auth
-- ----------------------------
INSERT INTO "public"."ss_user_auth"(user_auth_id, create_time, create_user_id, update_time, update_user_id, user_auth_credential, user_auth_identifier, user_auth_nickname, user_auth_photo, user_auth_refresh_token, user_auth_type, user_id) VALUES (100000003, NULL, NULL, NULL, NULL, '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', 'm123', '小明明', NULL, NULL, 'LOCAL', 3);
INSERT INTO "public"."ss_user_auth"(user_auth_id, create_time, create_user_id, update_time, update_user_id, user_auth_credential, user_auth_identifier, user_auth_nickname, user_auth_photo, user_auth_refresh_token, user_auth_type, user_id) VALUES (100000002, NULL, NULL, NULL, NULL, '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', 'b123', '小白白', NULL, NULL, 'LOCAL', 2);
INSERT INTO "public"."ss_user_auth"(user_auth_id, create_time, create_user_id, update_time, update_user_id, user_auth_credential, user_auth_identifier, user_auth_nickname, user_auth_photo, user_auth_refresh_token, user_auth_type, user_id) VALUES (100000001, NULL, NULL, NULL, NULL, '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', 'h123', '小黑黑', NULL, NULL, 'LOCAL', 1);
INSERT INTO "public"."ss_user_auth"(user_auth_id, create_time, create_user_id, update_time, update_user_id, user_auth_credential, user_auth_identifier, user_auth_nickname, user_auth_photo, user_auth_refresh_token, user_auth_type, user_id) VALUES (100000005, NULL, NULL, NULL, NULL, NULL, '2124929779@qq.com', '小黑黑email', NULL, NULL, 'EMAIL', 1);
INSERT INTO "public"."ss_user_auth"(user_auth_id, create_time, create_user_id, update_time, update_user_id, user_auth_credential, user_auth_identifier, user_auth_nickname, user_auth_photo, user_auth_refresh_token, user_auth_type, user_id) VALUES (100000004, NULL, NULL, NULL, NULL, '$2a$10$nce0IbdzY4Z5gc76XII7WekdVdeEgzNAvpgIAMTjHJIDXSsudjigm', 'l123', '小蓝蓝', NULL, NULL, 'LOCAL', 4);
INSERT INTO "public"."ss_user_auth"(user_auth_id, create_time, create_user_id, update_time, update_user_id, user_auth_credential, user_auth_identifier, user_auth_nickname, user_auth_photo, user_auth_refresh_token, user_auth_type, user_id) VALUES (100000006, NULL, NULL, NULL, NULL, NULL, '18239286535', '小白白phone', NULL, NULL, 'PHONE', 2);

-- ----------------------------
-- Records of ss_user_role
-- ----------------------------
INSERT INTO "public"."ss_user_role"(user_role_id, create_time, create_user_id, update_time, update_user_id, role_id, user_id) VALUES (200011, NULL, NULL, NULL, NULL, 11, 2);
INSERT INTO "public"."ss_user_role"(user_role_id, create_time, create_user_id, update_time, update_user_id, role_id, user_id) VALUES (100010, NULL, NULL, NULL, NULL, 10, 1);
INSERT INTO "public"."ss_user_role"(user_role_id, create_time, create_user_id, update_time, update_user_id, role_id, user_id) VALUES (300012, NULL, NULL, NULL, NULL, 12, 3);
INSERT INTO "public"."ss_user_role"(user_role_id, create_time, create_user_id, update_time, update_user_id, role_id, user_id) VALUES (400012, NULL, NULL, NULL, NULL, 12, 4);


