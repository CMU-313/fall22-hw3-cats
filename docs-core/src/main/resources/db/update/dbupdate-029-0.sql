
drop table DOCUMENT_REVIEWS;

create cached table DOCUMENT_REVIEWS ( REVIEWER_NAME varchar(40) not null, REVIEWED_DOCUMENT_ID varchar(36) not null, ACADEMIC_SCORE int not null constraint ONE_THROUGH_TEN check (ACADEMIC_SCORE between 0 and 11), EXTRACURRICULAR_SCORE int not null constraint ONE_THROUGH_TEN check (EXTRACURRICULAR_SCORE between 0 and 11), ATHLETICS_SCORE int not null constraint ONE_THROUGH_TEN check (ATHLETICS_SCORE between 0 and 11), PERSONAL_FIT_SCORE int not null constraint ONE_THROUGH_TEN check (PERSONAL_FIT_SCORE between 0 and 11), DATE_CREATED datetime, primary key (REVIEWER_NAME) );
alter table DOCUMENT_REVIEWS add constraint FK_REVIEWED_DOCUMENT_ID foreign key (REVIEWED_DOCUMENT_ID) references T_DOCUMENT (DOC_ID_C) on delete restrict on update restrict;
update T_CONFIG set CFG_VALUE_C = '29' where CFG_ID_C = 'DB_VERSION';

















