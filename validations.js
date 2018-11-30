
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

function init() {
var today = new Date();
var todayPlus2 = new Date();
todayPlus2.setDate(today.getDate()+2);

document.getElementById('checkin').valueAsDate = today;
document.getElementById('checkout').valueAsDate = todayPlus2;
}

function validateInput(){
var checkIn = document.getElementById('checkin').value;
var checkout = document.getElementById('checkout').value;
if(checkIn>checkout){alert("Please select valid Check In and Check Out dates");}
else{document.getElementById("homePageForm").submit();}
}
