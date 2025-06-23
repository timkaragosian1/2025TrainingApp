package com.timkaragosian.proflowapp.data.resourcesprovider

import androidx.annotation.StringRes

interface ResourceProvider {
    fun string(@StringRes resId: Int, vararg args: Any):String
}