/**
 * 
 */

var goalList = document.querySelectorAll('.nav-goal span');
for( var i=0; i < goalList.length; i++){
	goalList[i].addEventListener('click', slideOut, false);
}

function slideOut(e){
	e.preventDefault();
	
	var 타켓한 e.target vs e.currentTarget
	var url="/changeContent";
	var params = "questionId=" + answerForm[0].value ;
	
	var request = new XMLHttpRequest();
	request.open("GET", url, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded";)
	request.send(params);
	
	request.onreadystatechange = function(){
		if(request.readyState==4 && request.status==200){
			location.reload(true);
		}
	}
}