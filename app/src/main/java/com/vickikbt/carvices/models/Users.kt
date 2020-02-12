package com.vickikbt.carvices.models

class Users(
    val Username: String,
    val Email: String,
    val PhoneNumber: String,
    val UID: String,
    val ProfileImageUrl: String
) {
    constructor() : this("", "", "", "", "")
}