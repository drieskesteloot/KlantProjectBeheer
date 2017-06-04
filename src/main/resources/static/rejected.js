$(document).ready(function () {

	$.ajax({
        type: "GET",
        url: "/rejectedTijdsregistraties",
        //data: { userId: Id },
        //contentType: "application/json",
        dataType: 'json',
        success: function (result) {
        	if(result.length > 0){
        		localStorage.removeItem('alerted');
        		var alerted = sessionStorage.getItem('alerted') || '';
                if (alerted != 'yes') {
	                alert('U heeft afgekeurde tijdsregistraties!\n' + joinObj(result, 'omschrijving').join("\n"));
	                sessionStorage.setItem('alerted','yes');
                } 
        	}
        },
        error: function (e) {
            //alert('error');
        }
    });
});

function joinObj(a, attr) {
	  var out = []; 
	  for (var i=0; i<a.length; i++) {  
	    out.push("Omschrijving: " + a[i][attr]); 
	  } 
	 return out;
}