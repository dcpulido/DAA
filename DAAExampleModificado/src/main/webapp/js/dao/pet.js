function listPes(done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/pet',
		type: 'GET'
	})
	.done(done)
	.fail(fail)
	.always(always);
}

function addPet(person, done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/pet',
		type: 'POST',
		data: pet
	})
	.done(done)
	.fail(fail)
	.always(always);
}

function modifyPet(person, done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/pet/' + pet.id,
		type: 'PUT',
		data: pet
	})
	.done(done)
	.fail(fail)
	.always(always);
}

function deletePet(id, done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/pet/' + id,
		type: 'DELETE',
	})
	.done(done)
	.fail(fail)
	.always(always);
}