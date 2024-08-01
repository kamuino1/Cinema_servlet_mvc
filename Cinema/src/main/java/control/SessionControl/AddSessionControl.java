/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.SessionControl;

import dao.FilmDAO;
import dao.RoomDAO;
import dao.SeatDAO;
import dao.SessionDAO;
import entities.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tienp
 */
@WebServlet(name = "AddSessionControl", urlPatterns = {"/addSession"})
public class AddSessionControl extends HttpServlet {

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
        int filmid = Integer.parseInt((String) request.getParameter("filmId"));
        int roomid = Integer.parseInt((String) request.getParameter("roomId"));
        LocalDate sdate = LocalDate.parse(request.getParameter("date"));
        LocalTime stime = LocalTime.parse(request.getParameter("time"));
        BigDecimal tiketPrice = new BigDecimal(request.getParameter("ticketPrice"));

//        System.out.println(filmid);
//        System.out.println(roomid);
//        System.out.println(sdate);
//        System.out.println(stime);
//        System.out.println(tiketPrice);

        SessionDAO sdao = new SessionDAO();
        FilmDAO fdao = new FilmDAO();
        RoomDAO rdao = new RoomDAO();
        SeatDAO seatdao = new SeatDAO();

        Session session = new Session(tiketPrice, sdate, stime, rdao.getRoomById(roomid));
        session.setFilm(fdao.getFilmById(filmid));

        request.setAttribute("cond", true);
        if (sdao.checkSession(session)) {
            sdao.addSession(session);
            seatdao.addFreeSeats(sdao.getIdSession(filmid, roomid, sdate, stime), roomid);
            request.setAttribute("mess", "Đã thêm Session thành công");
        } else {

            request.setAttribute("mess", "Session bị trùng thời gian");
            
        }
        request.getRequestDispatcher("loadAddSession").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddSessionControl.class.getName()).log(Level.SEVERE, null, ex);
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
