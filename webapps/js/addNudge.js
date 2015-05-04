/**
 * 
 */

var NUDGE = NUDGE || {};
NUDGE.methods = {};
NUDGE.elements = {};

NUDGE.init = function() {
	var methods = NUDGE.methods;
	methods.getElements();
	methods.addEvents();
};
NUDGE.methods.getElements = function() {
	var querySelector = CANDRUN.util.querySelector;
	var querySelectorAll = CANDRUN.util.querySelectorAll;
	var elements = NUDGE.elements;
	elements.nudgeList = querySelectorAll(".btn-nudge");
	elements.numberToNudge;
	elements.taskIdToNudge;
};

NUDGE.methods.addEvents = function() {
	var nudgeList = NUDGE.elements.nudgeList;
	
	for ( var idx in nudgeList) {
		if (nudgeList[idx] >= nudgeList.length)
			return;
		var tempEl = nudgeList[idx];
		tempEl.addEventListener("click", NUDGE.methods.addNudge);
		tempEl.addEventListener("click", NUDGE.methods.checkComplete);
	}
};

NUDGE.methods.addNudge = function (e) {
	e.preventDefault();
	
	var elements = NUDGE.elements;

	elements.numberToNudge = e.target.parentNode.parentNode.querySelector('.nudge-number');
	elements.taskIdToNudge = e.target.parentNode.querySelector('.tasksId');

	var sUrl = "/tasks";
	var params = "&tasksId=" + elements.taskIdToNudge.value;
	var addNudgeAjax = new CANDRUN.util.ajax(sUrl, NUDGE.methods.refreshNumber);

	addNudgeAjax.setMethod("POST");
	addNudgeAjax.open();
	addNudgeAjax.setSimplePost();
	addNudgeAjax.send(params);

};

NUDGE.methods.checkComplete = function(e){
	if(e.target.parentNode.childNodes[5].value === "true"){
		e.target.value ="너나 잘하시오";
		e.target.className = "btn-nudge background-pink";
	}
	else{
		e.target.value= "넛지 감사";
		e.target.className ="btn-nudge background-red";
	}
};

NUDGE.methods.refreshNumber = function (responseText) {
	NUDGE.elements.numberToNudge.innerHTML = JSON.parse(responseText).nudge;

};
document.addEventListener("DOMContentLoaded", function() {
	NUDGE.init();
});
