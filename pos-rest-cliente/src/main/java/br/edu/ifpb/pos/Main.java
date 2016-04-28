/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pos;

import br.edu.ifpb.pos.command.CommandExecute;
import br.edu.ifpb.pos.command.Commands;
import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.commandline.Parser;
import org.restlet.resource.ResourceException;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Main {

    public static void main(String[] args)  {
        try {
            Parser parser=new Parser();
            //
            Commands commands=new Commands();
            Group options=commands.createOptions();
            //
            parser.setGroup(options);
            CommandLine line = parser.parse(args);
            CommandExecute execute=new CommandExecute(line,options);
            execute.run();
        } catch (OptionException ex) {
            System.out.println(ex.getMessage());
        } catch(ResourceException ex){
            System.out.println("ERROR: "+ex.getMessage() );
        }


    }
}
