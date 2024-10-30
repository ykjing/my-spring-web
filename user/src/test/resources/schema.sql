
DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
  "id" VARCHAR(25) NOT NULL,
  "name" VARCHAR(50) NOT NULL,
  "salt" VARCHAR(15) NOT NULL,
  "password" VARCHAR(50) NOT NULL,
  "email" VARCHAR(64),
  "phone" VARCHAR(15),
  "create_dt" TIMESTAMP,
  PRIMARY KEY ("id")
);
