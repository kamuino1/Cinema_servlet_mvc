/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.SessionControl;

import dao.FilmDAO;
import dao.RoomDAO;
import dao.SeatDAO;
import dao.SessionDAO;
import dao.TicketDAO;
import dao.UserDAO;
import entities.EmailModel;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

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
        TicketDAO ticketdao = new TicketDAO();
        UserDAO userdao = new UserDAO();

        int sessionid = Integer.parseInt(request.getParameter("asession"));
        int filmid = Integer.parseInt((String) request.getParameter("filmId"));
        int roomid = Integer.parseInt((String) request.getParameter("roomId"));
        LocalDate sdate = LocalDate.parse(request.getParameter("date"));
        LocalTime stime = LocalTime.parse(request.getParameter("time"));
        BigDecimal tiketPrice = new BigDecimal(request.getParameter("ticketPrice"));
        
        Session beforSession = sessionDao.getSessionById(sessionid);
        int roomidBefor = beforSession.getRoom().getId();
        beforSession.setRoom(rdao.getRoomById(roomidBefor));

        Session updateSession = new Session(sessionid, tiketPrice, sdate, stime, fdao.getFilmById(filmid), beforSession.getSeatsAmount());
        updateSession.setRoom(rdao.getRoomById(roomid));
        
        if (sessionDao.checkSession(updateSession)) {
            sessionDao.updateSession(updateSession);
            if (roomid != roomidBefor) {
                List<Seat> oldSeats = seatdao.getAllFreeSeats(sessionid);
                List<Seat> seatsByRoom = seatdao.getAllSeatsByRoomId(roomid);
                List<Seat> newSeats = new ArrayList<>();

                for (Seat s : seatsByRoom) {
                    Optional<Seat> rs = oldSeats.stream()
                            .filter(seat -> seat.getPlaceNumber() == s.getPlaceNumber()
                            && seat.getRowNumber() == s.getRowNumber())
                            .findFirst();

                    if (rs.isPresent()) {
                        newSeats.add(s);
                    }
                }
                seatdao.deleteFreeSeatsBySession(sessionid);
                for (Seat seat : newSeats) {
                    seatdao.addFreeSeats(sessionid, seat.getId());
                }
            }
            
            List<Ticket> tickets = ticketdao.getAllTicketBySessionId(updateSession.getId());
            if(tickets != null){
                for(Ticket t : tickets){
                    t.setSeat(seatdao.getSeatById(t.getSeat().getId()));
                    User u = userdao.getUserById(t.getUser().getId());
                    
                    String recipient = u.getEmail();
                    String subject = "Thông báo cập nhật lịch chiếu";
                    String content = "Xin chào " + u.getFirstName() + " " + u.getLastName() + "/n" 
                                        +"Chúng tôi xin thống báo: /n"
                                        +"Lịch chiếu của bạn: " 
                                        + "Phòng " + beforSession.getRoom().getRoomName()
                                        + ", ghế số " + t.getSeat().getPlaceNumber()
                                        + ", hàng "  + t.getSeat().getRowNumber()
                                        + ", phim " + beforSession.getFilm().getName()
                                        + ", ngày giờ: " + beforSession.getDate() + " " + beforSession.getTime()
                                        + "đã được đổi sang lịch chiếu mới./n "
                                        + "Thông tin lịch chiếu mới: "
                                        + "Phòng " + updateSession.getRoom().getRoomName()
                                        + ", ghế số " + t.getSeat().getPlaceNumber()
                                        + ", hàng "  + t.getSeat().getRowNumber()
                                        + ", phim " + updateSession.getFilm().getName()
                                        + ", ngày giờ: " + updateSession.getDate() + " " + updateSession.getTime()
                                        + "/n"
                                        + "Nếu bạn không đồng ý với lịch chiếu được thay đổi vui lòng liên hệ lại với rạp chiếu phim qua email này./n/n"
                                        + "Xin cảm ơn!";
                    EmailModel emailModel = new EmailModel(recipient,subject,content);
                    emailModel.saveEmailToFile();

                    // Cấu hình JavaMail
                    Properties properties = new Properties();
                    properties.put("mail.smtp.host", emailModel.getHost());
                    properties.put("mail.smtp.port", emailModel.getPort());
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");

                    // Xác thực tài khoản email
                    javax.mail.Session s = javax.mail.Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(emailModel.getEmail(), emailModel.getPassword());
                        }
                    });

                    try {
                        // Tạo email
                        Message message = new MimeMessage(s);
                        message.setFrom(new InternetAddress(emailModel.getEmail()));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                        message.setSubject(subject);
                        message.setText(content);

                        // Gửi email
                        Transport.send(message);

                        

                        response.getWriter().println("Email đã gửi và lưu thành công!");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        response.getWriter().println("Gửi email thất bại: " + e.getMessage());
                    }
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