var baseUrl = window.location.origin + '/webstore_war'
var vueData = {
    orders: [],
    total: {},
    topClients: []
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
    $.ajax({
        url: baseUrl + '/admin/getTotalGain',
        type: 'get',
        success: function (data) {
            vueData.total = data
        },
        error: function (jqXHR, status, errorThrown) {
            console.log('ERROR: ' + jqXHR.responseText)
        }
    })
}
getOrders()

function getTopClients() {
    $.ajax({
        url: baseUrl + '/admin/getTopClients',
        type: 'get',
        success: function (data) {
            vueData.topClients = data
        },
        error: function (jqXHR, status, errorThrown) {
            console.log('ERROR: ' + jqXHR.responseText)
        }
    })
}
getTopClients()

var vue = new Vue({
    el: '#app',
    data: vueData,
    methods: {
        changeStatus: function (e) {
            var newStatus = e.target.value
            var id = $(e.target).parents('tr').attr('data-id')
            $.ajax({
                url: baseUrl + '/admin/changeOrderStatus/'+id+'/'+newStatus,
                type: 'PUT',
                success: function (data) {
                    console.log(data)
                    getOrders()
                },
                error: function (jqXHR, status, errorThrown) {
                    console.log('ERROR: ' + jqXHR.responseText)
                }
            })
        }
    }
})