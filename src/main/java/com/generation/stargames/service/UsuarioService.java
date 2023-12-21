package com.generation.stargames.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.stargames.model.Usuario;
import com.generation.stargames.model.UsuarioLogin;
import com.generation.stargames.repository.UsuarioRepository;
import com.generation.stargames.security.JwtService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
			return Optional.empty();
		}

		LocalDate dataNascimento = usuario.getDataNascimento();
		LocalDate teto = LocalDate.now().minusYears(18);

		if (dataNascimento.isAfter(teto)) {
			return Optional.empty();
		}

		usuario.setSenha(criptografarSenhar(usuario.getSenha()));
		return Optional.ofNullable(usuarioRepository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {

			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
			
			LocalDate dataNascimento = usuario.getDataNascimento();
			LocalDate teto = LocalDate.now().minusYears(18);

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			if(buscaUsuario.isPresent() && dataNascimento.isAfter(teto))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de Nascimento Inválida!", null);
			
			usuario.setSenha(criptografarSenhar(usuario.getSenha()));
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}
		return Optional.empty();
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
				usuarioLogin.get().getSenha());

		Authentication authentication = authenticationManager.authenticate(credenciais);

		if (authentication.isAuthenticated()) {
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
			if (usuario.isPresent()) {
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setSenha("");
				usuarioLogin.get().setToken(gerarTokent(usuarioLogin.get().getUsuario()));

				return usuarioLogin;
			}
		}
		return Optional.empty();
	}

	private String gerarTokent(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

	private String criptografarSenhar(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

}
