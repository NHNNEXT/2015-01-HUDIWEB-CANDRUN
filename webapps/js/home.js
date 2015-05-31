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
	methods.runInitMethods();
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
	elements.taskIds = querySelectorAll("#show-goal .tasksId");
	elements.userCard = querySelector("#user-card");
	elements.btnLogout = querySelector("#user-card .btn-logout");
	elements.sectionToggle = querySelector("#section-toggle");
	elements.flipContainer = querySelector("#flip-container");
}

HOME.methods.addEvents = function() {
	var elements = HOME.elements;
	var nav = HOME.nav;
	var form = HOME.form;
	
	elements.profilePic.addEventListener("click", nav.userCardToggle);
	elements.btnLogout.addEventListener("click", nav.logout);
	elements.goalSubmit.addEventListener("click", form.send);
	elements.taskInputAdd.addEventListener("click", form.makeNextInput);
	elements.taskInput.addEventListener("click", form.clearInputValue);
	elements.taskInput.addEventListener("keydown", form.makeNextInputWithEnter);
	elements.goalInput.addEventListener("focus", form.clearInputValue);
	elements.sectionToggle.addEventListener("click", nav.sectionChangeToggle);
	for (var i = 0; i < elements.goalsInNav.length; i++) {
		elements.goalsInNav[i].addEventListener("click", function(e) {
			HOME.methods.slideNav(e);	
		});
	}
}

HOME.methods.slideNav = function(e){
	var openGoalId = e.target.id;
	var ani = document.querySelector('#ani');
	ani.classList.remove('active');
	ani.classList.add('active');

	ani.style.transform = 'translate3d(0,0,0)';

	setTimeout(function(){ ani.style.transform= 'translate3d(100%,0,0)'; HOME.nav.requestGoal(openGoalId)}, 500);
	
	setTimeout(function(){ ani.classList.remove('active');
		ani.style.removeProperty('transform'); }, 1500);
}

HOME.methods.runInitMethods = function(){
	var taskList = document.querySelectorAll("#show-goal .task-wrapper");
	var taskIdInputs = document.querySelectorAll(".tasksId");
	var taskIdValues = [];
	
	for(var i=0; i<taskIdInputs.length; i++){
		taskIdValues.push(taskIdInputs[i].value);
	}
	HOME.methods.makeChart(taskIdValues);

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

HOME.methods.makeChart = function (taskIds){
	var sUrl = "/tasks?";
	for(var i =0 ; i<taskIds.length; i++){
		sUrl = sUrl + "&taskIds[]"+"="+taskIds[i];
	}
	var ajax = new CANDRUN.util.ajax(sUrl, HOME.methods.drawChart);
	ajax.open();
	ajax.setSimplePost();
	ajax.send();
}

HOME.methods.drawChart = function (responseText){
	const previousDayLength = 6;
	var taskLogLists = JSON.parse(responseText);
	var data = {labels: [], datasets: []};
	var options={ datasetFill : false};
	setLabels();
	setDatas();
	
	var ctx = document.querySelector("#taskChart").getContext("2d");
	var myLineChart = new Chart(ctx).Line(data, options);
	
	function setLabels(){
		var firstDayIndex = taskLogLists[0].length-1;
		var firstDayString = taskLogLists[0][firstDayIndex].date;
		var firstDay = changeIntoDateFormat(firstDayString);

		//현재 날짜까지 표시해주기 위해 +1을 한다.
		for(var i=0; i<previousDayLength+1;i++){			
			data.labels.push(firstDay.getDate() + i);
		}
	}
	function setDatas(){
		for(var i=0; i<taskLogLists.length;i++){
			var dataContents = makeDataContents(taskLogLists[i], i);		
			data.datasets.push(dataContents);
		}		
	}
	function makeDataContents(taskLogList, number){
		var dataTemplete = {
				pointStrokeColor: "#fff",
				pointHighlightFill: "#fff",
				pointHighlightStroke: "rgba(220,220,220,1)",
				data: []
		}
		var nullCount = 0;
		var curNudgeCount = findCurNudgeCount(taskLogList[0].taskId);

		//먼저 리스트에 들어간 값이 그래프에 왼쪽으로 그려진다. 
		//최신순으로 값을 받아왔으므로 뒤에서부터 dataTemplete에 넣는다. 		
		for(var i=previousDayLength; i>0;i--){	
			if(taskLogList[i]===undefined){
				nullCount++;
			}else{				
				dataTemplete.data.push(taskLogList[i].count);
			}
		}
		for(var i=0; i<nullCount; i++){
			dataTemplete.data.push(0);
		}
		dataTemplete.data.push(curNudgeCount);
	
		setColor(dataTemplete, number);
		return dataTemplete;
	}
	
	function findCurNudgeCount(taskId){
		var taskIds = document.querySelectorAll(".tasksId");
		var nudgeCount = 0;
		for(var i=0; i < taskIds.length ; i++){
			if(taskId==taskIds[i].value){
				nudgeCount = taskIds[i].parentNode.querySelector(".nudge-number");
				return nudgeCount.innerHTML;
			};
		}
	}
	function changeIntoDateFormat(dayString){
		var monthPattern = /^([A-Z])\w+/g;
		var dayPattern = /(\d)+,/g;
		var yearPattern = /, (\d)+/g;	
		var monthMap = {}; 
		monthMap["Jan"] = "01";
		monthMap["Feb"] = "02";
		monthMap["Mar"] = "03";
		monthMap["Apr"] = "04";
		monthMap["May"] = "05";
		monthMap["Jun"] = "06";
		monthMap["Jul"] = "07";
		monthMap["Aug"] = "08";
		monthMap["Sep"] = "09";
		monthMap["Oct"] = "10";
		monthMap["Nov"] = "11";
		monthMap["Dec"] = "12";
		var day = dayPattern.exec(dayString)[0].replace(",","");
		var month = monthMap[monthPattern.exec(dayString)[0]];
		var year = yearPattern.exec(dayString)[0].replace(", ","");
		return new Date(year+"-"+month+"-"+day);
	}
	function setColor(dataTemplete, number){
		var colorSet = ["rgb(29,82,97)", "rgb(86,152,163)", "rgb(245,225,201)", "rgb(161,82,78)", "rgb(97,10,29)"];
		dataTemplete.fillColor = colorSet[number];
		dataTemplete.strokeColor= colorSet[number];
		dataTemplete.pointColor= colorSet[number];
	}
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
	HOME.methods.runInitMethods();
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
		params = params + "&taskContents[]"+"="+inputs[i].value;
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
		'{{/each}}','<canvas id="taskChart" width="800" height="300"></canvas>'
 ].join("\n");

HOME.templates.taskInput = ['<input class="task-input" value="Task를 입력하세요." />',
                            '<div class=btn-delete-task></div>'
                           ].join("\n");

