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
        url: '/live_suche?parameter=' + parameter + '&pharmacyName=' + names,
        contentType: "application/json",
        success: function (data) {
            console.log(data.content);
            $.each(data.content, function (key, value) {
                var v = $(this)[0];
                var div = '<div>' + v.name + '</div>';
                htmlContent = htmlContent + div;
            });
            $('.box-product').html(htmlContent);
        }
    });
}