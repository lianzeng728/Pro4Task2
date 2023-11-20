<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Servlet Testing</title>
</head>
<body>

<h1>Servlet Testing</h1>

<!-- User Registration Form -->
<h2>User Registration</h2>
<form action="user/register" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"><br><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password"><br><br>
    <input type="submit" value="Register">
</form>

<!-- User Login Form -->
<h2>User Login</h2>
<form action="user/login" method="post">
    <label for="loginUsername">Username:</label>
    <input type="text" id="loginUsername" name="username"><br><br>
    <label for="loginPassword">Password:</label>
    <input type="password" id="loginPassword" name="password"><br><br>
    <input type="submit" value="Login">
</form>

<!-- Player Search Form -->
<h2>Search Players</h2>
<form action="players" method="get">
    <label for="searchTerm">Player Name:</label>
    <input type="text" id="searchTerm" name="searchTerm"><br><br>
    <input type="submit" value="Search">
</form>

</body>
</html>
