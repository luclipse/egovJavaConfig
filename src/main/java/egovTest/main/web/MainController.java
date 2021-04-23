package egovTest.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/main/page.do")
    public String mainPage(){
        return  "egovTest/main/main";
    }

}
