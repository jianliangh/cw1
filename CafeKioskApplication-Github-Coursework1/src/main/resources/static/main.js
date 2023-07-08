$(document).ready(function() {
	const maxLength = 16;
	const cvvLength = 3;

	$('#banknumber').on('input', function() {
		if ($(this).val().length > maxLength) {
			$(this).val($(this).val().slice(0, maxLength));
		}
	});

	$('#cvv').on('input', function() {
		if ($(this).val().length > cvvLength) {
			$(this).val($(this).val().slice(0, cvvLength)); 
		} 
	});

});