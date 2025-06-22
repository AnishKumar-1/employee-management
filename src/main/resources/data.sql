-- Insert Roles
INSERT INTO `Role` (name) VALUES ('ROLE_ADMIN'),('ROLE_MANAGER');

-- Insert Users (email must match LoginDto input)
INSERT INTO `Users` (email, password, role_id)
VALUES ('admin@gmail.com', '$2a$12$yIZIu5OUp2CNM5K5IOdUAesA7jq8UcVKgN73B3tcS9jDaQWkv2dQa', 1),
('manager@gmail.com','$2a$12$eba60AG/lQe67Bp3eYITE.jCyDNWqLjrR3kgzPZV7Mtz3Sz2yzFNS',2);
