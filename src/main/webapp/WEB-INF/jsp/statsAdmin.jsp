<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="_header.jsp"></c:import>
<section id="app">
   <table class="table table-striped">
        <tr class="thead-dark">
            <th>Username</th>
            <th>Payment method</th>
            <th>Delivery method</th>
            <th>Date</th>
            <th>Status</th>
            <th>Change status</th>
            <th>Items</th>
        </tr>
        <tr v-for="order in orders">
            <td>{{order.username}}</td>
            <td>{{order.paymentMethod}}</td>
            <td>{{order.deliveryMethod}}</td>
            <td>{{order.date}}</td>
            <td>{{order.status}}</td>
            <td>
                <select @change="changeStatus">
                    <c:forEach items="${statusList}" var="status">
                        <option>${status}</option>
                    </c:forEach>
                </select>
            </td>
            <td><ul><li v-for="item in order.items">{{item.name}}</li></ul></td>
        </tr>
   </table>
</section>
<style>

</style>
<c:import url="_footer.jsp"></c:import>
<script src="<c:url value="/resources/js/statsAdmin.js"/>"></script>

