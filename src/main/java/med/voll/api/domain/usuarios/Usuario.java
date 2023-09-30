package med.voll.api.domain.usuarios;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String clave;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        //para darle un rol al usuario y pueda acceder
    }
    @Override
    public String getPassword() {
        return clave; //indicando que clave es la contraseña
    }
    @Override
    public String getUsername() {
        return login; //indicando que el login es nuestro usuario
    } 
    @Override
    public boolean isAccountNonExpired() {
        return true; //la cuenta no esta expirada
    }
    @Override
    public boolean isAccountNonLocked() {
        return true; //la cuenta no esta bloqueada
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //la credencial no ha expirado
    }
    @Override
    public boolean isEnabled() {
        return true; //porque si esta habilitado
    }
}
