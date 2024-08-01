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
    private static final String SELECT_BY_ID = "SELECT * FROM seats WHERE seat_id=?";
    private static final String DELETE_FREE_SEATS = "DELETE FROM free_seats WHERE session_id = ?";
    private static final String SEAT_BY_ID = "SELECT * FROM seats WHERE seat_id = ?";
            
    private static final String WHERE_ROOMID = " WHERE room_id = ?;";
    private static final String INSERT_FREE_SEAT = "  INSERT INTO free_seats (session_id, seat_id)  VALUES (?, ?); ";
    private static final String SELECT_FREE_SEATS_BY_SESSION_ID = "SELECT * FROM free_seats JOIN seats s on free_seats.seat_id = s.seat_id WHERE session_id=?";
    private static final String SELECT_FREE_SEAT_BY_ID_AND_SESSION = "SELECT * FROM free_seats WHERE seat_id=? AND session_id=?";
    private static final String REMOVE_FREE_SEAT = "DELETE FROM free_seats WHERE seat_id=? AND session_id=?";

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

    public void addFreeSeats(int sessionid, int roomid) {
        String query = INSERT_FREE_SEAT;
        List<Seat> seatList = getAllSeatsByRoomId(roomid);
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            for (Seat seat : seatList) {
                ps.setInt(1, sessionid);
                ps.setInt(2, seat.getId());
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteFreeSeatsBySession(int sessionid) {
        String query = DELETE_FREE_SEATS;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public Seat getSeatById(int seatid){
        String query = SEAT_BY_ID ;
        try {
            Connection conn1 = new DBContext().getConnection();
            PreparedStatement ps1 = conn1.prepareStatement(query);
            ps1.setInt(1, seatid);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Seat seat = new Seat(rs1.getInt("seat_id"),
                        rs1.getInt("row_number"),
                        rs1.getInt("place_number"));
                return seat;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
}
