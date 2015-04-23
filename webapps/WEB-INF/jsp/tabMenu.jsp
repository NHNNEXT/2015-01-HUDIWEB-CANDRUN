<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ tagliburi ="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>9bagiShowGoal</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/tabMenu.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</head>
<body>
	<header> </header>
	<nav>
	<div class="profile_wrapper">
		<div class="profile_picture"></div>
		<div class="nick">nickname</div>
	</div>
	<div id="goal_groups">
		<div class="label">List of goals & people in group</div>
		<ul>
			<li class="goal">살빼기</li>
			<li class="goal">밥먹기</li>
			<li class="goal">잠자기</li>
		</ul>
	</nav>
	<section id="showGoal">
	<div class="form_wrapper">
		<ul class="tabs">
			<c:forEach items="${tasks}" var="tasks" varStatus="status">
				<h2>
					<span>${goal.startDate}</span>
				</h2>
				<li class="tabs__item color${status.count}">
					<p class="tabs__stats">${goal.contents}</p>
					<p class="tabs__stats">${tasks.contents}
					<form class="submit_nudge">
						<input name="tasksId" class="tasksId" value="${tasks.id}" />
						<div>
						<p class="tabs__stats">
							<span class="number">${tasks.nudge}</span>
							<input type="submit" class="nudge_btn" value="NUDGE" />
						</p>
						</div>
					</form>
					</p>
				</li>
			</c:forEach>
			<div class="views-toggle views-toggle--hidden">
				<svg fill="white" viewBox="0 0 24 24"> <path
					d="M16.59 8.59l-4.59 4.58-4.59-4.58-1.41 1.41 6 6 6-6z" /> <path
					d="M0 0h24v24h-24z" fill="none" /> </svg>
			</div>
		</ul>
	</div>
	</section>
	<footer></footer>
	<script src="/js/addNudge.js"></script>
	<script src="/js/tabMenu.js"></script>
	<script src="/js/candrun.js"></script>
</body>
</html>