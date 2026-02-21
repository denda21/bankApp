
INSERT INTO bank_user (user_name, email, password)
VALUES ('test', 'test@test', '$2a$10$muMr6GW/aNL1xVwT9n7dmOlN62ChvZnhiv5aemcydRFlBhOZ58aZ6');
-- BCrypt hash of '123456'

INSERT INTO accounts (account_number, account_name, user_id, balance, updated_at) VALUES
('1000001', 'Account 1',  1, 100000, NOW()),
('1000002', 'Account 2',  1, 100000, NOW()),
('1000003', 'Account 3',  1, 100000, NOW()),
('1000004', 'Account 4',  1, 100000, NOW()),
('1000005', 'Account 5',  1, 100000, NOW()),
('1000006', 'Account 6',  1, 100000, NOW()),
('1000007', 'Account 7',  1, 100000, NOW()),
('1000008', 'Account 8',  1, 100000, NOW()),
('1000009', 'Account 9',  1, 100000, NOW()),
('1000010', 'Account 10', 1, 100000, NOW());
