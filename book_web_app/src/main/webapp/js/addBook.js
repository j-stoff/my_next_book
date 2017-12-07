(function($) {
    $(document).ready(function() {
        $("#bookResults").on("click", ".addToUserBooks", function() {
            console.log("Sending post request..");
            var id = $(this).parent().parent().attr("id");
            $.post("addUserBook", {"id": id}, function (data) {
                console.log("From post: " + data);
            });
            $(this).remove();

        });

    })
})(jQuery);