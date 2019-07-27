<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="_header.jsp"></c:import>
<section id="app">
    <h4>Show products:</h4>
    <div>
        <button v-for="cat in categories" @click="getProducts" class="btn btn-primary catBtn ml-3">{{cat.name}}</button>
    </div>
    <h4>Delete category:</h4>
    <div>
        <button v-for="cat in categories" @click="deleteCat" class="btn btn-primary btn-danger catBtn ml-3">{{cat.name}}</button>
    </div>
    <h4>Add new category</h4>
    <c:url value="/admin/addCategory" var="addCatUrl"></c:url>
    <form:form id="addCatForm" action="${addCatUrl}" method="post" modelAttribute="categoryDto" enctype="multipart/form-data">
        <table>
            <tr>
                <td><form:input path="name" type="text" placeholder="Category name"/></td>
                <td><form:input path="files" type="file" lang="en"/></td>
                <td><button type="button" @click="addCategory" class="btn btn-primary">Add</button></td>
            </tr>
        </table>
    </form:form>
    <h4>Products:</h4>
    <div class="products">
        <div v-for="product in products" class="product">
            <img :src="getImgUrl(product.image)">
            <h5>{{product.name}}</h5>
            <h5>{{product.price}}$</h5>
            <h5>Spicy:{{product.spicy}}</h5>
            <h5>Tags: <span v-for="tag in product.tags">{{tag.name}} </span></h5>
            <h5>Ingredients: <span v-for="ingredient in product.ingredients">{{ingredient.name}} </span></h5>
        </div>
    </div>
</section>
<c:import url="_footer.jsp"></c:import>
<script src="resources/js/admin.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">