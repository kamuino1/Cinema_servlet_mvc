/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.TicketControl;

import dao.SeatDAO;
import dao.SessionDAO;
import dao.TicketDAO;
import entities.Session;
import entities.Ticket;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author tienp
 */
@WebServlet(name = "SaveTicketControl", urlPatterns = {"/saveTicket"})
public class SaveTicketControl extends HttpServlet {

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
        TicketDAO tdao = new TicketDAO();
        SeatDAO seatdao = new SeatDAO();
        SessionDAO sessiondao = new SessionDAO();
        HttpSession session = request.getSession();
        
        List<Ticket> tickets = (List<Ticket>) session.getAttribute("ticketList");
        System.out.println("check user: ");
        for(Ticket t : tickets){
            tdao.addTicket(t);
            Session s = t.getSession();
            s.setSeatsAmount((s.getSeatsAmount() - 1));
            sessiondao.updateSession(s);
            seatdao.deleteFreeSeatBySeatId(s.getId(),t.getSeat().getId());
        }
        
        session.removeAttribute("ticketList");
        request.getRequestDispatcher("/jsp/successPay.jsp").forward(request, response);
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
