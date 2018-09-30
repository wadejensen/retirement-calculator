/**
 * Adapted from Raphael Stäbler's Pappel Node.js framework for Kotlin
 * https://github.com/blazer82/pappel-framework
 */

package express.http

import express.JSONUtils
import kotlin.js.Json

class ExpressRequest(external private val req: dynamic) {

    val baseURL: String?
    val body: Json
    val cookies: Json?
    val hostname: String
    val ip: String
    val ips: Array<String>?
    val method: String
    val parameters: Map<String, String>?
    val path: String
    val protocol: String
    val query: Json
    // TODO: Add route

    init {
        baseURL = req.baseUrl as? String
        body = req.body as Json
        console.dir(jsTypeOf(req.cookies))
        cookies = req.cookies as? Json
        hostname = req.hostname as String
        ip = req.ip as String
        ips = req.ips as? Array<String>
        method = req.method
        parameters = JSONUtils.retrieveMap(req.params) as Map<String, String>?
        path = req.path as String
        protocol = req.protocol
        console.log(jsTypeOf(req.query))
        console.dir(req.query)
        query = req.query as Json
    }
}
