/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuarios.model.impl;

import com.mycompany.usuarios.bean.Usuario;
import com.mycompany.usuarios.entity.UsuarioEntity;
import com.mycompany.usuarios.model.UsuariosModel;
import com.mycompany.usuarios.repo.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author hescu
 */
@Service
@Transactional
public class UsuariosModelImpl implements UsuariosModel {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void crearUsuario(Usuario usuario) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(usuario.getEmail());
        if (!usuarioEntity.isPresent()) {
            UsuarioEntity usuarioEntityToSave = this.usuarioToEntity(usuario);
            usuarioRepository.save(usuarioEntityToSave);
        }
    }

    private UsuarioEntity usuarioToEntity(Usuario usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setEdad(usuario.getEdad());
        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setNombre(usuario.getNombre());
        usuarioEntity.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioEntity;
    }

    private Function<UsuarioEntity, Usuario> entityToUsuario = usuarioEntity -> {
        Usuario usuario = new Usuario();
        usuario.setEdad(usuarioEntity.getEdad());
        usuario.setEmail(usuarioEntity.getEmail());
        usuario.setNombre(usuarioEntity.getNombre());
        usuario.setPassword(usuarioEntity.getPassword());

        return usuario;
    };

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();
        return usuarioEntities.stream().map(entityToUsuario::apply).collect(Collectors.toList());
    }

    @Override
    public Usuario obtenerUsuario(Long id) throws NotFoundException {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);
        if (usuarioEntity.isPresent()) {
            return entityToUsuario.apply(usuarioEntity.get());
        } else {
            throw new NotFoundException("Usuario no encontrado");
        }
    }

    @Override
    public int login(Usuario usuario) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEntity != null) {
            boolean isPasswordMatch = passwordEncoder.matches(usuario.getPassword(), usuarioEntity.get().getPassword());
            if (isPasswordMatch) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }

    }

}
