<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>We are Gubagi!!</title>
<link rel="stylesheet" href="/css/index.css" media="all">
</head>
<body class="index-body">
	<video class="videobg"
		src="http://www.trippeo.com/assets/video/1--receptor.webm"
		preload="auto" autoplay loop></video>
	<div class="title">
		Welcome<br /> I'm GUBAGI
	</div>
	<div class="sign-link-wrapper">
		<div class="sign-link-border">
			<a id="signin-link" href="javascript:void(0);">SING IN</a> <span>/</span>
			<a id="signup-link" href="javascript:void(0);">SIGN UP</a>
		</div>
	</div>
	<div class="form-wrapper">
		<form id="signup-form" action="" method="post" class="dp-none">
			<div class="form-dody">
				<div id="signup-close-mark" class="close-mark">&#10006</div>
				<div class="form-title">회원가입</div>
				<ul id="signup-form-fields">
					<li><label for="input-1-1">E-mail</label>
						<div class="input-container">
							<input id="input-1-1" type="email" name="email">
						</div>
						<p class="responser" id="signup-email-responser"></p> <br /></li>
					<li><label for="input-1-2">Nick</label>
						<div class="input-container">
							<input id="input-1-2" type="text" name="nick">
						</div> <br /></li>
					<li><label for="input-1-3">Password</label>
						<div class="input-container">
							<input id="input-1-3" type="password" name="password">
						</div>
						<p class="responser" id="signup-pw-responser"></p> <br /></li>
					<li><label for="input-1-4">Confirm password</label>
						<div class="input-container">
							<input id="input-1-4" type="password">
						</div>
						<p class="responser" id="signup-confirm-pw-responser"></p> <br />
					</li>
				</ul>
				<input id="signup-form-submit" type="submit" value="가입" disabled>
			</div>
		</form>
		<form id="signin-form" class="dp-none" action="" method="post"
			class="dp-none">
			<div class="form-dody">
				<div id="signin-close-mark" class="close-mark">&#10006</div>
				<div class="form-title">Sign in</div>
				<ul id="signin-form-fields">
					<li><label for="input-2-1">E-mail</label>
						<div class="input-container">
							<input id="input-2-1" type="email" name="email">
						</div>
						<p class="responser" id="signin-email-responser"></p> <br /></li>
					<li><label for="input-2-2">Password</label>
						<div class="input-container">
							<input id="input-2-2" type="password" name="password">
						</div>
						<p class="responser" id="signin-pw-responser"></p> <br /></li>
				</ul>
				<input id="signin-form-submit" type="submit" value="로그인" disabled>
			</div>
		</form>
	</div>
</body>
<script src="/js/candrun.js" type="text/javascript"></script>
<script src="/js/index.js" type="text/javascript"></script>
<script src="/js/aes.js" type="text/javascript"></script>
<script src="/js/aesCtr.js" type="text/javascript"></script>
</html>