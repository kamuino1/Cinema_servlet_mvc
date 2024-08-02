

-- Table structure for table `films`
--
CREATE DATABASE cinema_db;


USE cinema_db
GO
-- Kiểm tra và xóa bảng nếu tồn tại
IF OBJECT_ID('dbo.films', 'U') IS NOT NULL 
   DROP TABLE dbo.films;
GO

-- Tạo bảng mới
CREATE TABLE dbo.films (
  film_id INT NOT NULL IDENTITY(1,1),
  film_name NVARCHAR(120) NOT NULL,
  [description] NVARCHAR(800) NULL,
  poster_url NVARCHAR(2000) NOT NULL DEFAULT 'https://upload.wikimedia.org/wikipedia/commons/a/ac/No_image_available.svg',
  duration INT NOT NULL,
  PRIMARY KEY (film_id)
);
GO

--
-- Dumping data for table `films`
--

BEGIN TRANSACTION;

ALTER INDEX ALL ON dbo.films DISABLE;
ALTER INDEX [PK__films__349764A9BAC465A9] ON [films] REBUILD;
SET IDENTITY_INSERT films ON;


INSERT INTO dbo.films (film_id, film_name, [description], poster_url, duration) VALUES
(2, 'Fluffy brawlers', 'One king of bears named Bantura appropriated something that in fact did not belong to him - this is the magical Water Stone. This artifact has always been in the forest, thanks to which the stream was always full of healing liquid. Now, without the Stone, there is almost no water in the stream, it literally dries up completely from day to day. To fix this situation, someone needs to go to Bantura''s lair and take the stone from him. The brave hedgehog Latte and her squirrel friend Tyum agreed to this. Many adventures will be encountered on their way, and the leader of the wolf gang Lupo and Lynx will bring them the most trouble. This will be a real test for Latte and Tyum''s friendship.', 'https://kinoafisha.ua/upload/2019/07/films/8906/20fejso5ejik-latte-i-magicseskii-kamen.jpg', 81),
(3, 'Quick', 'In order to save his wife from an illness, an honored veteran turns to the adopted brother of a criminal for help. Robbing a bank and stealing a multimillion-dollar fortune seems like a good idea... But when things don''t go according to plan, the heroes have no choice but to steal an ambulance with two hostages inside.', 'https://woodmallcinema.com/storage/app/uploads/public/ba3/865/a70/thumb__450_0_0_0_auto.jpg', 136),
(4, 'Batman', NULL, 'https://woodmallcinema.com/storage/app/uploads/public/8a2/3e7/b0e/thumb__450_0_0_0_auto.jpg', 176),
(5, 'Date in Paris', 'Vincenzo (Sergio Castellitto) is a bookstore owner In Paris, they rarely buy anything, preferring to read with the help of gadgets. Meanwhile, he lives above the store with his daughter, who has been confined to a wheelchair for the past four years. Since then, the girl does not speak, and her father tries to comfort her. Vincenzo''s settled and leisurely life is turned upside down with the appearance of the actress Iolanta (Berenice Bejo) in the shop. This eccentric woman, it would seem, was supposed to cause irritation. Vincenzo is used to silence, and this tradition should be respected. Iolanta will try to share her perspective on life with him.', 'https://woodmallcinema.com/storage/app/uploads/public/bba/5eb/117/thumb__450_0_0_0_auto.jpg', 98),
(25, 'Game of Shadows', '', 'https://woodmallcinema.com/storage/app/uploads/public/e19/ec4/a50/thumb__450_0_0_0_auto.jpg', 108),
(26, 'Jurassic World 3: Dominion', '', 'https://woodmallcinema.com/storage/app/uploads/public/4d7/699/e54/thumb__450_0_0_0_auto.jpg', 120),
(29, 'Panama', '', 'https://woodmallcinema.com/storage/app/uploads/public/edd/3c9/a54/thumb__450_0_0_0_auto.jpg', 94),
(30, 'Pagans', '''Pagans'' is an American computer-animated heist comedy film produced by DreamWorks Animation and Scholastic Entertainment. The film is based on the popular children''s book series written by Aaron Blabey.', 'https://woodmallcinema.com/storage/app/uploads/public/54c/93d/8a6/thumb__450_0_0_0_auto.jpg', 100),
(32, 'Fantastic Beasts: The Mysteries of Dumbledore', 'In 1932, a lover of magical creatures named Newt Salamander is in the city of Quilin, located in China. He takes birth from the dragon-like Tsylin, who can see a person''s soul and the events of the future. Twins are born, but at this moment, the henchmen of Gellert Grindelwald, led by the vile Credence Barebone, attack and kill the mother, taking one of the cubs. The presence of the second villain is not suspected, and the main character manages to escape with the newborn. When the antagonist obtains the beast, it kills, taking away the supernatural gift of foresight.', 'https://woodmallcinema.com/storage/app/uploads/public/1bb/c7a/67d/thumb__450_0_0_0_auto.jpg', 142);
SET IDENTITY_INSERT films OFF;
-- Enable indexes
ALTER INDEX ALL ON dbo.films REBUILD;
COMMIT;

------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `genres`
--
-- Kiểm tra và xóa bảng nếu tồn tại
IF OBJECT_ID('dbo.genres', 'U') IS NOT NULL
    DROP TABLE dbo.genres;

-- Tạo bảng
CREATE TABLE dbo.genres (
    genre_id INT IDENTITY(1,1) PRIMARY KEY,
    genre_name NVARCHAR(45) NOT NULL UNIQUE
);
-- Chèn dữ liệu vào bảng
SET IDENTITY_INSERT dbo.genres ON;
INSERT INTO dbo.genres (genre_id, genre_name) VALUES
(15,N'Chính kịch lịch sử'),
(9,N'Hoạt hình'),
(10,N'Hồi ký'),
(6,N'Hành động'),
(12,N'Miền Tây'),
(13,N'Trinh thám'),
(14,N'Thiếu nhi'),
(11,N'Phim tài liệu'),
(3,N'Chính kịch'),
(5,N'Phim Hành động'),
(20,N'Kinh dị'),
(1,N'Hài kịch'),
(4,N'Hình sự'),
(16,N'Tình cảm'),
(17,N'Nhạc kịch'),
(7,N'Phiêu lưu'),
(2,N'Lãng mạn'),
(8,N'Gia đình'),
(18,N'Rùng rợn'),
(19,N'Viễn tưởng');
SET IDENTITY_INSERT dbo.genres OFF;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `films_genres`
--
-- Kiểm tra và xóa bảng nếu tồn tại

IF OBJECT_ID('dbo.films_genres', 'U') IS NOT NULL
    DROP TABLE dbo.films_genres;

-- Tạo bảng
CREATE TABLE dbo.films_genres (
    film_id INT NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT PK_film_genre PRIMARY KEY (film_id, genre_id),
    CONSTRAINT FK_film_id FOREIGN KEY (film_id) REFERENCES dbo.films (film_id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT FK_genre_id FOREIGN KEY (genre_id) REFERENCES dbo.genres (genre_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Chèn dữ liệu vào bảng
INSERT INTO dbo.films_genres (film_id, genre_id) VALUES
(2, 1),
(2, 7),
(2, 8),
(2, 9),
(3, 3),
(3, 4),
(3, 5),
(4, 4),
(4, 5),
(4, 6),
(5, 1),
(5, 2),
(25, 10),
(25, 3),
(26, 7),
(29, 6),
(29, 4),
(30, 9),
(30, 1),
(30, 7),
(30, 8),
(32, 7),
(32, 8),
(32, 19);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `seats`
--

-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('dbo.seats', 'U') IS NOT NULL
    DROP TABLE dbo.seats;

-- Tạo bảng `seats`
CREATE TABLE dbo.seats (
    seat_id INT IDENTITY(1,1) PRIMARY KEY,
    row_number INT NOT NULL,
    place_number INT NOT NULL
);

ALTER TABLE dbo.seats
ADD room_id INT NOT NULL DEFAULT 1
CONSTRAINT FK_Seats_Rooms FOREIGN KEY (room_id) REFERENCES dbo.room(room_id);

ALTER TABLE dbo.seats
ALTER COLUMN room_id INT NOT NULL DEFAULT 2

SELECT name
FROM sys.default_constraints
WHERE parent_object_id = OBJECT_ID('dbo.seats')
  AND col_name(parent_object_id, parent_column_id) = 'room_id';
  DF__seats__room_id__73BA3083

-- Thay 'DF_dbo.seats_room_id' bằng tên của ràng buộc mặc định cũ



-- Chèn dữ liệu vào bảng `seats`
SET IDENTITY_INSERT dbo.seats ON;
INSERT INTO dbo.seats (seat_id, row_number, place_number) VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,2,1),(12,2,2),(13,2,3),(14,2,4),(15,2,5),(16,2,6),(17,2,7),(18,2,8),(19,2,9),(20,2,10),(21,3,1),(22,3,2),(23,3,3),(24,3,4),(25,3,5),(26,3,6),(27,3,7),(28,3,8),(29,3,9),(30,3,10);
SET IDENTITY_INSERT dbo.seats OFF;

ALTER TABLE dbo.seats
DROP CONSTRAINT DF__seats__room_id__73BA3083;

ALTER TABLE dbo.seats
ADD CONSTRAINT DF__seats__room_id__73BA3083 DEFAULT 6 FOR room_id;

INSERT INTO dbo.seats (row_number, place_number) VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(2,1),(2,2),(12,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(3,1),(3,2),(3,3),(3,4),(3,5),(3,6),(3,7),(3,8),(3,9),(3,10);

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `sessions`
--
-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('dbo.sessions', 'U') IS NOT NULL
    DROP TABLE dbo.sessions;

-- Tạo bảng `sessions`
CREATE TABLE dbo.sessions (
    session_id INT IDENTITY(1,1) PRIMARY KEY,
    film_id INT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    ticket_price DECIMAL(10,2) NOT NULL,
    free_seats INT NOT NULL,
    FOREIGN KEY (film_id) REFERENCES dbo.films(film_id) ON DELETE CASCADE
);

ALTER TABLE dbo.sessions
ADD room_id INT NOT NULL DEFAULT 1
CONSTRAINT FK_Sessions_Rooms FOREIGN KEY (room_id) REFERENCES dbo.room(room_id);

-- Chèn dữ liệu vào bảng `sessions`
SET IDENTITY_INSERT dbo.sessions ON;
INSERT INTO dbo.sessions (session_id, film_id, date, time, ticket_price, free_seats) VALUES (24,26,'2023-06-07','12:00:00',340.00,28),(25,25,'2023-06-09','12:00:00',220.00,30),(26,5,'2023-06-15','17:45:00',120.00,30),(27,4,'2023-06-07','19:00:00',370.00,30),(28,3,'2023-06-24','09:00:00',190.00,30),(29,3,'2023-06-22','15:15:00',350.00,30),(30,2,'2023-06-15','16:15:00',200.00,30),(31,5,'2023-06-15','14:00:00',220.00,30),(32,25,'2023-06-09','18:00:00',250.00,30),(33,26,'2023-06-09','12:11:00',290.00,30);
SET IDENTITY_INSERT dbo.sessions OFF;
----------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `free_seats`
--

IF OBJECT_ID('dbo.free_seats', 'U') IS NOT NULL
    DROP TABLE dbo.free_seats;

CREATE TABLE dbo.free_seats (
    session_seat_id INT IDENTITY(1,1) PRIMARY KEY,
    session_id INT NOT NULL,
    seat_id INT NOT NULL,
    CONSTRAINT FK_free_seats_sessions FOREIGN KEY (session_id) REFERENCES dbo.sessions(session_id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT FK_free_seats_seats FOREIGN KEY (seat_id) REFERENCES dbo.seats(seat_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Create indexes
CREATE INDEX idx_session_id ON dbo.free_seats(session_id);
CREATE INDEX idx_seat_id ON dbo.free_seats(seat_id);

-- Start a transaction
BEGIN TRANSACTION;
SET IDENTITY_INSERT dbo.free_seats ON;
-- Insert data
INSERT INTO dbo.free_seats (session_seat_id, session_id, seat_id) VALUES (341,24,1),(342,24,2),(343,24,3),(344,24,4),(345,24,5),(346,24,6),(347,24,7),(348,24,8),(349,24,9),(350,24,10),(351,24,11),(352,24,12),(353,24,13),(354,24,14),(357,24,17),(358,24,18),(359,24,19),(360,24,20),(361,24,21),(362,24,22),(363,24,23),(364,24,24),(365,24,25),(366,24,26),(367,24,27),(368,24,28),(369,24,29),(370,24,30),(371,25,1),(372,25,2),(373,25,3),(374,25,4),(375,25,5),(376,25,6),(377,25,7),(378,25,8),(379,25,9),(380,25,10),(381,25,11),(382,25,12),(383,25,13),(384,25,14),(385,25,15),(386,25,16),(387,25,17),(388,25,18),(389,25,19),(390,25,20),(391,25,21),(392,25,22),(393,25,23),(394,25,24),(395,25,25),(396,25,26),(397,25,27),(398,25,28),(399,25,29),(400,25,30),(401,26,1),(402,26,2),(403,26,3),(404,26,4),(405,26,5),(406,26,6),(407,26,7),(408,26,8),(409,26,9),(410,26,10),(411,26,11),(412,26,12),(413,26,13),(414,26,14),(415,26,15),(416,26,16),(417,26,17),(418,26,18),(419,26,19),(420,26,20),(421,26,21),(422,26,22),(423,26,23),(424,26,24),(425,26,25),(426,26,26),(427,26,27),(428,26,28),(429,26,29),(430,26,30),(431,27,1),(432,27,2),(433,27,3),(434,27,4),(435,27,5),(436,27,6),(437,27,7),(438,27,8),(439,27,9),(440,27,10),(441,27,11),(442,27,12),(443,27,13),(444,27,14),(445,27,15),(446,27,16),(447,27,17),(448,27,18),(449,27,19),(450,27,20),(451,27,21),(452,27,22),(453,27,23),(454,27,24),(455,27,25),(456,27,26),(457,27,27),(458,27,28),(459,27,29),(460,27,30),(461,28,1),(462,28,2),(463,28,3),(464,28,4),(465,28,5),(466,28,6),(467,28,7),(468,28,8),(469,28,9),(470,28,10),(471,28,11),(472,28,12),(473,28,13),(474,28,14),(475,28,15),(476,28,16),(477,28,17),(478,28,18),(479,28,19),(480,28,20),(481,28,21),(482,28,22),(483,28,23),(484,28,24),(485,28,25),(486,28,26),(487,28,27),(488,28,28),(489,28,29),(490,28,30),(491,29,1),(492,29,2),(493,29,3),(494,29,4),(495,29,5),(496,29,6),(497,29,7),(498,29,8),(499,29,9),(500,29,10),(501,29,11),(502,29,12),(503,29,13),(504,29,14),(505,29,15),(506,29,16),(507,29,17),(508,29,18),(509,29,19),(510,29,20),(511,29,21),(512,29,22),(513,29,23),(514,29,24),(515,29,25),(516,29,26),(517,29,27),(518,29,28),(519,29,29),(520,29,30),(521,30,1),(522,30,2),(523,30,3),(524,30,4),(525,30,5),(526,30,6),(527,30,7),(528,30,8),(529,30,9),(530,30,10),(531,30,11),(532,30,12),(533,30,13),(534,30,14),(535,30,15),(536,30,16),(537,30,17),(538,30,18),(539,30,19),(540,30,20),(541,30,21),(542,30,22),(543,30,23),(544,30,24),(545,30,25),(546,30,26),(547,30,27),(548,30,28),(549,30,29),(550,30,30),(551,31,1),(552,31,2),(553,31,3),(554,31,4),(555,31,5),(556,31,6),(557,31,7),(558,31,8),(559,31,9),(560,31,10),(561,31,11),(562,31,12),(563,31,13),(564,31,14),(565,31,15),(566,31,16),(567,31,17),(568,31,18),(569,31,19),(570,31,20),(571,31,21),(572,31,22),(573,31,23),(574,31,24),(575,31,25),(576,31,26),(577,31,27),(578,31,28),(579,31,29),(580,31,30),(581,32,1),(582,32,2),(583,32,3),(584,32,4),(585,32,5),(586,32,6),(587,32,7),(588,32,8),(589,32,9),(590,32,10),(591,32,11),(592,32,12),(593,32,13),(594,32,14),(595,32,15),(596,32,16),(597,32,17),(598,32,18),(599,32,19),(600,32,20),(601,32,21),(602,32,22),(603,32,23),(604,32,24),(605,32,25),(606,32,26),(607,32,27),(608,32,28),(609,32,29),(610,32,30),(611,33,1),(612,33,2),(613,33,3),(614,33,4),(615,33,5),(616,33,6),(617,33,7),(618,33,8),(619,33,9),(620,33,10),(621,33,11),(622,33,12),(623,33,13),(624,33,14),(625,33,15),(626,33,16),(627,33,17),(628,33,18),(629,33,19),(630,33,20),(631,33,21),(632,33,22),(633,33,23),(634,33,24),(635,33,25),(636,33,26),(637,33,27),(638,33,28),(639,33,29),(640,33,30);
SET IDENTITY_INSERT dbo.free_seats OFF;
COMMIT TRANSACTION;



--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `roles`
--
-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('dbo.roles', 'U') IS NOT NULL
    DROP TABLE dbo.roles;

-- Tạo bảng `roles`
CREATE TABLE dbo.roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(45) NOT NULL UNIQUE
);

-- Chèn dữ liệu vào bảng `roles`
INSERT INTO dbo.roles (role_name) VALUES
(N'admin'),
(N'user');






-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `tickets`
--
-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('dbo.tickets', 'U') IS NOT NULL
    DROP TABLE dbo.tickets;

-- Tạo bảng `tickets`
CREATE TABLE dbo.tickets (
    ticket_id INT IDENTITY(1,1) PRIMARY KEY,
    session_id INT NOT NULL,
    user_id INT NOT NULL,
    seat_id INT NOT NULL,
    ticket_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (session_id) REFERENCES dbo.sessions(session_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES dbo.users(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (seat_id) REFERENCES dbo.seats(seat_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Chèn dữ liệu vào bảng `tickets`
SET IDENTITY_INSERT dbo.tickets ON;
INSERT INTO dbo.tickets (ticket_id, session_id, user_id, seat_id, ticket_price) VALUES
(55,24,57,15,340.00),(56,24,57,16,340.00);
SET IDENTITY_INSERT dbo.tickets OFF;

-----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `users`
--

-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('dbo.users', 'U') IS NOT NULL
    DROP TABLE dbo.users;

-- Tạo bảng `users`
CREATE TABLE dbo.users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    role_id INT NOT NULL DEFAULT 2,
    first_name VARCHAR(45) NOT NULL,
    second_name VARCHAR(45) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    phone_number VARCHAR(13) NULL,
    notification BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (role_id) REFERENCES dbo.roles(role_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Chèn dữ liệu vào bảng `users`
SET IDENTITY_INSERT dbo.users ON;
INSERT INTO dbo.users (user_id, role_id, first_name, second_name, email, password, phone_number, notification) VALUES
(55, 2, 'Yehor', 'Liannyk', 'admin@test.com', 'iCXKHlYT6GNwwFby3eWafsE3ib74eTG5MpMOupXJ2Lk=', '', 1),
(57, 1, 'Max', 'Bondarenko', 'user@test.com', 'WvOaKWhYE58fFWTx91vzFzrROQIcthiayUyMzv75ayo=', '380679999999', 1);
SET IDENTITY_INSERT dbo.users OFF;

SET IDENTITY_INSERT dbo.users ON;
INSERT INTO dbo.users (user_id, role_id, first_name, second_name, email, password, phone_number, notification) VALUES
(1, 1, 'Tien', 'PA', 'tienp323@gmail.com', 'tien012369', '', 1);
SET IDENTITY_INSERT dbo.users OFF;

SET IDENTITY_INSERT dbo.users ON;
INSERT INTO dbo.users (first_name, second_name, email, password, phone_number, notification) VALUES
('Tien', 'PA', 'tienj323@gmail.com', 'tien012369', '', 1);
SET IDENTITY_INSERT dbo.users OFF;


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table `room`
--
IF OBJECT_ID('dbo.room', 'U') IS NOT NULL
    DROP TABLE dbo.room;

-- Tạo bảng `room`
CREATE TABLE dbo.room (
    room_id INT IDENTITY(1,1) PRIMARY KEY,
    room_name  VARCHAR(45) NOT NULL
);

INSERT INTO dbo.room (room_name) VALUES
(N'101'),
(N'102'),
(N'103'),
(N'104'),
(N'105'),
(N'106');
