package distribuidos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductoFacade extends AbstractFacade<ProductoRepository> {

    @PersistenceContext(
            unitName = "demoappdockerPersistenceUnit"
    )
    private EntityManager entityManager;

    public ProductoFacade(){
        super(ProductoRepository.class);
    }

    protected EntityManager getEntityManager(){
        return this.entityManager;
    }
}
