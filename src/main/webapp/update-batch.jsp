<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Update Batch View</title>
</head>
<body>
	<h2>Update Batch View</h2>
	<%-- Display a success message if available --%>
	<% if (request.getAttribute("successMessage") != null) { %>
	<p style="color:green;"><%= request.getAttribute("successMessage") %></p>
	<% } %>
	<%-- Your HTML content here, displaying participant data, etc. --%>
	<p><strong>Batch Name:</strong> <%= request.getAttribute("batch_Name") %></p>
	<p><strong>TimeOfDay:</strong> <%= request.getAttribute("TimeOfDay") %></p>
	
	<br>
	<a href="index.html">Go back to Home</a>
</body>
</html>