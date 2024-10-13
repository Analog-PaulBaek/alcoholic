<%@page import="web.admin.Order"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/order.css" />
</head>
<body>
    <!-- 헤더 부분 시작 -->
    <header>
        <div class="top">
            <div class="logo">
                <a href="">SYSTEM</a>
            </div>

            <div class="menu">
                <ul>
                    <li>
                        <a class="main_menu" href="">SYSTEM</a>
                        <ul>
                            <li><a href="/web/admin">ADMIN</a></li>
                                    <li><a href="/web/admin_product">PRODUCT</a></li>
                                    <li><a href="/web/admin_order">ORDER</a></li>
                        </ul>
                </ul>
            </div>
        </div>
        <!-- 우측 상단 로그인&회원가입 -->
        <div class="login">
            <p>system 님 , 안녕하세요.</p>
            <a class="log_btn">LOGIN(LOGOUT)</a>
        </div>
    </header>
    <!-- 헤더 부분  끝-->


    <!-- 메인 부분 시작 -->
    <main>
        <div class="container">
            <h1 class="title">ORDER</h1>

            <table>
                <tr>
                    <th class="info">No</th>
                    <th class="info">주문일자</th>
                    <th class="info">주문ID</th>
                    <th class="info">주문금액</th>
                    <th class="info">주문수량</th>
                    <th class="register"><a href=""></a></th>
                </tr>
				
				<%ArrayList<Order> list = (ArrayList<Order>)request.getAttribute("list"); %>
				
                <!-- 고객 추가 반복 부분 -->
                <%for (Order order : list) {%>
                <tr class="order_info">
                    <th><%=order.getOrderNo() %></th>
                    <th><%=order.getOrderDate() %></th>
                    <th><%=order.getUserId() %></th>
                    <th><%=order.getPrice() %></th>
                    <th><%=order.getQuantity() %></th>
                    <th><a class="detail" href="/web/admin_order.detail?no=<%=order.getOrderNo() %>">상세보기</a></th>
                </tr>
                 <%} %>
                <!-- 고객 추가 반복 부분 -->

            </table>

        </div>
        
    </main>
    <!-- 메인 부분 끝 -->
</body>
</html>