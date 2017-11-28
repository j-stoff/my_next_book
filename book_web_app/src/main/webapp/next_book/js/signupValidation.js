(function($) {
    $(document).ready(function() {
        //$.validator.setDefaults({"submitHandler": function() {alert("Form submitted properly")}});
        $("#signupForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },
                emailConfirmation: {
                    required: true,
                    email: true,
                    equalTo: "#email"
                },
                username: {
                    required: true,
                    minlength: 3,
                    maxlength: 24
                },
                password: {
                    required: true,
                    minlength: 5
                },
                passwordConfirmation: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                }
            },
            messages: {
                emailConfirmation: {
                    equalTo: "Emails must match"
                },
                passwordConfirmation: {
                    equalTo: "Passwords must match"
                }
            }
        });
    })
})(jQuery);