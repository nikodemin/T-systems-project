<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="_header.jsp"></c:import>
<section>
    <form:form action="/register" method="post" modelAttribute="form">
        <table>
            <tr>
                <td>
                    <form:input type="text" class="form-control" placeholder="Эл. почта" path="email"/>
                </td>
                <td>
                    <form:errors path="email" cssClass="alert alert-danger" element="div"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" class="btn btn-primary">
                </td>
            </tr>
        </table>
    </form:form>
</section>
<style>
     form
     {
          max-width: 350px;
          margin: 100px auto;
     }
</style>
<c:import url="_footer.jsp"></c:import>

