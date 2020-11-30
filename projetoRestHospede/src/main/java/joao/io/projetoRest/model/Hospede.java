package joao.io.projetoRest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hospedes")
public class Hospede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "documento", nullable = false)
	private String documento;
	
	@Column(name = "telefone", nullable = false, precision=13, scale=2)
	private String telefone;

	public Hospede (){}

	public Hospede(Long id) {
		this.id = id;
	}
	
	public Hospede(Hospede hospede) {
		setId(hospede.getId());
		setNome(hospede.getNome());
		setDocumento(hospede.getDocumento());
		setTelefone(hospede.getTelefone());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
}
