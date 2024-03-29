DROP TABLE IF EXISTS
    public.requests,
    public.compilations_events,
    public.compilations,
    public.events,
    public.users,
    public.categories,
    public.events_likes,
    public.events_dislikes;

CREATE TABLE IF NOT EXISTS public.categories
(
    category_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY (MINVALUE 0 START 0),
    name        character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT categories_pkey PRIMARY KEY (category_id),
    CONSTRAINT uk_t8o6pivur7nn124jehx7cygw5 UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.users
(
    user_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY (MINVALUE 0 START 0),
    email   character varying(255) COLLATE pg_catalog."default",
    name    character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.events
(
    event_id           bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY (MINVALUE 0 START 0),
    annotation         character varying(2000) COLLATE pg_catalog."default",
    confirmed_requests integer,
    created_on         timestamp without time zone,
    description        character varying(7000) COLLATE pg_catalog."default",
    event_date         character varying(255) COLLATE pg_catalog."default",
    lat                real,
    lon                real,
    paid               boolean,
    participant_limit  integer,
    published_on       timestamp without time zone,
    request_moderation boolean,
    state              character varying(255) COLLATE pg_catalog."default",
    title              character varying(255) COLLATE pg_catalog."default",
    views              bigint,
    category_id        bigint,
    initiator_user_id  bigint,
    rating            bigint,
    CONSTRAINT events_pkey PRIMARY KEY (event_id),
    CONSTRAINT fkebk89pa1akksue2opwsmnkood FOREIGN KEY (initiator_user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fko6mla8j1p5bokt4dxrlmgwc28 FOREIGN KEY (category_id)
        REFERENCES public.categories (category_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.requests
(
    request_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY (MINVALUE 0 START 0),
    created    character varying(255) COLLATE pg_catalog."default",
    status     integer,
    event_id   bigint,
    user_id    bigint,
    CONSTRAINT requests_pkey PRIMARY KEY (request_id),
    CONSTRAINT fk8usbpx9csc6opbjg1d7kvtf8c FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkm7vtr0204t3xcymbx4sa9t1ot FOREIGN KEY (event_id)
        REFERENCES public.events (event_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.compilations
(
    compilation_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY (MINVALUE 0 START 0),
    pinned         boolean,
    title          character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT compilations_pkey PRIMARY KEY (compilation_id)
);

CREATE TABLE IF NOT EXISTS public.compilations_events
(
    compilations_compilation_id bigint NOT NULL,
    events_event_id             bigint NOT NULL,
    CONSTRAINT fk31ntq6v1hbc2y6uwawd9khtmw FOREIGN KEY (events_event_id)
        REFERENCES public.events (event_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmc6fbjrq3kw9qc2b2olcj01f3 FOREIGN KEY (compilations_compilation_id)
        REFERENCES public.compilations (compilation_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.events_likes
(
    user_id  bigint NOT NULL,
    event_id bigint NOT NULL,
    CONSTRAINT events_likes_pkey PRIMARY KEY (user_id, event_id)
);

CREATE TABLE IF NOT EXISTS public.events_dislikes
(
    user_id  bigint NOT NULL,
    event_id bigint NOT NULL,
    CONSTRAINT events_dislikes_pkey PRIMARY KEY (user_id, event_id)
);
