package com.test.chatterwave.beta.utils

// uppercase, 1 lowercase, 1 digit and 1 special character
val passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,24}\$".toRegex()
val passwordMinLengthRegex = Regex("^.{8,}$")
val passwordUpperCaseRegex = Regex(".*[A-Z].*")
val passwordLowerCaseRegex = Regex(".*[a-z].*")
val passwordDigitalRegex = Regex(".*\\d.*")
val passwordSpecialCharacterRegex = Regex(".*?[~!@#\$%^&*()_+\\-=[{]}\\\\|;:'\",<.>/?].*")
val passwordOnlyLatinRegex = Regex(".*[А-ЯЄІЇа-яєіїёЁ].*")

val mailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
val fullNameRegex = """^(?=.{2,35}$)[a-zA-Zа-яА-Я"'-]+(?:\s[a-zA-Zа-яА-Я"'-]+)?$""".toRegex()
val nicknameRegex = """^[a-zA-Z0-9_.'-]{8,20}$""".toRegex()

val cityRegex = """^(?=.{2,50}$)[a-zA-Zа-яА-Я"',-]+(?:\s[a-zA-Zа-яА-Я"'-]+)?$""".toRegex()

val hashtagRegex = Regex("(?<=\\s|^)#[\\p{L}0-9_]+")
