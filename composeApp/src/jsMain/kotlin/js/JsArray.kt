@file:OptIn(ExperimentalJsCollectionsApi::class)

package js

import kotlin.js.collections.JsArray

fun <T> newJsArray(): JsArray<T> = js("[]") as JsArray<T>

//fun <T> jsArrayOf1(value: JsAny): JsArray<T> = js("[value]")
//fun <T> jsArrayOf2(value1: JsAny, value2: JsAny): JsArray<T> = js("[value1, value2]")

fun <T> jsArrayOf(vararg values: T): JsArray<T> {
    val jsArray = newJsArray<T>()
    for (value in values) {
        jsArray.push(value)
    }
    return jsArray
}

fun <T> JsArray<T>.push(value: T): Int = pushIntoArray(this, value)

fun <T> JsArray<T>.pop(): T? = popFromArray(this)

private fun <T> pushIntoArray(array: JsArray<T>, value: T): Int =
    js("array.push(value)") as Int

private fun <T> popFromArray(array: JsArray<T>): T? = js("array.pop()") as T?
