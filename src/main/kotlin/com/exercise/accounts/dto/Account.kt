package com.exercise.accounts.dto

import com.exercise.accounts.entity.AccountEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

@Schema(
    name = "Account",
    description = "Schema to hold Account information"
)
data class Account(
    @Schema(
        description = "Account Number of X Bank account", example = "3454433243"
    )
    @field:NotEmpty(message = "Account number can not be null or empty")
    @field:Pattern(regexp = "(^$|[0-9]{12})", message = "Account number must be 12 digits of numbers")
    val accountNumber: Long,

    @Schema(
        description = "Account type of X Bank account", example = "Salary"
    )
    @field:NotEmpty(message = "Account type can not be null or empty")
    val accountType: String,

    @Schema(
        description = "X Bank branch address", example = "123 Jakarta"
    )
    @field:NotEmpty(message = "Branch address can not be null or empty")
    val branchAddress: String,
) {
    constructor(accountEntity: AccountEntity): this (
        accountNumber = accountEntity.accountNumber,
        accountType = accountEntity.accountType,
        branchAddress = accountEntity.branchAddress,
    )
}
