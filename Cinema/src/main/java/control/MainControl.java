

package control;

import dao.FilmDAO;
import dao.RoomDAO;
import dao.SessionDAO;
import entities.Film;
import entities.Genre;
import entities.Room;
import entities.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "MainControl", urlPatterns = {"/main"})
public class MainControl extends HttpServlet {

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
//        SessionDAO sdao = new SessionDAO();
//        RoomDAO rdao = new RoomDAO();
        List<Film> list = dao.getAllFilm();
        for(Film film : list){
            List<Genre> listGenreFilm = dao.getGenresFilm(film.getId());
            film.setGenreList(listGenreFilm);
        }
        List<Genre> listGenre = dao.getAllGenres();
        int cnt = dao.getTotalPage()/3;
        if(dao.getTotalPage() % 3 != 0) cnt++;
//        List<Session> sessionList = sdao.getAllSession("all", "dateTime", "asc");
//        for (Session s : sessionList) {
//            Film f = s.getFilm();
//            Room room = s.getRoom();
//            int roomId = room.getId();
//            
//            f.setGenreList(dao.getGenresFilm(f.getId()));
//            
//            s.setFilm(f);
//            s.setRoom(rdao.getRoomById(roomId));
//        }
//        List<Room> roomList = rdao.getAllRoom();
        
        HttpSession session = request.getSession();
        session.setAttribute("genreList", listGenre);
        session.setAttribute("totalPages", cnt);
        session.setAttribute("filmList", list);
//        session.setAttribute("sessionList", sessionList);
//        session.setAttribute("roomList", roomList);
        
        request.setAttribute("prev", "Previous");
        request.setAttribute("next", "Next");
        
        request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
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
            Logger.getLogger(MainControl.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MainControl.class.getName()).log(Level.SEVERE, null, ex);
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
