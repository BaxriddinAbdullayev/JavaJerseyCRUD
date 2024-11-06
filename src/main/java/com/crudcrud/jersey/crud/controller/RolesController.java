package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.annotations.PublicAll;
import com.crudcrud.jersey.crud.entity.RolesEntity;
import com.crudcrud.jersey.crud.service.RolesService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/roles")
@PublicAll
public class RolesController {

    @Inject
    private RolesService rolesService;

    @GET
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<RolesEntity> allRoles = rolesService.getAllRoles();
        if (allRoles.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorns found.")
                    .build();
        } else {
            return Response.ok(allRoles).build();
        }
    }

    @GET
    @Path("/resource/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        RolesEntity rolesById = rolesService.getRolesById(id);
        if (rolesById == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorn found.")
                    .build();
        } else {
            return Response.ok(rolesById).build();
        }
    }

    @POST
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(RolesEntity rolesEntity) {
        int i = rolesService.createRoles(rolesEntity);
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
    public Response update(@PathParam("id") Integer id, RolesEntity rolesEntity) {
        int i = rolesService.updateRoles(rolesEntity, id);
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
        int i = rolesService.deleteRoles(id);
        if (i == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorn with such id found.")
                    .build();
        } else {
            return Response.ok("Unicorn successfully deleted!").build();
        }
    }

}
