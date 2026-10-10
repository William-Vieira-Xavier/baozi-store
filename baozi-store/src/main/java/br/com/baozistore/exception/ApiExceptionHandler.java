package br.com.baozistore.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroApi> naoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest req) {
        return resposta(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroApi> validacao(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return resposta(HttpStatus.BAD_REQUEST, mensagem, req.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroApi> argumento(IllegalArgumentException ex, HttpServletRequest req) {
        return resposta(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroApi> integridade(DataIntegrityViolationException ex, HttpServletRequest req) {
        return resposta(HttpStatus.CONFLICT, "Não foi possível excluir o recurso porque há registros relacionados.", req.getRequestURI());
    }

    private ResponseEntity<ErroApi> resposta(HttpStatus status, String mensagem, String caminho) {
        return ResponseEntity.status(status).body(new ErroApi(Instant.now(), status.value(),
            status.getReasonPhrase(), mensagem, caminho));
    }
}
