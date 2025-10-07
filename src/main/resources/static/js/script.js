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