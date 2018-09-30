package com.wadejensen.retirement.flatmates.routes

import com.wadejensen.retirement.flatmates.FlatmatesClient
import com.wadejensen.retirement.flatmates.model.AutocompleteInput
import com.wadejensen.retirement.flatmates.model.AutocompleteSuggestion
import express.http.ExpressRequest
import express.http.ExpressResponse
import kotlinx.coroutines.experimental.async
import org.funktionale.isSuccess
import org.funktionale.success
import kotlin.js.Json

/**
 *  Express route handler listening on `https://hostname:port/async-post`.
 *  Makes a HTTP POST request asyncronously using the w3c window.fetch API.
 *  The fetch API is called from a typed wrapper which accepts Kotlin data class objects
 *  as the message body, a Map<String, String> for the headers, and an enum for the request type.
 *  Uses a Kotlin coroutine wrapper around native JS `Promise`s, to mimic the ES7 async - await pattern.
 */
fun mapMarkersHandler(flatmatesClient: FlatmatesClient, req: ExpressRequest, res: ExpressResponse) {
    println("mapMarkersHandler")

    console.dir(req.body)
    println(req.method)

    val bod = req.body
    println(bod)
    console.dir(bod)

    res.sendJSON(bod)
}

fun autocompleteHandler(flatmatesClient: FlatmatesClient, req: ExpressRequest, res: ExpressResponse) {
    println("autocompleteHandler")

    val autocompleteInput = JSON.parse<AutocompleteInput>(JSON.stringify(req.body))
    async {

        val userInput = req.parameters?.get("userInput")!!

        val suggestionsOrErr = flatmatesClient.autocomplete(userInput)
        if (suggestionsOrErr.isSuccess()) {
            val data = suggestionsOrErr.success()

            //JSON.stringify(data)

            // suggestionsOrErr.success().asDynamic()

            res.sendJSON(data as Json)
        }
        else {
            res.sendJSON(arrayOf<AutocompleteSuggestion>() as Json)
        }
    }
}
