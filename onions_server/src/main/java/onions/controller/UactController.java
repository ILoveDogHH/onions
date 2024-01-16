package onions.controller;

import onions.entity.UAct;
import onions.service.UactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UactController {

    @Autowired
    UactService uactService;


    @RequestMapping(value = "uact", method = RequestMethod.GET)
    @ResponseBody
    public String getUactInfo(){
        List<UAct> actList = uactService.getUactInfo(1);
        return actList.toString();
    }




}
