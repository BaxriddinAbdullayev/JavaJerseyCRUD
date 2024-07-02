package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.entity.UnicornEntity;
import com.crudcrud.jersey.crud.service.UnicornService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/unicorns")
public class UnicornController {

    @Inject
    private UnicornService unicornService;

    @GET
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<UnicornEntity> allUnicorn = unicornService.getAllUnicorn();
        if (allUnicorn.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorns found.")
                    .build();
        } else {
            return Response.ok(allUnicorn).build();
        }
    }

    @GET
    @Path("/resource/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        UnicornEntity unicornById = unicornService.getUnicornById(id);
        if (unicornById == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorn found.")
                    .build();
        } else {
            return Response.ok(unicornById).build();
        }
    }

    @POST
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UnicornEntity unicornEntity) {
        int i = unicornService.createUnicorn(unicornEntity);

        if(i==0){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Unicorn was not added.")
                    .build();
        }else {
            return Response.ok("unicorn successfully added!").build();
        }
    }

    @PUT
    @Path("/resource/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Integer id, UnicornEntity unicornEntity) {
        int i = unicornService.updateUnicorn(unicornEntity, id);
        if (i == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorn with such id found.")
                    .build();
        } else {
            return Response.ok("Unicorn successfully updated!").build();
        }
    }

    @DELETE
    @Path("/resource/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) {
        int i = unicornService.deleteUnicorn(id);
        if (i == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorn with such id found.")
                    .build();
        } else {
            return Response.ok("Unicorn successfully deleted!").build();
        }
    }

}
