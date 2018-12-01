
function validateNumber(evt) {
  var theEvent = evt || window.event;
  if (theEvent.type === 'paste') {
    key = event.clipboardData.getData('text/plain')
  }
  else {
    var key = theEvent.keyCode || theEvent.which;
    key = String.fromCharCode(key)
  }
  var regex = /[0-9]|\\./;
  if( !regex.test(key) ) {
    theEvent.returnValue = false;
    if(theEvent.preventDefault)
      theEvent.preventDefault();
  }
}


function validateInput(){
var checkIn = document.getElementById('checkin').value;
var checkout = document.getElementById('checkout').value;
if(checkIn>checkout){alert("Please select valid Check In and Check Out dates");}
else{document.getElementById("homePageForm").submit();}
}

function selectPaymentMethod(radioGroup){
if(radioGroup.value == 'card'){
document.getElementById("cardpayment").style.display = "block";
document.getElementById("directpayment").style.display = "none";
}
else
{
document.getElementById("cardpayment").style.display = "none";
document.getElementById("directpayment").style.display = "block";
}
}
