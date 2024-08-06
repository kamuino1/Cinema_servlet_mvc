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
            Session s = new Session();
            System.out.println(s.getId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
