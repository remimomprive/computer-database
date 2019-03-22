package com.excilys.rmomprive.computerdatabase.persistence;

import org.hibernate.Session;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

public abstract class Dao<T> {
  public static Session getSession(LocalSessionFactoryBean localSessionFactoryBean) {
    return localSessionFactoryBean.getObject().openSession();
  }

  public HibernateQuery<T> getQuery(Session session) {
    return new HibernateQuery<>(session);
  }

  public HibernateDeleteClause getDeleteClause(Session session, EntityPath<?> entityPath) {
    return new HibernateDeleteClause(session, entityPath);
  }

  public HibernateQueryFactory getQueryFactory(Session session) {
    return new HibernateQueryFactory(session);
  }
}
