package br.edu.ifpb.pos.repository;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * @author job
 * @param <T>
 */
public class DAOJPA<T> {

    protected EntityManager entityManager;

    public DAOJPA() {
        this("br.edu.ifpb.pos_pos-rest-servidor_jar_1.0PU");
    }

    public DAOJPA(String unidadePersistencia) {
        entityManager = Persistence.createEntityManagerFactory(unidadePersistencia).createEntityManager();
    }

    public boolean salvar(T obj) throws Exception {
        EntityTransaction transacao = entityManager.getTransaction();

        try {
            transacao.begin();
            entityManager.persist(obj);
            transacao.commit();
            return true;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            throw new Exception(ex);
        }

    }

    public boolean atualizar(T obj) throws Exception {
        EntityTransaction transacao = entityManager.getTransaction();

        try {
            transacao.begin();
            entityManager.merge(obj);
            transacao.commit();
            return true;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }

            throw new Exception(ex);
        }
    }

    public boolean excluir(T obj) throws Exception {
        EntityTransaction transacao = entityManager.getTransaction();

        try {
            transacao.begin();
            entityManager.remove(obj);
            transacao.commit();
            return true;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }

            throw new Exception(ex);

        }
    }

    public T buscar(Object chave, Class<T> entidade) {
        return entityManager.find(entidade, chave);
    }

    public List<T> consultaLista(String consulta, Map<String, Object> parametros) {
        Query query = entityManager.createNamedQuery(consulta);
        if (parametros != null && !parametros.isEmpty()) {
            for (String parametro : parametros.keySet()) {
                query.setParameter(parametro, parametros.get(parametro));
            }
        }
        return query.getResultList();
    }

    public T consultaSimples(String consulta, Map<String, Object> parametros) {
        Query query = entityManager.createNamedQuery(consulta);
        if (parametros != null && !parametros.isEmpty()) {
            for (String parametro : parametros.keySet()) {
                query.setParameter(parametro, parametros.get(parametro));
            }
        }
        return (T) query.getSingleResult();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
