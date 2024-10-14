<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin.css" />

<style>
body {
	margin: 0;
	padding: 0;
	font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS',
		sans-serif;
	background-color: rgba(252, 245, 223, 0.95);
}

header {
	height: 160px;
	background-color: rgba(252, 245, 223, 0.95);
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
}

.top {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: 100%;
	position: relative;
	letter-spacing: 5px;
}

.logo {
	width: 320px;
	height: 65px;
	font-size: 50px;
	font-weight: 600;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 55px;
}

.logo a:hover {
	text-decoration: none;
}

.menu {
	width: 440px;
	height: 45px;
	position: absolute;
	bottom: 0;
}

.menu ul {
	display: flex;
	align-items: center;
	justify-content: space-around;
	padding: 0;
	margin: 0;
	height: 100%;
	position: relative;
}

.menu ul li {
	list-style: none;
	font-size: 20px;
	padding: 20px;
}

.main_menu {
	text-decoration: none;
	color: black;
}

.menu ul li ul {
	display: none;
	position: absolute;
	top: 45px;
	left: calc(50% - 500px);
	background-color: rgba(252, 245, 223, 0.95);
	width: 1000px;
	height: 250px;
	z-index: 999;
}

.menu ul li ul li {
	font-size: 40px;
	font-weight: 600;
	margin: 25px;
}

.menu ul li:hover ul {
	display: flex;
	flex-direction: column;
}

.menu ul li:hover {
	text-decoration: underline;
	text-decoration-color: rgb(250, 220, 100);
}

.menu ul li a {
	text-decoration: none;
}

.login {
	width: auto;
	position: absolute;
	right: 95px;
	top: 45px;
}

.log_btn:hover {
	text-decoration: underline;
}

main {
	min-height: 315px;
	padding: 0 190px;
}

.container {
	display: flex;
	justify-content: center;
	flex-direction: column;
	align-items: center;
}

.title {
	padding: 15px 0;
	width: 500px;
	border-bottom: 1px solid black;
	text-align: center;
	font-size: 40px;
}

.input_wrap {
	border: 1px solid gray;
	width: 600px;
	padding: 30px 50px;
}

.input_wrap p {
	font-size: 20px;
	font-weight: 300;
}

.input_wrap input {
	background-color: rgba(252, 245, 223, 0.95);
	border: 0;
	border-bottom: 1px solid gray;
	font-size: 20px;
	padding-left: 15px;
}
</style>
</head>
<body>
	<!-- 헤더 부분  -->
	<jsp:include page="../componants/header.jsp"></jsp:include>


	<!-- 메인 부분 -->
	<main>
		<div class="container">
			<h1 class="title">LOGIN</h1>
			<div class="input_wrap">
				<form action="${pageContext.request.contextPath}/adminlogin"
					method="post">
					<p>
						아이디 : <input type="text" name="user_id" id="user_id">
					</p>
					<p>
						비밀번호 : <input type="password" name="user_pw" id="user_pw">
					</p>
					<button id="loginBtn">로그인</button>
				</form>
				<a href="${pageContext.request.contextPath}/login">유저 로그인</a>
			</div>
		</div>
	</main>
</body>
</html>