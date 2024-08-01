/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import entities.Room;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tienp
 */
public class RoomDAO extends DAO {
    public Room getRoomById(int roomId){
        String query = "SELECT * FROM room WHERE room_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            while (rs.next()) {  // Thêm kiểm tra rs.next()
                return new Room(roomId, rs.getString("room_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Room> getAllRoom(){
        List<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM room ";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {  // Thêm kiểm tra rs.next()
                roomList.add(new Room(rs.getInt("room_id"),rs.getString("room_name")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomList;
    }
}
