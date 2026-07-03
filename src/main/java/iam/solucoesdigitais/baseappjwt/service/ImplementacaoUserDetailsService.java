package iam.solucoesdigitais.baseappjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;


@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findUserByUsername(username);/* Recebe o login pra consulta */

		if (usuario == null) {
			System.out.println("❌ Usuário não encontrado: " + username);
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}
		

		System.out.println("✅ Usuário autenticado com sucesso: " + username);

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());
	}
}
