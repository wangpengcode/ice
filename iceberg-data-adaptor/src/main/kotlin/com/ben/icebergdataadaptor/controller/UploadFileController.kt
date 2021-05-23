package com.ben.icebergdataadaptor.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/file/upload")
class UploadFileController {
	@PostMapping("/all")
	fun uploadAllStock(@RequestBody list: String) {
		kotlin.io.println(list)
		val a = list.split(',')
		System.out.println(a)
	}
}