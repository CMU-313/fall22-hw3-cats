create cached table DOCUMENT_REVIEWS ( REVIEWER_NAME varchar(40) not null, REVIEWED_DOCUMENT_ID int not null, ACADEMIC_SCORE int not null constraint ONE_THROUGH_TEN check (ACADEMIC_SCORE between 1 and 10), EXTRACURRICULR_SCORE int not null constraint ONE_THROUGH_TEN check (EXTRACURRICULR_SCORE between 1 and 10), ATHLETICS_SCORE int not null constraint ONE_THROUGH_TEN check (ATHLETICS_SCORE between 1 and 10), PERSONAL_FIT_SCORE int not null constraint ONE_THROUGH_TEN check (PERSONAL_FIT_SCORE between 1 and 10), DATE_CREATED date, primary key (REVIEWER_NAME) );
alter table DOCUMENT_REVIEWS add constraint FK_REVIEWED_DOCUMENT_ID foreign key (REVIEWED_DOCUMENT_ID) references T_DOCUMENT (DOC_ID_C) on delete restrict on update restrict;
update T_CONFIG set CFG_VALUE_C = '28' where CFG_ID_C = 'DB_VERSION';













