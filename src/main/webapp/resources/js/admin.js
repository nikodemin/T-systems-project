$(function () {

    var baseUrl = window.location.origin+'/webstore_war';
    var currentCategory;
    var vueData = {
        categories: [],
        products: []
    }

    function getCategories(){
        $.ajax({
            url: baseUrl+'/admin/getCategories',
            type: 'get',
            success: function (data) {
                console.log(data)
                console.log(data[0])
                vueData.categories = data
            },
            error: function (jqXHR, status, errorThrown ){
                console.log( 'ERROR: ' + jqXHR.responseText);
            }
        })
    }
    getCategories();

    var app = new Vue({
        el: '#app',
        data: vueData,
        methods: {
            getImgUrl: function(url){
                return baseUrl+'/'+url
            },
            addCategory: function (e) {
                e.preventDefault();
                var data = new FormData($('#addCatForm')[0]);

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
                            vueData.categories.push({name:data.get('name')});
                        } else {
                            console.log('ERROR: ' + respond.error, jqXHR.responseText);
                        }
                    },
                    error: function (jqXHR, status, errorThrown) {
                        console.log('ERROR: ' + jqXHR.responseText);
                    }
                })
            },
            
            getProducts: function (e) {
                $.ajax({
                    url: baseUrl+'/admin/getProducts/'+e.target.innerText,
                    type: 'get',
                    success: function (data) {
                        vueData.products = data
                        currentCategory = e.target.innerText;
                    },
                    error: function (jqXHR, status, errorThrown ){
                        console.log( 'ERROR: ' + jqXHR.responseText);
                    }
                })
            },
            deleteCat: function (e) {
                $.ajax({
                    url: baseUrl+'/admin/deleteCategory/'+e.target.innerText,
                    type: 'post',
                    success: function (data) {
                        vueData.products = data
                        if (e.target.innerText == currentCategory)
                            vueData.products=[]
                        getCategories()
                    },
                    error: function (jqXHR, status, errorThrown ){
                        console.log( 'ERROR: ' + jqXHR.responseText);
                    }
                })
            }
        }
    })
})