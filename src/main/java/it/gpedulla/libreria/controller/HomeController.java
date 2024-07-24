package it.gpedulla.libreria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        // Log dei cookie
        StringBuilder cookies = new StringBuilder();
        if (request.getCookies() != null) {
        	for (Cookie cookie : request.getCookies()) {
                cookies.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
            }
        } else {
            cookies.append("No cookies found");
        }
        System.out.println("Received cookies: " + cookies.toString());

        // Mostra la home
        return "home";  // Assicurati di avere un template "home.html" in "src/main/resources/templates"
    }

}
