package com.timkaragosian.proflowapp.data.resourcesprovider

import android.content.Context

class FlowAppResourceProvider(
    private val context: Context
): ResourceProvider {
    override fun string(resId: Int, vararg args: Any): String = context.getString(resId, *args)
}