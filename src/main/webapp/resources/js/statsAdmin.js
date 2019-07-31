var baseUrl = window.location.origin + '/webstore_war'
var vueData = {
    orders: []
}

function getOrders() {
    $.ajax({
        url: baseUrl + '/admin/getOrders',
        type: 'get',
        success: function (data) {
            vueData.orders = data
        },
        error: function (jqXHR, status, errorThrown) {
            console.log('ERROR: ' + jqXHR.responseText)
        }
    })
}
getOrders()

var vue = new Vue({
    el: '#app',
    data: vueData,
    methods: {
        changeStatus: function (e) {
            console.log(e.target.value)
        }
    }
})