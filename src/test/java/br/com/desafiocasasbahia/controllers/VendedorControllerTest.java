package br.com.desafiocasasbahia.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.desafiocasasbahia.domain.dtos.VendedorDTO;
import br.com.desafiocasasbahia.domain.enums.TipoContratacao;
import br.com.desafiocasasbahia.domain.vendedor.Filial;
import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import br.com.desafiocasasbahia.proxy.FilialProxy;
import br.com.desafiocasasbahia.repositories.FilialRepository;
import br.com.desafiocasasbahia.repositories.VendedorRepository;
import br.com.desafiocasasbahia.response.VendedorAtualizadoResponse;
import br.com.desafiocasasbahia.services.VendedorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {VendedorController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VendedorControllerTest {
    @Autowired
    private VendedorController vendedorController;

    @MockBean
    private VendedorService vendedorService;

    /**
     * Method under test: {@link VendedorController#getVendedores(Integer, Integer)}
     */
    @Test
    void testGetVendedores() {

        // Arrange
        VendedorRepository repository = mock(VendedorRepository.class);
        when(repository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        ResponseEntity<Page<Vendedor>> actualVendedores = (new VendedorController(
                new VendedorService(repository, mock(FilialProxy.class), mock(FilialRepository.class)))).getVendedores(1, 1);

        // Assert
        verify(repository).findAll(isA(Pageable.class));
        assertEquals(200, actualVendedores.getStatusCodeValue());
        assertTrue(actualVendedores.getBody().toList().isEmpty());
        assertTrue(actualVendedores.hasBody());
        assertTrue(actualVendedores.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link VendedorController#getVendedores(Integer, Integer)}
     */
    @Test
    void testGetVendedores2() {

        // Arrange
        VendedorService service = mock(VendedorService.class);
        when(service.getVendedores(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        ResponseEntity<Page<Vendedor>> actualVendedores = (new VendedorController(service)).getVendedores(1, 1);

        // Assert
        verify(service).getVendedores(isA(Pageable.class));
        assertEquals(200, actualVendedores.getStatusCodeValue());
        assertTrue(actualVendedores.getBody().toList().isEmpty());
        assertTrue(actualVendedores.hasBody());
        assertTrue(actualVendedores.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link VendedorController#cadastraVendedor(VendedorDTO)}
     */
    @Test
    void testCadastraVendedor() throws Exception {
        // Arrange
        doNothing().when(vendedorService).cadastraVendedor(Mockito.<VendedorDTO>any());
        doNothing().when(vendedorService).checaSeVendedorExiste(Mockito.<String>any(), Mockito.<String>any());
        doNothing().when(vendedorService).validaDocumentoVendedor(Mockito.<String>any(), Mockito.<TipoContratacao>any());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new VendedorDTO("Nome", "Sobrenome", "alice.liddell@example.org",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(vendedorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test:
     * {@link VendedorController#atualizaVendedor(Long, VendedorDTO)}
     */
    @Test
    void testAtualizaVendedor() throws Exception {
        // Arrange
        when(vendedorService.atualizaVendedor(Mockito.<Long>any(), Mockito.<VendedorDTO>any()))
                .thenReturn(new VendedorAtualizadoResponse("Nome", "Sobrenome", LocalDate.of(1970, 1, 1)));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/vendedores/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new VendedorDTO("Nome", "Sobrenome", "alice.liddell@example.org",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(vendedorController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    /**
     * Method under test: {@link VendedorController#removeVendedor(Long)}
     */
    @Test
    void testRemoveVendedor() throws Exception {
        // Arrange
        Filial filial = new Filial();
        filial.setCidade("Cidade");
        filial.setDataCadastro("Data Cadastro");
        filial.setDocumento("alice.liddell@example.org");
        filial.setId(1L);
        filial.setNome("Nome");
        filial.setTipo("Tipo");
        filial.setUF("UF");
        filial.setUltimaAtualizacao("Ultima Atualizacao");

        Vendedor vendedor = new Vendedor();
        vendedor.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor.setDocumento("alice.liddell@example.org");
        vendedor.setEmail("jane.doe@example.org");
        vendedor.setFilial(filial);
        vendedor.setId(1L);
        vendedor.setMatricula("Matricula");
        vendedor.setNome("Nome");
        vendedor.setSobrenome("Sobrenome");
        vendedor.setTipoContratacao(TipoContratacao.OUTSOURCING);
        when(vendedorService.removeVendedor(Mockito.<Long>any())).thenReturn(vendedor);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/vendedores/delete/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(vendedorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"matricula\":\"Matricula\",\"nome\":\"Nome\",\"sobrenome\":\"Sobrenome\",\"dataNascimento\":[1970,1,1],"
                                        + "\"documento\":\"alice.liddell@example.org\",\"email\":\"jane.doe@example.org\",\"tipoContratacao\":\"OUTSOURCING"
                                        + "\",\"filial\":{\"nome\":\"Nome\",\"documento\":\"alice.liddell@example.org\",\"cidade\":\"Cidade\",\"tipo\":\"Tipo\","
                                        + "\"dataCadastro\":\"Data Cadastro\",\"ultimaAtualizacao\":\"Ultima Atualizacao\",\"id\":1,\"uf\":\"UF\"}}"));
    }

    /**
     * Method under test: {@link VendedorController#getVendedor(Long)}
     */
    @Test
    void testGetVendedor() throws Exception {
        // Arrange
        Filial filial = new Filial();
        filial.setCidade("Cidade");
        filial.setDataCadastro("Data Cadastro");
        filial.setDocumento("alice.liddell@example.org");
        filial.setId(1L);
        filial.setNome("Nome");
        filial.setTipo("Tipo");
        filial.setUF("UF");
        filial.setUltimaAtualizacao("Ultima Atualizacao");

        Vendedor vendedor = new Vendedor();
        vendedor.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor.setDocumento("alice.liddell@example.org");
        vendedor.setEmail("jane.doe@example.org");
        vendedor.setFilial(filial);
        vendedor.setId(1L);
        vendedor.setMatricula("Matricula");
        vendedor.setNome("Nome");
        vendedor.setSobrenome("Sobrenome");
        vendedor.setTipoContratacao(TipoContratacao.OUTSOURCING);
        when(vendedorService.findVendedorById(Mockito.<Long>any())).thenReturn(vendedor);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/vendedores/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(vendedorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"matricula\":\"Matricula\",\"nome\":\"Nome\",\"sobrenome\":\"Sobrenome\",\"dataNascimento\":[1970,1,1],"
                                        + "\"documento\":\"alice.liddell@example.org\",\"email\":\"jane.doe@example.org\",\"tipoContratacao\":\"OUTSOURCING"
                                        + "\",\"filial\":{\"nome\":\"Nome\",\"documento\":\"alice.liddell@example.org\",\"cidade\":\"Cidade\",\"tipo\":\"Tipo\","
                                        + "\"dataCadastro\":\"Data Cadastro\",\"ultimaAtualizacao\":\"Ultima Atualizacao\",\"id\":1,\"uf\":\"UF\"}}"));
    }
}
