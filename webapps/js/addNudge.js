/**
 * 
 */

var nudgeList = document.querySelectorAll('.nudge_btn');
for ( var j=0 ; j < nudgeList.length ; j++) {
	nudgeList[j].addEventListener('click', addNudge, false);
}


function addNudge(e) {
	 e.preventDefault();
	 var answerForm = e.currentTarget.form;

	 var url = "/nudges";
	 var params = "tasksId=" + answerForm[0].value;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState === 4 && request.status === 200) {
			var task = JSON.parse(request.responseText);
//			alert(task.nudge);
//			console.log(e.target.parentNode.childNodes[1]);
			 e.target.parentNode.childNodes[3].innerHTML = task.nudge;
		 }
	 };
	 
	 request.send(params);
}
