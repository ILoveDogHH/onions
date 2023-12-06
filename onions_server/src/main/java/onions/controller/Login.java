package onions.controller;

import onions.service.LoginService;
import onions.utils.RedissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Login {

    @Autowired
    LoginService loginService;

    @Autowired
    RedissionUtil redissionUtil;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginHtml(){
        return "Login";
    }


    @RequestMapping(value = "userLogin", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password){
        boolean check = redissionUtil.mapKeyExists("user", username);
        if(!check){
            //直接进入到注册页面中
            return "账号不存在，请进行注册";
        }
        //验证下账号是否chengg
        String msg = redissionUtil.getMapValue("user", username);
        if(!msg.endsWith(password)){
            return "密码错误";
        }
        return "登陆成功";
    }

    @RequestMapping(value = "userRegister", method = RequestMethod.GET)
    public String register(){
        return "Register";
    }



}
