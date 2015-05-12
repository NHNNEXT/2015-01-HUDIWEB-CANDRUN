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
	elements.taskList = querySelectorAll("#show-goal .task-wrapper");
	elements.userCard = querySelector("#user-card");
	elements.btnLogout = querySelector("#user-card .btn-logout");
	elements.sectionToggle = querySelector("#section-toggle");
	elements.flipContainer = querySelector("#flip-container");
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
	elements.sectionToggle.addEventListener("click", nav.sectionChangeToggle)
	
	var taskList = elements.taskList;

	for ( var idx in taskList) {
		if (taskList[idx] >= taskList.length)
			return;
		var nudgeEl = taskList[idx];
		nudgeEl.addEventListener("click", HOME.methods.addNudge);
		nudgeEl.addEventListener("click", HOME.methods.checkComplete);
		HOME.methods.checkGlow(nudgeEl);
	}
}

HOME.methods.addNudge = function (e) {
	e.preventDefault();

	var elements = HOME.elements;

	elements.numberToNudge = e.target.parentNode.querySelector('.nudge-number');
	elements.taskIdToNudge = e.target.parentNode.querySelector('.tasksId');
	elements.goalOwnerEmail = e.target.parentNode.parentNode.parentNode.querySelector('.goal-owner-email');
	var sUrl = "/tasks";
	var params = "&tasksId=" + elements.taskIdToNudge.value+"&goalOwnerEmail="+ elements.goalOwnerEmail.value;
	var ajax = new CANDRUN.util.ajax(sUrl, HOME.methods.refreshNumber);

	ajax.setMethod("POST");
	ajax.open();
	ajax.setSimplePost();
	ajax.send(params);
};

HOME.methods.checkGlow = function(nudgeEl){
	var taskCompleteEl = nudgeEl.querySelector('.task-complete');
	if(taskCompleteEl.value === "false"){
		nudgeEl.classList.add("glow");
	}
};

HOME.methods.checkComplete = function(e){
	if(e.target.parentNode.childNodes[5].value === "true"){
		e.target.value ="너나 잘하시오";
		e.target.className = "btn-nudge background-pink";
	}
	else{
		e.target.value= "넛지 감사";
		e.target.className ="btn-nudge background-red";
	}
};

HOME.methods.refreshNumber = function (responseText) {
	HOME.elements.numberToNudge.innerHTML = JSON.parse(responseText).nudge;

};


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

	HOME.elements.taskList = document.querySelectorAll("#show-goal .task-wrapper");
	var nudgeList = HOME.elements.taskList;
	console.log(nudgeList);
	for (var idx in nudgeList) {
		if (nudgeList[idx] >= nudgeList.length)
			return;
		var nudgeEl = nudgeList[idx];
		nudgeEl.addEventListener("click", HOME.methods.addNudge);
		nudgeEl.addEventListener("click", HOME.methods.checkComplete);
		HOME.methods.checkGlow(nudgeEl);
	}
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
HOME.nav.sectionChangeToggle = function(){
	HOME.elements.flipContainer.classList.toggle('flip-container');
}


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

