package com.example.studysynchub.Login_Reg

class UserLoginModel {

    var name: String? = null
    var number: String? = null
    var email: String? = null
    var password: String? = null
    var uid: String? = null


    constructor(name: String?, number: String?, email: String?, password: String?, uid: String) {
        this.name = name
        this.number = number
        this.email = email
        this.password = password
        this.uid = uid
    }
}
