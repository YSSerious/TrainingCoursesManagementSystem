<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
<div class="col-sm-12">
	<div class="row">
		<div class="col-md-11" id="table">
			<c:if test="${!users.isEmpty()}">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title"><spring:message code="users.table"/></h3>
					</div>
					<table class="table table-striped table-bordered users"
						id="pagination">
						<tr>
							<th><spring:message code="users.user"/></th>
							<th><spring:message code="users.role"/></th>
						</tr>

						<c:forEach items="${users}" var="user">
							<tr>
								<td><font size="3"><b> <a
											href="/users/${user.id}"> ${user.firstName}
												${user.secondName} ${user.lastName} </a></b></font></td>
								<td><font size="3"> <c:forEach items="${user.roles}"
											var="role" varStatus="loop">
											<b>
											<c:if test="${role.id == 1}">
											 <spring:message code="users.admin"/>
											 </c:if>
											 <c:if test="${role.id == 2}">
											 <spring:message code="users.mentor"/>
											 </c:if>
											 <c:if test="${role.id == 3}">
											 <spring:message code="users.hr"/>
											 </c:if>
											 <c:if test="${role.id == 4}">
											 <spring:message code="users.student"/>
											 </c:if>
											 <c:if
													test="${!loop.last}">,</c:if>
											</b>

										</c:forEach>
								</font></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
			<c:if test="${users.isEmpty()}">
				<h1><spring:message code="users.no"/></h1>
			</c:if>
		</div>

		<div class="col-md-1" id="search">
			<button type="button" id="button" class="btn btn-primary pull-right "
				data-toggle="collapse" data-target="#filter-panel">
				<span class="glyphicon glyphicon-cog"></span>
			</button>
			<br /> <br />
			<div id="filter-panel" class="collapse filter-panel">
				<form action="" method="get" onsubmit="return validate();">


					<div class="form-group">
						<label class="filter-col" for="value"><spring:message code="users.searchBy"/>:</label> <br />
						<input type="radio" name="type" value="name"
							onclick="yesnoCheck();"
							<c:if test="${param.type == 'name' || param.type == null}">
  								checked
  							</c:if>>
						<spring:message code="users.name"/> <input type="radio" name="type" value="role" id="roles"
							onclick="yesnoCheck();"
							<c:if test="${param.type == 'role'}">
  								checked
  							</c:if>>
						<spring:message code="users.role"/> <input type="radio" name="type" value="project"
							onclick="yesnoCheck();"
							<c:if test="${param.type == 'project'}">
  								checked
  							</c:if>>
						<spring:message code="users.project"/> <input type="radio" name="type" value="group"
							onclick="yesnoCheck();"
							<c:if test="${param.type == 'group'}">
  								checked
  							</c:if>>
						<spring:message code="users.group"/> <input type="submit" value="Search!"
							class="btn btn-default filter-col pull-right"> <span
							id="rev-err" class="text-danger hidden">Unknown error</span>
					</div>
					<div class="form-group">
						<label class="filter-col" for="value"><spring:message code="users.searchFor"/>:</label> <input
							type="text" name="value" class="form-control input-sm"
							id="submitted"
							<c:if test="${param.value != null && !param.type.equals('role')}">
   								value="${param.value}"
   							</c:if>>

						<div id="check">
							<input type="checkbox" name="value" value="1"
								<c:if test="${param.value == '1' && param.type == 'role'}">
  									checked
 							    </c:if>>
							<spring:message code="users.admin"/> <br /> <input type="checkbox" name="value" value="3"
								<c:if test="${param.value == '3' && param.type == 'role'}">
 									checked
  								</c:if>>
							<spring:message code="users.hr"/> <br /> <input type="checkbox" name="value" value="2"
								<c:if test="${param.value == '2' && param.type == 'role'}">
  									checked
  								</c:if>>
							<spring:message code="users.mentor"/> <br /> <input type="checkbox" name="value"
								value="4"
								<c:if test="${param.value == '4' && param.type == 'role'}">
  									checked
  								</c:if>>
							<spring:message code="users.student"/> <br />
						</div>
					</div>
				</form>
			</div>


		</div>
		<br> <br>
	</div>
	<br />
	<div class="col-md-11">
		<div class="text-center">
			<div style="min-width: 100%;" id="bootstrap-pagination"></div>
		</div>
	</div>
</div>
<script>
var max = ${noOfPages};
var curr = ${currentPage};
	
	function getURLParam(key,target){
        var values = [];
        if(!target){
            target = location.href;
        }

        key = key.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");

        var pattern = key + '=([^&#]+)';
        var o_reg = new RegExp(pattern,'ig');
        while(true){
            var matches = o_reg.exec(target);
            if(matches && matches[1]){
                values.push(matches[1]);
            }
            else{
                break;
            }
        }

        if(!values.length){
             return null;   
         }
        else{
           return values.length == 1 ? values[0] : values;
        }

    }
	
var value = getURLParam("value");
var type = getURLParam("type");
if(max!=1){
$('#bootstrap-pagination').bootpag({
	total: max,
    page: curr,
    maxVisible: 5,
    leaps: true,
    firstLastUse: true,
    first: '←',
    last: '→',
    wrapClass: 'pagination',
    activeClass: 'active',
    disabledClass: 'disabled',
    nextClass: 'next',
    prevClass: 'prev',
    lastClass: 'last',
    firstClass: 'first'
}).on("page", function(event, num){

	if(value!=null && type!=null && value.length == 1)
		window.location.href = "?page="+num+"&value="+value+"&type="+type;
	else if (type == "role" && value.length == 2)
		window.location.href = "?page="+num+"&value="+value[0]+"&value="+value[1]+"&type="+type;
	else if (type == "role" && value.length == 3)
		window.location.href = "?page="+num+"&value="+value[0]+"&value="+value[1]+"&value="
				+value[2]+"&type="+type;
	else if (type == "role" && value.length == 4)
		window.location.href = "?page="+num+"&value="+value[0]+"&value="+value[1]+"&value="
				+value[2]+"&value="+value[3]+"&type="+type;
	else
		window.location.href = "?page="+num;
	
});
};
function yesnoCheck() {
	if (document.getElementById('roles').checked) {
		$('#check').removeClass('hidden');
		$('#submitted').addClass('hidden');
		document.getElementById('submitted').disabled = true;
		$('#rev-err').addClass('hidden');
		$('#check').children('input').each(function() {
			this.disabled = false;
		});
	} else {
		$('#check').addClass('hidden');
		$('#submitted').removeClass('hidden');
		document.getElementById('submitted').disabled = false;
		$('#check').children('input').each(function() {
			this.disabled = true;
		});
		document.getElementById('check').disabled = true;
	}
};

$( document ).ready(yesnoCheck()); 

var clicked = false;
document.getElementById('button').onclick = function() {
	if (!clicked) {
		$('#table').removeClass('col-md-11');
		$('#search').removeClass('col-md-1');
		$('#table').addClass('col-md-9');
		$('#search').addClass('col-md-3');
		clicked = true;
	} else {
		setTimeout(function() {
			$('#table').removeClass('col-md-9');
			$('#search').removeClass('col-md-3');
			$('#table').addClass('col-md-10');
			$('#search').addClass('col-md-1');
			$('#table').removeClass('col-md-10');
			$('#table').addClass('col-md-11');
			clicked = false;
		}, 250);
	}
};
</script>
<%@include file="footer.jsp"%>
