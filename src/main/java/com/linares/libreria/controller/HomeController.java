package com.linares.libreria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Hidden;

/** Redirige la raíz (http://localhost:8080/) a la interfaz de Swagger. */
@Hidden
@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "redirect:/swagger-ui.html";
	}
}
