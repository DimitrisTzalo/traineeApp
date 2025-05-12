package myy803.springboot.trainee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/admin/dashboard")
    public String getAdminHome(){
        return "admin/dashboard";
    }
}
