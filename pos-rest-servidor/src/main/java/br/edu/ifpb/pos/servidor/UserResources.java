/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pos.servidor;

import br.edu.ifpb.pos.entidades.User;
import br.edu.ifpb.pos.repository.DAOFactory;
import br.edu.ifpb.pos.repository.DAOJPA;
import com.google.gson.Gson;
import java.io.IOException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class UserResources extends ServerResource{
     private final DAOJPA<User> dao = DAOFactory.createDaoUser();
    private final Gson gson = new Gson();

    @Post()
    public StringRepresentation save(Representation representation) {
        try {
            String userString = representation.getText();
            User person = gson.fromJson(userString, User.class);
            dao.salvar(person);
            return new StringRepresentation("SUCCESS");
        } catch (IOException ex) {
            return new StringRepresentation("ERROR: " + ex.getMessage());
        }
    }

    @Delete
    public StringRepresentation delete() {
        String userString = (String) getRequest().getAttributes().get("id");
        User person = dao.buscar(userString, User.class);
        dao.excluir(person);
        return new StringRepresentation("SUCCESS");
    }

    @Put
    public StringRepresentation update(Representation representation) {
        try {
            String userString = representation.getText();
            User person = gson.fromJson(userString, User.class);
            dao.atualizar(person);
            return new StringRepresentation("SUCCESS");
        } catch (IOException ex) {
            return new StringRepresentation("ERROR: " + ex.getMessage());
        }
    }

    @Get()
    public StringRepresentation getOne() {
        String id = (String) getRequest().getAttributes().get("id");
        User user = dao.buscar(id, User.class);
        return new StringRepresentation(gson.toJson(user));
    }
}
