<%@ page contentType="text/html;charset=UTF-8" language="java" %>}
<%@ page import="org.example.lab7.beans.Employees" %>
<%@ page import="org.example.lab7.beans.Jobs" %>
<%@ page import="org.example.lab7.beans.Departments" %>
<%@ page import="java.util.ArrayList" %>
<%Employees empleado = (Employees) request.getAttribute("employeeE");%>
<%ArrayList<Jobs> lista_jobs = (ArrayList<Jobs>) request.getAttribute("jobs");%>
<%ArrayList<Departments> lista_departamentos = (ArrayList<Departments>) request.getAttribute("departments");%>
<%ArrayList<Employees> lista_jefes = (ArrayList<Employees>) request.getAttribute("jefes");%>

<html>
<head>
    <title>Form Editar Empleado</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        input[type="tel"],
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .form-group select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            background-image: url('data:image/svg+xml;utf8,<svg fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 8 12"><path d="M1 4h6L4 8 1 4z"/></svg>');
            background-repeat: no-repeat;
            background-position-x: calc(100% - 12px);
            background-position-y: center;
            background-size: 8px;
        }

        .buttons {
            text-align: center;
        }

        .green {
            background-color: #00913f;
            border: none;
            color: #fff;
            font-weight: bold;
            border-radius: 5px;
            padding: 12px 24px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 50%;
            margin: 0 auto;
            display: block;
        }

        .green:hover {
            background-color: #007b38;
        }
        .green{
            background-color: #00913f;
        }
        .orange{
            background-color: #FFA500;
            border: none;
            color: #fff;
            font-weight: bold;
            border-radius: 5px;
            padding: 12px 24px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-left: 10px;
        }

    </style>
</head>
<body>
<div class="container">
    <h1>Editar Empleado</h1>
    <div class="buttons">
        <button  class="orange" onclick="Regresar()">Regresar</button>
    </div>
    <form action="<%=request.getContextPath()%>/home?action=actualizarEmpleado" method="post">
        <input type="hidden" name="action" value="actualizarEmpleado">
        <input type="hidden" name="idEmployee"  value="<%= empleado.getEmployee_id() %>">
        <div class="inputs">
            <label for="nombre">Nombre completo:</label>
            <input type="text" id="nombre" name="fullName" value="<%= empleado.getFullNameEmployee() %>" required>

            <label for="correo">Correo:</label>
            <input type="text" id="correo" name="email" value="<%= empleado.getEmail() %>" required>

            <label for="phone">Teléfono:</label>
            <input type="text" id="phone" name="phone" value="<%= empleado.getPhone_number() %>" required>

            <label for="hire_date">Fecha de contratación:</label>
            <input type="date" id="hire_date" name="hire_date" value="<%= empleado.getHire_date() %>" required>

            <div class="form-group">
                <label for="job_id">Job:</label>
                <select id="job_id" name="job_id" class = "form-control" required>
                    <%
                        for (Jobs job : lista_jobs) {
                    %>
                    <option value="<%= job.getJob_id() %>"<%= empleado.getJob().equals(job.getJob_title()) ? "selected": "" %>><%=job.getJob_title()%></option>
                    <%
                        }
                    %>
                </select>
            </div>

            <label for="salary">Salario:</label>
            <input type="text" id="salary" name="salary" value="<%= empleado.getSalary() %>" >


            <div class="form-group">
                <label for="manager_id">Manager:</label>
                <select id="manager_id" name="manager_id" class="form-control" required>
                    <% if (empleado.getManager() == null) { %>
                    <option value="-1" selected>--Sin Jefe--</option>
                    <% } else { %>
                    <option value="-1">--Sin Jefe--</option>
                    <% } %>
                    <% for (Employees manager : lista_jefes) { %>
                    <% if (empleado.getManager() != null && empleado.getManager().equals(manager.getFullNameEmployee())) { %>
                    <option value="<%= manager.getEmployee_id() %>" selected><%= manager.getFullNameEmployee() %></option>
                    <% } else { %>
                    <option value="<%= manager.getEmployee_id() %>"><%= manager.getFullNameEmployee() %></option>
                    <% } %>
                    <% } %>
                </select>
            </div>

            <div class="form-group">
                <label for="department_id">Departamento:</label>
                <select id="department_id" name="department_id" class = "form-control" required>
                    <%
                        for (Departments department : lista_departamentos) {
                    %>
                    <option value="<%= department.getDepartment_id() %>" <%= empleado.getDepartment().equals(department.getDepartment_name()) ? "selected" : "" %>><%=department.getDepartment_name()%></option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>
        <div class = "buttons">
            <button type="submit" class = "green" style="width: 100%;">Actualizar Empleado</button>
        </div>

    </form>
</div>
<script>
    function Regresar() {
        window.location.href = "<%=request.getContextPath()%>/home";
    }
</script>
</body>
</html>
