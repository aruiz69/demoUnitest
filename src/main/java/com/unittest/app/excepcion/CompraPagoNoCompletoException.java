package com.unittest.app.excepcion;

public class CompraPagoNoCompletoException extends IllegalStateException{
    public CompraPagoNoCompletoException(String mensje){
        super(mensje);
    }
}
