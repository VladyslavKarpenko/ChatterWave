package com.test.chatterwave.beta.utils

data class PasswordValidation(
    val minCharacterPassed: Boolean = false,
    val oneLowerCasePassed: Boolean = false,
    val oneUpperCasePassed: Boolean = false,
    val oneDigitPassed: Boolean = false,
    val oneSpecialPassed: Boolean = false,
    val empty: Boolean = false,
    val fullRegexPassed: Boolean = false,
    val onlyLatin: Boolean = false
)
