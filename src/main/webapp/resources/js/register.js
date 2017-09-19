$(document).ready(function() {
    $('#userForm').formValidation({
        message: 'This value is not valid',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	firstname: {
                validators: {
                    notEmpty: {
                        message: 'The first name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 3,
                        max: 30,
                        message: 'The first name must be more than 3 and less than 30 characters long'
                    }
                }
            },
            lastname: {
                validators: {
                    notEmpty: {
                        message: 'The last name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 3,
                        max: 30,
                        message: 'The last name must be more than 3 and less than 30 characters long'
                    }
                }
            },
            mobileNumber: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    phone: {
                        country: 'NG'
                    }
                }
            },
            emailaddress: {
                message: 'The email address is not valid',
                validators: {
                    notEmpty: {
                    	message: 'The email address is required and can\'t be empty'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and can\'t be empty'
                    },
                    different: {
                        field: 'emailaddress',
                        message: 'The password can\'t be the same as emailaddress'
                    },
                    callback: {
                        callback: function(value, validator) {
                            // Check the password strength
                            if (value.length < 6) {
                                return {
                                    valid: false,
                                    message: 'The password must be more than 6 characters'
                                }
                            }

                            if (value === value.toLowerCase()) {
                                return {
                                    valid: false,
                                    message: 'The password must contain at least one upper case character'
                                }
                            }
                            if (value === value.toUpperCase()) {
                                return {
                                    valid: false,
                                    message: 'The password must contain at least one lower case character'
                                }
                            }
                            if (value.search(/[0-9]/) < 0) {
                                return {
                                    valid: false,
                                    message: 'The password must contain at least one digit'
                                }
                            }

                            return true;
                        }
                    }
                }
            }
        }
    });
});