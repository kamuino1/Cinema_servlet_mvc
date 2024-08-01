 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author tienp
 */
public class TicketDAO extends DAO{
    private static final String INSERT = "INSERT INTO tickets VALUES (ticket_id, ?,?,?,?)";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM tickets WHERE user_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM tickets t WHERE ticket_id=?";
    private static final String COUNT_TOTAL_ROWS = "SELECT COUNT(*) FROM tickets WHERE user_id=?";
    private static final String LIMIT = " LIMIT ?, ?";
}
