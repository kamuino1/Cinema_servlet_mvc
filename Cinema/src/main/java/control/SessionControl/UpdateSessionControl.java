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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tienp
 */
@WebServlet(name = "UpdateSessionControl", urlPatterns = {"/updateSession"})
public class UpdateSessionControl extends HttpServlet {

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
        HttpSession sessions = request.getSession();
        SessionDAO sessionDao = new SessionDAO();
        FilmDAO fdao = new FilmDAO();
        RoomDAO rdao = new RoomDAO();
        SeatDAO seatdao = new SeatDAO();

        int sessionid = Integer.parseInt(request.getParameter("asession"));
        int filmid = Integer.parseInt((String) request.getParameter("filmId"));
        int roomid = Integer.parseInt((String) request.getParameter("roomId"));
        LocalDate sdate = LocalDate.parse(request.getParameter("date"));
        LocalTime stime = LocalTime.parse(request.getParameter("time"));
        BigDecimal tiketPrice = new BigDecimal(request.getParameter("ticketPrice"));
        Session asession = sessionDao.getSessionById(sessionid);
        int roomidBefor = asession.getRoom().getId();

        Session session = new Session(sessionid, tiketPrice, sdate, stime, fdao.getFilmById(filmid), asession.getSeatsAmount());
        session.setRoom(rdao.getRoomById(roomid));
        if (sessionDao.checkSession(session)) {
            sessionDao.updateSession(session);
            if (roomid != roomidBefor) {
                List<Seat> oldSeats = seatdao.getAllFreeSeats(sessionid);
                System.out.println("Check: oldSeats" + oldSeats);
                List<Seat> seatsByRoom = seatdao.getAllSeatsByRoomId(roomid);
                List<Seat> newSeats = new ArrayList<>();

                for (Seat s : seatsByRoom) {
                    Optional<Seat> rs = oldSeats.stream()
                            .filter(seat -> seat.getPlaceNumber() == s.getPlaceNumber()
                            && seat.getRowNumber() == s.getRowNumber())
                            .findFirst();

                    if (rs.isPresent()) {
                        System.out.println(s);
                        newSeats.add(s);
                    }

                }
                seatdao.deleteFreeSeatsBySession(sessionid);
                for (Seat seat : newSeats) {
                    seatdao.addFreeSeats(sessionid, seat.getId());
                }
            }
            sessions.setAttribute("check_session", "update");
        }else {
            sessions.setAttribute("check_session", "nonUpdate");
        }

        request.getRequestDispatcher("loadSessionSetting").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UpdateSessionControl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
