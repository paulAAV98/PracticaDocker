package distribuidos;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
public abstract class AbstractFacade<T>{

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public boolean create(T entity) {
        try {
            this.getEntityManager().persist(entity);
            this.getEntityManager().flush();
            return true;
        } catch (Exception var3) {
            System.out.println("MENSAJE: " + var3.getMessage() + "\tCAUSA: " + var3.getCause());
            return false;
        }
    }

    public boolean edit(T entity) {
        try {
            this.getEntityManager().merge(entity);
            return true;
        } catch (Exception var3) {
            System.out.println("MENSAJE: " + var3.getMessage() + "\tCausa: " + var3.getCause());
            return false;
        }
    }

    public boolean remove(T entity) {
        try {
            if (!this.getEntityManager().contains(entity)) {
                entity = this.getEntityManager().merge(entity);
            }

            this.getEntityManager().remove(entity);
            return true;
        } catch (Exception var3) {
            System.out.println(var3.getMessage() + "\nCause:" + var3.getCause());
            return false;
        }
    }

    public T find(Object id) {
        try {
            this.getEntityManager().refresh(this.getEntityManager().find(this.entityClass, id));
            return this.getEntityManager().find(this.entityClass, id);
        } catch (Exception var3) {
            System.out.println("Error al obtener el objeto: " + var3.getMessage());
            return null;
        }
    }

    public List<T> findAll() {
        CriteriaQuery criteriaQuery = this.getEntityManager().getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(this.entityClass));
        TypedQuery query = this.getEntityManager().createQuery(criteriaQuery);

        try {
            return query.getResultList();
        } catch (Exception var4) {
            System.out.println("Error al obtener una lista de objetos: " + var4.getMessage());
            return new ArrayList();
        }
    }

    public int count() {
        CriteriaQuery cq = this.getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(this.entityClass);
        cq.select(this.getEntityManager().getCriteriaBuilder().count(rt));
        Query q = this.getEntityManager().createQuery(cq);
        return ((Long)q.getSingleResult()).intValue();
    }

}
