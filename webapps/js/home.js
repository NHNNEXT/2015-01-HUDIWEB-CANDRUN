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
	elements.goalsInNav = querySelectorAll(".nav-goal");
	elements.showGoalSec = querySelector("#show-goal");
}
HOME.methods.addEvents = function() {
	var elements = HOME.elements;
	var nav = HOME.nav;
	for (var i = 0; i < elements.goalsInNav.length; i++) {
		elements.goalsInNav[i].addEventListener("click", function(e) {
			nav.requestGoal(e.target.id);
		});
	}
}

HOME.nav = HOME.nav || {};
HOME.nav.requestGoal = function(id) {
	var util = CANDRUN.util;
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
