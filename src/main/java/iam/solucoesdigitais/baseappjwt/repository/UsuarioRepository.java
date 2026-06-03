package iam.solucoesdigitais.baseappjwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iam.solucoesdigitais.baseappjwt.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar usuário por login
    Usuario findUserByUsername(String username);
    
	    // Adicione o método para buscar usuário por email
	    //Usuario findByEmail(String email);
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByCpf(String cpf);
    
    List<Usuario> findByNomeContaining(String nome);
    
    List<Usuario> findByRolesIn(List<String> roles);
    
    Optional<Usuario> findByUsername(String username);
    
    
}
