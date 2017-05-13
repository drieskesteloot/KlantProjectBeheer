$(document).ready(function () {
	$("#submitTijdsregistraties").click(function(e){
		e.preventDefault();
		var ids = [];
		
		$(".trid").each(function(){
			ids.push($(this).text());
		});
		
		$.ajax({
			type : "POST",
			url : "/updateUnsubmittedTijdregistraties",
			contentType: "application/json",
			data : ids,
			dataType: 'json',
			success : function(response) {
					alert('Ja' + response);
			    },
			    error : function(e) {
			    	alert('Error: ' + e);
			    }
	    });
	});
});