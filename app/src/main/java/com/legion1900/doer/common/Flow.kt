package com.legion1900.doer.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

fun <T> Flow<T>.vetoIf(
    veto: (old: T, new: T) -> Boolean
): Flow<T> = distinctUntilChanged { old, new -> veto(old, new) }
