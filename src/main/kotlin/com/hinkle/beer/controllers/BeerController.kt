package com.hinkle.beer.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BeerController{

    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Beer"
        return "beer"
    }

}


