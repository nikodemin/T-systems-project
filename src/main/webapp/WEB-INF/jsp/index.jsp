<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="_header.jsp"></c:import>
<section>
    <div id="slider" class="carousel slide" data-ride="carousel" data-interval="3000">
        <ol class="carousel-indicators">
            <li data-target="#slider" data-slide-to="0" class="active"></li>
            <li data-target="#slider" data-slide-to="1"></li>
            <li data-target="#slider" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img class="d-block w-100" src="resources/img/pizza.jpg">
                <div class="carousel-caption d-none d-md-block">
                    <h3>Large selection of pizza for every taste!</h3>
                </div>
            </div>
            <div class="carousel-item">
                <img class="d-block w-100" src="resources/img/sushi.png">
                <div class="carousel-caption d-none d-md-block">
                    <h3>Try out our new sushi sets!</h3>
                </div>
            </div>
            <div class="carousel-item">
                <img class="d-block w-100" src="resources/img/burgerAndCola.jpg">
                <div class="carousel-caption d-none d-md-block">
                    <h3>Discounts on sets!</h3>
                </div>
            </div>
        </div>
    </div>
</section>
<a name="menu"></a>
<section class="menu">
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/burger.jpg">
            <p>Burgers</p>
        </a>
    </div>
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/drink.jpg">
            <p>Drinks</p>
        </a>
    </div>
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/hot.jpg">
            <p>Hot</p>
        </a>
    </div>
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/pizza.jpg">
            <p>Pizza</p>
        </a>
    </div>
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/set.jpg">
            <p>Sets</p>
        </a>
    </div>
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/sushi.jpg">
            <p>Sushi</p>
        </a>
    </div>
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/sweet.jpg">
            <p>Desserts</p>
        </a>
    </div>
    <div class="menuItem">
        <a href="#">
            <img src="resources/img/cat/wok.jpg">
            <p>Wok</p>
        </a>
    </div>
</section>
<section class="leaders">
    <h2 class="mt-2 ml-2">Top sales:</h2>
    <div class="tags d-block my-4 ml-2">
        <button class="btn btn-outline-warning" data-toggle="button" aria-pressed="false">
            <i class="fas fa-plus-circle"></i> Spicy
        </button>
        <button class="btn btn-outline-warning" data-toggle="button" aria-pressed="false">
            <i class="fas fa-plus-circle"></i> Spicy
        </button>
        <button class="btn btn-outline-warning" data-toggle="button" aria-pressed="false">
            <i class="fas fa-plus-circle"></i> Spicy
        </button>
    </div>
    <div class="products">
        <c:forEach var="product" items="${leaders}">
            <div class="productItem card">
                <img class="card-img-top" src="<c:url value="${product.image}"/>">
                <div class="card-body">
                    <h4 class="card-title productName"><c:out value="${product.name}"></c:out></h4>
                    <h5><c:out value="${product.price}"></c:out> $</h5>
                    <p class="card-text">
                        <c:forEach var="ingredient" items="${product.subListIngredients}">
                            <c:out value="${ingredient.name}, "></c:out>
                        </c:forEach>
                        <c:out value="${product.lastIngredient.name}"></c:out>
                    </p>
                    <button class="btn btn-primary btn-warning addToCart">
                        <i class="fas fa-shopping-cart"></i>Add to cart
                    </button>
                </div>
            </div>
        </c:forEach>
    </div>
</section>
<c:import url="_footer.jsp"></c:import>

