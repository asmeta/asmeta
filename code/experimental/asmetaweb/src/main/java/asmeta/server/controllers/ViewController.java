package asmeta.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {
	
	public ViewController() {
        
    }

//	@RequestMapping(value = "/{page}.html", method = RequestMethod.GET)
//	public String redirectToPage(@PathVariable("page") String page) {
//	    return "forward:/Users/luciomarcomaranta/eclipse-workspace/server/asmeta-frontend/dist" + page + ".html";
//	}
}