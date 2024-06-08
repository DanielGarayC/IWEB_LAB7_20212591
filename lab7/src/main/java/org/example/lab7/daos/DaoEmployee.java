package org.example.lab7.daos;
import org.example.lab7.beans.Employees;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
public class DaoEmployee extends BaseDao{

    public ArrayList<Employees> listarEmpleados() {
        ArrayList<Employees> listaEmpleados = new ArrayList<>();


        String query = "SELECT e.employee_id, CONCAT(e.first_name, ' ', e.last_name) AS 'fullNameEmployee', e.email, " +
                "e.phone_number, e.salary, e.hire_date, " +
                "(SELECT d.department_name FROM departments d WHERE d.department_id = e.department_id) AS 'departament', " +
                "(SELECT j.job_title FROM jobs j WHERE j.job_id = e.job_id) AS 'job', " +
                "(SELECT CONCAT(m.first_name, ' ', m.last_name) FROM employees m WHERE m.employee_id = e.manager_id) AS 'manager' " +
                "FROM employees e ";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employees employee = new Employees();

                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setFullNameEmployee(rs.getString("fullNameEmployee"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone_number(rs.getString("phone_number"));
                employee.setHire_date(rs.getDate("hire_date"));
                employee.setJob(rs.getString("job"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setDepartment(rs.getString("departament"));
                employee.setManager(rs.getString("manager"));

                listaEmpleados.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaEmpleados;
    }
    public void agregarEmpleado(Employees employee, String jobId, Integer managerId, Integer departmentId){


        String query = "INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary, manager_id, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            String fullName = employee.getFullNameEmployee();

            String[] names = fullName.split(" ");

            String first_name = names[0].trim();
            String last_name = names.length > 1 ? names[1].trim() : "";

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhone_number());
            if (employee.getHire_date() != null) {
                pstmt.setDate(5, new java.sql.Date(employee.getHire_date().getTime()));
            } else {
                pstmt.setNull(5, Types.DATE);
            }
            pstmt.setString(6, jobId);
            pstmt.setBigDecimal(7, employee.getSalary());
            if (managerId != null) {
                pstmt.setInt(8, managerId);
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            pstmt.setInt(9, departmentId);
            pstmt.executeUpdate();
        }catch( SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void actualizarEmpleado(Employees employee, String jobId, Integer managerId, Integer departmentId){
        String query = "UPDATE employees " +
                "SET first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, " +
                "job_id = ?, salary = ?, manager_id = ?, department_id = ? " +
                "WHERE employee_id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            String fullName = employee.getFullNameEmployee();
            String[] names = fullName.split(" ");
            String first_name = names[0].trim();
            String last_name = names.length > 1 ? names[1].trim() : "";

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhone_number());
            pstmt.setDate(5,new java.sql.Date(employee.getHire_date().getTime()));
            pstmt.setString(6, jobId);
            pstmt.setBigDecimal(7, employee.getSalary());
            if (managerId != -1) {
                pstmt.setInt(8, managerId);
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            pstmt.setInt(9, departmentId);
            pstmt.setInt(10, employee.getEmployee_id());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEmpleado(int employee_id) {

        try (Connection conn = this.getConnection()) {
            //Para este caso particular de eliminar empleados, al existir la posibilidad de que algunos empleados
            //sean manager, debemos "quitarlos" de ese cargo antes de eliminarlos de la tabla empleados.
            //Es por eso que primero se actualiza todos los empleados que hayan tenido de manager al empleado que
            //será eliminado, mostrarán el valor de "Sin Jefe".
            String updEmployees = "UPDATE employees SET manager_id = NULL WHERE manager_id = ?";
            try (PreparedStatement pstmtUpdEmployees = conn.prepareStatement(updEmployees);) {
                pstmtUpdEmployees.setInt(1, employee_id);
                pstmtUpdEmployees.executeUpdate();
            }
            // Ahora, quitamos el valor de manager para el departamento al cual pertenecía
            String updDepartments = "UPDATE departments SET manager_id = NULL WHERE manager_id = ?";
            try (PreparedStatement pstmtUpdDepartments = conn.prepareStatement(updDepartments);) {
                pstmtUpdDepartments.setInt(1, employee_id);
                pstmtUpdDepartments.executeUpdate();
            }
            String delJobH = "DELETE FROM job_history WHERE employee_id = ?";
            try (PreparedStatement pstmtDelEmployee = conn.prepareStatement(delJobH)) {
                pstmtDelEmployee.setInt(1, employee_id);
                pstmtDelEmployee.executeUpdate();

            }
            //Por último, ya podremos eliminarlo sin problemas :D
            String delEmployee = "DELETE FROM employees WHERE employee_id = ?";
            try (PreparedStatement pstmtDelEmployee = conn.prepareStatement(delEmployee)) {
                pstmtDelEmployee.setInt(1, employee_id);
                pstmtDelEmployee.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Employees obtenerEmployeeporId(int employee_id){
        Employees employee = new Employees();

        String query = "SELECT e.employee_id, CONCAT(e.first_name, ' ', e.last_name) AS 'fullNameEmployee', e.email, " +
                "e.phone_number, e.salary, e.hire_date, " +
                "(SELECT d.department_name FROM departments d WHERE d.department_id = e.department_id) AS 'department', " +
                "(SELECT j.job_title FROM jobs j WHERE j.job_id = e.job_id) AS 'job', " +
                "(SELECT CONCAT(m.first_name, ' ', m.last_name) FROM employees m WHERE m.employee_id = e.manager_id) AS 'manager' " +
                "FROM employees e " +
                "WHERE e.employee_id = ?" ;

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, employee_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setFullNameEmployee(rs.getString("fullNameEmployee"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone_number(rs.getString("phone_number"));
                employee.setHire_date(rs.getDate("hire_date"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setJob(rs.getString("job"));
                employee.setManager(rs.getString("manager"));
                employee.setDepartment(rs.getString("department"));

            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return employee;
    }
}

