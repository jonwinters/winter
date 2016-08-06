package top.sourcecode.winter.web;

import org.springframework.web.bind.annotation.*;
import top.sourcecode.winter.entity.User;
import top.sourcecode.winter.service.AspectService;
import top.sourcecode.winter.service.UserService;

import javax.annotation.Resource;

@RestController
@RequestMapping("home")
public class HomeController {

	@Resource
	private AspectService aspectServiceImpl;
	@Resource
	private UserService userService;

	@RequestMapping(value = "interceptor", method = RequestMethod.GET)
	public String intercept(@RequestParam(required = false) String msg) {
		System.out.println(msg);
		return "interceptor测试";
	}
	
	@RequestMapping(value = "aop", method = RequestMethod.GET)
	public String cut() {
		aspectServiceImpl.record();
		return "aop测试";
	}
	
	@RequestMapping(value = "aope", method = RequestMethod.GET)
	public String cute() throws Exception {
		throw new Exception("bug");
	}

	@RequestMapping(value = "user", method = RequestMethod.POST)
	public String register(@RequestBody User user) {
		userService.saveUser(user);
		return "ok";
	}
}
