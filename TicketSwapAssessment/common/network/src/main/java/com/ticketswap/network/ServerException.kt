package com.ticketswap.network

class ServerException constructor(private val url: String) :
    UnsuccessfulRequest("Server Error at url: $url", null)
