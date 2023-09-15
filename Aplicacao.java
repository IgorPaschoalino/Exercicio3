package app;

import static spark.Spark.*;
import service.ClientesService;


public class Aplicacao {
	
	private static ClientesService ClientesService = new ClientesService();
	
    public static void main(String[] args) {
        port(4567);
        
        staticFiles.location("/public");
        
        post("/Clientes/insert", (request, response) -> ClientesService.insert(request, response));

        get("/Clientes/:id", (request, response) -> ClientesService.get(request, response));
        
        get("/Clientes/list/:orderby", (request, response) -> ClientesService.getAll(request, response));

        get("/Clientes/update/:id", (request, response) -> ClientesService.getToUpdate(request, response));
        
        post("/Clientes/update/:id", (request, response) -> ClientesService.update(request, response));
           
        get("/Clientes/delete/:id", (request, response) -> ClientesService.delete(request, response));

             
    }
}