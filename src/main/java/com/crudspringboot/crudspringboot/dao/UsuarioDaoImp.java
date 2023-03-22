package com.crudspringboot.crudspringboot.dao;

import com.crudspringboot.crudspringboot.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository // Esto indica que es un repositorio
@Transactional // Esto indica que es una transaccion y que se va a realizar una transaccion con la base de datos y que se va a guardar en la base de datos
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext //Para que se pueda inyectar el entity manager en el dao y se pueda hacer la consulta a la base de datos
    EntityManager entityManager;

    @Override
     public List<Usuario> getUsuarios(){
        String query = "FROM Usuario";
        //List <Usuario> usuarios = entityManager.createQuery(query).getResultList(); // Esto es para hacer la consulta a la base de datos y se guarda en la lista de usuarios
        return entityManager.createQuery(query).getResultList();
     }

    @Override
    public void eliminar(Long id) {
        Usuario usuario= entityManager.find(Usuario.class,id); // Esto es para buscar el usuario por el id y se guarda en la variable usuario y se va a eliminar
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario); // Esto es para guardar el usuario en la base de datos

    }

    @Override
    public boolean verificarEmailPassword(Usuario usuario) {
        return false;
    }

    public Usuario ObtenerUsuariopPorCredenciales(Usuario usuario){
        String query ="FROM Usuario WHERE email=:email";
        List<Usuario> lista= entityManager.createQuery(query).setParameter("email",usuario.getEmail()).getResultList();

            //Controlar el null exception
            if(lista.isEmpty()){
                return null;
            }

        String passwordHashed= lista.get(0).getPassword(); //Obtener la password del objeto usuario de la lista y se guarda en la variable passwordHashed y se va a verificar
        //Verificar password encriptado con argon2
        Argon2 argon2= Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(passwordHashed,usuario.getPassword())){
            return lista.get(0); //Devuelve el usuario de la lista si la contraseña es correcta
        }

        return null; //Devuelve null si la contraseña es incorrecta

       /* return !lista.isEmpty();*/
    }

}
