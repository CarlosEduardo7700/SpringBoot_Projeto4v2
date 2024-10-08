package br.com.fiap.aula04.exercicio.controller;

import br.com.fiap.aula04.exercicio.dto.autenticacao.AutenticacaoDto;
import br.com.fiap.aula04.exercicio.dto.autenticacao.DadosTokenJwtDto;
import br.com.fiap.aula04.exercicio.model.Usuario;
import br.com.fiap.aula04.exercicio.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autenticacao")
@Tag(name = "Autenticação", description = "Operações relacionadas a Autenticação do usuário")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJwtDto> login(@RequestBody @Valid AutenticacaoDto dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var authentication = manager.authenticate(token);
        var tokenJwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJwtDto(tokenJwt));
    }
}
