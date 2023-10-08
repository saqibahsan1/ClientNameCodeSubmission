package com.apex.codeassesment.di

import com.squareup.moshi.Moshi
import dagger.Module

interface MoshiInt {
    fun initMoshi(): Moshi
}

@Module
class MoshiManager : MoshiInt {
    override fun initMoshi(): Moshi =
        Moshi.Builder().build()

}