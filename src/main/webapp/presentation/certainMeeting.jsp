<%@include file="header.jsp"%>
This is meeting ${meeting.name}
<br>
In ${meeting.place}
<br>
At ${meeting.time}

<br>
<br>
<br>
<br>
<div id="table-listing">
	<div class="panel panel-primary  table-responsive table-scrollable" >
		<div class="panel-heading">
			<h3 class="panel-title">Student list</h3>
		</div>
		<table class="table table-striped table-bordered meeting"
			id="pagination" data-count-fixed-columns="1" cellpadding="0" cellspacing="0">
			<thead class="header">

				<tr>
					<th>Student</th>
					<c:forEach items="${criteria}" var="criterion">
						<th>${criterion.title}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody class="results">
			<c:forEach items="${students}" var="user">
				<tr>
					<td><font size="3"><b> <a href="/users/${user.id}">
									${user.firstName} ${user.secondName} ${user.lastName} </a></b></font></td>
					<c:forEach items="${criteria}" var="criterion">
						<td><font size="3"><center>
									<a href="#" data-toggle="tooltip" data-placement="top"
										title="No mark, yet">-</a>
								</center></font></td>
					</c:forEach>
				</tr>
			</c:forEach>
			<c:forEach items="${marks}" var="entry">
				<tr>
					<td><font size="3"><b> <a
								href="/users/${entry.key.id}">${entry.key.firstName}
									${entry.key.secondName} ${entry.key.lastName}</a></b></font></td>

					<c:forEach items="${entry.value}" var="mark">
						<td><font size="3"><center>
									<a href="#" data-toggle="tooltip" data-placement="top"
										title="${mark.commentary}">${mark.mark}</a>
								</center></font></td>
					</c:forEach>
					<c:if test="${entry.value.size() < criteria.size()}">
						<c:forEach items="${criteria}"
							begin="${entry.value.size()}">
							<td><font size="3"><center>
										<a href="#" data-toggle="tooltip" data-placement="top"
											title="No mark, yet">-</a>
									</center></font></td>
						</c:forEach>
					</c:if>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	</div>
<br>
<br>
<br>
<br>
<h2>Criteria List</h2>

<dl>
	<c:forEach items="${categories}" var="category">
		<dt>${category.name}</dt>
		<c:forEach items="${criteria}" var="criterion">
			<c:if test="${category.id == criterion.category.id}">
				<dd>&nbsp;&nbsp; -${criterion.title}</dd>
			</c:if>
		</c:forEach>
	</c:forEach>
</dl>

<script>
	function app_handle_listing_horisontal_scroll(listing_obj) {
		//get table object   
		table_obj = $('.table', listing_obj);

		//get count fixed collumns params
		count_fixed_collumns = table_obj.attr('data-count-fixed-columns')

		if (count_fixed_collumns > 0) {
			//get wrapper object
			wrapper_obj = $('.meeting', listing_obj);

			wrapper_left_margin = 0;

			table_collumns_width = new Array();
			table_collumns_margin = new Array();

			//calculate wrapper margin and fixed column width
			$('th', table_obj).each(function(index) {
				if (index < count_fixed_collumns) {
					wrapper_left_margin += $(this).outerWidth();
					table_collumns_width[index] = $(this).outerWidth();
				}
			})

			//calcualte margin for each column  
			$.each(table_collumns_width, function(key, value) {
				if (key == 0) {
					table_collumns_margin[key] = wrapper_left_margin;
				} else {
					next_margin = 0;
					$.each(table_collumns_width,
							function(key_next, value_next) {
								if (key_next < key) {
									next_margin += value_next;
								}
							});

					table_collumns_margin[key] = wrapper_left_margin
							- next_margin;
				}
			});

			//set wrapper margin               
			if (wrapper_left_margin > 0) {
				wrapper_obj.css('cssText', 'margin-left:' + wrapper_left_margin
						+ 'px !important; width: auto')
			}

			//set position for fixed columns
			$('tr', table_obj)
					.each(
							function() {

								//get current row height
								current_row_height = $(this).outerHeight();

								$('th,td', $(this))
										.each(
												function(index) {

													//set row height for all cells
													$(this).css('height',
															current_row_height)

													//set position 
													if (index < count_fixed_collumns) {
														$(this)
																.css(
																		'position',
																		'absolute')
																.css(
																		'margin-left',
																		'-'
																				+ table_collumns_margin[index]
																				+ 'px')
																.css(
																		'width',
																		table_collumns_width[index])

														$(this)
																.addClass(
																		'table-fixed-cell')
													}
												})
							})
		}
	}

	$(function() {
		app_handle_listing_horisontal_scroll($('#table-listing'))
	})
</script>

<%@include file="footer.jsp"%>