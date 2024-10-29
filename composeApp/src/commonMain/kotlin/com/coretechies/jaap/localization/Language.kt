package com.coretechies.jaap.localization

sealed class Language(val isoFormat : String) {
    data object English : Language("en")
    data object Hindi : Language("hi")
}