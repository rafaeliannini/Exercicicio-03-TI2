package service;

import dao.CarroDAO;
import model.Carro;
import spark.Request;
import spark.Response;

public class CarroService {

	private CarroDAO carroDAO;

	public CarroService() {
		carroDAO = new CarroDAO();
		carroDAO.conectar();
	}

	public Object add(Request request, Response response) {
		int codigo = Integer.parseInt(request.queryParams("codigo"));
		String marca = request.queryParams("marca");
		String modelo = request.queryParams("modelo");
		int ano = Integer.parseInt(request.queryParams("ano"));
		String placa = request.queryParams("placa");
		float preco = Float.parseFloat(request.queryParams("preco"));

		Carro carro = new Carro(codigo, marca, modelo, ano, placa, preco);
		carroDAO.inserirCarro(carro);

		response.status(201);
		return "Carro " + codigo + " inserido com sucesso!";
	}

	public Object get(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":id"));

		Carro carro = carroDAO.getCarro(codigo);

		if (carro != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<carro>\n" + 
					"\t<codigo>" + carro.getCodigo() + "</codigo>\n" +
					"\t<marca>" + carro.getMarca() + "</marca>\n" +
					"\t<modelo>" + carro.getModelo() + "</modelo>\n" +
					"\t<ano>" + carro.getAno() + "</ano>\n" +
					"\t<placa>" + carro.getPlaca() + "</placa>\n" +
					"\t<preco>" + carro.getPreco() + "</preco>\n" +
					"</carro>\n";
		} else {
			response.status(404);
			return "Carro " + codigo + " não encontrado.";
		}
	}

	public Object update(Request request, Response response) {
	    int codigo = Integer.parseInt(request.params(":id"));

	    Carro carro = carroDAO.getCarro(codigo);

	    if (carro != null) {
	        carro.setMarca(request.queryParams("marca"));
	        carro.setModelo(request.queryParams("modelo"));
	        carro.setAno(Integer.parseInt(request.queryParams("ano")));
	        carro.setPlaca(request.queryParams("placa"));
	        carro.setPreco(Float.parseFloat(request.queryParams("preco")));

	        carroDAO.atualizarCarro(carro);

	        response.status(200);
	        return "Carro " + codigo + " atualizado com sucesso!";
	    } else {
	        response.status(404);
	        return "Carro não encontrado.";
	    }
	}



	public Object remove(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":id"));

		boolean status = carroDAO.excluirCarro(codigo);

		if (status) {
			response.status(200);
			return "carro" + codigo + "removido com sucesso!";
		} else {
			response.status(404);
			return "Carro não encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<carros type=\"array\">");
		for (Carro carro : carroDAO.getCarros()) {
			returnValue.append("\n<carro>\n" + 
					"\t<codigo>" + carro.getCodigo() + "</codigo>\n" +
					"\t<marca>" + carro.getMarca() + "</marca>\n" +
					"\t<modelo>" + carro.getModelo() + "</modelo>\n" +
					"\t<ano>" + carro.getAno() + "</ano>\n" +
					"\t<placa>" + carro.getPlaca() + "</placa>\n" +
					"\t<preco>" + carro.getPreco() + "</preco>\n" +
					"</carro>\n");
		}
		returnValue.append("</carros>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
