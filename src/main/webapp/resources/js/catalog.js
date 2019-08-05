$(function () {
    var baseUrl = window.location.origin + '/webstore_war'
    var category = document.location.pathname.match(/.*\/(.*)/)[1]
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var vueData = {
        products: [],
        selectedTags: []
    }

    function getProducts(){
        $.ajax({
            url: baseUrl + '/getProducts/'+category,
            type: 'GET',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                console.log(data)
                vueData.products = data
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
            getImgUrl: function (url) {
                return baseUrl + url
            }
        }
    })
})