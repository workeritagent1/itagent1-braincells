-- used in tests that use HSQL
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONGVARBINARY,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);

create table oauth_code (
  code VARCHAR(256), authentication LONGVARBINARY
);

create table oauth_approvals (
     userId VARCHAR(256),
     clientId VARCHAR(256),
     scope VARCHAR(256),
     status VARCHAR(10),
     expiresAt TIMESTAMP NULL,
     lastModifiedAt TIMESTAMP NULL
);

-- 插入一条数据
INSERT INTO `oauth_client_details` VALUES ( 'clientId1', '1', '$2a$10$WFDhpNunaH0aPlPQp33q8OAlqot6NANran7HeP/DSSWt1kVRvF2d2', 'all', 'password,refresh_token,authorization_code,implicit,client_credentials', '百度一下，你就知道', 'ROLE_ADMIN', 604800, 1209600, '{\"info\": \"test\"}', 'false' );