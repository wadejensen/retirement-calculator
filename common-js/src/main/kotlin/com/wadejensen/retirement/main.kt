package com.wadejensen.retirement

import com.wadejensen.example.SharedClass

/**
 * main function for JavaScript
 */
fun main(vararg args: String) {
    //nothing here, it's executed before DOM is ready
}

/**
 * We start this function from <button onClick="
 */
fun start() {
    val shared = SharedClass(com.wadejensen.example.Console(), com.wadejensen.example.Math())
    shared.platform = "JavaScript"
    shared.printMe()
    shared.printPrimes(1000)
}



