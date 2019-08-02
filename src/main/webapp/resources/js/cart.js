$(function () {
    var baseUrl = window.location.origin + '/webstore_war'
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var vueData = {
        products: [],
        total: 0
    }

    function getProducts(){
        $.ajax({
            url: baseUrl + '/getCartProducts/',
            type: 'GET',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                console.log(data)
                vueData.products = data
                vueData.total = 0
                vueData.products.forEach(function (tuple) {
                    vueData.total += tuple.key.price*tuple.value
                })
            },
            error: function (jqXHR, status, errorThrown) {
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }
    getProducts()

    var vue = new Vue({
        el: '#app',
        data: vueData,
        methods: {
            addProduct: function (e) {
                var name = $(e.target).parents('tr').find('.productName').text()
                $.ajax({
                    url: baseUrl + '/addToCart/'+name,
                    type: 'PUT',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        console.log(data)
                        getProducts()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            removeProduct: function (e) {
                var name = $(e.target).parents('tr').find('.productName').text()
                $.ajax({
                    url: baseUrl + '/removeFromCart/'+name,
                    type: 'PUT',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        console.log(data)
                        getProducts()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            deleteProduct:function (e) {
                var name = $(e.target).parents('tr').find('.productName').text()
                $.ajax({
                    url: baseUrl + '/deleteAllFromCart/'+name,
                    type: 'DELETE',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        console.log(data)
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