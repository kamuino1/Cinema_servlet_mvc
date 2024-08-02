/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import entities.Film;
import entities.Seat;
import entities.Session;
import entities.Ticket;
import entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tienp
 */
public class TicketDAO extends DAO {

    private static final String INSERT = "INSERT INTO dbo.tickets (session_id, user_id, seat_id, ticket_price) VALUES (?, ?, ?, ?);";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM tickets WHERE user_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM tickets t WHERE ticket_id=?";
    private static final String COUNT_TOTAL_ROWS = "SELECT COUNT(*) FROM tickets WHERE user_id=?";
    private static final String LIMIT = " LIMIT ?, ?";

    public void addTicket(Ticket ticket) {
        String query = INSERT;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, ticket.getSession().getId());
            ps.setInt(2, ticket.getUser().getId());
            ps.setInt(3, ticket.getSeat().getId());
            ps.setBigDecimal(4, ticket.getSession().getTicketPrice());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Ticket> getAllTicketByUserId(int userId){
        String query = SELECT_BY_USER_ID;
        List<Ticket> tickets = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while(rs.next()){
                Session session = new Session();
                User u = new User();
                Seat seat = new Seat();
                Film film = new Film();
                session.setId(rs.getInt("session_id"));
                u.setId(rs.getInt("user_id"));
                seat.setId(rs.getInt("seat_id"));
                tickets.add(new Ticket(session, u, seat, rs.getBigDecimal("ticket_price")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
