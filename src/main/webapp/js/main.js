$(document).ready(function () {
    $.getJSON('rest/logos/all',
    function (json) {
        //console.log(json);

        var tr;
        for (var i = 0; i < json.length; i++) {
            tr = $('<tr/>');
            tr.append("<td>" + json[i].brokerName + "</td>");
            tr.append("<td> <img src='" + json[i].logoUrl + "' style=\"max-height: 66px;max-width: 330px\"></td>");
            tr.append("<td> <img src='" + json[i].profileImageUrl + "' style=\"max-height: 150px;max-width: 150px\"></td>");
                tr.append("<td>" + json[i].modifiedByDate + "</td>");
            $('tbody').append(tr);
        }
    });

    $.getJSON('rest/logos/counts',
        function (json) {
        $('#logo_count').append(json.logo_active_count);
        $('#profile_count').append(json.profile_active_count);
    });
});