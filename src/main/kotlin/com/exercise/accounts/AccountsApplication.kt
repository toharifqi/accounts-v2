package com.exercise.accounts

import com.exercise.accounts.dto.ProjectContactInfo
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = [ProjectContactInfo::class])
@EnableFeignClients
@OpenAPIDefinition(
	info = Info(
		title = "Account microservice REST API Documentation",
		description = "Account Microservice is a restful HTTP Api service to handle bank Account business logic such as create account, fetch account details, update account, and close account.",
		version = "v1",
		contact = Contact(
			name = "Rifqi Naufal Tohari",
			email = "rifqinaufaltohari@gmail.com",
			url = "https://www.linkedin.com/in/rifqi-naufal-tohari/"
		)
	),
	externalDocs = ExternalDocumentation(
		description =  "Source Code",
		url = "https://github.com/toharifqi/accounts"
	)
)
class AccountsApplication

fun main(args: Array<String>) {
	runApplication<AccountsApplication>(*args)
}
