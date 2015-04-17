/**
* writer: cob first edited date: 2015.04.06 last edited date: 2015.04.06
* related project: 9bagi by candrun made for control friends list
*/

var FRIENDS = FRIENDS || {};

// 구현부
FRIENDS.run = function() {
	var elBtn = document.querySelector("#btnLoadFriends");
	var elUl = document.querySelector("#ulFriendsList");
	var friendsCaller = new FRIENDS.classes.Caller(elBtn, elUl,
	"/getFriends.cdr");
	var fAfterEvents = [ function() {
		FRIENDS.classes.ListMaker(elUl, friendsCaller.getFriends());
	}, function() {
		FRIENDS.classes.AddSelectEvnt(elUl);
	} ]
	friendsCaller.setAfterCallEvnts(fAfterEvents);
}

FRIENDS.classes = {};
FRIENDS.classes.ListMaker = function(elUl, lists) {
	var htmlLists = "";
	if (lists && lists.length && lists.length > 0) {
		for ( var idx in lists) {
			htmlLists += "<li id=" + lists[idx].email + ">"
			+ lists[idx].nickname + "</li>";
		}
		elUl.innerHTML = htmlLists;
	}
}
FRIENDS.classes.AddSelectEvnt = function(elUl) {
	var addSelected = function(e) {
		e.target.className += " selected";
	}
	var removeSelected = function(e) {
		e.target.className = e.target.className.replace(" selected", "");
	}
	var isSelected = function(e) {
		if (e.target.className.indexOf("selected") > -1) {
			return true;
		}
		return false;
	}
	var fMagageSelected = function(e) {
		if (isSelected(e)) {
			removeSelected(e);
			return;
		}
		addSelected(e);
		return;
	}
	if (elUl) {
		elUl.addEventListener("click", fMagageSelected);
	}
}
FRIENDS.classes.Caller = function(elBtn, elUl, url, fAfterCallEvnts) {
	var elLoader = elBtn;
	var elTargetUl = elUl;
	var sUrl = url;
	var oFriends = {};
	var fAfterCallEvents = fAfterCallEvnts;

	var fSucces = function(sRespText) {
		oFriends = JSON.parse(sRespText);
		for ( var idx in fAfterCallEvents) {
			fAfterCallEvents[idx]();
		}
	}

	this.addEvent = function() {
		if (elLoader && sUrl) {
			var getFriendsAjax = new CANDRUN.util.ajax(sUrl, fSucces);
			getFriendsAjax.setJson();
			elLoader.addEventListener("click", function() {
				getFriendsAjax.send();
			});
			console.log("Event added.")
		} else {
			console.log("Event not added.")
		}
	}

	this.setElLoader = function(el) {
		elLoader = el;
	}
	this.setUrl = function(u) {
		sUrl = u;
	}
	this.getFriends = function() {
		return oFriends;
	}
	this.setAfterCallEvnts = function(fEvnts) {
		fAfterCallEvents = fEvnts;
	}
	if (elLoader && url) {
		this.addEvent();
	}
}

document.addEventListener("DOMContentLoaded", function(e) {
	FRIENDS.run();
});
