package com.exercise.accounts.entity

import com.exercise.accounts.dto.Customer
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "customer")
@EntityListeners(AuditingEntityListener::class)
data class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    val customerId: Long = 0,

    val name: String,

    val email: String,

    val mobileNumber: String,

    @CreatedDate
    override var createdAt: LocalDateTime? = null,

    @CreatedBy
    override var createdBy: String? = null,

    @LastModifiedDate
    override var updatedAt: LocalDateTime? = null,

    @LastModifiedBy
    override var updatedBy: String? = null,

    ) : BaseEntity {
    constructor(customer: Customer) : this(
        name = customer.name,
        email = customer.email,
        mobileNumber = customer.mobileNumber,
    )
}
