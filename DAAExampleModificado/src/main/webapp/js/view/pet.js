var petFormId = 'pet-form';
var petFormQuery = '#' + petFormId;
var petListId = 'pet-list';
var petListQuery = '#' + petListId;
function insertPetList(parent) {
	appendToTablePet(new Pet(1,"sd","asdf",2));
	parent.append(
		'<table id="' + petListId + '">\
			<tr>\
				<th>Nombre</th>\
				<th>Raza</th>\
				<th></th>\
				<th></th>\
			</tr>\
		</table>'
	);
}
function insertPetForm(parent) {
	parent.append(
		'NUEVO ANIMAL\
		<form id="' + peopleFormId + '">\
			<input name="id" type="hidden" value=""/>\
			<input name="idPeople" type="text" value=""/>\
			<input name="name" type="text" value="" />\
			<input name="tipe" type="text" value=""/>\
			<input id="btnSubmitPet" type="submit" value="Create"/>\
			<input id="btnClearPet" type="reset" value="Limpiar"/>\
		</form>'
	);
}

function createPetRow(pet) {
	return '<tr id="pet-'+ pet.id +'">\
		<td class="name">' + pet.name + '</td>\
		<td class="tipe">' + pet.tipe + '</td>\
		<td>\
			<a class="edit" href="#">Edit</a>\
		</td>\
		<td>\
			<a class="delete" href="#">Delete</a>\
		</td>\
	</tr>';
}

function formToPet() {
	var form = $(petFormQuery);
	return {
		'id': form.find('input[name="id"]').val(),
		'name': form.find('input[name="name"]').val(),
		'tipe': form.find('input[name="tipe"]').val(),
		'idPeople': form.find('input[name="idPeople"]').val()
	};
}

function petToForm(pet) {
	var form = $(peopleFormQuery);
	form.find('input[name="id"]').val(pet.id);
	form.find('input[name="name"]').val(pet.name);
	form.find('input[name="tipe"]').val(pet.tipe);
	form.find('input[name="idPeople"]').val(pet.idPeople);
}
function rowToPet(id) {
	var row = $('#pet-' + id);

	return {
		'id': id,
		'name': row.find('td.name').text(),
		'tipe': row.find('td.tipe').text(),
		'idPeople': row.find('td.idPeople').text()
	};
}
function isEditingPet() {
	return $(petFormQuery + ' input[name="id"]').val() != "";
}

function disableFormPet() {
	$(petFormQuery + ' input').prop('disabled', true);
}

function enableFormPet() {
	$(petFormQuery + ' input').prop('disabled', false);
}

function resetFormPet() {
	$(petFormQuery)[0].reset();
	$(petFormQuery + ' input[name="id"]').val('');
	$('#btnSubmit').val('Crear');
}

function showErrorMessagePet(jqxhr, textStatus, error) {
	alert(textStatus + ": " + error);
}

function addRowListeners(pet) {
	$('#pet-' + pet.id + ' a.edit').click(function() {
		petToForm(rowToPet(pet.id));
		$('input#btnSubmit').val('Modificar');
	});
	
	$('#pet-' + pet.id + ' a.delete').click(function() {
		if (confirm('Está a punto de eliminar a una mascota. ¿Está seguro de que desea continuar?')) {
			deletePet(pet.id,
				function() {
					$('tr#pet-' + pet.id).remove();
				},
				showErrorMessagePet()
			);
		}
	});
}

function appendToTablePet(pet) {
	$(petListQuery + ' > tbody:last')
		.append(createPetRow(pet));
	addRowListeners(pet);
}

function initPet() {
	// getScript permite importar otro script. En este caso, se importan las
	// funciones de acceso a datos.
	$.getScript('js/dao/pet.js', function() {
		listPet(function(pet) {
			$.each(pet, function(key, pet) {
				appendToTablePet(pet);
			});
		});
		
		// La acción por defecto de enviar formulario (submit) se sobreescribe
		// para que el envío sea a través de AJAX
		$(peopleFormQuery).submit(function(event) {
			var pet = formToPet();
			
			if (isEditingPet()) {
				modifyPet(pet,
					function(pet) {
						$('#pet-' + pet.id + ' td.name').text(pet.name);
						$('#pet-' + pet.id + ' td.tipe').text(pet.tipe);
						$('#pet-' + pet.id + ' td.tipe').text(pet.idPeople);
						resetFormPet();
					},
					showErrorMessagePet(),
					enableFormPet()
				);
			} else {
				addPet(pet,
					function(pet) {
						appendToTablePet(pet);
						resetFormPet();
					},
					showErrorMessagePet(),
					enableFormPet()
				);
			}
			
			return false;
		});
		
		$('#btnClear').click(resetFormPet);
	});
};
