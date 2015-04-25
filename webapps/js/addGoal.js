
var GOAL = GOAL || {};
GOAL.methods = {};
GOAL.elements = {};
GOAL.form = {};
GOAL.nav = {};

GOAL.init = function() {
	var methods = GOAL.methods;
	methods.getElements();
	methods.addEvents();
};

GOAL.methods.getElements = function() {
	var querySelector = CANDRUN.util.querySelector;
	var querySelectorAll = CANDRUN.util.querySelectorAll;
	var elements = GOAL.elements;
	elements.goalInput = querySelector("#make-goal .goal-input");
	elements.goalSubmit = querySelector("#make-goal .goal-form-submit");
	elements.navGoalContainer = querySelector(".nav-goal-container");
	elements.taskInput = querySelector(".task-input");
	elements.taskInputs = querySelectorAll(".task-input");
	elements.taskInputContainer = querySelector("#make-goal .input-container");
	elements.taskInputAdd = querySelector("#make-goal .task-input-add");
	
};

GOAL.methods.addEvents = function() {
	var elements = GOAL.elements;
	var form = GOAL.form;
	elements.goalSubmit.addEventListener("click", form.send);
	elements.taskInput.addEventListener("keydown", form.makeNextInputWithEnter);
	elements.taskInputAdd.addEventListener("click", form.makeNextInput);
};

GOAL.form.send = function () {

	var sUrl = "/goals";
	var params = "goal_contents="+GOAL.elements.goalInput.value;

	//nav.appendNewNavGoal를 nav.appendNewNavGoal()로 써서 함수 실행시 에러 발생했음
	//error code: unexpected token u
	var addGoalAjax = new CANDRUN.util.ajax(sUrl, GOAL.nav.appendNewNavGoal);
	var inputs = document.querySelectorAll(".task-input");

	for(var i =0 ; i<inputs.length; i++){
		params = params + "&task_contents_"+i+"="+inputs[i].value;
	}
	
	addGoalAjax.setMethod("POST");
	addGoalAjax.open();
	addGoalAjax.setJson();
	addGoalAjax.send(params);
};

GOAL.form.makeNextInputWithEnter = function(e){
	if(e.keyCode === 13){
		GOAL.form.makeNextInput();
	}
};

GOAL.form.makeNextInput = function(){
	var input = document.createElement('input');

	//마지막 inputTag의 이벤트를 제거
	var inputNodes = document.querySelectorAll(".task-input");
	var lastTaskInput = inputNodes[inputNodes.length-1];
	lastTaskInput.removeEventListener("keydown", form.makeNextInputWithEnter);

	//inputTag를 만들어 InputContainer의 자식으로 등록
	input.setAttribute("class", "task-input");
	input.addEventListener("keydown", GOAL.form.makeNextInputWithEnter);
	GOAL.elements.taskInputContainer.appendChild(input);
};

GOAL.nav.appendNewNavGoal = function (responseText) {
	var navGoal = GOAL.nav.makeNavGoal(JSON.parse(responseText).contents);
	GOAL.elements.navGoalContainer.appendChild(navGoal);
};

GOAL.nav.makeNavGoal= function (value) {
	var li = document.createElement( 'li' );
	li.innerHTML = value;
	li.setAttribute("class", "nav-goal");
	return li;
};

document.addEventListener("DOMContentLoaded", function() {
	GOAL.init();
});
