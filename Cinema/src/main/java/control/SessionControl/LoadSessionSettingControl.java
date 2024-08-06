/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.SessionControl;

import dao.FilmDAO;
import dao.RoomDAO;
import dao.SessionDAO;
import entities.Film;
import entities.Room;
import entities.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tienp
 */
@WebServlet(name = "LoadSessionSettingControl", urlPatterns = {"/loadSessionSetting"})
public class LoadSessionSettingControl extends HttpServlet {

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
        SessionDAO sDao = new SessionDAO();
        FilmDAO fDao = new FilmDAO();
        RoomDAO rDao = new RoomDAO();

        List<Session> sessionList = sDao.getAllSession("all", "dateTime", "desc");
        for (Session s : sessionList) {
            Film f = s.getFilm();
            Room room = s.getRoom();
            int roomId = room.getId();

            f.setGenreList(fDao.getGenresFilm(f.getId()));

            s.setFilm(f);
            s.setRoom(rDao.getRoomById(roomId));
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
        int totals = sessionList.size();
        int totalPages = totals / PAGE_SIZE;
        if (totals % 6 != 0) {
            totalPages++;
        }
        int start = (currentPage - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totals);
        List<Session> sessionForPage = sessionList.subList(start, end);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        
        HttpSession session = request.getSession();
        request.setAttribute("sessionList", sessionForPage);
        String check_session = (String) session.getAttribute("check_session");
        if (check_session == null) {

            request.getRequestDispatcher("/jsp/adminPages/sessionsSetting.jsp").forward(request, response);

        } else if (check_session.equals("delete")){
            request.setAttribute("session_mess", "Delete session successfully");
            session.removeAttribute("check_session");
            request.getRequestDispatcher("/jsp/adminPages/sessionsSetting.jsp").forward(request, response);

        }else if(check_session.equals("update")){
            request.setAttribute("session_mess", "Update session successfully");
            session.removeAttribute("check_session");
            request.getRequestDispatcher("/jsp/adminPages/sessionsSetting.jsp").forward(request, response);
        }else if(check_session.equals("nonUpdate")){
            request.setAttribute("session_mess", "Session time overlapy");
            session.removeAttribute("check_session");
            request.getRequestDispatcher("/jsp/adminPages/sessionsSetting.jsp").forward(request, response);
        }
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
            Logger.getLogger(LoadSessionSettingControl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoadSessionSettingControl.class.getName()).log(Level.SEVERE, null, ex);
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
