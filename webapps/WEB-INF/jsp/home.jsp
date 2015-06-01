<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>9bagi</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="/css/home.css">
</head>
<body>
	<nav id="nav">
		<div class="section-toggle">flip</div>
		<div class="btn-logout">logout</div>
		
		<div class="profile-wrapper">
			<div class="profile-picture"
				style='background-image: url("/img/pics/${user.picPath}")'></div>
			<div class="nick">${user.nickname}</div>
		</div>
		<!-- id로 되어있는 friend 부분은 DB에서 받아오면서 class로 바꾸어야 한다. -->
		<div id="goal-groups">
			<ul class="nav-goal-container">
				<c:forEach var="goalRelation" items="${goalRelations}">
					<li class="nav-goal"><span id="${goalRelation.myGoal.id}">${goalRelation.myGoal.contents}</span>
						<div class="nav-friends-container">
							<c:forEach var="relation" items="${goalRelation.relation}">
								<div class="friend-picture" id="${relation.value.id}"
									style='background-image: url("/img/pics/${relation.key.picPath}")'></div>
								<input class="friend-email" type="hidden"
									value="${relation.key.email}">
							</c:forEach>
						</div></li>
				</c:forEach>
			</ul>
		</div>
	</nav>
	<div id='wholeWrap'>
		<div id="ani"></div>
		<div id='floatContainer'>
			<div class="realData">
				<div id="flip-container" class="flip-container">
					<div class="flipper">
						<section id="make-goal" class="make-goal">
							<form class="submit-form">
								<input class="goal-input" name="goal_contents"
									value="Goal을 입력하세요.">
								<div class="input-container">

									<div class="wrapper-task-input">
										<input class="task-input" value="Task를 입력하세요." />
										<div class="btn-delete-task"></div>
									</div>
								</div>
								<div class="wrapper-task-input-add">
									<div class="task-input-add">+</div>
								</div>
								<div class="goal-form-submit">submit</div>
							</form>
						</section>
						<section id="show-goal" class="show-goal">
							<form class="submit-nudge">
								<div class="goal-wrapper">
									<div class="goal-title">${goalRelations[0].myGoal.contents}</div>
									<div class="startdate">${goalRelations[0].myGoal.startDate}</div>
									<input class="goal-owner-email" type="hidden"
										value="${goalRelations[0].myGoal.email}">
								</div>
								<c:forEach items="${tasks}" var="task">
									<div class="task-wrapper">
										<input type="submit" class="btn-nudge"
											value="${task.contents}" /> <input type="hidden"
											class="tasksId" value="${task.id}" /> <input type="hidden"
											class="task-complete" value="${task.complete}" />
										<div class="nudge-number">${task.nudge}</div>
									</div>
								</c:forEach>
								<canvas id="taskChart" width="800" height="300"></canvas>
							</form>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
	<nav id="friendsBar" class="toggle">
		<div id="toggle_button"></div>
		<ul>
			<c:forEach var="group" items="${friends}">
				<c:forEach var="friend" items="${group.value}">
					<c:if test="${group.key == 'requestesFriends' }">
						<li class="li-request-friend"><a href="javascript:void(0);"
							class="a-request-friend" id="${friend.email}">${friend.nickname}</a>
							<a href="javascript:void(0);" value="${friend.email}"
							class="btn-request-friend">수락</a></li>
					</c:if>
					<c:if test="${group.key == 'availableFriends' }">
						<li class="li-available-friend"><a href="javascript:void(0);"
							class="a-available-friend" id="${friend.email}">${friend.nickname}</a>
						</li>
					</c:if>
					<c:if test="${group.key == 'nonavailableFriends' }">
						<li class="li-nonavailable-friend"><a
							href="javascript:void(0);" class="a-nonavailable-friend"
							id="${friend.email}">${friend.nickname}</a></li>
					</c:if>
				</c:forEach>
			</c:forEach>
		</ul>
	</nav>
</body>
<script src="/js/template/handlebars-v3.0.3.js"></script>
<script src="/js/candrun.js"></script>
<script src="/js/home.js"></script>
<script src="/js/chart/Chart.js"></script>
</html>

