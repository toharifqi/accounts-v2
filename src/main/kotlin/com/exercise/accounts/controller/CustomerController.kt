package com.exercise.accounts.controller

import com.exercise.accounts.dto.CustomerDetails
import com.exercise.accounts.dto.ErrorResponse
import com.exercise.accounts.service.CustomerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "REST API for Customers in X Bank",
    description = "REST API in X Bank to fetch customer details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class CustomerController(
    private val customerService: CustomerService
) {
    
    @Operation(
        summary = "Fetch Customer Details REST API",
        description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/fetchCustomerDetails")
    fun fetchCustomerDetails(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile number must be 12 digits of numbers")
        mobileNumber: String
    ): ResponseEntity<CustomerDetails> {
        val customerDetails = customerService.fetchCustomerDetails(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(customerDetails)
    }
}
