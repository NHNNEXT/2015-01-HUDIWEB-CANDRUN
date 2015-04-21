<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>9bagi</title>
	<meta charset = "utf-8">
	<link rel="stylesheet" type="text/css" href="/css/home.css">
</head>
<body>
	<header>
	</header>
			<nav>
		<div class="profile-wrapper">
			<div class="profile-picture"></div>
			<div class="nick">nickname</div>
		</div>
		<div id="goal-groups">
		<div class="label">List of goals & people in group</div>
			<ul id="goal-container">
				<li class="nav_goal">살빼기</li>
				<li class="nav_goal">밥먹기</li>
				<li class="nav_goal">잠자기</li>
			</ul>	
		</div>
	</nav>
	<section id="makeGoal">
		<div class="form-wrapper">
			<form class ="submit-form" >
				<input id="goal-input" name="goal_contents">
				<div id="input-container">
					<input class="task-input">
				</div>
				<div id="task-input-add"> + </div>
				<div id = "goal-form-submit">확인</div>
			</form> 
		</div>
	</section>
	<footer></footer>
</body>
<script src="/js/addGoal.js"></script>
<script src="/js/candrun.js"></script>
</html>