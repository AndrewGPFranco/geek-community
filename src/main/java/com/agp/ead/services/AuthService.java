package com.agp.ead.services;

import com.agp.ead.dtos.auth.InputRegisterUser;
import com.agp.ead.entities.User;
import com.agp.ead.mappers.UserMapper;
import com.agp.ead.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private static final String MENSAGEM_ERRO_REGISTRO = "Ocorreu um erro ao registrar o usuário, verifique os dados e tente novamente!";

    public void registerUser(InputRegisterUser inputDTO) {
        try {
            User user = userMapper.dtoParaEntidade(inputDTO);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            String campoJaUtilizado = recuperaCampoJaUtilizado(e.getMessage());
            throw new DataIntegrityViolationException(campoJaUtilizado);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(MENSAGEM_ERRO_REGISTRO);
        }
    }

    /**
     * Responsável por verificar qual o campo que já foi utilizado, passando uma resposta certeira ao usuário
     *
     * @return mensagem formatada informando o campo que já foi utilizado!
     */
    protected String recuperaCampoJaUtilizado(String mensagemException) {
        if (mensagemException.contains("users_email_key"))
            return "O email informado já esta em uso!";
        else if (mensagemException.contains("users_apelido_key"))
            return "O apelido informado já esta em uso!";

        // Chegando nesse ponto é um erro desconhecido, tendo em vista que
        // os únicos campos atualmente que é unique é o email e o apelido.
        log.error(mensagemException);
        return MENSAGEM_ERRO_REGISTRO;
    }
}
