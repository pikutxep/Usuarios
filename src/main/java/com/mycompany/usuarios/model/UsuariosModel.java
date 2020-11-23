/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuarios.model;

import com.mycompany.usuarios.bean.Usuario;
import java.util.List;
import javassist.NotFoundException;

/**
 *
 * @author hescu
 */
public interface UsuariosModel {

    public void crearUsuario(Usuario usuario);

    public List<Usuario> obtenerUsuarios();

    public Usuario obtenerUsuario(Long id) throws NotFoundException;

    public int login(Usuario usuario);
}
