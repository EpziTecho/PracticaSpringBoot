package com.crudspringboot.crudspringboot.controllers;

import com.crudspringboot.crudspringboot.dao.UsuarioDao;
import com.crudspringboot.crudspringboot.models.Usuario;
import com.crudspringboot.crudspringboot.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Para controlar el login de los usuarios
@RestController
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UsuarioDao usuarioDao;


    @RequestMapping(value = "api/login",method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        //Aqui se va a hacer la logica para el login
        Usuario usuarioLogeado=usuarioDao.ObtenerUsuariopPorCredenciales(usuario);
        if(usuarioLogeado!=null){
           String tokenJWT = jwtUtil.create(String.valueOf(usuarioLogeado.getId()), usuarioLogeado.getEmail());
           //aqui podemos devolver permisos, roles, etc
            return tokenJWT;

        }
        return "ERROR";

    }


}
