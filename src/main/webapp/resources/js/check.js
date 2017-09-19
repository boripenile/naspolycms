function SetAllCheckBoxes(FormName, AreaID, CheckValue)
{
    if (!document.forms[FormName])
        return;
    var objCheckBoxes = document.getElementById(AreaID).getElementsByTagName('input');
    if (!objCheckBoxes)
        return;
    var countCheckBoxes = objCheckBoxes.length;
    if (!countCheckBoxes)
        objCheckBoxes.checked = CheckValue;
    else
        for (var i = 0; i < countCheckBoxes; i++)
            objCheckBoxes[i].checked = CheckValue;
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || (charCode > 57 && charCode != 190 && charCode != 110)))
      return false;

   return true;
}


function testValue(input){  
  var re = /^[0-9]+\.?[0-9]*$/; 
  var OK = re.exec(input.value);  
  if (!OK){
    input.value = '0';
    input.focus();
  }
}  

function testValueMax(input, input2){  
	  var re = /^[0-9]+\.?[0-9]*$/; 
	  var second = document.getElementById(input2).value;
	  var OK = re.exec(input.value); 
	  if (!OK){
		  input.value = '0';
		  input.focus();
	  }
	  else if (OK){
		  var floatFirst = parseFloat(input.value);
		  var floatSecond = parseFloat(second);
		  if(floatFirst >  floatSecond){
			  input.value = '0';
			  input.focus();
		  }   
	  }
}	

function testValueMax2(input, input2){  
	  var re = /^[0-9]+\.?[0-9]*$/; 
	  var second = document.getElementById(input2).value;
	  var OK = re.exec(input.value); 
	  if (!OK){
		  input.value = '0';
		  input.focus();
	  }
	  else if (OK){
		  var floatFirst = parseFloat(input.value);
		  var floatSecond = parseFloat(second);
		  if(floatFirst >  floatSecond){
			  input.value = '0';
			  input.focus();
		  }   
	  }
}	  

function testMinValue(input, input2){  
	  var re = /^[0-9]+\.?[0-9]*$/; 
	  var second = document.getElementById(input2).value;
	  var OK = re.exec(input.value); 
	  if (!OK){
		  input.value = '0';
		  input.focus();
	  }
	  else if (OK){
		  var floatFirst = parseFloat(input.value);
		  var floatSecond = parseFloat(second);
		  if(floatFirst >=  floatSecond){
			  input.value = '0';
			  input.focus();
		  }   
	  }
}	

function testMaxValue(input, input2){  
	  var re = /^[0-9]+\.?[0-9]*$/; 
	  var second = document.getElementById(input2).value;
	  var OK = re.exec(input.value); 
	  if (!OK){
		  input.value = '0';
		  input.focus();
	  }
	  else if (OK){
		  var floatFirst = parseFloat(input.value);
		  var floatSecond = parseFloat(second);
		  if(floatFirst <=  floatSecond){
			  input.value = floatSecond;
			  input.focus();
		  }   
	  }
}	
