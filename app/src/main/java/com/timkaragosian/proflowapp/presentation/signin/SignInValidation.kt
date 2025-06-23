package com.timkaragosian.proflowapp.presentation.signin

private val USERNAME_REGEX = Regex(".{5,}")
private val PASSWORD_REGEX = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$")

fun validateUsername(name:String): Boolean = USERNAME_REGEX.matches(name)
fun validatePassword(pass:String): Boolean = PASSWORD_REGEX.matches(pass)
