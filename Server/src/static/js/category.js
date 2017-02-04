$(document).ready(function(){

    var server = "http://127.0.0.1:4480";


    /*****************************************************************
     *                          edit button
     ****************************************************************/
    $(".edit-btn").click(function () {

        var btnID = $(this).attr('id');
        var ID = btnID.slice(4);//"74f759ef751a464c83fe22ab7a853f69"

        var name = $('#name'+ID).val();
        var start =parseInt($("#start"+ID).val());
        var end = parseInt($("#end"+ID).val());
        var modelType = $("#model-type"+ID).val();
        $.ajax({
            type: "PUT",
            dataType: 'json',
            url: server+'/admin',
            contentType: "application/json",
            data:JSON.stringify({
                "id": ID,
                "name": name,
                "start": start,
                "end": end,
                "model_type": modelType
            })

        })
            .done(function( data ) {


            })
            .fail( function(xhr, textStatus, errorThrown) {

            });


    });


});
