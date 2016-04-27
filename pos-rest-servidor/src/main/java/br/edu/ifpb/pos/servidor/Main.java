/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pos.servidor;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8080);
        //
        Router router = new Router();
        router.attach("/persons", PersonResources.class);
        router.attach("/persons/{id}", PersonResources.class);
        router.attach("/users", UserResources.class);
        router.attach("/users/{id}", UserResources.class);
        //
        Application application = new Application();
        application.setInboundRoot(router);
        //
        component.getDefaultHost().attach("/app", application);
        component.start();
    }
}
