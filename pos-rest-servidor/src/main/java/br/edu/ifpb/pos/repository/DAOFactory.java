/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pos.repository;

import br.edu.ifpb.pos.entidades.Person;
import br.edu.ifpb.pos.entidades.User;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DAOFactory {
    
    public static DAOJPA<Person> createDaoPerson(){
        return new DAOJPA<>();
    }
    
    public static DAOJPA<User> createDaoUser(){
        return new DAOJPA<>();
    }
}
