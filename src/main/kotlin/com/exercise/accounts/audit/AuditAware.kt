package com.exercise.accounts.audit

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

@Component("auditAwareImpl")
class AuditAwareImpl: AuditorAware<String>{
    override fun getCurrentAuditor(): Optional<String> {
        return Optional.of("ACCOUNT_MS")
    }
}
