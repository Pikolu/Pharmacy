function updateResultList() {
    var test = "test";
    $.ajax({
        url : '/suche?parameter=Test&pharmacyName=test',
        success : function(data) {
            $('#result').html(data);
        }
    });
}