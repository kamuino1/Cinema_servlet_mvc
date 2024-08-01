/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.TicketControl;

import dao.RoomDAO;
import dao.SeatDAO;
import dao.SessionDAO;
import entities.Room;
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
@WebServlet(name = "LoadPayingControl", urlPatterns = {"/loadPaying"})
public class LoadPayingControl extends HttpServlet {

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
        HttpSession session = request.getSession();
        if (session.getAttribute("ticketList") == null) {
            SessionDAO sessionDao = new SessionDAO();
            SeatDAO seatDao = new SeatDAO();
            RoomDAO roomDao = new RoomDAO();

            String[] seatIds = request.getParameterValues("seatIds");
            int sessionId = Integer.parseInt(request.getParameter("sessionId"));
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            User u = (User) session.getAttribute("acc");
            Room room = roomDao.getRoomById(roomId);
            Session s = sessionDao.getSessionById(sessionId);
            s.setRoom(room);
            List<Ticket> ticketList = new ArrayList<>();
            for (String seatid : seatIds) {
                Seat seat = seatDao.getSeatById(Integer.parseInt(seatid));
//                System.out.println(seat);
                ticketList.add(new Ticket(s, u, seat, s.getTicketPrice()));
            }

            session.setAttribute("ticketList", ticketList);
        }
//        response.sendRedirect("/jsp/paying.jsp");
        request.getRequestDispatcher("/jsp/paying.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
