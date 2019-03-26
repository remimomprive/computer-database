<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Please sign in</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous" />
</head>
<body>
	<div class="container">
		<form class="form-signin" method="post" action="/computer-database/register">
			<h2 class="form-signin-heading">Please sign out</h2>
			<p>
				<label for="username" class="sr-only"><spring:message code="username.message" /></label> <input type="text" id="username" name="username" class="form-control"
					placeholder="<spring:message code="username.message" />" required autofocus>
			</p>
			<p>
				<label for="password" class="sr-only"><spring:message code="password.message" /></label> <input type="password" id="password" name="password" class="form-control"
					placeholder="<spring:message code="password.message" />" required>
			</p>
			<p>
				<label for="password" class="sr-only"><spring:message code="password_repeat.message" /></label> <input type="password" id="passwordRepeat" name="password_repeat" class="form-control"
					placeholder="<spring:message code="password_repeat.message" />" required>
			</p>
			<button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="register.message" /></button>
		</form>
		<a href="/computer-database/login">Login</a>
	</div>
</body>
</html>