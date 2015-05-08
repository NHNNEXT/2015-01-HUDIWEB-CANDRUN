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
	elements.userCard = querySelector("#user-card");
	elements.profilePic = querySelector(".profile-picture");
	elements.btnLogout = querySelector(".btn-logout");

}
HOME.methods.addEvents = function() {
	var elements = HOME.elements;
	var nav = HOME.nav;
	for (var i = 0; i < elements.goalsInNav.length; i++) {
		elements.goalsInNav[i].addEventListener("click", function(e) {
			nav.requestGoal(e.target.id);
		});
	}
	elements.profilePic.addEventListener("click", nav.userCardToggle);
	elements.btnLogout.addEventListener("click", nav.logout);
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

HOME.nav.userCardToggle = function(){
	if(HOME.elements.userCard.style.display==="block"){
		HOME.elements.userCard.style.display = "none";
	}else{
		HOME.elements.userCard.style.display = "block";
	}
}

HOME.nav.logout = function(){
	var util = CANDRUN.util;
	var ajax = new CANDRUN.util.ajax("/auth", function(){
		location.href="http://localhost:8080";
	});
	ajax.setMethod("DELETE");
	ajax.open();
	ajax.setSimplePost();
	ajax.send();
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
