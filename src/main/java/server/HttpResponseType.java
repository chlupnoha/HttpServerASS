package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chlupnoha
 */
public enum HttpResponseType {
    _200_OK("200 OK"),
    _201_CREATED("201 Created"),
    _204_NO_CONTENT("204 No Content"),
    _401_UNAUTHRORIZED("401 Authorization Required"),
    _404_NOT_FOUND("404 Not Found"),
    _415_UNSUPPORTED_MEDIA_TYPE("415 Unsupported Media Type"),
    _500_INTERNAL_SERVER_ERROR("500 Internal Server Error"),;

    private final String name;       

    private HttpResponseType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
