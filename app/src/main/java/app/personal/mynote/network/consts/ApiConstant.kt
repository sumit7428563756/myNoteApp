package app.personal.mynote.network.consts

object ApiConstant {

    //base url
    const val BASE_URL = "https://authentication-698a.onrender.com/"

    //authentication endpoints
    const val SEND_OTP = "send-otp"

    const val VERIFY_OTP = "verify-otp"

    const val SIGNUP = "signUp"

    const val LOGIN = "login"

    //password endpoint

    const val FORGOT_OTP = "forgot-request-otp"

    const val FORGOT_PASSWORD = "forgotPassword"

    //profile endpoint

    const val GET_PROFILE = "getProfile"

    const val EDIT_PROFILE = "edit-profile"

    //notes endpoint

    const val CREATE_NOTE = "create-note"

    const val GET_NOTE = "getNotes"

    const val UPDATE_NOTE = "updateNote"

    const val DELETE_NOTE = "deleteNote"

}