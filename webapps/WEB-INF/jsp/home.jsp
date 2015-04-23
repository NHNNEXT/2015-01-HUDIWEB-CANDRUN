<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ tagliburi ="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>9bagi</title>
<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="/css/home.css">
</head>
<body>
	<header> </header>

	<nav>
	<div class="profile-wrapper">
		<div class="profile-picture"></div>
		<div class="nick">nickname</div>
	</div>
	<div id="goal-groups">
		<div class="label">List of goals & people in group</div>
		<ul class="nav-goal-container">
			<li class="nav-goal">${goals[0].contents} 
				<c:forEach items="${friends0}" var="friends0">
					<div>${friends0.nickname}</div>
					<div>111</div>
				</c:forEach>
			</li>
			<li class="nav-goal">${goals[1].contents}
				<c:forEach items="${friends1}" var="friends1">
					<div>${friends1.nickname}</div>
					<div>222</div>
				</c:forEach>
			</li>
			<li class="nav-goal">${goals[2].contents}
				<c:forEach items="${friends2}" var="friends2">
					<div>${friends2.nickname}</div>
					<div>333</div>
				</c:forEach>
			</li>
		</ul>
	</div>
	</nav>

	<section id="make-goal">
		<div class="form_wrapper">
			<form class="submit_form">
				<input class="goal_contents" name="goal_contents">
					<div class="input_container">
						<input class="task_contents">
					</div>
				<div class="add_input_btn">+</div>
				<div id="btnAddGoal" class="submit_btn">확인</div>
			</form>
		</div>
	</section>

	<section id="show-goal">
		<div class="goal-wrapper">
			<div class = "title">${goals[0].contents}</div>
			<div class = "startdate">${goals[0].startDate}</div>
		</div>
		<div class="task-wrapper">
			<c:forEach items="${tasks}" var="tasks">
				<div class="task-title">${tasks.contents}
				<form class="submit_nudge">
					<div class="task-nudge">${tasks.nudge}</div>
					<input type="submit" class="nudge_btn" value="NUDGE" />
				</form>
				</div>
			</c:forEach>
		</div>
	</section>
	
	<footer></footer>
</body>
<script src="/js/addGoal.js"></script>
<script src="/js/addNudge.js"></script>
<script src="/js/candrun.js"></script>
</html>