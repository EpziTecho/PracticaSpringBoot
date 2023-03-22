package com.crudspringboot.crudspringboot.dao;


import com.crudspringboot.crudspringboot.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario>getUsuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    boolean verificarEmailPassword(Usuario usuario);

    Usuario ObtenerUsuariopPorCredenciales(Usuario usuario);

}
