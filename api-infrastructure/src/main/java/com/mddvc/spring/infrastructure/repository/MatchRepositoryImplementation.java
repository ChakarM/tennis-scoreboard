package com.mddvc.spring.infrastructure.repository;

import com.mddvc.spring.domain.model.MatchModel;
import com.mddvc.spring.domain.model.PlayerModel;
import com.mddvc.spring.domain.repository.MatchRepository;
import com.mddvc.spring.infrastructure.entity.MatchEntity;
import com.mddvc.spring.infrastructure.entity.PlayerEntity;
import com.mddvc.spring.infrastructure.mapper.MatchMapper;
import com.mddvc.spring.infrastructure.mapper.PlayerMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Transactional
public class MatchRepositoryImplementation implements MatchRepository {
    private final SessionFactory sessionFactory;
    private static final MatchMapper matchMapper = new MatchMapper();
    private static final PlayerMapper playerMapper = new PlayerMapper();

    @Autowired
    public MatchRepositoryImplementation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public void save(MatchModel matchModel) {
        PlayerEntity player1 = findOrCreatePlayer(matchModel.getPlayer(1).getName());
        PlayerEntity player2 = findOrCreatePlayer(matchModel.getPlayer(2).getName());
        PlayerEntity winner = matchModel.getWinner().getName().equals(player1.getName()) ? player1 : player2;

        MatchEntity match = new MatchEntity(player1, player2, winner);

        getCurrentSession().persist(match);
    }

    private PlayerEntity findOrCreatePlayer(String name) {
        List<PlayerEntity> existing = getCurrentSession().createQuery("from PlayerEntity where name = :name", PlayerEntity.class)
                .setParameter("name", name)
                .getResultList();
        if (!existing.isEmpty()) {
            return existing.get(0);
        }
        PlayerEntity player = new PlayerEntity();
        player.setName(name);
        getCurrentSession().persist(player);
        return player;
    }

    @Override
    public Long getTotalPages(String name) {
        int offset = 3;
        long totalRows = 0;
        if (name.isEmpty()) {
            totalRows = getCurrentSession().createQuery("select count(m) from MatchEntity m", Long.class)
                    .getSingleResult();
        } else {
            totalRows = getCurrentSession().createQuery("select count(m) from MatchEntity m where m.playerEntity1.name = :name or m.playerEntity2.name = :name", Long.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        return Math.max(1, (long) Math.ceil((double) totalRows / offset));
    }

    //    @Override
//    @Transactional
//    public List<MatchModel> findAll(int page) {
//        int offset = page * 3;
//        return getCurrentSession().createQuery("from MatchEntity", MatchEntity.class)
//                .setFirstResult(offset)
//                .setMaxResults(3)
//                .getResultList()
//                .stream()
//                .map(matchMapper::toModel)
//                .toList();
//    }

    @Override
    public List<MatchModel> findAll(String name, int page) {
        int offset = page * 3;
        if (name.isEmpty() && page == 0) {
            return getCurrentSession().createQuery("from MatchEntity", MatchEntity.class)
                    .setFirstResult(offset)
                    .setMaxResults(3)
                    .getResultList()
                    .stream()
                    .map(matchMapper::toModel)
                    .toList();
        }

        return getCurrentSession()
                .createQuery("from MatchEntity where playerEntity1.name = :player or playerEntity2.name = :player", MatchEntity.class)
                .setParameter("player", name)
                .setFirstResult(offset)
                .setMaxResults(3)
                .getResultList()
                .stream()
                .map(matchMapper::toModel)
                .toList();
    }


}
