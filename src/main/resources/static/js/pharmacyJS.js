function updateResultList(input, parameter) {
    var names = "";

    var checkboxes = $('.name-category-checkbox:checked');
    $.each(checkboxes, function (key, value) {
        var box = $(this);
        names += box.val() + ':';
    });

    var field = $(input);
    var pharmacyName = field.val();
    console.log(field)
    var htmlContent = "";
    $.ajax({
        url: '/suche?parameter=' + parameter + '&pharmacyName=' + names,
        contentType: "application/json",
        success: function (data) {
            var content = $(data);
            var box = content.find('.box-product').html();
            $('.box-product').html(box);
        }
    });
}