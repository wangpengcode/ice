package com.ben.icebergdataadaptor.controller

import org.python.util.PythonInterpreter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {
	@GetMapping("/python")
	fun test() : String {
//		val py = PythonInterpreter()
		return "hello"
	}
}