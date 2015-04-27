var INDEX = INDEX || {};

INDEX.init = function() {
	var methods = INDEX.methods;
	methods.getElements();
	methods.addEvents();
}
INDEX.elements = {};
INDEX.methods = {};
INDEX.methods.getElements = function() {
	var querySelector = CANDRUN.util.querySelector;
	var elements = INDEX.elements;
	elements.signinForm = querySelector("#signin-form");
	elements.signupForm = querySelector("#signup-form");
	elements.signinCloser = querySelector("#signin-close-mark");
	elements.signupCloser = querySelector("#signup-close-mark");
	elements.bg = querySelector(".form-wrapper");
	elements.signLinksWrapper = querySelector(".sign-link-wrapper");
	elements.signinLink = querySelector("#signin-link");
	elements.signupLink = querySelector("#signup-link");
	elements.signupFormFields = querySelector("#signup-form-fields");
	elements.signinFormFields = querySelector("#signin-form-fields");
	elements.signupEmailInput = querySelector("#input-1-1");
	elements.signupNickInput = querySelector("#input-1-2");
	elements.signupPwInput = querySelector("#input-1-3");
	elements.signupConPwInput = querySelector("#input-1-4");
	elements.signupEmailRep = querySelector("#signup-email-responser");
	elements.signupPwRep = querySelector("#signup-pw-responser");
	elements.signupConPwRep = querySelector("#signup-confirm-pw-responser");
	elements.signupSubmit = querySelector("#signup-form-submit");
	elements.signinEmailInput = querySelector("#input-2-1");
	elements.signinPwInput = querySelector("#input-2-2");
	elements.signinEmailRep = querySelector("#signin-email-responser");
	elements.signinPwRep = querySelector("#signin-pw-responser");
	elements.signinSubmit = querySelector("#signin-form-submit");
}
INDEX.methods.addEvents = function() {
	var elements = INDEX.elements;
	var form = INDEX.form;

	elements.signinCloser.addEventListener("click", function() {
		form.closeModal(elements.signinForm);
	});
	elements.signupCloser.addEventListener("click", function() {
		form.closeModal(elements.signupForm);
	});
	elements.signinLink.addEventListener("click", function(e) {
		e.preventDefault();
		form.openModal(elements.signinForm);
	});
	elements.signupLink.addEventListener("click", function(e) {
		e.preventDefault();
		form.openModal(elements.signupForm);
	});
	elements.signupFormFields
			.addEventListener("keyup", form.validateSignUpForm);
	elements.signupFormFields.addEventListener("blur", form.validateSignUpForm,
			true);
	elements.signinFormFields.addEventListener("keyup",
			form.confirmSigninSubmittable);
	elements.signinFormFields.addEventListener("blur",
			form.confirmSigninSubmittalble, true);

	elements.signinForm.addEventListener("submit", form.submitSigninForm);
	elements.signupForm.addEventListener("submit", form.submitSignupForm);
}
INDEX.form = INDEX.form || {};
INDEX.form.submitSignupForm = function(e) {
	e.preventDefault();
	var util = CANDRUN.util;
	var form = INDEX.form;
	var elements = INDEX.elements;
	var ajax = new util.ajax("/users", form.checkSignUpResult);
	var params = "email=" + elements.signupEmailInput.value + "&password="
			+ elements.signupPwInput.value + "&nickname="
			+ elements.signupNickInput.value;
	ajax.setMethod("POST");
	ajax.open();
	ajax.setJson();
	ajax.readyParam();
	ajax.send(params);
}
INDEX.form.checkSignUpResult = function(sResp) {
	var oReturnMsg = {};
	oReturnMsg = JSON.parse(sResp);

	if (oReturnMsg.returnMsg === "dup") {
		alert("동일한 E-mail이 존재합니다.");
		return;
	}
	if (oReturnMsg.returnMsg === "success") {
		alert("이메일 인증을 하셔야 회원가입이 완료 됩니다.")
		location.replace("/");
	}
}
INDEX.form.submitSigninForm = function(e) {
	e.preventDefault();
	var util = CANDRUN.util;
	var form = INDEX.form;
	var elements = INDEX.elements;
	var ajax = new util.ajax("/auth", form.checkLoginResult);
	var params = "email=" + elements.signinEmailInput.value + "&password="
			+ elements.signinPwInput.value;
	ajax.setMethod("POST");
	ajax.open();
	ajax.setJson();
	ajax.readyParam();
	ajax.send(params);
}
INDEX.form.checkLoginResult = function(sResp) {
	var oReturnMsg = {};
	oReturnMsg = JSON.parse(sResp);

	if (oReturnMsg.returnMsg === "empty") {
		alert("존재하지 않는 E-mail 입니다.");
		return;
	}
	if (oReturnMsg.returnMsg === "wrongPw") {
		alert("잘못된 비밀번호 입니다.");
		return;
	}
	if (oReturnMsg.returnMsg === "notYetCerti") {
		alert("E-mail 인증이 진행되지 않았습니다.");
		return;
	}
	if (oReturnMsg.returnMsg === "success") {
		location.replace("/");
	}
}
INDEX.form.validateSignUpForm = function(e) {
	var util = CANDRUN.util;
	var elements = INDEX.elements;
	var form = INDEX.form;
	var keyCode = util.getKeyCode(e);
	var elTarget;
	var fValidator;

	switch (e.target.id) {
	case "input-1-1":
		elTarget = elements.signupEmailRep;
		fValidator = util.isValidateEmail;
		break;
	case "input-1-3":
		elTarget = elements.signupPwRep;
		fValidator = util.isValidatePW;
		break;
	case "input-1-4":
		elTarget = elements.signupConPwRep;
		form.validateConPW(elTarget, elements.signupPwInput.value,
				e.target.value);
		form.confirmSignupSubmittable();
		return;
	default:
		return;
	}
	if (keyCode != 13) {
		form.validateInput(elTarget, e.target.value, fValidator);
		form.confirmSignupSubmittable();
		return;
	}
}
INDEX.form.confirmSignupSubmittable = function() {
	var elements = INDEX.elements;
	if (!elements.signupEmailInput.value || !elements.signupNickInput.value
			|| !elements.signupPwInput.value
			|| !elements.signupConPwInput.value) {
		elements.signupSubmit.disabled = true;
		return;
	}
	if (elements.signupEmailRep.innerHTML || elements.signupPwRep.innerHTML
			|| elements.signupConPwRep.innerHTML) {
		elements.signupSubmit.disabled = true;
		return;
	}
	elements.signupSubmit.disabled = false;
}
INDEX.form.confirmSigninSubmittable = function() {
	var elements = INDEX.elements;
	if (!elements.signinEmailInput.value || !elements.signinPwInput.value) {
		elements.signinSubmit.disabled = true;
		return;
	}
	elements.signinSubmit.disabled = false;
}
INDEX.form.validateInput = function(elTarget, sValue, fValidator) {
	if (!sValue) {
		elTarget.innerHTML = "";
		return;
	}

	var result = fValidator(sValue);
	if (result != true) {
		elTarget.innerHTML = result;
		return;
	}
	elTarget.innerHTML = "";
}
INDEX.form.validateConPW = function(elTarget, sVal1, sVal2) {
	if (!sVal2) {
		elTarget.innerHTML = "";
		return;
	}
	var result = CANDRUN.util.isSamePW(sVal1, sVal2);
	if (result != true) {
		elTarget.innerHTML = result;
		return;
	}
	elTarget.innerHTML = "";
}
INDEX.form.closeModal = function(elModal) {
	var util = CANDRUN.util;
	var elements = INDEX.elements;
	util.addClass(elModal, "dp-none");
	util.removeClass(elements.bg, "overlay-darken");
	util.removeClass(elements.signLinksWrapper, "dp-none");
}
INDEX.form.openModal = function(elModal) {
	var util = CANDRUN.util;
	var elements = INDEX.elements;
	util.removeClass(elModal, "dp-none");
	util.addClass(elements.bg, "overlay-darken");
	util.addClass(elements.signLinksWrapper, "dp-none");
	elModal[0].focus();
}

document.addEventListener("DOMContentLoaded", function(e) {
	INDEX.init();
});
