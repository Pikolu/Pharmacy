function updateResultList(parameter, page) {
    var names = "";

    var checkboxes = $('.name-category-checkbox:checked');
    $.each(checkboxes, function (key, value) {
        var box = $(this);
        names += box.val() + ':';
    });

    $.ajax({
        url: '/suche?parameter=' + parameter + '&pharmacyName=' + names + '&page=' + page,
        contentType: "application/json",
        success: function (data) {
            var content = $(data);
            var box = content.find('.box-product').html();
            $('.box-product').html(box);
        }
    });
}

function closeInfoDialog() {
    jQuery('#info-dialog').hide();
}