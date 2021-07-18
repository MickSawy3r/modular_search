package com.ticketswap.network

class EmptyResponseException constructor(url: String) : UnsuccessfulRequest(url, "Empty Response")
