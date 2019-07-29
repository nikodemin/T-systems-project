<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="_header.jsp"></c:import>
<section id="app">
    <h4>Show products:</h4>
    <div>
        <button v-for="cat in categories" @click="getProductsByClick" class="btn btn-primary catBtn ml-3">{{cat.name}}
        </button>
    </div>
    <h4>Delete category:</h4>
    <div>
        <button v-for="cat in categories" @click="deleteCat" class="btn btn-primary btn-danger catBtn ml-3">
            {{cat.name}}
        </button>
    </div>
    <h4>Add new category</h4>
    <form:form id="addCatForm" modelAttribute="categoryDto" enctype="multipart/form-data">
        <table>
            <tr>
                <td><form:input path="name" type="text" placeholder="Category name"/></td>
                <td><form:input path="files" type="file" lang="en"/></td>
                <td>
                    <button type="button" @click="addCategory" class="btn btn-primary">Add</button>
                </td>
            </tr>
        </table>
    </form:form>
    <h4>Delete ingredient:</h4>
    <div>
        <button v-for="ing in ingredients" @click="deleteIng" class="btn btn-primary btn-danger catBtn ml-3">
            {{ing.name}}
        </button>
    </div>
    <h4>Add Ingredient:</h4>
    <form:form id="addIngForm" modelAttribute="ingredientDto" enctype="multipart/form-data">
        <table>
            <tr>
                <td><form:input path="name" type="text" placeholder="Name"/></td>
                <td><form:input path="price" type="number" placeholder="Price"/></td>
                <td>
                    <button type="button" @click="addIng" class="btn btn-primary">Add</button>
                </td>
            </tr>
        </table>
    </form:form>
    <h4>Delete Tags:</h4>
    <div>
        <button v-for="tag in tags" @click="deleteTag" class="btn btn-primary btn-danger catBtn ml-3">{{tag.name}}
        </button>
    </div>
    <h4>Add tag:</h4>
    <form:form id="addTagForm" modelAttribute="tagDto" enctype="multipart/form-data">
        <table>
            <tr>
                <td><form:input path="name" type="text" placeholder="Name"/></td>
                <td>
                    <button type="button" @click="addTag" class="btn btn-primary">Add</button>
                </td>
            </tr>
        </table>
    </form:form>
    <h4>Products:</h4>
    <div class="products">
        <div v-for="product in products" class="product">
            <img :src="getImgUrl(product.image)">
            <h5 class="name">{{product.name}}</h5>
            <h5>{{product.price}}$</h5>
            <h5>Spicy:{{product.spicy}}</h5>
            <h5>Tags:
                <div v-for="tag in product.tags" :data-name="tag.name">{{tag.name}}
                    <button @click="deleteTagFromProduct" class="btn btn-primary btn-danger">&times;</button>
                </div>
            </h5>
            <h5>Ingredients:
                <div v-for="ingredient in product.ingredients" :data-name="ingredient.name">{{ingredient.name}}
                    <button @click="deleteIngredientFromProduct" class="btn btn-primary btn-danger">&times;</button>
                </div>
            </h5>
            <button @click="editProduct" class="btn btn-primary">Edit</button>
        </div>
    </div>
    <div id="shadow"></div>
    <div id="dialog">
        <a class="close">&times</a>
        <form:form id="editProductForm" modelAttribute="productDto" enctype="multipart/form-data">
            <div class="wrapper">
                <div>
                    <form:input path="name" type="text" placeholder="Rename"></form:input>
                </div>
                <div>
                    <form:input path="price" type="number" placeholder="Change price"></form:input>
                </div>
                <div>
                    <form:input cssStyle="width: 205px" path="spicy" type="number" min="0" max="5"
                                placeholder="Change spicy"></form:input>
                </div>
                <div>
                    <label for="filesEditProduct">Change image:</label>
                </div>
                <div>
                    <form:input id="filesEditProduct" path="files" type="file"></form:input>
                </div>
                <div>Add tags:</div>
                <div>
                    <button type="button" v-for="tag in tags" @click="addTagToProduct"
                            class="btn btn-primary catBtn ml-3">{{tag.name}}
                    </button>
                </div>
                <div>Add ingredients:</div>
                <div>
                    <button type="button" v-for="ing in ingredients" @click="addIngToProduct"
                            class="btn btn-primary catBtn ml-3">{{ing.name}}
                    </button>
                </div>
                <div>
                    <button type="button" @click="submitProduct" class="btn btn-primary">Submit</button>
                    <button type="button" @click="deleteProduct" class="btn d-inline-block btn-primary btn-danger">
                        Delete product
                    </button>
                </div>
            </div>
        </form:form>
    </div>
</section>
<c:import url="_footer.jsp"></c:import>
<script src="resources/js/admin.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">