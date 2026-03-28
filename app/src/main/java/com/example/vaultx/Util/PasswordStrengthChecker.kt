package com.example.vaultx.Util

class PasswordStrengthChecker {

    enum class Strength{

        WEAK, MEDIUM, STRONG
    }
    fun checkPasswordScore(password: String): Int{

        var score = 0

        if(password.length > 8) score++
        if(password.length > 12) score++

        if(password.any{it.isUpperCase()}) score++
        if(password.any { it.isLowerCase() }) score++

        if(password.any { !it.isLetterOrDigit() }) score++

        if(password.any { it.isDigit() }) score++

        return score
    }

    fun checkStrength(password: String) : Strength{

        var score = checkPasswordScore(password)

        return when{

            score <= 2 -> Strength.WEAK
            score <= 4 -> Strength.MEDIUM
            else -> Strength.STRONG

        }
    }

}