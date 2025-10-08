let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".sidebarBtn");

sidebarBtn.addEventListener('click', () =>{
    sidebar.classList.toggle("active");
})

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

    // Limpiar resultados anteriores
    const resultDiv = document.getElementById('calculationResult');
    resultDiv.style.display = 'none';
    resultDiv.innerHTML = '';
    resultDiv.classList.remove('alert-success', 'alert-danger');
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