package br.com.desafiocasasbahia.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.desafiocasasbahia.domain.dtos.VendedorDTO;
import br.com.desafiocasasbahia.domain.enums.TipoContratacao;
import br.com.desafiocasasbahia.domain.vendedor.Filial;
import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import br.com.desafiocasasbahia.exceptions.UserAlreadyExistsException;
import br.com.desafiocasasbahia.exceptions.UserNotFoundException;
import br.com.desafiocasasbahia.proxy.FilialProxy;
import br.com.desafiocasasbahia.repositories.FilialRepository;
import br.com.desafiocasasbahia.repositories.VendedorRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VendedorService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VendedorServiceTest {
    @MockBean
    private FilialProxy filialProxy;

    @MockBean
    private FilialRepository filialRepository;

    @MockBean
    private VendedorRepository vendedorRepository;

    @Autowired
    private VendedorService vendedorService;

    /**
     * Method under test: {@link VendedorService#getVendedores(Pageable)}
     */
    @Test
    void testGetVendedores() {
        // Arrange
        PageImpl<Vendedor> pageImpl = new PageImpl<>(new ArrayList<>());
        when(vendedorRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<Vendedor> actualVendedores = vendedorService.getVendedores(null);

        // Assert
        verify(vendedorRepository).findAll((Pageable) isNull());
        assertTrue(actualVendedores.toList().isEmpty());
        assertSame(pageImpl, actualVendedores);
    }

    /**
     * Method under test: {@link VendedorService#getVendedores(Pageable)}
     */
    @Test
    void testGetVendedores2() {
        // Arrange
        when(vendedorRepository.findAll(Mockito.<Pageable>any()))
                .thenThrow(new UserAlreadyExistsException("An error occurred"));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> vendedorService.getVendedores(null));
        verify(vendedorRepository).findAll((Pageable) isNull());
    }

    /**
     * Method under test: {@link VendedorService#cadastraVendedor(VendedorDTO)}
     */
    @Test
    void testCadastraVendedor() {
        // Arrange, Act and Assert
        assertThrows(DateTimeException.class,
                () -> vendedorService.cadastraVendedor(new VendedorDTO("Nome", "Sobrenome", "alice.liddell@example.org",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));
    }

    /**
     * Method under test: {@link VendedorService#cadastraVendedor(VendedorDTO)}
     */
    @Test
    void testCadastraVendedor2() {
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
        when(vendedorRepository.save(Mockito.<Vendedor>any())).thenReturn(vendedor);

        Filial filial2 = new Filial();
        filial2.setCidade("Cidade");
        filial2.setDataCadastro("Data Cadastro");
        filial2.setDocumento("alice.liddell@example.org");
        filial2.setId(1L);
        filial2.setNome("Nome");
        filial2.setTipo("Tipo");
        filial2.setUF("UF");
        filial2.setUltimaAtualizacao("Ultima Atualizacao");
        Optional<Filial> ofResult = Optional.of(filial2);

        Filial filial3 = new Filial();
        filial3.setCidade("Cidade");
        filial3.setDataCadastro("Data Cadastro");
        filial3.setDocumento("alice.liddell@example.org");
        filial3.setId(1L);
        filial3.setNome("Nome");
        filial3.setTipo("Tipo");
        filial3.setUF("UF");
        filial3.setUltimaAtualizacao("Ultima Atualizacao");
        when(filialRepository.save(Mockito.<Filial>any())).thenReturn(filial3);
        when(filialRepository.getFilialByDocumento(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        vendedorService.cadastraVendedor(new VendedorDTO("Nome", "Sobrenome", "09/09/9999", "alice.liddell@example.org",
                "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial"));

        // Assert
        verify(filialRepository).getFilialByDocumento(eq("Documento Filial"));
        verify(filialRepository).save(isA(Filial.class));
        verify(vendedorRepository).save(isA(Vendedor.class));
    }

    /**
     * Method under test: {@link VendedorService#cadastraVendedor(VendedorDTO)}
     */
    @Test
    void testCadastraVendedor3() {
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
        when(vendedorRepository.save(Mockito.<Vendedor>any())).thenReturn(vendedor);

        Filial filial2 = new Filial();
        filial2.setCidade("Cidade");
        filial2.setDataCadastro("Data Cadastro");
        filial2.setDocumento("alice.liddell@example.org");
        filial2.setId(1L);
        filial2.setNome("Nome");
        filial2.setTipo("Tipo");
        filial2.setUF("UF");
        filial2.setUltimaAtualizacao("Ultima Atualizacao");
        Optional<Filial> ofResult = Optional.of(filial2);
        when(filialRepository.save(Mockito.<Filial>any())).thenThrow(new UserAlreadyExistsException("An error occurred"));
        when(filialRepository.getFilialByDocumento(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class,
                () -> vendedorService.cadastraVendedor(new VendedorDTO("Nome", "Sobrenome", "09/09/9999",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));
        verify(filialRepository).getFilialByDocumento(eq("Documento Filial"));
        verify(filialRepository).save(isA(Filial.class));
    }

    /**
     * Method under test:
     * {@link VendedorService#atualizaVendedor(Long, VendedorDTO)}
     */
    @Test
    void testAtualizaVendedor() {
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
        Optional<Vendedor> ofResult = Optional.of(vendedor);
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(DateTimeException.class,
                () -> vendedorService.atualizaVendedor(1L, new VendedorDTO("Nome", "Sobrenome", "alice.liddell@example.org",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));
        verify(vendedorRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link VendedorService#atualizaVendedor(Long, VendedorDTO)}
     */
    @Test
    void testAtualizaVendedor2() {
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
        Vendedor vendedor = mock(Vendedor.class);
        doNothing().when(vendedor).setDataNascimento(Mockito.<LocalDate>any());
        doNothing().when(vendedor).setDocumento(Mockito.<String>any());
        doNothing().when(vendedor).setEmail(Mockito.<String>any());
        doNothing().when(vendedor).setFilial(Mockito.<Filial>any());
        doNothing().when(vendedor).setId(Mockito.<Long>any());
        doNothing().when(vendedor).setMatricula(Mockito.<String>any());
        doNothing().when(vendedor).setNome(Mockito.<String>any());
        doNothing().when(vendedor).setSobrenome(Mockito.<String>any());
        doNothing().when(vendedor).setTipoContratacao(Mockito.<TipoContratacao>any());
        vendedor.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor.setDocumento("alice.liddell@example.org");
        vendedor.setEmail("jane.doe@example.org");
        vendedor.setFilial(filial);
        vendedor.setId(1L);
        vendedor.setMatricula("Matricula");
        vendedor.setNome("Nome");
        vendedor.setSobrenome("Sobrenome");
        vendedor.setTipoContratacao(TipoContratacao.OUTSOURCING);
        Optional<Vendedor> ofResult = Optional.of(vendedor);
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(DateTimeException.class,
                () -> vendedorService.atualizaVendedor(1L, new VendedorDTO("Nome", "Sobrenome", "alice.liddell@example.org",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));
        verify(vendedor).setDataNascimento(isA(LocalDate.class));
        verify(vendedor).setDocumento(eq("alice.liddell@example.org"));
        verify(vendedor).setEmail(eq("jane.doe@example.org"));
        verify(vendedor).setFilial(isA(Filial.class));
        verify(vendedor).setId(eq(1L));
        verify(vendedor).setMatricula(eq("Matricula"));
        verify(vendedor, atLeast(1)).setNome(eq("Nome"));
        verify(vendedor, atLeast(1)).setSobrenome(eq("Sobrenome"));
        verify(vendedor).setTipoContratacao(eq(TipoContratacao.OUTSOURCING));
        verify(vendedorRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link VendedorService#atualizaVendedor(Long, VendedorDTO)}
     */
    @Test
    void testAtualizaVendedor3() {
        // Arrange
        Optional<Vendedor> emptyResult = Optional.empty();
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UserNotFoundException.class,
                () -> vendedorService.atualizaVendedor(1L, new VendedorDTO("Nome", "Sobrenome", "alice.liddell@example.org",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));
        verify(vendedorRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link VendedorService#atualizaVendedor(Long, VendedorDTO)}
     */
    @Test
    void testAtualizaVendedor4() {
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
        Vendedor vendedor = mock(Vendedor.class);
        when(vendedor.getNome()).thenReturn("Nome");
        when(vendedor.getSobrenome()).thenReturn("Sobrenome");
        when(vendedor.getDataNascimento()).thenReturn(LocalDate.of(1970, 1, 1));
        doNothing().when(vendedor).setDataNascimento(Mockito.<LocalDate>any());
        doNothing().when(vendedor).setDocumento(Mockito.<String>any());
        doNothing().when(vendedor).setEmail(Mockito.<String>any());
        doNothing().when(vendedor).setFilial(Mockito.<Filial>any());
        doNothing().when(vendedor).setId(Mockito.<Long>any());
        doNothing().when(vendedor).setMatricula(Mockito.<String>any());
        doNothing().when(vendedor).setNome(Mockito.<String>any());
        doNothing().when(vendedor).setSobrenome(Mockito.<String>any());
        doNothing().when(vendedor).setTipoContratacao(Mockito.<TipoContratacao>any());
        vendedor.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor.setDocumento("alice.liddell@example.org");
        vendedor.setEmail("jane.doe@example.org");
        vendedor.setFilial(filial);
        vendedor.setId(1L);
        vendedor.setMatricula("Matricula");
        vendedor.setNome("Nome");
        vendedor.setSobrenome("Sobrenome");
        vendedor.setTipoContratacao(TipoContratacao.OUTSOURCING);
        Optional<Vendedor> ofResult = Optional.of(vendedor);

        Filial filial2 = new Filial();
        filial2.setCidade("Cidade");
        filial2.setDataCadastro("Data Cadastro");
        filial2.setDocumento("alice.liddell@example.org");
        filial2.setId(1L);
        filial2.setNome("Nome");
        filial2.setTipo("Tipo");
        filial2.setUF("UF");
        filial2.setUltimaAtualizacao("Ultima Atualizacao");

        Vendedor vendedor2 = new Vendedor();
        vendedor2.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor2.setDocumento("alice.liddell@example.org");
        vendedor2.setEmail("jane.doe@example.org");
        vendedor2.setFilial(filial2);
        vendedor2.setId(1L);
        vendedor2.setMatricula("Matricula");
        vendedor2.setNome("Nome");
        vendedor2.setSobrenome("Sobrenome");
        vendedor2.setTipoContratacao(TipoContratacao.OUTSOURCING);
        when(vendedorRepository.save(Mockito.<Vendedor>any())).thenReturn(vendedor2);
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        vendedorService.atualizaVendedor(1L, new VendedorDTO("Nome", "Sobrenome", "09/09/9999", "alice.liddell@example.org",
                "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial"));

        // Assert
        verify(vendedor).getDataNascimento();
        verify(vendedor).getNome();
        verify(vendedor).getSobrenome();
        verify(vendedor, atLeast(1)).setDataNascimento(Mockito.<LocalDate>any());
        verify(vendedor).setDocumento(eq("alice.liddell@example.org"));
        verify(vendedor).setEmail(eq("jane.doe@example.org"));
        verify(vendedor).setFilial(isA(Filial.class));
        verify(vendedor).setId(eq(1L));
        verify(vendedor).setMatricula(eq("Matricula"));
        verify(vendedor, atLeast(1)).setNome(eq("Nome"));
        verify(vendedor, atLeast(1)).setSobrenome(eq("Sobrenome"));
        verify(vendedor).setTipoContratacao(eq(TipoContratacao.OUTSOURCING));
        verify(vendedorRepository).findById(eq(1L));
        verify(vendedorRepository).save(isA(Vendedor.class));
    }

    /**
     * Method under test:
     * {@link VendedorService#atualizaVendedor(Long, VendedorDTO)}
     */
    @Test
    void testAtualizaVendedor5() {
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
        Vendedor vendedor = mock(Vendedor.class);
        when(vendedor.getNome()).thenThrow(new DateTimeException("09/09/9999"));
        doNothing().when(vendedor).setDataNascimento(Mockito.<LocalDate>any());
        doNothing().when(vendedor).setDocumento(Mockito.<String>any());
        doNothing().when(vendedor).setEmail(Mockito.<String>any());
        doNothing().when(vendedor).setFilial(Mockito.<Filial>any());
        doNothing().when(vendedor).setId(Mockito.<Long>any());
        doNothing().when(vendedor).setMatricula(Mockito.<String>any());
        doNothing().when(vendedor).setNome(Mockito.<String>any());
        doNothing().when(vendedor).setSobrenome(Mockito.<String>any());
        doNothing().when(vendedor).setTipoContratacao(Mockito.<TipoContratacao>any());
        vendedor.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor.setDocumento("alice.liddell@example.org");
        vendedor.setEmail("jane.doe@example.org");
        vendedor.setFilial(filial);
        vendedor.setId(1L);
        vendedor.setMatricula("Matricula");
        vendedor.setNome("Nome");
        vendedor.setSobrenome("Sobrenome");
        vendedor.setTipoContratacao(TipoContratacao.OUTSOURCING);
        Optional<Vendedor> ofResult = Optional.of(vendedor);

        Filial filial2 = new Filial();
        filial2.setCidade("Cidade");
        filial2.setDataCadastro("Data Cadastro");
        filial2.setDocumento("alice.liddell@example.org");
        filial2.setId(1L);
        filial2.setNome("Nome");
        filial2.setTipo("Tipo");
        filial2.setUF("UF");
        filial2.setUltimaAtualizacao("Ultima Atualizacao");

        Vendedor vendedor2 = new Vendedor();
        vendedor2.setDataNascimento(LocalDate.of(1970, 1, 1));
        vendedor2.setDocumento("alice.liddell@example.org");
        vendedor2.setEmail("jane.doe@example.org");
        vendedor2.setFilial(filial2);
        vendedor2.setId(1L);
        vendedor2.setMatricula("Matricula");
        vendedor2.setNome("Nome");
        vendedor2.setSobrenome("Sobrenome");
        vendedor2.setTipoContratacao(TipoContratacao.OUTSOURCING);
        when(vendedorRepository.save(Mockito.<Vendedor>any())).thenReturn(vendedor2);
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(DateTimeException.class,
                () -> vendedorService.atualizaVendedor(1L, new VendedorDTO("Nome", "Sobrenome", "09/09/9999",
                        "alice.liddell@example.org", "jane.doe@example.org", TipoContratacao.OUTSOURCING, "Documento Filial")));
        verify(vendedor).getNome();
        verify(vendedor, atLeast(1)).setDataNascimento(Mockito.<LocalDate>any());
        verify(vendedor).setDocumento(eq("alice.liddell@example.org"));
        verify(vendedor).setEmail(eq("jane.doe@example.org"));
        verify(vendedor).setFilial(isA(Filial.class));
        verify(vendedor).setId(eq(1L));
        verify(vendedor).setMatricula(eq("Matricula"));
        verify(vendedor, atLeast(1)).setNome(eq("Nome"));
        verify(vendedor, atLeast(1)).setSobrenome(eq("Sobrenome"));
        verify(vendedor).setTipoContratacao(eq(TipoContratacao.OUTSOURCING));
        verify(vendedorRepository).findById(eq(1L));
        verify(vendedorRepository).save(isA(Vendedor.class));
    }

    /**
     * Method under test: {@link VendedorService#removeVendedor(Long)}
     */
    @Test
    void testRemoveVendedor() {
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
        Optional<Vendedor> ofResult = Optional.of(vendedor);
        doNothing().when(vendedorRepository).delete(Mockito.<Vendedor>any());
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Vendedor actualRemoveVendedorResult = vendedorService.removeVendedor(1L);

        // Assert
        verify(vendedorRepository).delete(isA(Vendedor.class));
        verify(vendedorRepository).findById(eq(1L));
        assertSame(vendedor, actualRemoveVendedorResult);
    }

    /**
     * Method under test: {@link VendedorService#removeVendedor(Long)}
     */
    @Test
    void testRemoveVendedor2() {
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
        Optional<Vendedor> ofResult = Optional.of(vendedor);
        doThrow(new UserAlreadyExistsException("Usuário já existe")).when(vendedorRepository)
                .delete(Mockito.<Vendedor>any());
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> vendedorService.removeVendedor(1L));
        verify(vendedorRepository).delete(isA(Vendedor.class));
        verify(vendedorRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link VendedorService#removeVendedor(Long)}
     */
    @Test
    void testRemoveVendedor3() {
        // Arrange
        Optional<Vendedor> emptyResult = Optional.empty();
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> vendedorService.removeVendedor(1L));
        verify(vendedorRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link VendedorService#checaSeVendedorExiste(String, String)}
     */
    @Test
    void testChecaSeVendedorExiste() {
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
        Optional<Vendedor> ofResult = Optional.of(vendedor);
        when(vendedorRepository.findByDocumento(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class,
                () -> vendedorService.checaSeVendedorExiste("alice.liddell@example.org", "jane.doe@example.org"));
        verify(vendedorRepository).findByDocumento(eq("alice.liddell@example.org"));
    }

    /**
     * Method under test:
     * {@link VendedorService#checaSeVendedorExiste(String, String)}
     */
    @Test
    void testChecaSeVendedorExiste2() {
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
        Optional<Vendedor> ofResult = Optional.of(vendedor);
        Optional<Vendedor> emptyResult = Optional.empty();
        when(vendedorRepository.findByDocumento(Mockito.<String>any())).thenReturn(emptyResult);
        when(vendedorRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class,
                () -> vendedorService.checaSeVendedorExiste("alice.liddell@example.org", "jane.doe@example.org"));
        verify(vendedorRepository).findByDocumento(eq("alice.liddell@example.org"));
        verify(vendedorRepository).findByEmail(eq("jane.doe@example.org"));
    }

    /**
     * Method under test:
     * {@link VendedorService#checaSeVendedorExiste(String, String)}
     */
    @Test
    void testChecaSeVendedorExiste3() {
        // Arrange
        when(vendedorRepository.findByDocumento(Mockito.<String>any())).thenThrow(new DateTimeException("foo"));

        // Act and Assert
        assertThrows(DateTimeException.class,
                () -> vendedorService.checaSeVendedorExiste("alice.liddell@example.org", "jane.doe@example.org"));
        verify(vendedorRepository).findByDocumento(eq("alice.liddell@example.org"));
    }

    /**
     * Method under test: {@link VendedorService#findVendedorById(Long)}
     */
    @Test
    void testFindVendedorById() {
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
        Optional<Vendedor> ofResult = Optional.of(vendedor);
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Vendedor actualFindVendedorByIdResult = vendedorService.findVendedorById(1L);

        // Assert
        verify(vendedorRepository).findById(eq(1L));
        assertSame(vendedor, actualFindVendedorByIdResult);
    }

    /**
     * Method under test: {@link VendedorService#findVendedorById(Long)}
     */
    @Test
    void testFindVendedorById2() {
        // Arrange
        Optional<Vendedor> emptyResult = Optional.empty();
        when(vendedorRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> vendedorService.findVendedorById(1L));
        verify(vendedorRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link VendedorService#findVendedorById(Long)}
     */
    @Test
    void testFindVendedorById3() {
        // Arrange
        when(vendedorRepository.findById(Mockito.<Long>any()))
                .thenThrow(new UserAlreadyExistsException("Usuário já existe!"));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> vendedorService.findVendedorById(1L));
        verify(vendedorRepository).findById(eq(1L));
    }
}
