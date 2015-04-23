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
	 var url = "/tasks";
	 var params = "tasksId=" + answerForm[0].value;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState === 4 && request.status === 200) {
			var task = JSON.parse(request.responseText);
			 e.target.parentNode.querySelector('.task-nudge').innerHTML = task.nudge;
		 }
	 };
	 
	 request.send(params);
}
