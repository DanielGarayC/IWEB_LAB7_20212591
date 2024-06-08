package org.example.lab7.daos;
import org.example.lab7.beans.Departments;
import java.sql.*;
import java.util.ArrayList;

public class DaoDeparments extends BaseDao{

    public ArrayList<Departments> listarDepartamentos() {
        ArrayList<Departments> listaDepartamentos = new ArrayList<>();

        String query = "SELECT * " +
                "FROM departments ";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Departments department = new Departments();
                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                department.setManager_id(rs.getInt("manager_id"));
                department.setLocation_id(rs.getInt("location_id"));
                listaDepartamentos.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaDepartamentos;
    }

}
