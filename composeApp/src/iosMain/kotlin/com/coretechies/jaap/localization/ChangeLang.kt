package com.coretechies.jaap.localization


actual fun changeLang(lang: String) {
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang),"AppleLanguages")
}