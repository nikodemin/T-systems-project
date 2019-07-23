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
            <c:if test="${sessionScope.username == null}">
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
            </c:if>
            <c:if test="${sessionScope.username != null}">
                <li class="list-inline-item">
                    <a href="#">
                        <i class="fas fa-cog"></i> Настройки
                    </a>
                </li>
                <li class="list-inline-item">
                    <a href="#">
                        <i class="fas fa-sign-out-alt"></i> Выйти
                    </a>
                </li>
            </c:if>
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
                <c:if test="${sessionScope.username == null}">
                    <a class="dropdown-item" href="#"><i class="fas fa-user"></i> Войти</a>
                    <a class="dropdown-item" href="#"><i class="fas fa-user-plus"></i> Регистрация</a>
                </c:if>
                <c:if test="${sessionScope.username != null}">
                    <a class="dropdown-item" href="#"><i class="fas fa-cog"></i> Настройки</a>
                    <a class="dropdown-item" href="#"><i class="fas fa-sign-out-alt"></i> Выйти</a>
                </c:if>
                <a class="dropdown-item" href="#"><i class="fas fa-shopping-cart"></i> Корзина</a>
            </div>
        </div>
    </nav>
</section>
