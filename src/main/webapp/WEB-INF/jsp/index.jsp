<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
     <head>
          <meta charset="utf-8">
          <meta http-equiv="X-UA-Compatible" content="IE=edge">
          <meta name="viewport" content="width=device-width, initial-scale=1">
          <title>PizzaMaker</title>
          <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
                integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
          <link rel="stylesheet" type="text/css" href="resources/css/main.css">
     </head>
     <body>
          <section id="topMenu">
               <nav class="navbar navbar-light fixed-top px-4 bg-light">
                    <a class="navbar-brand" href="#">
                         <img src="resources/img/logo.png" id="logo">
                         <h3>Pizza Maker</h3>
                    </a>
                    <ul class="list-unstyled inline-list float-right d-none d-md-block">
                         <li class="list-inline-item">
                              <a href="#">
                                   <i class="fas fa-book-open"></i> Меню
                              </a>
                         </li>
                         <li class="list-inline-item">
                              <a href="#">
                                   <i class="fas fa-user"></i> Войти
                              </a>
                         </li>
                         <li class="list-inline-item">
                              <a href="#">
                                   <i class="fas fa-user-plus"></i> Регистрация
                              </a>
                         </li>
                         <li class="list-inline-item">
                              <a href="#">
                                   <i class="fas fa-shopping-cart"></i> Корзина
                              </a>
                         </li>
                    </ul>
                    <div class="dropdown d-md-none">
                         <button class="btn btn-secondary dropdown-toggle" type="button"
                                 id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                                 aria-expanded="false">
                              Меню
                         </button>
                         <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                              <a class="dropdown-item" href="#"><i class="fas fa-book-open"></i> Меню</a>
                              <a class="dropdown-item" href="#"><i class="fas fa-user"></i> Войти</a>
                              <a class="dropdown-item" href="#"><i class="fas fa-user-plus"></i> Регистрация</a>
                              <a class="dropdown-item" href="#"><i class="fas fa-shopping-cart"></i> Корзина</a>
                         </div>
                    </div>
               </nav>
          </section>
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
                                   <h3>Большой выбор пиццы на любой вкус!</h3>
                              </div>
                         </div>
                         <div class="carousel-item">
                              <img class="d-block w-100" src="resources/img/sushi.png">
                              <div class="carousel-caption d-none d-md-block">
                                   <h3>Попробуйте наши новые суши наборы!</h3>
                              </div>
                         </div>
                         <div class="carousel-item">
                              <img class="d-block w-100" src="resources/img/burgerAndCola.jpg">
                              <div class="carousel-caption d-none d-md-block">
                                   <h3>Скидки на наборы!</h3>
                              </div>
                         </div>
                    </div>
               </div>
          </section>
          <section class="menu">
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/burger.jpg">
                         <p>Бургеры</p>
                    </a>
               </div>
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/drink.jpg">
                         <p>Напитки</p>
                    </a>
               </div>
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/hot.jpg">
                         <p>Горячее</p>
                    </a>
               </div>
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/pizza.jpg">
                         <p>Пицца</p>
                    </a>
               </div>
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/set.jpg">
                         <p>Наборы</p>
                    </a>
               </div>
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/sushi.jpg">
                         <p>Суши</p>
                    </a>
               </div>
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/sweet.jpg">
                         <p>Десерты</p>
                    </a>
               </div>
               <div class="menuItem">
                    <a href="#">
                         <img src="resources/img/cat/wok.jpg">
                         <p>Воки</p>
                    </a>
               </div>
          </section>
          <section class="leaders">
               <h2 class="mt-2 ml-2">Лидеры продаж:</h2>
               <div class="tags d-block my-4 ml-2">
                    <button class="btn btn-outline-warning" data-toggle="button" aria-pressed="false">
                         <i class="fas fa-plus-circle"></i> Острое
                    </button>
                    <button class="btn btn-outline-warning" data-toggle="button" aria-pressed="false">
                         <i class="fas fa-plus-circle"></i> Острое
                    </button>
                    <button class="btn btn-outline-warning" data-toggle="button" aria-pressed="false">
                         <i class="fas fa-plus-circle"></i> Острое
                    </button>
               </div>
               <div class="leaders">
                    <div class="leadersItem card">
                         <img class="card-img-top" src="resources/img/pizza.jpg">
                         <div class="card-body">
                              <h5 class="card-title">Пицца Оливер</h5>
                              <p class="card-text">
                                   Томатный соус, сыр моцарелла, пепперони,
                                   ароматная свинина, ветчина, шампиньоны,
                                   сладкий зеленый перец, лук, черные оливки
                              </p>
                              <a href="#" class="btn btn-primary btn-warning">
                                   <i class="fas fa-shopping-cart"></i>В корзину
                              </a>
                         </div>
                    </div>
                    <div class="leadersItem card">
                         <img class="card-img-top" src="resources/img/pizza.jpg">
                         <div class="card-body">
                              <h5 class="card-title">Пицца Оливер</h5>
                              <p class="card-text">
                                   Томатный соус, сыр моцарелла, пепперони,
                                   ароматная свинина, ветчина, шампиньоны,
                                   сладкий зеленый перец, лук, черные оливки
                              </p>
                              <a href="#" class="btn btn-primary btn-warning">
                                   <i class="fas fa-shopping-cart"></i>В корзину
                              </a>
                         </div>
                    </div>
                    <div class="leadersItem card">
                         <img class="card-img-top" src="resources/img/pizza.jpg">
                         <div class="card-body">
                              <h5 class="card-title">Пицца Оливер</h5>
                              <p class="card-text">
                                   Томатный соус, сыр моцарелла, пепперони,
                                   ароматная свинина, ветчина, шампиньоны,
                                   сладкий зеленый перец, лук, черные оливки
                              </p>
                              <a href="#" class="btn btn-primary btn-warning">
                                   <i class="fas fa-shopping-cart"></i> В корзину
                              </a>
                         </div>
                    </div>
               </div>
          </section>
          <footer>
               <p>
                    © Copyright 2004 — 2019 ООО «2Пицца» Все права защищены. Возраст 6+
                    Все права защищены. Возраст 6+
               </p>
          </footer>
          <script src="https://kit.fontawesome.com/c08c803562.js"></script>
          <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                  integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
                  integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
          <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
                  integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
     </body>
</html>
