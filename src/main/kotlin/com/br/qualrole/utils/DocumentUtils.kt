package com.br.qualrole.utils

import com.br.qualrole.exception.InvalidDocumentException

fun String.formatToCPForCNPJ(): String {
    val digits = this.replace(Regex("[^0-9]"), "")

    if (digits.length == 11 && validateCPF(digits)) {
        return digits
    }

    if (digits.length == 14 && validaCNPJ(digits)) {
        return digits
    }

    throw InvalidDocumentException("Invalid document!")
}


fun validateCPF(document: String): Boolean {
    if (document.length != 11) return false

    val digits = document.toCharArray().map { it.digitToInt() }.toIntArray()

    var sum = 0
    for (i in 0 until 9) {
        sum += digits[i] * (10 - i)
    }

    var remainder = sum % 11
    val firstDigitCalculated = if (remainder == 0 || remainder == 1) 0 else 11 - remainder

    sum = 0
    for (i in 0 until 10) {
        sum += digits[i] * (11 - i)
    }

    remainder = sum % 11
    val secondDigitCalculated = if (remainder == 0 || remainder == 1) 0 else 11 - remainder

    return firstDigitCalculated == digits[9] && secondDigitCalculated == digits[10]
}

fun validaCNPJ(document: String): Boolean {
    if (document.length != 14) return false

    val digits = document.toCharArray().map { it.digitToInt() }.toIntArray()

    var sum = 0
    val weight = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    for (i in 0 until 12) {
        sum += digits[i] * weight[i]
    }

    var remainder = sum % 11
    if (remainder < 2) {
        if (digits[12] != 0) return false
    } else {
        if (digits[12] != 11 - remainder) return false
    }

    sum = 0
    val weight2 = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    for (i in 0 until 13) {
        sum += digits[i] * weight2[i]
    }
    remainder = sum % 11
    if (remainder < 2) {
        if (digits[13] != 0) return false
    } else {
        if (digits[13] != 11 - remainder) return false
    }

    return true
}
