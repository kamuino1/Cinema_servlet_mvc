/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import entities.Room;
import entities.Seat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tienp
 */
public class SeatDAO extends DAO {

    private static final String SELECT_ALL = "SELECT * FROM seats";
    private static final String DELETE_FREE_SEATS = "DELETE FROM free_seats ";
    
    private static final String SEAT_ID = " WHERE row_number = ? AND place_number = ? AND room_id = ?";
    private static final String SEAT_BY_ID = " WHERE seat_id = ?";
    private static final String WHERE_SESSIONID = " WHERE session_id = ?";
    private static final String AND_SESSIONID = "  AND session_id = ?";
    private static final String WHERE_SEATID = " WHERE seat_id = ?";
    private static final String WHERE_ROOMID = " WHERE room_id = ?;";
    
    private static final String INSERT_FREE_SEAT = "  INSERT INTO free_seats (session_id, seat_id)  VALUES (?, ?); ";
    private static final String SELECT_FREE_SEATS_BY_SESSION_ID = "SELECT * FROM free_seats JOIN seats s on free_seats.seat_id = s.seat_id WHERE session_id=?";
    
    public List<Seat> getAllSeats() {
        String query = SELECT_ALL;
        List<Seat> seatList = new ArrayList<>();

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {  // Thêm kiểm tra rs.next()
                Room room = new Room();
                room.setId(rs.getInt("room_id"));
                Seat seat = new Seat(rs.getInt("seat_id"),
                        rs.getInt("row_number"),
                        rs.getInt("place_number"));
                seat.setRoom(room);
                seatList.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seatList;
    }

    public List<Seat> getAllSeatsByRoomId(int roomid) {
        String query = SELECT_ALL + WHERE_ROOMID;
        List<Seat> seatList = new ArrayList<>();

        try {
            Connection conn1 = new DBContext().getConnection();
            PreparedStatement ps1 = conn1.prepareStatement(query);
            ps1.setInt(1, roomid);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Seat seat = new Seat(rs1.getInt("seat_id"),
                        rs1.getInt("row_number"),
                        rs1.getInt("place_number"));
                seatList.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seatList;
    }

    public List<Seat> getAllFreeSeats(int sessionId) {
        String query = SELECT_FREE_SEATS_BY_SESSION_ID;
        List<Seat> seatList = new ArrayList<>();

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, sessionId);
            rs = ps.executeQuery();
            while (rs.next()) {  // Thêm kiểm tra rs.next()

                Room room = new Room();
                room.setId(rs.getInt("room_id"));
                Seat seat = new Seat(rs.getInt("seat_id"),
                        rs.getInt("row_number"),
                        rs.getInt("place_number"));
                seat.setRoom(room);
                seatList.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seatList;
    }

    public void addFreeSeats(int sessionid, int seatid) {
        String query = INSERT_FREE_SEAT;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, sessionid);
            ps.setInt(2, seatid);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteFreeSeatsBySession(int sessionid) {
        String query = DELETE_FREE_SEATS + WHERE_SESSIONID;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, sessionid);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteFreeSeatBySeatId(int sessionid, int seatId) {
        String query = DELETE_FREE_SEATS + WHERE_SEATID + AND_SESSIONID;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, seatId);
            ps.setInt(2, sessionid);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Seat getSeatById(int seatid) {
        String query = SELECT_ALL + SEAT_BY_ID;
        try {
            Connection conn1 = new DBContext().getConnection();
            PreparedStatement ps1 = conn1.prepareStatement(query);
            ps1.setInt(1, seatid);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Room room = new Room();
                room.setId(rs1.getInt("room_id"));
                Seat seat = new Seat(rs1.getInt("seat_id"),
                        rs1.getInt("row_number"),
                        rs1.getInt("place_number"));
                seat.setRoom(room);
                return seat;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getSeatId(int placeNumber, int rowNumber, int roomid) {
        String query = SELECT_ALL + SEAT_ID;
        try {
            Connection conn1 = new DBContext().getConnection();
            PreparedStatement ps1 = conn1.prepareStatement(query);
            ps1.setInt(1, rowNumber);
            ps1.setInt(2, placeNumber);
            ps1.setInt(3, roomid);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                return rs1.getInt("seat_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
