<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>9bagi</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/home.css">
</head>
<body>
	<nav id='nav'>
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
				</div> <c:forEach items="${friends0}" var="friends0">
					<div>${friends0.nickname}</div>
					<li></li>
					<li></li>
					<li></li>
				</c:forEach>
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
	<button>make new goal</button>
	</nav>

	<section id="show-goal">
		<div class="goal-wrapper">
			<div class="goal-title">${goalRelations[0].myGoal.contents}</div>
			<div class="startdate">${goalRelations[0].myGoal.startDate}</div>
		</div>
		<c:forEach items="${tasks}" var="task">
			<div class="task-wrapper" id="taskid${task.id}">
				<form class="submit_nudge">
					<input type="submit" class="btn-nudge" id="taskContents"
						value="${task.contents}" /> <input type="hidden" class="tasksId"
						value="${task.id}" /> <input type="hidden" class="task-complete"
						value="${task.complete}" />
					<div class="nudge-number">${task.nudge}</div>
				</form>
			</div>
		</c:forEach> 
	</section>

	<section id="make-goal">
	<form class="submit-form">
		<input class="goal-input" name="goal_contents" value="제목을 입력해주세요.">
		<div class="input-container">
			<input class="task-input" />
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