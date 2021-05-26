package com.ben.icebergdataadaptor.extensions

fun String.toNakedCode(): String {
	return this.replace("sh.","").replace("sz.","").trim()
}