package org.example.lab7.daos;
import org.example.lab7.beans.Employees;
import org.example.lab7.beans.Jobs;
import java.sql.*;
import java.util.ArrayList;

public class DaoJobs extends BaseDao{
    public ArrayList<Jobs> listarEmpleos() {
        ArrayList<Jobs> listaEmpleos = new ArrayList<>();

        String query = "SELECT * " +
                "FROM jobs ";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Jobs job = new Jobs();
                job.setJob_id(rs.getString("job_id"));
                job.setJob_title(rs.getString("job_title"));
                job.setMin_salary(rs.getInt("min_salary"));
                job.setMax_salary(rs.getInt("max_salary"));
                listaEmpleos.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaEmpleos;
    }
    public void agregarEmpleo(Jobs job){


        String query = "INSERT INTO jobs (job_title, min_salary, max_salary) VALUES (?, ?, ?)";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, job.getJob_title());
            pstmt.setInt(2, job.getMin_salary());
            pstmt.setInt(3, job.getMax_salary());

            pstmt.executeUpdate();
        }catch( SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Jobs obtenerJobPorId(int job_id){
        Jobs job = new Jobs();

        String query = "SELECT * " +
                "FROM jobs " +
                "WHERE job_id = ? ";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, job_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                job.setJob_id(rs.getString("job_id"));
                job.setJob_title(rs.getString("job_title"));
                job.setMin_salary(rs.getInt("min_salary"));
                job.setMax_salary(rs.getInt("max_salary"));

            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return job;
    }
}
