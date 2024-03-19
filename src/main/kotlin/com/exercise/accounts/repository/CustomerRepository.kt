package com.exercise.accounts.repository

import com.exercise.accounts.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository: JpaRepository<CustomerEntity, Long> {
    fun findByMobileNumber(mobileNumber: String): Optional<CustomerEntity>
}
