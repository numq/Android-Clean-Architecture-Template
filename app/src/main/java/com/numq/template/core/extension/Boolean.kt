package com.numq.template.core.extension

val Boolean.asInteger: Int
    get() = if (this) 1 else 0