package dao;

import context.DBContext;
import entities.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author tienp
 */
public class UserDAO extends DAO {

    public User login(String email, String pass) {
        String query = "select * from users\n"
                + "where email = ?\n"
                + "and password = ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("check1");
                return new User(rs.getInt(1),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString("phone_number"),
                        User.Role.getUserRoleFromString(getRoleName(rs.getInt("role_id"))),
                        rs.getBoolean("notification"));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String checkUserexist(String email) {
        String query = "select email\n"
                + "from users\n"
                + "where email = ?\n";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public String getRoleName(int roleId) {
        if (roleId == 1) {
            return "ADMIN";
        } else if (roleId == 2) {
            return "USER";
        }
        return "GUEST";
    }

    public int getRoleId(String roleName) {
        if (roleName.equals("ADMIN")) {
            return 1;
        } else if (roleName.equals("USER")) {
            return 2;
        }
        return 0;
    }

    public void singUp(User u) {
        String query = "INSERT INTO dbo.users (first_name, second_name, email, password, phone_number, notification) VALUES\n"
                + "(?, ?, ?, ?, ?, ?);";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getPhoneNumber());
            ps.setBoolean(6, u.getNotification());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
