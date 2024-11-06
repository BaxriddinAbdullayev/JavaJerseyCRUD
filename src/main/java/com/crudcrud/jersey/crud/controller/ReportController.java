package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.annotations.Public;
import com.crudcrud.jersey.crud.annotations.SystemAction;
import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.service.ReportService;
import com.crudcrud.jersey.crud.utils.Localization;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/reports")
public class ReportController {

    @Inject
    private ReportService reportService;

    @Inject
    private Localization localization;

    @GET
    @Path("/print-report")
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    public Result<String> getAll() {
        reportService.getReport();

        return new Result<>(0,
                localization.getMessage("success.report-successful-published"),
                null);
    }
}
