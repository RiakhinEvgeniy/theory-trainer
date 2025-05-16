use theory_db;

create table if not exists user_answer(
    id bigint not null primary key auto_increment,
    chosen_answer_id bigint not null,
    variant varchar(20) not null
);

alter table user_answer
add column answered_at timestamp;

alter table user_answer
    add column user_id bigint;

alter table user_answer
add foreign key(user_id)
references users(id);


alter table user_answer
    add column question_id bigint;

alter table user_answer
    add foreign key(question_id)
        references question(id);

alter table wrong_answer
add column question_id bigint;

alter table wrong_answer
add foreign key (question_id)
references question(id);

alter table users
add column ROLE varchar(20) not null;

alter table users
modify column email varchar(100);

alter table users
modify column name varchar(100);

alter table question
    modify column question text;