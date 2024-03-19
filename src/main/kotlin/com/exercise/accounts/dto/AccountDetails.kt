package com.exercise.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(
    name = "AccountDetails",
    description = "Schema to hold Customer and Account information"
)
data class AccountDetails(
    @Schema(
        description = "Name of the customer", example = "John Doe"
    )
    @field:NotEmpty(message = "Name can not be null or empty")
    @field:Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    val name: String,

    @Schema(
        description = "Email address of the customer", example = "johndoe@gmail.com"
    )
    @field:NotEmpty(message = "Email address can not be null or empty")
    @field:Email(message = "Email address should be valid value")
    val email: String,

    @Schema(
        description = "Mobile Number of the customer", example = "085555555555"
    )
    @field:Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile number must be 12 digits of numbers")
    val mobileNumber: String,

    @Schema(
        description = "Account details of the Customer"
    )
    val account: Account,
)
