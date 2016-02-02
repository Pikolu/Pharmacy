function updateResultList(input, parameter) {
    var names = "";

    var checkboxes = $('.name-category-checkbox:checked');
    console.log(checkboxes)
    $.each(checkboxes, function (key, value) {
        var box = $(this);
        names += box.val() + ':';
        console.log(names);
        //console.log(box)
        //console.log("key= " + key + " value= " + value);
    });

    var field = $(input);
    var pharmacyName = field.val();
    console.log(field)
    $.ajax({
        url: '/suche?parameter=' + parameter + '&pharmacyName=' + names,
        success: function (data) {
            $('#result').html(data);
        }
    });
}