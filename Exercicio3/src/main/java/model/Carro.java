package model;

public class Carro {
	private int codigo;
	private String marca;
	private String modelo;
	private int ano;
	private String placa;
	private float preco;
	
	public Carro() {
		this.codigo = -1;
		this.marca = "";
		this.modelo = "";
		this.ano = -1;
		this.placa = "";
		this.preco = -1;
	}
	
	public Carro(int codigo, String marca, String modelo, int ano, String placa, float preco) {
		this.codigo = codigo;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.placa = placa;
		this.preco = preco;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		if(placa.length() == 7) {
		this.placa = placa;
		}
	}
	
	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		if(preco > 0) {
		this.preco = preco;
		}
	}

	@Override
	public String toString() {
		return "Carro [codigo=" + codigo + ", marca=" + marca + ", modelo=" + modelo + ", ano=" + ano + ", placa=" + placa + ", preco=" + preco + "]";
	}	
}
