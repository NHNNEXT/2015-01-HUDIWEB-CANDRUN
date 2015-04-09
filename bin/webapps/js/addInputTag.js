(function (){

	var inputContainer = document.querySelector(".input_container");
	var addInputTagBtn = document.querySelector(".add_input_btn");
	var submitBtn = document.querySelector(".submit_btn");
	var submitForm = document.querySelector(".submit_form")

	addInputTagBtn.addEventListener("click", function(){
		var inputTag= makeInputTag("");
		inputContainer.appendChild(inputTag);
	})


	submitBtn.addEventListener("click", function(){
		var elInputs = document.querySelectorAll(".input_container input");
		var arrInputValues =  new Array(elInputs.length);
		
		for(var i=0; i<elInputs.length; i++){
			arrInputValues[i] = elInputs[i].value;
		}

		var strInputValues = arrInputValues.toString();

		var strContainer = document.createElement("input");
		strContainer.setAttribute("name", "task_contents");
		strContainer.setAttribute("type", "hidden");
		strContainer.setAttribute("value", strInputValues);

		submitForm.appendChild(strContainer);

		submitForm.submit();
	})

})();


function makeInputTag(value) {
    var input = document.createElement( 'input' );
    input.value = value;
	return input;
}