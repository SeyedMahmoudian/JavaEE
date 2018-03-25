<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Flight booking</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Optional theme -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>                       
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>

<script language="JavaScript" type="text/javascript" src="/js/jquery-1.2.6.min.js"></script>
<script language="JavaScript" type="text/javascript" src="/js/jquery-ui-personalized-1.5.2.packed.js"></script>
<script language="JavaScript" type="text/javascript" src="/js/sprinkle.js"></script>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<style>
#flight_table table {
	font-family: 'Open Sans Condensed', sans-serif;
	font-size: 21px;
	font-weight: 600;
	border-collapse: collapse;
	width: 100%;
	
}
#flight_table td, th {
	border: 1px solid #337ab7;
	text-align: left;
	padding: 8px;
}
#flight_table tr:nth-child(even) {
	background-color: #337ab7;
}
#datetimepicker1  td, th {
	border: 1px solid #ffffff;	
}
#datetimepicker2  td, th {
	border: 1px solid #ffffff;	
}
body {
	background:
		url("https://thetechjournal.com/wp-content/uploads/2014/10/Plane-Without-Having-Windows-At-Night.jpg")
		fixed;
	background-size: cover;
	font-family: 'Open Sans Condensed', sans-serif;
	font-size: 21px;
	font-weight: 600;
	position: absolute;
}
.container {
	font-color: white;
}
.panel-body {
	font-size: 19px;
}
.jumbotron {
	height: 150px;
	opacity: 0.8;
	padding-bottom: 1em;
}
.glyphicon {
	font-size: 15px;
	padding-right: 2em;
}
.nav-tabs>li.active>a, .nav-tabs>li.active>a:hover, .nav-tabs>li.active>a:focus
	{
	border: 0;
	background-color: #444;
	width: 100%;
}

.navbar-fixed-left {
	width: 250px;
	position: relative;
	border-radius: 0;
	height: 100%;
}

.navbar-fixed-left .navbar-nav>li {
	float: left; /* Cancel default li float: left */
}

.navbar-fixed-left+.container {
	padding-left: 0.5em;
	padding-right: 0.5em;
	height: 100%;
}

.glyphicon-remove-sign {
    color: red;
}
 .glyphicon-ok-sign : hover  {
    color:gray;
}
</style>
<script  type="text/javascript"> 
function seatClick(element) {
    if( element.className === "glyphicon glyphicon-ok-sign"){
    	$("#"+element.id).toggleClass("glyphicon glyphicon-question-sign");
    	document.getElementById("object").value = element.id;
    	document.getElementById("credit-card-button").click()
    }
    else if (element.className =="glyphicon glyphicon-remove-sign"){
    	alert(" This seat is taken")
    }else {
    	alert ( " Proceed to payment ? ")
    }				
} 

</script>
<script type="text/javascript"
	src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
<body>
<input id="object" name ="object" value="" hidden="hidden" />
	<div id="google_translate_element"></div>
	<div class="container-fluid">

		<div class="jumbotron">

			<h1>
				Welcome
				<%= session.getAttribute("firstname") %></h1>
		</div>
		<div class="col-md-3" style="height:100%">
			<div class="col-xs-1">
				<!-- required for floating -->
				<!-- Nav tabs -->
				<ul class="nav navbar-inverse navbar-fixed-left nav-tabs tabs-left">
					<li><a href="#client-panel" id="client-toggle"
						data-toggle="collapse"><span class="glyphicon glyphicon-user"></span>Profile</a></li>
					<li><a href="#payment-panel" id="payment-toggle"
						data-toggle="collapse"><span
							class="glyphicon glyphicon-credit-card"></span>Payment</a></li>
					<li><a href="#history-panel" id="history-toggle"
						data-toggle="collapse"><span
							class="glyphicon glyphicon-pushpin"></span>Booking History</a></li>
					<li><a href="#booking-panel" id="booker-toggle"
						data-toggle="collapse"><span class="glyphicon glyphicon-plane"></span>Flight
							Booker</a></li>
					<li>
					
						<form class="logout" action="LogoutServlet" method="post">
							<a><span
							class="glyphicon glyphicon-off" style="font-size:15px; padding-left: 15px; padding-right: 5px;"></span><input class="btn btn-primary" type="submit" value="Logout"
								class="btn btn-default"
								style="min-width: 100px; color: #337ab7; border: 1px; font-weight: 600; font-size: 21px; background: #222222">

						</a></form>
						
						
					</li>

				</ul>
			

			</div>

		</div>
		<!-- menu row -->
		<!-- end Left Side -->
		<div class="col-md-9" style="height=100%;">
			<div id="client-panel" class="collapse in">
				<div class="panel panel-info">
					<div class="panel-heading"></div>
					<div class="panel-body">

						<div class="row">
							<div class=".col-md-10" align="center">
								<img alt="User Pic"
									src="http://icons.iconarchive.com/icons/icons8/windows-8/128/Transport-Airplane-Takeoff-icon.png"
									class="img-circle img-responsive">
							</div>
							<table class="table table-user-information">
								<tbody>
									<tr>
										<td>First Name :</td>
										<td><%=session.getAttribute("firstname")%></td>
									</tr>
									<tr>
										<td>Last Name :</td>
										<td><%=session.getAttribute("lastname")%></td>
									</tr>
									<tr>
										<td>Apt Number:</td>
										<td><%=session.getAttribute("aptNumber")%></td>
									</tr>
									<tr>
										<td>Address</td>
										<td><%=session.getAttribute("streetNum")%> <%=session.getAttribute("streetName")%>
										</td>
									</tr>
									<tr>
										<td>City, Country</td>
										<td><%=session.getAttribute("city")%>, <%=session.getAttribute("country")%>
										</td>
									</tr>
									<tr>
										<td>Email</td>
										<td><%=session.getAttribute("userEmail")%></td>
									</tr>
									<tr>
										<td>Phone Number</td>
										<td><%=session.getAttribute("phoneNum")%><br></td>

									</tr>

								</tbody>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!--  client panel -->
		<div class="col-md-9">
			<div id="payment-panel" class="collapse">

				<div class="panel panel-info">

					<div class="panel-heading">
						<h3 class="panel-title">Payment Details</h3>
					</div>
					<div class="panel-body">
						<form role="form" class="creditCard" action="/CustomerServlet"
							method="doPost">
							<div class="form-group">
								<label for="cardNumber"> CardNumber</label>
								<div class="input-group">
									<input type="text" class="form-control" id="cardNumber"
										placeholder="Valid Card Number" required autofocus /> <span
										class="input-group-addon"><span
										class="glyphicon glyphicon-lock" style="padding: 0;"></span></span>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-4 col-md-4">
									<div class="form-group">
										<label for="expiry"> EXPIRY DATE</label> <input type="text"
											class="form-control" id="expiry" placeholder="MM/YYYY"
											required />
									</div>
								</div>
								<div class="col-xs-4 col-md-4">
									<div class="form-group">
										<label for="cvv">CV CODE</label> <input type="password"
											class="form-control" id="cvv" placeholder="CV" required />
									</div>
								</div>
							</div>
							<div class="col-md-8 text-center">
								<button type="button" id="payment_subscribe"
									class="btn btn-success" id="credit-card-button"
									data-toggle="modal" data-target="#paymentModal">Submit</button>

							</div>
						</form></div>
				</div>
			</div>
			<div>
				<!-- Modal -->
				<div class="modal fade" id="paymentModal" role="dialog">
					<div class="modal-dialog modal-sm">
						<div class="modal-content">

							<div class="modal-body">
								<p>Payment method updated</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--  payment panel -->

		<div class="col-md-9">

			<div id="history-panel" class="collapse">
				<div class="panel panel-info">
					<div class="panel-heading"></div>
					<div class="panel-body">

						<%= session.getAttribute("data")%>

					</div>
				</div>
			</div>
		</div>
		<!--  history panel -->
		<div class="col-md-9">
			<div id="booking-panel" class="collapse in">
				<div class="panel panel-info">

					<div class="panel-heading">
						<h3 class="panel-title">Flight Booker</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="form-group">
								<form class="flights" action="FlightBookingServlet"
									method="post">

									<div class="col-lg-2">
										<div class="btn-group">

											<!-- <button class="btn btn-primary btn-lg  dropdown-toggle"
												type="button" data-toggle="dropdown">
												FROM <span class="caret"></span>
											</button>
											-->
											<%=session.getAttribute("airport-departure-dropdown")%>

										</div>
									</div>
									<div class="col-lg-4">
										<div class='input-group date' id='datetimepicker1'
											style="border: 0px solid #ffffff;" data-date-format="yyyy-MM-dd hh:mm:ss" >
											<input  type='text'
												name="departure-date" class="form-control" /> <span
												class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
										<script>
											$(function() {
												$('#datetimepicker1')
														.datetimepicker(
																{
																	defaultDate : "04/1/2017",
																	daysOfWeekDisabled : [
																			0,
																			6 ], // block Weekends
																	disabledDates : [
																			moment("04/2/2017"), 
																			new Date(
																					2013,
																					11 - 1,
																					21),
																			"11/22/2013 00:53" ]
																});
											});
											var departureDate  = $("#datetimepicker1").find("input").val();
										</script>

									</div>
									<div class="col-lg-2">
										<div class="btn-group">
											<!--<button class="btn btn-primary btn-lg dropdown-toggle"
												type="button" data-toggle="dropdown">
												TO <span class="caret"></span>
											</button>
											-->
											<%=session.getAttribute("airport-arrival-dropdown")%>
										</div>
									</div>
									<div class="col-lg-4">
										<div class='input-group date' id='datetimepicker2'
											style="border: 0px solid #ffffff;" data-date-format="yyyy-MM-dd hh:mm:ss" >
											<input type='text'
												name="arrival-date" class="form-control" /> <span
												class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
										<script>
											$(function() {
												$('#datetimepicker2')
														.datetimepicker(
																{
																	defaultDate : "04/1/2017",
																	daysOfWeekDisabled : [
																			0,
																			6 ], // block Weekends
																	disabledDates : [
																			moment("04/2/2017"),
																			new Date(
																					2013,
																					11 - 1,
																					21),
																			"11/22/2013 00:53" ]
							
											});
											});
										</script>
									</div>
									<input type="submit" value="BOOK FLIGHT " id="flight-book"
										class="btn btn-success btn-lg" />

								</form>
							        
										
							</div>

						</div>
					</div>
				</div>

			</div>
		</div>


	</div>
	<!-- container -->

</body>
<script>
$(".form-group option").click(function(){
	  var selText = $(this).text();
	  $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
	});
</script>

<script type="text/javascript">
				function googleTranslateElementInit() {
					new google.translate.TranslateElement(
							{
								pageLanguage : 'en',
							}, 'google_translate_element');
				}
			</script>
<br>
</html>