<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Flight booking</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Optional theme -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>

		
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700"
	rel="stylesheet">
<script src="js/jquery.simple.timer.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<style>
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
	width: 250px;
	display: block;
}

.li {
	width: 250px;
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

.glyphicon-ok-sign : hover {
	color: gray;
}
</style>


<script>
/*
$("#admin").click(function() {
	$("#success_add").click();
	return true;
});

$("#backToAdminWelcome").Onclick(function() {

	document.getElementByName("PlaneId").required = "required";
	document.getElementByName("flightDate").required = "required";
	document.getElementByName("flightDepartTime").required = "required";
	document.getElementByName("flightArrival").required = "required";
	document.getElementByName("flightDepartAirport").required = "required";
	document.getElementByName("flightSeatCost").required = "required";
});
*/
</script>

<script type="text/javascript"
	src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit">
</script>

<body>
	<input id="object" name="object" value="" hidden="hidden" />
	<div id="google_translate_element"></div>
	<div class="container">

		<div class="jumbotron">
			<h1>
				Welcome Admin</h1>
		</div>
		<div class="col-md-3" style="height: 100%">
			<div class="col-xs-1">
				<ul class="nav navbar-inverse navbar-fixed-left nav-tabs tabs-left">
					<li><a href="#addflight-panel" id="client-toggle"
						data-toggle="collapse"><span class="glyphicon glyphicon-plus"></span>Add Flight</a></li>
					<li><a href="#findByEmail-panel" id="payment-toggle"
						data-toggle="collapse"><span
							class="	glyphicon glyphicon-search"></span>Find Client by Email</a></li>
					<li>
						<form class="logout" action="LogoutServlet" method="post">
							<a><span class="glyphicon glyphicon-off"
								style="font-size: 15px; padding-left: 15px; padding-right: 5px;"></span><input
								class="btn btn-primary" type="submit" value="Logout"
								class="btn btn-default"
								style="min-width: 100px; color: #337ab7; border: 1px; font-weight: 600; font-size: 21px; background: #222222">

							</a>
						</form>
					</li>
				</ul>
			</div>

		</div>
		<!-- section 1 -->
			<div class="col-md-9" style="">
			<div id="addflight-panel" class="collapse in">
				<div class="panel panel-info">
					<div class="panel-heading">Add a Flight</div>
					<div class="panel-body">

						<div class="row">
							<div class=".col-md-10" align="center">
								<img alt="User Pic"
									src="http://icons.iconarchive.com/icons/icons8/windows-8/128/Transport-Airplane-Takeoff-icon.png"
									class="img-circle img-responsive">
							</div>
							<form class="admin" action="AdminServlet" method="post">
								<div class="form-group form-inline">
									<div class="row" style="padding-top: 3em;">
										<div class="col-md-4" style="margin-left: 2em;">
											<label class="form-inline" for="flightDate">Departure
												Date </label>
											<div class='input-group date' id='datetimepicker3'
												style="border: 0px solid #ffffff;"
												data-date-format="yyyy-MM-dd">

												<input type='text' name="flightDate"
													class="form-control" /> <span class="input-group-addon">
													<span class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
											<script>
										
											$(function() {
												$('#datetimepicker3')
														.datetimepicker(
																{
																	format : 'YYYY-MM-DD',
																	defaultDate : "2017-05-01",
																	daysOfWeekDisabled : [], // 0, 6 to block Weekends
																	disabledDates : [
																	/* moment("04/2/2017"), 
																	new Date(
																			2013,
																			11 - 1,
																			21),
																	"11/22/2013 00:53"*/]
																});
											});
											
											var departureDate = $(
													"#datetimepicker3").find(
													"input").val();
											console.log(departureDate);
										</script>

										</div>
										<div class="col-md-4">
											<div class="col-md-2"></div>
											<label class="form-inline" for="flightTime">Departure
												Time</label>
											<div class='input-group date' id='datetimepicker4'
												style="border: 0px solid #ffffff;"
												data-date-format="hh:mm:ss">

												<input type='text' name="flightDepartureTime"
													class="form-control" /> <span class="input-group-addon"><i
													class="glyphicon glyphicon-time"></i></span>
											</div>

											<script type="text/javascript">
										
										$(function() {
											$('#datetimepicker4')
													.datetimepicker(
															{
																 format: 'hh:mm:ss'
															});
										});
										

										var departureTime = $(
												"#datetimepicker4").find(
												"input").val();
										console.log(departureTime);
										</script>
										</div>
									</div>
									<br>
									<br>
									<div class="row">

										<div class="col-md-3" style="margin-left: 2em;">
											<label class="form-inline" for="flightDuration">Duration
											</label> <input type="number" min=50 placeholder="minutes "
												name="flightDuration" />
										</div>
										<div class="col-md-3" style="margin-left: 2em;">
											<label class="form-inline" for="flightArrivalAirport">Arrival
												Airport </label> <input type="text" 
												name="flightArrivalAirport" />
										</div>
									</div>
									<br>
									<div class="row">
										<div class="col-md-3" style="margin-left: 2em;">
											<label class="form-inline" for="flightDepartAirport">Departure
												Airport </label> 
											<input type="text" 
												name="flightDepartAirport" />
										</div>
										<div class="col-md-3" style="margin-left: 2em;">
										<label class="form-inline" for="flightSeatCost">Flight Cost </label> 
											<input type="number" placeholder="CAD"
												name="flightSeatCost" />
										</div>
									</div>
									<br>
									<div class="row">
									  <div style="margin-left: 4em;">
										<input type="submit" value="Add the flight to Database"
											 class="btn btn-success btn-lg" />
											</div>
									</div>
								</div>
							


						</div>
					</div>
				</div>
			<div>

					<table class="table table-user-information" style="background-color:white; color:black;">
						<tbody>
							<tr>
								<td>Departure Airport :</td>
								<%
								session.getAttribute("flightDepartAirport");
								%>
							</tr>
							<tr>
								<td>Departure Date :</td>
								<td>
									<%
									session.getAttribute("flightDate");
									%>
								</td>
							</tr>
							<tr>
								<td>Departure Time :</td>
								<td>
									<%
									request.getSession().getAttribute("flightDepart");
									%>
								</td>
							</tr>
							<tr>
								<td>Flight Duration:</td>
								<td>
									<%
									request.getSession().getAttribute("flightDuration");
									%>
								</td>
							</tr>
							<tr>
								<td>Arrival Airport :</td>
								<td>
								<%
								request.getSession().getAttribute("flightArrival");
								%>
								</td>
							</tr>

							<tr>
								<td>Price :</td>
								<td>
									<%
									request.getSession().getAttribute("flightSeatCost");
									%>
								</td>
							</tr>

						</tbody>
					</table>
</form>





				</div>
			</div>
		</div>
		
	  <!-- section 2 -->	
      
	</div>
	<!-- container -->
</body>
</html>