/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Session;

/**
 *
 * @author tienp
 */
public class NewClass {
        public static void main(String[] args) {
        FilmDAO filmDAO = new FilmDAO();
        SessionDAO sdao = new SessionDAO();
        RoomDAO rdao = new RoomDAO();

        try {
            // Kiểm tra getNameGenre
            System.out.println("Testing session:");
            Session s = new Session();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
