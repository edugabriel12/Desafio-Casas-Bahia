package br.com.desafiocasasbahia.services;

import br.com.desafiocasasbahia.domain.dtos.VendedorDTO;
import br.com.desafiocasasbahia.domain.enums.TipoContratacao;
import br.com.desafiocasasbahia.domain.vendedor.Filial;
import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import br.com.desafiocasasbahia.exceptions.UserAlreadyExistsException;
import br.com.desafiocasasbahia.exceptions.UserNotFoundException;
import br.com.desafiocasasbahia.helpers.VendedorValidator;
import br.com.desafiocasasbahia.proxy.FilialProxy;
import br.com.desafiocasasbahia.repositories.FilialRepository;
import br.com.desafiocasasbahia.repositories.VendedorRepository;
import br.com.desafiocasasbahia.response.VendedorAtualizadoResponse;
import br.com.desafiocasasbahia.response.feign.FilialResponse;
import br.com.desafiocasasbahia.strategy.TipoVendedorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
public class VendedorService {

    private final VendedorRepository repository;

    private final FilialRepository filialRepository;

    private final FilialProxy proxy;


    @Autowired
    public VendedorService(VendedorRepository repository, FilialProxy proxy, FilialRepository filialRepository) {
        this.repository = repository;
        this.proxy = proxy;
        this.filialRepository = filialRepository;
    }

    public Page<Vendedor> getVendedores(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void validaDocumentoVendedor(String documento, TipoContratacao tipoContratacao)  {
        TipoVendedorInterface tipoVendedorInterface = tipoContratacao.getVendedorStrategy();
        tipoVendedorInterface.validaDocumento(documento);
    }

    public void cadastraVendedor(VendedorDTO vendedorRequest)  {
        LocalDate dataNascimento = formataData(vendedorRequest.dataNascimento());
        String matricula = geraMatriculaSequencial(vendedorRequest.tipoContratacao());
        Filial filial = getFilial(vendedorRequest.documentoFilial());

        Vendedor vendedor = Vendedor.builder()
                .matricula(matricula)
                .nome(vendedorRequest.nome())
                .sobrenome(vendedorRequest.sobrenome())
                .dataNascimento(dataNascimento)
                .documento(vendedorRequest.documento())
                .email(vendedorRequest.email())
                .tipoContratacao(vendedorRequest.tipoContratacao())
                .filial(filial)
                .build();

        filialRepository.save(filial);
        repository.save(vendedor);
    }

    public VendedorAtualizadoResponse atualizaVendedor(Long id, VendedorDTO vendedorRequest)  {

        Vendedor vendedor = findVendedorById(id);
        vendedor.setNome(vendedorRequest.nome());
        vendedor.setSobrenome(vendedorRequest.sobrenome());
        vendedor.setDataNascimento(formataData(vendedorRequest.dataNascimento()));

        repository.save(vendedor);

        return new VendedorAtualizadoResponse(
                vendedor.getNome(),
                vendedor.getSobrenome(),
                vendedor.getDataNascimento());
    }

    public Vendedor removeVendedor(Long id)  {
        Vendedor vendedor = findVendedorById(id);

        repository.delete(vendedor);

        return vendedor;
    }

    public void checaSeVendedorExiste(String documento, String email)  {
        boolean vendedorExiste = repository.findByDocumento(documento).isPresent();

        if (vendedorExiste) throw new UserAlreadyExistsException("Vendedor com documento: "
                + documento + " já existe");

        vendedorExiste = repository.findByEmail(email).isPresent();

        if (vendedorExiste) throw new UserAlreadyExistsException("Vendedor com email: "
                + email + " já existe");
    }

    private Filial getFilial(String cnpj)  {
        Optional<Filial> filialOptional = filialRepository.getFilialByDocumento(cnpj);

        if (filialOptional.isPresent()) {
            return filialOptional.get();
        }

        FilialResponse response = getFilialProxy(cnpj);

        return Filial.builder()
                .nome(response.nome())
                .documento(response.documento())
                .cidade(response.cidade())
                .UF(response.UF())
                .tipo(response.tipo())
                .dataCadastro(response.dataCadastro())
                .ultimaAtualizacao(response.ultimaAtualizacao()).build();
    }


    private FilialResponse getFilialProxy(String cnpj)  {
        ResponseEntity<FilialResponse> response = proxy.getFilial(cnpj);

        if (response.getBody() == null)
            throw new UserNotFoundException("Filial não encontrada com CNPJ: " + cnpj);

        return response.getBody();
    }

    private LocalDate formataData(String data)  {
        if (!VendedorValidator.validaData(data)) throw new 
                DateTimeException("Data de nascimento inválida!");

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(data, formato);
    }

    private String geraMatriculaSequencial(TipoContratacao tipoContratacao) {
        TipoVendedorInterface tipoVendedorInterface = tipoContratacao.getVendedorStrategy();
        String tipoVendedor = tipoVendedorInterface.getTipoVendedor();

        Random random = new Random();
        StringBuilder sequencial = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digito = random.nextInt(10);
            sequencial.append(digito);
        }

        return sequencial + "-" + tipoVendedor;
    }

    public Vendedor findVendedorById(Long id)  {
        Optional<Vendedor> vendedorOptional = repository.findById(id);
        if (vendedorOptional.isEmpty())
            throw new UserNotFoundException("Vendedor não encontrado com id: " + id);

        return vendedorOptional.get();
    }
}
