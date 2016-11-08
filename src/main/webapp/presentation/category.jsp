<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 07.11.2016
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/presentation/resources/js/category.js"></script>
</head>
<body>
    Name: <input type="text"  id="nameId"/> <br/>
    Age :<input type="text" id="ageId"/> <br/>
    <button class="btn btn-sm btn-success" type="submit" id="submitId">Submit</button>

    <table id="categoryTable" class="table table-condensed table-hover col-md-8">
        <thead class="table-head">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
        </tbody>
    </table>
</body>
</html>
