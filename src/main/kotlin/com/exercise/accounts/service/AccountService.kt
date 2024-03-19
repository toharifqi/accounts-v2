package com.exercise.accounts.service

import com.exercise.accounts.constant.AccountConstant
import com.exercise.accounts.dto.Account
import com.exercise.accounts.dto.Customer
import com.exercise.accounts.dto.AccountDetails
import com.exercise.accounts.entity.AccountEntity
import com.exercise.accounts.entity.CustomerEntity
import com.exercise.accounts.exception.CustomerAlreadyExistException
import com.exercise.accounts.exception.ResourceNotFoundException
import com.exercise.accounts.repository.AccountRepository
import com.exercise.accounts.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random

interface AccountService {
    fun createAccount(customer: Customer)

    fun fetchAccount(mobileNumber: String): AccountDetails
    
    fun updateAccountDetails(accountDetails: AccountDetails): Boolean
    
    fun deleteAccount(mobileNumber: String): Boolean
}

@Service
class AccountServiceImpl(
    val accountRepository: AccountRepository,
    val customerRepository: CustomerRepository,
) : AccountService {
    override fun createAccount(customer: Customer) {
        val customerEntity = CustomerEntity(customer)
        val optionalCustomer = customerRepository.findByMobileNumber(customerEntity.mobileNumber)

        if (optionalCustomer.isPresent) {
            throw CustomerAlreadyExistException("Customer already registered with given number: ${customerEntity.mobileNumber}")
        }

        val savedCustomer = customerRepository.save(customerEntity)

        accountRepository.save(createNewAccount(savedCustomer))
    }

    override fun fetchAccount(mobileNumber: String): AccountDetails {
        val customerEntity = customerRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        }
        val accountEntity = accountRepository.findByCustomerId(customerEntity.customerId).orElseThrow {
            ResourceNotFoundException("Account", "customerId", customerEntity.customerId.toString())
        }
        
        return AccountDetails(
            name = customerEntity.name,
            email = customerEntity.email,
            mobileNumber = customerEntity.mobileNumber,
            account = Account(accountEntity)
        )
    }

    override fun updateAccountDetails(accountDetails: AccountDetails): Boolean {
        val account = accountDetails.account
        val updatedAccount = accountRepository.findById(account.accountNumber).orElseThrow {
            ResourceNotFoundException("Account", "accountNumber", account.accountNumber.toString())
        }.copy(
            accountNumber = account.accountNumber,
            accountType = account.accountType,
            branchAddress = account.branchAddress,
            updatedAt = LocalDateTime.now()
        )
        accountRepository.save(updatedAccount)
        
        val updatedCustomer = customerRepository.findById(updatedAccount.customerId).orElseThrow {
            ResourceNotFoundException("Customer", "customerId", updatedAccount.customerId.toString())
        }.copy(
            name = accountDetails.name,
            email = accountDetails.email,
            mobileNumber = accountDetails.mobileNumber,
            updatedAt = LocalDateTime.now()
        )
        customerRepository.save(updatedCustomer)
        
        return true
    }

    override fun deleteAccount(mobileNumber: String): Boolean {
        val customerEntity = customerRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        }
        
        accountRepository.deleteByCustomerId(customerEntity.customerId)
        customerRepository.deleteById(customerEntity.customerId)
        
        return true
    }

    private fun createNewAccount(customerEntity: CustomerEntity): AccountEntity {
        val randomAccountNumber = 1000000000L + Random.nextInt(900000000)

        val accountEntity = AccountEntity(
            customerId = customerEntity.customerId,
            accountNumber = randomAccountNumber,
            accountType = AccountConstant.SAVINGS,
            branchAddress = AccountConstant.ADDRESS
        )

        return accountEntity
    }

}
