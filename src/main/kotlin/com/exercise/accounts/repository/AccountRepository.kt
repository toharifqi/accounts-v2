package com.exercise.accounts.repository

import com.exercise.accounts.entity.AccountEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AccountRepository: JpaRepository<AccountEntity, Long> {
    fun findByCustomerId(customerId: Long): Optional<AccountEntity>
    
    @Transactional
    @Modifying
    fun deleteByCustomerId(customerId: Long)
}
