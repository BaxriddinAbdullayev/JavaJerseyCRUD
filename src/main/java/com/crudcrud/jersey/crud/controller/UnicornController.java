package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.annotations.Public;
import com.crudcrud.jersey.crud.annotations.SystemAction;
import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.entity.UnicornEntity;
import com.crudcrud.jersey.crud.exceptions.NotFoundException;
import com.crudcrud.jersey.crud.exceptions.ParamNotFoundException;
import com.crudcrud.jersey.crud.service.UnicornService;
import com.crudcrud.jersey.crud.utils.Localization;
import org.apache.ibatis.session.RowBounds;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/unicorns")
public class UnicornController {

    @Inject
    private UnicornService unicornService;

    @Inject
    private Localization localization;

    @GET
    @Path("/resource")
    @SystemAction(value = "get_all_unicorns")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<UnicornEntity>> getAll() {
        List<UnicornEntity> allUnicorn = unicornService.getAllUnicorn();
        if (allUnicorn.isEmpty()) {
            throw new NotFoundException(localization.getMessage("exception.unicorns-not-found"));
        }
        return new Result<>(0,
                localization.getMessage("success.all-unicorns-found"),
                allUnicorn);
    }

    @GET
    @Path("/resource/{id}")
    @SystemAction(value = "get_unicorns_by_id")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<UnicornEntity> getById(@PathParam("id") Integer id) {
        if (id == null) throw new ParamNotFoundException("id");

        UnicornEntity unicornById = unicornService.getUnicornById(id);
        if (unicornById == null) {
            throw new NotFoundException(localization.getMessage("exception.unicorn-not-found-with-id"));
        } else {
            return new Result<>(0,
                    localization.getMessage("success.unicorn-found"),
                    unicornById);
        }
    }

    @POST
    @Path("/resource")
    @SystemAction(value = "create_unicorn")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<String> create(UnicornEntity unicornEntity) {
        int i = unicornService.createUnicorn(unicornEntity);
        if (i == 0) {
            return new Result<>(1,
                    "Unicorn was not added.",
                    null);

        } else {
            return new Result<>(0,
                    localization.getMessage("success.successfully-added"),
                    null);
        }
    }

    @PUT
    @Path("/resource/{id}")
    @SystemAction(value = "update_unicorn")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Result<String> update(@PathParam("id") Integer id, UnicornEntity unicornEntity) {
        int i = unicornService.updateUnicorn(unicornEntity, id);
        if (i == 0) {
            throw new NotFoundException(localization.getMessage("exception.unicorn-not-found-with-id"));
        } else {
            return new Result<>(0,
                    localization.getMessage("success.successfully-updated"),
                    null);
        }
    }

    @DELETE
    @Path("/resource/{id}")
    @SystemAction(value = "delete_unicorn")
    @Produces({MediaType.APPLICATION_JSON})
    public Result<String> delete(@PathParam("id") int id) {
        int i = unicornService.deleteUnicorn(id);
        if (i == 0) {
            throw new NotFoundException(localization.getMessage("exception.unicorn-not-found-with-id"));
        } else {
            return new Result<>(0,
                    localization.getMessage("success.successfully-deleted"),
                    null);
        }
    }

    @GET
    @Path("/pagination")
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<UnicornEntity>> getUnicornsWithPagination(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("20") int pageSize) {

        int offset = (page - 1) * pageSize;
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        List<UnicornEntity> allUnicorns = unicornService.getAllUnicorn(rowBounds);

        return new Result<>(0,
                localization.getMessage("success.found"),
                allUnicorns);
    }

    @GET
    @Path("/print/excel-write")
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<UnicornEntity>> printUnicornsWithExcel() {
        unicornService.printUnicornToExcel();

        return new Result<>(0,
                localization.getMessage("success.print-unicorns-to-excel"),
                null);
    }

    @GET
    @Path("/print/excel-read")
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<UnicornEntity>> writingUnicornsToDatabase() {
        unicornService.writingUnicornsToDatabase();

        return new Result<>(0,
                localization.getMessage("success.print-unicorns-to-excel"),
                null);
    }
}
