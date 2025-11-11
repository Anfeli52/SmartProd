let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".sidebarBtn");
let modal = document.getElementById('calculationModal');
let modalCloseBtn = document.querySelector("#modal-close");

sidebarBtn.addEventListener('click', () =>{
    sidebar.classList.toggle("active");
})
modalCloseBtn.addEventListener('click', closeCalculationModal)

function confirmDeleteItem(itemId) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "El Ítem " + itemId + " será eliminado y esta acción es irreversible.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#e74c3c',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = 'registros/delete/' + itemId;
        }
    });
}


function openCalculationModal(itemId) {
    document.getElementById('itemId').value = itemId;
    document.getElementById('modalItemNumber').textContent = itemId;
    document.getElementById('inputPieces').value = ''; // Limpiar campo
    modal.showModal();
}

function closeCalculationModal() {
    modal.close();
}

function confirmDeleteReport(itemId) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "El reporte " + itemId + " será eliminado y esta acción es irreversible.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#e74c3c',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = 'reportes/delete/' + itemId;
        }
    });
}

function confirmDeleteUser(correo) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡No podrás revertir la eliminación del usuario con correo: " + correo + "!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = 'usuarios/delete/' + correo;
        }
    });
}