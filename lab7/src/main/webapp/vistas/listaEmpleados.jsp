<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ page import="org.example.lab7.beans.Employees" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%ArrayList<Employees> listaEmpleados = (ArrayList<Employees>) request.getAttribute("listaEmpleados");%>
<%SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Empleados</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            border-spacing: 0;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .btn-container {
            text-align: right;
            margin-bottom: 20px;
        }
        .btn {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .icon {
            font-size: 18px;
            margin-right: 5px;
        }
    </style>
</head>
<body>
<h1>Lista de empleados</h1>
<div class="btn-container">
    <a href="<%=request.getContextPath()%>/home?action=agregarEmpleado" class="btn">Agregar Empleado</a>
</div>
<% if (listaEmpleados == null || listaEmpleados.isEmpty()) { %>
<p>No se registró ningún empleado</p>
<% } else { %>
<table>
    <thead>
    <tr>
        <th>Nombre completo</th>
        <th>Correo</th>
        <th>Número telefónico</th>
        <th>Fecha de contratación</th>
        <th>Trabajo</th>
        <th>Salario</th>
        <th>Manager</th>
        <th>Departamento</th>
        <th>Editar/Borrar</th>
    </tr>
    </thead>
    <tbody>
    <% for (Employees empleado : listaEmpleados) { %>
    <tr>
        <td><%=empleado.getFullNameEmployee()%></td>
        <td><%=empleado.getEmail()%></td>
        <td><%=empleado.getPhone_number()%></td>
        <td><%=sdf.format(empleado.getHire_date())%></td>
        <td><%=empleado.getJob()%></td>
        <td><%=empleado.getSalary()%></td>
        <td><%= empleado.getManager() != null ? empleado.getManager() : "Sin Jefe" %></td>
        <td><%=empleado.getDepartment()%></td>
        <td>
            <a href="<%=request.getContextPath()%>/home?action=editarEmpleado&idEditar=<%=empleado.getEmployee_id()%>" title="Editar" class="icon-link">
                <i class="fas fa-pencil-alt icon"></i>
            </a>
            <form id="eliminarEmpleado_<%=empleado.getEmployee_id()%>" action="<%=request.getContextPath()%>/home" method="post" style="display: inline;">
                <input type="hidden" name="action" value="eliminarEmpleado">
                <input type="hidden" name="idDelete" value="<%= empleado.getEmployee_id() %>">
                <a href="#" onclick="document.getElementById('eliminarEmpleado_<%=empleado.getEmployee_id()%>').submit();" class="icon-link" title="Borrar" style="background: none; border: none;">
                    <i class="fas fa-trash-alt icon"></i>
                </a>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<% } %>
</body>
</html>