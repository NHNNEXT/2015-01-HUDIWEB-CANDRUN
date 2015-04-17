<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>9bagiShowGoal</title>
	<meta charset = "utf-8">
	<link rel="stylesheet" type="text/css" href="css/showGoal.css">
</head>
<body>
	<header>
	</header>
	<nav>
		<div class="profile_wrapper">
			<div class="profile_picture"></div>
			<div class="nick">nickname</div>
		</div>
		<div id="goal_groups">
		<div class="label">List of goals & people in group</div>
			<ul>
				<li><button id="showCollection">모아보기</button></li>
				<li class="goal">살빼기</li>
				<li class="goal">밥먹기</li>
				<li class="goal">잠자기</li>
			</ul>	
	</nav>
	<section id="showGoal">
		<div class="form_wrapper">		
				
			<div class="input_container">${goal.contents}</div>
			<div>${goal.startDate}</div>
			
			<c:forEach items="${task}" var="tasks">
        		<div>${task.contents}</div>
				<form class ="submit_nudge" action="/addNudge.cdr" method="post">
					<input name="tasksId" value="${tasks.id}"/>
					<div>${task.nudge}</div>
					<button>NUDGE</button>
				</form>
	      </c:forEach>
		</div>
	</section>
	<footer></footer>
</body>
<script src="/js/addInputTag.js"></script>
</html>