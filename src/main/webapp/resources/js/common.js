$(function () {
    var baseUrl = window.location.origin + '/webstore_war'
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('.addToCart').on('click',function (e) {
        e.preventDefault()
        var product = $(e.target).parents('.productItem').find('.productName').text()

        $.ajax({
            url: baseUrl + '/addToCart/' + product,
            type: 'PUT',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                console.log(data)
            },
            error: function (jqXHR, status, errorThrown) {
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    })
})