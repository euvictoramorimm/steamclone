package br.com.plataforma.steamclone.service;

import org.springframework.stereotype.Component; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ⭐️ Requisito 13: Padrão Factory (Criador de Notas Fiscais)
 * ⭐️ Requisito 12: Padrão Singleton (Garantido pela anotação @Component do Spring)
 */
@Component
public class NotaFiscalFactory {
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public String gerarNotaFiscal(String tipo) {
        String timestamp = LocalDateTime.now().format(formatter);
        
        if ("DIGITAL".equalsIgnoreCase(tipo)) {
            // Lógica para gerar Nota Fiscal Eletrônica
            return "NF-ELEC-" + timestamp;
        } else {
            // Lógica para Recibo Padrão
            return "RECIBO-PADRAO-" + timestamp;
        }
    }
}