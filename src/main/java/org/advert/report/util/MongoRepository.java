package org.advert.report.util;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by shiqm on 2018-03-15.
 */
public class MongoRepository<T, V> {

    @Autowired
    private MongoTemplate template;

    private Class<T> entityClass;

    public MongoRepository() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            this.entityClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
    }

    private Query getQuery(T t) {
        Query query = new Query();
        //config query
        ReflectionUtils.doWithFields(entityClass, field -> query.addCriteria(Criteria.where(field.getName()).is(field.get(t))), field -> {
            field.setAccessible(true);
            try {
                if (field.get(t) != null && field.get(t).toString().length() > 0) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        });
        return query;
    }

    private Update getUpdate(T t) {
        Update update = new Update();
        //config update
        ReflectionUtils.doWithFields(entityClass, field -> update.set(field.getName(), field.get(t)), field -> {
            field.setAccessible(true);
            try {
                if (field.get(t) != null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        });
        return update;
    }

    /**
     * query by ID
     */
    public T findById(V id) {
        return template.findById(id, entityClass);
    }

    /**
     * query All
     */
    public List<T> findAll() {
        return template.findAll(entityClass);
    }

    /**
     * query by condition
     */
    public List<T> find(T t, Integer page, Integer limit, Sort.Order[] sorts) {
        Query query = this.getQuery(t);
        if (limit > 0) {
            query.skip((page - 1) * limit);
            query.limit(limit);
        }
        if (sorts != null && sorts.length > 0) {
            query.with(new Sort(sorts));
        }
        return template.find(query, entityClass);
    }

    public List<T> find(T t, Integer page, Integer limit, Sort.Order[] sorts, String collectionName) {
        Query query = this.getQuery(t);
        if (limit > 0) {
            query.skip((page - 1) * limit);
            query.limit(limit);
        }
        if (sorts != null && sorts.length > 0) {
            query.with(new Sort(sorts));
        }
        return template.find(query, entityClass, collectionName);
    }


    /**
     * query And Modify
     */
    public T findAndModify(T t, T u) {
        Query query = this.getQuery(t);
        Update update = this.getUpdate(u);
        return template.findAndModify(query, update, entityClass);
    }

    /**
     * query And findOne
     */
    public T findOne(T t) {
        Query query = this.getQuery(t);
        return template.findOne(query, entityClass);
    }

    /**
     * query And Remove
     */
    public T findAndRemove(T t) {
        Query query = this.getQuery(t);
        return template.findAndRemove(query, entityClass);
    }

    /**
     * query And RemoveAll
     */
    public List<T> findAndRemoveAll(T t) {
        Query query = this.getQuery(t);
        return template.findAllAndRemove(query, entityClass);
    }

    public List<T> findByIds(List<V> ids, Sort.Order[] sorts) {
        Query query = new Query();
        //config query
        query.addCriteria(Criteria.where("_id").in(ids));
        if (sorts != null && sorts.length > 0) {
            query.with(new Sort(sorts));
        }
        return template.find(query, entityClass);
    }

    /**
     * count
     */
    public Long count(T t) {
        Query query = this.getQuery(t);
        return template.count(query, entityClass);
    }

    /**
     * exists
     */
    public boolean exists(T t) {
        Query query = this.getQuery(t);
        return template.exists(query, entityClass);
    }

    /**
     * save
     */
    public void save(T t) {
        template.insert(t);
    }

    /**
     * save
     */
    public void save(T t, String collectionName) {
        template.insert(t, collectionName);
    }

    /**
     * remove
     */
    public Integer remove(T t) {
        return template.remove(t).getN();
    }

    /**
     * updateById
     */
    public Integer updateById(T t) {
        return this.updateById(t, null);
    }

    /**
     * updateById
     */
    public Integer updateById(T t, String collectionName) {
        Query query = new Query();
        Update update = new Update();
        ReflectionUtils.doWithFields(entityClass, field -> {
            if (field.isAnnotationPresent(Id.class)) {
                query.addCriteria(Criteria.where("_id").is(field.get(t)));
            } else {
                update.set(field.getName(), field.get(t));
            }
        }, field -> {
            field.setAccessible(true);
            try {
                if (field.get(t) != null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        });

        if (query.getQueryObject().toMap().size() == 0) {
            return 0;
        }
        if (StringUtils.isEmpty(collectionName)) {
            return template.updateFirst(query, update, entityClass).getN();
        } else {
            return template.updateFirst(query, update, collectionName).getN();
        }
    }


    /**
     * update
     */
    public Integer update(T t, T u) {
        Query query = this.getQuery(t);
        Update update = this.getUpdate(u);
        return template.updateMulti(query, update, entityClass).getN();
    }

    /**
     * inc
     */
    public T inc(T t, String key, Integer num) {
        Query query = this.getQuery(t);
        //config update
        Update update = new Update();
        update.inc(key, num);
        return template.findAndModify(query, update, entityClass);
    }


    /**
     * dropCollection
     */
    public void dropCollection(String collectionName) {
        template.dropCollection(collectionName);
    }


    /**
     * aggregate
     */

    public List<Map> aggregate(Aggregation aggregation) {
        AggregationResults<Map> aggRes = template.aggregate(aggregation,
                entityClass, Map.class);
        return aggRes.getMappedResults();

    }

}
