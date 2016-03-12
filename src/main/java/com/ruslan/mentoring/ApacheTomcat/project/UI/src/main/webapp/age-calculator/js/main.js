$(document).ready(function () {
    $('.calculate-button').click(function () {
        $('.wrapper').fadeOut(function () {
        	var url = $('.calculator-form').attr('action'),
        		data = {
            		firstName: $('#first-name').val(),
            		lastName: $('#last-name').val(),
            		birthday: $('#birthday').val()
            	},
            	success = function (data) {
            		$('#result').html(data);
            	};

            $.post(url, data, success);
        });
    });
});