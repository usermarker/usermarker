package com.xiaoshu.entity;

import java.util.ArrayList;
import java.util.List;

public class TeacherExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TeacherExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andTidIsNull() {
            addCriterion("tid is null");
            return (Criteria) this;
        }

        public Criteria andTidIsNotNull() {
            addCriterion("tid is not null");
            return (Criteria) this;
        }

        public Criteria andTidEqualTo(Integer value) {
            addCriterion("tid =", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotEqualTo(Integer value) {
            addCriterion("tid <>", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThan(Integer value) {
            addCriterion("tid >", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThanOrEqualTo(Integer value) {
            addCriterion("tid >=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThan(Integer value) {
            addCriterion("tid <", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThanOrEqualTo(Integer value) {
            addCriterion("tid <=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidIn(List<Integer> values) {
            addCriterion("tid in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotIn(List<Integer> values) {
            addCriterion("tid not in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidBetween(Integer value1, Integer value2) {
            addCriterion("tid between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotBetween(Integer value1, Integer value2) {
            addCriterion("tid not between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andTnameIsNull() {
            addCriterion("tname is null");
            return (Criteria) this;
        }

        public Criteria andTnameIsNotNull() {
            addCriterion("tname is not null");
            return (Criteria) this;
        }

        public Criteria andTnameEqualTo(String value) {
            addCriterion("tname =", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotEqualTo(String value) {
            addCriterion("tname <>", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameGreaterThan(String value) {
            addCriterion("tname >", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameGreaterThanOrEqualTo(String value) {
            addCriterion("tname >=", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameLessThan(String value) {
            addCriterion("tname <", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameLessThanOrEqualTo(String value) {
            addCriterion("tname <=", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameLike(String value) {
            addCriterion("tname like", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotLike(String value) {
            addCriterion("tname not like", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameIn(List<String> values) {
            addCriterion("tname in", values, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotIn(List<String> values) {
            addCriterion("tname not in", values, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameBetween(String value1, String value2) {
            addCriterion("tname between", value1, value2, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotBetween(String value1, String value2) {
            addCriterion("tname not between", value1, value2, "tname");
            return (Criteria) this;
        }

        public Criteria andShengidIsNull() {
            addCriterion("shengid is null");
            return (Criteria) this;
        }

        public Criteria andShengidIsNotNull() {
            addCriterion("shengid is not null");
            return (Criteria) this;
        }

        public Criteria andShengidEqualTo(Integer value) {
            addCriterion("shengid =", value, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidNotEqualTo(Integer value) {
            addCriterion("shengid <>", value, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidGreaterThan(Integer value) {
            addCriterion("shengid >", value, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidGreaterThanOrEqualTo(Integer value) {
            addCriterion("shengid >=", value, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidLessThan(Integer value) {
            addCriterion("shengid <", value, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidLessThanOrEqualTo(Integer value) {
            addCriterion("shengid <=", value, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidIn(List<Integer> values) {
            addCriterion("shengid in", values, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidNotIn(List<Integer> values) {
            addCriterion("shengid not in", values, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidBetween(Integer value1, Integer value2) {
            addCriterion("shengid between", value1, value2, "shengid");
            return (Criteria) this;
        }

        public Criteria andShengidNotBetween(Integer value1, Integer value2) {
            addCriterion("shengid not between", value1, value2, "shengid");
            return (Criteria) this;
        }

        public Criteria andShiidIsNull() {
            addCriterion("shiid is null");
            return (Criteria) this;
        }

        public Criteria andShiidIsNotNull() {
            addCriterion("shiid is not null");
            return (Criteria) this;
        }

        public Criteria andShiidEqualTo(Integer value) {
            addCriterion("shiid =", value, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidNotEqualTo(Integer value) {
            addCriterion("shiid <>", value, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidGreaterThan(Integer value) {
            addCriterion("shiid >", value, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidGreaterThanOrEqualTo(Integer value) {
            addCriterion("shiid >=", value, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidLessThan(Integer value) {
            addCriterion("shiid <", value, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidLessThanOrEqualTo(Integer value) {
            addCriterion("shiid <=", value, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidIn(List<Integer> values) {
            addCriterion("shiid in", values, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidNotIn(List<Integer> values) {
            addCriterion("shiid not in", values, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidBetween(Integer value1, Integer value2) {
            addCriterion("shiid between", value1, value2, "shiid");
            return (Criteria) this;
        }

        public Criteria andShiidNotBetween(Integer value1, Integer value2) {
            addCriterion("shiid not between", value1, value2, "shiid");
            return (Criteria) this;
        }

        public Criteria andQuidIsNull() {
            addCriterion("quid is null");
            return (Criteria) this;
        }

        public Criteria andQuidIsNotNull() {
            addCriterion("quid is not null");
            return (Criteria) this;
        }

        public Criteria andQuidEqualTo(Integer value) {
            addCriterion("quid =", value, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidNotEqualTo(Integer value) {
            addCriterion("quid <>", value, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidGreaterThan(Integer value) {
            addCriterion("quid >", value, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("quid >=", value, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidLessThan(Integer value) {
            addCriterion("quid <", value, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidLessThanOrEqualTo(Integer value) {
            addCriterion("quid <=", value, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidIn(List<Integer> values) {
            addCriterion("quid in", values, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidNotIn(List<Integer> values) {
            addCriterion("quid not in", values, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidBetween(Integer value1, Integer value2) {
            addCriterion("quid between", value1, value2, "quid");
            return (Criteria) this;
        }

        public Criteria andQuidNotBetween(Integer value1, Integer value2) {
            addCriterion("quid not between", value1, value2, "quid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}