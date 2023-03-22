package com.crudspringboot.crudspringboot.controllers;


import com.crudspringboot.crudspringboot.dao.UsuarioDao;
import com.crudspringboot.crudspringboot.models.Usuario;
import com.crudspringboot.crudspringboot.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // Esto indica que es un controlador
public class UsuarioController {
    @Autowired // Esto indica que es una inyeccion de dependencia y que se va a inyectar el usuarioDao
    private UsuarioDao usuarioDao; // Esto indica que es una variable y que se va a inyectar el usuarioDao
    @Autowired
    private JWTUtil jwtUtil;
    @RequestMapping(value = "api/usuario/{id}",method = RequestMethod.GET)
    // Esto indica que es una ruta y sirve para que se pueda acceder a la ruta desde el navegador
    public Usuario getUsuario(@PathVariable Long id) { // Esto indica que es un metodo y que recibe un parametro
        Usuario usuario = new Usuario();
        usuario.setId(id); //
        usuario.setNombre("Sergio");
        usuario.setApellido("Huayllas");
        usuario.setEmail("Sergius6ht@gmail.com");
        usuario.setTelefono("999999999");
        return usuario;

    }
    //Con @RequestHeader se puede obtener el token que se envia desde el front end para poder validar el token y asi poder acceder a los datos
    @RequestMapping(value = "api/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader("Authorization") String token) {
        if(!validarToken(token)){
            return null;
        }
      return usuarioDao.getUsuarios();

    }
    private boolean validarToken(String token){
        String usuarioId=jwtUtil.getKey(token);
        return usuarioId !=null; // Esto es para validar el token y si es valido devuelve true y si no es valido devuelve false
    }
    @RequestMapping(value = "api/usuarios/{id}",method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token ,@PathVariable Long id){
        if(!validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "api/usuarios",method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2= Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); // Esto es para encriptar la contraseña del usuario y se guarda en la variable argon2
        String hash= argon2.hash(1,1024,1,usuario.getPassword()); // Esto es para encriptar la contraseña del usuario
        usuario.setPassword(hash); // Esto es para guardar la contraseña encriptada en la base de datos y se guarda en la variable usuario
        usuarioDao.registrar(usuario);


    }

}
