package com.agp.geek.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import com.agp.geek.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false)
    @NotBlank(message = "O nome é obrigatório!")
    private String nome;

    @NotBlank(message = "O email é obrigatório!")
    @Email(message = "Email fora do formato esperado!")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória!")
    @Column(name = "senha", length = 60, nullable = false)
    private String senha;

    @Column(name = "nome_completo", nullable = false)
    @NotBlank(message = "O nome completo é obrigatório!")
    private String nomeCompleto;

    @Column(name = "data_nascimento", nullable = false)
    @NotNull(message = "A data de nascimento deve ser informada!")
    private LocalDate dataNascimento;

    @Column(name = "apelido", nullable = false, unique = true)
    @NotBlank(message = "É obrigatório informar um apelido!")
    private String apelido;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull(message = "A lista de (roles) não pode ser nula")
    @Size(min = 1, message = "É necessário ao menos uma (role) para o usuário")
    private Set<RoleType> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.apelido;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", apelido='" + apelido + '\'' +
                ", roles=" + roles +
                '}';
    }
}
