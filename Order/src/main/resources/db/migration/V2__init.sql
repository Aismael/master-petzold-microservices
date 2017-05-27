

CREATE TABLE todo (
	id BIGINT NOT NULL ,
	checked bit(1),
	description varchar(255),
	PRIMARY KEY (id)
);

INSERT INTO public.todo(
	id, checked, description)
VALUES (1, '0', 'hihi');

INSERT INTO public.todo(
	id, checked, description)
VALUES (2, '1', 'hihi');
