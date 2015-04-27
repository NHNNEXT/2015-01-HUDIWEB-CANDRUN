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
	<nav>
	<div class="profile-wrapper">
		<div class="profile-picture"></div>
		<div class="nick">pobi</div>
	</div>
	<!-- id로 되어있는 friend 부분은 DB에서 받아오면서 class로 바꾸어야 한다. -->
	<div id="goal-groups">
		<ul class="nav-goal-container">
			<li class="nav-goal">${goals[0].contents} 
				<div class="nav-friends-container">
					<div class="nigayo"></div>
					<div class="cob"></div>
					<div class="hth"></div>
				</div>
<!-- 				<c:forEach items="${friends0}" var="friends0">
					<div>${friends0.nickname}</div>
					<li></li>
					<li></li>
					<li></li>
				</c:forEach> -->
			</li>
			<li class="nav-goal">${goals[1].contents}
				<div class="nav-friends-container">
					<div class="kdj"></div>
					<div class="hsj"></div>
				</div>
			</li>
			<li class="nav-goal">${goals[2].contents}
				<div class="nav-friends-container">
					<div class="nigayo"></div>
					<div class="hsj"></div>
					<div class="kdj"></div>
				</div>
			</li>
		</ul>
	</div>
	</nav>

	<section id="show-goal">
		<div class="goal-wrapper">
			<div class = "goal-title">${goals[0].contents}</div>
			<div class = "startdate">${goals[0].startDate}</div>
		</div>
		<c:forEach items="${tasks}" var="tasks">
			<div class="task-wrapper">
				<form class="submit_nudge">
					<input type="submit" class="btn-nudge" value="${tasks.contents}" />
					<input type="hidden" class="task-id" value="${tasks.id}" />
					<div class="nudge-number">${tasks.nudge}</div>
				</form>
			</div>
		</c:forEach>
	</section>	

	<section id="make-goal">
			<form class="submit-form">
				<input class="goal-input" name="goal_contents" value="제목을 입력해주세요.">
					<div class="input-container">
						<input class="task-input"/>
					</div>
				<div class="task-input-add">+</div>
				<div class="goal-form-submit">submit</div>
			</form>
	</section>
</body>
<script src="/js/addGoal.js"></script>
<script src="/js/addNudge.js"></script>
<script src="/js/candrun.js"></script>
</html>