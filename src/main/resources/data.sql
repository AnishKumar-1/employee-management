-- Insert Roles
INSERT INTO `Role` (name) VALUES
('ROLE_ADMIN'),
('ROLE_MANAGER'),
('ROLE_PROGRAMMER'),
('ROLE_ASSISTANT_SYSTEM_ENGINEER'),
('ROLE_SYSTEM_ENGINEER'),
('ROLE_DEVELOPER'),
('ROLE_ANALYST'),
('ROLE_HR');


-- Insert Users (email must match LoginDto input)
INSERT INTO `users` (email, password, role_id)
VALUES
('admin@gmail.com', '$2a$12$yIZIu5OUp2CNM5K5IOdUAesA7jq8UcVKgN73B3tcS9jDaQWkv2dQa',1),
('manager@gmail.com','$2a$12$eba60AG/lQe67Bp3eYITE.jCyDNWqLjrR3kgzPZV7Mtz3Sz2yzFNS',2),
('anish@gmail.com', '$2a$12$bVwGA1ALQIjPXhJCtteepePThbyDY8ENpbjtvX7yZGQ6ENmSmBxBK',3),
('rahul@gmail.com','$2a$12$Naw.2t6k2HoUoIzQeUZxE.fCd9JeHcHZX8gkI4Tj6jcDTgOqeOCqu',3),
('rohini@gmail.com','$2a$12$7N5AuLhqDpVUyqNrxs.KMuo8z6cf8TAXuOup3Fd2mdsQWoxmgkyzK',3),
('sumith@gmail.com', '$2a$12$s81MIY9oQuMHj5Fd3K6vCO9GcDouLdnduxZvvh7eg1zZF/gceVvUG',3),
('json@gmail.com','$2a$12$fLP/bKFwBgnDNYh/oxQ5k.HI1mmZW59H67BVcCy8gjAqEm5./Vb7K',3),
('jenish@gmail.com','$2a$12$UVUfp/bKPyr.6.yvSU8EeelDdAdMCcck8qfP/abm6hMReSiQBAzX.',3);



INSERT INTO department (name, description) VALUES
 ('Software Development', 'Main tech department'),
 ('Human Resources', 'Manages employee relations, hiring, and welfare'),
 ('Finance', 'Handles budgeting, payroll, and expenses'),
 ('Sales', 'Responsible for selling products/services'),
 ('Marketing', 'Promotes brand and manages campaigns'),
 ('IT Support', 'Provides technical assistance and system support'),
 ('Quality Assurance', 'Ensures product quality through testing'),
 ('Research and Development', 'Innovates and creates new products'),
 ('Operations', 'Manages day-to-day business processes'),
 ('Legal', 'Handles contracts, compliance, and legal issues');

INSERT INTO designation (title)
VALUES
  ('Software Engineer'),
  ('QA Engineer'),
  ('Project Manager'),
  ('Tech Lead'),
  ('HR Executive');


INSERT INTO project (name, description, start_date)
VALUES
  ('Employee Management System', 'Internal tool to manage employee records', '2025-06-25'),
  ('Billing System', 'Automates client billing and invoicing', '2025-06-01'),
  ('E-Commerce Platform', 'Online retail platform for B2C', '2025-05-20');
