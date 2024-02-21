# dev 데이터베이스 사용
use dev;

# 삭제 순서 고정
drop table if exists review_image;
drop table if exists review;
drop table if exists recommended_place;
drop table if exists schedule_place;
drop table if exists place;
drop table if exists content_type;
drop table if exists schedule;
drop table if exists plan;
drop table if exists region;
drop table if exists sub_category;
drop table if exists user;

# 테이블 생성
create table content_type
(
    id            bigint      not null,
    name          varchar(20) not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table place
(
    id               bigint      not null,
    region_id        bigint,
    content_type_id  bigint,
    sub_category_id  varchar(10),
    address          varchar(255),
    address_detail   varchar(255),
    name             varchar(20) not null,
    main_category_id varchar(10),
    mid_category_id  varchar(10),
    image_origin     varchar(255),
    image_thumbnail  varchar(255),
    latitude         float(53)   not null,
    longitude        float(53)   not null,
    sigungu_code     integer,
    views            bigint      not null,
    created_time     varchar(20),
    modified_time    varchar(20),
    primary key (id)
) engine = InnoDB;

create table plan
(
    id            bigint      not null auto_increment,
    user_id       bigint,
    name          varchar(20) not null,
    code          varchar(255),
    end_date      date        not null,
    start_date    date        not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table region
(
    id              bigint       not null,
    name            varchar(255) not null,
    image_origin    varchar(255),
    image_thumbnail varchar(255),
    latitude        float(53)    not null,
    longitude       float(53)    not null,
    overview        text,
    created_date    datetime(6),
    modified_date   datetime(6),
    primary key (id)
) engine = InnoDB;

create table recommended_place
(
    id        bigint not null auto_increment,
    region_id bigint,
    place_id  bigint,
    overview  text,
    created_date    datetime(6),
    modified_date   datetime(6),
    primary key (id)
) engine=InnoDB;

create table review
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

create table review_image
(
    id            bigint       not null auto_increment,
    review_id     bigint,
    image_origin  varchar(255) not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table schedule
(
    id            bigint not null auto_increment,
    plan_id       bigint,
    visit_date    date   not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table schedule_place
(
    id          bigint not null auto_increment,
    schedule_id bigint not null,
    place_id    bigint not null,
    visit_order int    not null,
    expense     int null default 0,
    memo        TEXT null,
    primary key (id)
) engine = InnoDB;

create table sub_category
(
    id            varchar(10) not null,
    name          varchar(10) not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table user
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


# 제약조건
alter table place
    add constraint UK_CONTENT_TYPE_ID unique (content_type_id);
alter table place
    add constraint FK_PLACE_CONTENT_TYPE foreign key (content_type_id) references content_type (id);
alter table place
    add constraint FK_PLACE_REGION foreign key (region_id) references region (id);
alter table place
    add constraint FK_PLACE_SUB_CATEGORY foreign key (sub_category_id) references sub_category (id);

alter table plan
    add constraint FK_PLAN_USER foreign key (user_id) references user (id);

alter table review
    add constraint FK_REVIEW_PLACE foreign key (place_id) references place (id);
alter table review
    add constraint FK_REVIEW_USER foreign key (user_id) references user (id);

alter table review_image
    add constraint FK_REVIEW_IMAGE_REVIEW foreign key (review_id) references review (id);

alter table schedule
    add constraint FK_SCHEDULE_PLAN foreign key (plan_id) references plan (id);

alter table schedule_place
    add constraint FK_SCHEDULE_PLACE_SCHEDULE foreign key (schedule_id) references schedule (id);
alter table schedule_place
    add constraint FK_SCHEDULE_PLACE_PLACE foreign key (place_id) references place (id);

alter table recommended_place
    add constraint FK_RECOMMENDED_PLACE_PLACE foreign key (place_id) references place (id);
alter table recommended_place
    add constraint FK_RECOMMENDED_PLACE_REGION foreign key (region_id) references region (id);