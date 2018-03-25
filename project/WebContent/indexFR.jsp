<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
 <title>Flight booking </title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Login Application</title>
</head>
<div class="languages" >
<a href="index.jsp"> English </a>
 </div> 
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="pr-wrap">
                <div class="pass-reset">
                    <label>
                        s'il vous plaît entrez votre nom d'utilisateur </label>
                    <input type="email" placeholder="Email" />
                    <input type="submit" value="Submit" class="pass-reset-submit btn btn-success btn-sm" />
                </div>
            </div>
            <div class="wrap">
                <p class="form-title">
                    Ouvrir une session </p>
                <form name =" login" class="login" action="loginServlet" method="post" >
                <div>
                 <input  id ="uname" type="text" placeholder="Identifiant"  type="text" name="username">
                <input type="password" placeholder="Mot de Passe"  name="password" />
                <input id = " submit" type="submit" name="sign_in" value="Connexion" class="btn btn-success btn-sm"  />
                </div>
             
              
                <input type="submit" value="S'inscrire" name="subscribe" class="btn btn-success btn-sm"  onclick="form.action='subscribeFR.jsp'; form.name='subscribe';"/>
                  </form>
                <div class="remember-forgot">
                    <div class="row">
                        <div class="col-md-6">

                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
    <div class="posted-by">" L'expérience sans fin ...  " <a href="http://www.jquery2dotnet.com"></a></div>
</div>


</html>
