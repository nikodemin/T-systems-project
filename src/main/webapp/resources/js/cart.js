$(function () {
    var baseUrl = window.location.origin + '/webstore_war'
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var vueData = {
        products: [],
        total: 0,
        payment: 'cash',
        cardError: false,
        delivery: 'courier',
        addressError: false,
        cardEmpty: false
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
            },
            redirectToPayment: function () {
                window.location = baseUrl + '/payment'
            },
            submitOrder: function () {
                var deliveryForm = new FormData($('#deliveryForm')[0])
                var cardForm = new FormData($('#cardForm')[0])

                vueData.addressError = false
                vueData.cardError = false
                vueData.cardEmpty = false

                $.ajax({
                    url: baseUrl + '/isCartEmpty',
                    type: 'GET',
                    async: false,
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        vueData.cardEmpty = data
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })

                if (vueData.cardEmpty)
                    return

                if (vueData.delivery == 'courier')
                    $.ajax({
                        url: baseUrl + '/setAddress',
                        type: 'POST',
                        enctype: 'multipart/form-data',
                        data: deliveryForm,
                        cache: false,
                        processData: false,
                        contentType: false,
                        async: false,
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function(data){
                            vueData.addressError = data
                        },
                        error: function (jqXHR, status, errorThrown) {
                            console.log('ERROR: ' + jqXHR.responseText)
                        }
                    })
                if (vueData.payment == 'card')
                    $.ajax({
                        url: baseUrl + '/setCard',
                        type: 'POST',
                        enctype: 'multipart/form-data',
                        data: cardForm,
                        cache: false,
                        processData: false,
                        contentType: false,
                        async: false,
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function(data){
                            vueData.cardError = data
                        },
                        error: function (jqXHR, status, errorThrown) {
                            console.log('ERROR: ' + jqXHR.responseText)
                        }
                    })
                if (vueData.cardError === false && vueData.addressError === false)
                    $.ajax({
                        url: baseUrl + '/submitOrder',
                        type: 'POST',
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function(data){
                            window.location = baseUrl + '/confirmation'
                        },
                        error: function (jqXHR, status, errorThrown) {
                            console.log('ERROR: ' + jqXHR.responseText)
                        }
                    })
            }
        }
    })
})