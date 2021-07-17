package com.ticketswap.network

class UnsuccessfulRequest constructor(private val error: String) : Exception() {
    override fun toString(): String {
        return "Bad Response: $error"
    }
}
