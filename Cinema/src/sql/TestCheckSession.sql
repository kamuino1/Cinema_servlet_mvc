SELECT * FROM [cinema_db].[dbo].[sessions]  DELETE FROM [cinema_db].[dbo].[sessions] WHERE session_id = 35  INSERT INTO sessions (film_id, date, time, ticket_price, free_seats, room_id) VALUES (26, '2023-06-08', '00:50:00.0000000', 340.00, 30, 2)  DECLARE @new_start_date DATE = '2023-06-07'; DECLARE @duration INT = 50; -- phút DECLARE @new_start_time TIME = '23:50:00'; DECLARE @new_end_time TIME = DATEADD(MINUTE, @duration, @new_start_time);   SELECT * FROM sessions s JOIN films f ON s.film_id = f.film_id WHERE s.film_id = 26   AND s.room_id = 2   AND (         (           s.date = @new_start_date           AND ( 			DATEADD(MINUTE, f.duration, s.time) > @new_start_time 			OR s.time <  @new_end_time 			)         )         OR         (           s.date = DATEADD(DAY, -1, @new_start_date)           AND DATEADD(MINUTE, f.duration, s.time) > @new_start_time         )         OR         (           s.date = DATEADD(DAY, 1, @new_start_date)           AND DATEADD(MINUTE, -f.duration, s.time) < @new_start_time         )       )   UPDATE s SET      s.date = '2023-06-06',     s.time = DATEADD(MINUTE, -f.duration, s.time) FROM      sessions s INNER JOIN      films f ON s.film_id = f.film_id WHERE     s.session_id = 35;  