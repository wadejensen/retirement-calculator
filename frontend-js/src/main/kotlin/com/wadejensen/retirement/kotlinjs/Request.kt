package com.wadejensen.retirement.kotlinjs

import com.wadejensen.retirement.kotlinjs.http.Method
import kotlin.js.json

fun Request(method: Method, headers: Map<String, String>?, body: Any?): dynamic {

    val o = js("{}")
    o["method"] = method.value
    o["headers"] = headers
        ?.entries
        ?.map { it.toPair() }
        ?.toTypedArray()
        ?.let { json(*it) }

    console.log("before body encode")

    o["body"] = body?.let { JSON.stringify(body) }

    console.log("after body encode")

    return o
}
