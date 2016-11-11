<%@include file="header.jsp" %>

<div class="container">
      <!-- Example row of columns -->
      <div class="row">
        <div class="col-md-12">
          <div class="page-header">
            <h3>
              ${project.name}
            </h3>
            <div class="row">
              <div class="col-md-6">
            <div class="panel panel-default panel-horizontal top-panel">
				<div class="panel-heading">
				<h3 class="panel-title">Start date</h3>
    			</div>
				<div class="panel-body">${project.startDate}</div>
    		</div>
    		<div class="panel panel-default panel-horizontal bottom-panel">
				<div class="panel-heading">
				<h3 class="panel-title">Finish date</h3>
    			</div>
				<div class="panel-body">${project.finishDate}</div>
    		</div>
              </div>
              <div class="col-md-6">
                <h4 class="desc-label" style="margin-top: 0px;"> Description:</h4>
                <p>${project.description}</p>
              </div>
              </div>
            </div>
            
            <h2>
            Groups
            </h2>
          <div class="panel panel-btn"> 
	        <a href="" class="btn btn-default btn-xs pull-right-btn">Remove</a>
	        <a href="" class="btn btn-default btn-xs pull-right-btn">Edit</a>
            <a href="" class="btn btn-default btn-xs pull-right-btn"> Add</a>
          </div>
          <ul class="list-group">
            <li class="list-group-item">
              Group_1
            </li>
            <li class="list-group-item">
              Group_1
            </li>
            <li class="list-group-item">
              Group_1
            </li>
          </ul>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <h2>
            Criteria List
          </h2>
          <div class="panel panel-btn"> 
	        
	        <a href="" class="btn btn-default btn-xs pull-right-btn">Remove</a>
	        <a href="" class="btn btn-default btn-xs pull-right-btn">Edit</a>
            <a href="" class="btn btn-default btn-xs pull-right-btn">Add</a>
          </div>
          <ul class="list-group">
            <li class="list-group-item">
              Criterium_1
            </li>
            <li class="list-group-item">
              Criterium_1
            </li>
            <li class="list-group-item">
              Criterium_1
            </li>
          </ul>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <h2>
            Attachments
          </h2>
          <div class="panel panel-btn"> 
	        <a href="#" class="btn btn-default btn-xs pull-right-btn">Remove</a>
	        <a href="#" class="btn btn-default btn-xs pull-right-btn">Edit</a>
            <a href="#" class="btn btn-default btn-xs pull-right-btn">Add</a>
          </div>
          <ul class="list-group">
            <li class="list-group-item">
              Attachment_1
            </li>
            <li class="list-group-item">
              Attachment_1
            </li>
            <li class="list-group-item">
              Attachment_1
            </li>
          </ul>
        </div>
      </div>
      <hr>
</div>

<%@include file="footer.jsp"%>