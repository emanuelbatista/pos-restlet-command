/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pos.command;

//import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.cli2.Argument;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.validation.EnumValidator;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Commands {

    public static final String TYPE_PERSON="json_person";
    public static final String TYPE_USER="json_user";
    
    private final DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
    private final ArgumentBuilder abuilder = new ArgumentBuilder();
    private final GroupBuilder gbuilder = new GroupBuilder();
    

    public Group createOptions() {
        Option help = obuilder.
                withLongName("help").
                withDescription("Ajuda para o usuário").
                create();
        //
        Set<String> typeEntity = new TreeSet();
        typeEntity.add("json_person");
        typeEntity.add("json_user");
        EnumValidator enumValidator = new EnumValidator(typeEntity);
        
        Option type = obuilder.
                withLongName("type").
                withDescription("Tipo de Objeto a ser salvo").
                withArgument(
                        abuilder.
                        withMaximum(1).
                        withMinimum(1).
                        withName("type").
                        withValidator(enumValidator).
                        create()
                ).create();
        //
        Group groupType = gbuilder
                .withOption(type)
                .withMaximum(1)
                .withMinimum(1)
                .withRequired(true)
                .create();
        //
        Argument argument = abuilder
                .withName("json_object")
                .withMaximum(1)
                .withMinimum(1)
                .create();
        //
        Option insert = obuilder
                .withLongName("insert")
                .withDescription("Insere um novo objeto em formarto JSON de acordo com os atributos da entidade")
                .withChildren(groupType)
                .withArgument(argument)
                .create();
        Option delete = obuilder
                .withLongName("delete")
                .withDescription("Exclui um objeto passando a chave da entidade nesse formato: {\"key\": value}")
                .withArgument(argument)
                .withChildren(groupType)
                .create();
        Option update = obuilder
                .withLongName("update")
                .withDescription("Atualiza um objeto em formarto JSON de acordo com esse formato: {\"key\": value, outros parametros...}")
                .withArgument(argument)
                .withChildren(groupType)
                .create();

        Group crudGroup = gbuilder
                .withOption(update)
                .withOption(insert)
                .withOption(delete)
                .withRequired(true)
                .create();
        
        Option select=obuilder
                .withLongName("select")
                .withDescription("Visualiza um objeto passando a chave da entidade nesse formato: {\"key\": value}")
                .withArgument(argument)
                .withChildren(groupType)
                .create();

        Group group = gbuilder.
                withOption(help).
                withOption(crudGroup).
                withOption(select).
                create();

        return group;
    }

}
