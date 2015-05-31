package candrun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.support.enums.CommonError;
import candrun.support.enums.CommonInvar;

@RestController
@RequestMapping(value="/error")
public class ErrorController {
	@RequestMapping(method=RequestMethod.GET)
    public Object error(HttpServletRequest request) {
		Map<String, String> returnMsg = new HashMap<String, String>();
		returnMsg.put(CommonInvar.RETURNMSG.getValue(), CommonError.UPLOAD.getValue());
		return returnMsg;
	}
}

