package me.dgahn.application.service

import me.dgahn.infrastructure.client.CommunityClient
import org.springframework.stereotype.Service

@Service
class QuickConnectChecker(
    private val communityClient: CommunityClient,
) {
    fun check(registrationNumber: String): Boolean {
        return communityClient.check(registrationNumber)
    }
}
