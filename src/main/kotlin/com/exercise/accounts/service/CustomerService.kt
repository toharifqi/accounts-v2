package com.exercise.accounts.service

import com.exercise.accounts.dto.Account
import com.exercise.accounts.dto.CustomerDetails
import com.exercise.accounts.exception.ResourceNotFoundException
import com.exercise.accounts.repository.AccountRepository
import com.exercise.accounts.repository.CustomerRepository
import com.exercise.accounts.service.client.CardsFeignClient
import com.exercise.accounts.service.client.LoansFeignClient
import org.springframework.stereotype.Service

interface CustomerService {
    fun fetchCustomerDetails(mobileNumber: String): CustomerDetails
}

@Service
class CustomerServiceImpl(
    val cardsFeignClient: CardsFeignClient,
    val loansFeignClient: LoansFeignClient,
    val accountRepository: AccountRepository,
    val customerRepository: CustomerRepository
): CustomerService {
    override fun fetchCustomerDetails(mobileNumber: String): CustomerDetails {
        val customerEntity = customerRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        }
        val accountEntity = accountRepository.findByCustomerId(customerEntity.customerId).orElseThrow {
            ResourceNotFoundException("Account", "customerId", customerEntity.customerId.toString())
        }
        
        val card = cardsFeignClient.fetch(mobileNumber).body
        val loan = loansFeignClient.fetch(mobileNumber).body
        
        if (card == null) {
            throw ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        } 
        
        if (loan == null) {
            throw ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        }

        return CustomerDetails(
            name = customerEntity.name,
            email = customerEntity.email,
            mobileNumber = customerEntity.mobileNumber,
            account = Account(accountEntity),
            card = card,
            loan = loan,
        )
    }
}
