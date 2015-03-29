<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>9bagi</title>
	<meta charset = "utf-8">
	<link rel="stylesheet" type="text/css" href="/css/gubagi.css">
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
				<li class="goal">살빼기</li>
				<li class="goal">밥먹기</li>
				<li class="goal">잠자기</li>
			</ul>	
		</div>
	</nav>
	<section id="makeGoal">
		<div class="form_wrapper">
			<form class ="submit_form" action="/addGoal.cdr" method="post">
				<input class="goal_contents" name="goal_contents" value=""/>
				<div class="input_container"></div>
				<div class="add_input_btn"> + </div>
				<div class ="submit_btn">확인</div>
			</form>
		</div>
	</section>
	<footer></footer>
</body>
<script src="/js/addInputTag.js"></script>
</html>