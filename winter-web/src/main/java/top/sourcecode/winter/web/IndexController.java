package top.sourcecode.winter.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.sourcecode.winter.common.domain.HttpResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mountain on 7/16/16.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.addAttribute("greeting", "Hello World from Mountain");
        return "index";
    }

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult ping() {
        Map<String, Object> map = new HashMap<>();
        map.put("ping", "pong");
        return HttpResult.success(map);
    }
}
