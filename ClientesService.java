package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.ClientesDAO;
import model.Clientes;
import spark.Request;
import spark.Response;


public class ClientesService {

	private ClientesDAO ClientesDAO = new ClientesDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_nome = 2;
	private final int FORM_ORDERBY_sexo = 3;
	
	
	public ClientesService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Clientes(), FORM_ORDERBY_nome);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Clientes(), orderBy);
	}

	
	public void makeForm(int tipo, Clientes Clientes, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umClientes = "";
		if(tipo != FORM_INSERT) {
			umClientes += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/Clientes/list/1\">Novo Clientes</a></b></font></td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t</table>";
			umClientes += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/Clientes/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Clientes";
				nome = "leite, pão, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + Clientes.getId();
				name = "Atualizar Clientes (ID " + Clientes.getId() + ")";
				nome = Clientes.getNome();
				buttonLabel = "Atualizar";
			}
			umClientes += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umClientes += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umClientes += "\t\t\t<td>sexo: <input class=\"input--register\" type=\"text\" name=\"sexo\" value=\""+ Clientes.getSexo() +"\"></td>";
			umClientes += "\t\t\t<td>idade: <input class=\"input--register\" type=\"text\" name=\"idade\" value=\""+ Clientes.getIdade() +"\"></td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" name=\"cidade\" value=\""+ Clientes.getCidade() + "\"></td>";
			umClientes += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t</table>";
			umClientes += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umClientes += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Clientes (ID " + Clientes.getId() + ")</b></font></td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td>&nbsp;Descrição: "+ Clientes.getNome() +"</td>";
			umClientes += "\t\t\t<td>sexo: "+ Clientes.getSexo() +"</td>";
			umClientes += "\t\t\t<td>idade: "+ Clientes.getIdade() +"</td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t\t<tr>";
			umClientes += "\t\t\t<td>&nbsp;Data de fabricação: "+ Clientes.getCidade() + "</td>";
			umClientes += "\t\t\t<td>&nbsp;</td>";
			umClientes += "\t\t</tr>";
			umClientes += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-Clientes>", umClientes);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Clientess</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/Clientes/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/Clientes/list/" + FORM_ORDERBY_nome + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/Clientes/list/" + FORM_ORDERBY_sexo + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Clientes> Clientess;
		if (orderBy == FORM_ORDERBY_ID) {                 	Clientess = ClientesDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_nome) {		Clientess = ClientesDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_sexo) {			Clientess = ClientesDAO.getOrderBySexo();
		} else {											Clientess = ClientesDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Clientes p : Clientess) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getSexo() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/Clientes/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/Clientes/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteClientes('" + p.getId() + "', '" + p.getNome() + "', '" + p.getSexo() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-Clientes>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String sexo = (request.queryParams("sexo"));
		int idade = Integer.parseInt(request.queryParams("idade"));
		String cidade = request.queryParams("cidade");
		
		String resp = "";
		
		Clientes Clientes = new Clientes(-1, nome, sexo, idade, cidade);
		
		if(ClientesDAO.insert(Clientes) == true) {
            resp = "Clientes (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Clientes (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Clientes Clientes = (Clientes) ClientesDAO.get(id);
		
		if (Clientes != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, Clientes, FORM_ORDERBY_nome);
        } else {
            response.status(404); // 404 Not found
            String resp = "Clientes " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Clientes Clientes = (Clientes) ClientesDAO.get(id);
		
		if (Clientes != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, Clientes, FORM_ORDERBY_nome);
        } else {
            response.status(404); // 404 Not found
            String resp = "Clientes " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Clientes Clientes = ClientesDAO.get(id);
        String resp = "";       

        if (Clientes != null) {
        	Clientes.setNome(request.queryParams("nome"));
        	Clientes.setSexo(request.queryParams("sexo"));
        	Clientes.setIdade(Integer.parseInt(request.queryParams("idade")));
        	Clientes.setCidade(request.queryParams("cidade"));
        	ClientesDAO.update(Clientes);
        	response.status(200); // success
            resp = "Clientes (ID " + Clientes.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Clientes (ID \" + Clientes.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Clientes Clientes = ClientesDAO.get(id);
        String resp = "";       

        if (Clientes != null) {
            ClientesDAO.delete(id);
            response.status(200); // success
            resp = "Clientes (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Clientes (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}