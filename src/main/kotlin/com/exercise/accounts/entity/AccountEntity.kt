package com.exercise.accounts.entity

import com.exercise.accounts.dto.Account
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "account")
@EntityListeners(AuditingEntityListener::class)
data class AccountEntity(
    val customerId: Long = 0,
    
    @Id
    val accountNumber: Long,
    
    val accountType: String,
    
    val branchAddress: String,

    @CreatedDate
    override var createdAt: LocalDateTime? = null,

    @CreatedBy
    override var createdBy: String? = null,
    
    @LastModifiedDate
    override var updatedAt: LocalDateTime? = null,
    
    @LastModifiedBy
    override var updatedBy: String? = null,
    
): BaseEntity {
    constructor(account: Account): this(
        accountNumber = account.accountNumber,
        accountType = account.accountType,
        branchAddress = account.branchAddress,
    )
}
