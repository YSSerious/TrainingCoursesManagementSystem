<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Nastasia
  Date: 05.11.2016
  Time: 11:49
--%>


  <div class="modal fade" id="createGroupModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-body" style="padding:40px 50px;">
          <form role="form">
            <div> <!-- class="form-group" -->
              <label for="groupname"> <spring:message code="group.name"/></label>
              <input type="text" class="form-control" id="groupname" placeholder="Enter group name">
            </div>
            <button type="submit" class="btn btn-default btn-success pull-right" id="submitButton"><spring:message code="btn.submit"/></button>
            <div class="alert alert-danger">
  				<strong>This name already exists!</strong> Please, change it.
			</div>
          </form>
        </div>
        <div class="modal-footer">

          <button type="button" class="btn btn-default btn-lg" id="cancelButton"><span class="glyphicon glyphicon-remove"></span><spring:message code="btn.cancel"/></button>
        </div>
      </div>
      
    </div>
  </div>



