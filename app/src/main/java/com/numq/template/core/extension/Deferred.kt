package com.numq.template.core.extension

import kotlinx.coroutines.Deferred

fun Deferred<Any>.waitThen(wait: () -> Unit = {}, then: () -> Unit) {
    if (this.isActive) {
        wait()
    }
    this.invokeOnCompletion { then() }
}