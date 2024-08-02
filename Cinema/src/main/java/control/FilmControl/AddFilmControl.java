/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.FilmControl;

import dao.FilmDAO;
import entities.Film;
import entities.Genre;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author tienp
 */
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
@WebServlet(name = "AddFilmControl", urlPatterns = {"/addFilm"})
public class AddFilmControl extends HttpServlet {

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
        FilmDAO dao = new FilmDAO();
        List<Genre> listGenre = dao.getAllGenres();
        request.setAttribute("genreList", listGenre);
        
        String filmName = request.getParameter("filmName");
        String filmDescription = request.getParameter("filmDescription");
        String posterUrl = request.getParameter("posterUrl");
        String filmDurationStr = request.getParameter("filmDuration");
        String[] genreIds = request.getParameterValues("genreIds");

        // Xác thực dữ liệu
        List<String> errors = new ArrayList<>();
        if (filmName == null || filmName.trim().isEmpty()) {
            request.setAttribute("filmName_empty", true);
        } else if(filmName.length() >= 119){
            request.setAttribute("filmName_length", true);
        }
        
        if (filmDescription == null || filmDescription.trim().isEmpty()) {
            errors.add("Film description is required");
        } else if (filmDescription.length() >= 799){
            request.setAttribute("filmDesc_length", true);
        }
        
        if (posterUrl == null || posterUrl.trim().isEmpty()) {
            request.setAttribute("url_empty", true);
        } else if (posterUrl.length() >= 799){
            request.setAttribute("url_length", true);
        }
        
        if (filmDurationStr == null || filmDurationStr.trim().isEmpty()) {
            request.setAttribute("filmDurationEmpty", true);
        }
        
        if (genreIds == null || genreIds.length == 0) {
            request.setAttribute("genreList_empty", true);
        }


        // Chuyển đổi filmDuration sang kiểu Duration
        int filmDuration = 0;
        try {
            filmDuration = Integer.parseInt(filmDurationStr);
            if(filmDuration >= 300){
                request.setAttribute("duration_range", true);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("duration_range", true);
        }

        // Tạo đối tượng Film
        Film film = new Film();
        film.setName(filmName);
        film.setDescription(filmDescription);
        film.setPosterUrl(posterUrl);
        film.setDuration(Duration.ofMinutes(filmDuration));

        // Tạo danh sách các thể loại (Genres)
        List<Genre> genreList1 = new ArrayList<>();
        try {
            for (String genreId : genreIds) {
                int id = Integer.parseInt(genreId);
                Genre genre = new Genre();
                genre.setId(id);
                genre.setName(dao.getNameGenre(id)); // Lấy tên thể loại từ cơ sở dữ liệu
                genreList1.add(genre);
            }
            film.setGenreList(genreList1);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Lưu đối tượng Film vào cơ sở dữ liệu
        try {
            dao.addFilm(film);
            response.sendRedirect("main");
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/jsp/adminPages/addFilm.jsp").forward(request, response);
            return;
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
            Logger.getLogger(AddFilmControl.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddFilmControl.class.getName()).log(Level.SEVERE, null, ex);
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
