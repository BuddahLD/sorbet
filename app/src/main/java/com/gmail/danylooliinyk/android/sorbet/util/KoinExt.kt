package com.gmail.danylooliinyk.android.sorbet.util

import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ScopeDSL
import org.koin.dsl.module

/**
 * KoinExt
 */

fun moduleWithScope(scopeName: Qualifier,
                    scopeSet: ScopeDSL.() -> Unit): Module {
    return module {
        scope(scopeName, scopeSet)
    }
}
