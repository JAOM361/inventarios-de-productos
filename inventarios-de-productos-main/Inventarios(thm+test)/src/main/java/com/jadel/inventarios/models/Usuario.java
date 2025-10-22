package com.jadel.inventarios.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=100)
    private String nombre;

    @NotBlank
    @Email
    @Size(max=150)
    private String correo;

    @NotBlank
    @Size(min=4, max=255)
    private String contrasena;

    @NotBlank
    private String rol;

    public Usuario() {}

    public Usuario(String nombre, String correo, String contrasena, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
    public String getCorreo(){return correo;}
    public void setCorreo(String correo){this.correo=correo;}
    public String getContrasena(){return contrasena;}
    public void setContrasena(String contrasena){this.contrasena=contrasena;}
    public String getRol(){return rol;}
    public void setRol(String rol){this.rol=rol;}
}
