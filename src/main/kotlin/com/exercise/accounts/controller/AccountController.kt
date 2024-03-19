package com.exercise.accounts.controller

import com.exercise.accounts.constant.AccountConstant
import com.exercise.accounts.dto.AccountDetails
import com.exercise.accounts.dto.ProjectContactInfo
import com.exercise.accounts.dto.Customer
import com.exercise.accounts.dto.ErrorResponse
import com.exercise.accounts.dto.Response
import com.exercise.accounts.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment

@Tag(
    name = "CRUD REST APIs for Account in X Bank",
    description = "CRUD APIs in X Bank to CREATE, UPDATE, FETCH, & DELETE account details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class AccountController(
    private val accountService: AccountService,
    private val environment: Environment,
    private val projectContactInfo: ProjectContactInfo
) {
    
    @Value("\${build.version}")
    val buildVersion: String? = null

    @Operation(
        summary = "Create Account REST API",
        description = "REST API to create new Customer & Account inside X Bank"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @PostMapping("/create")
    fun createAccount(
        @RequestBody @Valid customer: Customer,
    ): ResponseEntity<Response> {
        accountService.createAccount(customer)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                Response(
                    statusCode = AccountConstant.STATUS_201,
                    statusMessage = AccountConstant.MESSAGE_201
                )
            )
    }

    @Operation(
        summary = "Fetch Account Details REST API",
        description = "REST API to fetch Customer & Account details based on a mobile number"
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
    @GetMapping("/fetch")
    fun fetchAccountDetails(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile number must be 12 digits of numbers")
        mobileNumber: String
    ): ResponseEntity<AccountDetails> {
        val accountDetails = accountService.fetchAccount(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountDetails)
    }

    @Operation(
        summary = "Update Account Details REST API",
        description = "REST API to update Customer & Account details based on a account number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @PutMapping("/update")
    fun updateAccountDetails(
        @Valid @RequestBody accountDetails: AccountDetails
    ): ResponseEntity<Response> {
        val isUpdated = accountService.updateAccountDetails(accountDetails)
        return if (isUpdated) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = AccountConstant.STATUS_200,
                        statusMessage = AccountConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = AccountConstant.STATUS_417,
                        statusMessage = AccountConstant.MESSAGE_417_UPDATE
                    )
                )
        }
    }

    @Operation(
        summary = "Delete Account & Customer Details REST API",
        description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @DeleteMapping("/delete")
    fun deleteAccountDetails(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile number must be 12 digits of numbers")
        mobileNumber: String
    ): ResponseEntity<Response> {
        val isDeleted = accountService.deleteAccount(mobileNumber)
        return if (isDeleted) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = AccountConstant.STATUS_200,
                        statusMessage = AccountConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = AccountConstant.STATUS_417,
                        statusMessage = AccountConstant.MESSAGE_417_DELETE
                    )
                )
        }
    }

    @Operation(
        summary = "Get Build Information",
        description = "Get Build information that is deployed into accounts microservice"
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
    @GetMapping("/build-info")
    fun getBuildInfo(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion)
    }

    @Operation(
        summary = "Get Java version",
        description = "Get Java version that is installed into accounts microservice"
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
    @GetMapping("/java-version")
    fun getJavaVersion(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME") ?: "null, need permission")
    }

    @Operation(
        summary = "Get Contact Info",
        description = "Contact Info details that can be reached out in case of any issues"
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
    @GetMapping("/contact-info")
    fun getContactInfo(): ResponseEntity<ProjectContactInfo> {
        return ResponseEntity.status(HttpStatus.OK).body(projectContactInfo)
    }
}
