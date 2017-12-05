(function($) {
    $(document).ready(function() {
        $("#buttonRecommendation").on("click", function() {
            console.log("Sending post request..");
            $.post("addUserBook", {
                "title": "testBook", "authorName": "j-stoff",
                "rating": "5", "numberOfRatings": "500000", "numberOfReviews": "3442",
                "genres": "none for now", "isbn": "1234567890"
            }, function (data) {
                console.log("From post: " + data);
            });
        });

    })
})(jQuery);