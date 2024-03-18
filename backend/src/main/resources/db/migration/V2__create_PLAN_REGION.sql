create table PLAN_REGION
(
    id         bigint not null auto_increment,
    plan_id    bigint not null,
    region_id  bigint not null,
    primary key (id)
);

ALTER TABLE PLAN_REGION
    ADD CONSTRAINT FK_PLAN_REGION_PLAN_ID
        FOREIGN KEY (plan_id)
            REFERENCES PLAN(id);

ALTER TABLE PLAN_REGION
    ADD CONSTRAINT FK_PLAN_REGION_REGION_ID
        FOREIGN KEY (region_id)
            REFERENCES REGION(id);