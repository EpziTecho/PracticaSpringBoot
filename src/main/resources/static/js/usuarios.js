// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailDelUsuario();
});
//async para que espere a que se ejecute la funcion fetch*/
//fetch para hacer la peticion al servidor*/

function actualizarEmailDelUsuario(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}
 async function cargarUsuarios(){

  const request = await fetch(' api/usuarios',{
    method: 'GET',
    headers: getHeaders()
  });
    const usuarios = await request.json();
    console.log(usuarios);

    let listadoHtml = '';
    for(let usuario of usuarios){
        let botonEliminar='<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i></a>'
        let telefono = usuario.telefono == null ? '-' : usuario.telefono;
        let usuarioHtml= '<tr><td>'+ usuario.id +'</td><td>'+ usuario.nombre +''+usuario.apellido+' </td><td>'+usuario.email+'</td><td>'+telefono+'</td><td>'+ botonEliminar+' <a href="#" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a></td></tr>';
        listadoHtml += usuarioHtml;
    }


    document.querySelector('#usuarios tbody').outerHTML= listadoHtml;

}
//para reutilizar el codigo de recuperar el token
function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization':localStorage.token
    }
}
async function eliminarUsuario(id) {
    /*
    alert('eliminando usuario con id: '+id);*/

 if(!confirm('¿Estas seguro de eliminar el usuario?')){
        return;
 }
   const request = await fetch(' api/usuarios/'+id,{
       method: 'DELETE',
         headers: getHeaders()
   });
 location.reload();

}