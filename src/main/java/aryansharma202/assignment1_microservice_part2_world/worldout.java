package aryansharma202.assignment1_microservice_part2_world;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // similarly we need rest here to handle requests
public class worldout 
{

    @GetMapping("/world")
    public String printworld()
    {
        return "World";
    }

}
