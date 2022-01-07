CREATE TABLE "public"."person"
(
    "id"   serial  NOT NULL,
    "name" text    NOT NULL,
    "age"  integer NOT NULL,
    PRIMARY KEY ("id")
);
INSERT INTO "public"."person" VALUES (DEFAULT, 'David', 12);
INSERT INTO "public"."person" VALUES (DEFAULT, 'Margaret', 23);
INSERT INTO "public"."person" VALUES (DEFAULT, 'Joe', 30);