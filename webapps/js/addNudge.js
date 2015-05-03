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
	elements.lastSelectedTabNumber;
	elements.lastSelectedForm;
};

NUDGE.methods.addEvents = function() {
	var nudgeList = NUDGE.elements.nudgeList;
	
	for ( var idx in nudgeList) {
		if (nudgeList[idx] >= nudgeList.length)
			return;
		var tempEl = nudgeList[idx];
		tempEl.addEventListener("click", NUDGE.methods.addNudge);
	}
};

NUDGE.methods.addNudge = function (e) {
	e.preventDefault();
//	
//	var elements = NUDGE.elements;
//
//	elements.lastSelectedTabNumber = e.target.parentNode.parentNode.querySelector('.nudge-number');
//	elements.lastSelectedForm = e.target.parentNode.querySelector('.tasksId');
//
//	var sUrl = "/tasks";
//	var params = "&tasksId=" + elements.lastSelectedForm.value;
//	var addNudgeAjax = new CANDRUN.util.ajax(sUrl, NUDGE.methods.refreshNumber);
//
//	addNudgeAjax.setMethod("POST");
//	addNudgeAjax.open();
//	addNudgeAjax.readyParam();
//	addNudgeAjax.send(params);
	NUDGE.methods.refreshNumber({"nudge":"nudge"});

};

NUDGE.methods.checkComplete = function(){
	var taskContents = document.getElementById('taskContents');
	if(document.querySelector('.taskComplete').value == "true"){
		taskContents.value ="너나 잘하시오";
		taskContents.className = "btn-nudge background-pink";
	}
	else{
		taskContents.value= "넛지 감사";
		taskContents.className ="btn-nudge background-red";
	}
};

NUDGE.methods.refreshNumber = function (responseText) {
//	NUDGE.elements.lastSelectedTabNumber.innerHTML = JSON.parse(responseText).nudge;
	NUDGE.methods.checkComplete();

};
document.addEventListener("DOMContentLoaded", function() {
	NUDGE.init();
});
