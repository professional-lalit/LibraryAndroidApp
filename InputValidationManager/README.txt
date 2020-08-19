* Problem Statement:
    Various screens in android application has to get data from user. This data is not processed
or validated. This module address this problem of validating the user input. These screens
include: Signup, Login, Forgot Password, Change Password, etc.

* Usage:
    The client program needs to use 'ValidationManager' class. The module is totally customizable.
ValidationManager returns InputValidator, an abstract class that is base for every Validator.
Based on the 'ValidationScheme'(from ValidatorFactory) set the instance of either of Validator is
initiated.
    Here, the Builder pattern is used in order to construct the required Validator. The client
program just needs to do following;

*********************************** SAMPLE CODE ****************************************************

private val mValidationManager = ValidationManager();
//Instead of directly returning 'SignupValidator', InputValidator will be returned.
val validator = mValidationManager.getSignupValidator(
                                           name: String,
                                           email: String,
                                           password: String,
                                           confirmPassword: String
                                       )
//Validation Code is returned, based on this value the respective message should be shown.
val code = validator.isValid();

*********************************** SAMPLE CODE ****************************************************

* Modifications:
The code is totally modifiable, you need to look into following classes;
1. InputValidator
2. ValidationFactory
You can either add more Validator classes or update/change existing as per your needs.
Just remember to also update/change the enums 'ValidationScheme' and 'ValidationCode'
