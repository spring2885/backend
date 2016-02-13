<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" crossorigin="anonymous">
<title>Spring2885 API Server</title>
<style>
/* Must do this so the navbar isn't over our content.
  See http://getbootstrap.com/components/#navbar-fixed-top/
  */
body { padding-top: 70px; }
</style>
</head>

<body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">Spring2885 API Server</a>
        </div>
      </div>
    </nav>
    
    <div class="container">
	    <div class="starter-template">
		<h1>Welcome to the <c:out value="${name}"/></h1>
        <p class="lead">This server is only for service APIs. <br/>
            It is not intended to be accessed from a browser... <br />
            These are not the driods you are looking for... yada yada..<br>
            <br/>
		</div>
   		<p class="lead">Since you are here check out our 
   		<a href="/api-docs/index.html">API Documentation</a>
   		</p>
		<img src="/img/face.png"><br/>
	</div>
    <footer class="footer">
      <div class="container">
        <p class="text-muted">Why are you still here? Go away already!</p>
      </div>
    </footer>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha256-KXn5puMvxCw+dAYznun+drMdG1IFl3agK0p/pqT9KAo= sha512-2e8qq0ETcfWRI4HJBzQiA3UoyFk6tbNyG+qSaIBZLyW9Xf3sWZHN/lxe9fTh1U45DpPf07yj94KsUHHWe4Yk1A==" crossorigin="anonymous"></script>
</body>
</html>