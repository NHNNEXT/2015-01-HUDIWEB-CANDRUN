var CANDRUN = CANDRUN || {};

CANDRUN.enums = {};
CANDRUN.enums.pwValMsg = {
	SHORT: "비밀번호는 최소 6자 이상이어야 합니다.",
	NUM: "비밀번호는 숫자를 포함하여야 합니다.",
	MARK: "비밀번호는 특수문자를 포함하여야 합니다."
}
CANDRUN.enums.emailValMsg = {
	FAULT: "유효한 이메일 형식이 아닙니다."
}
CANDRUN.enums.pwConMsg = {
	FAULT: "비밀번호가 일치하지 않습니다."
}

CANDRUN.util = {};
//사용 예시
//var myAjax = new CANDRUN.util.ajax("http://candrun.com/user", function() {console.log("success");}, function() {console.log("fail");});
//헤더를 json으로 - option
//myAjax.setJson();
//메소드를 put으로 - option
//myAjax.setMethod("PUT");
//Ajax 실행
//myAjax.send();
CANDRUN.util.ajax = function(sUrl, fSuccess, fFail) {
	var httpRequest;
	var method = 'GET';

	this.setJson = function() {
		 httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	}
	this.setMethod = function(sMethod) {
		method = sMethod;
	}
	this.open = function() {
		httpRequest.open(method, sUrl);
	}
	//send 메소드에 params 추가.
	this.send = function(params) {
		httpRequest.send(params);
	}	
	
	if (window.XMLHttpRequest) {
		httpRequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		try {
			httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}
	if (!httpRequest) {
		console.log('Giving up :( Cannot create an XMLHTTP instance');
		return false;
	}
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === 4) {
			if (httpRequest.status === 200) {
				fSuccess(httpRequest.responseText);
			} else {
				if (fFail) {
					fFail();
				}
			}
		}
	}
}
CANDRUN.util.querySelector = function(el) {
	return document.querySelector(el);
}
CANDRUN.util.addClass = function(elTarget, sClass) {
	elTarget.className += " " + sClass;
}
CANDRUN.util.removeClass = function(elTarget, sClass) {
	elTarget.className = CANDRUN.util.trimString(elTarget.className.replace(sClass, ''));
}
CANDRUN.util.trimLString = function(str) {
	return str.replace(/^\s+/, '');
}
CANDRUN.util.trimRString = function(str) {
	return str.replace(/\s+$/, '');
}
CANDRUN.util.trimString = function(str) {
	return str.replace(/^\s+|\s+$/g, '');
}
CANDRUN.util.isValidateEmail = function(sEmail) {
	var mailFormat = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/
	if (sEmail.match(mailFormat)) {
		return true;
	}
	return CANDRUN.enums.emailValMsg.FAULT;
}
CANDRUN.util.isValidatePW = function(sPW) {
	var reg;
	var pwValMsgEnum = CANDRUN.enums.pwValMsg;

	if (sPW.length < 6) {
		return pwValMsgEnum.SHORT;
	}
	reg = /[0-9]/;
	if (!reg.test(sPW)) {
		return pwValMsgEnum.NUM;
	}
	reg = /[!#$%&'*+/=?^_`{|}~-]/;
	if (!reg.test(sPW)) {
		return pwValMsgEnum.MARK;
	}
	return true;
}
CANDRUN.util.isSamePW = function(sPW1, sPW2) {
	if (sPW1 === sPW2) {
		return true;
	}
	return CANDRUN.enums.pwConMsg.FAULT;
}
CANDRUN.util.getKeyCode = function(e) {
	if (e.which) {
		return e.which;
	}
	return keyCode = e.keyCode;
}
