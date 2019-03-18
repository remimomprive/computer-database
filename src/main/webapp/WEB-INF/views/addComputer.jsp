<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<!-- Bootstrap -->
	<link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form:form action="${pageContext.request.contextPath}/addComputer" method="POST" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name">Computer name</form:label>
                                <form:input type="text" class="form-control" id="name" path="name" placeholder="Computer name" required=""/>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced">Introduced date</form:label>
                                <form:input type="date" class="form-control" id="introduced" path="introduced" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued">Discontinued date</form:label>
                                <form:input type="date" class="form-control" id="discontinued" path="discontinued" placeholder="Discontinued date"/>
                            </div>
                            <div class="form-group">
                                <form:label path="companyId">Company</form:label>
                                <form:select class="form-control" id="companyId" path="companyId">
                                   	<form:option value="" selected="selected">--</form:option>
                                	<c:forEach items="${companies}" var="company">
                                		<form:option value="${company.id}">${company.name}</form:option>
                                	</c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    
   	<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/add_computer.js"></script>
</body>
</html>