package com.timkaragosian.proflowapp.domain.auth

import com.timkaragosian.proflowapp.BuildConfig
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.data.resourcesprovider.FlowAppResourceProvider

interface AuthRepository {
    suspend fun login(username: String, password: String, strings:FlowAppResourceProvider): Result<Unit>
}

class FakeAuthRepository : AuthRepository {
    override suspend fun login(username: String, password: String, strings: FlowAppResourceProvider): Result<Unit> =
        if (!BuildConfig.ALLOW_ANY_CREDENTIALS) {
            if (username == strings.string(R.string.admin_username) && password == strings.string(R.string.admin_pass)) Result.success(Unit)
            else Result.failure(IllegalArgumentException(strings.string(R.string.bad_credentials_error)))
        } else {
            Result.success(Unit)
        }
}