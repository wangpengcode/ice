package com.ben.icebergdataadaptor.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/file/upload")
class UploadFileController {
	@PostMapping("/all")
	fun uploadAllStock(@RequestBody list: String) {
		System.out.println(list)
		val array = list
	}
}