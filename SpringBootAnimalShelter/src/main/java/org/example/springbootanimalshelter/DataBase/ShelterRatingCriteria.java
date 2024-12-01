package org.example.springbootanimalshelter.DataBase;

import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;

import jakarta.persistence.criteria.CriteriaBuilder;

import jakarta.persistence.criteria.CriteriaQuery;

import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public class ShelterRatingCriteria {

    @PersistenceContext

    private EntityManager entityManager;

    public List<Object[]> getAverageRatingPerShelter() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<ShelterRatingEncja> root = query.from(ShelterRatingEncja.class);

        query.multiselect(

                root.get("shelter").get("name"),

                cb.avg(root.get("score"))

        ).groupBy(root.get("shelter").get("id"));

        return entityManager.createQuery(query).getResultList();

    }

}
