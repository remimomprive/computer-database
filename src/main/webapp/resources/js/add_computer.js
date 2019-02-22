(function($) {
	$(document).on('submit', 'form', function() {
		var introduced_input = $("#introduced")[0];
		var discontinued_input = $("#discontinued")[0];
		
		var introduced = introduced_input.value;
		var discontinued = discontinued_input.value;
		
		var introducedDate = new Date(introduced).getDate();
		var discontinuedDate = new Date(discontinued).getDate();
		
		$(introduced_input.closest('.form-group')).removeClass('has-error');
		$(discontinued_input.closest('.form-group')).removeClass('has-error');
		
		if ((!isNaN(introducedDate) && !isNaN(discontinuedDate) && introducedDate > discontinuedDate) ||
				(isNaN(introducedDate) && !isNaN(discontinuedDate))) {
			$(introduced_input.closest('.form-group')).addClass('has-error');
			$(discontinued_input.closest('.form-group')).addClass('has-error');
			return false;
		}
		
		return true;
	});
}(jQuery));