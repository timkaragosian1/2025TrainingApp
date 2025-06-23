package com.timkaragosian.proflowapp.data.resourcesprovider

class FakeFlowAppResourceProvider(
    private val map:Map<Int,String> = emptyMap()
):ResourceProvider {
    override fun string(resId: Int, vararg args: Any): String = map[resId]?:"Missing-res-$resId"
}