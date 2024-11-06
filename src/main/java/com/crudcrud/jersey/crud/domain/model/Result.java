package com.crudcrud.jersey.crud.domain.model;

public class Result<T> {

    private Integer rusult_code;
    private String result_message;
    private T rows;

    public Result(Integer rusult_code, String result_message, T rows) {
        this.rusult_code = rusult_code;
        this.result_message = result_message;
        this.rows = rows;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public Integer getRusult_code() {
        return rusult_code;
    }

    public void setRusult_code(Integer rusult_code) {
        this.rusult_code = rusult_code;
    }
}
