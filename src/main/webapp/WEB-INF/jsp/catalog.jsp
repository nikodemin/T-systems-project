<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="_header.jsp"/>
<section>
    <div class="products">
        <c:forEach var="product" items="${products}">
            <div class="productItem card">
                <img class="card-img-top" src="<c:url value="${product.image}"/>"/>
                <div class="card-body">
                    <h4 class="card-title productName"><c:out value="${product.name}"/></h4>
                    <h5><c:out value="${product.price}"/> $</h5>
                    <p class="card-text">
                        <c:forEach var="ingredient" items="${product.subListIngredients}">
                            <c:out value="${ingredient.name}, "/>
                        </c:forEach>
                        <c:out value="${product.lastIngredient.name}"/>
                    </p>
                    <button class="btn btn-primary btn-warning addToCart">
                        <i class="fas fa-shopping-cart"></i>Add to cart
                    </button>
                </div>
            </div>
        </c:forEach>
    </div>
</section>
<c:import url="_footer.jsp"/>

