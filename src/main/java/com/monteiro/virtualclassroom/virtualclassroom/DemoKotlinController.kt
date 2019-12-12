package com.monteiro.virtualclassroom.virtualclassroom

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class DemoKotlinController {


    // inject via application.properties
    @Value("\${welcome.message}")
    private val message: String? = null

    private val tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g")

    // /kotlin
    @GetMapping("/kotlin")
    fun main(model: Model): String {
        print("/DemoKotlinController.main")

        model.addAttribute("message", message)
        model.addAttribute("tasks", tasks)

        return "welcome" //view
    }

    // /kotlinParam?name=kotlin
    @GetMapping("/kotlinParam")
    fun mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
            name: String, model: Model): String {

        print("/DemoKotlinController.mainWithParam name=$name")

        model.addAttribute("message", name)

        return "welcome" //view
    }
}