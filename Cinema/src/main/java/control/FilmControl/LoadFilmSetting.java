/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.FilmControl;

import dao.FilmDAO;
import entities.Film;
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
@WebServlet(name = "LoadFilmSetting", urlPatterns = {"/loadFilmSetting"})
public class LoadFilmSetting extends HttpServlet {

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
        FilmDAO filmdao = new FilmDAO();
        List<Film> list = filmdao.getAllFilm();
        
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
        int totalFilms = list.size();
        int totalPages = totalFilms / PAGE_SIZE;
        if (totalFilms % 6 != 0) {
            totalPages++;
        }
        int start = (currentPage - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalFilms);
        List<Film> filmsForPage = list.subList(start, end);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        
        HttpSession session = request.getSession();
        session.setAttribute("filmList", filmsForPage);
        String check_film = (String) session.getAttribute("check_film");
        if(check_film == null){
            request.getRequestDispatcher("/jsp/adminPages/filmsSetting.jsp").forward(request, response);
        }else if(check_film.equals("delete")){
            session.removeAttribute("check_film");
            request.setAttribute("film_mess", "Delete film successfully");
            request.getRequestDispatcher("/jsp/adminPages/filmsSetting.jsp").forward(request, response);
        }else if(check_film.equals("update")){
            session.removeAttribute("check_film");
            request.setAttribute("film_mess", "Update film successfully");
            request.getRequestDispatcher("/jsp/adminPages/filmsSetting.jsp").forward(request, response);
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
            Logger.getLogger(LoadFilmSetting.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoadFilmSetting.class.getName()).log(Level.SEVERE, null, ex);
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
