package org.example.lab7.servlets;
import org.example.lab7.beans.Employees;
import org.example.lab7.beans.Jobs;
import org.example.lab7.beans.Departments;
import org.example.lab7.daos.DaoDeparments;
import org.example.lab7.daos.DaoEmployee;
import org.example.lab7.daos.DaoJobs;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "EmployeeServlet", value = "/home")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null? "ListaEmpleados" : request.getParameter("action");
        String vista;
        RequestDispatcher rd;
        switch(action) {
            case "ListaEmpleados":
                DaoEmployee daoEmployee = new DaoEmployee();
                ArrayList<Employees> listaEmpleados = daoEmployee.listarEmpleados();
                request.setAttribute("listaEmpleados",listaEmpleados);
                vista = "vistas/listaEmpleados.jsp";
                rd = request.getRequestDispatcher(vista);
                rd.forward(request, response);
                break;
            case "agregarEmpleado":
                DaoEmployee daoEmployeeA = new DaoEmployee();
                DaoJobs daoJobA = new DaoJobs();
                DaoDeparments daoDepartmentA = new DaoDeparments();

                ArrayList<Jobs> listaTrabajos = daoJobA.listarEmpleos();
                request.setAttribute("jobs", listaTrabajos);

                ArrayList<Departments> listaDepartamentos = daoDepartmentA.listarDepartamentos();
                request.setAttribute("departments", listaDepartamentos);

                ArrayList<Employees> listaJefes = daoEmployeeA.listarEmpleados();
                request.setAttribute("jefes", listaJefes);

                vista = "vistas/registroEmpleado.jsp";
                rd = request.getRequestDispatcher(vista);
                rd.forward(request, response);
                break;
            case "editarEmpleado":
                int idEmployeeEdit = Integer.parseInt(request.getParameter("idEditar"));

                DaoEmployee daoEmployee2 = new DaoEmployee();
                DaoJobs daoJobU = new DaoJobs();
                DaoDeparments daoDepartmentU = new DaoDeparments();

                Employees employeeEdit = daoEmployee2.obtenerEmployeeporId(idEmployeeEdit);
                request.setAttribute("employeeE", employeeEdit);

                ArrayList<Jobs> listaTrabajosU = daoJobU.listarEmpleos();
                request.setAttribute("jobs", listaTrabajosU);

                ArrayList<Departments> listaDepartamentosU = daoDepartmentU.listarDepartamentos();
                request.setAttribute("departments", listaDepartamentosU);

                ArrayList<Employees> listaJefesU = daoEmployee2.listarEmpleados();
                request.setAttribute("jefes", listaJefesU);

                vista = "vistas/editarEmpleado.jsp";
                rd = request.getRequestDispatcher(vista);
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if(action.equals("agregarEmpleado")) {
            String fullName = request.getParameter("fullName");
            String correo = request.getParameter("email");
            String telefono = request.getParameter("phone");
            String fContratacion = request.getParameter("hire_date");
            String job = request.getParameter("job_id");
            String salario = request.getParameter("salary");
            String manager = request.getParameter("manager_id");
            String departamento = request.getParameter("department_id");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date hire_date = null;
            try {
                hire_date = formatter.parse(fContratacion);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            BigDecimal salarioD = null;

            if (salario != null && !salario.isEmpty() ) {
                salarioD = new BigDecimal(salario);
            }

            Integer managerId = null;;
            if ("null".equals(manager)) {
                managerId = null;
            } else if (manager != null && !manager.isEmpty()) {
                managerId = Integer.parseInt(manager);
            } else {
            }

            int departmentId = Integer.parseInt(departamento);

            Employees nuevoEmpleado = new Employees();
            nuevoEmpleado.setFullNameEmployee(fullName);
            nuevoEmpleado.setEmail(correo);
            nuevoEmpleado.setPhone_number(telefono);
            nuevoEmpleado.setSalary(salarioD);
            nuevoEmpleado.setHire_date(hire_date);

            DaoEmployee employeeDaoAdd = new DaoEmployee();
            employeeDaoAdd.agregarEmpleado(nuevoEmpleado,job,managerId,departmentId);

            response.sendRedirect(request.getContextPath() + "/home?action=ListaEmpleados");

        }else if(action.equals("actualizarEmpleado")){

            DaoEmployee daoEmployeeU = new DaoEmployee();
            int employee_id = Integer.parseInt(request.getParameter("idEmployee"));
            Employees employeeU = daoEmployeeU.obtenerEmployeeporId(employee_id);

            String fullName = request.getParameter("fullName");
            String correo = request.getParameter("email");
            String telefono = request.getParameter("phone");
            String fContratacion = request.getParameter("hire_date");
            String job = request.getParameter("job_id");
            String salario = request.getParameter("salary");
            String manager = request.getParameter("manager_id");
            String department = request.getParameter("department_id");

            employeeU.setFullNameEmployee(fullName);

            employeeU.setEmail(correo);

            employeeU.setPhone_number(telefono);


            if (fContratacion != null && !fContratacion.isEmpty()) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date hire_date = formatter.parse(fContratacion);
                    employeeU.setHire_date(hire_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            BigDecimal salarioDU = null;
            if (salario != null && !salario.isEmpty()) {
                try {
                    salarioDU = new BigDecimal(salario);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            employeeU.setSalary(salarioDU);

            Integer managerId = -1;
            if (!manager.equals("-1")) {
                managerId = Integer.parseInt(manager);
            }
            int departmentId = Integer.parseInt(department);

            daoEmployeeU.actualizarEmpleado(employeeU,job,managerId,departmentId);
            response.sendRedirect(request.getContextPath() + "/home");

        }else if(action.equals("eliminarEmpleado")) {

            int idEmployee = Integer.parseInt(request.getParameter("idDelete"));

            DaoEmployee daoEmployeeD = new DaoEmployee();

            daoEmployeeD.eliminarEmpleado(idEmployee);

            response.sendRedirect(request.getContextPath() + "/home?action=ListaEmpleados");
        }else{
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}
