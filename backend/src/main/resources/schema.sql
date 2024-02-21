use dev;

drop table if exists REVIEW;
drop table if exists RECOMMENDED_PLACE;
drop table if exists SCHEDULE_PLACE;
drop table if exists PLACE;
drop table if exists CONTENT_TYPE;
drop table if exists SCHEDULE;
drop table if exists PLAN;
drop table if exists REGION;
drop table if exists SUB_CATEGORY;
drop table if exists USER;


create table CONTENT_TYPE
(
    id            bigint      not null,
    name          varchar(20) not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table PLACE
(
    id               bigint      not null,
    region_id        bigint,
    content_type_id  bigint,
    sub_category_id  varchar(10),
    address          varchar(255),
    address_detail   varchar(255),
    name             varchar(100) not null,
    main_category_id varchar(10),
    mid_category_id  varchar(10),
    image_origin     varchar(255),
    image_thumbnail  varchar(255),
    latitude         double   not null,
    longitude        double   not null,
    sigungu_code     integer,
    views            bigint  not null default 0,
    created_date     datetime(6),
    modified_date    datetime(6),
    primary key (id)
) engine = InnoDB;

create table PLAN
(
    id            bigint      not null auto_increment,
    user_id       bigint,
    title         varchar(20) not null,
    end_date      date        not null,
    start_date    date        not null,
    code          varchar(255),
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table REGION
(
    id              bigint       not null,
    name            varchar(255) not null,
    image_origin    varchar(255),
    image_thumbnail varchar(255),
    latitude        double    not null,
    longitude       double    not null,
    overview        text,
    created_date    datetime(6),
    modified_date   datetime(6),
    primary key (id)
) engine = InnoDB;

create table RECOMMENDED_PLACE
(
    id        bigint not null auto_increment,
    region_id bigint,
    place_id  bigint,
    overview  text,
    created_date    datetime(6),
    modified_date   datetime(6),
    primary key (id)
) engine=InnoDB;

create table REVIEW
(
    id            bigint    not null auto_increment,
    user_id       bigint,
    place_id      bigint,
    tip           text,
    content       text      not null,
    score         float(23) not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table SCHEDULE
(
    id            bigint not null auto_increment,
    plan_id       bigint,
    visit_date    date   not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table SCHEDULE_PLACE
(
    id          bigint not null auto_increment,
    schedule_id bigint not null,
    place_id    bigint not null,
    visit_order int    not null,
    expense     int null default 0,
    memo        TEXT null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table SUB_CATEGORY
(
    id            varchar(10) not null,
    name          varchar(20) not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table USER
(
    id              bigint      not null auto_increment,
    name            varchar(10),
    nickname        varchar(10) not null,
    email           varchar(30),
    image_origin    varchar(255),
    image_thumbnail varchar(255),
    created_date    datetime(6),
    modified_date   datetime(6),
    primary key (id)
) engine = InnoDB;


alter table PLACE
    add constraint UK_CONTENT_TYPE_ID unique (content_type_id);
alter table PLACE
    add constraint FK_PLACE_CONTENT_TYPE foreign key (content_type_id) references CONTENT_TYPE (id);
alter table PLACE
    add constraint FK_PLACE_REGION foreign key (region_id) references REGION (id);
alter table PLACE
    add constraint FK_PLACE_SUB_CATEGORY foreign key (sub_category_id) references SUB_CATEGORY (id);

alter table PLAN
    add constraint FK_PLAN_USER foreign key (user_id) references USER (id);

alter table REVIEW
    add constraint FK_REVIEW_PLACE foreign key (place_id) references PLACE (id);
alter table REVIEW
    add constraint FK_REVIEW_USER foreign key (user_id) references USER (id);

alter table SCHEDULE
    add constraint FK_SCHEDULE_PLAN foreign key (plan_id) references PLAN (id);

alter table SCHEDULE_PLACE
    add constraint FK_SCHEDULE_PLACE_SCHEDULE foreign key (schedule_id) references SCHEDULE (id);
alter table SCHEDULE_PLACE
    add constraint FK_SCHEDULE_PLACE_PLACE foreign key (place_id) references PLACE (id);

alter table RECOMMENDED_PLACE
    add constraint FK_RECOMMENDED_PLACE_PLACE foreign key (place_id) references PLACE (id);
alter table RECOMMENDED_PLACE
    add constraint FK_RECOMMENDED_PLACE_REGION foreign key (region_id) references REGION (id);