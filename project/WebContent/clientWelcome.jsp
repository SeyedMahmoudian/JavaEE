<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Flight booking</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


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

body {
	background:
		url("https://thetechjournal.com/wp-content/uploads/2014/10/Plane-Without-Having-Windows-At-Night.jpg")
		fixed;
	background-size: cover;
	font-family: 'Open Sans Condensed', sans-serif;
	font-size: 21px;
	font-weight: 600;
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
	float: none; /* Cancel default li float: left */
}

.navbar-fixed-left+.container {
	padding-left: 0.5em;
	padding-right: 0.5em;
}

</style>
<script type="text/javascript"
	src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
<body>
  
	<div id="google_translate_element"></div>
	<div class="container-fluid">
		<div class="jumbotron">
			<h1>
				Welcome
				<%=session.getAttribute("firstname")%></h1>
		</div>
		<div class="col-md-3">
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


				</ul>
				<form class="logout" action="LogoutServlet" method="post">


					<input class="btn btn-primary" type="submit" value="Logout"
						class="btn btn-default"
						style="min-width: 150px; color: #337ab7; border: 1px; font-weight: 600; font-size: 21px; background: #222222">

				</form>
			</div>
		</div>
		
		<!-- menu row -->
		<!-- end Left Side -->
		<div class="col-md-9">
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
						</form>
					</div>
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
			<div id="booking-panel" class="collapse">
				<div class="panel panel-info">
					<div class="panel-heading"></div>
					<div class="panel-body">
						<%= session.getAttribute("picker")%> 
					</div>
				</div>
			</div>
				</div>
		<!--  booking  panel -->
	</div>
	<!-- container -->
</body>
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