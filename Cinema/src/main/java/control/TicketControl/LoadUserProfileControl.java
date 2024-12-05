/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.TicketControl;

import dao.RoomDAO;
import dao.SeatDAO;
import dao.SessionDAO;
import dao.TicketDAO;
import entities.Seat;
import entities.Session;
import entities.Ticket;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tienp
 */
@WebServlet(name = "LoadUserProfileControl", urlPatterns = {"/loadUserProfile"})
public class LoadUserProfileControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        TicketDAO ticketDao = new TicketDAO();
        SessionDAO sessionDao = new SessionDAO();
        SeatDAO seatDao = new SeatDAO();
        RoomDAO roomDao = new RoomDAO();
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("acc");

        List<Ticket> tickets = ticketDao.getAllTicketByUserId(u.getId());
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket t : tickets) {
            Session s = sessionDao.getSessionById(t.getSession().getId());
            Seat seat = seatDao.getSeatById(t.getSeat().getId());
            seat.setRoom(roomDao.getRoomById(seat.getRoom().getId()));
            ticketList.add(new Ticket(s, u, seat, t.getTicketPrice()));
        }

        int PAGE_SIZE = 6;
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1; // Nếu tham số không hợp lệ, mặc định về trang 1
            }
        }
        int totals = ticketList.size();
        int totalPages = totals / PAGE_SIZE;
        if (totals % 6 != 0) {
            totalPages++;
        }
        int start = (currentPage - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totals);
        List<Ticket> ticketForPage = ticketList.subList(start, end);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("ticketList", ticketForPage);
        
        request.getRequestDispatcher("/jsp/userProfile.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
