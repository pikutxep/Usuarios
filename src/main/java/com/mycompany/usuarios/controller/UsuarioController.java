/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuarios.controller;

import com.mycompany.usuarios.bean.Usuario;
import com.mycompany.usuarios.model.UsuariosModel;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hescu
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    UsuariosModel usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getUsers() {

        return usuarioService.obtenerUsuarios();
    }

    @PostMapping("/usuarios")
    void addUser(@Valid @RequestBody Usuario usuario) {
        usuarioService.crearUsuario(usuario);
    }

    @PostMapping("/usuarios/login")
    public ResponseEntity<Integer> login(@Valid @RequestBody Usuario usuario) {
        int result = usuarioService.login(usuario);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
