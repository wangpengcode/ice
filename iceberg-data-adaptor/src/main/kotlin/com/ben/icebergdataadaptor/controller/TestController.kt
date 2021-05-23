package com.ben.icebergdataadaptor.controller

import org.python.util.PythonInterpreter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/test")
class TestController {
	@GetMapping("/python")
	fun test() : String {
//		val py = PythonInterpreter()
		val props = Properties()
		props.put("python.home", "../jython-2.7.0")
		props.put("python.console.encoding", "UTF-8")
		props.put("python.security.respectJavaAccessibility", "false")
		props.put("python.import.site", "false")
		val preprops = System.getProperties()
		PythonInterpreter.initialize(preprops, props,  arrayOf(""));
		val pyInterpreter = PythonInterpreter()
		pyInterpreter.exec("import sys")
		pyInterpreter.exec("print 'prefix', sys.prefix")
		pyInterpreter.exec("print sys.path")
		System.out.println("python的jar包引用正确")
		pyInterpreter.exec("print('hello world')")
		return "hello"
	}
}