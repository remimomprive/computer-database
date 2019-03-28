<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authentication var="user" property="principal" />

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/static/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard"> Application - Computer Database </a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">${user.username} <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/computer-database/logout"><spring:message code="logout.message" /></a></li>
					</ul></li>
				<li><a
					href="?locale=fr&page_id=${pageId}&page_size=${pageSize}&search=${search}&order_by=${orderBy}&order_direction=${orderDirection}">fr</a></li>
				<li><a
					href="?locale=en&page_id=${pageId}&page_size=${pageSize}&search=${search}&order_by=${orderBy}&order_direction=${orderDirection}">en</a></li>
			</ul>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${computerCount}
				<spring:message code="computers_found" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="${pageContext.request.contextPath}/dashboard" method="GET" class="form-inline">
						<input type="hidden" name="page_size" value="${pageSize}" /> <input type="hidden" name="page_id" value="1" /> <input type="hidden"
							name="order_by" value="${orderBy}" /> <input type="hidden" name="order_direction" value="${orderDirection}" /> <input type="search"
							id="searchbox" name="search" class="form-control" placeholder="<spring:message code="search.name.input"/>" /> <input type="submit"
							id="searchsubmit" value="<spring:message code="search.name.button"/>" class="btn btn-primary" />
					</form>
				</div>
				
				<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="${pageContext.request.contextPath}/addComputer"><spring:message
							code="add_computer.button" /></a>
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="edit.button" /></a>
				</div>
				</sec:authorize>
			</div>
		</div>

		<form id="deleteForm" action="/computer-database/" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">

			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
							<th class="editMode" style="width: 60px; height: 22px;"><input type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
						</sec:authorize>

						<th><spring:message code="computer.name" /> <c:choose>
								<c:when test="${orderBy == 'name'}">
									<c:choose>
										<c:when test="${orderDirection == 'asc'}">
											<a href="?page_id=1&search=${search}&order_by=name&order_direction=desc"><i class="fa fa-sort-down"></i></a>
										</c:when>
										<c:otherwise>
											<a href="?page_id=1&search=${search}&order_by=name&order_direction=asc"><i class="fa fa-sort-up"></i></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<a href="?page_id=1&search=${search}&order_by=name&order_direction=desc"><i class="fa fa-sort-down"></i></a>
								</c:otherwise>
							</c:choose></th>

						<th><spring:message code="computer.introduced" /> <c:choose>
								<c:when test="${orderBy == 'introduced'}">
									<c:choose>
										<c:when test="${orderDirection == 'asc'}">
											<a href="?page_id=1&search=${search}&order_by=introduced&order_direction=desc"><i class="fa fa-sort-down"></i></a>
										</c:when>
										<c:otherwise>
											<a href="?page_id=1&search=${search}&order_by=introduced&order_direction=asc"><i class="fa fa-sort-up"></i></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<a href="?page_id=1&search=${search}&order_by=introduced&order_direction=desc"><i class="fa fa-sort-down"></i></a>
								</c:otherwise>
							</c:choose></th>

						<th><spring:message code="computer.discontinued" /> <c:choose>
								<c:when test="${orderBy == 'discontinued'}">
									<c:choose>
										<c:when test="${orderDirection == 'asc'}">
											<a href="?page_id=1&search=${search}&order_by=discontinued&order_direction=desc"><i class="fa fa-sort-down"></i></a>
										</c:when>
										<c:otherwise>
											<a href="?page_id=1&search=${search}&order_by=discontinued&order_direction=asc"><i class="fa fa-sort-up"></i></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<a href="?page_id=1&search=${search}&order_by=discontinued&order_direction=desc"><i class="fa fa-sort-down"></i></a>
								</c:otherwise>
							</c:choose></th>

						<th><spring:message code="computer.company" /> <c:choose>
								<c:when test="${orderBy == 'company_name'}">
									<c:choose>
										<c:when test="${orderDirection == 'asc'}">
											<a href="?page_id=1&search=${search}&order_by=company_name&order_direction=desc"><i class="fa fa-sort-down"></i></a>
										</c:when>
										<c:otherwise>
											<a href="?page_id=1&search=${search}&order_by=company_name&order_direction=asc"><i class="fa fa-sort-up"></i></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<a href="?page_id=1&search=${search}&order_by=company_name&order_direction=desc"><i class="fa fa-sort-down"></i></a>
								</c:otherwise>
							</c:choose></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers.content}" var="computer">
						<tr>
							<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
								<td class="editMode"><input type="checkbox" name="cb" class="cb" value="${computer.id}"></td>
							</sec:authorize>
							
							<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
								<td><a href="computer/${computer.id}/edit" onclick="">${computer.name}</a></td>
							</sec:authorize>
							<sec:authorize access="!hasRole('ROLE_ADMIN') and isAuthenticated()">
								<td>${computer.name}</td>
							</sec:authorize>
							
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:choose>
					<c:when test="${pageId > 1}">
						<li><a href="?page_id=1&search=${search}&order_by=${orderBy}&order_direction=${orderDirection}" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span></a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item disabled"><span class="page-item" aria-hidden="true">&laquo;</span></li>
					</c:otherwise>
				</c:choose>

				<c:if test="${pageId - 1 >= 0}">
					<c:forEach var="i" begin="${pageId - 1}" end="${pageId + 1}" step="1">
						<c:if test="${(i ne 0) && (i ne pageCount + 1)}">
							<c:choose>
								<c:when test="${i eq pageId}">
									<li class="page-item disabled"><a href="#">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="?page_id=${i}&page_size=${pageSize}&search=${search}&order_by=${orderBy}&order_direction=${orderDirection}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:if>

				<c:choose>
					<c:when test="${pageId < pageCount}">
						<li><a href="?page_id=${pageCount}&search=${search}&order_by=${orderBy}&order_direction=${orderDirection}" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item disabled"><span aria-hidden="true" class="page-link">&raquo;</span></li>
					</c:otherwise>
				</c:choose>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:set var="pageSizeValues">10,20,50</c:set>
				<c:forTokens items="${pageSizeValues}" var="pageSizeValue" delims=",">
					<a role="button" type="button" class="btn btn-default"
						href="http://localhost:8080/computer-database/dashboard?page_id=1&page_size=${pageSizeValue}&search=${search}&order_by=${orderBy}&order_direction=${orderDirection}">
						${pageSizeValue} </a>
				</c:forTokens>
			</div>
		</div>
	</footer>
	<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>
</body>
</html>