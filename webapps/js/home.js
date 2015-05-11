/**
 * for home.jpg author: cob
 */
document.addEventListener("DOMContentLoaded", function(e) {
	HOME.init();
});

var HOME = HOME || {};

HOME.init = function() {
	var methods = HOME.methods;
	methods.getElements();
	methods.addEvents();
}

HOME.elements = HOME.elements || {};
HOME.methods = HOME.methods || {};
HOME.methods.getElements = function() {
	var querySelectorAll = CANDRUN.util.querySelectorAll;
	var querySelector = CANDRUN.util.querySelector;
	var elements = HOME.elements;
	elements.goalsInNav = querySelectorAll("nav .nav-goal");
	elements.profilePic = querySelector("nav .profile-picture");
	elements.navGoalContainer = querySelector("nav .nav-goal-container");
	elements.goalInput = querySelector("#make-goal .goal-input");
	elements.goalSubmit = querySelector("#make-goal .goal-form-submit");
	elements.taskInput = querySelector("#make-goal .task-input");
	elements.taskInputs = querySelectorAll("#make-goal .task-input");
	elements.taskInputContainer = querySelector("#make-goal .input-container");
	elements.taskInputAdd = querySelector("#make-goal .task-input-add");
	elements.showGoalSec = querySelector("#show-goal");
	elements.userCard = querySelector("#user-card");
	elements.btnLogout = querySelector("#user-card .btn-logout");
}

HOME.methods.addEvents = function() {
	var elements = HOME.elements;
	var nav = HOME.nav;
	var form = HOME.form;

	for (var i = 0; i < elements.goalsInNav.length; i++) {
		elements.goalsInNav[i].addEventListener("click", function(e) {
			nav.requestGoal(e.target.id);
		});
	}
	elements.profilePic.addEventListener("click", nav.userCardToggle);
	elements.btnLogout.addEventListener("click", nav.logout);
	elements.goalSubmit.addEventListener("click", form.send);
	elements.taskInputAdd.addEventListener("click", form.makeNextInput);
	elements.taskInput.addEventListener("click", form.clearInputValue);
	elements.taskInput.addEventListener("keydown", form.makeNextInputWithEnter);
	elements.goalInput.addEventListener("focus", form.clearInputValue);
}

HOME.nav = HOME.nav || {};
HOME.nav.requestGoal = function(id) {
	var ajax = new CANDRUN.util.ajax("/goals/" + id, HOME.nav.refreshGoalView);
	ajax.open();
	ajax.setJson();
	ajax.send();
}

HOME.nav.refreshGoalView = function(sResp) {
	var oGoal = {};
	var template;
	var html;
	oGoal = JSON.parse(sResp);
	template = Handlebars.compile(HOME.templates.showGoal);
	html = template(oGoal);
	HOME.elements.showGoalSec.innerHTML = html;
	NUDGE.init();
}

HOME.nav.userCardToggle = function(){
	var userCard = HOME.elements.userCard;
	if(userCard.style.display==="block"){
		userCard.style.display = "none";
	}else{
		userCard.style.display = "block";
	}
}

HOME.nav.logout = function(){
	var ajax = new CANDRUN.util.ajax("/auth", function(){
		location.href="http://localhost:8080";
	});
	ajax.setMethod("DELETE");
	ajax.open();
	ajax.setSimplePost();
	ajax.send();
}

HOME.nav.appendNewNavGoal = function (responseText) {
	var navGoal = HOME.nav.makeNavGoal(JSON.parse(responseText).contents);
	HOME.elements.navGoalContainer.appendChild(navGoal);
};

HOME.nav.makeNavGoal= function (value) {
	var li = document.createElement( 'li' );
	li.innerHTML = value;
	li.setAttribute("class", "nav-goal");
	return li;
};

HOME.form = HOME.form || {};
HOME.form.send = function () {
	var sUrl = "/goals";
	var params = "goal_contents="+HOME.elements.goalInput.value;
	var ajax = new CANDRUN.util.ajax(sUrl, HOME.nav.appendNewNavGoal);
	var inputs = CANDRUN.util.querySelectorAll(".task-input");
	var blankPattern = /^\s+|\s+$/g;
	
	
	if(HOME.elements.goalInput.value.replace(blankPattern,"")==""){
		alert("Goal이 공백입니다.")
	}
	
	for(var i =0 ; i<inputs.length; i++){
		params = params + "&task_contents_"+i+"="+inputs[i].value;
		if(inputs[i].value.replace(blankPattern,"")==""){
			alert("Task가 공백입니다.");
			return;
		}
	}
	
	ajax.setMethod("POST");
	ajax.open();
	ajax.setSimplePost();
	ajax.send(params);
};

HOME.form.clearInputValue = function(e){
	if(e.target.value=="Task를 입력하세요."||e.target.value=="Goal을 입력하세요."){
		e.target.value=" ";
	}
}

HOME.form.makeNextInputWithEnter = function(e){
	if(e.keyCode === 13){
		HOME.form.makeNextInput();
	}
};

HOME.form.makeNextInput = function(){
	var form = HOME.form;
	var taskInputWrapper = document.createElement('div');
	var inputNodes = document.querySelectorAll(".task-input");
	var lastInput = inputNodes[inputNodes.length-1];

	if(inputNodes.length > 4){
		alert("Task를 5개 이상 등록할 수 없습니다.");		
		return;
	}
	
	lastInput.removeEventListener("keydown", form.makeNextInputWithEnter);
	makeNewInput(taskInputWrapper);
	HOME.elements.taskInputContainer.appendChild(taskInputWrapper);
	addEventForInput(taskInputWrapper);
	
	function makeNewInput(taskInputWrapper) {
		taskInputWrapper.setAttribute("class", "wrapper-task-input");
		template = Handlebars.compile(HOME.templates.taskInput);
		taskInputWrapper.innerHTML = template();
	}

	function addEventForInput(taskInputWrapper){
		input = taskInputWrapper.querySelector(".task-input");
		input.addEventListener("keydown", form.makeNextInputWithEnter);
		input.addEventListener("click", form.clearInputValue);
		input.addEventListener("keydown", form.clearInputValue);
		input.focus();
	
		btnInputDelete = taskInputWrapper.querySelector(".btn-delete-task");
		btnInputDelete.addEventListener("click", form.deleteCurrentInput);		
	}
};

HOME.form.deleteCurrentInput = function(e){
	var wrapper= e.target.parentNode;
	e.target.parentNode.parentNode.removeChild(wrapper);
}

HOME.templates = {};
// YG: 앞으로도 계속 이 방식으로 템플릿을 작성하실 계획이신가요?
//     템플릿 유지보수가 매우 어려울 것 같습니다. 더 큰 템플릿을 추가하게 된다면 더더욱.
//     제가 아는 다른 방법으로는
//       1. html 파일에 <noscript> 태그를 사용하여 템플릿을 저장해두는 방법.
//       2. 다른 html 파일로 저장해둔 뒤, ajax로 요청하여 받아내는 방법.
//     등이 있습니다. 위 방법들은 html 코드를 js 코드와 분리하는 것을 우선으로 생각합니다.
//     만약 js 코드 내에 템플릿을 보관하고 싶으시다고 하더라도
//     리스트로 작성하고, join()하는 방식은 좋지 못한 방법으로 보입니다.
//     다른 방법을 찾아보셨으면 좋겠습니다.
HOME.templates.showGoal = [ '<div class="goal-wrapper">',
		'<div class="goal-title">{{goal.contents}}</div>', 
		'<input type="hidden" class="goal-owner-email" value="{{goal.email}}" />','</div>',
		'{{#each tasks}}', '<div class="task-wrapper">',
		'<form class="submit_nudge">',
		'<input type="submit" class="btn-nudge" value="{{contents}}" />',
		'<input type="hidden" class="tasksId" value="{{id}}" />',
		'<input type="hidden" class="task-complete" value="{{complete}}" />',
		'<div class="nudge-number">{{nudge}}</div>', '</form>', '</div>',
		'{{/each}}' ].join("\n");

HOME.templates.taskInput = ['<input class="task-input" value="Task를 입력하세요." />',
                            '<div class=btn-delete-task></div>'
                           ].join("\n");

