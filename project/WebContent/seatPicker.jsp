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
<script src="https://js.stripe.com/v3/"></script>

<link
	href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seat Selection</title>
</head>
<style>
* {
  font-family: "Helvetica Neue", Helvetica, sans-serif;
  font-size: 15px;
  font-variant: normal;
  padding: 0;
  margin: 0;
}

#modal-body {
    max-height: 300px !important;
}
html {
  height: 100%;
}

body {
  background: #F6F9FC;
  display: flex;
  align-items: center;
  min-height: 100%;
}

form {
  width: 480px;
  margin: 20px auto;
}

label {
  position: relative;
  color: #6A7C94;
  font-weight: 400;
  height: 48px;
  line-height: 48px;
  margin-bottom: 10px;
  display: block;
}

label > span {
  float: left;
}

.field {
  background: white;
  box-sizing: border-box;
  font-weight: 400;
  border: 1px solid #CFD7DF;
  border-radius: 24px;
  color: #32315E;
  outline: none;
  height: 48px;
  line-height: 48px;
  padding: 0 20px;
  cursor: text;
  width: 76%;
  float: right;
}

.field::-webkit-input-placeholder { color: #CFD7DF; }
.field::-moz-placeholder { color: #CFD7DF; }
.field:-ms-input-placeholder { color: #CFD7DF; }

.field:focus,
.field.StripeElement--focus {
  border-color: #F99A52;
}

button {
  float: left;
  display: block;
  background-image: linear-gradient(-180deg, #F8B563 0%, #F99A52 100%);
  box-shadow: 0 1px 2px 0 rgba(0,0,0,0.10), inset 0 -1px 0 0 #E57C45;
  color: white;
  border-radius: 24px;
  border: 0;
  margin-top: 20px;
  font-size: 17px;
  font-weight: 500;
  width: 100%;
  height: 48px;
  line-height: 48px;
  outline: none;
}

button:focus {
  background: #EF8C41;
}

button:active {
  background: #E17422;
}

.outcome {
  float: left;
  width: 100%;
  padding-top: 8px;
  min-height: 20px;
  text-align: center;
}

.success, .error {
  display: none;
  font-size: 13px;
}

.ticketWrap {
 display: none;
 float :right;
}

.ticketWrap.visible {
 display: inline;
 color: black;

}

.success.visible, .error.visible {
  display: inline;
}

.error {
  color: #E4584C;
}

.success {
  color: #F8B563;
}

.success .token {
  font-weight: 500;
  font-size: 13px;
}

.ticketWrap {
  width: 27em;
  margin: 3em auto;
  color: #F8B563;;
  font-family: sans-serif;
}

.ticket {
  background: linear-gradient(to bottom, #000000 0%, #000000 26%, #ecedef 26%, #ecedef 100%);
  height: 600px;
  width: 500px;
  float: left;
  position: relative;
  padding: 1em;
  margin-top: 100px;
}
.glyphicon-remove-sign {
    color: red;
}

.ticketLeft {
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
  width: 16em;
}

.ticketRight {
  width: 10.5em;
  height : 600px;
  border-left: .18em dashed #fff;
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
}

.ticketRight:before, .ticketRight:after {
  content: "";
  /*position: absolute;*/
  display: block;
  width: .9em;
  height: .9em;
  background: #fff;
  border-radius: 50%;
  left: -.5em;
}
.ticketRight:before {
  top: -.4em;
}
.ticketRight:after {
  bottom: -.4em;
}

h1 , h3 {
  font-size: 15px;
  margin-top: 0;
}
h1 span {
  font-weight: normal;
}

.title, .name, .seat, .time {
  text-transform: uppercase;
  font-weight: normal;
}
.title h2, .name h2, .seat h2, .time h2 {
  font-size: 15px;
  color: black	;
  margin: 0;
}
.title span, .name span, .seat span, .time span {
  font-size: 15px;
  color: #a2aeae;
}

.title {
  margin: 2em 0 0 0;
}

.name, .seat {
  margin: .7em 0 0 0;
}

.time {
  margin: .7em 0 0 1em;
}

.seat, .time , .depature, .arrival {
  float: left;
}


.number {
  text-align: center;
  text-transform: uppercase;
}
.number h3 {
  color: #e84c3d;
  margin: .9em 0 0 0;
  font-size: 15px;
}
.number span {
  display: block;
  color: #a2aeae;
}

.barcode {
  height: 2em;
  width: 0;
  margin: 1.2em 0 0 .8em;
  box-shadow: 1px 0 0 1px #343434, 5px 0 0 1px #343434, 10px 0 0 1px #343434, 11px 0 0 1px #343434, 15px 0 0 1px #343434, 18px 0 0 1px #343434, 22px 0 0 1px #343434, 23px 0 0 1px #343434, 26px 0 0 1px #343434, 30px 0 0 1px #343434, 35px 0 0 1px #343434, 37px 0 0 1px #343434, 41px 0 0 1px #343434, 44px 0 0 1px #343434, 47px 0 0 1px #343434, 51px 0 0 1px #343434, 56px 0 0 1px #343434, 59px 0 0 1px #343434, 64px 0 0 1px #343434, 68px 0 0 1px #343434, 72px 0 0 1px #343434, 74px 0 0 1px #343434, 77px 0 0 1px #343434, 81px 0 0 1px #343434;
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
.glyphicon {
	font-size: 15px;
	padding-right: 2em;
}
</style>
<script  type="text/javascript"> 
function seatClick(element) {
    if( element.className === "glyphicon glyphicon-ok-sign"){
    	$("#"+element.id).toggleClass("glyphicon glyphicon-question-sign");
    	//document.getElementById("object").value = element.id;
    	document.getElementById("credit-card-button").click();
    }
    else if (element.className =="glyphicon glyphicon-remove-sign"){
    	alert(" This seat is taken")
    }else {
    	alert ( " Proceed to payment ? ")
    }				
} 
</script>
<body>
	<div class="container">
	<div style="float left">
		<div>
        <%= session.getAttribute("picker")%> 
    	 </div>
		<div style =" height:100px;"> </div> 
		<div class="col-md-8 text-center"  style = "margin-left:300px;" hidden="hidden" >
			<button type="button"  class="btn btn-success btn-lg"
				id="credit-card-button" data-toggle = "modal"
				data-target="#paymentModal" hidden="hidden"></button>
				</div>
				<div style="margin-left: 430px"> 
				 <a class="btn btn-success btn-lg" href="http://localhost:8081/Flight_Booking/userWelcome.jsp">      BACK TO CHANGE BOOKING     </a>				
				</div> 
				</div>
		<div class="col-md-8 text-center">
			<!-- Modal -->
			<div class="modal fade" id="paymentModal" role="dialog">
				<div class="modal-dialog modal-lg">
					<div class="modal-content" style="height: 450px !important;">
						<div class="modal-body">
							<form>
								<label> <input name="cardholder-name"
									class="field is-empty" placeholder="Name on Card" /> <span><span>Name</span></span>
								</label> <label> <input class="field is-empty" type="tel"
									placeholder="(123) 456-7890" /> <span><span>Phone</span></span>
								</label> <label>
									<div id="card-element" class="field is-empty"></div>
									<span>Card</span>
								</label>
								<button type="submit" onClick="document.getElementById('close').click()">
									Pay $
									<%=session.getAttribute("flightSeatPrice")%></button>
								<div class="outcome">
									<div class="error">Invalid Payment</div>
									<div class="success">
										Success! Your Stripe token is <span class="token"></span>
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal" id="close">Close</button>
						</div>
					</div>


				</div>
			</div></div>

	
			<div class="col-md-8 pull-right">
				
					<div class="ticketWrap">
				
					<div class="ticket ticketLeft">
					
						<h1>
							Traveler Airlines
						</h1>
						<div class="name">
						<span>name</span>
							<h2><%=session.getAttribute("firstname")%>
								<%=session.getAttribute("lastname")%></h2>
							
						</div>
						<div class="seat">
						<span>seat</span>
							<h2><%=session.getAttribute("seat")%></h2>
							
						</div>

						<div class="departure">
						<h1>
							<br><br><br><br>
						</h1>
						<span>Depature Date</span>
							<h2><%=session.getAttribute("departureAirport")%></h2>
							<span>Departure Airport</span>
							<h2><%=session.getAttribute("depDate")%></h2>
							
						</div>
						<div class="arrival">
						<span>Return Date</span>
							<h2><%=session.getAttribute("departureAirport")%></h2>
							<span>Arrival Airport</span>
							<h2><%=session.getAttribute("arrDate")%></h2>
							
						</div>

					</div>
					<div class="ticket ticketRight">
						<span class="glyphicon glyphicon-plane"></span>
						<div class="number">
						<span>seat</span>
							<h3><%=session.getAttribute("seat")%></h3>
							
						</div>
						<div class="barcode"></div>
						</div>
					
				</div>
			</div>
		</div>

</body>
<script>
	var stripe = Stripe('pk_test_AsLR1Q9ZrLjPeghznz85XIDy');
	var elements = stripe.elements();

	var card = elements.create('card', {
		hidePostalCode : true,
		iconStyle : 'solid',
		style : {
			base : {

				iconColor : '#8898AA',
				color : 'black',
				lineHeight : '36px',
				fontWeight : 300,
				fontFamily : '"Helvetica Neue", Helvetica, sans-serif',
				fontSize : '19px',

				'::placeholder' : {
					color : '#8898AA',
				},
			},

			invalid : {
				iconColor : '#e85746',
				color : '#e85746',
			}
		},
		classes : {

			focus : 'is-focused',
			empty : 'is-empty',
		},
	});
	card.mount('#card-element');

	var inputs = document.querySelectorAll('input.field');
	Array.prototype.forEach.call(inputs, function(input) {
		input.addEventListener('focus', function() {
			input.classList.add('is-focused');
		});
		input.addEventListener('blur', function() {
			input.classList.remove('is-focused');
		});
		input.addEventListener('keyup', function() {
			if (input.value.length === 0) {
				input.classList.add('is-empty');
			} else {
				input.classList.remove('is-empty');
			}
		});
	});

	function setOutcome(result) {
		var successElement = document.querySelector('.success');
		var errorElement = document.querySelector('.error');
		var ticket = document.querySelector('.ticketWrap');
		
		ticket.classList.remove('visible');
		successElement.classList.remove('visible');
		
		errorElement.classList.remove('visible');

		if (result.token) {
			// Use the token to create a charge or a customer
			// https://stripe.com/docs/charges
			successElement.querySelector('.token').textContent = result.token.id;
			successElement.classList.add('visible');
			ticket.classList.add('visible');
			
		} else if (result.error) {
			errorElement.textContent = result.error.message;
			errorElement.classList.add('visible');
		}
	}

	card.on('change', function(event) {
		setOutcome(event);
	});

	document.querySelector('form').addEventListener('submit', function(e) {
		e.preventDefault();
		var form = document.querySelector('form');
		/*var extraDetails = {
			name : form.querySelector('input[name=cardholder-name]').value,
		};*/
		stripe.createToken(card).then(setOutcome);
	});
</script>