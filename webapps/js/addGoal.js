
//TODO: 이전 버전 코드와 통합하지 아직 못함 리팩토링 필요.

(function (){

	var inputContainer = document.querySelector(".input_container");
	var addInputTagBtn = document.querySelector(".add_input_btn");
	var submitBtn = document.querySelector(".submit_btn");
	var submitForm = document.querySelector(".submit_form")
	var taskInput = document.querySelector(".task_contents");
	var taskInputCount = 0;


	function enterKeyDown(e){
    	if(e.keyCode == 13){
  	   		addTaskIputTag(e);
  	   	}
	}

	function addTaskIputTag(e){
		
		if(taskInputCount > 3){
			addInputTagBtn.innerHTML = "그만 추가하세요";
			addInputTagBtn.removeEventListener("click", addTaskIputTag);
			return;
		}
		
		//input tag를 만들어, event 추가
		var inputTag= makeInputTag("");
		inputTag.setAttribute("class", "task_contents");
		inputTag.addEventListener("keydown", enterKeyDown);
		inputContainer.appendChild(inputTag);
		
		//맨 마지막 input tag를 제외하고 이벤트 제거
		e.target.removeEventListener("keydown",enterKeyDown);
		taskInputCount++;
	}

	function makeInputTag(value) {
		var input = document.createElement( 'input' );
		input.value = value;
		return input;
	}

	taskInput.addEventListener("keydown", enterKeyDown);
	addInputTagBtn.addEventListener("click", addTaskIputTag);

})();



var GOALS = GOALS || {};

GOALS.run = function() {
	var elBtn = document.querySelector("#btnAddGoal");
	var navGoalContainer = document.querySelector(".nav_goal_container");
	var goalInput = document.querySelector(".goal_contents");
	var taskInputs = document.querySelectorAll(".task_contents");
	var sUrl = "/goals";

	var showNewGoal = function(responseText) {
		 var result = JSON.parse(responseText);
		 var navGoal = makeNavGoal(result.contents);
		 console.log(result.contents);
		 navGoalContainer.appendChild(navGoal);
	}

	var addGoalAjax = new CANDRUN.util.ajax(sUrl, showNewGoal);

	elBtn.addEventListener("click", function(){
		var elInputs = document.querySelectorAll(".input_container input");
		var params = "goal_contents="+goalInput.value;
		
		//TODO: 리팩토링 필요, param이 아닌 json혹은 list 형태로 요청을 보내야 한다. 
		alert(elInputs.length);
		for(var i =0 ; i<elInputs.length; i++){
			params = params + "&task_contents_"+i+"="+elInputs[i].value;
		}
		
		addGoalAjax.open();
		addGoalAjax.setJson();
		addGoalAjax.setMethod("POST");
		addGoalAjax.send(params);  
	});	
}

function makeNavGoal(value){
	var li = document.createElement( 'li' );
	li.innerHTML = value;
	return li;
}

document.addEventListener("DOMContentLoaded", function(e) {
	GOALS.run();
});
