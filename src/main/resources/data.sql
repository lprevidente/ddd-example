-- TRUNCATE
DELETE FROM team_members;
DELETE FROM teams;
DELETE FROM users;

-- Users data
-- Note: Passwords are BCrypt hashed 'Password123!' for all users
INSERT INTO users (id, email, first_name, last_name, password) VALUES
       ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'john.smith@example.com', 'John', 'Smith', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('550e8400-e29b-41d4-a716-446655440000', 'emily.jones@example.com', 'Emily', 'Jones', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('38400000-8cf0-11bd-b23e-10b96e4ef00d', 'michael.brown@example.com', 'Michael', 'Brown', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'sarah.wilson@example.com', 'Sarah', 'Wilson', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('6ba7b810-9dad-11d1-80b4-00c04fd430c8', 'david.lee@example.com', 'David', 'Lee', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('6ba7b811-9dad-11d1-80b4-00c04fd430c8', 'jennifer.taylor@example.com', 'Jennifer', 'Taylor', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('6ba7b812-9dad-11d1-80b4-00c04fd430c8', 'robert.martinez@example.com', 'Robert', 'Martinez', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('6ba7b813-9dad-11d1-80b4-00c04fd430c8', 'lisa.rodriguez@example.com', 'Lisa', 'Rodriguez', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('6ba7b814-9dad-11d1-80b4-00c04fd430c8', 'james.anderson@example.com', 'James', 'Anderson', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q'),
       ('6ba7b815-9dad-11d1-80b4-00c04fd430c8', 'sophia.garcia@example.com', 'Sophia', 'Garcia', '$2a$12$8KnVJG9S8SBvzYLyA/fNJeMTQ5UigBEWgdojAqSr5u7c5fD/FAq4q');

-- Teams data

INSERT INTO teams (id, created_at, name)
VALUES ('c81d4e2e-bcf2-11e6-869b-7df92533d2db', '2023-10-15 09:00:00', 'Engineering'),
       ('c81d4e2e-bcf2-11e6-869b-7df92533d2dc', '2023-11-01 10:30:00', 'Marketing'),
       ('c81d4e2e-bcf2-11e6-869b-7df92533d2dd', '2023-11-20 14:15:00', 'Sales'),
       ('c81d4e2e-bcf2-11e6-869b-7df92533d2de', '2023-12-05 11:45:00', 'Product'),
       ('c81d4e2e-bcf2-11e6-869b-7df92533d2df', '2024-01-10 08:20:00', 'Customer Support');

-- Team Membership data

INSERT INTO team_members (user_id, team_id, joined_at) VALUES
-- Engineering team members
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'c81d4e2e-bcf2-11e6-869b-7df92533d2db', '2023-10-16 10:00:00'), -- John Smith
('550e8400-e29b-41d4-a716-446655440000', 'c81d4e2e-bcf2-11e6-869b-7df92533d2db', '2023-10-17 11:30:00'), -- Emily Jones
('6ba7b810-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2db', '2023-10-20 09:15:00'), -- David Lee

-- Marketing team members
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'c81d4e2e-bcf2-11e6-869b-7df92533d2dc', '2023-11-02 13:45:00'), -- Sarah Wilson
('6ba7b811-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2dc', '2023-11-03 14:20:00'), -- Jennifer Taylor
('6ba7b815-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2dc', '2023-11-05 10:10:00'), -- Sophia Garcia

-- Sales team members
('38400000-8cf0-11bd-b23e-10b96e4ef00d', 'c81d4e2e-bcf2-11e6-869b-7df92533d2dd', '2023-11-21 09:30:00'), -- Michael Brown
('6ba7b812-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2dd', '2023-11-22 11:00:00'), -- Robert Martinez
('6ba7b814-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2dd', '2023-11-23 15:45:00'), -- James Anderson

-- Product team members
('550e8400-e29b-41d4-a716-446655440000', 'c81d4e2e-bcf2-11e6-869b-7df92533d2de', '2023-12-06 13:15:00'), -- Emily Jones (also in Engineering)
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'c81d4e2e-bcf2-11e6-869b-7df92533d2de', '2023-12-07 10:30:00'), -- Sarah Wilson (also in Marketing)
('6ba7b813-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2de', '2023-12-08 14:45:00'), -- Lisa Rodriguez

-- Customer Support team members
('6ba7b813-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2df', '2024-01-11 09:00:00'), -- Lisa Rodriguez (also in Product)
('38400000-8cf0-11bd-b23e-10b96e4ef00d', 'c81d4e2e-bcf2-11e6-869b-7df92533d2df', '2024-01-12 10:15:00'), -- Michael Brown (also in Sales)
('6ba7b815-9dad-11d1-80b4-00c04fd430c8', 'c81d4e2e-bcf2-11e6-869b-7df92533d2df', '2024-01-13 11:30:00'); -- Sophia Garcia (also in Marketing)