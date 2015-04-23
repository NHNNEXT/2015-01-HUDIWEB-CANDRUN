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
	elements.nudgeList = querySelectorAll(".nudge_btn");
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
	
	var elements = NUDGE.elements;

	elements.lastSelectedTabNumber = e.target.parentNode.parentNode.querySelector('.tabs__stats .number');
	elements.lastSelectedForm = e.target.parentNode.parentNode.parentNode.querySelector('.tasksId');

	var sUrl = "/tasks";
	var params = "&tasksId=" + elements.lastSelectedForm.value;

	//nav.appendNewNavGoal를 nav.appendNewNavGoal()로 써서 함수 실행시 에러 발생했음
	//error code: unexpected token u
	var addNudgeAjax = new CANDRUN.util.ajax(sUrl, NUDGE.methods.refreshNumber);

	addNudgeAjax.setMethod("POST");
	addNudgeAjax.open();
	addNudgeAjax.setJson();
	addNudgeAjax.send(params);
};

NUDGE.methods.refreshNumber = function (responseText) {
	NUDGE.elements.lastSelectedTabNumber.innerHTML = JSON.parse(responseText).nudge;
};
document.addEventListener("DOMContentLoaded", function() {
	NUDGE.init();
});
>>>>>>> ajaxNudge
