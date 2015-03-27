<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>9bagi</title>
	<meta charset = "utf-8">
	<link rel="stylesheet" type="text/css" href="/css/makeGoal.css">
</head>
<body>
	<div id="main">
		<div id="bar">
			tabs
		</div>
		<div id="left">
			<div id="profile">
				<div><img src="profilePic.jpg" alt="profilePic"></div>
				<div>nickname</div>
			</div>
			<div id="goalGroups">List of goals & people in group
				<ul>
					<li>살빼기</li>
					<li>메이븐 공부</li>
					<li>자바스크립 공부</li>
				</ul>	
			</div>
		</div>
		<div id="right">
			<div id="inputGoal">
				<form action="/addGoal.cdr" method="post">
					<label>주목표</label>
					<br>
					<input name="goal_contents" value=""/>
					<br>
					<label>세부목표</label>
					<br>
					<input name="task_contents" value=""/>
					<br>
					 <button> + </button>
					 <br>
					<button>확인</button>
				</form>
			</div>
		</div>
		<!-- <div id="friendsList">
			<ul>
				<li>JB</li>
				<li>COB</li>
				<li>SSH</li>
				<li>KOH</li>
			</ul>
		</div> -->
	</div>
</body>
</html>