package aryansharma202.assignment1_microservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController// we need a rest controller to tell this class to handle requests from web
public class hellouput 
{
    @GetMapping("/hello")  // we need mapping which helps returning the hello string when passed
    public String printhello()
    {
        return "Hello";
    }

}
