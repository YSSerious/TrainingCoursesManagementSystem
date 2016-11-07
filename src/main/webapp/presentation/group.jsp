<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Nastasia
  Date: 05.11.2016
  Time: 11:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
  .modal-header, h4, .close {
      background-color: #5cb85c;
      color:white !important;
      text-align: center;
      font-size: 30px;
  }
  .modal-footer {
      background-color: #f9f9f9;
  }
  </style>
<script src="<c:url value="/presentation/resources/js/create_group.js"/>"></script>
</head>
<body>

<div class="container">

  <button type="button" class="btn btn-default btn-lg" id="createGroupButton">Create group</button>

  <div class="modal fade" id="createGroupModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-body" style="padding:40px 50px;">
          <form role="form">
            <div class="form-group">
              <label for="groupname"> Group name: </label>
              <input type="text" class="form-control" id="groupname" placeholder="Enter group name">
            </div>
            <button type="submit" class="btn btn-default btn-success pull-center" id="submitButton"><span class="glyphicon glyphicon-off"></span>Submit</button>
          </form>
        </div>
        <div class="modal-footer">

          <button type="button" class="btn btn-default btn-lg" id="cancelButton"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
        </div>
      </div>
      
    </div>
  </div>
</div>
</body>
</html>
