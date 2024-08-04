/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import entities.Film;
import entities.Room;
import entities.Session;
import java.sql.Time;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tienp
 */
public class SessionDAO extends DAO {

    private static final String ALL_SESSION = "SELECT * FROM sessions s JOIN films f on s.film_id = f.film_id ";
    private static final String ADD_SESSION = "INSERT INTO sessions (film_id, date, time, ticket_price, free_seats, room_id) VALUES (?, ?, ?, ?, 30, ?)";
    private static final String DEL_SESSION = "DELETE FROM sessions WHERE session_id = ?";
    private static final String UPDATE_SESSION = "UPDATE dbo.sessions SET film_id = ?, date = ?, time = ?, ticket_price = ?, free_seats = ?, room_id = ?  ";

    private static final String ID_SESSION = " WHERE s.film_id = ? AND s.room_id = ? AND s.date = ? AND s.time = ?";
    private static final String CHECK_SESSION = "WHERE f.film_id = ? AND s.room_id = ? AND s.date = ? AND (s.time < ? AND (s.time + f.duration) > ?)";
    private static final String WHERE_SESSION_ID = " WHERE session_id = ?";
    private static final String WHERE_DEFAULT = " WHERE s.date>=? AND IF (s.date=?, s.time>=?, s.time>=?)";
    private static final String AND_FREE_SEATS = " WHERE s.free_seats>0";
    private static final String ORDER_BY_DATETIME_ASC = " ORDER BY s.date, s.time";
    private static final String ORDER_BY_DATETIME_DESC = " ORDER BY s.date DESC, s.time DESC";
    private static final String ORDER_BY_FILM_NAME = " ORDER BY f.film_name ";
    private static final String ORDER_BY_FREE_SEATS = " ORDER BY s.free_seats";
    private static final String DESCENDING = " DESC";

    public List<Session> getAllSession(String show, String sortBy, String sortMethod) throws Exception {
        List<Session> sessionList = new ArrayList<>();
        String query = ALL_SESSION;
        int check = 0;

        if (show.equals("onlyAvailable")) {
            query += AND_FREE_SEATS;
        }
        if (sortBy.equals("dateTime")) {
            check = 1;
            if (sortMethod.equals("asc")) {
                query += ORDER_BY_DATETIME_ASC;
            } else {
                query += ORDER_BY_DATETIME_DESC;
            }
        } else if (sortBy.equals("filmName")) {
            query += ORDER_BY_FILM_NAME;
        } else {
            query += ORDER_BY_FREE_SEATS;
        }
        if (check == 0) {
            if (sortMethod.equals("desc")) {
                query += DESCENDING;
            }
        }

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Film film = new Film(rs.getInt("film_id"),
                        rs.getString("film_name"),
                        rs.getString("description"),
                        rs.getString("poster_url"),
                        Duration.ofMinutes(rs.getInt("duration")));
                Room room = new Room();
                room.setId(rs.getInt("room_id"));

                Session session = new Session(rs.getInt("session_id"),
                        rs.getBigDecimal("ticket_price"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class),
                        film,
                        rs.getInt("free_seats"));
                session.setRoom(room);
                sessionList.add(session);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionList;
    }

    public Session getSessionById(int sessionId) {
        String query = ALL_SESSION + WHERE_SESSION_ID;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, sessionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Film film = new Film(rs.getInt("film_id"),
                        rs.getString("film_name"),
                        rs.getString("description"),
                        rs.getString("poster_url"),
                        Duration.ofMinutes(rs.getInt("duration")));
                Room room = new Room();
                room.setId(rs.getInt("room_id"));

                Session session = new Session(rs.getInt("session_id"),
                        rs.getBigDecimal("ticket_price"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class),
                        film,
                        rs.getInt("free_seats"));
                session.setRoom(room);
                return session;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSession(Session session) {
        String query = ADD_SESSION;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, session.getFilm().getId());
            ps.setObject(2, session.getDate());
            ps.setObject(3, session.getTime());
            ps.setBigDecimal(4, session.getTicketPrice());
            ps.setInt(5, session.getRoom().getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean checkSession(Session session) throws Exception {
        System.out.println("Begin check: ");
        LocalDateTime timeBegin1 = session.getDate().atTime(session.getTime());
        LocalDateTime timeEnd1 = timeBegin1.plus(session.getFilm().getDuration());
        System.out.println("BeginTime1:" + timeBegin1 + "EndTime1" + timeEnd1);
        List<Session> sList = getAllSession("all", "dateTime", "acs");

        for (Session s : sList) {
            System.out.println(s);
            LocalDateTime timeBegin2 = s.getDate().atTime(s.getTime());
            LocalDateTime timeEnd2 = timeBegin2.plus(s.getFilm().getDuration());
            System.out.println(s.getId() + ": BeginTime2:" + timeBegin2 + "EndTime2" + timeEnd2);
            if (timeBegin1.isBefore(timeEnd2) && timeEnd1.isAfter(timeBegin2)) {
                System.out.println("Checked");
                return false;

            }
        }
        return true;
    }

    public int getIdSession(int filmid, int roomid, LocalDate date, LocalTime time) throws Exception {
        List<Session> sessions = getAllSession("all", "dateTime", "asc");
        for(Session s : sessions){
            if(s.getFilm().getId() == filmid && s.getRoom().getId() == roomid
                    && s.getDate().equals(date) && s.getTime().equals(time)){
                return s.getId();
            }
        }
//        String query = "SELECT * FROM dbo.sessions WHERE film_id = ? AND room_id = ? AND date = ? AND time = ?";
//        try {
//            conn = new DBContext().getConnection();
//            ps = conn.prepareStatement(query);
//            ps.setInt(1, filmid);
//            ps.setInt(2, roomid);
//            ps.setObject(3, date);
//            ps.setObject(4, time);
////            Date sqlDate = Date.valueOf(date);
////            ps.setDate(3, sqlDate);
////
////            // Chuyển đổi LocalTime thành java.sql.Time
////            Time sqlTime = Time.valueOf(time);
////            ps.setTime(4, sqlTime);
//            System.out.println(query);
//            rs = ps.executeQuery();
//            System.out.println("check2");
//            while (rs.next()) {
//                System.out.println("check");
//                return rs.getInt("session_id");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return 0;
    }

    public void deleteSession(int sessionid) {
        String query = DEL_SESSION;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, sessionid);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSession(Session session) {
        String query = UPDATE_SESSION + WHERE_SESSION_ID;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, session.getFilm().getId());
            ps.setObject(2, session.getDate());
            ps.setObject(3, session.getTime());
            ps.setBigDecimal(4, session.getTicketPrice());
            ps.setInt(5, session.getSeatsAmount());
            ps.setInt(6, session.getRoom().getId());
            ps.setInt(7, session.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
