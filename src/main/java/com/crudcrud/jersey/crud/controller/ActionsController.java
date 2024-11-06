package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.annotations.PublicAll;
import com.crudcrud.jersey.crud.entity.ActionsEntity;
import com.crudcrud.jersey.crud.entity.RolesEntity;
import com.crudcrud.jersey.crud.service.ActionsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/actions")
@PublicAll
public class ActionsController {

    @Inject
    private ActionsService actionsService;

    @GET
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<ActionsEntity> allActions = actionsService.getAllActions();
        if (allActions.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorns found.")
                    .build();
        } else {
            return Response.ok(allActions).build();
        }
    }

    @GET
    @Path("/resource/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        ActionsEntity actions = actionsService.getActionsById(id);
        if (actions == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorn found.")
                    .build();
        } else {
            return Response.ok(actions).build();
        }
    }

    @POST
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ActionsEntity actionsEntity) {
        int i = actionsService.createActions(actionsEntity);
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
    public Response update(@PathParam("id") Integer id, ActionsEntity actionsEntity) {
        int i = actionsService.updateActions(actionsEntity, id);
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
        int i = actionsService.deleteActions(id);
        if (i == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No unicorn with such id found.")
                    .build();
        } else {
            return Response.ok("Unicorn successfully deleted!").build();
        }
    }
}
