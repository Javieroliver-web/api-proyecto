package gateway.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;
import java.util.List;

/**
 * Gateway Controller - Reenvía todas las peticiones al backend
 * Actúa como proxy con validación de seguridad
 */
@Path("/")
public class GatewayController {

    // URL del backend interno (nombre del servicio en Docker)
    private static final String BACKEND_URL = "http://java-jpa-app:8080/api";
    private final Client client = ClientBuilder.newClient();

    /**
     * Reenvía todas las peticiones GET
     */
    @GET
    @Path("{path: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response proxyGet(@PathParam("path") String path,
                            @Context UriInfo uriInfo,
                            @Context HttpHeaders headers) {
        
        String fullPath = BACKEND_URL + "/" + path;
        if (uriInfo.getRequestUri().getQuery() != null) {
            fullPath += "?" + uriInfo.getRequestUri().getQuery();
        }
        
        System.out.println("→ GET: " + fullPath);
        
        try {
            Response response = client.target(fullPath)
                .request(MediaType.APPLICATION_JSON)
                .headers(copyHeaders(headers))
                .get();
            
            return buildResponse(response);
        } catch (Exception e) {
            System.err.println("❌ Error en GET: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{ \"error\": \"Error comunicándose con el backend\" }")
                .build();
        }
    }

    /**
     * Reenvía todas las peticiones POST
     */
    @POST
    @Path("{path: .*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response proxyPost(@PathParam("path") String path,
                             String body,
                             @Context HttpHeaders headers) {
        
        String fullPath = BACKEND_URL + "/" + path;
        System.out.println("→ POST: " + fullPath);
        
        try {
            Response response = client.target(fullPath)
                .request(MediaType.APPLICATION_JSON)
                .headers(copyHeaders(headers))
                .post(Entity.json(body));
            
            return buildResponse(response);
        } catch (Exception e) {
            System.err.println("❌ Error en POST: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{ \"error\": \"Error comunicándose con el backend\" }")
                .build();
        }
    }

    /**
     * Reenvía todas las peticiones PUT
     */
    @PUT
    @Path("{path: .*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response proxyPut(@PathParam("path") String path,
                            String body,
                            @Context HttpHeaders headers) {
        
        String fullPath = BACKEND_URL + "/" + path;
        System.out.println("→ PUT: " + fullPath);
        
        try {
            Response response = client.target(fullPath)
                .request(MediaType.APPLICATION_JSON)
                .headers(copyHeaders(headers))
                .put(Entity.json(body));
            
            return buildResponse(response);
        } catch (Exception e) {
            System.err.println("❌ Error en PUT: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{ \"error\": \"Error comunicándose con el backend\" }")
                .build();
        }
    }

    /**
     * Reenvía todas las peticiones DELETE
     */
    @DELETE
    @Path("{path: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response proxyDelete(@PathParam("path") String path,
                               @Context HttpHeaders headers) {
        
        String fullPath = BACKEND_URL + "/" + path;
        System.out.println("→ DELETE: " + fullPath);
        
        try {
            Response response = client.target(fullPath)
                .request(MediaType.APPLICATION_JSON)
                .headers(copyHeaders(headers))
                .delete();
            
            return buildResponse(response);
        } catch (Exception e) {
            System.err.println("❌ Error en DELETE: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{ \"error\": \"Error comunicándose con el backend\" }")
                .build();
        }
    }

    /**
     * Copia los headers importantes de la petición original
     */
    private MultivaluedMap<String, Object> copyHeaders(HttpHeaders headers) {
        MultivaluedMap<String, Object> newHeaders = new MultivaluedHashMap<>();
        
        // Headers que queremos reenviar
        List<String> headersToForward = List.of("Authorization", "Content-Type", "Accept");
        
        for (String headerName : headersToForward) {
            List<String> headerValues = headers.getRequestHeader(headerName);
            if (headerValues != null) {
                for (String value : headerValues) {
                    newHeaders.add(headerName, value);
                }
            }
        }
        
        return newHeaders;
    }

    /**
     * Construye la respuesta copiando el cuerpo y el status code
     */
    private Response buildResponse(Response backendResponse) {
        String body = backendResponse.readEntity(String.class);
        int status = backendResponse.getStatus();
        
        return Response.status(status)
            .entity(body)
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}