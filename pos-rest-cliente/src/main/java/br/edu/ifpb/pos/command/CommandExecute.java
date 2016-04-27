/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pos.command;

import static br.edu.ifpb.pos.command.Commands.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.util.HelpFormatter;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class CommandExecute implements Runnable {

    private final static String URL = "http://localhost:8080/app";
    private CommandLine line;
    private Group options;

    public CommandExecute(CommandLine line, Group options) {
        this.line = line;
        this.options = options;
    }

    @Override
    public void run() throws ResourceException {
        System.out.println("");
        
        if (line.hasOption("--help")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.setGroup(options);
            helpFormatter.setDivider(" ");
            helpFormatter.getFullUsageSettings().remove(DisplaySetting.DISPLAY_GROUP_OUTER);
            helpFormatter.print();
        }
        
        if (line.hasOption("--insert")) {
            Representation representation = new StringRepresentation((CharSequence) line.getValue("--insert"), MediaType.APPLICATION_JSON);
            ClientResource clientResource;
            if (line.getValue("--type").equals(TYPE_PERSON)){
                clientResource = new ClientResource(URL + "/persons");
            } else {
                clientResource = new ClientResource(URL + "/users");
            }
            clientResource.post(representation);
            System.out.println("Dados inseridos com sucesso");
        }

        if (line.hasOption("--update")) {
            Representation representation = new StringRepresentation((CharSequence) line.getValue("--update"), MediaType.APPLICATION_JSON);
            ClientResource clientResource;
            if (line.getValue("--type").equals(TYPE_PERSON)){
                clientResource = new ClientResource(URL + "/persons");
            } else {
                clientResource = new ClientResource(URL + "/users");
            }
            clientResource.put(representation);
             System.out.println("Dados inseridos com sucesso");
        }

        if (line.hasOption("--delete")) {
            ClientResource clientResource;
            String key=getKey(line.getValue("--delete").toString());
            
            if (line.getValue("--type").equals(TYPE_PERSON)){
                clientResource = new ClientResource(URL + "/persons/"+key);
            } else {
                clientResource = new ClientResource(URL + "/users/"+key);

            }
            clientResource.delete();
             System.out.println("Dados inseridos com sucesso");
        }

        if (line.hasOption("--select")) {
            ClientResource clientResource;
            String key=getKey(line.getValue("--select").toString());
            if (line.getValue("--type").equals(TYPE_PERSON)){
                clientResource = new ClientResource(URL + "/persons/"+key);
            } else {
                clientResource = new ClientResource(URL + "/users/"+key);
            }
            Representation representation=clientResource.get();
            try {
                System.out.println(representation.getText());
            } catch (IOException ex) {
                Logger.getLogger(CommandExecute.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String getKey(String key){
        Gson gson=new Gson();
        CommandKey commandKey=gson.fromJson(key, CommandKey.class);
        return commandKey.getKey();
    }

}
