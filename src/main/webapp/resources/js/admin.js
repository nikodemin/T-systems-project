$(function () {

    var baseUrl = window.location.origin + '/webstore_war'
    var currentCategory
    var currentProduct

    var vueData = {
        categories: [],
        products: [],
        ingredients: [],
        tags: []
    }

    function getCategories() {
        $.ajax({
            url: baseUrl + '/admin/getCategories',
            type: 'get',
            success: function (data) {
                vueData.categories = data
            },
            error: function (jqXHR, status, errorThrown) {
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }

    getCategories()

    function getIngredients() {
        $.ajax({
            url: baseUrl + '/admin/getIngredients',
            type: 'get',
            success: function (data) {
                vueData.ingredients = data
            },
            error: function (jqXHR, status, errorThrown) {
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }

    getIngredients()

    function getTags() {
        $.ajax({
            url: baseUrl + '/admin/getTags',
            type: 'get',
            success: function (data) {
                vueData.tags = data
            },
            error: function (jqXHR, status, errorThrown) {
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }

    getTags()

    function getProducts(category) {
        category = category || currentCategory
        $.ajax({
            url: baseUrl + '/admin/getProducts/' + category,
            type: 'get',
            success: function (data) {
                vueData.products = data
                currentCategory = category
            },
            error: function (jqXHR, status, errorThrown) {
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }

    var app = new Vue({
        el: '#app',
        data: vueData,
        methods: {
            getImgUrl: function (url) {
                return baseUrl + '/' + url
            },
            addCategory: function (e) {
                e.preventDefault()
                var data = new FormData($('#addCatForm')[0])

                $.ajax({
                    url: baseUrl + '/admin/addCategory',
                    type: 'POST',
                    enctype: 'multipart/form-data',
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function (respond, status, jqXHR) {

                        if (typeof respond.error === 'undefined') {
                            console.log("SUCCESS " + respond, jqXHR.responseText)
                            vueData.categories.push({name: data.get('name')})
                        } else {
                            console.log('ERROR: ' + respond.error, jqXHR.responseText)
                        }
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },

            getProductsByClick: function (e) {
                getProducts(e.target.innerText)
            },
            deleteCat: function (e) {
                $.ajax({
                    url: baseUrl + '/admin/deleteCategory/' + e.target.innerText,
                    type: 'DELETE',
                    success: function (data) {
                        vueData.products = data
                        if (e.target.innerText == currentCategory)
                            vueData.products = []
                        getCategories()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            deleteIng: function (e) {
                $.ajax({
                    url: baseUrl + '/admin/deleteIngredient/' + e.target.innerText,
                    type: 'DELETE',
                    success: function (data) {
                        getProducts()
                        getIngredients()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            addIng: function (e) {
                e.preventDefault()
                var data = new FormData($('#addIngForm')[0])

                $.ajax({
                    url: baseUrl + '/admin/addIngredient',
                    type: 'POST',
                    enctype: 'multipart/form-data',
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function (respond, status, jqXHR) {

                        if (typeof respond.error === 'undefined') {
                            console.log("SUCCESS " + respond, jqXHR.responseText)
                            vueData.ingredients.push({name: data.get('name')})
                        } else {
                            console.log('ERROR: ' + respond.error, jqXHR.responseText)
                        }
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            deleteTag: function (e) {
                $.ajax({
                    url: baseUrl + '/admin/deleteTag/' + e.target.innerText,
                    type: 'DELETE',
                    success: function (data) {
                        getProducts()
                        getTags()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            addTag: function (e) {
                e.preventDefault()
                var data = new FormData($('#addTagForm')[0])

                $.ajax({
                    url: baseUrl + '/admin/addTag',
                    type: 'POST',
                    enctype: 'multipart/form-data',
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function (respond, status, jqXHR) {

                        if (typeof respond.error === 'undefined') {
                            console.log("SUCCESS " + respond, jqXHR.responseText)
                            vueData.tags.push({name: data.get('name')})
                        } else {
                            console.log('ERROR: ' + respond.error, jqXHR.responseText)
                        }
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            deleteTagFromProduct: function (e) {
                var tagName = $(e.target).parent().attr('data-name').trim()
                var productName = $(e.target).parents('div.product')
                    .find('h5.name').text().trim()
                $.ajax({
                    url: baseUrl + '/admin/deleteTag/' + currentCategory + '/' + productName + '/' + tagName,
                    type: 'DELETE',
                    success: function (data) {
                        getProducts()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            deleteIngredientFromProduct: function (e) {
                var IngName = $(e.target).parent().attr('data-name').trim()
                var productName = $(e.target).parents('div.product')
                    .find('h5.name').text().trim()
                $.ajax({
                    url: baseUrl + '/admin/deleteIngredient/' + currentCategory + '/' + productName + '/' + IngName,
                    type: 'DELETE',
                    success: function (data) {
                        getProducts()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            editProduct: function (e) {
                currentProduct = $(e.target).siblings('h5.name').text()
                $('#shadow').show()
                $('#dialog').show()
                $('a.close').one('click', function () {
                    $('#shadow').hide()
                    $('#dialog').hide()
                })
            },
            submitProduct: function (e) {
                var data = new FormData($('#editProductForm')[0])

                $.ajax({
                    url: baseUrl + '/admin/updateProduct/' + currentCategory + '/' + currentProduct,
                    type: 'POST',
                    enctype: 'multipart/form-data',
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function (respond, status, jqXHR) {

                        if (typeof respond.error === 'undefined') {
                            console.log("SUCCESS " + respond, jqXHR.responseText)
                            getProducts()
                        } else {
                            console.log('ERROR: ' + respond.error, jqXHR.responseText)
                        }
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            deleteProduct: function (e) {

            },
            addTagToProduct: function (e) {
                $.ajax({
                    url: baseUrl + '/admin/addTagToProduct/' + currentCategory + '/' +
                        currentProduct + '/' + $(e.target).text(),
                    type: 'PUT',
                    success: function (data) {
                        getProducts()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            addIngToProduct: function (e) {
                $.ajax({
                    url: baseUrl + '/admin/addIngToProduct/' + currentCategory + '/' +
                        currentProduct + '/' + $(e.target).text(),
                    type: 'PUT',
                    success: function (data) {
                        getProducts()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            }
        }
    })
})