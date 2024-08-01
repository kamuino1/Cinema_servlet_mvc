package dao;

import context.DBContext;
import java.util.ArrayList;
import java.util.List;
import entities.Film;
import entities.Genre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author tienp
 */
public class FilmDAO extends DAO {

    public String getNameGenre(int genre_id) {
        String query = "SELECT genre_name FROM genres WHERE genre_id = ?";
        try {
            Connection conn1 = new DBContext().getConnection();
            PreparedStatement ps1 = conn1.prepareStatement(query);
            ps1.setInt(1, genre_id);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {  // Thêm kiểm tra rs.next()
                return rs1.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Genre> getGenresFilm(int film_id) throws Exception {
        List<Genre> list = new ArrayList<>();
        String query = "SELECT genre_id FROM films_genres WHERE film_id = ?";
        try {
            Connection conn2 = new DBContext().getConnection();
            PreparedStatement ps2 = conn2.prepareStatement(query);
            ps2.setInt(1, film_id);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                int genre_id = rs2.getInt(1);
                list.add(new Genre(genre_id, getNameGenre(genre_id)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    public List<Film> getAllFilm() throws Exception {
        List<Film> list = new ArrayList<>();
        String query = "SELECT * FROM films ORDER BY film_id DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int film_id = rs.getInt(1);
                list.add(new Film(film_id,
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        Duration.ofMinutes(rs.getInt(5)),
                        getGenresFilm(film_id)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public int getTotalPage() throws Exception {
        String query = "select count(*) from films";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public List<Genre> getAllGenres() throws Exception {
        List<Genre> list = new ArrayList<>();
        String query = "SELECT * FROM genres ";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {  // Thêm kiểm tra rs.next()
                list.add(new Genre(rs.getInt(1), rs.getString(2)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public void addFilm(Film film) {
        String query1 = "  INSERT INTO films (film_name, [description], poster_url, duration) \n"
                + "  VALUES (?, ?, ?, ?); ";
        String query2 = "INSERT INTO films_genres VALUES(?,?)";
        try {
            conn = new DBContext().getConnection();

            ps = conn.prepareStatement(query1);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setString(3, film.getPosterUrl());
            ps.setLong(4, film.getDurationInMinutes());
            ps.executeUpdate();
            ps.close();

            
            Film f = getFilmByName(film);
            for (Genre genre : film.getGenreList()) {
                Connection conn1 = new DBContext().getConnection();
                PreparedStatement ps1 = conn1.prepareStatement(query2);
                ps1.setInt(1, f.getId());
                ps1.setInt(2, genre.getId());
                ps1.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Film getFilmByName(Film film) throws Exception {
        String query = "SELECT * FROM films WHERE film_name = ? AND poster_url = ?";
        try {
            Connection conn2 = new DBContext().getConnection();
            PreparedStatement ps2 = conn2.prepareStatement(query);
            ps2.setString(1, film.getName());
            ps2.setString(2, film.getPosterUrl());
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                int film_id = rs2.getInt(1);
                return new Film(film_id,
                        rs2.getString(2),
                        rs2.getString(3),
                        rs2.getString(4),
                        Duration.ofMinutes(rs2.getInt(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteFilm(int filmId) {
        String query1 = "  DELETE FROM films WHERE film_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query1);
            ps.setInt(1, filmId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Film getFilmById(int filmid) throws Exception {
        String query = "SELECT * FROM films WHERE film_id = ?";
        try {
            Connection conn2 = new DBContext().getConnection();
            PreparedStatement ps2 = conn2.prepareStatement(query);
            ps2.setInt(1, filmid);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                return new Film(filmid,
                        rs2.getString(2),
                        rs2.getString(3),
                        rs2.getString(4),
                        Duration.ofMinutes(rs2.getInt(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
