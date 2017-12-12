$(document).ready(function() {
    $("#userTable tr").on("click", ".deleteUser", function() {
        var row = $(this).parent().parent();
        var id = $(this).parent().parent().attr("id");
        $.post("adminDeleteUser", {"id": id}, function(data) {
            if (data.length > 0) {
                row.remove();
            }
        });
    })
});