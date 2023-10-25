package ru.neoflex.tariffs.repositories.audit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Component;
import ru.neoflex.tariffs.models.Tariff;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class AuditRepositoryImpl implements AuditRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tariff> getAllVersions(UUID id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Tariff.class, true, true)
                .add(AuditEntity.id().eq(id));

        List<Tariff> results = auditQuery.getResultList();

        return results;
    }

    @Override
    public List<Tariff> getVersionForCertainPeriod(UUID id, LocalDate startDate, LocalDate endDate) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Tariff.class, true, true)
                .add(AuditEntity.id().eq(id))
                .add(AuditEntity.property("startDate").ge(startDate))
                .add(AuditEntity.property("endDate").le(endDate));

        List<Tariff> results = auditQuery.getResultList();

        return results;
    }

    @Override
    public Tariff getPreviousVersion(UUID id, Integer currentVersion) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Tariff.class, true, true)
                .add(AuditEntity.id().eq(id))
                .add(AuditEntity.property("version").eq(currentVersion - 1));

        Tariff result = (Tariff) auditQuery.getSingleResult();

        return result;
    }

    @Override
    public void deleteCurrentVersion(UUID id, Integer currentVersion) {
        entityManager.createNativeQuery("delete from tariff_aud where id = :id and (version = :currentV or version = :previousV)")
                .setParameter("id", id)
                .setParameter("currentV", currentVersion)
                .setParameter("previousV", currentVersion - 1)
                .executeUpdate();
    }
}
