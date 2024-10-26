
DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
  "id" BIGINT NOT NULL,
  "name" VARCHAR(50) NOT NULL,
  "password" VARCHAR(50) NOT NULL,
  "email" VARCHAR(64),
  "phone" VARCHAR(15),
  PRIMARY KEY ("id")
);
