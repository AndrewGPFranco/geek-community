package com.agp.geek.services;

import com.agp.geek.components.CacheComponent;
import com.agp.geek.dtos.auth.ChangePasswordDTO;
import com.agp.geek.dtos.auth.InputRegisterUserDTO;
import com.agp.geek.dtos.auth.ValidateCodeDTO;
import com.agp.geek.entities.User;
import com.agp.geek.mappers.UserMapper;
import com.agp.geek.repositories.UserRepository;
import com.agp.geek.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final EmailService emailService;
    private final Random random = new Random();
    private final UserRepository userRepository;
    private final CacheComponent cacheComponent;
    private static final String MENSAGEM_ERRO_REGISTRO = "Ocorreu um erro ao registrar o usuário, verifique os dados e tente novamente!";

    public UUID registerUser(InputRegisterUserDTO inputDTO) {
        try {
            ValidationUtils.validEmailAndPasswordAndAge(inputDTO.email(), inputDTO.senha(), inputDTO.dataNascimento());
            checksUserAndEmailInUse(inputDTO);

            User user = userMapper.dtoParaEntidade(inputDTO);

            String uuid = UUID.randomUUID().toString();
            int code = 100000 + random.nextInt(900000);
            ValidateCodeDTO dto = ValidateCodeDTO.builder()
                    .uuid(uuid)
                    .user(user)
                    .code(code)
                    .build();
            cacheComponent.savedUser(dto, UUID.fromString(uuid));

            emailService.sendCodeForRegistration(code, inputDTO.email(), "Código para ativação da conta!", "Geek Community");
            return UUID.fromString(dto.uuid());
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Ocorreu um problema ao cadastrar o usuário no sistema!");
        }
    }

    private void checksUserAndEmailInUse(InputRegisterUserDTO inputDTO) {
        User nickameInUse = userRepository.findByIdentificador(inputDTO.identificador());
        User emailInUse = userRepository.findByEmail(inputDTO.email());

        if (nickameInUse != null)
            throw new DataIntegrityViolationException("Usuário já em uso!");

        if (emailInUse != null)
            throw new DataIntegrityViolationException("Email já em uso!");
    }

    /**
     * Responsável por verificar qual o campo que já foi utilizado, passando uma resposta certeira ao usuário
     *
     * @return mensagem formatada informando o campo que já foi utilizado!
     */
    protected String recoversFieldAlreadyUsed(String mensagemException) {
        if (mensagemException.contains("users_email_key"))
            return "O email informado já esta em uso!";
        else if (mensagemException.contains("users_apelido_key"))
            return "O apelido informado já esta em uso!";

        // Chegando nesse ponto é um erro desconhecido, tendo em vista que
        // os únicos campos atualmente que é unique é o email e o apelido.
        log.error(mensagemException);
        return MENSAGEM_ERRO_REGISTRO;
    }

    public void changePassword(User user, ChangePasswordDTO input) {
        user.setSenha(userMapper.encriptarSenha(input.newPassword()));
        userRepository.save(user);
    }

    public void inviteLinkForgotPassword(String email) {
        emailService.enviarEmailParaAlteracaoSenha(email, "Alteração de senha!", "Geek Community");
    }

    public void forgotPasswordChange(ChangePasswordDTO input) {
        String email = emailService.validaUUID(input.uuid());
        User usuario = userRepository.findByEmail(email);

        if (usuario != null) {
            changePassword(usuario, input);
            cacheComponent.removeCache(input.uuid());
        }
    }

    /**
     * Responsável em validar o código informado pelo usuário e salvar a conta.
     *
     * @param dto contendo as informações do usuário.
     * @return TRUE caso tudo tenha saido corretamente.
     */
    public Boolean validateCodeAndSaveUser(ValidateCodeDTO dto) {
        try {
            ValidateCodeDTO validateCodeDTO = cacheComponent.recoverUserCache(UUID.fromString(dto.uuid()));

            if (validateCodeDTO == null) return false;

            if (dto.code().equals(validateCodeDTO.code())) {
                userRepository.save(validateCodeDTO.user());
                cacheComponent.removeUserCache(UUID.fromString(dto.uuid()));
                return true;
            }

            return false;
        } catch (DataIntegrityViolationException e) {
            String campoJaUtilizado = recoversFieldAlreadyUsed(e.getMessage());
            throw new DataIntegrityViolationException(campoJaUtilizado);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(MENSAGEM_ERRO_REGISTRO);
        }
    }

    public void invalidaUserCache(String token) {
        cacheComponent.removeUserCache(UUID.fromString(token));
        log.info("Cache com o ID: {} foi removido!", token);
    }
}
