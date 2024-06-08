<%@ page contentType="text/html;charset=UTF-8" language="java" %>}
<%@ page import="org.example.lab7.beans.Employees" %>
<%@ page import="org.example.lab7.beans.Jobs" %>
<%@ page import="org.example.lab7.beans.Departments" %>
<%@ page import="java.util.ArrayList" %>
<%ArrayList<Jobs> lista_jobs = (ArrayList<Jobs>) request.getAttribute("jobs");%>
<%ArrayList<Departments> lista_departamentos = (ArrayList<Departments>) request.getAttribute("departments");%>
<%ArrayList<Employees> lista_jefes = (ArrayList<Employees>) request.getAttribute("jefes");%>

<html>
<head>
    <title>Form Registro Empleado</title>
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
        <h1>Registro de Empleado</h1>
        <div class="buttons">
            <button  class="orange" onclick="Regresar()">Regresar</button>
        </div>
        <form action="<%=request.getContextPath()%>/home?action=agregarEmpleado" method="post">
            <input type="hidden" name="action" value="agregarEmpleado">
            <div class="inputs">
                <div class="form-group">
                    <label for="fullName">Nombre completo:</label>
                    <input type="text" id="fullName" name="fullName" placeholder="Nombre" required>
                </div>

                <div class="form-group">
                    <label for="email">Correo:</label>
                    <input type="text" id="email"  name="email" placeholder="Correo" required>
                </div>

                <div class="form-group">
                    <label for="phone">Número telefónico:</label>
                    <input type="text" id="phone" name="phone" placeholder="teléfono" required>
                </div>

                <div class="form-group">
                    <label for="hire_date">Fecha de contratación:</label>
                    <input type="date" id="hire_date" name="hire_date" placeholder="Fecha de contratación" required>
                </div>

                <div class="form-group">
                    <label for="job_id">Job:</label>
                    <select id="job_id" name="job_id" class = "form-control" required>
                        <%
                            for (Jobs job : lista_jobs) {
                        %>
                        <option value="<%= job.getJob_id() %>"><%= job.getJob_title() %></option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="salary">Salario:</label>
                    <input type="text" id="salary" name="salary" placeholder="Salario" >
                </div>

                <div class="form-group">
                    <label for="manager_id">Manager:</label>
                    <select id="manager_id" name="manager_id" class = "form-control" required>
                        <option value="null">--Sin Jefe--</option>
                        <%
                            for (Employees employee : lista_jefes) {
                        %>
                        <option value="<%= employee.getEmployee_id() %>"><%= employee.getFullNameEmployee() %></option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="department_id">Departamento:</label>
                    <select id="department_id" name="department_id" class = "form-control" required>
                        <%
                            for (Departments department : lista_departamentos) {
                        %>
                        <option value="<%= department.getDepartment_id() %>"><%= department.getDepartment_name() %></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
            <div class = "buttons">
                <button type="submit" class="green" style="width: 100%;">Registrar nuevo empleado</button>
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
