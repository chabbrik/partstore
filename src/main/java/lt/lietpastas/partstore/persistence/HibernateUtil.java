package lt.lietpastas.partstore.persistence;

import lt.lietpastas.partstore.dataloaders.CsvDataLoader;
import lt.lietpastas.partstore.entities.CarPartDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HibernateUtil {
    Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    public List<CarPartDTO> getInventoryList() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from CarPartDTO", CarPartDTO.class).list();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void save(CarPartDTO part) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(part);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e.getMessage());
        }
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                logger.error(e.getMessage());
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}