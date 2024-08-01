/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.SessionControl;

import dao.FilmDAO;
import dao.RoomDAO;
import dao.SeatDAO;
import dao.SessionDAO;
import entities.Seat;
import entities.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tienp
 */
@WebServlet(name = "SessiondetailControl", urlPatterns = {"/sessionDetail"})
public class SessionDetailControl extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        FilmDAO filmDao = new FilmDAO();
        SessionDAO sessionDao = new SessionDAO();
        SeatDAO seatDao = new SeatDAO();
        RoomDAO roomDao = new RoomDAO();
        int sessionid = Integer.parseInt(request.getParameter("sessionId"));
        Session s = sessionDao.getSessionById(sessionid);
        s.getFilm().setGenreList(filmDao.getGenresFilm(s.getFilm().getId()));
        s.setRoom(roomDao.getRoomById(s.getRoom().getId()));
        List<Seat> allSeat = seatDao.getAllSeatsByRoomId(s.getRoom().getId());
        List<Seat> freeSeat = seatDao.getAllFreeSeats(sessionid);
        
        request.setAttribute("session", s);
        request.setAttribute("allSeatList", allSeat);
        request.setAttribute("freeSeatList", freeSeat);
        
        request.getRequestDispatcher("/jsp/session.jsp").forward(request, response);
        
        
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SessionDetailControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
