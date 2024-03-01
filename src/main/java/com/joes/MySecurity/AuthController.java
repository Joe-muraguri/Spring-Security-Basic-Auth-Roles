package com.joes.MySecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

   @GetMapping("/open")
    public String open(){
       return "No Login Required";
   }

   @GetMapping("/closed")
    public String closed(){
       return "Login is Required";
   }

   @GetMapping("/basic")
    public String basic(){
       return "BASIC";
   }

   @GetMapping("/special")
    public String special(){
       return "SPECIAL";
   }
}
