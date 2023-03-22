// Call the dataTables jQuery plugin
$(document).ready(function() {
  //onready para que se ejecute cuando se cargue la pagina
});
//async para que espere a que se ejecute la funcion fetch*/
//fetch para hacer la peticion al servidor*/
async function registrarUsuario(){
   let datos = {};
    datos.nombre = document.querySelector('#txtNombre').value;
    datos.apellido = document.querySelector('#txtApellido').value;
    datos.email = document.querySelector('#txtEmail').value;
    /*datos.telefono = document.querySelector('#telefono').value;*/
    datos.password = document.querySelector('#txtPassword').value;

    let repetirPassword = document.querySelector('#txtRepetirPassword').value;
    if(repetirPassword != datos.password){
        alert('Las contraseñas no coinciden');
        return;
    }

    const request = await fetch(' api/usuarios',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'

        },
        body:JSON.stringify(datos)
    });

    alert('Usuario registrado');
    window.location.href = 'login.html';
}

