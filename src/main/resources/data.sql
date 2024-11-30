INSERT INTO app_user (name, email, password) VALUES
('Alice Smith', 'alice.smith@example.com', 'password123'),
('Bob Johnson', 'bob.johnson@example.com', 'securepassword456'),
('Charlie Brown', 'charlie.brown@example.com', 'charlie789'),
('David Lee', 'david.lee@example.com', 'david2023'),
('Eva White', 'eva.white@example.com', 'evapassword2024'),
('Frank Green', 'frank.green@example.com', 'frank@123'),
('Grace Walker', 'grace.walker@example.com', 'gracepass2024'),
('Henry Adams', 'henry.adams@example.com', 'henry2024pass'),
('Ivy Turner', 'ivy.turner@example.com', 'ivysecure!2024'),
('Jack Wilson', 'jack.wilson@example.com', 'jackstrongpwd');

INSERT INTO user_roles (user_id, roles) VALUES
(1, 'RIDER'),
(2, 'DRIVER'),
(3, 'RIDER'),
(4, 'RIDER'),
(5, 'RIDER'),
(6, 'DRIVER'),
(7, 'RIDER'),
(8, 'RIDER'),
(9, 'RIDER'),
(10, 'DRIVER');


INSERT INTO rider (user_id, rating) VALUES
(1,4.2);

INSERT INTO driver (user_id, rating, available, vehicle_id, currentlocation) VALUES
(2, 4.3, true, 'MH09AK2324', ST_GeomFromText('POINT(73.9070 18.5604)')),   -- Near Kothrud, Pune
(3, 4.8, true, 'MH09AK3456', ST_GeomFromText('POINT(73.7898 18.5090)')),   -- Near Shivajinagar, Pune
(1, 4.5, true, 'MH09AK1234', ST_GeomFromText('POINT(73.8567 18.5204)')),   -- Pune city center
(4, 4.7, true, 'MH09AK4567', ST_GeomFromText('POINT(73.8467 18.4642)')),   -- Near Baner, Pune
(5, 4.6, true, 'MH09AK5678', ST_GeomFromText('POINT(73.8342 18.5535)')),   -- Near Wakad, Pune
(6, 5.0, true, 'MH09AK6789', ST_GeomFromText('POINT(73.9633 18.5141)')),   -- Near Magarpatta, Pune
(7, 4.4, true, 'MH09AK7890', ST_GeomFromText('POINT(73.7763 18.5236)')),   -- Near Koregaon Park, Pune
(8, 4.9, true, 'MH09AK8901', ST_GeomFromText('POINT(73.8627 18.5468)')),   -- Near Viman Nagar, Pune
(9, 4.2, true, 'MH09AK9012', ST_GeomFromText('POINT(73.9123 18.5042)')),   -- Near Hinjewadi, Pune
(10, 4.1, true, 'MH09AK1235', ST_GeomFromText('POINT(73.8930 18.5730)'));  -- Near Pimple Saudagar, Pune

INSERT INTO wallet (user_id, balance) VALUES
(1,100),
(2,200);