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
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tienp
 */
@WebServlet(name = "UpdateFilmControl", urlPatterns = {"/updateFilm"})
public class UpdateFilmControl extends HttpServlet {

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
        int filmId = Integer.parseInt(request.getParameter("filmId"));
        System.out.println("chekc id:" + filmId);
        String filmName = request.getParameter("filmName");
        String filmDescription = request.getParameter("filmDescription");
        String posterUrl = request.getParameter("posterUrl");
        int filmDuration = Integer.parseInt(request.getParameter("filmDuration"));
        String[] genreIds = request.getParameterValues("genreIds");
        
        List<Genre> genreList1 = new ArrayList<>();
        for (String genreId : genreIds) {
            int id = Integer.parseInt(genreId);
            Genre genre = new Genre();
            genre.setId(id);
            genre.setName(filmdao.getNameGenre(id));
            genreList1.add(genre);
        }
        Film film = new Film(filmId, filmName, filmDescription, posterUrl, Duration.ofMinutes(filmDuration));
        film.setGenreList(genreList1);

        filmdao.updateFilm(film);
        filmdao.removeGenresFilm(filmId);
        for (Genre genre : genreList1) {
            filmdao.addGenresFilm(genre, filmId);
        }
        response.sendRedirect("main");

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
            Logger.getLogger(UpdateFilmControl.class.getName()).log(Level.SEVERE, null, ex);
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
