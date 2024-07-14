package js

fun <T : JsAny> newJsArray(): JsArray<T> = js("[]")

//fun <T : JsAny> jsArrayOf1(value: JsAny): JsArray<T> = js("[value]")
//fun <T : JsAny> jsArrayOf2(value1: JsAny, value2: JsAny): JsArray<T> = js("[value1, value2]")

fun <T : JsAny> jsArrayOf(vararg values: T): JsArray<T> {
    val jsArray = newJsArray<T>()
    for (value in values) {
        jsArray.push(value)
    }
    return jsArray
}

fun <T : JsAny> JsArray<T>.push(value: T): Int = pushIntoArray(this, value)

fun <T : JsAny> JsArray<T>.pop(): T? = popFromArray(this)

private fun <T : JsAny> pushIntoArray(array: JsArray<T>, value: T): Int =
    js("array.push(value)")

private fun <T : JsAny> popFromArray(array: JsArray<T>): T? = js("array.pop()")
