<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Multi Module Project</title>
	</head>
	<body>
		<h1>So, ${user.firstName} ${user.lastName}</h1>
		<div>
		    <span>It seems that your age is ${userAgeMessage}</span>
		</div>
		<a href="/build-maven-gradle">Back</a>
	</body>
</html>