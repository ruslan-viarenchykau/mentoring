<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Multi Module Project</title>
		<style>
            input {width: 170px;}
        </style>
	</head>
	<body>
		<h1>${welcomeMessage}</h1>
		<form url="/build-maven-gradle" method="post">
		    <table>
		        <tr>
		            <td><label for="first-name">First Name</label></td>
		            <td><input name="firstName" id="first-name" type="text"/></td>
		        </tr>
		        <tr>
		            <td><label for="last-name">Last Name</label></td>
		            <td><input name="lastName" id="last-name" type="text"/></td>
		        </tr>
		        <tr>
		            <td><label for="birthday">Birthday</label></td>
		            <td><input name="birthday" id="birthday" type="date"/></td>
		        </tr>
		    </table>
			<div><input type="submit"/></div>
		</form>
	</body>
</html>