package com.exercise.accounts.service.client

import com.exercise.accounts.dto.Loan
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("loans")
interface LoansFeignClient {
    @GetMapping(value = ["/api/fetch"], consumes = ["application/json"])
    fun fetch(
        @RequestParam
        mobileNumber: String
    ): ResponseEntity<Loan>
}
