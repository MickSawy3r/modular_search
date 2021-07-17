package com.ticketswap.network

class BadRequestException constructor(
    url: String,
    request: String
) : UnsuccessfulRequest(url, "bad request $request")
