package com.radovan.play.controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class HealthController extends Controller {
    public Result healthCheck(Http.Request request) {
        System.out.println("Health check called from: " + request.remoteAddress());
        return ok("OK");
    }
}