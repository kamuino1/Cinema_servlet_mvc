/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Film;
import entities.Session;
import java.sql.Time;

/**
 *
 * @author tienp
 */
public class NewClass {
        public static void main(String[] args) {
        FilmDAO filmDAO = new FilmDAO();
        SessionDAO sdao = new SessionDAO();

        try {
            // Kiểm tra getNameGenre
            Session s = sdao.getSessionById(59);
            Film f = s.getFilm();
            System.out.println("Testing sessionID:");
            System.out.println(s.getDate());
            System.out.println(Time.valueOf(s.getTime()));
            int id = sdao.getIdSession(30, 3, s.getDate(), s.getTime());
            System.out.println(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
