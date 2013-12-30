<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Coding Test: Teguh Wanrasagita</title>
	<link href="<c:url value="/resources/css/global.css" />" rel="stylesheet">
</head>

<body>
	<h1>Please enter valid US zip code (e.g. 94117)</h1>

	<form:form method="post" commandName="weather">
	
		<form:errors path="*" cssClass="errorblock" element="div"/>

		<table>
			<tr>
				<td><form:label path="zip">US Zip Code: </form:label></td>
				<td><form:input path="zip" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Find Weather" /></td>
			</tr>

		</table>

		<c:if test="${showResult}">
			<h2>Weather result:</h2>

			<table>
				<tr>
					<td>City:</td>
					<td>${result.city}</td>
				</tr>
				<tr>
					<td>State:</td>
					<td>${result.state}</td>
				</tr>
				<tr>
					<td>Temperature:</td>
					<td>${result.fahrenheitTemp} &deg;F</td>
				</tr>
			</table>
		</c:if>


	</form:form>

</body>
</html>