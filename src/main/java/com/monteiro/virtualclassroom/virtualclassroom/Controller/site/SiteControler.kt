package com.monteiro.virtualclassroom.virtualclassroom.Controller.site

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpSession


@Controller
class SiteControler {

    @GetMapping("/site")
    @Throws(Exception::class)
    fun index(session: HttpSession, model: Model): String {
        println("GET /site")

        return "site/index"
    }


    @GetMapping("/dispo")
    fun dispo(): String {
        println("GET /dispo ")
        return "site/dispo" //view
    }

    @GetMapping("/resume")
    fun resume(): String {
        println("GET /resume ")
        return "site/resume" //view
    }

    //@PathVariable("name") name: String
    @GetMapping("/portfolio")
    fun portfolio(name: String): String {
        println("GET /portfolio $name")

        return "site/portfolio-$name" //view
    }

    /* -------------------------------- */
    // Page contact
    /* -------------------------------- */

    @GetMapping("/contact")
    fun contact(): String {
        println("GET /contact ")
        return "site/contact" //view
    }

    //@PathVariable("name") name: String
    @GetMapping("/sendMessage")
    fun sendMessage(model: Model, name: String?, email: String?, comments: String?): String {
        println("GET /sendMessage name=$name\n email=$email\n comments=$comments")


        var errorMessage: String? = null
        //Controle de l'émail
        if (email.isNullOrBlank() || !email.contains("@")) {
            errorMessage = "Email invalide"
        }
        else if (comments.isNullOrBlank()) {
            errorMessage = "Message Vide"
        }

        if (!errorMessage.isNullOrBlank()) {
            model.addAttribute("errorMessage", errorMessage)
        }


        return "site/portfolio-$name" //view
    }


}