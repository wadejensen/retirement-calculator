package com.wadejensen.retirement

import com.wadejensen.xplat.SharedClass
import com.wadejensen.retirement.flatmates.FlatmatesClient
import com.wadejensen.retirement.flatmates.model.RoomType
import com.wadejensen.retirement.flatmates.routes.autocompleteHandler
import com.wadejensen.retirement.flatmates.routes.mapMarkersHandler
import com.wadejensen.retirement.handlers.*
import com.wadejensen.retirement.kotlinjs.require
import express.Application
import kotlinx.coroutines.experimental.async
import org.funktionale.*
import kotlinx.serialization.json.JSON as Json

external val process: dynamic
external val __dirname: dynamic

/**
 * main function for JavaScript
 */
fun main(vararg args: String) {
    //nothing here
}

/**
 * We start this function from atlas.js"
 */
fun start() {
    // Create Node.js Express app and handle boilerplate
    val app = Application()

    // Show off that I can share code between client and server
    val shared = SharedClass(com.wadejensen.xplat.Console(), com.wadejensen.xplat.Math())
    println(shared.givePrimes(2))

    async {
        val (flatmatesClient, nextClient) = initApiClients()

        setupRoutes(app, shared, flatmatesClient)
        serveStaticResources(app, staticResourcesPath = "../../frontend-js/src/main/web")
        startHttpServer(app, defaultPort = 3000)

        println("Kotlin - Node.js webserver ready.")

        // cutting edge
        val listingsOrErr = flatmatesClient.getListings(
            lat1 = -33.878453691548835,
            lon1 = 151.16001704415282,
            lat2 = -33.90481527152859,
            lon2 = 151.2626705475708,
            roomType = RoomType.PRIVATE_ROOM,
            minPrice = 100.0,
            maxPrice = 2000.0)

        println("Num listings found")
        console.dir(listingsOrErr.success().size)
        println(listingsOrErr.success()[0].price)
        console.dir(listingsOrErr.success()[0])

        val suggestions = flatmatesClient.autocomplete("redfern")
        console.dir(suggestions)
    }
}

fun setupRoutes(app: Application, shared: SharedClass, flatmatesClient: FlatmatesClient): Unit {
    println("Configuring express middleware router...")

    app.enableJsonBodyParser()

    app.post("/autocompleteHandler/:userInput") {
        req, res -> autocompleteHandler(flatmatesClient, req, res) }

    app.get("/verge") { req, res -> res.send("hiiiii") }
    app.post("/posty") { req, res -> res.send(JSON.stringify(req.body)) }
    app.post("/getMapMarkers") { req, res -> mapMarkersHandler(flatmatesClient, req, res) }
    app.get("/primes") { req, res -> printPlatformSpecificPrimes(req, res, shared) }
    app.get("/async-get") { req, res -> asyncHttpGet(req, res) }
    app.get("/async-post") { req, res -> asyncHttpPost(req, res) }
    app.get("/parse-json") { req, res -> parseJson(req, res) }
    app.get("/promise-get") { req, res -> httpGetPromise(req, res) }

    println("Express middleware configuration successful.")
}

fun startHttpServer(app: Application, defaultPort: Int): Unit {
    println("Launching HTTP server...")
    // Attempt risky parsing of port environment variable
    val portOrErr = Try { process.env.PORT.toString().toInt() }

    val port = if (portOrErr.isSuccess()) {
        println("Using provided port: ${portOrErr.success()} from environment variable.")
        portOrErr.success()
    }
    else {
        defaultPort
    }

    println("Listening server on port ${port}.")
    app.listen(port)
    println("HTTP server started.")
}

/**
 * Mounts static web files for serving to browser clients
 * @param Initialised Express application
 * @param staticResourcesPath Relative filepath to static web resources
 */
fun serveStaticResources(app: Application, staticResourcesPath: String) {
    val path = require("path")
    val staticWebContentPath = path.join(__dirname, staticResourcesPath) as String
    println("Mounting static routes at root from: ${staticWebContentPath}...")
    app.serveStaticContent(staticWebContentPath)
    println("Serving content from: $staticWebContentPath")
}

suspend fun initApiClients(): Pair<FlatmatesClient, String> {
    println("Initialising API clients...")

    println("Initialising flatmates.com.au...")
    val flatmatesClientOrErr: Try<FlatmatesClient> = FlatmatesClient.create()

    if (flatmatesClientOrErr.isFailure()) {
        throw RuntimeException("""| Failed to create flatmates.com.au API client for reason:
                   | ${flatmatesClientOrErr.failure().message}
                   | cause:
                   | ${flatmatesClientOrErr.failure().cause}""".trimMargin())
    }
    println("Created flatmates.com.au client.")
    println(flatmatesClientOrErr)

    println("API clients' initialisation successful.")
    return Pair(flatmatesClientOrErr.success(), "Next client")
}
