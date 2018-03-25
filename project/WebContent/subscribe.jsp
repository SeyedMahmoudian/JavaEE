
<html>
<head>
<title>Flight booking</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="javascript/login.js"></script>

<script>
	$("#subscribe").click(function(){
	    $("#success_subscribe").click(); 
	    return true; 
	});
	
	$("#backToLogin").Onclick(function(){
		
		document.getElementByName("FirstName").required = "required";
		document.getElementByName("LastName").required = false;
		document.getElementByName("customeremail").required = false;
		document.getElementByName("customerpass").required = false;
		document.getElementByName("confirmpassword").required = false;
		document.getElementByName("AptNum").required = false;
		document.getElementByName("phoneNumber").required = false;
		document.getElementByName("StreetNum").required = false;
		document.getElementByName("StreetName").required = false;
		document.getElementByName("city").required = false;
		document.getElementByName("country").required = false;
		document.getElementByName("postalcode").required = false;
		
	});

	</script>
<link rel="stylesheet" href="css/style.css" type="text/css"
	media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Application</title>
</head>
<body>
	<form class="login" action="SubscribeServlet" method="post">
		<input type="text" placeholder="Firstname" name="FirstName"/> 
			 <input type="text" placeholder="LastName"
			name="LastName"   /> <input type="text"
			placeholder="customer Email" name="customeremail" />
		<input type="password" placeholder="Password" name="customerpass"
			/> <input type="password"
			placeholder="Confirm Password" name="confirmpassword"
			 /> <input type="text" placeholder="Apt Num"
			name="AptNum" /> <input type="text"
			placeholder="Phone Number" name="phoneNumber"  />
		<input type="text" placeholder="Street Num" name="StreetNum"
			 /> <input type="text" placeholder="Street Name"
			name="StreetName" /> <input type="text"
			placeholder="City" name="city" /> <input
			type="text" placeholder="Country" name="country" />
		<input type="text" placeholder="postal Code" name="postalcode"
			 />


		

		<input type="submit" value="SUBSCRIBE NOW " 
			id="subscribe" class="btn btn-success btn-sm"   />
			
			<input type="submit" value="BACK TO LOGIN"   id ="backToLogin"class="btn btn-success btn-sm"  onclick="form.action='index.jsp';"/>
			
	</form>
</body>

</html>