INSERT INTO user(
ban_lift_date, user_perm_ban, user_temp_ban, user_created, user_mail, user_name, user_pass, user_type
) VALUES (
CURRENT_TIMESTAMP, false, false, CURRENT_TIMESTAMP, 'kermit@henson.co', 'Kermit the Frog', '<3piggy', 'ADMIN'
);

INSERT INTO forum_section(
element_creation_date, element_edit_date, element_type, element_pinned, section_desc, section_title, author_user_id, editor_user_id
) VALUES (
CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROOT', true, 'forum description', 'Forum title',
(SELECT user_id from user where user_type like 'ADMIN'),
(SELECT user_id from user where user_type like 'ADMIN')
);

INSERT INTO user_privileges(
priv_usertype, priv_scopeid
) VALUES (
'ADMIN', (SELECT user_id from user where user_type like 'ADMIN')
);